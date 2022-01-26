package com.adib.mynote.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public NoteAdapter(ArrayList<Note> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @NotNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.card_list, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NoteViewHolder holder, int position) {
        holder.header.setText(dataList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder{
        private TextView header;

        public NoteViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.Header_Card);
        }
    }
}
