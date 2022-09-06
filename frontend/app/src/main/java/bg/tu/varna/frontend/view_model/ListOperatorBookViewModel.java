package bg.tu.varna.frontend.view_model;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import bg.tu.varna.frontend.config.RetrofitConfig;
import bg.tu.varna.frontend.model.ErrorMessage;
import bg.tu.varna.frontend.network.model.BookDto;
import bg.tu.varna.frontend.network.model.UserDto;
import bg.tu.varna.frontend.repository.BookRepository;
import bg.tu.varna.frontend.repository.UserRepository;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import bg.tu.varna.frontend.utils.ErrorUtils;
import bg.tu.varna.frontend.utils.StringConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListOperatorBookViewModel extends ViewModel {
    private MutableLiveData<List<BookDto>> booksData;

    public ListOperatorBookViewModel() {
        this.booksData = new MutableLiveData<>();
    }

    public MutableLiveData<List<BookDto>> getResponseObserver() {
        return booksData;
    }

    public void fetchAll(Context context) {
            Retrofit retrofit = RetrofitConfig.getInstance();
            BookRepository bookRepository = retrofit.create(BookRepository.class);
            Call<List<BookDto>> bookDtoCall = bookRepository.fetchAll(
                    StringConstants.BEARER + AuthenticationUtils.getToken(context));

        bookDtoCall.enqueue(new Callback<List<BookDto>>() {
            @Override
            public void onResponse(Call<List<BookDto>> call, Response<List<BookDto>> response) {
                if (!response.isSuccessful()) {
                    ErrorMessage errorMessage = ErrorUtils.parseError(response);
                    Toast.makeText(context, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                    booksData.postValue(null);
                    return;
                }

                booksData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<BookDto>> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
