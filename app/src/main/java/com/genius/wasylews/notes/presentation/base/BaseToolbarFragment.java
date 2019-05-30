package com.genius.wasylews.notes.presentation.base;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.genius.wasylews.notes.R;

import butterknife.BindView;

public abstract class BaseToolbarFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void onViewReady(Bundle args) {
        getBaseActivity().setSupportActionBar(toolbar);
        getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
