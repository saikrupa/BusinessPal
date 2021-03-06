package businesspal.saikrupa.com.businesspal.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import businesspal.saikrupa.com.businesspal.R;
import businesspal.saikrupa.com.businesspal.Transactions;
import businesspal.saikrupa.com.businesspal.helper.AddPayableHelper;

/**
 * Created by Ndroid on 12/22/2016.
 */

public class PayableListAdapter extends RecyclerView.Adapter {

    private List<AddPayableHelper> payableList;
    // instance of the main class will be stored here
    private Activity _context;
    private SparseBooleanArray mSelectedItemsIds;

    public PayableListAdapter(List<AddPayableHelper> payableList, Activity context) {
        this.payableList = payableList;
        _context = context;
        mSelectedItemsIds = new SparseBooleanArray();

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        AddPayableHelper singlePayableDetails = payableList.get(position);

        PayableListItemsViewHolder viewHolder = (PayableListItemsViewHolder) holder;

        viewHolder.tvName.setText(singlePayableDetails.getFirstName() + " " + singlePayableDetails.getLastName());
        viewHolder.tvPhoneNo.setText(singlePayableDetails.getPhoneNo());
        viewHolder.tvDate.setText(singlePayableDetails.getDate());
        viewHolder.tvAddress.setText(singlePayableDetails.getAddress());
        viewHolder.itemView
                .setBackgroundColor(mSelectedItemsIds.get(position) ? 0x9934B5E4
                        : Color.TRANSPARENT);
        viewHolder.payableItem = singlePayableDetails;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.payable_list_item, parent, false);
        vh = new PayableListItemsViewHolder(v);
        return vh;
    }

    @Override
    public int getItemCount() {
        return payableList.size();
    }


    //Toggle selection methods
    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }


    //Remove selected selections
    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }


    //Put or delete selected position into SparseBooleanArray
    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    //Get total selected count
    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    //Return all selected ids
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }





    private class PayableListItemsViewHolder extends RecyclerView.ViewHolder {

        public AddPayableHelper payableItem;
        TextView tvName;
        TextView tvPhoneNo;
        TextView tvAddress;
        TextView tvDate;

        public PayableListItemsViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tvname);
            tvPhoneNo = (TextView) v.findViewById(R.id.tvPhone);
            tvAddress = (TextView) v.findViewById(R.id.tvAddress);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(_context, Transactions.class);
                    i.putExtra("user_id", payableItem.getID());
                    i.putExtra("amount", payableItem.getAmount());
                    i.putExtra("name", payableItem.getFirstName());
                    _context.startActivity(i);
                }
            });

        }
    }
}
