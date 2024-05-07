package com.example.taskmanagerapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditTask extends AppCompatActivity {
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        db = new DatabaseHelper(this); // Initialize DatabaseHelper

        Button backButton = findViewById(R.id.button6);
        Button insert = findViewById(R.id.buttoninsert);
        Button update = findViewById(R.id.buttonupdate);
        Button delete = findViewById(R.id.buttondelete);
        Button view = findViewById(R.id.buttonview);
        EditText Title = findViewById(R.id.editTextTitle);
        EditText Description = findViewById(R.id.editTextDescription);
        EditText DueDate = findViewById(R.id.editTextDueDate);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditTask.this, MainActivity.class));
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = Title.getText().toString();
                String desc = Description.getText().toString();
                String date = DueDate.getText().toString();

                Boolean checkinsertdata = db.insertuserdata(title, desc, date);
                if(checkinsertdata==true)
                    Toast.makeText(EditTask.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(EditTask.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
            }        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = Title.getText().toString();
                String desc = Description.getText().toString();
                String date = DueDate.getText().toString();

                Boolean checkupdatedata = db.updateuserdata(title, desc, date);
                if(checkupdatedata==true)
                    Toast.makeText(EditTask.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(EditTask.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();
            }        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = Title.getText().toString();
                Boolean checkudeletedata = db.deletedata(title);
                if(checkudeletedata==true)
                    Toast.makeText(EditTask.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(EditTask.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();
            }        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.getdata();
                if(res.getCount()==0){
                    Toast.makeText(EditTask.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                int count=1;
                while(res.moveToNext()){
                    buffer.append(count++).append(".").append(res.getString(0)).append("\n");
                    buffer.append("Description :"+res.getString(1)+"\n");
                    buffer.append("DueDate :"+res.getString(2)+"\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(EditTask.this);
                builder.setCancelable(true);
                builder.setTitle("Task Details");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });
    }
}

