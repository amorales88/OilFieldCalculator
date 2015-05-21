package myapps.oilfieldcalculator;

import android.os.Bundle;

/**
 * Created by mpereira on 5/19/2015.
 */
public class Measurement {

    Bundle data;

    public Measurement() {
        // Initialize all variables with default?
        data = new Bundle();
    }

    public Bundle getData() {
        return data;
    }

    public void setData(Bundle data) {
        this.data = data;
    }

    // For the sake of our app, we're just returning strings
    public String getProperty(String key) {
        return data.getString(key);
    }

    public void setProperty(String key, String value) {
        data.putString(key, value);
    }


}
