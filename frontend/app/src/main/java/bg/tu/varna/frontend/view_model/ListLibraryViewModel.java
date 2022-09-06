package bg.tu.varna.frontend.view_model;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import bg.tu.varna.frontend.config.RetrofitConfig;
import bg.tu.varna.frontend.model.ErrorMessage;
import bg.tu.varna.frontend.network.model.LibraryDto;
import bg.tu.varna.frontend.repository.LibraryRepository;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import bg.tu.varna.frontend.utils.ErrorUtils;
import bg.tu.varna.frontend.utils.StringConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListLibraryViewModel extends ViewModel {
    private MutableLiveData<List<LibraryDto>> librariesData;

    public ListLibraryViewModel() {
        this.librariesData = new MutableLiveData<>();
    }

    public MutableLiveData<List<LibraryDto>> getResponseObserver() {
        return librariesData;
    }

    public void fetchAll(Context context) {
        Retrofit retrofit = RetrofitConfig.getInstance();
        LibraryRepository libraryRepository = retrofit.create(LibraryRepository.class);
        Call<List<LibraryDto>> listCall = libraryRepository.fetchAll(
                StringConstants.BEARER + AuthenticationUtils.getToken(context));

        listCall.enqueue(new Callback<List<LibraryDto>>() {
            @Override
            public void onResponse(Call<List<LibraryDto>> call, Response<List<LibraryDto>> response) {
                if (!response.isSuccessful()) {
                    ErrorMessage errorMessage = ErrorUtils.parseError(response);
                    Toast.makeText(context, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                    librariesData.postValue(null);
                    return;
                }

                librariesData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<LibraryDto>> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
