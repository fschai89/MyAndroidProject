package com.fschai.myandroidproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fschai.myandroidproject.R;
import com.fschai.myandroidproject.adapter.RecyclerViewAdapter;
import com.fschai.myandroidproject.model.ContactCollection;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static RecyclerViewAdapter adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public static ContactCollection contactCollection;
    private static int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.search_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contactCollection = new ContactCollection();
        contactCollection.setContactListFromJSON(MainActivity.this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        RecyclerView recyclerView = findViewById(R.id.rvContact);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayout.VERTICAL));

        adapter = new RecyclerViewAdapter(this, contactCollection.getContactList());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        currentPosition = position;

        Bundle bundle = new Bundle();
        bundle.putString("mode", "edit");
        bundle.putSerializable("contact", adapter.getItem(position));

        Intent intent = new Intent(MainActivity.this, ContactActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "You clicked search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_add:
                Bundle bundle = new Bundle();
                bundle.putString("mode", "add");

                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        contactCollection.setContactListFromJSON(MainActivity.this);
        adapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public static void dataUpdated(){
        adapter.notifyItemChanged(currentPosition);
    }

    public static void dataAdded(){
        adapter.notifyItemInserted(contactCollection.getContactList().size() - 1);
    }

}