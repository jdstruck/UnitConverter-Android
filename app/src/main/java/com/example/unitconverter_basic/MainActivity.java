package com.example.unitconverter_basic;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

enum Unit {
    Celsius,
    Fahrenheit;

    static Double celsiusToFahrenheit(Editable c) {
        return Double.parseDouble(c.toString()) * (9.0/5.0) + 32.0;
    }

    static Double fahrenheitToCelsius(Editable c) {
        return (Double.parseDouble(c.toString()) - 32.0) * (5.0/9.0);
    }
};

public class MainActivity extends AppCompatActivity {

    Unit fromType = Unit.Celsius;
    Unit toType = Unit.Fahrenheit;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {

            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_3:
            case KeyEvent.KEYCODE_4:
            case KeyEvent.KEYCODE_5:
            case KeyEvent.KEYCODE_6:
            case KeyEvent.KEYCODE_7:
            case KeyEvent.KEYCODE_8:
            case KeyEvent.KEYCODE_9:
                convert();
                break;
            default:
                return super.onKeyUp(keyCode, event);
        }
        return super.onKeyUp(keyCode, event);
    }

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
                convert();
            }
        });

        FloatingActionButton swap_button = findViewById(R.id.swap_button);
        swap_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapUnits();
            }
        });
    }

    private void convert() {
        Double num_out_dbl = 0.0;
        EditText num_in = findViewById(R.id.num_in_input);
        Editable numval = num_in.getText();
        EditText num_out = findViewById(R.id.num_out_input);

        if(this.fromType == Unit.Celsius) {
            switch(this.toType) {
                case Celsius:
                    num_out_dbl = Double.parseDouble(numval.toString());
                    break;
                case Fahrenheit:
                    num_out_dbl = Unit.celsiusToFahrenheit(numval);
                    break;
            }
        }
        if(this.fromType == Unit.Fahrenheit) {
            switch(this.toType) {
                case Celsius:
                    num_out_dbl = Unit.fahrenheitToCelsius(numval);
                    break;
                case Fahrenheit:
                    num_out_dbl = Double.parseDouble(numval.toString());
                    break;
            }
        }
        num_out.setText(String.format("%.2f", num_out_dbl));
    }

    private void swapUnits() {
        Unit tmp = this.fromType;
        this.fromType = this.toType;
        this.toType = tmp;

        TextView num_in_label = findViewById(R.id.num_in_label);
        num_in_label.setText(this.fromType.name());

        TextView num_out_label = findViewById(R.id.num_out_label);
        num_out_label.setText(this.toType.name());
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
