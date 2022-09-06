package bg.tu.varna.frontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

import bg.tu.varna.frontend.MainActivity;
import bg.tu.varna.frontend.R;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import bg.tu.varna.frontend.utils.Permissions;
import bg.tu.varna.frontend.utils.common.RoleType;

public class BaseActivity extends AppCompatActivity {

    protected void openActivity(Class activity) {
        Intent i = new Intent(this, activity);
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.application_menu, menu);

        Set<String> permissions = AuthenticationUtils.getPermissions(getApplicationContext());

        if (!AuthenticationUtils.getRole(getApplicationContext()).equals(RoleType.ADMIN.toString().toLowerCase())) {
            if (!permissions.contains(Permissions.MANAGE_READERS)) {
                setItemVisibility(menu.findItem(R.id.users), false);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.users:
                openActivity(UserActivity.class);
                return true;
            case R.id.friends:
                Toast.makeText(this, "Friends are selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.findConnections:
                Toast.makeText(this, "Find connections are selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:
                AuthenticationUtils.unsetUser(getApplicationContext());
                openActivity(MainActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setItemVisibility(MenuItem item, boolean visible) {
        if (item != null)
            item.setVisible(visible);
    }
}
