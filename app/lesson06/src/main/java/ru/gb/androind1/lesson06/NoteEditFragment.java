package ru.gb.androind1.lesson06;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NoteEditFragment extends Fragment {

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
}