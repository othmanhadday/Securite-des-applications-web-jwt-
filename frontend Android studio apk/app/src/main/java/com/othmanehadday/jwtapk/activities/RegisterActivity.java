package com.othmanehadday.jwtapk.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.othmanehadday.jwtapk.R;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUsername,editTextPassword,editTextRepassword;
    Button buttonRegister;
    TextView textViewGoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername=findViewById(R.id.editTextUsernameRegister);
        editTextPassword=findViewById(R.id.editTextPasswordRegister);
        editTextRepassword=findViewById(R.id.editTextRepasswordRegister);
        buttonRegister=findViewById(R.id.buttonRegister);
        textViewGoLogin=findViewById(R.id.textViewGoToLogin);

        buttonRegister.setOnClickListener((event)->{

        });


        textViewGoLogin.setOnClickListener((event)->{
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        });

    }
}
