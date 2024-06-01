package com.example.newsapp;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ArrayList;

public interface IWebDAO {
    public ArrayList<Hashtable<String,String>> load(Hashtable<String,String> params);
}
