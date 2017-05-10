package com.aplana.ivan.p0041basicviews;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView name;
    private Button button;
    private TextView editText;
    private String nick;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (TextView) findViewById(R.id.name);
        nick= name.getText().toString();
        button = (Button) findViewById(R.id.button);
      //  editText = (TextView) findViewById(R.id.editText);
        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Выполнен вход", Toast.LENGTH_LONG).show();
      //  addr1.setText("Нажата кнопка Send");  // TODO Auto-generated method stub
        Intent intent = new Intent(this, ActivityTwo.class);
        intent.putExtra("author", nick);
     //   intent.putExtra("client", client);
        startActivity(intent);
    }
}
