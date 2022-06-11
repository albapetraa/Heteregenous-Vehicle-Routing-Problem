package com.example.appson2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appson2.algorithm.Node;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class fragmentGiris extends Fragment {

    private Button myButton;
    private String username,password;
    private EditText userName,userPass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_giris, container, false);
        myButton = (Button) view.findViewById(R.id.girisButton);
        userName = (EditText) view.findViewById(R.id.loginUserName) ;
        userPass = (EditText) view.findViewById(R.id.loginPassword);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = userName.getText().toString();
                password = userPass.getText().toString();
                DatabaseHelper db = new DatabaseHelper(getActivity());
               if(db.userSearch(username,password)){
                   Toast.makeText(getActivity(), "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(getActivity(),Userhome.class);
                   intent.putExtra("userLoginName",username);
                   intent.putExtra("userRotaDurum",String.valueOf(db.userRotaBilgisiAl(username)));
                   startActivity(intent);
               }
               else{
                   Toast.makeText(getActivity(), "Giriş Bilgileriniz Yanlış", Toast.LENGTH_SHORT).show();

               }

            }
        });
        return view;
    }

}