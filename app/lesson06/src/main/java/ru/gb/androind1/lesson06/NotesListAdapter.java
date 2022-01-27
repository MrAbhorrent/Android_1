package ru.gb.androind1.lesson06;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NotesListViewHolder> {

    private List<Note> noteList;
    private OnNoteClickListener clickListener;

    public NotesListAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    public void setClickListener(OnNoteClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public NotesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View currView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_title, parent, false);
        return new NotesListViewHolder(currView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesListAdapter.NotesListViewHolder holder, int position) {

        holder.bind(noteList.get(position));
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    class NotesListViewHolder extends RecyclerView.ViewHolder {

        TextView textView = itemView.findViewById(R.id.title_note);
        public NotesListViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(Note note) {
            textView.setText(note.getTitle());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onNoteClick(v, getAdapterPosition());
                    }
                }
            });
        }
    }

    interface  OnNoteClickListener {
        void onNoteClick(View view, int position);
    }
}
