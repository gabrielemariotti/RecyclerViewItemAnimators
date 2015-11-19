/*
 * ******************************************************************************
 *   Copyright (c) 2015 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */
package it.gmariotti.recyclerview.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * The class applies multiple {@link Animator}s at once to views when they are first shown.
 * The Animators applied include the animations specified in {@link #getAnimators(View)}, plus an alpha transition.
 *
 * @Author Gabriele Mariotti
 */
public abstract class AnimatorAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

    /**
     * Saved instance state key for the ViewAnimator
     */
    private static final String SAVEDINSTANCESTATE_VIEWANIMATOR = "savedinstancestate_viewanimator";

    /**
     * The ViewAnimator responsible for animating the Views.
     */
    @Nullable
    private ViewAnimator mViewAnimator;

    protected RecyclerView mRecyclerView;

    //-----------------------------------------------------------------------------
    // Constructors
    //-----------------------------------------------------------------------------

    public AnimatorAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        mAdapter = adapter;
    }


    //-----------------------------------------------------------------------------
    // Animators methods
    //-----------------------------------------------------------------------------

    /**
     * Call this method before setting this adapter to the RecyclerView
     *
     * @param recyclerView
     */
    public void setRecyclerView(@NonNull final RecyclerView recyclerView) {
        mViewAnimator = new ViewAnimator(recyclerView);
        mRecyclerView = recyclerView;
    }

    /**
     * Returns the Animators to apply to the views.
     *
     * @param view   The view that will be animated, as retrieved by onBindViewHolder().
     */
    @NonNull
    public abstract Animator[] getAnimators(@NonNull View view);

    /**
     * Alpha property
     */
    private static final String ALPHA = "alpha";

    /**
     * Animates given View if necessary.
     *
     * @param position the position of the item the View represents.
     * @param view     the View that should be animated.
     */
    private void animateViewIfNecessary(final int position, @NonNull final View view) {
        assert mViewAnimator != null;

        Animator[] animators = getAnimators(view);
        Animator alphaAnimator = ObjectAnimator.ofFloat(view, ALPHA, 0, 1);
        Animator[] concatAnimators = AnimatorUtil.concatAnimators(animators, alphaAnimator);
        if (mViewAnimator != null) {
            mViewAnimator.animateViewIfNecessary(position, view, concatAnimators);
        } else {
            Log.w("AnimatorAdapter", "Warning: call AnimatorAdapter.setRecyclerView(RecyclerView recyclerView) before calling the setAdapter() method");
        }
    }

    //-----------------------------------------------------------------------------
    // SaveInstanceState
    //-----------------------------------------------------------------------------
    /**
     * Returns a Parcelable object containing the AnimationAdapter's current dynamic state.
     */
    @NonNull
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();

        if (mViewAnimator != null) {
            bundle.putParcelable(SAVEDINSTANCESTATE_VIEWANIMATOR, mViewAnimator.onSaveInstanceState());
        }

        return bundle;
    }

    /**
     * Restores this AnimationAdapter's state.
     *
     * @param parcelable the Parcelable object previously returned by {@link #onSaveInstanceState()}.
     */
    public void onRestoreInstanceState(@Nullable final Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            if (mViewAnimator != null) {
                mViewAnimator.onRestoreInstanceState(bundle.getParcelable(SAVEDINSTANCESTATE_VIEWANIMATOR));
            }
        }
    }

    //-----------------------------------------------------------------------------
    // RecyclerView methods
    //-----------------------------------------------------------------------------

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mAdapter.onBindViewHolder(holder, position);
        animateViewIfNecessary(position, holder.itemView);
    }

    @Override public int getItemCount() {
        return mAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return mAdapter.getItemViewType(position);
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        mAdapter.registerAdapterDataObserver(observer);
    }

    @Override public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        mAdapter.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        mAdapter.onBindViewHolder(holder, position, payloads);
        animateViewIfNecessary(position, holder.itemView);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        mAdapter.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        return mAdapter.getItemId(position);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        mAdapter.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        return mAdapter.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mAdapter.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        mAdapter.onViewDetachedFromWindow(holder);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mAdapter.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mAdapter.onDetachedFromRecyclerView(recyclerView);
    }
}
