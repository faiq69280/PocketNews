package com.example.newsapp;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class WebService implements IWebDAO{


    public interface Observer{
        public void update();
    }

    String content;
    String baseURL = "https://newsapi.org/v2/top-headlines?country=us";
    String API_KEY = "8f4899016a194360b658de7429ea0249";

    ArrayList<Hashtable<String,String>> results;
    Observer observer;
    public WebService(Observer o){
        /*Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                content = download(params);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        results = parse(content);
                    }
                });
            }
        });
        thread.start();*/
        observer = o;
        results = new ArrayList<Hashtable<String,String>>();
    }

    public ArrayList<Hashtable<String,String>> load(Hashtable<String,String> params){

        Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                content = download(params);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        results = parse(content);
                        //notify observer here
                        observer.update();
                    }
                });
            }
        });
        thread.start();

        //content=download(params);
        //results = parse(content);
        return results;
    }

    public ArrayList<Hashtable<String,String>> fetchFresh(){
        return results;
    }
    public String makeUrl(Hashtable<String,String> params){
        Enumeration<String> keys = params.keys();
        String newUrl = String.valueOf(baseURL);
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            newUrl += "&" + key.toLowerCase() + "=" + params.get(key);
        }
        return newUrl + "&apiKey=" + API_KEY;
    }
    public String download(Hashtable<String,String> params){
        StringBuilder result = new StringBuilder();
        String request_url = makeUrl(params);

        try{
            URL url = new URL(request_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent","Mozilla/5.0");
            //connection.setRequestProperty("Accept","*/*");
            connection.setRequestProperty("Content-type","text/plain");
            connection.setDoInput(true);
            connection.connect();
            //connection.getInputStream();

            BufferedReader reader = new BufferedReader( new
                    InputStreamReader( connection.getInputStream() ) );
            String line = "";
            while( (line = reader.readLine()) != null ){
                result.append(line);
            }
            connection.disconnect();
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return result.toString();
    }
    public ArrayList<Hashtable<String,String>> parse(String content){
        JSONObject object = null;
        ArrayList<Hashtable<String,String>> parsed = new ArrayList<Hashtable<String,String>>();
        try{
            object = new JSONObject(content);
            JSONArray articles = (JSONArray)object.get("articles");
            for (int i = 0; i < articles.length(); i++){
                Hashtable<String, String> obj = new Hashtable<String, String>();

                JSONObject o = articles.getJSONObject(i);
                /*Iterator<String> iter = o.keys();
                while (iter.hasNext()) {
                    String key = iter.next();
                    obj.put(key, o.getString(key));
                }*/
                obj.put("author",o.getString("author"));
                obj.put("title",o.getString("title"));
                obj.put("urlToImage",o.getString("urlToImage"));
                obj.put("url",o.getString("url"));
                obj.put("description",o.getString("description"));

                parsed.add(obj);
            }


        }catch(JSONException e){

        }

        return parsed;
    }
}
