package myapps.oilfieldcalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    EditText upperTemp;
    EditText middleTemp;
    EditText lowerTemp;
    TextView AvgTempNum;
    EditText TotalObservedVolume;
    EditText FreeWaterVolume;
    EditText AmbientTemp;
    TextView ctshFactor;
    TextView ActualTemp;
    TextView VCF;
    Spinner operatorSpinner;
    EditText API;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScrollView v = (ScrollView) findViewById(R.id.scrollProfile);
        v.requestFocus();

        upperTemp = (EditText) findViewById(R.id.TempUpper);
        middleTemp = (EditText) findViewById(R.id.TempMiddle);
        lowerTemp = (EditText) findViewById(R.id.TempLower);
        AvgTempNum = (TextView) findViewById(R.id.AvgTempNum);
        TotalObservedVolume = (EditText) findViewById(R.id.TotalObservedVolume);
        FreeWaterVolume = (EditText) findViewById(R.id.FreeWaterVolume);
        AmbientTemp = (EditText) findViewById(R.id.AmbientTemperature);
        ctshFactor = (TextView) findViewById(R.id.ctshFactor);
        ActualTemp = (TextView) findViewById(R.id.ActualTemp);
        VCF = (TextView) findViewById(R.id.VCF);
        API = (EditText) findViewById(R.id.API);

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

        upperTemp.addTextChangedListener(textWatcher);
        middleTemp.addTextChangedListener(textWatcher);
        lowerTemp.addTextChangedListener(textWatcher);

        TextWatcher ambientTempWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ctshFactor.setText(String.valueOf(calculateCTSHFactor()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        AmbientTemp.addTextChangedListener(ambientTempWatcher);

        operatorSpinner = (Spinner) findViewById(R.id.GaugeInCriticalZoneSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Critical_Zone, android.R.layout.simple_spinner_dropdown_item);
        operatorSpinner.setAdapter(adapter);

        TextWatcher APIWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateVCFandRoofCorrection();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        API.addTextChangedListener(APIWatcher);

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

        if(id == R.id.fraction_converter){
            Intent fracCalcIntent = new Intent(this, FractionCalculator.class);
            startActivity(fracCalcIntent);
        }

        if(id == R.id.action_share) {
            share();
        }

        if(id == R.id.clear) {
            clear();
        }

        return super.onOptionsItemSelected(item);
    }



    public double calculateAverageTemp(){

        double result = 0.0;
        //Check to see if field is blank, if so it will cause an error

        result = result + ((upperTemp.getText().toString().equals("") || upperTemp.getText().toString().equals(".")) ? 0 : Double.valueOf(upperTemp.getText().toString()));

        result = result + ((middleTemp.getText().toString().equals("") || middleTemp.getText().toString().equals("."))? 0 : Double.valueOf(middleTemp.getText().toString()));

        result = result + ((lowerTemp.getText().toString().equals("") || lowerTemp.getText().toString().equals("."))? 0 : Double.valueOf(lowerTemp.getText().toString()));

        //Round to the 1st decimal
        int supplied = 0;
        if (!upperTemp.getText().toString().equals("")) supplied++;
        if (!middleTemp.getText().toString().equals("")) supplied++;
        if (!lowerTemp.getText().toString().equals("")) supplied++;
        return ((double)Math.round((result/supplied) * 10) / 10);

    }

    public double calculateCTSHFactor(){
        //Verify AmbientTemp isnt a '.' or blank
        double AmbientTempNum = ((AmbientTemp.getText().toString().equals("") || AmbientTemp.getText().toString().equals("."))? 0 : Double.valueOf(AmbientTemp.getText().toString()));
        if(AmbientTempNum == 0) {
            ActualTemp.setText("0.0");
            return 0.0;
        }

        // (Average Temp x 7 + AmbientTemp) / 8 = Actual Temp
        double result = (Double.valueOf(AvgTempNum.getText().toString()) * 7 + AmbientTempNum) / 8;

        //Set Actual Temp to 1st decimal and use that value for next calculation
        result = ((double)Math.round(result * 10) / 10);
        ActualTemp.setText(String.valueOf(result));

        // ((Actual Temp - 60) x .0000124) + 1
        result = ((result - 60) * .0000124) + 1;

        //Round to the 5th decimal
        return ((double)Math.round(result * 100000) / 100000);
    }

    public String getScreenValues() {
        String retStr = "";
        RelativeLayout relLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        for(int i = 0; i < relLayout.getChildCount(); i++) {
             View view = relLayout.getChildAt(i);
            if(view instanceof TextView) {
                TextView tv = (TextView) view;
                retStr = retStr + tv.getText().toString();

            }


            if(view instanceof Spinner) {
                Spinner sp = (Spinner) view;
                retStr = retStr + sp.getSelectedItem().toString();
            }

            retStr = retStr + " ";

            // Assess if it's the last field in that line
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
            int rules[] = params.getRules();

            if ((rules[RelativeLayout.ALIGN_PARENT_END] != 0)||(view.getId() == R.id.TempLower)) {
                retStr = retStr + "\n";
            }

            /* OLD WAY!!
            if ((view.getId() == R.id.TempLower)||(view.getId() == R.id.AvgTempNum)||(view.getId() == R.id.TotalObservedVolume)||(view.getId() == R.id.FreeWaterVolume)||
                (view.getId() == R.id.AmbientTemperature)||(view.getId() == R.id.ctshFactor)||(view.getId() == R.id.ActualTemp)||(view.getId() == R.id.GaugeInCriticalZoneSpinner)||
                (view.getId() == R.id.BblsPer)||(view.getId() == R.id.API)||(view.getId() == R.id.RoofCorrection)||(view.getId() == R.id.GrossObservedVolume)) {
                retStr = retStr + "\n";
            }
            */


        }

        return retStr;
    }

    public void share() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, getScreenValues());
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_using)));
    }

    public void clear() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.clear_fields))
                .setMessage(getString(R.string.clear_question))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue With Delete
                        RelativeLayout relLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

                        // Cycle through everything on screen and reset it
                        for(int i = 0; i < relLayout.getChildCount(); i++) {
                            View view = relLayout.getChildAt(i);
                            if (view instanceof EditText) {
                                ((EditText) view).setText("");
                            }
                            if (view instanceof Spinner) {
                                ((Spinner) view).setSelection(0,true);
                            }
                        }

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do Nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();



    }

    //This function calculates and sets the VCF and RoofCorrection TextView
    public void calculateVCFandRoofCorrection(){
        double api = (API.getText().toString().equals("") || API.getText().toString().equals("."))? 0 : Double.valueOf(API.getText().toString());
        double Density = (141.5/(api + 131.5)) * 999.016;
        double X = 0;

        //Density will never be exactly 838.3127
        if(Density > 838.3127)
            X = 103.872;
        else if(Density >= 787.5195 && Density < 838.3127)
            X = 330.301;
        else if(Density >= 770.352 && Density < 787.5195)
            X = 1489.067;
        else if(Density >= 610.6 && Density < 770.352)
            X = 192.457;

        VCF.setText(String.valueOf(X));

    }

}
