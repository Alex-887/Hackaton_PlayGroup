package com.example.hackathon_playgroupapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.hackathon_playgroupapp.ui.home.HomeFragment;
import com.example.hackathon_playgroupapp.ui.login.LoginFragment;
import com.example.hackathon_playgroupapp.ui.register.RegisterFragment;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

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

    public void Login(View view) {

        EditText login = findViewById(R.id.login);
        EditText password = findViewById(R.id.password);

        boolean error = false;
        View focusView = null;

        if (TextUtils.isEmpty(login.getText().toString())) {
            login.setError(getString(R.string.empty));
            focusView = login;
            error = true;
        } else {
            if (TextUtils.isEmpty(password.getText().toString())) {
                password.setError(getString(R.string.empty));
                focusView = password;
                error = true;
            }

            if (error) {
                focusView.requestFocus();
            }else{
                FragmentTransaction transaction;
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.login_fragment, new HomeFragment()).commit();
            }


        }



        /*Sending credentials to server through Java Socket


        Socket mySocket = null;
        InetAddress serverAdress;

        String serverName = "jobi.ddns.net";

        try {
            serverAdress = InetAddress.getByName(serverName);

            mySocket = new Socket(serverAdress,9876);

            //Send the credentials to the server

            OutputStream os = mySocket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(os);

            //Send the mail
            String email = login.getText().toString();
            dataOutputStream.writeUTF(email);
            //Send the password
            String passwordLogin = login.getText().toString();
            dataOutputStream.writeUTF(passwordLogin);

            //Actually send the information
            dataOutputStream.flush();
            //Close the flux
            dataOutputStream.close();
            //Close the socket
            mySocket.close();




        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

         */


    }


    public void ReturnLogin(View view){


            //Store the values in temporary fields
            EditText firstname = findViewById(R.id.first_name);
            EditText lastname = findViewById(R.id.last_name);
            EditText email = findViewById(R.id.email);
            EditText password2 = findViewById(R.id.password);
            EditText confirmPassword = findViewById(R.id.confirm_password);

            firstname.setError(null);
            lastname.setError(null);
            email.setError(null);
            password2.setError(null);
            confirmPassword.setError(null);


            boolean error2 = false;
            View focusView2 = null;

            if (TextUtils.isEmpty(firstname.getText().toString())) {
                firstname.setError(getString(R.string.empty));
                focusView2 = firstname;
                error2 = true;
            } else {
                if (TextUtils.isEmpty(lastname.getText().toString())) {
                    lastname.setError(getString(R.string.empty));
                    focusView2 = lastname;
                    error2 = true;
                } else {
                    if (TextUtils.isEmpty(email.getText().toString())) {
                        email.setError(getString(R.string.empty));
                        focusView2 = email;
                        error2 = true;

                    } else {
                        if (TextUtils.isEmpty(password2.getText().toString())) {
                            password2.setError(getString(R.string.empty));
                            focusView2 = password2;
                            error2 = true;
                        } else {
                            if (TextUtils.isEmpty(confirmPassword.getText().toString())) {
                                confirmPassword.setError(getString(R.string.empty));
                                focusView2 = confirmPassword;
                                error2 = true;
                            }
                        }
                    }
                }
            }

            if (error2) {
                focusView2.requestFocus();
            } else {
                boolean result = isValidEmail((EditText) findViewById(R.id.email));
                boolean result2 = isValidPassword((EditText) findViewById(R.id.register_password), (EditText) findViewById(R.id.confirm_password));
                if (result == true && result2 == true) {
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
