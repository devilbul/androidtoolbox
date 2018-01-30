package fr.isen.galiay.androidtoolbox.webService;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static fr.isen.galiay.androidtoolbox.util.Util.convertStreamToString;

public class WebServiceTask extends AsyncTask<Void, Void, List<results>> {
    AsyncResponse delegate = null;
    int nb = 25;

    public WebServiceTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected List<results> doInBackground(Void... voids) {

        final String Url = "https://randomuser.me/api/?nat=fr&results=" + nb;
        Gson gson;
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(Url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("GET");
            int statusCode = urlConnection.getResponseCode();

            if (statusCode == 200) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                String response = convertStreamToString(inputStream);
                gson = new Gson();
                List<results> list = new ArrayList<>();
                for (int i = 0; i < 25; i++)
                    list.add(gson.fromJson(response, json.class).getResult(i));

                return list;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<results> result) {
        delegate.processFinish(result);
    }
}
