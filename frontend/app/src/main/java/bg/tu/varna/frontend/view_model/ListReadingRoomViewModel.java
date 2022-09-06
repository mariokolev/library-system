package bg.tu.varna.frontend.view_model;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import bg.tu.varna.frontend.config.RetrofitConfig;
import bg.tu.varna.frontend.model.ErrorMessage;
import bg.tu.varna.frontend.network.model.ReadingRoomDto;
import bg.tu.varna.frontend.repository.ReadingRoomRepository;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import bg.tu.varna.frontend.utils.ErrorUtils;
import bg.tu.varna.frontend.utils.StringConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListReadingRoomViewModel extends ViewModel {
    private MutableLiveData<List<ReadingRoomDto>> readingRoomsData;

    public ListReadingRoomViewModel() {
        this.readingRoomsData = new MutableLiveData<>();
    }

    public MutableLiveData<List<ReadingRoomDto>> getResponseObserver() {
        return readingRoomsData;
    }

    public void fetchAll(Context context) {
        Retrofit retrofit = RetrofitConfig.getInstance();
        ReadingRoomRepository readingRoomRepository = retrofit.create(ReadingRoomRepository.class);
        Call<List<ReadingRoomDto>> listCall = readingRoomRepository.fetchAll(
        StringConstants.BEARER + AuthenticationUtils.getToken(context));

        listCall.enqueue(new Callback<List<ReadingRoomDto>>() {
            @Override
            public void onResponse(Call<List<ReadingRoomDto>> call, Response<List<ReadingRoomDto>> response) {
                if (!response.isSuccessful()) {
                    ErrorMessage errorMessage = ErrorUtils.parseError(response);
                    Toast.makeText(context, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                    readingRoomsData.postValue(null);
                    return;
                }

                readingRoomsData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ReadingRoomDto>> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }
}