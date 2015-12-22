package com.nfchack.archon.nfchack.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Evan on 12/21/2015.
 */
public class NfcRequest {
    public int Type;
    @SerializedName("url")
    public String url;
}
