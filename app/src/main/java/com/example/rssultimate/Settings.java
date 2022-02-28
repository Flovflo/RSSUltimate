package com.example.rssultimate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity implements View.OnClickListener{

    private EditText txtUrl =null;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        txtUrl = findViewById(R.id.editTextTextMultiLine);
        button = findViewById((R.id.button));
        button.setOnClickListener(this);


    }

    @Override
    public void onClick (View view) {
        String url = txtUrl.getText().toString();
        url = "https://www.lemonde.fr/rss/une.xml";
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("edittext", url);
        startActivity(intent);
        finish();
    }

}
