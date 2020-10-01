package com.varun.basic_banking_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.varun.basic_banking_app.database.DbHandler;
import com.varun.basic_banking_app.model.Customer;

public class MainActivity extends AppCompatActivity {
    Button button;
    Button transactions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DbHandler db=new DbHandler(this);
//
//        for(inn
//        int n=db.getCount();
//        for(int i=1;i<500;i++){
//            db.deleteContact(i);
//        }
        transactions=findViewById(R.id.transactions);
        button=findViewById(R.id.viewCustomers);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CustomersView.class);
                startActivity(intent);
            }
        });
        transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Transactions.class);
                startActivity(intent);
            }
        });
    }
}