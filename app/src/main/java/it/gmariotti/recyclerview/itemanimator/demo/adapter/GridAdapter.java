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
package it.gmariotti.recyclerview.itemanimator.demo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.recyclerview.itemanimator.demo.R;

/**
 * @author Gabriele Mariotti (gabri.mariotti@gmail.com)
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.SimpleViewHolder> {

    public static final int LAST_POSITION = -1 ;
    private static final int COUNT = 3;

    private final Context mContext;
    private final List<Integer> mItems;
    private int mCurrentItemId = 0;

    public GridAdapter(Context context) {
        mContext = context;
        mItems = new ArrayList<Integer>(COUNT);
        for (int i = 0; i < COUNT; i++) {
            addItem(i);
        }
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;

        public SimpleViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        holder.title.setText(mItems.get(position).toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = holder.getLayoutPosition();
                addItem(itemPosition + 1);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int itemPosition = holder.getLayoutPosition();
                removeItem(itemPosition);
                return true;
            }
        });
    }

    public void addItem(int position) {
        final int id = mCurrentItemId++;
        position = position == LAST_POSITION ? getItemCount()  : position;
        mItems.add(position, id);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {

        if (position == LAST_POSITION && getItemCount() > 0)
            position = getItemCount() -1 ;

        if (position > LAST_POSITION && position < getItemCount()) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
