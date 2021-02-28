package com.example.pwpbsqlite3.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.pwpbsqlite3.R;
import com.example.pwpbsqlite3.model.NoteModel;
import com.example.pwpbsqlite3.util.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;

public class CreateNoteActivity extends AppCompatActivity {
    NoteModel note;
    TextInputEditText inputTitle, inputBody;
    TextInputLayout inputTitleLayout, inputBodyLayout;
    Button createNote;
    String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        initView();
    }

    void initView() {
        inputTitleLayout = findViewById(R.id.inputTitleLayout);
        inputBodyLayout = findViewById(R.id.inputBodyLayout);
        inputTitle = findViewById(R.id.inputTitle);
        inputBody = findViewById(R.id.inputBody);
        createNote = findViewById(R.id.createNote);


        mode = getIntent().getExtras().getString("mode");
        if (mode.equals("edit")) {
            setTitle(R.string.edit);
            note = getIntent().getExtras().getParcelable("note");
            inputTitle.setText(note.title);
            inputBody.setText(note.body);
            createNote.setText("Update");
        } else {
            setTitle(R.string.create);
        }

        createNote.setOnClickListener(v -> {
            if (validateInput()) {
                if (mode.equals("edit")) {
                    note.date = new Date().toString();
                    note.title = inputTitle.getText().toString();
                    note.body = inputBody.getText().toString();
                    Log.d("note", "update");
                    updateNode(note);
                } else {
                    NoteModel newNote = new NoteModel();
                    newNote.date = new Date().toString();
                    newNote.title = inputTitle.getText().toString();
                    newNote.body = inputBody.getText().toString();
                    storeNote(newNote);
                }

                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    void storeNote(NoteModel note) {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        db.insert(note);
    }

    void updateNode(NoteModel note) {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        db.update(note);
    }

    boolean validateInput() {
        boolean valid = true;
        if (validateInputTitle()) {
            inputTitleLayout.setError("Title can't be empty");
            valid = false;
        }
        if (validateInputBody()) {
            inputBodyLayout.setError("Body can't be empty");
            valid = false;
        }
        return valid;
    }

    boolean validateInputTitle() {
        return inputTitle.getText().toString().isEmpty();
    }

    boolean validateInputBody() {
        return inputBody.getText().toString().isEmpty();
    }
}