package com.nfchack.archon.nfchack.models;

/**
 * Created by Evan on 12/21/2015.
 */
public class RequestType {
    public static String Product = "Product";
    public static String Order = "Order";
    public static String Location = "Location";

    public static String GetType(int value) {
        String Type = "";
        switch(value) {
            case 0:
                Type = Product;
                break;
            case 1:
                Type = Order;
                break;
            case 2:
                Type = Location;
                break;
            default:
                try {
                    throw new Exception("Value not a valid type");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return Type;
    }

    public static int GetValue(String type) {
        int value = -1;
        if (type.equals(Product)) {
            value = 0;
        } else if (type.equals(Order)) {
            value = 1;
        } else if (type.equals(Location)) {
            value = 2;
        }
        return value;
    }

}
