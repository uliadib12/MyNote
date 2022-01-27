package com.adib.mynote.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adib.mynote.R;
import com.adib.mynote.room.Note;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private ArrayList<Note> dataList;
    private ListenerNoteViewHolder listenerNoteViewHolder;
    private ListenerlongNoteViewHolder listenerlongNoteViewHolder;

    public NoteAdapter(ArrayList<Note> dataList, ListenerNoteViewHolder listenerNoteViewHolder, ListenerlongNoteViewHolder listenerlongNoteViewHolder) {
        this.listenerNoteViewHolder = listenerNoteViewHolder;
        this.listenerlongNoteViewHolder = listenerlongNoteViewHolder;
        this.dataList = dataList;
    }

    @NonNull
    @NotNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_list, parent, false);
        return new NoteViewHolder(view, listenerNoteViewHolder, listenerlongNoteViewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NoteViewHolder holder, int position) {
        holder.header.setText(dataList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private TextView header;
        private ListenerNoteViewHolder listenerNoteViewHolder;
        private ListenerlongNoteViewHolder listenerlongNoteViewHolder;

        public NoteViewHolder(@NonNull @NotNull View itemView, ListenerNoteViewHolder listenerNoteViewHolder, ListenerlongNoteViewHolder listenerlongNoteViewHolder){
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            header = (TextView) itemView.findViewById(R.id.Header_Card);
            this.listenerNoteViewHolder = listenerNoteViewHolder;
            this.listenerlongNoteViewHolder = listenerlongNoteViewHolder;
        }

        @Override
        public void onClick(View v) {
            listenerNoteViewHolder.onClick(v,getAbsoluteAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            listenerlongNoteViewHolder.onClick(v,getAbsoluteAdapterPosition());
            return true;
        }
    }

    public interface ListenerNoteViewHolder{
        public void onClick(View v, int position);
    }
    public interface ListenerlongNoteViewHolder{
        public void onClick(View v, int position);
    }
}
