package com.abbas.samih.samihtaskmngr2018;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    //1.add auth to project
    //2.

    FirebaseAuth auth;//to establish sign in sign up
    FirebaseUser user;//user

    private EditText etFirstName,etLastName,etPhone,etEmail2,etPassWord2;
    private Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etFirstName=(EditText)findViewById(R.id .edFirstName) ;
        etLastName=(EditText)findViewById(R.id .edLastName) ;
        etPhone=(EditText) findViewById(R.id .edPhone) ;
        etEmail2=(EditText) findViewById(R.id .edEmail2) ;
        etPassWord2=(EditText)findViewById(R.id .edPassWord2) ;
        btnSave=(Button)findViewById(R.id .btnSave) ;
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();//

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataHandler();
            }
        });



    }

    /**
     * get email and passwor from the field and try to create new user
     */
    private void dataHandler()
    {
        boolean isok=true;//if all the fields filled well
        String email=etEmail2.getText().toString();
        String passw1=etPassWord2.getText().toString();
        String fname=etFirstName.getText().toString();
        String lname=etLastName.getText().toString();
        String phone=etPhone.getText().toString();
        if(email.length()<4 ||
                email.indexOf('@')<0 ||
                email.indexOf('.')<0)
        {
            etEmail2.setError("Wrong Eamil");
            isok=false;
        }
        if(passw1.length()<8)
        {
            etPassWord2.setError("Have to be at least 8 char");
            isok=false;
        }
        if(isok)
        {
            creatAcount(email,passw1);
        }
    }

    //4.

    /**
     *create firebase user using email and password
     * @param email user eamil
     * @param passw user password
     */
    private void creatAcount(String email, String passw) {
        auth.createUserWithEmailAndPassword(email,passw)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(SignUpActivity.this, "Authentication Successful.", Toast.LENGTH_SHORT).show();
                    //updateUserProfile(task.getResult().getUser());
                    finish();
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Authentication failed."+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    task.getException().printStackTrace();
                }
            }
        });
    }

}
