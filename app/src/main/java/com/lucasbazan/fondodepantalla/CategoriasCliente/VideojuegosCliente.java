package com.lucasbazan.fondodepantalla.CategoriasCliente;

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
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lucasbazan.fondodepantalla.CategoriasAdmin.SeriesA.Serie;
import com.lucasbazan.fondodepantalla.CategoriasAdmin.VideoJuegosA.VideoJuego;
import com.lucasbazan.fondodepantalla.CategoriasAdmin.VideoJuegosA.ViewHolderVideojuegos;
import com.lucasbazan.fondodepantalla.DetalleCliente.DetalleImagen;
import com.lucasbazan.fondodepantalla.R;

import java.util.HashMap;

public class VideojuegosCliente extends AppCompatActivity {

    RecyclerView recyclerViewVideoJuegosC;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    FirebaseRecyclerAdapter<VideoJuego, ViewHolderVideojuegos> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<VideoJuego> options;

    SharedPreferences sharedPreferences;
    Dialog dialog;

    ValueEventListener valueEventListener;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_videojuegos_cliente);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("VideoJuegos");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerViewVideoJuegosC = findViewById(R.id.recyclerViewVideoJuegosC);
        recyclerViewVideoJuegosC.setHasFixedSize(true);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mFirebaseDatabase.getReference("VIDEOJUEGOS");

        dialog = new Dialog(VideojuegosCliente.this);

        ListarImagenesVideoJuegos();

    }

    private void ListarImagenesVideoJuegos() {

        options = new FirebaseRecyclerOptions.Builder<VideoJuego>().setQuery(mRef,VideoJuego.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<VideoJuego, ViewHolderVideojuegos>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderVideojuegos viewHolderVideojuegos, int i, @NonNull VideoJuego videoJuego) {
                viewHolderVideojuegos.SeteovideoJuegos(
                        getApplicationContext(),
                        videoJuego.getNombre(),
                        videoJuego.getVistas(),
                        videoJuego.getImagen()
                );
            }

            @NonNull
            @Override
            public ViewHolderVideojuegos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videojuegos, parent, false);

                ViewHolderVideojuegos viewHolderVideojuegos = new ViewHolderVideojuegos(itemView);

                viewHolderVideojuegos.setOnClickListener(new ViewHolderVideojuegos.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String Id = getItem(position).getId();
                        String Imagen = getItem(position).getImagen();
                        String Nombres = getItem(position).getNombre();
                        final int Vistas = getItem(position).getVistas();
                        String VistaString = String.valueOf(Vistas);

                        valueEventListener = mRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot ds : snapshot.getChildren()){
                                    VideoJuego videoJuego = ds.getValue(VideoJuego.class);

                                    if (videoJuego.getId().equals(Id)) {
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

                        Intent intent = new Intent(VideojuegosCliente.this, DetalleImagen.class);
                        intent.putExtra("Imagen", Imagen);
                        intent.putExtra("Nombre", Nombres);
                        intent.putExtra("Vista", VistaString);

                        startActivity(intent);

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                    }
                });
                return viewHolderVideojuegos;
            }
        };

        sharedPreferences = VideojuegosCliente.this.getSharedPreferences("VIDEOJUEGOS",MODE_PRIVATE);
        String ordenar_en = sharedPreferences.getString("Ordenar", "Dos");

        if (ordenar_en.equals("Dos")){
            recyclerViewVideoJuegosC.setLayoutManager(new GridLayoutManager(VideojuegosCliente.this, 2));
            firebaseRecyclerAdapter.startListening();
            recyclerViewVideoJuegosC.setAdapter(firebaseRecyclerAdapter);

        } else if (ordenar_en.equals("Tres")) {
            recyclerViewVideoJuegosC.setLayoutManager(new GridLayoutManager(VideojuegosCliente.this, 3));
            firebaseRecyclerAdapter.startListening();
            recyclerViewVideoJuegosC.setAdapter(firebaseRecyclerAdapter);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseRecyclerAdapter!=null) {
            firebaseRecyclerAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRef != null && valueEventListener!= null) {
            mRef.removeEventListener(valueEventListener);

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
        Typeface tf = Typeface.createFromAsset(VideojuegosCliente.this.getAssets(),ubicacion);

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