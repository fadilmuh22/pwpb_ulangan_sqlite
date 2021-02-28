package com.example.pwpbsqlite3.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pwpbsqlite3.R;
import com.example.pwpbsqlite3.model.NoteAdapter;
import com.example.pwpbsqlite3.model.NoteModel;
import com.example.pwpbsqlite3.util.DatabaseHelper;
import com.example.pwpbsqlite3.util.NoteOnClickListener;
import com.example.pwpbsqlite3.view.CreateNoteActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvNotes;
    NoteAdapter adapter;
    ArrayList<NoteModel> notes;

    FloatingActionButton fabCreateNote;

    SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    void initView() {
        pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                DatabaseHelper db = new DatabaseHelper(MainActivity.this.getApplicationContext());
                notes = db.selectAll();
                adapter.notelist = notes;
                adapter.notifyDataSetChanged();
                pullToRefresh.setRefreshing(false);
            }
        });

        rvNotes = findViewById(R.id.rvNotes);
        initNoteList();

        fabCreateNote = findViewById(R.id.fabCreateNote);
        fabCreateNote.setOnClickListener(v -> {
            Intent i = new Intent(this, CreateNoteActivity.class);
            i.putExtra("mode", "create");
            startActivity(i);
        });
    }

    void initNoteList() {
        DatabaseHelper db = new DatabaseHelper(this.getApplicationContext());
        notes = db.selectAll();
        adapter = new NoteAdapter(this, notes);

        rvNotes.setAdapter(adapter);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }
}