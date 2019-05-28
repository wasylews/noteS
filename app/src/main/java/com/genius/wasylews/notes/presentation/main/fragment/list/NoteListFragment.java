package com.genius.wasylews.notes.presentation.main.fragment.list;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.genius.wasylews.notes.R;
import com.genius.wasylews.notes.presentation.base.BaseFragment;
import com.genius.wasylews.notes.presentation.main.adapter.NoteAdapter;

import javax.inject.Inject;

import butterknife.BindView;

public class NoteListFragment extends BaseFragment implements NoteListView {

    @Inject
    @InjectPresenter
    NoteListPresenter presenter;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private NoteAdapter adapter = new NoteAdapter();


    @ProvidePresenter
    NoteListPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        getBaseActivity().setSupportActionBar(toolbar);
        getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getBaseActivity().getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_list;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
