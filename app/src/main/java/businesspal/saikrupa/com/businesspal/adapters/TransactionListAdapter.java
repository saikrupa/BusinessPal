package businesspal.saikrupa.com.businesspal.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import businesspal.saikrupa.com.businesspal.R;
import businesspal.saikrupa.com.businesspal.helper.AddTransaction;

/**
 * Created by Ndroid on 12/24/2016.
 */

public class TransactionListAdapter extends RecyclerView.Adapter {

    private List<AddTransaction> transactionList;
    private Activity _context;

    public TransactionListAdapter(List<AddTransaction> transactionList, Activity context) {

        this.transactionList = transactionList;
        this._context = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        AddTransaction singleTransactionDetails = transactionList.get(position);

        TransactionListItemsViewHolder viewHolder = (TransactionListItemsViewHolder) holder;

        viewHolder.tvDueAmount.setText(singleTransactionDetails.getDueAmount());
        viewHolder.tvAmount.setText(singleTransactionDetails.getAmount());
        viewHolder.tvDate.setText(singleTransactionDetails.getDate());
        viewHolder.tvNetDueAmount.setText(singleTransactionDetails.getNetDueAmount());
        viewHolder.transactionItem = singleTransactionDetails;
        if (singleTransactionDetails.getTransactionType().equals("add"))
            viewHolder.tvPaidAmountText.setText("Taken Amount");
        else
            viewHolder.tvPaidAmountText.setText("Paid Amount");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.transaction_item, parent, false);

        vh = new TransactionListItemsViewHolder(v);
        return vh;
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    private class TransactionListItemsViewHolder extends RecyclerView.ViewHolder {

        public AddTransaction transactionItem;
        TextView tvDueAmount;
        TextView tvAmount;
        TextView tvNetDueAmount;
        TextView tvDate;
        TextView tvPaidAmountText;

        public TransactionListItemsViewHolder(View v) {
            super(v);
            tvDueAmount = (TextView) v.findViewById(R.id.tvDueAmount);
            tvAmount = (TextView) v.findViewById(R.id.tvPaidAmount);
            tvNetDueAmount = (TextView) v.findViewById(R.id.tvNetDue);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
            tvPaidAmountText = (TextView) v.findViewById(R.id.tvPaidAmountText);


        }
    }

}
