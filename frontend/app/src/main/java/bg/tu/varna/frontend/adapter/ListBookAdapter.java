package bg.tu.varna.frontend.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bg.tu.varna.frontend.R;
import bg.tu.varna.frontend.activity.BookOperatorActivity;
import bg.tu.varna.frontend.fragment.BookDetailsFragment;
import bg.tu.varna.frontend.network.model.BookDto;

public class ListBookAdapter extends RecyclerView.Adapter<ListBookAdapter.BookHolder> {

    private List<BookDto> books = new ArrayList<>();
    private Context context;
    private BookOperatorActivity bookOperatorActivity;
    private LinearLayout linearLayout;

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

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            bookId = itemView.findViewById(R.id.bookItemBookId);
            title = itemView.findViewById(R.id.bookItemTitle);
            status = itemView.findViewById(R.id.bookItemStatus);
            linearLayout = itemView.findViewById(R.id.userItemLinearLayout);
        }
    }
}
