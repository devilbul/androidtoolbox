package fr.isen.galiay.androidtoolbox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText identifiantEdit;
    EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        setContentView(R.layout.login);
        identifiantEdit = findViewById(R.id.auth);
        passwordEdit = findViewById(R.id.pwd);

        if (sharedPreferences.contains("lastIdentifiant") && sharedPreferences.contains("lastPassword")) {
            identifiantEdit.setText(sharedPreferences.getString("lastIdentifiant", null));
            passwordEdit.setText(sharedPreferences.getString("lastPassword", null));
            if ((Objects.equals(sharedPreferences.getString("lastIdentifiant", null), "root")
                    || Objects.equals(sharedPreferences.getString("lastIdentifiant", null), "root@root"))
                    && Objects.equals(sharedPreferences.getString("lastPassword", null), "root")) {
                Intent goToHome = new Intent(getApplicationContext(), HomeActivity.class);

                goToHome.putExtra("identifiant", sharedPreferences.getString("lastIdentifiant", null));
                startActivity(goToHome);
            }
        }
    }

    public void Authentification(View view) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        Intent goToHome = new Intent(getApplicationContext(), HomeActivity.class);
        String identifiant;
        String password;

        identifiant = identifiantEdit.getText().toString();
        password = passwordEdit.getText().toString();

        if ((identifiant.equals("root") || identifiant.equals("root@root")) && password.equals("root")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Bonjour " + identifiant, Toast.LENGTH_SHORT);

            sharedPreferences.edit().putString("lastIdentifiant", identifiant).putString("lastPassword", password).apply();
            toast.show();
            goToHome.putExtra("identifiant", identifiant);
            startActivity(goToHome);
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Identifiant ou mot de passe incorrect !", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
