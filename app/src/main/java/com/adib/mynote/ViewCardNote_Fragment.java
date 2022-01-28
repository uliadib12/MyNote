package com.adib.mynote;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.adib.mynote.viewmodel.AddViewModel;
import com.adib.mynote.viewmodel.ViewCardNoteModel;

import org.jetbrains.annotations.NotNull;

public class ViewCardNote_Fragment extends Fragment {
    private ViewCardNoteModel model;
    private EditText textNote;
    private EditText titleNote;

    public ViewCardNote_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_card_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Init Variable
        titleNote = getView().findViewById(R.id.Title_Note);
        textNote = getView().findViewById(R.id.Text_Note);
        model = new ViewModelProvider(getActivity()).get(ViewCardNoteModel.class);

        //Set Observer Title
        final Observer<String> TitleObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String title) {
                titleNote.setText(title);
            }
        };
        model.getTitle().observe(this, TitleObserver);

        //Set Observer Text
        final Observer<String> TextObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String text) {
                textNote.setText(text);
            }
        };
        model.getText().observe(this, TextObserver);

        //Set Title and Text From Bundle Args
        model.getTitle().setValue(getArguments().getString("Title"));
        model.getText().setValue(getArguments().getString("Text"));
    }
}