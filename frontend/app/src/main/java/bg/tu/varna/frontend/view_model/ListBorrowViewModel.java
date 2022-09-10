package bg.tu.varna.frontend.view_model;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import bg.tu.varna.frontend.config.RetrofitConfig;
import bg.tu.varna.frontend.model.ErrorMessage;
import bg.tu.varna.frontend.network.model.BorrowDto;
import bg.tu.varna.frontend.network.model.UserDto;
import bg.tu.varna.frontend.repository.BorrowRepository;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import bg.tu.varna.frontend.utils.ErrorUtils;
import bg.tu.varna.frontend.utils.StringConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListBorrowViewModel extends ViewModel {
    private MutableLiveData<List<BorrowDto>> borrowsData;

    public ListBorrowViewModel() {
        this.borrowsData = new MutableLiveData<>();;
    }
    public MutableLiveData<List<BorrowDto>> getResponseObserver() {
        return borrowsData;
    }
    public void fetchAll(Context context) {
        Retrofit retrofit = RetrofitConfig.getInstance();
        BorrowRepository borrowRepository = retrofit.create(BorrowRepository.class);
        Call<List<BorrowDto>> borrowDtoCall = borrowRepository.fetchAll(
                StringConstants.BEARER + AuthenticationUtils.getToken(context)
        );

        borrowDtoCall.enqueue(new Callback<List<BorrowDto>>() {
            @Override
            public void onResponse(Call<List<BorrowDto>> call, Response<List<BorrowDto>> response) {
                if (!response.isSuccessful()) {
                    ErrorMessage errorMessage = ErrorUtils.parseError(response);
                    Toast.makeText(context, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                    borrowsData.postValue(null);
                    return;
                }

                borrowsData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<BorrowDto>> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
