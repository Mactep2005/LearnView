package com.aplana.ivan.learnview;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aplana.ivan.learnview.tracker.service.api.TrackerServiceApi;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;

import fr.quentinklein.slt.LocationTracker;
import fr.quentinklein.slt.TrackerSettings;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn;
    TextView res;
    RadioGroup rbtn;
    EditText tName,tLang,tVal;
    TestEntity testEntity = new TestEntity();

    HttpURLConnection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        res = (TextView) findViewById(R.id.res);
        rbtn=(RadioGroup) findViewById(R.id.action);
        tName= (EditText) findViewById(R.id.eName);
        tLang=(EditText) findViewById(R.id.eLanguage);
        tVal=(EditText) findViewById(R.id.eValue);
        btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
int a=rbtn.getCheckedRadioButtonId();
        switch (rbtn.getCheckedRadioButtonId()) {
            case 1: {
             //  new AsyncRequest().execute();
                //Intent intent = new Intent(this, row.class);
                // intent.putExtra("author", nick);
                //   intent.putExtra("client", client);
                //startActivity(intent);


                TestEntity tst=new  TestEntity();
                tst.name=tName.getText().toString();
                tst.language=tLang.getText().toString();
                tst.value=tVal.getText().toString();
                //res.setText(tst.ObjToJson(tst));
            }
            case 2131558524: {
        /*        MyLocation ml = new MyLocation(this);
                Location location = ml.getLocation();
                String latLongString = ml.updateWithNewLocation(location);

                res.setText("Your current position is:\n" + latLongString);*/
                if (    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // You need to ask the user to enable the permissions
                } else {
                    TrackerSettings settings =
                            new TrackerSettings()
                                    .setUseGPS(true)
                                    .setUseNetwork(true)
                                    .setUsePassive(true)
                                    .setTimeBetweenUpdates(10000);

                    LocationTracker tracker = new LocationTracker(this, settings) {
                        @Override
                        public void onLocationFound(Location location) {
                            Gson gson = new Gson();
                            testEntity.value = Double.toString(location.getLatitude());
                            testEntity.language = Double.toString(location.getLongitude());
                            testEntity.name = Long.toString(location.getTime());
                            res.setText(gson.toJson(testEntity));
                            new AsyncRequest().execute();
                        }

                        @Override
                        public void onTimeout() {

                        }
                    };
                    tracker.startListening();
                }

            }
           case 2: {
            //    new AsyncRequest().execute();
            }
       }
    }

    private class AsyncRequest extends AsyncTask<String, Integer, String> {
        TrackerServiceApi api = new TrackerServiceApi("http://172.16.0.5:9090/");
        @Override
        protected String doInBackground(String... params) {
            try {
                return new Request().json;
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
        public String json;
        public Request() throws IOException {
            Gson gson = new Gson();
            TrackerServiceApi api = new TrackerServiceApi("http://172.16.0.5:9090/");
            try {
                json = api.createEntity(gson.toJson(testEntity));
            } catch (IOException e) {
                throw e;
            }
        }
        //private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36";
        //private final String API_SERVER = "http://172.16.0.5:9090";
     //   private final String API_SERVER = "http://scool88.royal-webhost.tk";

        //public String Content;


        /*public Request() throws IOException {
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
            }*/
       // }
    }
}
