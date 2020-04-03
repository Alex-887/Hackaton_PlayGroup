package com.example.hackathon_playgroupapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.hackathon_playgroupapp.ui.login.LoginFragment;
import com.example.hackathon_playgroupapp.ui.register.RegisterFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);

    }

    public void Register(View view){
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login_fragment, new RegisterFragment()).commit();


    }


    public void ReturnLogin(View view){



        //Store the values in temporary fields
        EditText firstname = findViewById(R.id.first_name);
        EditText lastname = findViewById(R.id.last_name);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.register_password);
        EditText confirmPassword = findViewById(R.id.confirm_password);

        firstname.setError(null);
        lastname.setError(null);
        email.setError(null);
        password.setError(null);
        confirmPassword.setError(null);


        boolean error = false;
        View focusView = null;

        if(TextUtils.isEmpty(firstname.getText().toString())){
            firstname.setError(getString(R.string.empty));
            firstname.setText("");
            focusView = firstname;
            error = true;
        }else{
            if(TextUtils.isEmpty(lastname.getText().toString())){
                lastname.setError(getString(R.string.empty));
                lastname.setText("");
                focusView = lastname;
                error = true;
            }else{
                if(TextUtils.isEmpty(email.getText().toString())){
                    email.setError(getString(R.string.empty));
                    email.setText("");
                    focusView = email;
                    error = true;
                }else {
                    if(TextUtils.isEmpty(password.getText().toString())){
                        password.setError(getString(R.string.empty));
                        password.setText("");
                        focusView = password;
                        error = true;
                    } else {
                        if(TextUtils.isEmpty(confirmPassword.getText().toString())){
                            confirmPassword.setError(getString(R.string.empty));
                            confirmPassword.setText("");
                            focusView = confirmPassword;
                            error = true;
                        }
                    }
                }
            }
        }

        if(error){
            focusView.requestFocus();
        }else{
            boolean result = isValidEmail((EditText)findViewById(R.id.email));
            boolean result2 = isValidPassword((EditText)findViewById(R.id.register_password),(EditText)findViewById(R.id.confirm_password));
            if(result==true && result2==true ) {
                FragmentTransaction transaction;
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.register_fragment, new LoginFragment()).commit();
            }
        }



    }
    public final static boolean isValidEmail(EditText target1) {
        CharSequence target = target1.getText();
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    //This method checks if both password entered are the same
    public final static boolean isValidPassword(EditText target, EditText target2){
        String password = target.getText().toString();
        String password2 = target2.getText().toString();
        if(password.isEmpty() || password2.isEmpty())
            return false;
        if(password.equals(password2))
            return true;
        return false;
    }
}
