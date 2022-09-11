package bg.tu.varna.frontend.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bg.tu.varna.frontend.R;
import bg.tu.varna.frontend.activity.BorrowedBooksActivity;
import bg.tu.varna.frontend.fragment.BorrowDetailsFragment;
import bg.tu.varna.frontend.fragment.ReturnBookFragment;
import bg.tu.varna.frontend.network.model.BorrowDto;
import bg.tu.varna.frontend.network.model.BorrowReturnResponseDto;
import bg.tu.varna.frontend.utils.AuthenticationUtils;
import bg.tu.varna.frontend.utils.Permissions;
import bg.tu.varna.frontend.utils.common.RoleType;

public class ListBorrowAdapter  extends RecyclerView.Adapter<ListBorrowAdapter.BorrowHolder> {

    private List<BorrowDto> borrows = new ArrayList<>();
    private Context context;

    public BorrowedBooksActivity getBorrowedBooksActivity() {
        return borrowedBooksActivity;
    }

    public void setBorrowedBooksActivity(BorrowedBooksActivity borrowedBooksActivity) {
        this.borrowedBooksActivity = borrowedBooksActivity;
    }

    public RelativeLayout getRelativeLayout() {
        return relativeLayout;
    }

    public void setRelativeLayout(RelativeLayout relativeLayout) {
        this.relativeLayout = relativeLayout;
    }

    private BorrowedBooksActivity borrowedBooksActivity;
    private RelativeLayout relativeLayout;

    @NonNull
    @Override
    public BorrowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.borrow_item, parent, false);
        return new BorrowHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowHolder holder, int position) {
        BorrowDto borrowDto = borrows.get(position);
        holder.borrowId.setText(String.valueOf(borrowDto.getId()));
        holder.bookTitle.setText(borrowDto.getBook().getTitle());
        holder.reader.setText(borrowDto.getReader().getUsername());
        holder.status.setText(borrowDto.getDateReturn() == null ? "Не" : "Да");

        if (!AuthenticationUtils.getPermissions(context).contains(Permissions.MANAGE_BORROWS) || borrowDto.getDateReturn() != null) {
            holder.btnReturn.setVisibility(View.INVISIBLE);
        } else {
            holder.btnReturn.setOnClickListener(view -> {
                ReturnBookFragment returnBookFragment = new ReturnBookFragment();
                returnBookFragment.setBorrowAdapter(this);
                Bundle bundle = new Bundle();
                bundle.putParcelable("borrow", borrowDto);
                returnBookFragment.setArguments(bundle);
                returnBookFragment.show(borrowedBooksActivity.getSupportFragmentManager(), "return_book");
            });
        }

        relativeLayout.setOnClickListener(view -> {
            BorrowDetailsFragment borrowDetailsFragment = new BorrowDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("borrow", borrowDto);
            borrowDetailsFragment.setArguments(bundle);
            borrowDetailsFragment.show(borrowedBooksActivity.getSupportFragmentManager(), "borrow_details");
        });
    }

    @Override
    public int getItemCount() {
        return borrows.size();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<BorrowDto> getBorrows() {
        return borrows;
    }

    public void setBorrows(List<BorrowDto> borrows) {
        this.borrows = borrows;
        notifyDataSetChanged();
    }

    public void setBorrow(Long id, BorrowReturnResponseDto borrowReturn) {
        for (int index = 0; index <= borrows.size(); index++) {
            if (borrows.get(index).getId().equals(id)) {
                BorrowDto borrow = borrows.get(index);
                borrow.setDateReturn(borrowReturn.getDateReturned());
                borrow.getBook().setCondition(borrowReturn.getCondition());
                borrows.set(index, borrow);
                notifyItemChanged(index);
                return;
            }
        }
    }

    public void addBorrow(BorrowDto borrowDto) {
        borrows.add(borrowDto);
        notifyDataSetChanged();
    }

    class BorrowHolder extends RecyclerView.ViewHolder {
        private final TextView borrowId;
        private final TextView bookTitle;
        private final TextView reader;
        private final TextView status;
        private final Button btnReturn;

        public BorrowHolder(@NonNull View itemView) {
            super(itemView);
            borrowId = itemView.findViewById(R.id.borrowItemId);
            bookTitle = itemView.findViewById(R.id.borrowItemTitle);
            reader = itemView.findViewById(R.id.borrowItemReader);
            status = itemView.findViewById(R.id.borrowItemStatus);
            relativeLayout = itemView.findViewById(R.id.borrowItemLinearLayout);
            btnReturn = itemView.findViewById(R.id.btnReturnBook);
        }
    }
}
