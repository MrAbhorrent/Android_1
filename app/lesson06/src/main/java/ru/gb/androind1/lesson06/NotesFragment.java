package ru.gb.androind1.lesson06;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class NotesFragment extends Fragment {

    private List<Note> noteList = MainActivity.notesList;

    public NotesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intListView(view);
    }

    private void intListView(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        for (int i = 0; i < noteList.size(); i++) {
            TextView textView = new TextView(getContext());
            textView.setText(noteList.get(i).getTitle());
            textView.setTextSize(26);
            layoutView.addView(textView);
            final int posision = i;
            textView.setOnClickListener(v -> {
                showNote(posision);
            });
        }
    }

    private void showNote(int posision) {

        if (isLandscape()) {
            showLandNoteDescription(posision);
        } else {
            showPortNoteDescription(posision);
        }
    }

    private void showLandNoteDescription(int posision) {

        NoteContentFragment noteContentFragment = NoteContentFragment.newInstance(posision);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_description, noteContentFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void showPortNoteDescription(int posision) {

        NoteContentFragment noteContentFragment = NoteContentFragment.newInstance(posision);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container_notes, noteContentFragment)
                .addToBackStack("")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}