package fr.isen.galiay.androidtoolbox.bdd;

import android.os.AsyncTask;

import static fr.isen.galiay.androidtoolbox.SauvegardeActivity.db;

public class AddUserRequest extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... arg0) {
        User user = new User();

        user.setId_user(0);
        user.setIdentifiant("root");
        user.setMotDePasse("root");
        user.setDateNaissance("30-09-1995");
        user.setPrenom("Romain");
        user.setNom("Galiay");

        db.userDAO().insertUsers(user);

        return null;
    }
}
