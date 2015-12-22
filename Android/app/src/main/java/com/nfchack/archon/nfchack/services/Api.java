package com.nfchack.archon.nfchack.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
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
        void OnPostResponse(String response);
    }

    public static void GET(Context context, final Listener listener) {

        String url = "https://nflo.herokuapp.com/o";

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


    public static void POST(Context context) {

        String url = "https://nflo.herokuapp.com/o";


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response).getJSONObject("form");
                        //String site = jsonResponse.getString("site"),
                        //        network = jsonResponse.getString("network");
                        //System.out.println("Site: "+site+"\nNetwork: "+network);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("products", "['Pepperoni', 'Green Peppers']");
                return params;
            }
        };
        Volley.newRequestQueue(context).add(postRequest);
    }


}
