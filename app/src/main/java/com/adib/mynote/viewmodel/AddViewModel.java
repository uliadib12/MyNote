package com.adib.mynote.viewmodel;

import androidx.lifecycle.ViewModel;

public class AddViewModel extends ViewModel {
    private String noteText = "";

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }
}
