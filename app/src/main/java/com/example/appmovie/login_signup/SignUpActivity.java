package com.example.appmovie.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmovie.R;
import com.example.appmovie.db.db_user.UserDatabase;
import com.example.appmovie.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText inputEmail, inputPass;
    private Button btnSignUp;
    private ImageView imgFB, imgGG;
    private TextView txtSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        init();
        setEvent();
    }
    
    private void init() {
        inputEmail = findViewById(R.id.inputEmailSignUp);
        inputPass = findViewById(R.id.inputPassSignUp);
        btnSignUp = findViewById(R.id.btnSignUp);
        imgFB = findViewById(R.id.imgFB);
        imgGG = findViewById(R.id.imgGG);
        txtSignIn = findViewById(R.id.txtSignIn);
    }
    
    private void setEvent() {
        btnSignUp.setOnClickListener(v -> signUp());
        txtSignIn.setOnClickListener(v -> signIn());
    }
    
    private void signUp() {
        String email = inputEmail.getText().toString().trim();
        String pass = inputPass.getText().toString().trim();
        
        boolean isNullInput = isNullInput(email, pass);
        boolean isEmail = isEmail(email);
        boolean isStrongPass = isStrongPass(pass);
        User user = UserDatabase.getInstance(this).userDAO().searchUserByEmail(email);
        boolean isUserAlreadyExit = (user != null) ? true : false;
        if (isNullInput) {
            Toast.makeText(this, "Please complete all information!", Toast.LENGTH_SHORT).show();

            return;
        } if (!isEmail) {
            Toast.makeText(this, "Email invalidate!", Toast.LENGTH_SHORT).show();

            return;
        } if (isUserAlreadyExit) {
            Toast.makeText(this, "Email already registered!", Toast.LENGTH_SHORT).show();

            return;
        } if (!isStrongPass) {
            Toast.makeText(this, "Weak password!", Toast.LENGTH_SHORT).show();

            return;
        } 

        insertUser(email, pass);
        displayKeyBoard();
        clearForm();
    }
    
    private void signIn() {
        Intent i = new Intent(getBaseContext(), LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
    
    private boolean isNullInput(String... input) {
        for (int i=0; i<input.length; i++) {
            if (input[i].isEmpty()) return true;
        }
        
        return false;
    }

    private boolean isEmail(String input){
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(input);

        return matcher.matches();
    }

    private boolean isStrongPass(String input) {
        return input.length() > 3;
    }

    private void insertUser(String... input) {
        String email = input[0];
        String pass = input[1];

        User user = new User(email, pass);
        UserDatabase.getInstance(SignUpActivity.this).userDAO().insert(user);

        Toast.makeText(this, "Insertion successfully!", Toast.LENGTH_SHORT).show();
    }

    private void displayKeyBoard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        inputEmail.setText("");
        inputPass.setText("");
    }
}