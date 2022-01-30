package com.adib.mynote;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.adib.mynote.room.NoteDatabase;
import com.adib.mynote.viewmodel.AddViewModel;
import com.adib.mynote.viewmodel.ViewCardNoteModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ViewCardNote_Fragment extends Fragment {
    private NoteDatabase database;
    private ViewCardNoteModel model;
    private EditText textNote;
    private EditText titleNote;
    private FloatingActionButton editButton;
    private FloatingActionButton deleteButton;
    private View backButton;

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
        database = NoteDatabase.getInstance(getActivity());
        titleNote = getView().findViewById(R.id.Title_Note);
        textNote = getView().findViewById(R.id.Text_Note);
        editButton = getView().findViewById(R.id.Edit_Button);
        deleteButton = getView().findViewById(R.id.Delete_Button);
        model = new ViewModelProvider(getActivity()).get(ViewCardNoteModel.class);

        //Animation
        editButton.bringToFront();
        ObjectAnimator animTransX = ObjectAnimator.ofFloat(editButton, "translationX", -130f);
        animTransX.setDuration(700);
        ObjectAnimator animFade = ObjectAnimator.ofFloat(editButton, "alpha", 0f,1f);
        animFade.setDuration(700);
        ObjectAnimator animaFadeDelete = ObjectAnimator.ofFloat(deleteButton, "alpha", 0f,1f);
        animaFadeDelete.setDuration(700);
        animaFadeDelete.start();
        animFade.start();
        animTransX.start();

        //Set Observer
        final Observer<String> TitleObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String title) {
                titleNote.setText(title);
            }
        };
        final Observer<String> TextObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String text) {
                textNote.setText(text);
            }
        };
        model.getTitle().observe(this, TitleObserver);
        model.getText().observe(this, TextObserver);

        //Set Title and Text From Bundle Args
        model.getTitle().setValue(getArguments().getString("Title"));
        model.getText().setValue(getArguments().getString("Text"));

        //Edit Button Logic
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!titleNote.getText().toString().isEmpty() && !textNote.getText().toString().isEmpty()) {
                    editNote(view);
                }
                else {
                    Toast toast = Toast.makeText(getActivity(), "Error Please Insert Title and Text", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        //Delete Button Logic
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote();
            }
        });

        //Back Button Logic
        backButton = getView().findViewById(R.id.Back_Button_Add);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });
    }

    private void editNote(View view) {
        int id = getArguments().getInt("Id");
        database.noteDao().update(id,titleNote.getText().toString(),textNote.getText().toString()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
//                    Toast toast = Toast.makeText(getActivity(), "Edit Note", Toast.LENGTH_SHORT);
//                    toast.show();
                    Navigation.findNavController(view).popBackStack();
                },throwable -> {
                    Toast toast = Toast.makeText(getActivity(), "Error " + throwable, Toast.LENGTH_SHORT);
                    toast.show();
                });;
    }

    private void deleteNote(){
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Delete Note")
                .setMessage("Deleting Note ?")
        .setNegativeButton("Cancel", (dialog, which) -> {
            Toast toast = Toast.makeText(getActivity(), "Cancel Delete", Toast.LENGTH_SHORT);
            toast.show();
        })
        .setPositiveButton("Delete",(dialog, which) -> {
            int id = getArguments().getInt("Id");
            database.noteDao().delete(id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Navigation.findNavController(getView()).popBackStack();
                    },throwable -> {
                        Toast toast = Toast.makeText(getActivity(), "Error " + throwable, Toast.LENGTH_SHORT);
                        toast.show();
                    });;
        })
        .show();
    }
}