package bg.tu.varna.frontend.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import bg.tu.varna.frontend.R;
import bg.tu.varna.frontend.adapter.ListBorrowAdapter;
import bg.tu.varna.frontend.config.RetrofitConfig;
import bg.tu.varna.frontend.model.ErrorMessage;
import bg.tu.varna.frontend.network.model.BorrowDto;
import bg.tu.varna.frontend.network.model.BorrowReturnDto;
import bg.tu.varna.frontend.network.model.BorrowReturnResponseDto;
import bg.tu.varna.frontend.repository.BorrowRepository;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import bg.tu.varna.frontend.utils.ErrorUtils;
import bg.tu.varna.frontend.utils.StringConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReturnBookFragment extends DialogFragment {
    private BorrowDto borrowDto;
    private Button btnReturn;
    private EditText bookCondition;
    private ListBorrowAdapter borrowAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            borrowDto = bundle.getParcelable("borrow");
        }
        btnReturn = view.findViewById(R.id.returnBookFragmentBtn);
        bookCondition = view.findViewById(R.id.editTextBookCondition);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BorrowReturnDto borrowReturnDto = new BorrowReturnDto();
                borrowReturnDto.setBookId(borrowDto.getBook().getId());
                borrowReturnDto.setCondition(bookCondition.getText().toString());

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                String formatDateTime = now.format(formatter);
                borrowReturnDto.setDateReturned(formatDateTime);
                returnBook(borrowReturnDto);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.return_book, container, false);
    }

    public ListBorrowAdapter getBorrowAdapter() {
        return borrowAdapter;
    }

    public void setBorrowAdapter(ListBorrowAdapter borrowAdapter) {
        this.borrowAdapter = borrowAdapter;
    }

    private void returnBook(BorrowReturnDto borrowReturnDto) {
        Retrofit retrofit = RetrofitConfig.getInstance();
        BorrowRepository borrowRepository = retrofit.create(BorrowRepository.class);

        if (TextUtils.isEmpty(borrowReturnDto.getCondition())) {
            Toast.makeText(getContext(), "Попълнете състоянието на книгата!", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<BorrowReturnResponseDto> borrowCall = borrowRepository.returnBook(
                StringConstants.BEARER + AuthenticationUtils.getToken(getContext()),
                borrowDto.getId(),
                borrowReturnDto
        );

        borrowCall.enqueue(new Callback<BorrowReturnResponseDto>() {
            @Override
            public void onResponse(Call<BorrowReturnResponseDto> call, Response<BorrowReturnResponseDto> response) {
                if (!response.isSuccessful()) {
                    ErrorMessage errorMessage = ErrorUtils.parseError(response);
                    Toast.makeText(getContext(), errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                borrowAdapter.setBorrow(borrowDto.getId(), response.body());
                dismiss();
            }

            @Override
            public void onFailure(Call<BorrowReturnResponseDto> call, Throwable t) {

            }
        });
    }
}
