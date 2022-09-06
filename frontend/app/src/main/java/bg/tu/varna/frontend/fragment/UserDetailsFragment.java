package bg.tu.varna.frontend.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import bg.tu.varna.frontend.R;
import bg.tu.varna.frontend.network.model.UserDto;

public class UserDetailsFragment extends DialogFragment {

    private UserDto user;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            UserDto user = bundle.getParcelable("user");
            TextView userId = view.findViewById(R.id.userDetailsUserId);
            TextView username = view.findViewById(R.id.userDetailsUsername);
            TextView firstName = view.findViewById(R.id.userDetailsFirstName);
            TextView lastName = view.findViewById(R.id.userDetailsLastName);
            TextView roleName = view.findViewById(R.id.userDetailsRoleName);
            TextView dateAdded = view.findViewById(R.id.userDetailsDateAdded);

            userId.setText(String.valueOf(user.getId()));
            username.setText(user.getUsername());
            firstName.setText(user.getFirstName());
            lastName.setText(user.getLastName());
            roleName.setText(user.getRoleName());
            dateAdded.setText(user.getDateAdded());
        }

        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_details, container, false);
    }
}
