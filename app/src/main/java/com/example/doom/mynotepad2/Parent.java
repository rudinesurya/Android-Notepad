package com.example.doom.mynotepad2;

import com.example.doom.mynotepad2.Model.Note;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Doom on 9/17/2017.
 */

public class Parent extends RealmObject
{
    @SuppressWarnings("unused")
    private RealmList<Note> noteList;
    
    public RealmList<Note> getNoteList() {
        return noteList;
    }
}