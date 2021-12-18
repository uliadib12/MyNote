package com.adib.mynote.viewmodel;

import androidx.lifecycle.ViewModel;

public class AddViewModel extends ViewModel {
    private String noteTitle = "";
    private String noteText = "";

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }
}
