package bg.tu.varna.frontend.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import bg.tu.varna.frontend.R;
import bg.tu.varna.frontend.network.model.BookDto;

public class BookDetailsFragment extends DialogFragment {
    private BookDto book;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            BookDto book = bundle.getParcelable("book");
            TextView bookId = view.findViewById(R.id.bookDetailsBookId);
            TextView title = view.findViewById(R.id.bookDetailsTitle);
            TextView author = view.findViewById(R.id.bookDetailsAuthor);
            TextView genre = view.findViewById(R.id.bookDetailsGenre);
            TextView readingRoomId = view.findViewById(R.id.bookDetailsReadingRoomId);
            TextView dateAdded = view.findViewById(R.id.bookDetailsDateAdded);
            TextView status = view.findViewById(R.id.bookDetailsStatus);


            bookId.setText(String.valueOf(book.getId()));
            title.setText(book.getTitle());
            author.setText(book.getAuthor());
            genre.setText(book.getGenre().getName());
            readingRoomId.setText(String.valueOf(book.getReadingRoomId()));
            dateAdded.setText(book.getDateAdded());
            status.setText(book.getStatus());
        }

        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.book_operator_details, container, false);
    }
}
