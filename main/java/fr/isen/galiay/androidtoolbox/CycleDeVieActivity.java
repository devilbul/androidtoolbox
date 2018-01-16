package fr.isen.galiay.androidtoolbox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class CycleDeVieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cycle_de_vie);
        TextView textView = findViewById(R.id.textView2);
        textView.setText("Cycle de Vie");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cycle_de_vie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
        Intent goToSauvegarde = new Intent(getApplicationContext(), SauvegardeActivity.class);
        Intent goToPermissions = new Intent(getApplicationContext(), PermissionsActivity.class);

        switch (item.getItemId()) {
            case R.id.action_deconnexion:
                sharedPreferences.edit().remove("lastIdentifiant").remove("lastPassword").apply();
                startActivity(goToLogin);
                finish();
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
