package com.adib.mynote;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.adib.mynote.room.Note;
import com.adib.mynote.room.NoteDatabase;
import com.adib.mynote.viewmodel.AddViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Add_Fragment extends Fragment {
    private AddViewModel model;
    private FloatingActionButton addButton;
    private EditText textNote;
    private EditText titleNote;
    private NoteDatabase database;
    private boolean isDataSet;

    public Add_Fragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Init Variable
        database = NoteDatabase.getInstance(getActivity());
        addButton = getView().findViewById(R.id.Add_Button);
        model = new ViewModelProvider(getActivity()).get(AddViewModel.class);
        textNote = getView().findViewById(R.id.Text_Note);
        titleNote = getView().findViewById(R.id.Title_Note);
        isDataSet = false;
        View backButton;

        //Set Title and Text From ViewModel
        titleNote.setText(model.getNoteTitle());
        textNote.setText(model.getNoteText());

        //Add Button Logic
        addButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                if(!(titleNote.getText().toString().isEmpty()) && !(textNote.getText().toString().isEmpty())){
                    Note note = new Note(titleNote.getText().toString(),textNote.getText().toString());
                    database.noteDao().insert(note).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> {
                                Toast toast = Toast.makeText(getActivity(), "Add Note", Toast.LENGTH_SHORT);
                                toast.show();
                                isDataSet = true;
                                Navigation.findNavController(view).popBackStack();
                            },throwable -> {
                                Toast toast = Toast.makeText(getActivity(), "Error " + throwable, Toast.LENGTH_SHORT);
                                toast.show();
                            });
                }
                else {
                    Toast toast = Toast.makeText(getActivity(), "Please Input Some Note", Toast.LENGTH_SHORT);
                    toast.show();
                }

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

    @Override
    public void onStop() {
        super.onStop();

        //Set Title and Text To Model When Fragment Lifecycle onStop
        if(isDataSet){
            model.setNoteText("");
            model.setNoteTitle("");
        }
        else {
            model.setNoteText(textNote.getText().toString());
            model.setNoteTitle(titleNote.getText().toString());
        }
    }
}