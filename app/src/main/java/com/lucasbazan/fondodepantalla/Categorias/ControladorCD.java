package com.lucasbazan.fondodepantalla.Categorias;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lucasbazan.fondodepantalla.CategoriasCliente.MusicaCliente;
import com.lucasbazan.fondodepantalla.CategoriasCliente.PeliculasCliente;
import com.lucasbazan.fondodepantalla.CategoriasCliente.SeriesCliente;
import com.lucasbazan.fondodepantalla.CategoriasCliente.VideojuegosCliente;
import com.lucasbazan.fondodepantalla.R;

public class ControladorCD extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_controlador_cd);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String CategoriaRecuperada = getIntent().getStringExtra("Categoria");

        if (CategoriaRecuperada.equals("Peliculas")){
            startActivity(new Intent(ControladorCD.this, PeliculasCliente.class));
            finish();
        }

        if (CategoriaRecuperada.equals("Series")){
            startActivity(new Intent(ControladorCD.this, SeriesCliente.class));
            finish();
        }

        if (CategoriaRecuperada.equals("Musica")){
            startActivity(new Intent(ControladorCD.this, MusicaCliente.class));
            finish();
        }

        if (CategoriaRecuperada.equals("Videojuegos")){
            startActivity(new Intent(ControladorCD.this, VideojuegosCliente.class));
            finish();
        }

    }
}