package com.genius.wasylews.notes.presentation.main.fragment.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.genius.wasylews.notes.R;
import com.genius.wasylews.notes.data.db.model.NoteModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<NoteModel> items = new ArrayList<>();

    public void addItem(NoteModel item) {
        items.add(item);
        notifyItemInserted(items.size());
    }

    public void setItems(List<NoteModel> noteModels) {
        items.clear();
        items.addAll(noteModels);
        notifyDataSetChanged();
    }

    public void updateItem(NoteModel item, int position) {
        items.set(position, item);
        notifyItemChanged(position);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.note_title) TextView noteTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(NoteModel note) {
            noteTitle.setText(note.getTitle());
        }
    }
}
