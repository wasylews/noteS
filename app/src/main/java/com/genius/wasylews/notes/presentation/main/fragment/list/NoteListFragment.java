package com.genius.wasylews.notes.presentation.main.fragment.list;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class NoteListFragment extends BaseToolbarFragment implements NoteListView {

    @Inject
    @InjectPresenter
    NoteListPresenter presenter;

    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.note_list) RecyclerView noteList;

    private NoteAdapter adapter = new NoteAdapter(new NoteAdapter.ItemActionsListener() {
        @Override
        public void itemSwiped(NoteModel item) {
            presenter.removeNote(item);
        }

        @Override
        public void itemClicked(NoteModel item) {
            Bundle args = new Bundle();
            args.putSerializable(AddNoteFragment.KEY_NOTE, item);
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

        DividerItemDecoration itemDecoration = new DividerItemDecoration(noteList.getContext(),
                DividerItemDecoration.VERTICAL);
        noteList.addItemDecoration(itemDecoration);
        noteList.setLayoutManager(new LinearLayoutManager(requireContext()));
        noteList.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeController(adapter));
        itemTouchHelper.attachToRecyclerView(noteList);
        presenter.loadData();
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
            case R.id.item_add:
                navigate(R.id.action_add_note);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
    public void showNoteRemoved(NoteModel note) {
        adapter.removeItem(note);
    }
}
