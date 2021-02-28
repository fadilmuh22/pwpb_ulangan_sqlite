package com.example.pwpbsqlite3.model;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pwpbsqlite3.R;
import com.example.pwpbsqlite3.util.DatabaseHelper;
import com.example.pwpbsqlite3.util.NoteOnClickListener;
import com.example.pwpbsqlite3.view.CreateNoteActivity;

import java.util.ArrayList;

public class NoteAdapter  extends
        RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    public Context context;
    public ArrayList<NoteModel> notelist;

    public NoteAdapter(Context context, ArrayList<NoteModel> notelist) {
        this.context = context;
        this.notelist = notelist;
    }

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {

        holder.date.setText(notelist.get(position).date);
        holder.title.setText(notelist.get(position).title);
        holder.body.setText(notelist.get(position).body);

        holder.itemView.setOnClickListener(v -> {
            AlertDialog alert = new AlertDialog.Builder(context)
                .setTitle(R.string.choose_option)
                .setItems(R.array.note_action_item, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                onClickUpdate(notelist.get(position));
                                break;
                            case 1:
                                DatabaseHelper db = new DatabaseHelper(context.getApplicationContext());
                                db.delete(notelist.get(position).id);
                                notelist.remove(position);
                                notifyDataSetChanged();
                                break;
                        }
                    }
                }).create();
            alert.show();
        });
    }

    @Override
    public int getItemCount() {
        return notelist.size();
    }

    void onClickUpdate(NoteModel note) {
        Intent i = new Intent(context, CreateNoteActivity.class);
        i.putExtra("note", note);
        i.putExtra("mode", "edit");
        context.startActivity(i);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView date, title, body;
        public NoteViewHolder(@NonNull View v) {
            super(v);
            date = v.findViewById(R.id.date);
            title = v.findViewById(R.id.title);
            body = v.findViewById(R.id.body);
        }
    }
}
