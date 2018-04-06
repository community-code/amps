package com.communitycode.amps.main.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.BatteryManager;
import android.os.Build;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.communitycode.amps.main.R;
import com.communitycode.amps.main.battery.UnofficialBatteryApi;
import com.communitycode.amps.main.battery.UnofficialBatteryMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class SendDebugReportPreference extends DialogPreference {
    public SendDebugReportPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);
        setDialogIcon(null);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        final Context context = getContext();
        LayoutInflater li = LayoutInflater.from(context);
        View view = li.inflate(R.layout.senddebugreport_dialog, null, false);
        final ProgressBar progressBar = view.findViewById(R.id.progressBar);
        final View errorMessage = view.findViewById(R.id.error);
        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int id) {
                progressBar.setVisibility(View.VISIBLE);
                Context context = getContext();
                JSONObject report = getReport(context);

                RequestQueue queue = Volley.newRequestQueue(context);
                String url ="http://www.google.com";

                // Request a string response from the provided URL.
                JsonObjectRequest stringRequest = new JsonObjectRequest(url, report,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                errorMessage.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorMessage.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });
    }

    public static JSONObject getReport(Context context) {
        JSONObject props = new JSONObject();

        try {
            props.put("Model", Build.MODEL.toLowerCase(Locale.ENGLISH));

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                BatteryManager mBatteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
                if (mBatteryManager != null) {

                    int val = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
                    props.put("BATTERY_PROPERTY_CURRENT_NOW", Integer.toString(val));
                    val = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);
                    props.put("BATTERY_PROPERTY_CURRENT_AVERAGE", Integer.toString(val));
                    val = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                    props.put("BATTERY_PROPERTY_CAPACITY", Integer.toString(val));
                    val = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);
                    props.put("BATTERY_PROPERTY_CHARGE_COUNTER", Integer.toString(val));
                    val = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER);
                    props.put("BATTERY_PROPERTY_ENERGY_COUNTER", Integer.toString(val));
                }
            }

            for (UnofficialBatteryMethod method : UnofficialBatteryApi.methods) {
                Integer val = method.read();
                props.put(method.filePath, val != null ? val.toString() : null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return props;
    }
}
