package bg.tu.varna.frontend.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import bg.tu.varna.frontend.R;
import bg.tu.varna.frontend.adapter.ListUserAdapter;
import bg.tu.varna.frontend.config.RetrofitConfig;
import bg.tu.varna.frontend.model.ErrorMessage;
import bg.tu.varna.frontend.network.model.UserActiveDto;
import bg.tu.varna.frontend.network.model.UserDto;
import bg.tu.varna.frontend.repository.UserRepository;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import bg.tu.varna.frontend.utils.ErrorUtils;
import bg.tu.varna.frontend.utils.StringConstants;
import bg.tu.varna.frontend.utils.common.RoleType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserDetailsFragment extends DialogFragment {

    private UserDto user;
    private Switch userStatus;
    private ListUserAdapter listUserAdapter;

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

            userStatus = view.findViewById(R.id.userDetailsDeactivate);

            if (!AuthenticationUtils.getPermissions(getContext()).contains("MANAGE_READERS")) {
                userStatus.setVisibility(View.INVISIBLE);
            } else {
                userStatus.setChecked(user.getActive());
                userStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UserActiveDto userActiveDto = new UserActiveDto();
                        userActiveDto.setActive(userStatus.isChecked());
                        changeUserStatus(user.getId(), userActiveDto);
                    }
                });
            }
        }

        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_details, container, false);
    }

    private void changeUserStatus(Long id, UserActiveDto userActiveDto) {
        Retrofit retrofit = RetrofitConfig.getInstance();
        UserRepository userRepository = retrofit.create(UserRepository.class);
        Call<UserDto> userDtoCall = userRepository.updateStatus(
                StringConstants.BEARER + AuthenticationUtils.getToken(getContext()), id, userActiveDto);

        userDtoCall.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (!response.isSuccessful()) {
                    ErrorMessage errorMessage = ErrorUtils.parseError(response);
                    Toast.makeText(getContext(), errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                listUserAdapter.setUser(response.body());
                userStatus.setChecked(response.body().getActive());
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setListUserAdapter(ListUserAdapter listUserAdapter) {
        this.listUserAdapter = listUserAdapter;
    }
}
