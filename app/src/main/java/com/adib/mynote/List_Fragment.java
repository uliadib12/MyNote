package com.adib.mynote;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adib.mynote.recyclerview.NoteAdapter;
import com.adib.mynote.room.Note;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class List_Fragment extends Fragment {
    private ArrayList<Note> noteArrayList;

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
        RecyclerView recyclerView;
        NoteAdapter noteAdapter;

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
        recyclerView = (RecyclerView) getView().findViewById(R.id.RecycleView_NoteList);
        noteAdapter = new NoteAdapter(noteArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noteAdapter);
    }

    private void addData(){
        noteArrayList = new ArrayList<>();
        noteArrayList.add(new Note("Recycle View","Percobaan Recycle"));
    }
}