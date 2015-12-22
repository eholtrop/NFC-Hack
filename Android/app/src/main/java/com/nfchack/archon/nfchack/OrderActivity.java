package com.nfchack.archon.nfchack;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.nfchack.archon.nfchack.adapters.ProductAdapter;
import com.nfchack.archon.nfchack.models.Order;

import butterknife.Bind;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

public class OrderActivity extends AppCompatActivity {


    @Bind(R.id.list)
    public RecyclerView List;

    private BehaviorSubject<Order> Order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Order = BehaviorSubject.create();
        List.setLayoutManager(new LinearLayoutManager(this));
        Order.subscribe(new Action1<com.nfchack.archon.nfchack.models.Order>() {
            @Override
            public void call(Order order) {
                List.setAdapter(new ProductAdapter(OrderActivity.this, order.products));
            }
        });

        String order_json = getIntent().getStringExtra("order_id");
        if(!order_json.isEmpty()) {
            Order.onNext(new Gson().fromJson(order_json, Order.class));
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
