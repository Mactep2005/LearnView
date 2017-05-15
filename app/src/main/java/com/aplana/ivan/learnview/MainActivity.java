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
import java.text.SimpleDateFormat;
import java.util.Date;

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
                                    .setUsePassive(false)
                                    .setTimeBetweenUpdates(10000);

                    LocationTracker tracker = new LocationTracker(this, settings) {
                        @Override
                        public void onLocationFound(Location location) {
                            Gson gson = new Gson();
                            Date d = new Date(location.getTime() );
                            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
                            testEntity.value = Double.toString(location.getLatitude());
                            testEntity.language = Double.toString(location.getLongitude());
                            testEntity.name = sdf.format(d);
                            res.setText(gson.toJson(testEntity));
                            new AsyncRequest().execute();
                            stopListening();
                        }

                        @Override
                        public void onTimeout() {

                        }
                    };
                    tracker.startListening();
                    res.setText("Поиск местоположения ...");
                }

            }
           case 2: {
            //    new AsyncRequest().execute();
            }
       }
    }

    private class AsyncRequest extends AsyncTask<String, Integer, String> {
 //       TrackerServiceApi api = new TrackerServiceApi("http://172.16.0.5:9090/");
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

    }
}
