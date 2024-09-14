package com.lucasbazan.fondodepantalla.Detalle;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lucasbazan.fondodepantalla.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Detalle_Administrador extends AppCompatActivity {

    CircleImageView ImagenDetalleAdmin;
    TextView UidDetalleAdmin, NombresDetalleAdmin, ApellidosDetalleAdmin, CorreoDetalleAdmin, EdadDetalleAdmin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_administrador);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Detalle");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        ImagenDetalleAdmin = findViewById(R.id.ImagenDetalleAdmin);
        UidDetalleAdmin = findViewById(R.id.UidDetalleAdmin);
        NombresDetalleAdmin = findViewById(R.id.NombresDetalleAdmin);
        ApellidosDetalleAdmin = findViewById(R.id.ApellidosDetalleAdmin);
        CorreoDetalleAdmin = findViewById(R.id.CorreoDetalleAdmin);
        EdadDetalleAdmin = findViewById(R.id.EdadDetalleAdmin);

        String UIDDetalle = getIntent().getStringExtra("UID");
        String NombresDetalle = getIntent().getStringExtra("NOMBRE");
        String ApellidosDetalle = getIntent().getStringExtra("APELLIDO");
        String CorreoDetalle = getIntent().getStringExtra("CORREO");
        String EdadDetalle = getIntent().getStringExtra("EDAD");
        String ImagenDetalle = getIntent().getStringExtra("IMAGEN");

        UidDetalleAdmin.setText("UID = "+UIDDetalle);
        NombresDetalleAdmin.setText("NOMBRE = "+NombresDetalle);
        ApellidosDetalleAdmin.setText("APELLIDO = "+ApellidosDetalle);
        CorreoDetalleAdmin.setText("CORREO = "+CorreoDetalle);
        EdadDetalleAdmin.setText("EDAD = "+EdadDetalle);

        try {
            Picasso.get().load(ImagenDetalle).placeholder(R.drawable.admin_item).into(ImagenDetalleAdmin);
        }catch (Exception e) {
            Picasso.get().load(R.drawable.admin_item).into(ImagenDetalleAdmin);
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();  // or navigateUpTo() if you have an Up button in your action bar
        return super.onSupportNavigateUp();
    }
}