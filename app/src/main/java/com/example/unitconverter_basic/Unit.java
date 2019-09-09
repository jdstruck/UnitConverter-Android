package com.example.unitconverter_basic;

class Unit {

    static String[] temperatureUnits = {"Celsius", "Fahrenheit", "Kelvin"};
    static String[] lengthUnits = {"Centimeters", "Inches", "Feet", "Yards", "Meters", "Kilometers", "Miles", "Lightyears"};
    static String[] unitCategories = {"Temperature", "Length"};

    static Double convert(String inputCategory, String inputUnit, String outputUnit, Double inputFieldValue) {
        Double outputValue = 0.0;
        switch (inputCategory) {
            case "Temperature":
                if(inputUnit.equals("Celsius")) {
                    switch(outputUnit) {
                        case "Celsius":
                            outputValue = inputFieldValue;
                            break;
                        case "Fahrenheit":
                            outputValue = Unit.celsiusToFahrenheit(inputFieldValue);
                            break;
                        case "Kelvin":
                            outputValue = Unit.celsiusToKelvin(inputFieldValue);
                            break;
                    }
                }
                if(inputUnit.equals("Fahrenheit")) {
                    switch(outputUnit) {
                        case "Fahrenheit":
                            outputValue = Double.parseDouble(inputFieldValue.toString());
                            break;
                        case "Celsius":
                            outputValue = Unit.fahrenheitToCelsius(inputFieldValue);
                            break;
                        case "Kelvin":
                            outputValue = Unit.fahrenheitToKelvin(inputFieldValue);
                            break;
                    }
                }
                if(inputUnit.equals("Kelvin")) {
                    switch(outputUnit) {
                        case "Kelvin":
                            outputValue = Double.parseDouble(inputFieldValue.toString());
                            break;
                        case "Celsius":
                            outputValue = Unit.kelvinToCelsius(inputFieldValue);
                            break;
                        case "Fahrenheit":
                            outputValue = Unit.kelvinToFahrenheit(inputFieldValue);
                            break;

                    }
                }
                break;
            case "Length":

                break;
            default:
        }
        return outputValue;
    }

//    static Double convertLengthMetric(String inputUnit, String outputUnit, Double c) {
//
//        Double outputValue;
//
//
//        return outputValue;
//    }

    static Double celsiusToFahrenheit(Double c) {
        return Double.parseDouble(c.toString()) * (9.0/5.0) + 32.0;
    }

    static Double celsiusToKelvin(Double c) {
        return Double.parseDouble(c.toString()) + 273.15;
    }

    static Double fahrenheitToCelsius(Double c) {
        return (Double.parseDouble(c.toString()) - 32.0) * (5.0/9.0);
    }

    static Double fahrenheitToKelvin(Double c) {
        return (Double.parseDouble(c.toString()) - 32.0) / 1.8 + 273.15;
    }

    static Double kelvinToCelsius(Double c) {
        return Double.parseDouble(c.toString()) - 273.15;
    }

    static Double kelvinToFahrenheit(Double c) {
        return Double.parseDouble(c.toString()) * 1.8 - 459.67;
    }

    //static Double convertLengthMetric();
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