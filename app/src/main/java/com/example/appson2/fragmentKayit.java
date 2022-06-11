package com.example.appson2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class fragmentKayit extends Fragment {

    private Button myButton;
    private String username,password,passwordagain;
    private EditText userName,userPass,userPassAgain;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_kayit, container, false);
        myButton = (Button) view.findViewById(R.id.kayitButton);
        userName = (EditText) view.findViewById(R.id.registerUserName) ;
        userPass = (EditText) view.findViewById(R.id.registerPassword);
        userPassAgain = (EditText) view.findViewById(R.id.passwordAgain);


        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = userName.getText().toString();
                password = userPass.getText().toString();
                passwordagain = userPassAgain.getText().toString();

                if(!password.equals(passwordagain)){
                    userPass.setError("Şifeler uyuşmuyor.");
                    userPassAgain.setError("Şifreler uyuşmuyor");
                }else{
                    DatabaseHelper db = new DatabaseHelper(getActivity());
                    db.addUser(username,password);
                    Snackbar.make(view, "Kayıt edildi. Giriş yapabilirsiniz.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        return view;

    }
}