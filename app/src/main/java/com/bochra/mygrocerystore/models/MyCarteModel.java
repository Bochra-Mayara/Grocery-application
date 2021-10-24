package com.bochra.mygrocerystore.models;

import java.io.Serializable;

public class MyCarteModel implements Serializable {
    String productName;
    String productPrice;
    String currantDate;
    String currantTime;
    String totalQuantity;
    int totalPrice;
    String documentId;

    public MyCarteModel() {
    }

    public MyCarteModel(String productName, String productPrice, String currentDate, String currentTime, String totalQuantity, int totalPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.currantDate = currantDate;
        this.currantTime = currantTime;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCurrantDate() {
        return currantDate;
    }

    public void setCurrantDate(String currentDate) {
        this.currantDate = currentDate;
    }

    public String getCurrantTime() {
        return currantTime;
    }

    public void setCurrantTime(String currentTime) {
        this.currantTime = currentTime;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
