package fr.isen.galiay.androidtoolbox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import fr.isen.galiay.androidtoolbox.webService.AsyncResponse;
import fr.isen.galiay.androidtoolbox.webService.MyAdapter;
import fr.isen.galiay.androidtoolbox.webService.WebServiceTask;
import fr.isen.galiay.androidtoolbox.webService.results;

public class WebServiceActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    public static List<results> listUsers;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_service);
        context = WebServiceActivity.this;
        mRecyclerView = findViewById(R.id.listUser);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getUsers();
    }

    private void getUsers() {
        new WebServiceTask(new AsyncResponse() {
            @Override
            public void processFinish(List<results> user) {
                listUsers = user;
                mRecyclerView.setAdapter(new MyAdapter());
            }
        }).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_webservice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
        Intent goToCycleDeVie = new Intent(getApplicationContext(), CycleDeVieActivity.class);
        Intent goToSauvegarde = new Intent(getApplicationContext(), SauvegardeActivity.class);
        Intent goToPermissions = new Intent(getApplicationContext(), PermissionsActivity.class);

        switch (item.getItemId()) {
            case R.id.action_deconnexion:
                sharedPreferences.edit().remove("lastIdentifiant").remove("lastPassword").apply();
                startActivity(goToLogin);
                finish();
                return true;
            case R.id.action_cycle_de_vie :
                startActivity(goToCycleDeVie);
                return true;
            case R.id.action_sauvergarde :
                startActivity(goToSauvegarde);
                return true;
            case R.id.action_permission :
                startActivity(goToPermissions);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
