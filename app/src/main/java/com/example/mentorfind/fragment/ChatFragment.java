package com.example.mentorfind.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;
import com.example.mentorfind.R;

public class ChatFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        // Assuming you have a WebView in your fragment_chat layout with the id "webView"
        WebView webView = view.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient()); // This ensures that links are opened within the WebView

        return view;
    }

    // Handle click events for images
    // Handle click events for images
    public void openLink(View view) {
        WebView webView = getView().findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true); // Enable JavaScript if needed

        if (view.getId() == R.id.imageViewAndroid) {
            webView.loadUrl("https://www.geeksforgeeks.org/android-studio-tutorial/");
        } else if (view.getId() == R.id.imageViewDSA) {
            webView.loadUrl("https://www.geeksforgeeks.org/data-structures/");
        } else if (view.getId() == R.id.imageViewML) {
            // Load the embedded link for Machine Learning
            String embeddedLink = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.example.com/embedded-ml-video\"></iframe>";
            webView.loadData(embeddedLink, "text/html", "utf-8");
        } else if (view.getId() == R.id.imageViewCodeClub) {
            webView.loadUrl("https://docs.google.com/spreadsheets/d/1_hkN9keyTGT7-THlmi9J0LnB-Tp2PWDY8wwwnflxZhY/edit#gid=0");
        }
    }

}
