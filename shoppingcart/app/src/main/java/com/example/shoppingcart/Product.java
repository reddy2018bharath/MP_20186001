package com.example.shoppingcart;

public class Product {
    String productId;
    String productName;
    String price;
    String quantity;
    String desc;
    String url;
    public Product(String productId,String productName, String price,String q, String desc, String url){
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity  = q;
        this.desc = desc;
        this.url = url;
    }

}
