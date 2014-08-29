/*
 * ******************************************************************************
 *   Copyright (c) 2014 Gabriele Mariotti.
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
package it.gmariotti.recyclerview.itemanimator;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class SlideScaleInOutRightItemAnimator extends BaseItemAnimator {

    private float DEFAULT_SCALE_INITIAL = 0.6f;

    private float mInitialScaleX = DEFAULT_SCALE_INITIAL;
    private float mInitialScaleY = DEFAULT_SCALE_INITIAL;

    private float mEndScaleX = DEFAULT_SCALE_INITIAL;
    private float mEndScaleY = DEFAULT_SCALE_INITIAL;

    private float mOriginalScaleX;
    private float mOriginalScaleY;

    public SlideScaleInOutRightItemAnimator(RecyclerView recyclerView) {
        super(recyclerView);

        setAddDuration(750);
        setRemoveDuration(750);
    }

    protected void animateRemoveImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;

        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).setDuration(getRemoveDuration()).
                scaleX(mEndScaleX).scaleY(mEndScaleY).
                translationX(+mRecyclerView.getWidth()).setListener(new VpaListenerAdapter() {
            @Override
            public void onAnimationEnd(View view) {
                ViewCompat.setScaleX(view, mEndScaleX);
                ViewCompat.setScaleY(view, mEndScaleY);
                ViewCompat.setTranslationX(view, +mRecyclerView.getWidth());
                dispatchRemoveFinished(holder);
                mRemoveAnimations.remove(holder);
                dispatchFinishedWhenDone();
            }
        }).start();
        mRemoveAnimations.add(holder);
    }

    @Override
    protected void prepareAnimateAdd(RecyclerView.ViewHolder holder) {
        retrieveOriginalScale(holder);
        ViewCompat.setScaleX(holder.itemView, mInitialScaleX);
        ViewCompat.setScaleY(holder.itemView, mInitialScaleY);

        ViewCompat.setTranslationX(holder.itemView, +mRecyclerView.getWidth());
    }



    protected void animateAddImpl(final RecyclerView.ViewHolder holder) {
        final View view = holder.itemView;

        ViewCompat.animate(view).cancel();
        ViewCompat.animate(view).scaleX(mOriginalScaleX).scaleY(mOriginalScaleY).translationX(0)
                .setDuration(getAddDuration()).
                setListener(new VpaListenerAdapter() {
                    @Override
                    public void onAnimationCancel(View view) {
                        ViewCompat.setTranslationX(view, 0);
                        ViewCompat.setScaleX(view, mOriginalScaleX);
                        ViewCompat.setScaleY(view, mOriginalScaleY);
                    }

                    @Override
                    public void onAnimationEnd(View view) {
                        dispatchAddFinished(holder);
                        mAddAnimations.remove(holder);
                        dispatchFinishedWhenDone();
                    }
                }).start();
        mAddAnimations.add(holder);
    }

    public void setInitialScale(float scaleXY){
       setInitialScale(scaleXY, scaleXY);
    }

    public void setInitialScale(float scaleX, float scaleY){
        mInitialScaleX = scaleX;
        mInitialScaleY = scaleY;

        mEndScaleX = scaleX;
        mEndScaleY = scaleY;
    }

    public void setEndScale(float scaleXY){
        setEndScale(scaleXY, scaleXY);
    }

    public void setEndScale(float scaleX, float scaleY){
        mEndScaleX = scaleX;
        mEndScaleY = scaleY;
    }

    private void retrieveOriginalScale(RecyclerView.ViewHolder holder) {
        mOriginalScaleX = holder.itemView.getScaleX();
        mOriginalScaleY = holder.itemView.getScaleY();
    }

}
