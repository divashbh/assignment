package com.example.art_ordering.global;

import com.example.art_ordering.entity.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    public static List<Product> cart;
    static {
        cart=new ArrayList<>();
    }
}