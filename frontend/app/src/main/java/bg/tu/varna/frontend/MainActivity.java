package bg.tu.varna.frontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bg.tu.varna.frontend.activity.HomeActivity;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import bg.tu.varna.frontend.view_model.LoginViewModel;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button btnLogin;
    private LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);

        initViewModel();

        if (!AuthenticationUtils.isLoggedIn(getApplicationContext())) {
            btnLogin.setOnClickListener(view -> login());
        } else {
            openActivity();
        }
    }

    private void login() {
        loginViewModel.login(getApplicationContext(), username.getText().toString(), password.getText().toString());
    }

    private void initViewModel() {
        loginViewModel = new ViewModelProvider(MainActivity.this).get(LoginViewModel.class);
        loginViewModel.getResponseObserver().observe(this, loginResponse -> {
            if (loginResponse == null) {
                Toast.makeText(MainActivity.this,"Опитайте пак", Toast.LENGTH_SHORT).show();
            } else {
                openActivity();
            }
        });
    }


    private void openActivity() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}