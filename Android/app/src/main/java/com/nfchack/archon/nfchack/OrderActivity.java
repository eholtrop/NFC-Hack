package com.nfchack.archon.nfchack;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nfchack.archon.nfchack.adapters.ProductAdapter;
import com.nfchack.archon.nfchack.models.Order;
import com.nfchack.archon.nfchack.services.Api;

import java.util.Objects;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

public class OrderActivity extends AppCompatActivity {


    @Bind(R.id.list)
    public RecyclerView List;
    @Bind(R.id.id)
    public TextView Id;
    @Bind(R.id.type)
    public TextView Type;
    @Bind(R.id.url)
    public TextView Url;
    @Bind(R.id.status)
    public TextView Status;


    private Order _order;
    private BehaviorSubject<Order> Order;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.order, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.set_packed:
                break;
            case R.id.set_picked:
                break;
            case R.id.set_received:
                break;
            case R.id.set_shipped:
                break;
            case R.id.reset_order:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.setGroupVisible(R.id.created, Objects.equals(_order.status, "created"));
        menu.setGroupVisible(R.id.picked, Objects.equals(_order.status, "picked"));
        menu.setGroupVisible(R.id.packed, Objects.equals(_order.status, "packed"));
        menu.setGroupVisible(R.id.shipped, Objects.equals(_order.status, "shipped"));

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Order = BehaviorSubject.create();
        List.setLayoutManager(new LinearLayoutManager(this));
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Order.subscribe(new Action1<com.nfchack.archon.nfchack.models.Order>() {
            @Override
            public void call(Order order) {
                _order = order;
                List.setAdapter(new ProductAdapter(OrderActivity.this, order.products));
//                Id.setText(order.id);

                Url.setText(order.url);
                invalidateOptionsMenu();
//                Type.setText(order.type);

                if(order.status.equals("received")) {
                    Snackbar.make(fab, "No further work required on Order", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        String order_json = getIntent().getStringExtra("order_json");
        if(!order_json.isEmpty()) {
            Log.d("Order", order_json);
            Order.onNext(new Gson().fromJson(order_json, Order.class));
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    public enum OrderStates {
        Created, Picked, Packed, Shipped, Received
    }

}
