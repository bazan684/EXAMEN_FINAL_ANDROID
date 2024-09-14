package com.lucasbazan.fondodepantalla;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.lucasbazan.fondodepantalla.FragmentosCliente.AcerDeCliente;
import com.lucasbazan.fondodepantalla.FragmentosCliente.CompartirCliente;
import com.lucasbazan.fondodepantalla.FragmentosCliente.InicioCliente;
import com.lucasbazan.fondodepantalla.FragmentosCliente.Login_admin;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toobar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView  navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        if (savedInstanceState == null)  {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new InicioCliente()).commit();
            navigationView.setCheckedItem(R.id.InicioCliente);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.InicioCliente) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,

                    new InicioCliente()).commit();

        }

        if (item.getItemId() == R.id.AcercaDe) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,

                    new AcerDeCliente()).commit();

        }

        if (item.getItemId() == R.id.Compartir) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,

                    new CompartirCliente()).commit();

        }


        if (item.getItemId() == R.id.Administrador) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,

                    new Login_admin()).commit();

        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
        //return super.onOptionsItemSelected(item);
    }
}