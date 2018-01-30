package fr.isen.galiay.androidtoolbox.webService;

public class json {
    private results[] results;

    public json(results[] results) {
        this.results = results;
    }

    public results getResult(int i) {
        return results[i];
    }

    public results[] getResults() {
        return results;
    }

    public void setResults(results[] results) {
        this.results = results;
    }
}
