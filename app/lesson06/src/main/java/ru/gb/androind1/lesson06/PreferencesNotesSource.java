package ru.gb.androind1.lesson06;

import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PreferencesNotesSource implements NoteSource{

    private String notesDataKey = "NOTES_DATA";
    private SharedPreferences sharedPreferences;
    private List<Note> notesList;

    public PreferencesNotesSource(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        fetch();
    }

    @Override
    public Note getNote(int position) {
        return notesList.get(position);
    }

    @Override
    public int size() {
        return notesList.size();
    }

    @Override
    public void addNote(Note note) {
        notesList.add(note);
        update();
    }

    @Override
    public void deleteNote(int position) {
        notesList.remove(position);
        update();
    }

    @Override
    public void updateNote(int position, Note note) {
        notesList.set(position, note);
        update();
    }

    private void fetch() {
        String json = sharedPreferences.getString(notesDataKey, null);
        if (json == null) {
            notesList = new ArrayList<>();
        } else {
            Type type = new TypeToken<ArrayList<Note>>() {}.getType();
            notesList = new GsonBuilder().create().fromJson(json, type);
        }
    }

    private void update() {
        String json = new GsonBuilder().create().toJson(notesList);
        sharedPreferences.edit()
                .putString(notesDataKey, json)
                .apply();
    }
}
