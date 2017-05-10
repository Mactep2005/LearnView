package com.aplana.ivan.p0041basicviews;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by 1 on 14.04.2017.
 */

public class ActivityTwo extends Activity implements View.OnClickListener{
    EditText txtAuthor;
    EditText txtClient;
    EditText editText;
    ListView res;
    Button btnSend;
    Button btnReceive;

  //  String SERVER_NAME = "http://scool88.royal-webhost.tk";
    String SERVER_NAME = "http://172.16.0.5:9090";
    SQLiteDatabase chatDBlocal;
    String author, client,textSend,ansver, lnk;;

    INSERTtoChat insert_to_chat; // класс отправляет новое сообщение на сервер
    HttpURLConnection conn;
    ContentValues new_mess;
    UpdateReceiver upd_res; // класс ждет сообщение от сервиса и получив его -
    // обновляет ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnReceive = (Button) findViewById(R.id.btnReceive);
        res = (ListView) findViewById(R.id.lv);
        txtAuthor = (EditText) findViewById(R.id.txAuthor);
        txtClient = (EditText) findViewById(R.id.txСlient);
        editText=(EditText) findViewById(R.id.editText);

        Intent intent = getIntent();
        author = intent.getStringExtra("author");
        txtAuthor.setText(author);
        client = txtClient.getText().toString();

   //     EditText author1 = (EditText) findViewById(R.layout.activity_main.name);
        // прописываем обработчик
        btnSend.setOnClickListener(this);
        btnReceive.setOnClickListener(this);
       // res.setOnClickListener(this);

        chatDBlocal = openOrCreateDatabase("chatDBlocal.db",
                Context.MODE_PRIVATE, null);
        chatDBlocal
                .execSQL("CREATE TABLE IF NOT EXISTS service (_id integer primary key autoincrement, author, client, data, text)");

    }

    @Override
    public void onClick(View v) {
        textSend= editText.getText().toString();
        switch (v.getId()) {
            case R.id.btnSend:
                if (!textSend.trim().equals("")) {

                    // кнопку сделаем неактивной
                    btnSend.setEnabled(false);

                    // если чтото есть - действуем!
                    insert_to_chat = new INSERTtoChat();
                    insert_to_chat.execute();

                } else {
                    // если ничего нет - нечего и писать
                    editText.setText("");
                }
                break;
            case R.id.btnReceive:
                upd_res = new UpdateReceiver();
                create_lv();
                break;
            default:
                break;
        }
    //    res.setText(result1);
    }

    public void create_lv() {
        //      lnk = SERVER_NAME + "/bd/service.php?action=select";
        lnk = SERVER_NAME + "/api/values";
        // создаем соединение ---------------------------------->
        try {
            Log.i("chat","+ --------------- ОТКРОЕМ СОЕДИНЕНИЕ");

            conn = (HttpURLConnection) new URL(lnk).openConnection();
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(150000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setDoInput(true);
            conn.connect();

        } catch (Exception e) {
            Log.i("chat", "+ ошибка: " + e.getMessage());
        }
        // получаем ответ ---------------------------------->
        try {
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String bfr_st = null;
            while ((bfr_st = br.readLine()) != null) {
                sb.append(bfr_st);
            }

            Log.i("chat", "+  - полный ответ сервера:\n"
                    + sb.toString());
            // сформируем ответ сервера в string
            // обрежем в полученном ответе все, что находится за "]"
            // это необходимо, т.к. json ответ приходит с мусором
            // и если этот мусор не убрать - будет невалидным
            ansver = sb.toString();
            ansver = ansver.substring(0, ansver.indexOf("]") + 1);

            is.close(); // закроем поток
            br.close(); // закроем буфер

        } catch (Exception e) {
            Log.i("chat", "+ ошибка: " + e.getMessage());
        } finally {
            conn.disconnect();
            Log.i("chat",
                    "+ --------------- ЗАКРОЕМ СОЕДИНЕНИЕ");
        }
        // запишем ответ в БД ---------------------------------->
        if (ansver != null && !ansver.trim().equals("")) {
            Log.i("chat", "+ ---------- ответ содержит JSON:");
            try {
                // ответ превратим в JSON массив
                JSONArray ja = new JSONArray(ansver);
                JSONObject jo;
                Integer i = 0;
                final ArrayList<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>() ;
                HashMap<String, String> hm;

                while (i < ja.length()) {
                    // разберем JSON массив построчно
                    jo = ja.getJSONObject(i);
                    Log.i("chat","=================>>> "+ jo.getString("author") + " | " + jo.getString("client")+ " | " + jo.getLong("data") + " | " + jo.getString("text"));
                    hm = new HashMap<>();
       /*                     hm.put("_id", jo.getString("_id"));
                            hm.put("author", jo.getString("author"));
                            hm.put("client", jo.getString("client"));
                            hm.put("text", jo.getString("text"));
                            hm.put("data", jo.getString("data"));
                            mList.add(hm); */
                    hm.put("name", jo.getString("name"));
                    hm.put("author", jo.getString("value"));
                    mList.add(hm);
                    // создадим новое сообщение
                           /* new_mess = new ContentValues();
                            new_mess.put("author", jo.getString("author"));
                            new_mess.put("client", jo.getString("client"));
                            new_mess.put("data", jo.getLong("data"));
                            new_mess.put("text", jo.getString("text"));*/
                    // запишем новое сообщение в БД
                    //   chatDBlocal.insert("chat", null, new_mess);
                    new_mess.clear();
                    i++;
                    // отправим броадкаст для ChatActivity
                    // если она открыта - она обновить ListView
                    //      sendBroadcast(new Intent("by.andreidanilevich.action.UPDATE_ListView"));
                }
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                        mList,  android.R.layout.simple_list_item_2, new String[] {"name", "value",
                        "","",""},
                        new int[] { R.id.ColMemberID, R.id.ColAuthor,
                                R.id.ColClient, R.id.ColData,
                                R.id.ColText});
                res.setAdapter(adapter);
                //   Intent intent = new Intent(this, row.class);
                //   intent.putExtra("client", client);
                //     startActivity(intent);
            } catch (Exception e) {
                // если ответ сервера не содержит валидный JSON
                Log.i("chat","+  ---------- ошибка ответа сервера:\n" + e.getMessage());
            }
        } else {
            // если ответ сервера пустой
            Log.i("chat","+ FoneService ---------- ответ не содержит JSON!");
        }
    }

    private class INSERTtoChat extends AsyncTask<Void, Void, Integer> {

        HttpURLConnection conn;
        Integer res;

        protected Integer doInBackground(Void... params) {

            try {
                // соберем линк для передачи новой строки
                String post_url = SERVER_NAME+ "/bd/service.php?action=insert&author="+ URLEncoder.encode(author, "UTF-8")+ "&client="+ URLEncoder.encode(client, "UTF-8")+ "&text="
                        + URLEncoder.encode(textSend.trim(),
                        "UTF-8");

                Log.i("chat",
                        "+ ChatActivity - отправляем на сервер новое сообщение: "
                                + textSend.trim());

                URL url = new URL(post_url);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(10000); // ждем 10сек
                conn.setRequestMethod("POST");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                conn.connect();

                res = conn.getResponseCode();
                Log.i("chat", "+ ChatActivity - ответ сервера (200 - все ОК): "
                        + res.toString());

            } catch (Exception e) {
                Log.i("chat",
                        "+ ChatActivity - ошибка соединения: " + e.getMessage());

            } finally {
                // закроем соединение
                conn.disconnect();
            }
            return res;
        }

        protected void onPostExecute(Integer result) {

            try {
                if (result == 200) {
                    Log.i("chat", "+ ChatActivity - сообщение успешно ушло.");
                    // сбросим набранный текст
                    editText.setText("");
                }
            } catch (Exception e) {
                Log.i("chat", "+ ChatActivity - ошибка передачи сообщения:\n"
                        + e.getMessage());
                Toast.makeText(getApplicationContext(),
                        "ошибка передачи сообщения", Toast.LENGTH_SHORT).show();
            } finally {
                // активируем кнопку
                btnSend.setEnabled(true);
            }
        }

    }
}
