package com.example.lab5;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tvMain;
    static ListView ratesListView;
    ArrayList<String> ratesList;
    static ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ratesListView = findViewById(R.id.rates_listView);
        tvMain = findViewById(R.id.cryptocurrency_rates_textView);
        ratesList = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, R.layout.listview_items_style, ratesList);
        ratesListView.setAdapter(adapter);

        RetrofitDataLoader rt = new RetrofitDataLoader();
        rt.getData(getApplicationContext());
    }
}