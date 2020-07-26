package com.target.onlinecourierservice;

import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.target.onlinecourierservice.model.CartProduct;

import java.util.ArrayList;

public class Global {
    public static String ParcelId;


    public static EditText senderName;
    public static EditText senderMobile;
    public static EditText senderAddress;
    public static EditText pickupInstruction;
    public static String senderCity;
    public static String senderThana;

    public static EditText repName;
    public static EditText repMobile;
    public static EditText repAddress;
    public static EditText deliveyInstruction;
    public static String repCity;
    public static String repThana;


    public static String packageType;
    public static String size;
    public static String weight;
    public static RadioGroup sizeGroup;
    public static RadioGroup weightGroup;


    public static String paymentMethod;
    public static String totalMoney;
    public static EditText txID;

    public static ArrayList<CartProduct>cartProducts;

    public static String PurCity;
    public static String PurThana;
    public static String pMethod;


}
