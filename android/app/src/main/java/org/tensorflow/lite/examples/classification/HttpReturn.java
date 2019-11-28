package org.tensorflow.lite.examples.classification;

import android.os.AsyncTask;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpReturn extends AsyncTask<Void,Void,String> {

    public String data;

    public HttpReturn(String data){
        this.data = data;
    }

    @Override
    protected String doInBackground(Void... voids) {
        //log.i("teste",String);
        StringBuilder resposta= new StringBuilder();

        URL url= null;
        try {
            url = new URL("http://192.168.0.135:8080/Servidor/webresources/2048/direcao");

            // Create the urlConnection
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();


                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                urlConnection.setRequestProperty("Content-Type", "application/json");

                urlConnection.setRequestMethod("POST");

                // Send the post body

                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write(data.toString());
                writer.flush();


                int statusCode = urlConnection.getResponseCode();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
