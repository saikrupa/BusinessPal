package businesspal.saikrupa.com.businesspal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import businesspal.saikrupa.com.businesspal.AddNewPayable;
import businesspal.saikrupa.com.businesspal.Interfaces.RecyclerClick_Listener;
import businesspal.saikrupa.com.businesspal.Others.RecyclerTouchListener;
import businesspal.saikrupa.com.businesspal.Others.Toolbar_ActionMode_Callback;
import businesspal.saikrupa.com.businesspal.R;
import businesspal.saikrupa.com.businesspal.adapters.PayableListAdapter;
import businesspal.saikrupa.com.businesspal.database.DatabaseHandler;
import businesspal.saikrupa.com.businesspal.helper.AddPayableHelper;

/**
 * Created by Ndroid on 12/14/2016.
 */

public class Payable extends Fragment {
    DatabaseHandler db;
    RecyclerView rvPayableList;
    PayableListAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    private ActionMode mActionMode;
    List<AddPayableHelper> liPayable;
    public Payable() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payable, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        rvPayableList = (RecyclerView) view.findViewById(R.id.rvPayableList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddNewPayable.class);
                i.putExtra("transaction_type","payable");
                startActivity(i);
            }
        });

        db = new DatabaseHandler(getActivity());


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
        implementRecyclerViewClickListeners();
        rvPayableList.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        // use a linear layout manager
        rvPayableList.setLayoutManager(mLinearLayoutManager);
    }

    private void loadData() {
        Log.d("Reading: ", "Reading all contacts..");
        liPayable = db.getAllPayables("payable");
        mAdapter = new PayableListAdapter(liPayable, getActivity());
        rvPayableList.setAdapter(mAdapter);

        Log.d("contacts length: ", liPayable.size() + "");
        for (AddPayableHelper cn : liPayable) {
            String log = "Id: " + cn.getID() + " ,First Name: " + cn.getFirstName() + " ,Last Name: " + cn.getLastName() + ", phone" + cn.getPhoneNo() +
                    ", amount" + cn.getAmount() + ", address" + cn.getAddress();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
    }
    //Implement item click and long click over recycler view
    private void implementRecyclerViewClickListeners() {
        rvPayableList.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rvPayableList, new RecyclerClick_Listener() {
            @Override
            public void onClick(View view, int position) {
                //If ActionMode not null select item
                if (mActionMode != null)
                    onListItemSelect(position);
            }

            @Override
            public void onLongClick(View view, int position) {
                //Select item on long click
                onListItemSelect(position);
            }
        }));
    }

    //List item select method
    private void onListItemSelect(int position) {
        mAdapter.toggleSelection(position);//Toggle the selection

        boolean hasCheckedItems = mAdapter.getSelectedCount() > 0;//Check if any items are already selected or not


        if (hasCheckedItems && mActionMode == null)
            // there are some selected items, start the actionMode
            mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new Toolbar_ActionMode_Callback(getActivity(), null,mAdapter, liPayable, true));
        else if (!hasCheckedItems && mActionMode != null)
            // there no selected items, finish the actionMode
            mActionMode.finish();

        if (mActionMode != null)
            //set action mode title on item selection
            mActionMode.setTitle(String.valueOf(mAdapter.getSelectedCount()) + " selected");


    }
    //Set action mode null after use
    public void setNullToActionMode() {
        if (mActionMode != null)
            mActionMode = null;
    }

    //Delete selected rows
    public void deleteRows() {
        SparseBooleanArray selected = mAdapter
                .getSelectedIds();//Get selected ids
        //Loop all selected ids
        for (int i = (selected.size() - 1); i >= 0; i--) {
            if (selected.valueAt(i)) {
                AddPayableHelper payableHelper=liPayable.get(selected.keyAt(i));
                //If current id is selected remove the item via key
                liPayable.remove(selected.keyAt(i));
                //Toast.makeText(getActivity(),payableHelper.getID(),Toast.LENGTH_SHORT).show();
                db.deletePayable(payableHelper);
                mAdapter.notifyDataSetChanged();//notify adapter

            }
        }
        Toast.makeText(getActivity(), selected.size() + " item deleted.", Toast.LENGTH_SHORT).show();//Show Toast
        mActionMode.finish();//Finish action mode after use

    }
}