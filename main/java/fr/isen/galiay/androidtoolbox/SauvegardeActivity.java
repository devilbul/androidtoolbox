package fr.isen.galiay.androidtoolbox;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import fr.isen.galiay.androidtoolbox.bdd.AddUserRequest;
import fr.isen.galiay.androidtoolbox.bdd.AppDatabase;
import fr.isen.galiay.androidtoolbox.bdd.AsyncResponse;
import fr.isen.galiay.androidtoolbox.bdd.GetAllUsersRequest;
import fr.isen.galiay.androidtoolbox.bdd.User;

public class SauvegardeActivity extends AppCompatActivity implements AsyncResponse {
    EditText prenomEdit = null;
    EditText nomEdit = null;
    EditText dateNaissanceEdit = null;
    TextView json = null;
    public static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sauvegarde);
        json = findViewById(R.id.jsonResult);
        prenomEdit = findViewById(R.id.prenom);
        nomEdit = findViewById(R.id.nom);
        dateNaissanceEdit = findViewById(R.id.date_naissance);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build();
        new AddUserRequest().execute();
        new GetAllUsersRequest().execute();
        // writeJsonToLayout();
    }

    @Override
    public void processFinish(List<User> users) {
        StringBuilder display = new StringBuilder();

        for (User user : users) {
            display.append(user.getNom()).append(" ").append(user.getPrenom()).append(" ").append(user.getDateNaissance()).append("\n");
        }

        json.setText(display.toString());
    }

    public void Formulaire(View view) {
        try {
            JSONObject info = new JSONObject();
            OutputStreamWriter file = new OutputStreamWriter(getApplicationContext().openFileOutput("information.json", Context.MODE_PRIVATE));
            Toast toast = Toast.makeText(getApplicationContext(), "Sauvegarder", Toast.LENGTH_SHORT);
            String prenom = prenomEdit.getText().toString();
            String nom = nomEdit.getText().toString();
            String dateNaissance = dateNaissanceEdit.getText().toString();

            if (!prenom.isEmpty() && !nom.isEmpty() && !dateNaissance.isEmpty()) {
                info.put("prenom", prenom);
                info.put("nom", nom);
                info.put("date_naissance", dateNaissance);

                toast.show();
                file.write(info.toString());
                file.flush();
                file.close();
                writeJsonToLayout();
            }
            else {
                Toast erreur = Toast.makeText(getApplicationContext(), "Erreur dans les champs du formulaire", Toast.LENGTH_LONG);

                erreur.show();
            }
        }
        catch (JSONException | IOException e) {
            Log.e("error", e.getMessage());
        }
    }

    private void writeJsonToLayout() {
        if (searchFile("information.json")) {
            try {
                JSONObject jsonObject;
                String jsonResultString;
                int size = 66;
                byte bytes[] = new byte[size];
                byte tmpBuff[] = new byte[size];

                try (FileInputStream file = getApplicationContext().openFileInput("information.json")) {
                    int read = file.read(bytes, 0, size);

                    if (read < size) {
                        int remain = size - read;

                        while (remain > 0) {
                            read = file.read(tmpBuff, 0, remain);
                            System.arraycopy(tmpBuff, 0, bytes, size - remain, read);
                            remain -= read;
                        }
                    }
                }

                jsonObject = new JSONObject(new String(bytes, "UTF-8"));
                jsonResultString = jsonObject.getString("prenom") + "\n" + jsonObject.getString("nom") + "\n" + jsonObject.getString("date_naissance");

                json.setText(jsonResultString);
            }
            catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            json.setText("_.-.-._");
        }
    }

    private boolean searchFile(String file) {
        for (String s : getApplicationContext().fileList())
            if (s.equals(file))
                return true;

        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sauvegarde, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        Intent goToLogin = new Intent(getApplicationContext(), LoginActivity.class);
        Intent goToCycleDeVie = new Intent(getApplicationContext(), CycleDeVieActivity.class);
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
            case R.id.action_permission :
                startActivity(goToPermissions);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
