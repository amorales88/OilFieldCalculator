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
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);

        spinner = (Spinner) findViewById(R.id.spinner);
        editText = (EditText) findViewById(R.id.editText);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.quick_conv_array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


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

        editText.setSelection(0,1);

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

        float baseValue;
        if(editText.getText().toString().equals(".")) {
            baseValue = 0;
        }
        else {
            baseValue = Float.parseFloat(editText.getText().toString());
        }

        double convFactor = 0;
        double result = 0;
        String convertFrom = spinner.getSelectedItem().toString();



            switch (convertFrom) {
                case "Barrels to Gallons":
                    result = baseValue * 42;
                    break;
                case "Gallons to Barrels":
                    result = baseValue / 42;
                    break;
                case "Lbs to Metric Tons":
                    result = baseValue / 2204.62;
                    break;
                case "Lbs to Long Tons":
                    result = baseValue / 2240.00;
                    break;
                case "Lbs to Short Tons":
                    result = baseValue / 2000.00;
                    break;
                case "Lbs to Kilos":
                    result = baseValue / 2.20462;
                    break;
                case "m3 to Barrels":
                    result = baseValue * 6.28981;
                    break;
                case "m3 to Gallons":
                    result = baseValue * 264.172;
                    break;
                case "Lbs/Gal to Liter Weight":
                    result = baseValue * 0.1198264;
                    break;
                case "Gallons to m3":
                    result = baseValue * 0.00378;
                    break;
                case "Celsius to Fahrenheit":
                    result = (baseValue * 1.8) + 32;
                    break;
                case "Bar to PSI":
                    result = baseValue * 14.504;
                    break;
                case "Lbs/Gal to Specific Gravity":
                    result = baseValue / 8.32828;
                    break;
                case "Gallons to Liters":
                    result = baseValue * 3.785412;
                    break;
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
