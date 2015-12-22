package com.nfchack.archon.nfchack.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Evan on 12/21/2015.
 */
public class Order {
    @SerializedName("id")
    public Integer id;
    @SerializedName("type")
    public String type;
    @SerializedName("products")
    public List<String> products;
    @SerializedName("status")
    public String status;
    @SerializedName("url")
    public String url;
}
