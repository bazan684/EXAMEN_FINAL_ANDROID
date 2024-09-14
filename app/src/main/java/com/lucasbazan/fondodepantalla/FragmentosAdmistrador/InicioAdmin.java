package com.lucasbazan.fondodepantalla.FragmentosAdmistrador;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lucasbazan.fondodepantalla.CategoriasAdmin.MusicaA.MusicaA;
import com.lucasbazan.fondodepantalla.CategoriasAdmin.PeliculasA.PeliculasA;
import com.lucasbazan.fondodepantalla.CategoriasAdmin.SeriesA.SeriesA;
import com.lucasbazan.fondodepantalla.CategoriasAdmin.VideoJuegosA.VideoJuegosA;
import com.lucasbazan.fondodepantalla.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class InicioAdmin extends Fragment {

    Button Peliculas, Series, Musica, Videojuegos;

    TextView fechaAdmin, NombreTXT;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference BASE_DE_DATOS_ADMINISTRADORES;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_inicio_admin, container, false);

        Peliculas = view.findViewById(R.id.Peliculas);
        Series = view.findViewById(R.id.Series);
        Musica = view.findViewById(R.id.Musica);
        Videojuegos = view.findViewById(R.id.Videojuegos);

        fechaAdmin = view.findViewById(R.id.fechaAdmin);
        NombreTXT = view.findViewById(R.id.NombreTXT);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        BASE_DE_DATOS_ADMINISTRADORES = FirebaseDatabase.getInstance().getReference("BASE DE DATOS ADMINISTRADORES");

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d 'de' MMMM 'de' yyyy");
        String StringFecha = simpleDateFormat.format(date);
        fechaAdmin.setText("Hoy es :"+ StringFecha);



        Peliculas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PeliculasA.class));
            }
        });

        Series.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SeriesA.class));
            }
        });


        Musica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MusicaA.class));
            }
        });

        Videojuegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), VideoJuegosA.class));
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ComprobarUsuarioActivo();
    }

    private void ComprobarUsuarioActivo() {
        if (user != null) {
            CargaDeDatos();
        }
    }

    private void CargaDeDatos() {
        BASE_DE_DATOS_ADMINISTRADORES.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nombre = "" + snapshot.child("NOMBRE").getValue();
                    NombreTXT.setText(nombre);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}