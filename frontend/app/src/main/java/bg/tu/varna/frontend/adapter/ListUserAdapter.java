package bg.tu.varna.frontend.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bg.tu.varna.frontend.R;
import bg.tu.varna.frontend.activity.UserActivity;
import bg.tu.varna.frontend.fragment.UserDetailsFragment;
import bg.tu.varna.frontend.network.model.UserDto;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.UserHolder> {

    private List<UserDto> users = new ArrayList<>();
    private Context context;
    private UserActivity userActivity;
    private LinearLayout linearLayout;

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return  new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        UserDto user = users.get(position);
        holder.userId.setText(String.valueOf(user.getId()));
        holder.username.setText(user.getUsername());
        holder.firstName.setText(user.getFirstName());
        holder.lastName.setText(user.getLastName());
        holder.roleName.setText(user.getRoleName());

        linearLayout.setOnClickListener(view -> {
            UserDetailsFragment userDetailsFragment = new UserDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("user", user);
            userDetailsFragment.setArguments(bundle);
            userDetailsFragment.show(userActivity.getSupportFragmentManager(), "user_details");
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public void addUser(UserDto userDto) {
        users.add(userDto);
        notifyDataSetChanged();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public UserActivity getUserActivity() {
        return userActivity;
    }

    public void setUserActivity(UserActivity userActivity) {
        this.userActivity = userActivity;
    }

    class UserHolder extends RecyclerView.ViewHolder {
        private final TextView userId;
        private final TextView username;
        private final TextView firstName;
        private final TextView lastName;
        private final TextView roleName;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.userItemUserId);
            username = itemView.findViewById(R.id.userItemUsername);
            firstName = itemView.findViewById(R.id.userItemFirstName);
            lastName = itemView.findViewById(R.id.userItemLastName);
            roleName = itemView.findViewById(R.id.userItemRoleName);
            linearLayout = itemView.findViewById(R.id.userItemLinearLayout);
        }
    }
}
