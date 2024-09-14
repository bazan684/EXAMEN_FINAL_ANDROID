package com.lucasbazan.fondodepantalla.FragmentosCliente;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lucasbazan.fondodepantalla.InicioSesion;
import com.lucasbazan.fondodepantalla.R;


public class AcerDeCliente extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_acer_de_cliente, container, false);




        return view;
    }
}