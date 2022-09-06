package bg.tu.varna.frontend.view_model;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import bg.tu.varna.frontend.config.RetrofitConfig;
import bg.tu.varna.frontend.model.ErrorMessage;
import bg.tu.varna.frontend.network.model.GenreDto;
import bg.tu.varna.frontend.repository.GenreRepository;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import bg.tu.varna.frontend.utils.ErrorUtils;
import bg.tu.varna.frontend.utils.StringConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListGenreViewModel extends ViewModel {
    private MutableLiveData<List<GenreDto>> genresData;

    public ListGenreViewModel() {
        this.genresData = new MutableLiveData<>();
    }

    public MutableLiveData<List<GenreDto>> getResponseObserver() {
        return genresData;
    }

    public void fetchAll(Context context) {
        Retrofit retrofit = RetrofitConfig.getInstance();
        GenreRepository genreRepository = retrofit.create(GenreRepository.class);
        Call<List<GenreDto>> listCall = genreRepository.fetchAll(
                StringConstants.BEARER + AuthenticationUtils.getToken(context));

        listCall.enqueue(new Callback<List<GenreDto>>() {
            @Override
            public void onResponse(Call<List<GenreDto>> call, Response<List<GenreDto>> response) {
                if (!response.isSuccessful()) {
                    ErrorMessage errorMessage = ErrorUtils.parseError(response);
                    Toast.makeText(context, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                    genresData.postValue(null);
                    return;
                }

                genresData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<GenreDto>> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
