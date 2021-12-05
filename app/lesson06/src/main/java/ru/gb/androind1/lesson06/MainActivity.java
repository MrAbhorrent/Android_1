package ru.gb.androind1.lesson06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static int selectedPosition = -1;
    public static final String CURRENT_POS = "CURRENT_POS";
    public static List<Note> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNotesList();
        initToolbarAndDrawer();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_notes, new NotesFragment())
                    .commit();
        } else {
            selectedPosition = savedInstanceState.getInt(CURRENT_POS, -1);
            if (getSupportFragmentManager().getFragments().size() > 0) {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_note) {
            Snackbar.make(this.findViewById(R.id.activity_view), "Добавляем заметку", Snackbar.LENGTH_SHORT).show();
            if (isLandscape()) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("edit")
                        .replace(R.id.fragment_container_description, new NoteEditFragment())
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("edit")
                        .replace(R.id.fragment_container_notes, new NoteEditFragment())
                        .commit();
            }
        } else if (item.getItemId() == R.id.del_note) {
            String snackBar_text;
            // TODO: мы невозвращаем selectedPosition из NoteFragment. Надо это поправить.
            if (selectedPosition != -1) {
                snackBar_text = "Удаляем заметку - " + selectedPosition;
            } else {
                snackBar_text = "Заметка не выбрана";
            }
            Snackbar snackbar = Snackbar.make(this.findViewById(R.id.activity_view), snackBar_text, Snackbar.LENGTH_INDEFINITE);
            snackbar
                    .setAction("Ok", v -> snackbar.dismiss())
                    .show();
        } else if (item.getItemId() == R.id.menu_about) {
            Snackbar.make(this.findViewById(R.id.activity_view), "Показываем инфо о программе", Snackbar.LENGTH_SHORT).show();
            if (isLandscape()) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("about")
                        .replace(R.id.fragment_container_description, new AboutFragment())
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("about")
                        .replace(R.id.fragment_container_notes, new AboutFragment())
                        .commit();
            }
        } else if (item.getItemId() == R.id.search_note) {
            Snackbar.make(this.findViewById(R.id.activity_view), "Здесь будет поиск", Snackbar.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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
                new Note(new Date(System.currentTimeMillis()), "Заметка 7", "Содержимое заметки 7"),
                new Note(new Date(System.currentTimeMillis()), "Заметка 8", "Содержимое заметки 8"),
                new Note(new Date(System.currentTimeMillis()), "Заметка 9", "Содержимое заметки 9")
        );
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getFragments().size() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void initToolbarAndDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDrawer(toolbar);
    }

    private void initDrawer(Toolbar toolbar) {

        // Находим DrawerLayout
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        // Создаем ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // Обработка навигационного меню
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_drawer_about:
                        openAboutFragment();
                        drawer.closeDrawers();
                        return true;
                    case R.id.action_drawer_exit:
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

    private void openAboutFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("about")
                .replace(R.id.fragment_container_notes, new AboutFragment())
                .commit();
    }
}