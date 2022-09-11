package bg.tu.varna.frontend.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import bg.tu.varna.frontend.R;
import bg.tu.varna.frontend.adapter.ListBookAdapter;
import bg.tu.varna.frontend.config.RetrofitConfig;
import bg.tu.varna.frontend.model.ErrorMessage;
import bg.tu.varna.frontend.network.model.BookDto;
import bg.tu.varna.frontend.network.model.BookRequestDto;
import bg.tu.varna.frontend.network.model.GenreDto;
import bg.tu.varna.frontend.network.model.ReadingRoomDto;
import bg.tu.varna.frontend.repository.BookRepository;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import bg.tu.varna.frontend.utils.ErrorUtils;
import bg.tu.varna.frontend.utils.StringConstants;
import bg.tu.varna.frontend.view_model.ListGenreViewModel;
import bg.tu.varna.frontend.view_model.ListReadingRoomViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddBookFragment extends DialogFragment {

    private ListGenreViewModel listGenreViewModel;
    private ListReadingRoomViewModel listReadingRoomViewModel;
    private ArrayAdapter<GenreDto> genreAdapter;
    private ArrayAdapter<ReadingRoomDto> readingRoomAdapter;
    private Spinner genreSpinner;
    private Spinner readingRoomSpinner;
    private ListBookAdapter listBookAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_book, container, false);
        genreSpinner = view.findViewById(R.id.spinnerGenres);
        initViewModel();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText title = view.findViewById(R.id.addTitle);
        EditText author = view.findViewById(R.id.addAuthor);
        Button btnAddBook = view.findViewById(R.id.btnDialogAddBook);

        readingRoomSpinner = view.findViewById(R.id.spinnerReadingRooms);
        readingRoomSpinner.setAdapter(readingRoomAdapter);
        genreSpinner.setAdapter(genreAdapter);
        listGenreViewModel.fetchAll(getContext());
        listReadingRoomViewModel.fetchAll(getContext());

        btnAddBook.setOnClickListener(addUserView -> {
             Long genreId = ((GenreDto) genreSpinner.getSelectedItem()).getId();
             Long readingRoomId = ((ReadingRoomDto) this.readingRoomSpinner.getSelectedItem()).getId();

             createBook(title.getText().toString(), author.getText().toString(), genreId, readingRoomId);
        });
    }

    private void initViewModel() {
        loadGenres();
        loadReadingRooms();
    }

    private void loadGenres() {
        listGenreViewModel = new ViewModelProvider(AddBookFragment.this).get(ListGenreViewModel.class);
        listGenreViewModel.getResponseObserver().observe(this, genres -> {
            if (genres != null) {
                genreAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, genres);
                genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.genreSpinner.setAdapter(genreAdapter);
            }
        });
    }

    private void loadReadingRooms() {
        listReadingRoomViewModel = new ViewModelProvider(AddBookFragment.this).get(ListReadingRoomViewModel.class);
        listReadingRoomViewModel.getResponseObserver().observe(this, readingRooms -> {
            if (readingRooms != null) {
                readingRoomAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, readingRooms);
                readingRoomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.readingRoomSpinner.setAdapter(readingRoomAdapter);
            }
        });
    }

    private void createBook(String title, String author, Long genreId, Long readingRoomId) {
        Retrofit retrofit = RetrofitConfig.getInstance();
        BookRepository bookRepository = retrofit.create(BookRepository.class);

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(author) || genreId == null
                || readingRoomId == null) {
            Toast.makeText(getContext(), "Попълнете всички полета!", Toast.LENGTH_SHORT).show();
            return;
        }

        BookRequestDto bookRequestDto = new BookRequestDto(
                title,
                author,
                genreId,
                readingRoomId
        );

        Call<BookDto> bookDtoCall = bookRepository.save(
                StringConstants.BEARER + AuthenticationUtils.getToken(getContext()), bookRequestDto);
        bookDtoCall.enqueue(new Callback<BookDto>() {
            @Override
            public void onResponse(Call<BookDto> call, Response<BookDto> response) {
                if (!response.isSuccessful()) {
                    ErrorMessage errorMessage = ErrorUtils.parseError(response);
                    Toast.makeText(getContext(), errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                listBookAdapter.addBook(response.body());
                dismiss();
            }

            @Override
            public void onFailure(Call<BookDto> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setListBookAdapter(ListBookAdapter listBookAdapter) {
        this.listBookAdapter = listBookAdapter;
    }
}
