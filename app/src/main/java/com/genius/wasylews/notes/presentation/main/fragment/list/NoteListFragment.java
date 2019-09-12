package com.genius.wasylews.notes.presentation.main.fragment.list;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.genius.wasylews.notes.R;
import com.genius.wasylews.notes.data.db.model.NoteModel;
import com.genius.wasylews.notes.presentation.base.BaseToolbarFragment;
import com.genius.wasylews.notes.presentation.main.fragment.add.AddNoteFragment;
import com.genius.wasylews.notes.presentation.main.fragment.list.adapter.NoteAdapter;
import com.genius.wasylews.notes.presentation.main.fragment.list.adapter.SwipeController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.jakewharton.rxbinding3.appcompat.RxSearchView;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;

public class NoteListFragment extends BaseToolbarFragment implements NoteListView {

    @Inject
    @InjectPresenter
    NoteListPresenter presenter;

    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.nav_view) NavigationView navView;
    @BindView(R.id.note_list) RecyclerView noteList;
    @BindView(R.id.fab_add) FloatingActionButton addNoteFab;

    private NoteAdapter adapter = new NoteAdapter(new NoteAdapter.ItemActionsListener() {

        @Override
        public void itemSwiped(int position) {
            // removing item from db because user can close app before snackbar will be dismissed
            // and there will not be onDismissed callback
            presenter.removeNote(adapter.getItem(position), position);
        }

        @Override
        public void itemClicked(int position) {
            Bundle args = new Bundle();
            args.putSerializable(AddNoteFragment.KEY_NOTE, adapter.getItem(position));
            navigate(R.id.action_edit_note, args);
        }
    });

    @ProvidePresenter
    NoteListPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected void onViewReady(Bundle args) {
        super.onViewReady(args);
        getBaseActivity().getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        navView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.settings) {
                navigate(R.id.action_settings);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            return false;
        });

        presenter.addDisposable(RxView.clicks(addNoteFab).subscribe(unit -> navigate(R.id.action_add_note)));

        initNoteList();
        presenter.loadNotes();
    }

    private void initNoteList() {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(noteList.getContext(),
                DividerItemDecoration.VERTICAL);
        noteList.addItemDecoration(itemDecoration);
        noteList.setLayoutManager(new LinearLayoutManager(requireContext()));
        noteList.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeController(adapter));
        itemTouchHelper.attachToRecyclerView(noteList);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_list;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();
        presenter.addDisposable(RxSearchView.queryTextChanges(searchView)
                .debounce(200, TimeUnit.MILLISECONDS)
                .map(CharSequence::toString)
                .subscribe(text -> presenter.searchNotes(text)));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showNotes(List<NoteModel> noteModels) {
        adapter.setItems(noteModels);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(drawerLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showNoteRemoved(NoteModel note, int position) {
        adapter.removeItem(position);

        Snackbar.make(drawerLayout, R.string.message_note_removed, Snackbar.LENGTH_LONG)
                .setAction(R.string.title_undo, v -> presenter.insertNote(note, position))
                .show();
    }

    @Override
    public void showNoteInserted(NoteModel note, int position) {
        adapter.insertItem(note, position);
    }
}
