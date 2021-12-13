package ru.gb.androind1.lesson06;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class NotesFragment extends Fragment {

    public static final String CURRENT_POS = "CURRENT_POS";
    private int currentPosition = -1;
    private NoteSource noteList = MainActivity.source;
    public static NotesListAdapter adapter;

    public NotesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(CURRENT_POS, -1);
        }
        intListView(view);
        updateBackgroundNotes();
    }


    private void intListView(View view) {

/*        for (int i = 0; i < noteList.size(); i++) {
            TextView textView = new TextView(getContext());
            textView.setText(noteList.get(i).getTitle());
            textView.setTextSize(26);
            layoutView.addView(textView);
            final int position = i;
            textView.setOnClickListener(v -> {
                currentPosition = position;
                showNote(position);
                updateBackgroundNotes();
            });
        } */

        RecyclerView recyclerView = view.findViewById(R.id.rv_notes_list);
        adapter = new NotesListAdapter(noteList);
        adapter.setClickListener(new NotesListAdapter.OnNoteClickListener() {
            @Override
            public void onNoteClick(View view, int position) {
                showNote(position);
                updateBackgroundNotes();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        ;
        if (currentPosition != -1) {
            showNote(currentPosition);
        }
    }

    private void updateBackgroundNotes() {
        FrameLayout frameLayout = getView().findViewById(R.id.note_list);
        int colorBackgroud;
        for (int i = 0; i < frameLayout.getChildCount(); i++) {
            if (i == currentPosition) {
                colorBackgroud = Color.GRAY;
            } else {
                colorBackgroud = Color.TRANSPARENT;
            }
            frameLayout.getChildAt(i).setBackgroundColor(colorBackgroud);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_POS, currentPosition);
    }
}