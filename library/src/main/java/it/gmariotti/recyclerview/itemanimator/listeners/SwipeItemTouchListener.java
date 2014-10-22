package it.gmariotti.recyclerview.itemanimator.listeners;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

public class SwipeItemTouchListener implements OnItemTouchListener {

    private static final String LOGTAG = "SwipeItemTouchListener";

    /**
     * Interface definition for a callback to be invoked when an item in the
     * host RecyclerView has been swiped.
     */
    public interface OnItemSwipeListener {

        /**
         * Callback method to be invoked when an item in the host RecyclerView
         * has been swiped.
         *
         * @param parent The RecyclerView where the click happened.
         *
         * @param position The position of the view in the adapter.
         * @param id The row id of the item that was clicked.
         */
        void onDismiss(RecyclerView parent, RecyclerView.ViewHolder holder, int position, long id);
    }


    private final RecyclerView mHostView;
    private final GestureDetector mGestureDetector;
    private OnItemSwipeListener mItemSwipeListener;

    private SwipeItemTouchListener(RecyclerView hostView) {
        mHostView = hostView;

        final Context context = mHostView.getContext();
        mGestureDetector = new ItemSwipeGestureDetector(context, new ItemSwipeGestureListener());
    }

    private boolean isAttachedToWindow() {
        if (Build.VERSION.SDK_INT >= 19) {
            return mHostView.isAttachedToWindow();
        } else {
            return (mHostView.getHandler() != null);
        }
    }

    private boolean hasAdapter() {
        return (mHostView.getAdapter() != null);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent event) {
        if (!isAttachedToWindow() || !hasAdapter()) {
            return false;
        }

        mGestureDetector.onTouchEvent(event);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent event) {
        // We can silently track tap and and long presses by silently
        // intercepting touch events in the host RecyclerView.
    }

    /**
     * Register a callback to be invoked when an item in the host
     * RecyclerView has been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    public void setOnItemSwipeListener(OnItemSwipeListener listener) {
        mItemSwipeListener = listener;
    }


    public static SwipeItemTouchListener addTo(RecyclerView recyclerView) {
        SwipeItemTouchListener listener = new SwipeItemTouchListener(recyclerView);
        recyclerView.addOnItemTouchListener(listener);
        return listener;
    }

    private class ItemSwipeGestureDetector extends GestureDetector {
        private final ItemSwipeGestureListener mGestureListener;

        public ItemSwipeGestureDetector(Context context, ItemSwipeGestureListener listener) {
            super(context, listener);
            mGestureListener = listener;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            final boolean handled = super.onTouchEvent(event);

            final int action = event.getAction() & MotionEventCompat.ACTION_MASK;
            switch (action){
                case MotionEvent.ACTION_UP:
                    mGestureListener.onUp(event);
                    break;
                case MotionEvent.ACTION_MOVE:
                    mGestureListener.onMove(event);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    mGestureListener.onCancel(event);
                    break;
            }

            return handled;
        }
    }

    private class ItemSwipeGestureListener extends SimpleOnGestureListener {

        private static final int MIN_FLING_VELOCITY_FACTOR = 16;

        /**
         * The {@link android.view.View} that is actually being swiped.
         */
        private View mTargetChild;

        // Cached ViewConfiguration and system-wide constant values
        private int mSlop;
        private int mMinFlingVelocity;
        private int mMaxFlingVelocity;

        // Transient properties
        private int mViewWidth = 1;
        private float mDownX;
        private float mDownY;
        private boolean mSwiping;
        private int mSwipingSlop;
        private VelocityTracker mVelocityTracker;
        private boolean mPaused;
        private int swipeDistanceDivisor = 2;

        public ItemSwipeGestureListener(){
            ViewConfiguration vc = ViewConfiguration.get(mHostView.getContext());
            mSlop = vc.getScaledTouchSlop();
            mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * MIN_FLING_VELOCITY_FACTOR;
            mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        }


        @Override
        public boolean onDown(MotionEvent event) {

            if (mPaused) {
                return false;
            }

            if (mSwiping){
                return true;
            }

            mDownX = event.getRawX();
            mDownY = event.getRawY();

            mTargetChild = mHostView.findChildViewUnder(event.getX(),event.getY());
            if (mTargetChild == null) {
                return false;
            }

            if (mViewWidth < 2) {
                mViewWidth = mHostView.getLayoutManager().getDecoratedMeasuredWidth(mTargetChild);
            }

            if (!isDismissable(mTargetChild)){
                mTargetChild = null;
                return false;
            }

            mVelocityTracker = VelocityTracker.obtain();
            mVelocityTracker.addMovement(event);

            mTargetChild.onTouchEvent(event);

            return (mTargetChild != null);
        }


        public boolean onMove(MotionEvent event) {

            if (mVelocityTracker == null || mPaused || mTargetChild == null) {
                return false;
            }

            mVelocityTracker.addMovement(event);
            float deltaX = event.getRawX() - mDownX;
            float deltaY = event.getRawY() - mDownY;

            if (Math.abs(deltaX) > mSlop && Math.abs(deltaY) < Math.abs(deltaX) / 2)  {
                mSwiping = true;
                mSwipingSlop = (deltaX > 0 ? mSlop : -mSlop);
                mHostView.requestDisallowInterceptTouchEvent(true);

                // Cancel recyclerView's touch (un-highlighting the item)
                MotionEvent cancelEvent = MotionEvent.obtain(event);
                cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                        (event.getActionIndex()
                                << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                mTargetChild.onTouchEvent(cancelEvent);
                cancelEvent.recycle();
            }

            if (mSwiping) {
                ViewCompat.setTranslationX(mTargetChild, deltaX - mSwipingSlop);
                /*
                mHostView.getItemAnimator().animateMove(mHostView.getChildViewHolder(mTargetChild),
                        (int)mTargetChild.getX(),
                        (int)mTargetChild.getY(),
                        (int)mTargetChild.getX()  - ((int)deltaX -mSwipingSlop) ,
                        (int)mTargetChild.getY());
                */
                ViewCompat.setAlpha(mTargetChild, (Math.max(0f, Math.min(1f,
                        1f - 2f * Math.abs(deltaX) / mViewWidth))));
                return true;
            }

            return false;
        }


        public boolean onUp(MotionEvent event) {

            if (mVelocityTracker == null || mTargetChild == null) {
                return false;
            }

            float deltaX = event.getRawX() - mDownX;
            mVelocityTracker.addMovement(event);
            mVelocityTracker.computeCurrentVelocity(1000);
            float velocityX = mVelocityTracker.getXVelocity();
            float absVelocityX = Math.abs(velocityX);
            float absVelocityY = Math.abs(mVelocityTracker.getYVelocity());
            boolean dismiss = false;
            boolean dismissRight = false;

            if (Math.abs(deltaX) > mViewWidth / swipeDistanceDivisor  && mSwiping) {
                dismiss = true;
                dismissRight = deltaX > 0;
            } else if (mMinFlingVelocity <= absVelocityX && absVelocityX <= mMaxFlingVelocity
                    && absVelocityY < absVelocityX && mSwiping) {
                // dismiss only if flinging in the same direction as dragging
                dismiss = (velocityX < 0) == (deltaX < 0);
                dismissRight = mVelocityTracker.getXVelocity() > 0;
            }
            if (dismiss) {
                // dismiss
                doDismiss(mTargetChild, dismissRight);

            } else {
                doCancelSwipe(mTargetChild);
            }

            mVelocityTracker.recycle();
            mVelocityTracker = null;
            mDownX = 0;
            mDownY = 0;
            mTargetChild = null;

            if (mSwiping){
                //To prevent onClick event with a fast swipe
                mSwiping = false;
                return true;
            }
            mSwiping = false;
            return false;
        }

        private boolean onCancel(MotionEvent event){

            if (mVelocityTracker == null || mTargetChild == null) {
                return false;
            }

            doCancelSwipe(mTargetChild);

            mVelocityTracker.recycle();
            mVelocityTracker = null;
            mDownX = 0;
            mDownY = 0;
            mTargetChild = null;
            mSwiping = false;
            return false;
        }

        private void doDismiss(final View targetChild, boolean dismissRight) {

            if (targetChild != null) {
                ViewCompat.animate(targetChild)
                        .translationX(dismissRight ? mViewWidth : -mViewWidth)
                        .alpha(0)
                        .setDuration(mHostView.getItemAnimator().getMoveDuration())
                        .setListener(new ViewPropertyAnimatorListener() {

                            @Override
                            public void onAnimationStart(View targetView) {

                            }

                            @Override
                            public void onAnimationEnd(View targetView) {
                                performDismiss(targetView);
                            }

                            @Override
                            public void onAnimationCancel(View targetView) {

                            }
                        });
            }

        }

        private void performDismiss(View targetChild) {
            if (mItemSwipeListener != null && targetChild!=null) {
                RecyclerView.ViewHolder holder = mHostView.getChildViewHolder(targetChild);
                int position = mHostView.getChildPosition(targetChild);
                long id = mHostView.getChildItemId(targetChild);
                mItemSwipeListener.onDismiss(mHostView, holder, position, id);
            }
        }

        private void doCancelSwipe(View targetChild) {
            if (targetChild != null) {
                // cancel
                ViewCompat.animate(targetChild)
                        .translationX(0)
                        .alpha(1)
                        .setDuration(mHostView.getItemAnimator().getMoveDuration());
            }
        }


        private boolean isDismissable(final View view){
            //TODO
            return true;
        }

    }
}
