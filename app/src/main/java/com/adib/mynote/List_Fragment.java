package com.adib.mynote;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.adib.mynote.recyclerview.NoteAdapter;
import com.adib.mynote.room.Note;
import com.adib.mynote.room.NoteDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class List_Fragment extends Fragment {
    private ArrayList<Note> noteArrayList;
    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private NoteDatabase database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Init Variable
        View backButton;
        database = NoteDatabase.getInstance(getActivity());

        //Back Button Logic
        backButton = getView().findViewById(R.id.Back_Button_List);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        //RecycleView Logic
        addData();
    }

    private void addData(){
        noteArrayList = new ArrayList<>();
        database.noteDao().getAll().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( data -> {
                    NoteAdapter.ListenerNoteViewHolder listenerNoteViewHolder = new NoteAdapter.ListenerNoteViewHolder() {
                        @Override
                        public void onClick(View v, int position) {
                            Toast toast = Toast.makeText(getActivity(), "Title: " + data.get(position).getTitle(), Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    };
                    NoteAdapter.ListenerlongNoteViewHolder listenerlongNoteViewHolder = new NoteAdapter.ListenerlongNoteViewHolder() {
                        @Override
                        public void onClick(View v, int position) {
                            Toast toast = Toast.makeText(getActivity(), "ID: " + position, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    };
                    noteArrayList.addAll(data);
                    recyclerView = (RecyclerView) getView().findViewById(R.id.RecycleView_NoteList);
                    noteAdapter = new NoteAdapter(noteArrayList, listenerNoteViewHolder, listenerlongNoteViewHolder);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(noteAdapter);
                },throwable -> {
                    Toast toast = Toast.makeText(getActivity(), "Error " + throwable, Toast.LENGTH_SHORT);
                    toast.show();
                });
    }
}