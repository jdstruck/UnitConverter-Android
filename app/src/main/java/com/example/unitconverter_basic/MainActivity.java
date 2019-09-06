package com.example.unitconverter_basic;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /*
    : try catch for empty EditText num_in field
    : remove Convert button
    : remove Swap button
    : IN PROGRESS dropdown for unit on right of input
    TODO: buttons/radio on top to select other unit categories
    : programmatically add conversion output for each type
    : handle minus sign when no numbers are present
    */

    Unit fromType = Unit.Celsius;
    Unit toType = Unit.Fahrenheit;

    private LinearLayout parentLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);

        EditText num_in = findViewById(R.id.num_in_input);
        num_in.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println(s);
                convert();
            }
        });

        List<String> spinnerArray =  new ArrayList<String>();
        for (Unit unit: Unit.values()) {
            spinnerArray.add(unit.name());
            addField(parentLinearLayout, unit.name());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinner);
        sItems.setAdapter(adapter);

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                TextView spinnerTextView = (TextView) selectedItemView;
                String selectedText = spinnerTextView.getText().toString();
                onSpinnerChange(selectedText);
                return;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                return;
            }

        });

    }
    public void addField(View v, String s) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);
        EditText editText = (EditText)rowView.findViewById(R.id.num_out_template);
        TextView textView = (TextView)rowView.findViewById(R.id.num_out_label_template);
        editText.setTag(s);
        textView.setText(s);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }

    private void onSpinnerChange(String selectedText) {

        switch(selectedText) {
            case "Celsius": {
                this.fromType = Unit.Celsius;
                break;
            }
            case "Fahrenheit": {
                this.fromType = Unit.Fahrenheit;
                break;
            }
            case "Kelvin": {
                this.fromType = Unit.Kelvin;
                break;
            }
        }
        convert();
    }
    private void convert() {
        Double num_out_dbl = 0.0;
        EditText num_in = findViewById(R.id.num_in_input);
        Editable numval = num_in.getText();



        for (int i = 0; i < parentLinearLayout.getChildCount(); i++) {
            LinearLayout childLinearLayout = (LinearLayout) parentLinearLayout.getChildAt(i);
            TextView childElemTextView;
            EditText childElemEditText;
            for (int j = 0; j < childLinearLayout.getChildCount(); j++) {
                View childElem = childLinearLayout.getChildAt(j);
                if (childElem instanceof EditText) {
                    childElemEditText = (EditText) childElem;
                    String tag = (String) childElem.getTag();
                    System.out.println(tag);
                    if(num_in.length() == 0 || numval.toString().equals("-")) {
                        childElemEditText.setText("");
                        break;
                    }
                    if(this.fromType == Unit.Celsius) {
                        switch(tag) {
                            case "Celsius":
                                num_out_dbl = Double.parseDouble(numval.toString());
                                break;
                            case "Fahrenheit":
                                num_out_dbl = Unit.celsiusToFahrenheit(numval);
                                break;
                            case "Kelvin":
                                num_out_dbl = Unit.celsiusToKelvin(numval);
                                break;
                        }
                    }
                    if(this.fromType == Unit.Fahrenheit) {
                        switch(tag) {
                            case "Fahrenheit":
                                num_out_dbl = Double.parseDouble(numval.toString());
                                break;
                            case "Celsius":
                                num_out_dbl = Unit.fahrenheitToCelsius(numval);
                                break;
                            case "Kelvin":
                                num_out_dbl = Unit.fahrenheitToKelvin(numval);
                                break;

                        }
                    }
                    if(this.fromType == Unit.Kelvin) {
                        switch(tag) {
                            case "Kelvin":
                                num_out_dbl = Double.parseDouble(numval.toString());
                                break;
                            case "Celsius":
                                num_out_dbl = Unit.kelvinToCelsius(numval);
                                break;
                            case "Fahrenheit":
                                num_out_dbl = Unit.kelvinToFahrenheit(numval);
                                break;

                        }
                    }
                    childElemEditText.setText(Double.toString(num_out_dbl)); //String.format("%.4f", num_out_dbl));
                }
            }
        }
    }

//    private void swapUnits() {
//        Unit tmp = this.fromType;
//        this.fromType = this.toType;
//        this.toType = tmp;
//
//        TextView num_in_label = findViewById(R.id.num_in_label);
//        num_in_label.setText(this.fromType.name());
//
//        TextView num_out_label = findViewById(R.id.num_out_label);
//        num_out_label.setText(this.toType.name());
//    }

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
    private void alert(String s) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(s);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
enum Unit {
    Celsius,
    Fahrenheit,
    Kelvin;

    static Double celsiusToFahrenheit(Editable c) {
        return Double.parseDouble(c.toString()) * (9.0/5.0) + 32.0;
    }

    static Double celsiusToKelvin(Editable c) {
        return Double.parseDouble(c.toString()) + 273.15;
    }

    static Double fahrenheitToCelsius(Editable c) {
        return (Double.parseDouble(c.toString()) - 32.0) * (5.0/9.0);
    }

    static Double fahrenheitToKelvin(Editable c) {
        return (Double.parseDouble(c.toString()) - 32.0) / 1.8 + 273.15;
    }

    static Double kelvinToCelsius(Editable c) {
        return Double.parseDouble(c.toString()) - 273.15;
    }

    static Double kelvinToFahrenheit(Editable c) {
        return Double.parseDouble(c.toString()) * 1.8 - 459.67;
    }
};
