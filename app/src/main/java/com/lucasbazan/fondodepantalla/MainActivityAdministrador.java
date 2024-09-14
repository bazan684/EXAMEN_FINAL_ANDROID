package com.lucasbazan.fondodepantalla;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lucasbazan.fondodepantalla.FragmentosAdmistrador.InicioAdmin;
import com.lucasbazan.fondodepantalla.FragmentosAdmistrador.ListaAdmin;
import com.lucasbazan.fondodepantalla.FragmentosAdmistrador.PerfilAdmin;
import com.lucasbazan.fondodepantalla.FragmentosAdmistrador.RegistrarAdmin;

public class MainActivityAdministrador extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_administrador);

        Toolbar toolbar = findViewById(R.id.toobarA);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_A);

        NavigationView  navigationView = findViewById(R.id.nav_viewA);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        if (savedInstanceState == null)  {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA,
                    new InicioAdmin()).commit();
            navigationView.setCheckedItem(R.id.InicioAdmin);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.InicioAdmin) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA,

                    new InicioAdmin()).commit();

        }

        if (item.getItemId() == R.id.PerfilAdmin) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA,

                    new PerfilAdmin()).commit();

        }

        if (item.getItemId() == R.id.RegistrarAdmin) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA,

                    new RegistrarAdmin()).commit();

        }

        if (item.getItemId() == R.id.ListarAdmin) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA,

                    new ListaAdmin()).commit();

        }

        if (item.getItemId() == R.id.Salir) {

            CerrarSesion();

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
        //return super.onOptionsItemSelected(item);
    }


    private void ComprobandoInicioSesion(){
        if (user !=null) {
            Toast.makeText(this, "Se ha iniciado sesión exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(MainActivityAdministrador.this,MainActivity.class));
            finish();
        }
    }


    private void CerrarSesion(){
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivityAdministrador.this,MainActivity.class));
        Toast.makeText(this, "Cerraste sesión exitosamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        ComprobandoInicioSesion();
        super.onStart();
    }
}