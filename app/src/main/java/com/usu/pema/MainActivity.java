package com.usu.pema;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.usu.pema.fragment.HomeFragment;
import com.usu.pema.fragment.LostfoundFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bnvMain;
    Toolbar toolbarMain;

    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnvMain = findViewById(R.id.bnv_main);
        fab = findViewById(R.id.fab);

        toolbarMain = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setTitle("Pemerintahan Mahasiswa");

        bnvMain.setOnNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, new HomeFragment()).commit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PengaduanActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {

            case R.id.home_menu :
                this.getSupportFragmentManager().popBackStack();
                fragment = new HomeFragment();
                getSupportActionBar().setTitle("Pemerintahan Mahasiswa");
//                actionBar = getSupportActionBar();
//                getSupportActionBar().setTitle("Pemerintahan Mahasiswa");
                break;
//            case R.id.aduan_menu :
//                this.getSupportFragmentManager().popBackStack();
//                fragment = new AduanFragment();
//                getSupportActionBar().setTitle("Kotak Pengaduan");
//                break;
            case R.id.lostfound_menu :
                this.getSupportFragmentManager().popBackStack();
                fragment = new LostfoundFragment();
                getSupportActionBar().setTitle("Lost and Found");
//                actionBar = getSupportActionBar();
//                getSupportActionBar().setTitle("Lost and Found");
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, fragment).commit();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_update) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
