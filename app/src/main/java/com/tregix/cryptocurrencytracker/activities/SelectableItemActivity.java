package com.tregix.cryptocurrencytracker.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.tregix.cryptocurrencytracker.Model.SelectableItem;
import com.tregix.cryptocurrencytracker.R;
import com.tregix.cryptocurrencytracker.adapter.SelectableRecyclerViewAdapter;
import com.tregix.cryptocurrencytracker.adapter.SelectableViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gohar Ali on 12/25/2017.
 */

public class SelectableItemActivity extends BaseActivity implements SelectableViewHolder.OnItemSelectedListener {

    RecyclerView recyclerView;
    SelectableRecyclerViewAdapter adapter;
    TextView clearSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_selectable);
        initViews();
        setAdapter();

        //getSupportActionBar().setTitle("Select");

    }

    private void initViews() {
        recyclerView = (RecyclerView) this.findViewById(R.id.selection_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    private void setAdapter() {
        List<String> selectableItems = getIntent().getStringArrayListExtra("Data");
        String selectedItem = getIntent().getStringExtra("SelectedItem");
        adapter = new SelectableRecyclerViewAdapter(this, selectableItems, false,selectedItem);
        recyclerView.setAdapter(adapter);
    }

    private Intent getResultIntent() {
        Intent intent = new Intent();
        ArrayList<String> selectedItems = adapter.getSelectedItems();
        intent.putStringArrayListExtra("Data",selectedItems);
        return intent;
    }

    @Override
    public void onItemSelected(SelectableItem selectableItem) {
        setResult(RESULT_OK,getResultIntent());
        finish();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK,getResultIntent());
        finish();
    }
}
