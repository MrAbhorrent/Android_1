package ru.gb.androind1.lesson02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, Constants {

    private SwitchMaterial aSwitch;
    private Boolean isDark = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        aSwitch = findViewById(R.id.switch1);
        if (aSwitch != null) {
            aSwitch.setOnCheckedChangeListener(this);
        }

        // Добавляем кнопку назад в ActionBar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Intent intentResult = new Intent();
            intentResult.putExtra(selectedTheme, isDark);
            setResult(RESULT_OK, intentResult);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveSetting() {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isDark = isChecked;
    }
}