package myapps.oilfieldcalculator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    EditText upperTemp;
    EditText middleTemp;
    EditText lowerTemp;
    TextView AvgTempNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        upperTemp = (EditText) findViewById(R.id.TempUpper);
        middleTemp = (EditText) findViewById(R.id.TempMiddle);
        lowerTemp = (EditText) findViewById(R.id.TempLower);
        AvgTempNum = (TextView) findViewById(R.id.AvgTempNum);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AvgTempNum.setText(String.valueOf(calculateAverageTemp()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        Log.i("Murilo1", "OilField");
        upperTemp.addTextChangedListener(textWatcher);
        middleTemp.addTextChangedListener(textWatcher);
        lowerTemp.addTextChangedListener(textWatcher);

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

        if (id == R.id.action_converter) {
            Intent converterIntent = new Intent(this, Converter.class);
            startActivity(converterIntent);
        }

        return super.onOptionsItemSelected(item);
    }


    public double calculateAverageTemp(){

        double result = 0.0;
        //Check to see if field is blank, if so it will cause an error

        result = result + ((upperTemp.getText().toString().equals("") || upperTemp.getText().toString().equals(".")) ? 0 : Double.valueOf(upperTemp.getText().toString()));

        result = result + ((middleTemp.getText().toString().equals("") || middleTemp.getText().toString().equals("."))? 0 : Double.valueOf(middleTemp.getText().toString()));

        result = result + ((lowerTemp.getText().toString().equals("") || lowerTemp.getText().toString().equals("."))? 0 : Double.valueOf(lowerTemp.getText().toString()));
        return (result/3);

    }
}
