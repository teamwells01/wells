package com.example.nathalieseibert.wells;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.support.v4.app.Fragment;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Karte extends Fragment {



    public Karte() {
        // Required empty public constructor
    }


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_karte, container, false);
        String html = "<iframe src=\"https://www.google.com/maps/d/embed?mid=11lSl35ncbtOGnEDl9BQYPHbuuOQ\" width=\"390\" height=\"545\"></iframe>";
        WebView webview;
        webview = view.findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadData(html, "text/html", null);
        Button liste = view.findViewById(R.id.liste);
        liste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Trinkbrunnen listefragment = new Trinkbrunnen();
                FragmentManager manager = getFragmentManager();
                try{
                    assert manager != null;
                    manager.beginTransaction().replace(R.id.mainLayout, listefragment, listefragment.getTag()).commit();
                }catch (NullPointerException e){
                    Toast.makeText(getActivity(), "Error",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;



    }


}
