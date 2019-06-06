package com.genius.wasylews.notes.presentation.main.fragment.list.adapter;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeController extends ItemTouchHelper.Callback {

    private SwipeAdapter adapter;

    public SwipeController(SwipeAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                         int direction) {
        adapter.onItemSwiped(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder instanceof SwipeAdapter.SwipeViewHolder) {
                SwipeAdapter.SwipeViewHolder holder = (SwipeAdapter.SwipeViewHolder) viewHolder;
                getDefaultUIUtil().onSelected(holder.getForegroundView());
                holder.getBackgroundView().setVisibility(View.VISIBLE);
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (viewHolder instanceof SwipeAdapter.SwipeViewHolder) {
            SwipeAdapter.SwipeViewHolder holder = (SwipeAdapter.SwipeViewHolder) viewHolder;
            getDefaultUIUtil().clearView(holder.getForegroundView());
            holder.getBackgroundView().setVisibility(View.GONE);
        }
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c,
                                @NonNull RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (viewHolder instanceof SwipeAdapter.SwipeViewHolder) {
            SwipeAdapter.SwipeViewHolder holder = (SwipeAdapter.SwipeViewHolder) viewHolder;
            getDefaultUIUtil().onDrawOver(c, recyclerView, holder.getForegroundView(),
                    dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c,
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (viewHolder instanceof SwipeAdapter.SwipeViewHolder) {
            SwipeAdapter.SwipeViewHolder holder = (SwipeAdapter.SwipeViewHolder) viewHolder;
            getDefaultUIUtil().onDraw(c, recyclerView, holder.getForegroundView(),
                    dX, dY, actionState, isCurrentlyActive);
        }
    }
}
