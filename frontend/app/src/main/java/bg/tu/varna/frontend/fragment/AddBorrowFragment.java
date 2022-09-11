package bg.tu.varna.frontend.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import bg.tu.varna.frontend.R;
import bg.tu.varna.frontend.activity.BorrowedBooksActivity;
import bg.tu.varna.frontend.adapter.ListBookAdapter;
import bg.tu.varna.frontend.adapter.ListBorrowAdapter;
import bg.tu.varna.frontend.config.RetrofitConfig;
import bg.tu.varna.frontend.model.ErrorMessage;
import bg.tu.varna.frontend.network.model.BookDto;
import bg.tu.varna.frontend.network.model.BorrowDto;
import bg.tu.varna.frontend.network.model.BorrowRequestDto;
import bg.tu.varna.frontend.network.model.UserDto;
import bg.tu.varna.frontend.repository.BorrowRepository;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import bg.tu.varna.frontend.utils.ErrorUtils;
import bg.tu.varna.frontend.utils.StringConstants;
import bg.tu.varna.frontend.view_model.ListOperatorBookViewModel;
import bg.tu.varna.frontend.view_model.ListUserViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddBorrowFragment extends DialogFragment {

    private ListUserViewModel listUserViewModel;
    private ArrayAdapter<UserDto> readerAdapter;
    private BorrowedBooksActivity activity;
    private Button btnOpenDatePicker;
    private LocalDateTime dateTo;
    private TextView textViewChosenData;
    private TextView addBorrowChosenData;
    private Spinner readerSpinner;
    private Spinner booksSpinner;
    private ListOperatorBookViewModel listOperatorBookViewModel;
    private ArrayAdapter<BookDto> bookAdapter;
    private Button btnАddBorrow;
    private ListBorrowAdapter listBorrowAdapter;
    private String dateToFormatted;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_borrow, container, false);
        readerSpinner = view.findViewById(R.id.spinnerReaders);
        booksSpinner = view.findViewById(R.id.spinnerBooks);
        initViewModel();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewChosenData = view.findViewById(R.id.textViewChosenData);
        addBorrowChosenData = view.findViewById(R.id.addBorrowChosenData);
        btnOpenDatePicker = view.findViewById(R.id.btnOpenDatePicker);
        btnАddBorrow = view.findViewById(R.id.btnАddBorrow);

        addBorrowChosenData.setVisibility(View.INVISIBLE);
        textViewChosenData.setVisibility(View.INVISIBLE);
        readerSpinner.setAdapter(readerAdapter);
        booksSpinner.setAdapter(bookAdapter);
        listUserViewModel.fetchAll(getContext(), true);
        listOperatorBookViewModel.fetchAll(getContext(), "available");
        btnOpenDatePicker.setOnClickListener(open -> {
            MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder
                    .dateRangePicker()
                    .setSelection(Pair.create(MaterialDatePicker.thisMonthInUtcMilliseconds(), MaterialDatePicker.todayInUtcMilliseconds())).build();

            materialDatePicker.show(activity.getSupportFragmentManager(), "date_picker");

            materialDatePicker.addOnPositiveButtonClickListener(datePickerView -> {
                Pair<Long, Long> selection = (Pair<Long, Long>) materialDatePicker.getSelection();
                dateTo = Instant.ofEpochMilli(selection.second).atZone(ZoneId.systemDefault()).toLocalDateTime();
                materialDatePicker.dismiss();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                dateToFormatted = dateTo.format(formatter);
                
                addBorrowChosenData.setText(dateToFormatted);
                addBorrowChosenData.setVisibility(View.VISIBLE);
                textViewChosenData.setVisibility(View.VISIBLE);
            });
        });

        btnАddBorrow.setOnClickListener(borrow -> {
            Long bookId = ((BookDto) booksSpinner.getSelectedItem()).getId();
            Long readerId = ((UserDto) readerSpinner.getSelectedItem()).getId();

            if (TextUtils.isEmpty(dateToFormatted) || bookId == null
                    || readerId == null) {
                Toast.makeText(getContext(), "Попълнете всички полета!", Toast.LENGTH_SHORT).show();
                return;
            }

            BorrowRequestDto borrowRequestDto = new BorrowRequestDto(bookId, readerId, dateToFormatted);
            borrow(borrowRequestDto);
        });
    }

    private void initViewModel() {
       loadReaders();
       loadBooks();
    }

    private void loadReaders() {
        listUserViewModel = new ViewModelProvider(AddBorrowFragment.this).get(ListUserViewModel.class);
        listUserViewModel.getResponseObserver().observe(this, users -> {
            if (users != null) {
                readerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, users);
                readerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.readerSpinner.setAdapter(readerAdapter);
            }
        });
    }

    private void loadBooks() {
        listOperatorBookViewModel = new ViewModelProvider(AddBorrowFragment.this).get(ListOperatorBookViewModel.class);
        listOperatorBookViewModel.getResponseObserver().observe(this, books -> {
            if (books != null) {
                bookAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, books);
                bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                booksSpinner.setAdapter(bookAdapter);
            }
        });
    }

    public void setActivity(BorrowedBooksActivity activity) {
        this.activity = activity;
    }

    public void setListBorrowAdapter(ListBorrowAdapter listBorrowAdapter) {
        this.listBorrowAdapter = listBorrowAdapter;
    }

    private void borrow(BorrowRequestDto borrowRequestDto) {
        Retrofit retrofit = RetrofitConfig.getInstance();
        BorrowRepository borrowRepository = retrofit.create(BorrowRepository.class);

        Call<BorrowDto> borrowDtoCall = borrowRepository.save(
                StringConstants.BEARER + AuthenticationUtils.getToken(getContext()), borrowRequestDto);

        borrowDtoCall.enqueue(new Callback<BorrowDto>() {
            @Override
            public void onResponse(Call<BorrowDto> call, Response<BorrowDto> response) {
                if (!response.isSuccessful()) {
                    ErrorMessage errorMessage = ErrorUtils.parseError(response);
                    Toast.makeText(getContext(), errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                listBorrowAdapter.addBorrow(response.body());
                dismiss();
            }

            @Override
            public void onFailure(Call<BorrowDto> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
