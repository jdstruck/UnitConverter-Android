package com.example.unitconverter_basic;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String inputUnit;
    private String inputCategory;
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
    }

    private void configureInputUnitSpinner(String[] unitsArray) {

        inputUnitSpinnerAdapterList = new ArrayList<>(Arrays.asList(unitsArray));

        inputUnitSpinnerArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, inputUnitSpinnerAdapterList);

        inputUnitSpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputUnitSpinner.setAdapter(inputUnitSpinnerArrayAdapter);
    }

    private void configureUnitCategorySpinnerListener() {
        this.unitCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                TextView spinnerTextView = (TextView) selectedItemView;
                String selectedText = spinnerTextView.getText().toString();
                TextView tv = (TextView) unitCategorySpinner.getSelectedView();
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(20);
                inputField.setText("");
                inputCategory = selectedText;

                if (selectedText.equals("Temperature")) {
                    inflateOutputUnitFields(Unit.temperatureUnits);
                    resetInputUnitsSpinner(Unit.temperatureUnits);
                    inputField.setHint("Start typing!");
                } else if (selectedText.equals("Length")) {
                    inflateOutputUnitFields(Unit.lengthUnits);
                    resetInputUnitsSpinner(Unit.lengthUnits);
                    inputField.setHint("TODO: Length formulas...");
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
        this.inputField.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputField.length() == 2) {
                    if (s.charAt(0) == '0') {
                        inputField.setText(s.subSequence(1, s.length()).toString());
                        inputField.setSelection(inputField.length());
                    } else if (s.charAt(0) == '-' && (s.charAt(1) == '-' || s.charAt(1) == '0')) {
                        inputField.setText(s.subSequence(1, s.length()).toString());
                        inputField.setSelection(inputField.length());
                    }
                }
                convertUnits();
            }
        });
    }

    private void addField(View v, String unitTypeString) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        final View rowView = inflater.inflate(R.layout.field, null);
        EditText editText = (EditText)rowView.findViewById(R.id.output_item_field_template);
        TextView textView = (TextView)rowView.findViewById(R.id.output_item_label_template);
        editText.setTag(unitTypeString);
        textView.setText(unitTypeString);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }

    private void convertUnits() {
        if (inputField.length() == 0 || inputField.getText().toString().equals("-") || inputField.getText().toString().equals("+")) {
            clearOutputFields();
            return;
        }

        Editable inputFieldEditable = this.inputField.getText();
        Double inputFieldValue = Double.parseDouble(inputFieldEditable.toString());
        Double outputValue = 0.0;

        for (int i = 0; i < parentLinearLayout.getChildCount(); i++) {
            LinearLayout childLinearLayout = (LinearLayout) parentLinearLayout.getChildAt(i);
            EditText childElemEditText;
            for (int j = 0; j < childLinearLayout.getChildCount(); j++) {
                View childElem = childLinearLayout.getChildAt(j);
                if (childElem instanceof EditText) {
                    childElemEditText = (EditText) childElem;
                    String outputUnit = (String) childElem.getTag();
                    outputValue = Unit.convert(inputCategory, inputUnit, outputUnit, inputFieldValue);
                    childElemEditText.setText(Double.toString(outputValue));
                }
            }
        }
    }

    private void clearOutputFields() {
        ViewGroup group = (ViewGroup)parentLinearLayout;
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            LinearLayout outputItemContainer = (LinearLayout) group.getChildAt(i);
            View outputItemField = outputItemContainer.findViewById(R.id.output_item_field_template);
            if (outputItemField instanceof EditText) {
                ((EditText)outputItemField).setText("");
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
}