package com.lucasbazan.fondodepantalla.CategoriasClienteFirebase;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lucasbazan.fondodepantalla.CategoriasAdmin.MusicaA.Musica;
import com.lucasbazan.fondodepantalla.CategoriasAdmin.MusicaA.ViewHolderMusica;
import com.lucasbazan.fondodepantalla.CategoriasAdmin.PeliculasA.Pelicula;
import com.lucasbazan.fondodepantalla.CategoriasCliente.MusicaCliente;
import com.lucasbazan.fondodepantalla.DetalleCliente.DetalleImagen;
import com.lucasbazan.fondodepantalla.R;

import java.util.HashMap;

public class ListaCategoriaFirebase extends AppCompatActivity {

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerViewCat_Elegido;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseRecyclerAdapter<ImgCatFirebaseElegida, ViewHolderImgCatFElegida> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<ImgCatFirebaseElegida> options;

    SharedPreferences sharedPreferences;
    Dialog dialog;

    ValueEventListener valueEventListener;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_categoria_firebase);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Lista Imágenes");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        String BD_CAT_FIREBASE = getIntent().getStringExtra("NOMBRE_CATEGORIA");
        recyclerViewCat_Elegido = findViewById(R.id.recyclerViewCat_Elegido);
        recyclerViewCat_Elegido.setHasFixedSize(true);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("CATEGORIA_SUBIDAS_FIREBASE").child(BD_CAT_FIREBASE);

        dialog = new Dialog(ListaCategoriaFirebase.this);

        ListarCategoriaSeleccionada();

    }

    private void ListarCategoriaSeleccionada() {

        options = new FirebaseRecyclerOptions.Builder<ImgCatFirebaseElegida>().setQuery(databaseReference,ImgCatFirebaseElegida.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ImgCatFirebaseElegida, ViewHolderImgCatFElegida>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderImgCatFElegida viewHolderImgCatFElegida, int i, @NonNull ImgCatFirebaseElegida imgCatFirebaseElegida) {
                viewHolderImgCatFElegida.SeteoCategoriaFElegida(
                        getApplicationContext(),
                        imgCatFirebaseElegida.getNombre(),
                        imgCatFirebaseElegida.getVistas(),
                        imgCatFirebaseElegida.getImagen()
                );
            }

            @NonNull
            @Override
            public ViewHolderImgCatFElegida onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.img_cat_f_elegida, parent, false);

                ViewHolderImgCatFElegida viewHolderImgCatFElegida = new ViewHolderImgCatFElegida(itemView);

                viewHolderImgCatFElegida.setOnClickListener(new ViewHolderImgCatFElegida.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        final String Id = getItem(position).getId();
                        String Imagen = getItem(position).getImagen();
                        String Nombres = getItem(position).getNombre();
                        int Vistas = getItem(position).getVistas();
                        String VistaString = String.valueOf(Vistas);

                        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()){
                                    ImgCatFirebaseElegida imgElegida = ds.getValue(ImgCatFirebaseElegida.class);

                                    if (imgElegida.getId().equals(Id)) {
                                        int i = 1;
                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("vistas", Vistas+i);
                                        ds.getRef().updateChildren(hashMap);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        Intent intent = new Intent(ListaCategoriaFirebase.this, DetalleImagen.class);
                        intent.putExtra("Imagen", Imagen);
                        intent.putExtra("Nombre", Nombres);
                        intent.putExtra("Vista", VistaString);

                        startActivity(intent);

                    }

                    public void onItemLongClick(View view, int position) {
                    }
                });
                return viewHolderImgCatFElegida;
            }
        };
            /*recyclerViewCat_Elegido.setLayoutManager(new GridLayoutManager(ListaCategoriaFirebase.this, 2));
            firebaseRecyclerAdapter.startListening();
            recyclerViewCat_Elegido.setAdapter(firebaseRecyclerAdapter);*/


        sharedPreferences = ListaCategoriaFirebase.this.getSharedPreferences("MUSICA",MODE_PRIVATE);
        String ordenar_en = sharedPreferences.getString("Ordenar", "Dos");

        if (ordenar_en.equals("Dos")){
            recyclerViewCat_Elegido.setLayoutManager(new GridLayoutManager(ListaCategoriaFirebase.this, 2));
            firebaseRecyclerAdapter.startListening();
            recyclerViewCat_Elegido.setAdapter(firebaseRecyclerAdapter);

        } else if (ordenar_en.equals("Tres")) {
            recyclerViewCat_Elegido.setLayoutManager(new GridLayoutManager(ListaCategoriaFirebase.this, 3));
            firebaseRecyclerAdapter.startListening();
            recyclerViewCat_Elegido.setAdapter(firebaseRecyclerAdapter);
        }


    }

    @Override
    protected void onStart() {
        if (firebaseRecyclerAdapter != null) {
            firebaseRecyclerAdapter.startListening();
        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (databaseReference != null && valueEventListener!= null) {
            databaseReference.removeEventListener(valueEventListener);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_vista,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Vista) {
            Ordenar_Imagenes();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Ordenar_Imagenes(){
        String ubicacion = "fuentes/sans_negrita.ttf";
        Typeface tf = Typeface.createFromAsset(ListaCategoriaFirebase.this.getAssets(),ubicacion);

        TextView OrdenarTXT;
        Button Dos_Columnas, Tres_Columnas;

        dialog.setContentView(R.layout.dialog_ordenar);

        OrdenarTXT = dialog.findViewById(R.id.OrdenarTXT);
        Dos_Columnas = dialog.findViewById(R.id.Dos_Columnas);
        Tres_Columnas = dialog.findViewById(R.id.Tres_Columnas);

        OrdenarTXT.setTypeface(tf);
        Dos_Columnas.setTypeface(tf);
        Tres_Columnas.setTypeface(tf);

        Dos_Columnas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Ordenar", "Dos");
                editor.apply();
                recreate();
                dialog.dismiss();
            }
        });

        Tres_Columnas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Ordenar", "Tres");
                editor.apply();
                recreate();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}