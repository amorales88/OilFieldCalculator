package myapps.oilfieldcalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class FractionCalculator extends ActionBarActivity {

    EditText feetNum1,feetNum2;
    EditText inchNum1,inchNum2;
    EditText numeratorNum1,numeratorNum2;
    EditText denomNum1,denomNum2;
    TextView result;
    Spinner operatorSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_calculator);
        operatorSpinner = (Spinner) findViewById(R.id.opSpinner);
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
        result = (TextView) findViewById(R.id.result);

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
        //int[] fraction = reduceFraction(Integer.valueOf(numeratorNum1.getText().toString()), Integer.valueOf(denomNum1.getText().toString()));
        //result.setText(fraction[0] + "/" + fraction[1] + "/" + fraction[2]);
        //If the value is blank or '.', assign 0
        int numerator1 = (numeratorNum1.getText().toString().equals("") || numeratorNum1.getText().toString().equals(".")) ? 0 : Integer.valueOf(numeratorNum1.getText().toString());
        int numerator2 = (numeratorNum2.getText().toString().equals("") || numeratorNum2.getText().toString().equals(".")) ? 0 : Integer.valueOf(numeratorNum2.getText().toString());
        int denom1 = (denomNum1.getText().toString().equals("") || denomNum1.getText().toString().equals(".")) ? 0 : Integer.valueOf(denomNum1.getText().toString());
        int denom2 = (denomNum2.getText().toString().equals("") || denomNum2.getText().toString().equals(".")) ? 0 : Integer.valueOf(denomNum2.getText().toString());
        int feetNumber1 = (feetNum1.getText().toString().equals("") || feetNum1.getText().toString().equals(".")) ? 0 : Integer.valueOf(feetNum1.getText().toString());
        int feetNumber2 = (feetNum2.getText().toString().equals("") || feetNum2.getText().toString().equals(".")) ? 0 : Integer.valueOf(feetNum2.getText().toString());
        int inchNumber1 = (inchNum1.getText().toString().equals("") || inchNum1.getText().toString().equals(".")) ? 0 : Integer.valueOf(inchNum1.getText().toString());
        int inchNumber2 = (inchNum2.getText().toString().equals("") || inchNum2.getText().toString().equals(".")) ? 0 : Integer.valueOf(inchNum2.getText().toString());
        int totalInches = 0;
        int totalFeet = 0;

        //Return value from reduceFraction
        int[] newFraction;


        String operation = operatorSpinner.getSelectedItem().toString();
        if(operation.equals("+")){

        }
        else if(operation.equals("-")){

        }
        else if(operation.equals("X")) {
            //Converting to a faction
            while (feetNumber1 > 0) {
                inchNumber1 = inchNumber1 + 12;
                feetNumber1--;
            }
            while (feetNumber2 > 0) {
                inchNumber2 = inchNumber2 + 12;
                feetNumber2--;
            }
            if (inchNumber1 > 0) {
                numerator1 = numerator1 + (inchNumber1 * denom1);
            }
            if (inchNumber2 > 0) {
                numerator2 = numerator2 + (inchNumber2 * denom2);
            }

            //Multiply the fraction and get in lowest term
            newFraction = reduceFraction((numerator1 * numerator2), (denom1 * denom2));

            //Means that a 0 was entered for a denomintaor.
            if (newFraction[0] == 0 && newFraction[1] == 0 && newFraction[2] == 0) {
                if(denom1 == 0 && denom2 == 0){
                    totalInches = inchNumber1 * inchNumber2;

                    while(totalInches >= 12){
                        totalFeet++;
                        totalInches = totalInches - 12;
                    }

                    if(totalFeet > 0){
                        result.setText(totalFeet + "ft. " + totalInches + " " + "in.");
                    }
                    else{
                        result.setText(totalInches + "in.");
                    }
                }
                else if(denom1 == 0){
                    newFraction = reduceFraction( (inchNumber1*denom2 * numerator2),(denom2 * denom2));
                    while (newFraction[0] >= 12) {
                        totalFeet++;
                        newFraction[0] = newFraction[0] - 12;
                    }

                    if(totalFeet > 0){
                        //Set Text
                        if (newFraction[1] != 0)
                            result.setText(totalFeet + "ft. " + newFraction[0] + " " + newFraction[1] + "/" + newFraction[2] + "in.");
                        else
                            result.setText(totalFeet + "ft. " + newFraction[0] + " " + "in.");

                    }
                    else {
                        if(newFraction[1] != 0)
                            result.setText(newFraction[0] + " " + newFraction[1] + "/" + newFraction[2] + "in.");
                        else
                            result.setText(newFraction[0] + "in.");
                    }
                }
                else{
                    newFraction = reduceFraction( (inchNumber2*denom1 * numerator1),(denom1 * denom1));
                    while (newFraction[0] >= 12) {
                        totalFeet++;
                        newFraction[0] = newFraction[0] - 12;
                    }

                    if(totalFeet > 0){
                        //Set Text
                        if (newFraction[1] != 0)
                            result.setText(totalFeet + "ft. " + newFraction[0] + " " + newFraction[1] + "/" + newFraction[2] + "in.");
                        else
                            result.setText(totalFeet + "ft. " + newFraction[0] + " " + "in.");

                    }
                    else {
                        if(newFraction[1] != 0)
                            result.setText(newFraction[0] + " " + newFraction[1] + "/" + newFraction[2] + "in.");
                        else
                            result.setText(newFraction[0] + "in.");
                    }
                }


            }
            else {
                //Now generate number of feet
                while (newFraction[0] >= 12) {
                    totalFeet++;
                    newFraction[0] = newFraction[0] - 12;
                }

                if(totalFeet > 0){
                    //Set Text
                    if (newFraction[1] != 0)
                        result.setText(totalFeet + "ft. " + newFraction[0] + " " + newFraction[1] + "/" + newFraction[2] + "in.");
                    else
                        result.setText(totalFeet + "ft. " + newFraction[0] + " " + "in.");

                }
                else {
                    if(newFraction[1] != 0)
                        result.setText(newFraction[0] + " " + newFraction[1] + "/" + newFraction[2] + "in.");
                    else
                        result.setText(newFraction[0] + "in.");
                }
            }
        }
        else{

        }
    }

    //This functions reduces a fraction to its lowest terms.
    //It returns an array of 3 items.
    //result[0] = will be the whole number in case you pass in a fraction greater than 1. Ex. 9/3
    //result[1] = will be the numerator
    //result[2] = will be the denominator
    public int[] reduceFraction(int num, int denom){
        int[] result = new int[3];

        int i = Math.min(Math.abs(num),Math.abs(denom));
        if (i==0) return result;

        while((num % i != 0) || (denom % i != 0))
            i--;

        num = num / i;
        denom = denom / i;
        while(num >= denom){
            result[0]++;
            num = num - denom;
        }
        result[1] = num;
        result[2] = denom;

        return result;
    }
}
