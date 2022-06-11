package com.example.appson2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class fragmentAdmin extends Fragment {

    private Button myButton;
    private EditText token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        myButton = (Button) view.findViewById(R.id.adminButton);
        token = (EditText) view.findViewById(R.id.adminToken);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String adminToken = token.getText().toString();
                DatabaseHelper db = new DatabaseHelper(getActivity());
                if(db.tokenSearch(adminToken)){
                    Toast.makeText(getActivity(), "Admin Tokeni Başarılı", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(),Adminhome.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getActivity(), "Admin Token Bilgileriniz Yanlış", Toast.LENGTH_SHORT).show();

                }

            }
        });
        return view;

    }
}