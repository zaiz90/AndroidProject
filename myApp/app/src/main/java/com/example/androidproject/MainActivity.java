package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button signBtn,logBtn,logOutBtn,conBtn;
    TextView emailView,passView,messageView;
    String email,pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        signBtn = findViewById(R.id.signupBtn);
        logBtn = findViewById(R.id.logInBtn);
        emailView = findViewById(R.id.emailView);
        passView = findViewById(R.id.passwordView);
        messageView = findViewById(R.id.messageView);
        logOutBtn = findViewById(R.id.logOutBtn);
        conBtn = findViewById(R.id.conBtn);

        logOutBtn.setVisibility(View.INVISIBLE);
        conBtn.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void signupFunc(View v)
    {
        email = emailView.getText().toString();
        pass = passView.getText().toString();

        try {
            if(email.isEmpty()){
                emailView.setError("Please enter an email address");
                emailView.requestFocus();
            }
            else if(!email.contains("@")||(!email.contains("."))||(email.length()<6))
            {
                emailView.setError("Email is badly formatted");
                emailView.requestFocus();
            }
            else  if(pass.isEmpty()){
                passView.setError("Please enter a password");
                passView.requestFocus();
            }
            else if(pass.length()<6)
            {
                passView.setError("The length of the password must be at least 6 characters");
                passView.requestFocus();
            }
            else {
                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("createUserWithEmail:", "success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            logOutBtn.setVisibility(View.VISIBLE);
                            conBtn.setVisibility(View.VISIBLE);
                            logBtn.setVisibility(View.INVISIBLE);
                            signBtn.setVisibility(View.INVISIBLE);
                            emailView.setEnabled(false);
                            passView.setEnabled(false);
                            messageView.setText("Logged in as "+user.getEmail());
                            messageView.setTextColor(Color.parseColor("#FF0A1563"));
                            messageView.setVisibility(View.VISIBLE);
                            try {
                                Intent i = new Intent(MainActivity.this, ArticleListActivity.class);
                                startActivity(i);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("createUserWithEmail:", "failure");
                            Log.i("Exception:", task.getException().toString());
                            messageView.setText(task.getException().getMessage());
                            messageView.setTextColor(Color.parseColor("#FFD5250A"));
                        }

                    }
                });
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void logInFunc(View v)
    {
        email = emailView.getText().toString();
        pass = passView.getText().toString();
        try {
            if(email.isEmpty()){
                emailView.setError("Please enter an email address");
                emailView.requestFocus();
            }
            else if(!email.contains("@")||(!email.contains("."))||(email.length()<6))
            {
                emailView.setError("Email is badly formatted");
                emailView.requestFocus();
            }
            else  if(pass.isEmpty()){
                passView.setError("Please enter a password");
                passView.requestFocus();
            }
            else if(pass.length()<6)
            {
                passView.setError("The length of the password must be at least 6 characters");
                passView.requestFocus();
            }
            else {
                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.i("signInWithEmail:", "success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    logOutBtn.setVisibility(View.VISIBLE);
                                    conBtn.setVisibility(View.VISIBLE);
                                    logBtn.setVisibility(View.INVISIBLE);
                                    signBtn.setVisibility(View.INVISIBLE);
                                    emailView.setEnabled(false);
                                    passView.setEnabled(false);
                                    messageView.setText("Logged in as "+user.getEmail());
                                    messageView.setTextColor(Color.parseColor("#FF0A1563"));
                                    messageView.setVisibility(View.VISIBLE);
                                    try {
                                        Intent i = new Intent(MainActivity.this, ArticleListActivity.class);
                                        startActivity(i);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.i("signInWithEmail:", "failure");
                                    Log.i("Exception:", task.getException().toString());
                                    messageView.setText(task.getException().getMessage());
                                    messageView.setTextColor(Color.parseColor("#FFD5250A"));
                                }
                            }
                        });
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void logOutFunc(View v)
    {
        FirebaseAuth.getInstance().signOut();
        logOutBtn.setVisibility(View.INVISIBLE);
        conBtn.setVisibility(View.INVISIBLE);
        logBtn.setVisibility(View.VISIBLE);
        signBtn.setVisibility(View.VISIBLE);
        messageView.setText("Log in or sign up to continue");
        messageView.setTextColor(Color.parseColor("#FF0A1563"));
        emailView.setText("");
        passView.setText("");
        emailView.setEnabled(true);
        passView.setEnabled(true);
    }

    public void conFunc(View v)
    {
        try {
            Intent i = new Intent(MainActivity.this, ArticleListActivity.class);
            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

