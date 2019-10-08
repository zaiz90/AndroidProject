package com.example.androidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    Button signBtn,logBtn;
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

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("createUserWithEmail:","success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            messageView.setText(user.getEmail());
                            Intent i = new Intent(MainActivity.this,ArticleListActivity.class);
                            startActivity(i);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("createUserWithEmail:", "failure");
                            Log.i("Exception:",task.getException().toString());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void logInFunc(View v)
    {
        email = emailView.getText().toString();
        pass = passView.getText().toString();
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("signInWithEmail:","success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            messageView.setText(user.getEmail());
                            try {
                                Intent i = new Intent(MainActivity.this,ArticleListActivity.class);
                                startActivity(i);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("signInWithEmail:","failure");
                            Log.i("Exception:",task.getException().toString());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void logOutFunc(View v)
    {
        FirebaseAuth.getInstance().signOut();
        messageView.setText("no user");
    }

}

