package bg.tu.varna.frontend.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import bg.tu.varna.frontend.R;
import bg.tu.varna.frontend.adapter.ListBorrowAdapter;
import bg.tu.varna.frontend.fragment.AddBorrowFragment;
import bg.tu.varna.frontend.view_model.ListBorrowViewModel;

public class BorrowedBooksActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ListBorrowAdapter adapter;
    private ListBorrowViewModel listBorrowViewModel;
    private Button btnAddBorrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrowed_books);

        recyclerView = findViewById(R.id.borrowRecyclerView);
        btnAddBorrow = findViewById(R.id.btnAddBorrow);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new ListBorrowAdapter();
        adapter.setContext(getApplicationContext());
        adapter.setBorrowedBooksActivity(this);
        recyclerView.setAdapter(adapter);
        initViewModel();
        listBorrowViewModel.fetchAll(getApplicationContext());

        btnAddBorrow.setOnClickListener(view -> {
            AddBorrowFragment addBorrowFragment = new AddBorrowFragment();
            addBorrowFragment.setActivity(this);
            addBorrowFragment.setListBorrowAdapter(adapter);
            addBorrowFragment.show(getSupportFragmentManager(), "add_borrow");
        });
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
