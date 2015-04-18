package myapps.oilfieldcalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class FractionCalculator extends ActionBarActivity {

    EditText feetNum1,feetNum2;
    EditText inchNum1,inchNum2;
    EditText numeratorNum1,numeratorNum2;
    EditText denomNum1,denomNum2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_calculator);
        Spinner operatorSpinner = (Spinner) findViewById(R.id.opSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.operator_array, android.R.layout.simple_spinner_dropdown_item);
        operatorSpinner.setAdapter(adapter);

        feetNum1 = (EditText) findViewById(R.id.feetNum1);
        feetNum2 = (EditText) findViewById(R.id.feetNum2);
        inchNum1 = (EditText) findViewById(R.id.inchNum1);
        inchNum2 = (EditText) findViewById(R.id.inchNum2);
        numeratorNum1 = (EditText) findViewById(R.id.numeratorNum1);
        numeratorNum2 = (EditText) findViewById(R.id.numeratorNum2);
        denomNum1 = (EditText) findViewById(R.id.denomNum1);
        denomNum2 = (EditText) findViewById(R.id.denomNum2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fraction_calculator, menu);
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

    //This method is called by the button internally
    public void calculateResult(View v){
        Toast.makeText(getApplicationContext(),"Button Clicked",Toast.LENGTH_SHORT).show();
    }
}
