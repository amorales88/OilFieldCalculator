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
import android.widget.Toast;

import java.util.ArrayList;


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
        int numerator1 = fixInputAndConvert(numeratorNum1.getText().toString());
        int numerator2 = fixInputAndConvert(numeratorNum2.getText().toString());
        int denom1 = fixInputAndConvert(denomNum1.getText().toString());
        int denom2 = fixInputAndConvert(denomNum2.getText().toString());
        int feetNumber1 = fixInputAndConvert(feetNum1.getText().toString());
        int feetNumber2 = fixInputAndConvert(feetNum2.getText().toString());
        int inchNumber1 = fixInputAndConvert(inchNum1.getText().toString());
        int inchNumber2 = fixInputAndConvert(inchNum2.getText().toString());
        int totalInches = 0;
        int totalFeet = 0;

        //Return value from reduceFraction
        int[] newFraction;


        String operation = operatorSpinner.getSelectedItem().toString();

        //Converting to a faction
        if (feetNumber1 > 0) {
            inchNumber1 = inchNumber1 + (feetNumber1 * 12);;
        }
        if (feetNumber2 > 0) {
            inchNumber2 = inchNumber2 + (feetNumber2 * 12);
        }
        if (inchNumber1 > 0) {
            numerator1 = numerator1 + (inchNumber1 * denom1);
        }
        if (inchNumber2 > 0) {
            numerator2 = numerator2 + (inchNumber2 * denom2);
        }


        if(operation.equals("+")){
            numerator1 = numerator1 * denom2;
            numerator2 = numerator2 * denom1;

            newFraction = reduceFraction((numerator1 + numerator2), (denom1 * denom2));
            while (newFraction[0] >= 12) {
                totalFeet++;
                newFraction[0] = newFraction[0] - 12;
            }
            setResult(newFraction, totalFeet);
        }
        else if(operation.equals("-")){
            // use getLCM to find the least common multiple
            int lcm = getLCM(denom1, denom2);
            int tempNum1 = (lcm / denom1) * numerator1;
            int tempNum2 = (lcm / denom2) * numerator2;
            Toast.makeText(getApplicationContext(), String.valueOf(lcm) + "  " + String.valueOf(tempNum1) + " " + String.valueOf(tempNum2), Toast.LENGTH_SHORT).show();

            newFraction = reduceFraction((tempNum1 - tempNum2), lcm);
            while (newFraction[0] >= 12) {
                totalFeet++;
                newFraction[0] = newFraction[0] - 12;
            }
            setResult(newFraction, totalFeet);

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

    private int fixInputAndConvert(String auxString) {
        return (auxString.equals("") || auxString.equals(".")) ? 0 : Integer.valueOf(auxString);
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


    private void setResult(int[] newFraction, int totalFeet){
        if(totalFeet > 0){
            //Set Text
            if (newFraction[1] != 0 && newFraction[0] != 0)
                result.setText(totalFeet + "ft. " + newFraction[0] + " " + newFraction[1] + "/" + newFraction[2] + "in.");
            else if(newFraction[1] != 0)
                result.setText(totalFeet + "ft. " + newFraction[1] + "/" + newFraction[2] + "in.");
            else
                result.setText(totalFeet + "ft. " + newFraction[0] + " " + "in.");

        }
        else {
            if(newFraction[1] != 0 && newFraction[0] != 0)
                result.setText(newFraction[0] + " " + newFraction[1] + "/" + newFraction[2] + "in.");
            else if(newFraction[1] != 0)
                result.setText(newFraction[1] + "/" + newFraction[2] + "in.");
            else
                result.setText(newFraction[0] + "in.");
        }

    }

    public int getLCM (int denominator1, int denominator2) {
        ArrayList<Integer> multipliers = new ArrayList<Integer>();

        int x = 1;
        do {
            boolean divisible = false;
            while(!((modulo(denominator1, x) == 0)||(modulo(denominator1, x) == 0))) {
                x++;
            }
            if (modulo(denominator1, x) == 0) {
                divisible = true;
                denominator1 = denominator1 / x;
            }
            if (modulo(denominator2, x) == 0) {
                divisible = true;
                denominator2 = denominator2 / x;
            }

            if (divisible) {
                multipliers.add(x);
                divisible = false;
            }

        } while ((denominator1 != 1) && (denominator2 != 1));
        int result = 1;
        for (int temp : multipliers) {
            result = result * temp;
        }
        return result;
    }


    public int modulo (int m, int n) {
        int mod = m % n; // this returns the remainder. To get the modulo, you should tamper a bit with the result as in next line
        return (mod < 0) ? mod + n : mod;
    }
}
