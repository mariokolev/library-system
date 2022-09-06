package bg.tu.varna.frontend.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import bg.tu.varna.frontend.R;
import bg.tu.varna.frontend.adapter.ListUserAdapter;
import bg.tu.varna.frontend.fragment.AddUserFragment;
import bg.tu.varna.frontend.view_model.ListUserViewModel;

public class UserActivity extends BaseActivity{

    private RecyclerView recyclerView;
    private Button btnAddUser;
    private ListUserViewModel listUserViewModel;
    private ListUserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btnAddUser = findViewById(R.id.btnAddUser);
        recyclerView = findViewById(R.id.userRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new ListUserAdapter();
        adapter.setUserActivity(this);
        recyclerView.setAdapter(adapter);
        initViewModel();
        listUserViewModel.fetchAll(getApplicationContext());

        btnAddUser.setOnClickListener(view -> {
            AddUserFragment addUserFragment = new AddUserFragment();
            addUserFragment.setListUserAdapter(adapter);
            addUserFragment.show(getSupportFragmentManager(), "add_user");
        });
    }

    private void initViewModel() {
        listUserViewModel = new ViewModelProvider(UserActivity.this).get(ListUserViewModel.class);
        listUserViewModel.getResponseObserver().observe(this, users -> {
            if (users != null) {
                adapter.setUsers(users);
            }
        });
    }
}
