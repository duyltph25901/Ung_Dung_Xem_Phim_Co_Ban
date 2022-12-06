package com.example.appmovie.login_signup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmovie.R;
import com.example.appmovie.db.db_user.UserDatabase;
import com.example.appmovie.model.User;
import com.example.appmovie.view.view_ad.activities.ActorsActivity;
import com.example.appmovie.view.view_ad.activities.HomeAdActivity;
import com.example.appmovie.view.view_viewer.activity.HomeViewerActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText inputEmail, inputPass;
    private CheckBox chkRememberUser;
    private Button btnSignIn;
    private TextView txtForgotPassword, txtSignUp;

    private String mEmail, mPassword;

    private static final String FILE_NAME_SHARED = "fileUserLoginApp.txt";
    private static final String KEY_EMAIL = "keyEmail";
    private static final String KEY_PASSWORD = "keyPassword";
    private static final String KEY_STATUS_CHECK_BOX = "keyStatusCheckBox";
    private static final boolean DEF_VALUE_IS_CHECKED = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setEvent();

        if (flagCheck() > 0) {
            inputEmail.setText(mEmail);
            inputPass.setText(mPassword);
        } else {
            inputEmail.setText("");
            inputPass.setText("");
        }
    }

    private void init() {
        inputEmail = findViewById(R.id.inputEmailLogin);
        inputPass = findViewById(R.id.inputPassLogin);
        chkRememberUser =  findViewById(R.id.checkBoxRememberUser);
        btnSignIn = findViewById(R.id.btnLogin);
        txtForgotPassword = findViewById(R.id.txtForgotPass);
        txtSignUp = findViewById(R.id.txtSignUp);
    }

    private void setEvent() {
        btnSignIn.setOnClickListener(v -> signIn());
        txtForgotPassword.setOnClickListener(v -> forGotPassword());
        txtSignUp.setOnClickListener(v -> signUp());
    }

    private void signIn() {
        String email = inputEmail.getText().toString().trim();
        String pass = inputPass.getText().toString().trim();
        boolean isNullInput = isNullInput(email, pass);
        boolean isEmail = isEmail(email);
        User user = UserDatabase.getInstance(LoginActivity.this).userDAO().searchUserByEmail(email);
        if (isNullInput) {
            Toast.makeText(this, "Please complete all information!", Toast.LENGTH_SHORT).show();

            return;
        } if (!isEmail) {
            Toast.makeText(this, "Email invalidate!", Toast.LENGTH_SHORT).show();

            return;
        } if (user ==  null) {
            Toast.makeText(this, "Account not found!", Toast.LENGTH_SHORT).show();

            return;
        } boolean isNotMatch = pass.equalsIgnoreCase(user.getPass());
        if (!isNotMatch) {
            Toast.makeText(this, "Incorrect password!", Toast.LENGTH_SHORT).show();

            return;
        }


        // check ad account
        boolean isAd = isAd(user);
        if (isAd) {
            loginAd();
        } else {
            loginViewer();
        }

        // check remember account
        rememberAccount(email, pass, chkRememberUser.isChecked());
    }

    private void forGotPassword(){
        final Dialog dialogForgotPassInput = new Dialog(LoginActivity.this);
        dialogForgotPassInput.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogForgotPassInput.setContentView(R.layout.dialog_forgot_pass_input);
        Window window = dialogForgotPassInput.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        dialogForgotPassInput.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        
        // init
        EditText edtEmail = dialogForgotPassInput.findViewById(R.id.edtEmailForgot);
        Button btnDone = dialogForgotPassInput.findViewById(R.id.btnDoneForgot);
        
        btnDone.setOnClickListener(v -> doneDialogForgotPass(edtEmail, dialogForgotPassInput));
        
        dialogForgotPassInput.show();
    }

    private void signUp(){
        Intent i = new Intent(getBaseContext(), SignUpActivity.class);
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

    private void loginAd() {
        startActivity(new Intent(getBaseContext(), HomeAdActivity.class));
    }

    private void loginViewer() {
        startActivity(new Intent(getBaseContext(), HomeViewerActivity.class));
    }

    private boolean isAd(User user) {
        String adEmail = user.getEmail();
        String adPass = user.getPass();

        return adEmail.equalsIgnoreCase("ad@gmail.com") && adPass.equalsIgnoreCase("123456");
    }

    private int flagCheck(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(FILE_NAME_SHARED, MODE_PRIVATE);
        boolean isChecked = sharedPreferences.getBoolean(KEY_STATUS_CHECK_BOX, DEF_VALUE_IS_CHECKED);

        if(isChecked){
            mEmail = sharedPreferences.getString(KEY_EMAIL, "");
            mPassword    = sharedPreferences.getString(KEY_PASSWORD, "");

            return 1;
        }

        return -1;
    }

    private void rememberAccount(String email, String pass, boolean statusCheckBox){
        SharedPreferences sharedPreferences = this.getSharedPreferences(FILE_NAME_SHARED, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(!statusCheckBox) {
            editor.clear();
        } else {
            editor.putString(KEY_EMAIL, email);
            editor.putString(KEY_PASSWORD, pass);
            editor.putBoolean(KEY_STATUS_CHECK_BOX, statusCheckBox);
        }

        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();

        if(flagCheck() > 0){
            inputEmail.setText(mEmail);
            inputPass.setText(mPassword);
        } else {
            inputEmail.setText("");
            inputPass.setText("");
        }
    }
    
    private void doneDialogForgotPass(EditText edt, Dialog dialog) {
        String email = edt.getText().toString().trim();
        
        if (email.isEmpty()) {
            dialog.cancel();
            return;
        }
        
        boolean isEmail = isEmail(email);
        if (!isEmail) {
            Toast.makeText(this, "Email invalidate!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        User user = UserDatabase.getInstance(this).userDAO().searchUserByEmail(email);
        if (user == null) {
            Toast.makeText(this, "Account not found!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        String pass = user.getPass();
        dialog.cancel();
        
        showDialogPass(pass);
    }
    
    private void showDialogPass(String pass) {
        final Dialog dialogShowPassword = new Dialog(LoginActivity.this);
        dialogShowPassword.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogShowPassword.setContentView(R.layout.dialog_show);
        Window window = dialogShowPassword.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        dialogShowPassword.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // init
        TextView txtDisplayPass = dialogShowPassword.findViewById(R.id.txtPassDisplay);
        Button btnOk = dialogShowPassword.findViewById(R.id.btnOk);

        txtDisplayPass.setText(pass);
        btnOk.setOnClickListener(v -> dialogShowPassword.cancel());
        
        dialogShowPassword.show();
    }
}