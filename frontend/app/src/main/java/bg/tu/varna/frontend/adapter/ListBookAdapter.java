package bg.tu.varna.frontend.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bg.tu.varna.frontend.R;
import bg.tu.varna.frontend.activity.BookOperatorActivity;
import bg.tu.varna.frontend.config.RetrofitConfig;
import bg.tu.varna.frontend.fragment.BookDetailsFragment;
import bg.tu.varna.frontend.model.ErrorMessage;
import bg.tu.varna.frontend.network.model.BookDto;
import bg.tu.varna.frontend.network.model.BookUpdateDto;
import bg.tu.varna.frontend.network.model.GenreDto;
import bg.tu.varna.frontend.network.model.UserDto;
import bg.tu.varna.frontend.repository.BookRepository;
import bg.tu.varna.frontend.repository.GenreRepository;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import bg.tu.varna.frontend.utils.ErrorUtils;
import bg.tu.varna.frontend.utils.StringConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListBookAdapter extends RecyclerView.Adapter<ListBookAdapter.BookHolder> {

    private List<BookDto> books = new ArrayList<>();
    private Context context;
    private BookOperatorActivity bookOperatorActivity;
    private RelativeLayout linearLayout;

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);
        return  new BookHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        BookDto book = books.get(position);
        holder.bookId.setText(String.valueOf(book.getId()));
        holder.title.setText(book.getTitle());
        holder.status.setText(book.getStatus());

        linearLayout.setOnClickListener(view -> {
            BookDetailsFragment userDetailsFragment = new BookDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("book", book);
            userDetailsFragment.setArguments(bundle);
            userDetailsFragment.show(bookOperatorActivity.getSupportFragmentManager(), "book_details");
        });

        holder.btnArchive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBookStatus(book, "archived");
            }
        });

        holder.btnWriteOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBookStatus(book, "write_off");
            }
        });
    }

    public void changeBookStatus (BookDto bookDto, String status) {
        BookUpdateDto bookUpdateDto = new BookUpdateDto();
        bookUpdateDto.setStatus(status);
        Retrofit retrofit = RetrofitConfig.getInstance();
        BookRepository bookRepository = retrofit.create(BookRepository.class);
        Call<BookDto> bookCall = bookRepository.updateStatus(
                StringConstants.BEARER + AuthenticationUtils.getToken(bookOperatorActivity),
                bookDto.getId(),
                bookUpdateDto
        );

        bookCall.enqueue(new Callback<BookDto>() {
            @Override
            public void onResponse(Call<BookDto> call, Response<BookDto> response) {
                if (!response.isSuccessful()) {
                    ErrorMessage errorMessage = ErrorUtils.parseError(response);
                    Toast.makeText(bookOperatorActivity, errorMessage.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }

                setBook(response.body());
                System.out.println(response.body().getStatus());
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

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(List<BookDto> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public void addBook(BookDto bookDto) {
        books.add(bookDto);
        notifyDataSetChanged();
    }

    public void setBook(BookDto bookDto) {
        for (int index = 0; index <= books.size(); index++) {
            if (books.get(index).getId().equals(bookDto.getId()) ) {
                books.set(index, bookDto);
                notifyItemChanged(index);
                return;
            }
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public BookOperatorActivity getBookOperatorActivity() {
        return bookOperatorActivity;
    }

    public void setBookOperatorActivity(BookOperatorActivity bookOperatorActivity) {
        this.bookOperatorActivity = bookOperatorActivity;
    }

    class BookHolder extends RecyclerView.ViewHolder {
        private final TextView bookId;
        private final TextView title;
        private final TextView status;
        private final Button btnArchive;
        private final Button btnWriteOff;

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            bookId = itemView.findViewById(R.id.bookItemBookId);
            title = itemView.findViewById(R.id.bookItemTitle);
            status = itemView.findViewById(R.id.bookItemStatus);
            btnArchive = itemView.findViewById(R.id.btnArchive);
            btnWriteOff = itemView.findViewById(R.id.btnWriteOff);
            linearLayout = itemView.findViewById(R.id.userItemLinearLayout);
        }
    }
}
