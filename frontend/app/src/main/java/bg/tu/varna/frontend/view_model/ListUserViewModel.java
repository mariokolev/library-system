package bg.tu.varna.frontend.view_model;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import bg.tu.varna.frontend.config.RetrofitConfig;
import bg.tu.varna.frontend.model.ErrorMessage;
import bg.tu.varna.frontend.network.model.UserDto;
import bg.tu.varna.frontend.repository.UserRepository;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import bg.tu.varna.frontend.utils.ErrorUtils;
import bg.tu.varna.frontend.utils.StringConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListUserViewModel extends ViewModel {
    private MutableLiveData<List<UserDto>> usersData;

    public ListUserViewModel() {
        this.usersData = new MutableLiveData<>();
    }

    public MutableLiveData<List<UserDto>> getResponseObserver() {
        return usersData;
    }

    public void fetchAll(Context context) {
            Retrofit retrofit = RetrofitConfig.getInstance();
            UserRepository userRepository = retrofit.create(UserRepository.class);
            Call<List<UserDto>> userDtoCall = userRepository.fetchAll(
                    StringConstants.BEARER + AuthenticationUtils.getToken(context));

        userDtoCall.enqueue(new Callback<List<UserDto>>() {
            @Override
            public void onResponse(Call<List<UserDto>> call, Response<List<UserDto>> response) {
                if (!response.isSuccessful()) {
                    ErrorMessage errorMessage = ErrorUtils.parseError(response);
                    Toast.makeText(context, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                    usersData.postValue(null);
                    return;
                }

                usersData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<UserDto>> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void fetchAll(Context context, Boolean isActive) {
        Retrofit retrofit = RetrofitConfig.getInstance();
        UserRepository userRepository = retrofit.create(UserRepository.class);
        Call<List<UserDto>> userDtoCall = userRepository.fetchAll(
                StringConstants.BEARER + AuthenticationUtils.getToken(context), true);

        userDtoCall.enqueue(new Callback<List<UserDto>>() {
            @Override
            public void onResponse(Call<List<UserDto>> call, Response<List<UserDto>> response) {
                if (!response.isSuccessful()) {
                    ErrorMessage errorMessage = ErrorUtils.parseError(response);
                    Toast.makeText(context, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                    usersData.postValue(null);
                    return;
                }

                usersData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<UserDto>> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
