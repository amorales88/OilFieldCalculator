package myapps.oilfieldcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class Converter extends Activity {

    Spinner spinner;
    Spinner spinner2;
    EditText editText;
    Spinner magSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        magSpinner = (Spinner) findViewById(R.id.magnitudeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.magnitude_array, android.R.layout.simple_spinner_dropdown_item);
        magSpinner.setAdapter(adapter);

        // TODO: Change Spinners values depending on choice (volume/distance)
        // Do I still need to set them here or does it get preset automagically?
        // setValuesToSpinners(R.id.spinner, magSpinner.getSelectedItemId() );
        //setValuesToSpinners(R.id.spinner2);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateAndConvert();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Next line could get the chosen item, but not needed right now
                //String str = (String) adapterView.getItemAtPosition(i);
                updateAndConvert();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setOnItemSelectedListener(spinner.getOnItemSelectedListener());
        editText.setSelection(0,1);

        AdapterView.OnItemSelectedListener magListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int arrayID = 0;
                if (magSpinner.getSelectedItem().toString().equals("Distance")) {
                    arrayID = R.array.distance_array;
                }
                else if(magSpinner.getSelectedItem().toString().equals("Volume")) {
                    arrayID = R.array.volume_array;
                }
                else if(magSpinner.getSelectedItem().toString().equals("Quick Conv.")) {
                    arrayID = R.array.quick_conv_array;
                }

                setValuesToSpinners(spinner.getId(),arrayID);
                if(arrayID != R.array.quick_conv_array)
                   setValuesToSpinners(spinner2.getId(),arrayID);

                // Disable this if it's quick conv.
                spinner2.setEnabled(arrayID != R.array.quick_conv_array);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        magSpinner.setOnItemSelectedListener(magListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_converter, menu);
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

    private void setValuesToSpinners(int spinnerId, int arrayID) {
        Spinner spinner = (Spinner) findViewById(spinnerId);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayID, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void updateAndConvert() {

        if(editText.getText().toString().equals("")) {
            editText.setText("0");
            editText.setSelection(0,1);
        }

        // First, convert whatever was input to Meters. (I thought having it in a unique measure would help)

        float baseValue = 0;
        if(editText.getText().toString().equals(".")) {
            baseValue = 0;
        }
        else {
            baseValue = Float.parseFloat(editText.getText().toString());
        }

        double convFactor = 0;
        double result = 0;
        String convertFrom = spinner.getSelectedItem().toString();
        String convertTo = spinner2.getSelectedItem().toString();
        if(magSpinner.getSelectedItem().toString().equals("Distance")) {
            if (convertFrom.equals("m")) {
                convFactor = 1;
            } else if (convertFrom.equals("ft")) {
                convFactor = 0.3048;
            } else if (convertFrom.equals("mi")) {
                convFactor = 1609.34;
            } else if (convertFrom.equals("km")) {
                convFactor = 1000;
            }

            result = baseValue * convFactor;
            // Now all distances are in meter
            if (convertTo.equals("m")) {
                convFactor = 1;
            } else if (convertTo.equals("ft")) {
                convFactor = 0.3048;
            } else if (convertTo.equals("mi")) {
                convFactor = 1609.34;
            } else if (convertTo.equals("km")) {
                convFactor = 1000;
            }
            result = result / convFactor;
        }
        else if(magSpinner.getSelectedItem().toString().equals("Volume")) {
            // TODO: Implement volume conversion
        }
        else if(magSpinner.getSelectedItem().toString().equals("Quick Conv.")) {
            if(convertFrom.equals("Barrels to Gallons")) {
                result = baseValue * 42;
            }
            else if(convertFrom.equals("Gallons to Barrels")) {
                result = baseValue / 42;
            }
            else if(convertFrom.equals("Lbs to Metric Tons")) {
                result = baseValue / 2204.62;
            }
            else if(convertFrom.equals("Lbs to Long Tons")) {
                result = baseValue / 2240.00;
            }
            else if(convertFrom.equals("Lbs to Short Tons")) {
                result = baseValue / 2000.00;
            }
            else if(convertFrom.equals("Lbs to Kilos")) {
                result = baseValue / 2.20462;
            }
            else if(convertFrom.equals("m3 to Barrels")) {
                result = baseValue * 6.28981;
            }
            else if(convertFrom.equals("m3 to Gallons")) {
                result = baseValue * 264.172;
            }
            else if(convertFrom.equals("Lbs/Gal to Liter Weight")) {
                result = baseValue * 0.1198264;
            }
            else if(convertFrom.equals("Gallons to m3")) {
                result = baseValue * 0.00378;
            }
            else if(convertFrom.equals("Celsius to Fahrenheit")) {
                result = (baseValue * 1.8) + 32;
            }
            else if(convertFrom.equals("Bar to PSI")) {
                result = baseValue * 14.504;
            }
            else if(convertFrom.equals("Lbs/Gal to Specific Gravity")) {
                result = baseValue / 8.32828;
            }
            else if(convertFrom.equals("Gallons to Liters")) {
                result = baseValue * 3.785412;
            }

        }

        TextView tvResult = (TextView) findViewById(R.id.textView2);
        tvResult.setText(Double.toString(result));
        // If number was keyed just after 0, remove leading zero from edit
        if((String.valueOf(editText.getText().charAt(0)).equals("0"))&&(editText.getText().length() > 1)&&!(String.valueOf(editText.getText().charAt(0)).equals("."))) {
            editText.setText(String.valueOf(editText.getText().subSequence(1, editText.getText().length())));
            editText.setSelection(editText.getText().length());

        }
    }

}
