package com.abbas.samih.samihtaskmngr2018;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.abbas.samih.samihtaskmngr2018.data.MyTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {
    private EditText etTitle,etText,etDueDate;
    private SeekBar skbrImportant,skbrNecessary;
    private Button btnSave,btnDatePicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        etTitle=findViewById(R.id.etTitle);
        etText=findViewById(R.id.etTaskText);
        etDueDate=findViewById(R.id.etDueDate);
        skbrImportant=findViewById(R.id.skbrImportant);
        skbrNecessary=findViewById(R.id.skbrNeccesary);
        btnSave=findViewById(R.id.btnSaveTask);
        btnDatePicker=findViewById(R.id.btnDatePicker);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataHandler();
            }
        });
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void dataHandler() {
        boolean isok=true;
        String title=etTitle.getText().toString();
        String text=etText.getText().toString();
        String  date=etDueDate.getText().toString();
        int important=skbrImportant.getProgress();
        int necessary=skbrNecessary.getProgress();
        if(title.length()==0)
        {
            etTitle.setError("Title can not be empty");
            isok=false;

        }
        if(text.length()==0)
        {
            etText.setError("Text can not be empty");
            isok=false;

        }
        if(isok)
        {
            MyTask task=new MyTask();
            task.setCreatedAt(new Date());
            task.setDueDate(new Date(date));
            task.setText(text);
            task.setTitle(title);
            task.setImportant(important);
            task.setNecessary(necessary);

            //get user email to set is as the owner of this task
            FirebaseAuth auth = FirebaseAuth.getInstance();
            task.setOwner(auth.getCurrentUser().getEmail());
// to get the database root reference
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference();

           //to get uid(universal id)
            String key=reference.child("MyTasks").push().getKey();
            task.setKey(key);

            reference.child("MyTasks").child(key).setValue(task).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(AddTaskActivity.this, "Add Successful", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(AddTaskActivity.this, "Add Faild", Toast.LENGTH_LONG).show();

                    }
                }
            });




        }


    }
}
