package com.patis.wms.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.patis.wms.android.dto.WorkerDTO;
import com.patis.wms.android.screen.login.LoginActivity;
import com.patis.wms.android.screen.new_request.NewRequestFragment;
import com.patis.wms.android.screen.request.RequestListFragment;
import com.patis.wms.android.screen.task.TaskListFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView tvCurrentUser;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        tvCurrentUser = navigationView.getHeaderView(0).findViewById(R.id.tvCurrentUser);

        App.getBackendApi().findWorkerById(App.local().getLong("currentUserId")).enqueue(new Callback<WorkerDTO>() {
            @Override
            public void onResponse(Call<WorkerDTO> call, Response<WorkerDTO> response) {
                WorkerDTO workerDTO = response.body();
                if (workerDTO != null){
                    tvCurrentUser.setText(workerDTO.getPerson().getFio());
                }
            }
            @Override public void onFailure(Call<WorkerDTO> call, Throwable t) {}
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.nav_close){
            App.local().remove("password");
            App.local().remove("personId");
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }

        Fragment fragment = null;
        Class fragmentClass = null;

        int id = item.getItemId();
        if (id == R.id.nav_request) {
            fragmentClass = RequestListFragment.class;
        } else if (id == R.id.nav_worker) {
            fragmentClass = NewRequestFragment.class;
        } else if (id == R.id.nav_task) {
            fragmentClass = TaskListFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, fragment).commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setContent(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(null)
                .commit();

    }

    public void clearContent(){
        for(Fragment fragment:getSupportFragmentManager().getFragments()){
            if (fragment != null)
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

}
