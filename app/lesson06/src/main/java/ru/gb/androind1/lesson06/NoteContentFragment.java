package ru.gb.androind1.lesson06;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;
import java.util.List;

public class NoteContentFragment extends Fragment {

    static final String ARG_INDEX = "index";
    private List<Note> noteList = MainActivity.notesList;

    TextView tvTileNote, tvDescriptionNote, tvCreationDate;

    public static NoteContentFragment newInstance(int index) {

        NoteContentFragment fragment = new NoteContentFragment();
        Bundle args = new Bundle();
        args.putInt( ARG_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_content, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int index = 0;
        int mYear, mMonth, mDay;

        Bundle arguments = getArguments();
        if (arguments != null) {
            index = arguments.getInt(ARG_INDEX);
        }
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        tvTileNote = view.findViewById(R.id.note_title);
        tvDescriptionNote = view.findViewById(R.id.note_description);
        tvCreationDate = view.findViewById(R.id.note_createDate);
        tvTileNote.setText(noteList.get(index).getTitle());
        tvDescriptionNote.setText(noteList.get(index).getContent());
        tvCreationDate.setText(noteList.get(index).getCreateDate().toString());
        tvCreationDate.setOnClickListener(v -> {
                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view1, int year, int month, int dayOfMonth) {
                        String editDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        tvCreationDate.setText(editDate);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
        });
    }
}