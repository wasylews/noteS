package com.genius.wasylews.notes.presentation.main.fragment.list.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.genius.wasylews.notes.R;
import com.genius.wasylews.notes.data.db.model.NoteModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> implements SwipeAdapter {

    private List<NoteModel> items = new ArrayList<>();
    private ItemActionsListener listener;

    public interface ItemActionsListener {

        void itemSwiped(int position);

        void itemClicked(int position);
    }

    public NoteAdapter(ItemActionsListener listener) {
        this.listener = listener;
    }

    public void setItems(List<NoteModel> noteModels) {
        items.clear();
        items.addAll(noteModels);
        notifyDataSetChanged();
    }

    public void insertItem(NoteModel item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public NoteModel getItem(int position) {
        return items.get(position);
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

    @Override
    public void onItemSwiped(int position) {
        listener.itemSwiped(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements SwipeAdapter.SwipeViewHolder {

        @BindView(R.id.card_note) CardView noteCard;
        @BindView(R.id.note_title) TextView noteTitle;
        @BindView(R.id.note_body) TextView noteBody;
        @BindView(R.id.view_background) View backgroundView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(NoteModel note) {
            if (TextUtils.isEmpty(note.getTitle())) {
                noteTitle.setVisibility(View.GONE);
            } else {
                noteTitle.setVisibility(View.VISIBLE);
                noteTitle.setText(note.getTitle());
            }

            if (TextUtils.isEmpty(note.getText())) {
                noteBody.setVisibility(View.GONE);
            } else {
                noteBody.setVisibility(View.VISIBLE);
                noteBody.setText(note.getText());
            }

            itemView.setOnClickListener(v -> listener.itemClicked(getAdapterPosition()));
        }

        @Override
        public View getForegroundView() {
            return noteCard;
        }

        @Override
        public View getBackgroundView() {
            return backgroundView;
        }
    }
}
