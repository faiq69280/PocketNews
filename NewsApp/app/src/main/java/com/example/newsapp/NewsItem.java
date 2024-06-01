package com.example.newsapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public class NewsItem implements Serializable {
    private String author;
    private String urlToImage;
    private String url;
    private String title;
    private String content;
    private String description;
    public NewsItem(String newAuthor, String newUrlToImage,
                    String newUrl, String newTitle, String newContent,
                    String newDesc){

    }
   public static ArrayList<Hashtable<String,String>> getItems(IWebDAO dao, Hashtable<String,String> params){
            ArrayList<Hashtable<String,String>> res = dao.load(params);
            return res;
   }
}
