package bg.tu.varna.frontend.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import bg.tu.varna.frontend.R;
import bg.tu.varna.frontend.adapter.ListUserAdapter;
import bg.tu.varna.frontend.config.RetrofitConfig;
import bg.tu.varna.frontend.model.ErrorMessage;
import bg.tu.varna.frontend.network.model.LibraryDto;
import bg.tu.varna.frontend.network.model.RoleDto;
import bg.tu.varna.frontend.network.model.UserDto;
import bg.tu.varna.frontend.network.model.UserRequestDto;
import bg.tu.varna.frontend.repository.UserRepository;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import bg.tu.varna.frontend.utils.ErrorUtils;
import bg.tu.varna.frontend.utils.StringConstants;
import bg.tu.varna.frontend.utils.common.RoleType;
import bg.tu.varna.frontend.view_model.ListLibraryViewModel;
import bg.tu.varna.frontend.view_model.ListRoleViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddUserFragment extends DialogFragment {

    private ListLibraryViewModel listLibraryViewModel;
    private ListRoleViewModel listRoleViewModel;
    private ArrayAdapter<LibraryDto> libraryAdapter;
    private Spinner librarySpinner;
    private Spinner roleSpinner;
    private ArrayAdapter<String> roleAdapter;
    private ListUserAdapter listUserAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_user, container, false);
        librarySpinner = view.findViewById(R.id.spinnerLibraries);
        initViewModel();
        loadRoles();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText username = view.findViewById(R.id.addUsername);
        EditText password = view.findViewById(R.id.addPassword);
        EditText firstName = view.findViewById(R.id.addFirstName);
        EditText lastName = view.findViewById(R.id.addLastName);
        Button btnAddUser = view.findViewById(R.id.btnDialogAddUser);
        roleSpinner = view.findViewById(R.id.spinnerRoles);
        librarySpinner.setAdapter(libraryAdapter);
        listLibraryViewModel.fetchAll(getContext());
        listRoleViewModel.fetchAll(getContext());

        btnAddUser.setOnClickListener(addUserView -> {
            LibraryDto library = (LibraryDto) librarySpinner.getSelectedItem();
            String role = (String) roleSpinner.getSelectedItem();
            createUser(
                    username.getText().toString(),
                    password.getText().toString(),
                    firstName.getText().toString(),
                    lastName.getText().toString(),
                    library.getId(),
                    role);
        });
    }

    private void initViewModel() {
        listLibraryViewModel = new ViewModelProvider(AddUserFragment.this).get(ListLibraryViewModel.class);
        listLibraryViewModel.getResponseObserver().observe(this, libraries -> {
            if (libraries != null) {
                libraryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, libraries);
                libraryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.librarySpinner.setAdapter(libraryAdapter);
            }
        });
    }

    private void loadRoles() {
        listRoleViewModel = new ViewModelProvider(AddUserFragment.this).get(ListRoleViewModel.class);
        listRoleViewModel.getResponseObserver().observe(this, roles -> {
            if (roles != null) {
                List<String> roleNames = roles.stream().map(RoleDto::getRoleName).collect(Collectors.toList());
                roleAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, roleNames);
                roleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.roleSpinner.setAdapter(roleAdapter);
            }
        });
    }

    private void createUser(String username, String password, String firstName, String lastName, Long libraryId, String role) {
        Retrofit retrofit = RetrofitConfig.getInstance();
        UserRepository userRepository = retrofit.create(UserRepository.class);

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(firstName)
                || TextUtils.isEmpty(lastName) || libraryId == null || TextUtils.isEmpty(role)) {
            Toast.makeText(getContext(), "Попълнете всички полета!", Toast.LENGTH_SHORT).show();
            return;
        }

        UserRequestDto userRequestDto = new UserRequestDto(
                username,
                password,
                firstName,
                lastName,
                RoleType.getValue(role).toString().toLowerCase(),
                Collections.singletonList(libraryId));

        Call<UserDto> userDtoCall = userRepository.save(
                StringConstants.BEARER + AuthenticationUtils.getToken(getContext()), userRequestDto);
        userDtoCall.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (!response.isSuccessful()) {
                    ErrorMessage errorMessage = ErrorUtils.parseError(response);
                    Toast.makeText(getContext(), errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                listUserAdapter.addUser(response.body());
                dismiss();
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
