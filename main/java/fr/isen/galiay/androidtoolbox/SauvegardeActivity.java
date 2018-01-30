package fr.isen.galiay.androidtoolbox;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.List;

import fr.isen.galiay.androidtoolbox.bdd.AddUserRequest;
import fr.isen.galiay.androidtoolbox.bdd.AppDatabase;
import fr.isen.galiay.androidtoolbox.bdd.AsyncResponse;
import fr.isen.galiay.androidtoolbox.bdd.GetAllUsersRequest;
import fr.isen.galiay.androidtoolbox.bdd.User;

public class SauvegardeActivity extends AppCompatActivity {
    TextView json;
    TextView bdd;
    public static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sauvegarde);
        json = findViewById(R.id.jsonResult);
        bdd = findViewById(R.id.bddResult);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build();
        new AddUserRequest().execute();
        new GetAllUsersRequest(new AsyncResponse() {
            @Override
            public void processFinish(List<User> users) {
                StringBuilder display = new StringBuilder();

                for (User user : users) {
                    display.append(user.getNom()).append("\n").append(user.getPrenom()).append("\n")
                            .append(user.getDateNaissance()).append("\n").append(calculAge(user.getDateNaissance()));
                }

                bdd.setText(display.toString());
            }
        }).execute();
        createJson();
        writeJsonToLayout();
    }

    private String calculAge(String dateNaissance) {
        Calendar today = Calendar.getInstance();
        Calendar birthday = Calendar.getInstance();
        int jour = Integer.parseInt(dateNaissance.split("-")[0]);
        int mois = Integer.parseInt(dateNaissance.split("-")[1]);
        int annee = Integer.parseInt(dateNaissance.split("-")[2]);
        int age;

        birthday.set(annee, mois, jour);
        age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        today.add(Calendar.YEAR, -age);

        if (birthday.after(today))
            age--;

        return age + " ans";
    }

    private void createJson() {
        try {
            JSONObject info = new JSONObject();
            OutputStreamWriter file = new OutputStreamWriter(getApplicationContext().openFileOutput("information.json", Context.MODE_PRIVATE));
            Toast toast = Toast.makeText(getApplicationContext(), "Sauvegarder", Toast.LENGTH_SHORT);

            info.put("prenom", "Romain");
            info.put("nom", "Galiay");
            info.put("date_naissance", "30-09-1995");

            toast.show();
            file.write(info.toString());
            file.flush();
            file.close();
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
                int size = 64;
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
                jsonResultString = jsonObject.getString("prenom") + "\n" + jsonObject.getString("nom") +
                        "\n" + jsonObject.getString("date_naissance") + "\n" + calculAge(jsonObject.getString("date_naissance"));

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

    @Override
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
        Intent goToWebService = new Intent(getApplicationContext(), WebServiceActivity.class);

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
            case R.id.action_web_service :
                startActivity(goToWebService);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
