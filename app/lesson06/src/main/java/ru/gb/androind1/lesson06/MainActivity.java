package ru.gb.androind1.lesson06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Note> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNotesList();
        NotesFragment notesFragment = new NotesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_notes, notesFragment)
                .commit();

        if (isLandscape()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_description, NoteContentFragment.newInstance(0))
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_notes, new NotesFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }



    // Иинициализируем тестовый список заметок
    private void initNotesList() {

        notesList = Arrays.asList(
                new Note(new Date(System.currentTimeMillis()), "Заметка 1", "Содержимое заметки 1", "Простой тип", false),
                new Note(new Date(System.currentTimeMillis()), "Заметка 2", "Содержимое заметки 2", "Простой тип", false),
                new Note(new Date(System.currentTimeMillis()), "Заметка 3", "Содержимое заметки 3", "Простой тип", false),
                new Note(new Date(System.currentTimeMillis()), "Заметка 4", "Содержимое заметки 4", "Простой тип", false),
                new Note(new Date(System.currentTimeMillis()), "Заметка 5", "Содержимое заметки 5", "Простой тип", false),
                new Note(new Date(System.currentTimeMillis()), "Заметка 6", "Содержимое заметки 6", "Простой тип", false),
                new Note(new Date(System.currentTimeMillis()), "Заметка 7", "Содержимое заметки 7", "Простой тип", false),
                new Note(new Date(System.currentTimeMillis()), "Заметка 8", "Содержимое заметки 8", "Простой тип", false),
                new Note(new Date(System.currentTimeMillis()), "Заметка 9", "Содержимое заметки 9", "Простой тип", false)
        );
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}