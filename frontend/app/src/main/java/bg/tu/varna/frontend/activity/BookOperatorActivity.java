package bg.tu.varna.frontend.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import bg.tu.varna.frontend.R;
import bg.tu.varna.frontend.adapter.ListBookAdapter;
import bg.tu.varna.frontend.adapter.ListUserAdapter;
import bg.tu.varna.frontend.fragment.AddBookFragment;
import bg.tu.varna.frontend.fragment.AddUserFragment;
import bg.tu.varna.frontend.view_model.ListOperatorBookViewModel;
import bg.tu.varna.frontend.view_model.ListUserViewModel;

public class BookOperatorActivity extends BaseActivity{

    private RecyclerView recyclerView;
    private Button btnAddBook;
    private ListOperatorBookViewModel listOperatorBookViewModel;
    private ListBookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btnAddBook = findViewById(R.id.btnAddBook);
        recyclerView = findViewById(R.id.bookRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new ListBookAdapter();
        adapter.setBookOperatorActivity(this);
        recyclerView.setAdapter(adapter);
        initViewModel();
        listOperatorBookViewModel.fetchAll(getApplicationContext());

        btnAddBook.setOnClickListener(view -> {
            AddBookFragment addBookFragment = new AddBookFragment();
            addBookFragment.setListBookAdapter(adapter);
            addBookFragment.show(getSupportFragmentManager(), "add_book");
        });
    }

    private void initViewModel() {
        listOperatorBookViewModel = new ViewModelProvider(BookOperatorActivity.this).get(ListOperatorBookViewModel.class);
        listOperatorBookViewModel.getResponseObserver().observe(this, books -> {
            if (books != null) {
                adapter.setBooks(books);
            }
        });
    }
}
