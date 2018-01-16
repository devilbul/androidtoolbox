package fr.isen.galiay.androidtoolbox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    TextView bonjour = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        String identifiant = getIntent().getStringExtra("identifiant");
        bonjour = findViewById(R.id.textView);
        bonjour.setText("Bonjour " + identifiant);
    }

    public void goToCycleDeVie(View view) {
        Intent goToCycleDeVie = new Intent(getApplicationContext(), CycleDeVieActivity.class);
        startActivity(goToCycleDeVie);
    }

    public void goToSauvegarde(View view) {
        Intent goToSauvegarde = new Intent(getApplicationContext(), SauvegardeActivity.class);
        startActivity(goToSauvegarde);
    }

    public void goToPermissions(View view) {
        Intent goToPermissions = new Intent(getApplicationContext(), PermissionsActivity.class);
        startActivity(goToPermissions);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
