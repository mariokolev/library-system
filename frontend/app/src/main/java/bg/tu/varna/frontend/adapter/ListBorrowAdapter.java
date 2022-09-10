package bg.tu.varna.frontend.adapter;

import android.content.Context;
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
import bg.tu.varna.frontend.network.model.BorrowDto;

public class ListBorrowAdapter  extends RecyclerView.Adapter<ListBorrowAdapter.BorrowHolder> {

    private List<BorrowDto> borrows = new ArrayList<>();
    private Context context;
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

        holder.btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("book is returned");
            }
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
