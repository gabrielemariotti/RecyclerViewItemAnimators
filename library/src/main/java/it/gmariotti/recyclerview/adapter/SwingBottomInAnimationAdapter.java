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
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * An implementation of the AnimationAdapter class which applies a
 * swing-in-from-bottom-animation to views.
 *
 * @Author Gabriele Mariotti
 */
public class SwingBottomInAnimationAdapter extends AnimatorAdapter {

    private static final String TRANSLATION_Y = "translationY";

    public SwingBottomInAnimationAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter, RecyclerView recyclerView) {
        super(adapter, recyclerView);
    }

    @NonNull
    @Override
    public Animator[] getAnimators(@NonNull View view) {
        float mOriginalY = mRecyclerView.getLayoutManager().getDecoratedTop(view);
        float mDeltaY = mRecyclerView.getHeight() - mOriginalY;

        return new Animator[]{ObjectAnimator.ofFloat(view, TRANSLATION_Y, mDeltaY, 0)};
    }
}
