package com.example.unitconverter_basic;

import android.text.Editable;

public class Unit {

    static String inputUnit;
    static double inputValue;

    private String outputUnit;


    static String[] temperatureUnits = {"Celsius", "Fahrenheit", "Kelvin"};
    static String[] lengthUnits = {"Centimeters", "Inches", "Feet", "Yards", "Meters", "Kilometers", "Miles", "Lightyears"};
    static String[] unitCategories = {"Temperature", "Length"};

//    static String[] temperatureUnits = {"Celsius", "Fahrenheit", "Kelvin"};
//    static String[] lengthUnits = {"Centimeters", "Inches", "Feet", "Yards", "Meters", "Kilometers", "Miles", "Lightyears"};
//    static String[] unitCategories = {"Temperature", "Length"};

    public Unit(String inputUnit, String outputUnit, double inputValue) {
        this.inputUnit = inputUnit;
        this.inputValue = inputValue;

        this.outputUnit = outputUnit;

    }

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