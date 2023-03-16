package ntt.demo.base.service;

import com.google.gson.Gson;
import ntt.demo.base.domain.Order;

public class OrderUtils {
    public static Order stringToOrder(String str){
        Gson g = new Gson();
        Order s = g.fromJson(str, Order.class);
        return s;
    }
}
