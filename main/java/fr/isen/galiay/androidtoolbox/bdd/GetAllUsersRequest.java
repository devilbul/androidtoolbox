package fr.isen.galiay.androidtoolbox.bdd;

import android.os.AsyncTask;

import java.util.List;

import static fr.isen.galiay.androidtoolbox.SauvegardeActivity.*;

public class GetAllUsersRequest extends AsyncTask<Void, Void, List<User>> {
    AsyncResponse delegate = null;

    public GetAllUsersRequest(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected List<User> doInBackground(Void... voids) {
        return db.userDAO().getAll();
    }

    @Override
    protected void onPostExecute(List<User> result) {
        delegate.processFinish(result);
    }
}
