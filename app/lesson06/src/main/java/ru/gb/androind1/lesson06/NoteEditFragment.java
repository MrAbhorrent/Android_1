package ru.gb.androind1.lesson06;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class NoteEditFragment extends Fragment {

    Button btnSave;
    private NoteSource source = MainActivity.source;
    private NotesListAdapter adapter = NotesFragment.adapter;

    public NoteEditFragment() {
        // Required empty public constructor
    }

    public static NoteEditFragment newInstance(String param1, String param2) {
        NoteEditFragment fragment = new NoteEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_content_ed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText titleNote = view.findViewById(R.id.note_title_edit);
        EditText descrNote = view.findViewById(R.id.note_description_edit);

        btnSave = view.findViewById(R.id.btnSaveNote);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note newNote = new Note(new Date(System.currentTimeMillis()), titleNote.getText().toString(), descrNote.getText().toString());
                source.addNote(newNote);
                adapter.notifyItemInserted(source.size() - 1);
                MainActivity.source = source;
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}