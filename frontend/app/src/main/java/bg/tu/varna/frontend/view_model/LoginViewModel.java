package bg.tu.varna.frontend.view_model;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import bg.tu.varna.frontend.config.RetrofitConfig;
import bg.tu.varna.frontend.model.ErrorMessage;
import bg.tu.varna.frontend.model.User;
import bg.tu.varna.frontend.network.LoginRequest;
import bg.tu.varna.frontend.network.model.LoginResponse;
import bg.tu.varna.frontend.repository.UserRepository;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import bg.tu.varna.frontend.utils.ErrorUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginResponse> loginResponseData;

    public LoginViewModel() {
        this.loginResponseData = new MutableLiveData<>();
    }

    public MutableLiveData<LoginResponse> getResponseObserver() {
        return loginResponseData;
    }

    public void login(Context context, String username, String password) {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            LoginRequest request = new LoginRequest(username, password);
            Retrofit retrofit = RetrofitConfig.getInstance();
            UserRepository userRepository = retrofit.create(UserRepository.class);
            Call<LoginResponse> login = userRepository.login(request);
            login.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (!response.isSuccessful()) {
                        ErrorMessage errorMessage = ErrorUtils.parseError(response);
                        Toast.makeText(context, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                        loginResponseData.postValue(null);
                        return;
                    }

                    loginResponseData.postValue(response.body());

                    User loggedUser = new User(
                            response.body().getId(),
                            response.body().getUsername(),
                            response.body().getRole(),
                            response.body().getPermissions(),
                            response.body().getToken(),
                            response.body().getLibraryId()
                    );
                    AuthenticationUtils.setUser(context, loggedUser);
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    loginResponseData.postValue(null);
                }
            });
        } else {
            Toast.makeText(context, "Попълнете полетата", Toast.LENGTH_SHORT).show();
        }
    }
}
