package bg.tu.varna.frontend.view_model;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import bg.tu.varna.frontend.config.RetrofitConfig;
import bg.tu.varna.frontend.model.ErrorMessage;
import bg.tu.varna.frontend.network.model.RoleDto;
import bg.tu.varna.frontend.repository.RoleRepository;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import bg.tu.varna.frontend.utils.ErrorUtils;
import bg.tu.varna.frontend.utils.StringConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListRoleViewModel extends ViewModel {

    private MutableLiveData<List<RoleDto>> rolesData;

    public ListRoleViewModel() {
        this.rolesData = new MutableLiveData<>();
    }

    public MutableLiveData<List<RoleDto>> getResponseObserver() {
        return rolesData;
    }

    public void fetchAll(Context context) {
        Retrofit retrofit = RetrofitConfig.getInstance();
        RoleRepository roleRepository = retrofit.create(RoleRepository.class);
        Call<List<RoleDto>> listCall = roleRepository.fetchAll(
                StringConstants.BEARER + AuthenticationUtils.getToken(context));

        listCall.enqueue(new Callback<List<RoleDto>>() {
            @Override
            public void onResponse(Call<List<RoleDto>> call, Response<List<RoleDto>> response) {
                if (!response.isSuccessful()) {
                    ErrorMessage errorMessage = ErrorUtils.parseError(response);
                    Toast.makeText(context, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                    rolesData.postValue(null);
                    return;
                }

                rolesData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<RoleDto>> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
