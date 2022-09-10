package bg.tu.varna.frontend.activity;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import bg.tu.varna.frontend.R;
import bg.tu.varna.frontend.adapter.ListBorrowAdapter;
import bg.tu.varna.frontend.view_model.ListBorrowViewModel;

public class BorrowedBooksActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ListBorrowAdapter adapter;
    private ListBorrowViewModel listBorrowViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed_books);

        recyclerView = findViewById(R.id.borrowRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new ListBorrowAdapter();
        adapter.setContext(getApplicationContext());
        recyclerView.setAdapter(adapter);
        initViewModel();
        listBorrowViewModel.fetchAll(getApplicationContext());
    }

    private void initViewModel() {
        listBorrowViewModel = new ViewModelProvider(BorrowedBooksActivity.this).get(ListBorrowViewModel.class);
        listBorrowViewModel.getResponseObserver().observe(this, borrows -> {
            if (borrows != null) {
                adapter.setBorrows(borrows);
            }
        });
    }
}
