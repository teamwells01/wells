package com.example.nathalieseibert.wells;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class Karte extends Fragment {

    WebView webview;

    public Karte() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_karte, container, false);
        String html = "<iframe src=\"https://www.google.com/maps/d/embed?mid=11lSl35ncbtOGnEDl9BQYPHbuuOQ\" width=\"390\" height=\"545\"></iframe>";
        webview = (WebView) view.findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadData(html, "text/html", null);
        Button liste = (Button) view.findViewById(R.id.liste);
        liste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Trinkbrunnen listefragment = new Trinkbrunnen();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.mainLayout, listefragment, listefragment.getTag()).commit();
            }
        });
        return view;



    }


}
