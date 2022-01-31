package ru.gb.androind1.lesson06;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    public static int selectedPosition = -1;
    public static final String CURRENT_POS = "CURRENT_POS";
    public static NoteSource source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbarAndDrawer();
        if (savedInstanceState == null) {
            initNotesList();
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
            openAboutFragment();
        } else if (item.getItemId() == R.id.search_note) {
            Snackbar.make(this.findViewById(R.id.activity_view), "Здесь будет поиск", Snackbar.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    // Иинициализируем тестовый список заметок
    private void initNotesList() {

        //source = new NoteSourceImplementation(this);
        source = new PreferencesNotesSource(getPreferences(MODE_PRIVATE));
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
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.action_drawer_about:
                    openAboutFragment();
                    drawer.closeDrawers();
                    return true;
                case R.id.action_drawer_exit:
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);
                    builder.setIcon(R.drawable.ic_baseline_west_24)
                            .setTitle(R.string.exit_From_App)
                            .setMessage("Вы действительно хотите выйти")
                            .setPositiveButton(getString(R.string.dialog_positiveButton), (dialog, which) -> finish())
                            .setNegativeButton(R.string.dialog_negativeButton, (dialog, which) -> dialog.cancel());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    return true;
            }
            return false;
        });
    }

    private void openAboutFragment() {
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
    }
}