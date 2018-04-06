package com.communitycode.amps.main;

import android.content.Context;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Pair;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.communitycode.amps.main.battery.UnofficialBatteryApi;
import com.communitycode.amps.main.battery.UnofficialBatteryMethod;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ReportHome {
    public static void sendData(Context context, JSONObject jsonObject) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://www.google.com";

        // Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
//                        mTextView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
        //                mTextView.setText("That didn't work!");
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}
