package ru.gb.androind1.lesson06;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class NoteSourceImplementation implements NoteSource {

    private List<Note> notelist;

    public NoteSourceImplementation(@NonNull Context context) {
        this.notelist = new ArrayList<>(Arrays.asList(
                new Note(new Date(System.currentTimeMillis()), "Заметка 1", "Содержимое заметки 1", "Простой тип", false),
                new Note(new Date(System.currentTimeMillis()), "Заметка 2", "Содержимое заметки 2", "Простой тип", false),
                new Note(new Date(System.currentTimeMillis()), "Заметка 3", "Содержимое заметки 3", "Простой тип", false),
                new Note(new Date(System.currentTimeMillis()), "Заметка 4", "Содержимое заметки 4", "Простой тип", false),
                new Note(new Date(System.currentTimeMillis()), "Заметка 5", "Содержимое заметки 5", "Простой тип", false),
                new Note(new Date(System.currentTimeMillis()), "Заметка 6", "Содержимое заметки 6", "Простой тип", false),
                new Note(new Date(System.currentTimeMillis()), "Заметка 7", "Содержимое заметки 7"),
                new Note(new Date(System.currentTimeMillis()), "Заметка 8", "Содержимое заметки 8"),
                new Note(new Date(System.currentTimeMillis()), "Заметка 9", "Содержимое заметки 9")
        ));
    }


    @Override
    public Note getNote(int position) {
        return notelist.get(position);
    }

    @Override
    public int size() {
        return notelist.size();
    }

    @Override
    public void addNote(Note note) {
        notelist.add(note);
    }

    @Override
    public void deleteNote(int position) {
        notelist.remove(position);
    }

    @Override
    public void updateNote(int position, Note note) {
        notelist.set(position, note);
    }
}
