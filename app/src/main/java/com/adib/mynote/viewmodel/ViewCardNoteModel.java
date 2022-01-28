package com.adib.mynote.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewCardNoteModel extends ViewModel {
    private MutableLiveData<String> noteTitle;
    private MutableLiveData<String> noteText;

    public MutableLiveData<String> getTitle() {
        if (noteTitle == null) {
            noteTitle = new MutableLiveData<String>();
            noteTitle.setValue("Title");
        }
        return noteTitle;
    }
    public MutableLiveData<String> getText() {
        if (noteText == null) {
            noteText = new MutableLiveData<String>();
            noteText.setValue("Text");
        }
        return noteText;
    }
}
