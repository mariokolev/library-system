package bg.tu.varna.frontend.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

import bg.tu.varna.frontend.R;
import bg.tu.varna.frontend.network.model.BorrowDto;

public class BorrowDetailsFragment extends DialogFragment {
    private BorrowDto borrow;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            BorrowDto borrow = bundle.getParcelable("borrow");
            TextView  borrowId = view.findViewById(R.id.borrowDetailsId);
            TextView bookId = view.findViewById(R.id.borrowDetailsBookId);
            TextView title = view.findViewById(R.id.borrowDetailsTitle);
            TextView readerId = view.findViewById(R.id.borrowDetailsReaderid);
            TextView readerUsername = view.findViewById(R.id.borrowDetailsReaderNickname);
            TextView operatorId = view.findViewById(R.id.borrowDetailsOperatorId);
            TextView operatorUsername = view.findViewById(R.id.borrowDetailsOperatorNickname);
            TextView dateAdded = view.findViewById(R.id.borrowDetailsDateAdded);
            TextView dateReturned = view.findViewById(R.id.borrowDetailsDateReturned);
            TextView condition = view.findViewById(R.id.borrowDetailsCondition);

            borrowId.setText(String.valueOf(borrow.getId()));
            bookId.setText(String.valueOf(borrow.getBook().getId()));
            title.setText(borrow.getBook().getTitle());
            readerId.setText(String.valueOf(borrow.getReader().getId()));
            readerUsername.setText(borrow.getReader().getUsername());
            operatorId.setText(String.valueOf(borrow.getOperator().getId()));
            operatorUsername.setText(borrow.getOperator().getUsername());
            dateAdded.setText(borrow.getDateAdded());
            dateReturned.setText(borrow.getDateReturn());
            condition.setText(borrow.getBook().getCondition());
        }

        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.borrow_details, container, false);
    }
}
