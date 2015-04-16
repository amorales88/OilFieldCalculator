package myapps.oilfieldcalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    EditText upperTemp = (EditText) findViewById(R.id.TempUpper);
    EditText middleTemp = (EditText) findViewById(R.id.TempMiddle);
    EditText lowerTemp = (EditText) findViewById(R.id.TempLower);
    TextView AvgTempNum = (TextView) findViewById(R.id.AvgTempNum);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        upperTemp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                AvgTempNum.setText(Double.toString(calculateAverageTemp()));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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


    public double calculateAverageTemp(){
        double result = 0.0;
        //Check to see if field is blank, if so it will cause an error
        result =+ (upperTemp.getText().toString().matches("")? 0 : Double.valueOf(upperTemp.getText().toString()));
        result =+ (middleTemp.getText().toString().matches("")? 0 : Double.valueOf(middleTemp.getText().toString()));
        result =+ (lowerTemp.getText().toString().matches("")? 0 : Double.valueOf(lowerTemp.getText().toString()));
        return (result/3);
    }
}
