package com.aplana.ivan.learnview;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn;
    TextView res;

    HttpURLConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        res = (TextView) findViewById(R.id.res);

        btn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        new AsyncRequest().execute();
        Intent intent = new Intent(this, row.class);
        // intent.putExtra("author", nick);
        //   intent.putExtra("client", client);
        startActivity(intent);
    }

    private class AsyncRequest extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                return new Request().Content;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            res.setText(s);

        }
    }

    public class Request {

        private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";
        private final String API_SERVER = "http://172.16.0.5:9090";
     //   private final String API_SERVER = "http://scool88.royal-webhost.tk";

        public String Content;


        public Request() throws IOException {
            StringBuilder result = new StringBuilder();
            String urlParameters = API_SERVER + "/api/values"; // url params
     //       String urlParameters = API_SERVER + "/bd/service.php?action=select"; // url params
            URL url = new URL(urlParameters);
            conn = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            } finally {
                conn.disconnect();
            }
            try {
                // сформируем ответ сервера в string
                // обрежем в полученном ответе все, что находится за "]"
                // это необходимо, т.к. json ответ приходит с мусором
                // и если этот мусор не убрать - будет невалидным
                Content = result.toString();
                Content = Content.substring(0, Content.indexOf("]") + 1);
                Log.i("chat", "+  - полный ответ сервера:\n" + Content);

            } catch (Exception e) {
                Log.i("chat", "+ ошибка чтения из потока: " + e.getMessage());
            } finally {
                conn.disconnect();
                Log.i("chat", "+ --------------- ЗАКРОЕМ СОЕДИНЕНИЕ");
            }
        }
    }
}
