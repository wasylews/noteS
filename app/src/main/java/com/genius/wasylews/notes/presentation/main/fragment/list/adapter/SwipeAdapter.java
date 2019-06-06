package com.genius.wasylews.notes.presentation.main.fragment.list.adapter;

import android.view.View;

public interface SwipeAdapter {

    void onItemSwiped(int position);


    interface SwipeViewHolder {

       View getForegroundView();

        View getBackgroundView();
    }
}
