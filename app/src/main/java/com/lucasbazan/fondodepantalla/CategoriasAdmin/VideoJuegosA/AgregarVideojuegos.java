package com.lucasbazan.fondodepantalla.CategoriasAdmin.VideoJuegosA;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lucasbazan.fondodepantalla.CategoriasAdmin.MusicaA.AgregarMusica;
import com.lucasbazan.fondodepantalla.CategoriasAdmin.MusicaA.Musica;
import com.lucasbazan.fondodepantalla.CategoriasAdmin.MusicaA.MusicaA;
import com.lucasbazan.fondodepantalla.CategoriasAdmin.PeliculasA.AgregarPelicula;
import com.lucasbazan.fondodepantalla.CategoriasAdmin.PeliculasA.Pelicula;
import com.lucasbazan.fondodepantalla.CategoriasAdmin.PeliculasA.PeliculasA;
import com.lucasbazan.fondodepantalla.CategoriasAdmin.SeriesA.AgregarSerie;
import com.lucasbazan.fondodepantalla.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AgregarVideojuegos extends AppCompatActivity {

    TextView IdVideojuegos, VistaVideojuegos;
    EditText NombreVideojuegos;
    ImageView ImagenAgregarVideojuegos;
    Button PublicarVideojuegos;

    String RutaDeAlmacenamiento = "videoJuegos_Subida/";
    String RutaDeBaseDeDatos = "VIDEOJUEGOS";
    Uri RutaArchivoUri;

    StorageReference mStorageReference;
    com.google.firebase.database.DatabaseReference DatabaseReference;

    ProgressDialog progressDialog;

    String rId,rNombre, rImagen, rVista;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agregar_video_juegos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Publicar");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        IdVideojuegos = findViewById(R.id.IdVideojuegos);
        VistaVideojuegos = findViewById(R.id.VistaVideojuegos);
        NombreVideojuegos = findViewById(R.id.NombreVideojuegos);
        ImagenAgregarVideojuegos = findViewById(R.id.ImagenAgregarVideojuegos);
        PublicarVideojuegos = findViewById(R.id.PublicarVideojuegos);

        mStorageReference = FirebaseStorage.getInstance().getReference();
        DatabaseReference = FirebaseDatabase.getInstance().getReference(RutaDeBaseDeDatos);
        progressDialog = new ProgressDialog(AgregarVideojuegos.this);

        Bundle intent = getIntent().getExtras();
        if (intent != null) {

            rId = intent.getString("IdEnviado");
            rNombre = intent.getString("NombreEnviado");
            rImagen = intent.getString("ImagenEnviada");
            rVista  = intent.getString("VistaEnviada");

            IdVideojuegos.setText(rId);
            NombreVideojuegos.setText(rNombre);
            VistaVideojuegos.setText(rVista);
            Picasso.get().load(rImagen).into(ImagenAgregarVideojuegos);

            actionBar.setTitle("Actualizar");
            String actualizar = "Actualizar";

            PublicarVideojuegos.setText(actualizar);

        }

        ImagenAgregarVideojuegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                /*Código implementado en actualización*/
                ObtenerImagenGaleria.launch(intent);
            }
        });

        PublicarVideojuegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PublicarVideojuegos.getText().equals("Publicar")) {
                    SubirImagen();
                } else {
                    EmpezarActualizacion();
                }
            }
        });

    }

    private void EmpezarActualizacion() {

        progressDialog.setTitle("Actualizando");
        progressDialog.setMessage("Espere por favor...");
        progressDialog.show();

        progressDialog.setCancelable(false);
        EliminarImagenAnterior();

    }

    private void EliminarImagenAnterior() {

        StorageReference Imagen = getInstance().getReferenceFromUrl(rImagen);
        Imagen.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(AgregarVideojuegos.this, "La imagen anterior a sido eliminada", Toast.LENGTH_SHORT).show();
                SubirNuevaImagen();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AgregarVideojuegos.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void SubirNuevaImagen() {

        String nuevaImagen = System.currentTimeMillis()+".png";
        StorageReference mStorageReference2 = mStorageReference.child(RutaDeAlmacenamiento + nuevaImagen);
        Bitmap bitmap = ((BitmapDrawable)ImagenAgregarVideojuegos.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte [] data = byteArrayOutputStream.toByteArray();
        UploadTask uploadTask = mStorageReference2.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AgregarVideojuegos.this, "Nueva imagen cargada", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                Uri downloaUri = uriTask.getResult();
                ActualizarImagenBD(downloaUri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AgregarVideojuegos.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void ActualizarImagenBD(final String NuevaImagen) {

        final String nombreActualiar = NombreVideojuegos.getText().toString();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("VIDEOJUEGOS");

        Query query = databaseReference.orderByChild("id").equalTo(rId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    ds.getRef().child("nombre").setValue(nombreActualiar);
                    ds.getRef().child("imagen").setValue(NuevaImagen);
                }
                progressDialog.dismiss();
                Toast.makeText(AgregarVideojuegos.this, "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AgregarVideojuegos.this, VideoJuegosA.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void SubirImagen() {
        final String mNombre = NombreVideojuegos.getText().toString();

        /*Validar que el nombre y la imagen no sean nulos*/
        if (mNombre.equals("") || RutaArchivoUri==null){
            Toast.makeText(this, "Asigne un nombre o una imagen", Toast.LENGTH_SHORT).show();
        }

        else {
            progressDialog.setTitle("Espere por favor");
            progressDialog.setMessage("Subiendo Imagen MÚSICA ...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            StorageReference storageReference2 = mStorageReference.child(
                    RutaDeAlmacenamiento+System.currentTimeMillis()+"."
                            +ObtenerExtencionDelArchivo(RutaArchivoUri));
            storageReference2.putFile(RutaArchivoUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());

                            Uri downloadURI = uriTask.getResult();

                            /*Añadido en video anterior*/
                            String ID = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss",
                                    Locale.getDefault()).format(System.currentTimeMillis());
                            IdVideojuegos.setText(ID);

                            /*Añadido en video anterior*/
                            String mId = IdVideojuegos.getText().toString();
                            String mVista = VistaVideojuegos.getText().toString();
                            int VISTA = Integer.parseInt(mVista);

                            /*Añadido en video anterior*/
                            VideoJuego videoJuego = new VideoJuego(mNombre+"/"+mId,downloadURI.toString(),
                                    mNombre,VISTA);
                            //MUSICA (ID, IMAGEN, NOMBRE, VISTAS)
                            String ID_IMAGEN = DatabaseReference.push().getKey();

                            DatabaseReference.child(ID_IMAGEN).setValue(videoJuego);

                            progressDialog.dismiss();
                            Toast.makeText(AgregarVideojuegos.this, "Subido Exitosamente",
                                    Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(AgregarVideojuegos.this, VideoJuegosA.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AgregarVideojuegos.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.setTitle("Publicando");
                            progressDialog.setCancelable(false);
                        }
                    });

        }

    }

    private String ObtenerExtencionDelArchivo(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_DE_SOLITUD_IMAGEN
                && resultCode == RESULT_OK
                && data != null
                && data.getData()!= null) {

            RutaArchivoUri = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), RutaArchivoUri);
                ImagenAgregarVideojuegos.setImageBitmap(bitmap);

            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    private ActivityResultLauncher<Intent> ObtenerImagenGaleria = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK){

                        Intent data = result.getData();

                        RutaArchivoUri = data.getData();
                        ImagenAgregarVideojuegos.setImageURI(RutaArchivoUri);
                    }
                    else {
                        Toast.makeText(AgregarVideojuegos.this, "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

}