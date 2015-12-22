package com.nfchack.archon.nfchack.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by prabak on 2015-12-21.
 */
public class Api {

    public interface Listener{
        void OnGetResponse(JSONObject response);
        void OnPostResponse(JSONObject response);
    }

    public static void GET(Context context, final Listener listener) {

        String url = "https://archon-nfc-hack.herokuapp.com/orders/8126";

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.OnGetResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(context).add(jsonRequest);
    }

    //POST: `https://archon-nfc-hack.herokuapp.com/orders/` — body: `{"products: ['product1', 'product2'}`
    //GET:  `https://archon-nfc-hack.herokuapp.com/orders/:id`
    //PUT:  `https://archon-nfc-hack.herokuapp.com/orders/:id` —automatically ‘increments’ the status. `statuses = ['created', 'picked', 'packed', 'shipped', 'received']`

/*
    public static void POST(Context context) {

        String url = "https://archon-nfc-hack.herokuapp.com/orders";




        Volley.newRequestQueue(context).add(postRequest);
    }
*/

}
