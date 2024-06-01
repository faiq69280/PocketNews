package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import java.util.Hashtable;
import java.util.ArrayList;

import android.os.StrictMode;
import android.widget.FrameLayout;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity implements WebService.Observer {
    public void update(){
        res = articles.fetchFresh();
        t.setText(res.get(0).get("description").toString());
    }
    private TextView t;
    private WebService articles;
    private FrameLayout paneOne;
   // ScienceFragment science_fragment;

    ArrayList<Hashtable<String,String>> res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        /*(if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }*/
        /*
        Hashtable<String,String> h = new Hashtable<String,String>();
        h.put("category","science");
        //ArrayList<Hashtable<String,String>> articles = (new WebService()).load(h);
        t = (TextView) findViewById(R.id.content);
        //t.setText(articles.get(0).get("author").toString());
        articles = new WebService(this);





        res= NewsItem.getItems(articles,h);
        if(res.size()==0)
            t.setText("No articles found yet");
        else
            t.setText(res.get(0).get("description").toString());
        */

        createLayout();
        createFragments();


    }

    private void createLayout(){
        setContentView(R.layout.activity_main);
        /*paneOne = (FrameLayout) findViewById(R.id.paneOne);*/
    }
    private void createFragments(){
       /* science_fragment = new ScienceFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(paneOne.getId(), science_fragment,"science_list");
        transaction.commit();

        */
    }


}