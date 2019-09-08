package com.example.unitconverter_basic;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //TODO: fix initial 0 bug

    private String inputUnit;
    private double inputValue;

    private EditText inputField;
    private LinearLayout parentLinearLayout;
    private List<String> inputUnitSpinnerAdapterList;
    private Spinner unitCategorySpinner;
    private Spinner inputUnitSpinner;
    private ArrayAdapter<String> inputUnitSpinnerArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.inputField = findViewById(R.id.input_field);
        this.parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);
        this.unitCategorySpinner = (Spinner) findViewById(R.id.unit_category_spinner);
        this.inputUnitSpinner = (Spinner) findViewById(R.id.input_unit_spinner);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        initializeApp();
    }

    public void initializeApp() {

        configureUnitCategorySpinner(Unit.unitCategories);
        configureUnitCategorySpinnerListener();
        configureInputUnitSpinner(Unit.temperatureUnits);
        configureInputUnitSpinnerListener();
        configureInputFieldTextListener();

    }

    private void configureUnitCategorySpinner(String[] unitsArray) {

        List<String> unitCategorySpinnerAdapterList = new ArrayList<>(Arrays.asList(unitsArray));

        ArrayAdapter<String> unitCategorySpinnerArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, unitCategorySpinnerAdapterList);

        unitCategorySpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitCategorySpinner.setAdapter(unitCategorySpinnerArrayAdapter);

        //View v = unitCategorySpinner.getSelectedView();
        //((TextView)unitCategorySpinner.getSelectedView()).setTextColor(Color.WHITE);
    }

    private void configureInputUnitSpinner(String[] unitsArray) {

        inputUnitSpinnerAdapterList = new ArrayList<>(Arrays.asList(unitsArray));

        inputUnitSpinnerArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, inputUnitSpinnerAdapterList);

        inputUnitSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputUnitSpinner.setAdapter(inputUnitSpinnerArrayAdapter);
    }

    private void configureUnitCategorySpinnerListener() {
        unitCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                TextView spinnerTextView = (TextView) selectedItemView;
                String selectedText = spinnerTextView.getText().toString();

                TextView tv = (TextView) unitCategorySpinner.getSelectedView();
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(20);

                inputField.setText("");

                if (selectedText.equals("Temperature")) {
                    inflateOutputUnitFields(Unit.temperatureUnits);
                    resetInputUnitsSpinner(Unit.temperatureUnits);
                } else if (selectedText.equals("Length")) {
                    inflateOutputUnitFields(Unit.lengthUnits);
                    resetInputUnitsSpinner(Unit.lengthUnits);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

    private void configureInputUnitSpinnerListener() {
        inputUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                TextView spinnerTextView = (TextView) selectedItemView;
                String selectedText = spinnerTextView.getText().toString();

                TextView tv = (TextView) inputUnitSpinner.getSelectedView();

                //tv.setTextColor(Color.WHITE);
                tv.setTextSize(20);

                onInputSpinnerChange(selectedText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });
    }

    private void resetInputUnitsSpinner(String[] unitsList) {

        inputUnitSpinnerAdapterList.clear();

        inputUnitSpinnerAdapterList.addAll(Arrays.asList(unitsList));

        inputUnitSpinnerArrayAdapter.notifyDataSetChanged();
    }

    private void inflateOutputUnitFields(String[] unitsArray) {

        this.parentLinearLayout.removeAllViews();
        for (String unit: unitsArray) {
            addField(this.parentLinearLayout, unit);
        }
    }

    private void onInputSpinnerChange(String selectedText) {

        this.inputUnit = selectedText;
        convertUnits();
    }

    private void configureInputFieldTextListener() {
        // Enable TextWatcher on input unit field
        this.inputField.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("beforeTextChanged:\n\tCharSequence s: " + s +
                        "\n\tint start: " + start +
                        "\n\tint count: " + count +
                        "\n\tint after: " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("onTextChanged:\n\tCharSequence s: " + s +
                        "\n\tint start: " + start +
                        "\n\tint before: " + before +
                        "\n\tint count: " + count);
                convertUnits();
            }
        });
    }

    private void addField(View v, String unitTypeString) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        final View rowView = inflater.inflate(R.layout.field, null);
        EditText editText = (EditText)rowView.findViewById(R.id.num_out_template);
        TextView textView = (TextView)rowView.findViewById(R.id.num_out_label_template);
        editText.setTag(unitTypeString);
        textView.setText(unitTypeString);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }

    private void convertUnits() {
        Editable input_field_value = this.inputField.getText();

        String sInput_field_value = input_field_value.toString();

        Double num_out_dbl = 0.0;

        for (int i = 0; i < parentLinearLayout.getChildCount(); i++) {
            LinearLayout childLinearLayout = (LinearLayout) parentLinearLayout.getChildAt(i);
            EditText childElemEditText;
            for (int j = 0; j < childLinearLayout.getChildCount(); j++) {
                View childElem = childLinearLayout.getChildAt(j);
                if (childElem instanceof EditText) {
                    childElemEditText = (EditText) childElem;
                    String tag = (String) childElem.getTag();
                    System.out.println(tag);

                    // Clear fields and break if nothing in input or only minus symbol
                    if(inputField.length() == 0 || input_field_value.toString().equals("-")) {
                        childElemEditText.setText("");
                        break;
                    }

                    if(this.inputUnit.equals("Celsius")) {
                        switch(tag) {
                            case "Celsius":
                                num_out_dbl = Double.parseDouble(input_field_value.toString());
                                break;
                            case "Fahrenheit":
                                num_out_dbl = Unit.celsiusToFahrenheit(input_field_value);
                                break;
                            case "Kelvin":
                                num_out_dbl = Unit.celsiusToKelvin(input_field_value);
                                break;
                        }
                    }
                    if(this.inputUnit.equals("Fahrenheit")) {
                        switch(tag) {
                            case "Fahrenheit":
                                num_out_dbl = Double.parseDouble(input_field_value.toString());
                                break;
                            case "Celsius":
                                num_out_dbl = Unit.fahrenheitToCelsius(input_field_value);
                                break;
                            case "Kelvin":
                                num_out_dbl = Unit.fahrenheitToKelvin(input_field_value);
                                break;

                        }
                    }
                    if(this.inputUnit.equals("Kelvin")) {
                        switch(tag) {
                            case "Kelvin":
                                num_out_dbl = Double.parseDouble(input_field_value.toString());
                                break;
                            case "Celsius":
                                num_out_dbl = Unit.kelvinToCelsius(input_field_value);
                                break;
                            case "Fahrenheit":
                                num_out_dbl = Unit.kelvinToFahrenheit(input_field_value);
                                break;

                        }
                    }
                    childElemEditText.setText(Double.toString(num_out_dbl)); //String.format("%.4f", num_out_dbl));
                }
            }
        }
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