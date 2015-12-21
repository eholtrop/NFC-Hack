package com.nfchack.archon.nfchack.models;

import java.util.List;

/**
 * Created by Evan on 12/21/2015.
 */
public class Order {

    private List<String> Products;
    private String Name;

    public String getName() { return Name; }

    public List<String> getProducts() { return Products; }
}
