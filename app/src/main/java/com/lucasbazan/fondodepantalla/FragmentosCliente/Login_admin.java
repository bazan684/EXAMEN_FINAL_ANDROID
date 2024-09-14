package com.lucasbazan.fondodepantalla.FragmentosCliente;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lucasbazan.fondodepantalla.InicioSesion;
import com.lucasbazan.fondodepantalla.R;

public class Login_admin extends Fragment {

    Button Acceder;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_admin, container, false);

        Acceder = view.findViewById(R.id.Acceder);

        Acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getActivity(),InicioSesion.class));

                Intent intent = new Intent(getActivity(), InicioSesion.class);
                startActivity(intent);
            }
        });

        return view;


    }
}