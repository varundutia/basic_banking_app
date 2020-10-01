package com.varun.basic_banking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.varun.basic_banking_app.database.DbHandler;
import com.varun.basic_banking_app.model.Customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomersView extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> contactArrayList;
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_view);
        listView=findViewById(R.id.listView);
        DbHandler db = new DbHandler(CustomersView.this);
        final List<Customer> customers = db.getAllCustomers();
        int count = customers.size();
        if (customers != null) {
            contactArrayList = new ArrayList<String>();
            for (Customer contact : customers) {
                contactArrayList.add(contact.getName());
                Log.d("contacts", String.valueOf(contact));
            }
            arrayAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    contactArrayList
            );
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Customer c=customers.get(i);
                    Intent intent=new Intent(CustomersView.this,CustomerView.class);
                    intent.putExtra("id",c.getId());
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}