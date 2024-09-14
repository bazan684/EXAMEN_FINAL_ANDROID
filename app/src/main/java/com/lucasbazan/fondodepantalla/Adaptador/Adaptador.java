package com.lucasbazan.fondodepantalla.Adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lucasbazan.fondodepantalla.Detalle.Detalle_Administrador;
import com.lucasbazan.fondodepantalla.Modelo.Administrador;
import com.lucasbazan.fondodepantalla.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adaptador extends RecyclerView.Adapter<Adaptador.MyHolder>{
    private Context context;
    private List<Administrador> administradores;

    public Adaptador(Context context, List<Administrador> administradores) {
        this.context = context;
        this.administradores = administradores;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_item,parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String UID = administradores.get(position).getUID();
        String IMAGEN = administradores.get(position).getIMAGEN();
        String NOMBRE = administradores.get(position).getNOMBRE();
        String APELLIDO = administradores.get(position).getAPELLIDO();
        String CORREO = administradores.get(position).getCORREO();
        int EDAD = administradores.get(position).getEDAD();
        String EdadStrign = String.valueOf(EDAD);

        holder.NombresADMIN.setText(NOMBRE);
        holder.CorreoADMIN.setText(CORREO);

        try {
            Picasso.get().load(IMAGEN).placeholder(R.drawable.admin_item).into(holder.PerfilADMIN);
        }catch (Exception e) {

            Picasso.get().load(R.drawable.admin_item).into(holder.PerfilADMIN);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Detalle_Administrador.class);
                intent.putExtra("UID", UID);
                intent.putExtra("NOMBRE", NOMBRE);
                intent.putExtra("APELLIDO", APELLIDO);
                intent.putExtra("EDAD", EdadStrign);
                intent.putExtra("IMAGEN", IMAGEN);
                intent.putExtra("CORREO", CORREO);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return administradores.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        CircleImageView PerfilADMIN;
        TextView NombresADMIN, CorreoADMIN;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            PerfilADMIN = itemView.findViewById(R.id.PerfilADMIN);
            NombresADMIN = itemView.findViewById(R.id.NombresADMIN);
            CorreoADMIN = itemView.findViewById(R.id.CorreoADMIN);
        }
    }
}
