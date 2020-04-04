package com.example.hackathon_playgroupapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.hackathon_playgroupapp.ui.home.HomeFragment;
import com.example.hackathon_playgroupapp.ui.login.LoginFragment;
import com.example.hackathon_playgroupapp.ui.register.RegisterFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);
        fAuth = FirebaseAuth.getInstance();
    }

    public void Register(View view){
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.login_fragment, new RegisterFragment()).commit();

    }

    public void createAccount(View view){
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.register_password);
        EditText confirmPassword = findViewById(R.id.confirm_password);


        fAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Store the values in temporary fields



                    email.setError(null);
                    password.setError(null);
                    confirmPassword.setError(null);


                    boolean error2 = false;
                    View focusView2 = null;

                    if (TextUtils.isEmpty(email.getText().toString())) {
                        email.setError(getString(R.string.empty));
                        focusView2 = email;
                        error2 = true;

                    } else {
                        if (TextUtils.isEmpty(password.getText().toString())) {
                            password.setError(getString(R.string.empty));
                            focusView2 = password;
                            error2 = true;
                        } else {
                            if (TextUtils.isEmpty(confirmPassword.getText().toString())) {
                                confirmPassword.setError(getString(R.string.empty));
                                focusView2 = confirmPassword;
                                error2 = true;

                            }
                        }
                    }

                    if (error2) {
                        focusView2.requestFocus();
                    } else {
                        boolean result = isValidEmail((EditText) email);
                        boolean result2 = isValidPassword( password, confirmPassword);
                        if (result == true && result2 == true) {

                            FragmentTransaction transaction;
                            transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.register_fragment, new LoginFragment()).commit();
                        }
                    }

                }else{
                    //Si register retourne false





                }
            }
        });

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

            }

        }

        fAuth.signInWithEmailAndPassword(login.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FragmentTransaction transaction;
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.login_fragment, new HomeFragment()).commit();
                }else{
                    password.setError(getString(R.string.wrong));

                }
                }
        });

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
