package ru.gb.androind1.lesson06;

public interface NoteSource {

    Note getNote(int position);
    int size();
    void addNote(Note note);
    void deleteNote(int position);
    void updateNote(int position, Note note);
}
