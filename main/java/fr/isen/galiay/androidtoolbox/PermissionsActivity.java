package fr.isen.galiay.androidtoolbox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class PermissionsActivity extends AppCompatActivity {
    TextView phoneInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permissions);
        phoneInfo = findViewById(R.id.phoneInfo);
        displayPhoneInfo();
    }

    private void displayPhoneInfo() {
        String infos;

        infos = "os.version : " + System.getProperty("os.version") + "\n" +
                "SDK_INT : " + Build.VERSION.SDK_INT + "\n" +
                "CODENAME : " + Build.VERSION.CODENAME + "\n" +
                "DEVICE : " + Build.DEVICE + "\n" +
                "MODEL : " + Build.MODEL + "\n" +
                "PRODUCT : " + Build.PRODUCT + "\n" +
                "FINGERPRINT : " + Build.FINGERPRINT + "\n" +
                "DISPLAY : " + Build.DISPLAY + "\n" +
                "BOARD : " + Build.BOARD + "\n" +
                "BOOT LOADER : " + Build.BOOTLOADER + "\n" +
                "BRAND : " + Build.BRAND + "\n" +
                "HARDWARE : " + Build.HARDWARE + "\n" +
                "HOST : " + Build.HOST + "\n" +
                "MANUFACTURE : " + Build.MANUFACTURER + "\n" +
                "USER : " + Build.USER + "\n" +
                "ID : " + Build.ID + "\n" +
                "TAGS : " + Build.TAGS + "\n" +
                "TYPE : " + Build.TYPE + "\n";

        phoneInfo.setText(infos);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_permissions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
        Intent goToCycleDeVie = new Intent(getApplicationContext(), CycleDeVieActivity.class);
        Intent goToSauvegarde = new Intent(getApplicationContext(), SauvegardeActivity.class);

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
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
