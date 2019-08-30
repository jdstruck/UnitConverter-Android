package com.example.unitconverter_basic;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    enum ConvertType {
        CELSIUS_TO_FAHRENHEIT,
        FAHRENHEIT_TO_CELSIUS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button convert_button = findViewById(R.id.convert_button);
        convert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText num_in = findViewById(R.id.num_in_input);
                Editable numval = num_in.getText();

                EditText num_out = findViewById(R.id.num_out_input);
                num_out.setText(Double.toString(celsiusToFahrenheit(numval)));
            }
        });

        FloatingActionButton swap_button = findViewById(R.id.swap_button);
        swap_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText num_in = findViewById(R.id.num_in_input);
                Editable numval = num_in.getText();

                EditText num_out = findViewById(R.id.num_out_input);
                num_out.setText(Double.toString(celsiusToFahrenheit(numval)));
            }
        });
    }

    private double celsiusToFahrenheit(Editable c) {
        return (Double.parseDouble(c.toString()) * (9.0/5.0)) + 32.0;
    }

    private double fahrenheitToCelsius(Editable c) {
        return (Double.parseDouble(c.toString()) - 32.0) * (5.0/9.0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
