package com.chata.chata;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;

public class Utility {
    private RequestQueue queue;

    private static Utility instance;
    private static Context _context;

    public static Utility getObject(Context context) {
        if (instance == null)
            instance = new Utility(context);

        _context = context;
        return instance;
    }

    private Utility(Context context) {
        queue = Volley.newRequestQueue(context);
        queue.start();
    }

    public void Post(String url, final String body) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                }) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return body == null ? null : body.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", body, "utf-8");
                    return null;
                }
            }
        };

        queue.add(stringRequest);
    }

    public void Get(String url, Response.Listener<String> onResponse) throws InterruptedException {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, onResponse,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        queue.add(stringRequest);
    }
}
