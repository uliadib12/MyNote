package com.adib.mynote;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.adib.mynote.viewmodel.AddViewModel;

import org.jetbrains.annotations.NotNull;

public class Add_Fragment extends Fragment {
    private AddViewModel model;
    private EditText textNote;

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
        model = new ViewModelProvider(getActivity()).get(AddViewModel.class);
        textNote = getView().findViewById(R.id.Text_Note);
        View backButton;

        //Back Button Logic
        backButton = getView().findViewById(R.id.Back_Button_Add);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        //Set Text From ViewModel
        textNote.setText(model.getNoteText());
    }

    @Override
    public void onStop() {
        super.onStop();

        //Set Text To Model When Fragment Lifecycle onStop
        model.setNoteText(textNote.getText().toString());
    }
}