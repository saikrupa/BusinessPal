package businesspal.saikrupa.com.businesspal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import businesspal.saikrupa.com.businesspal.AddNewPayable;
import businesspal.saikrupa.com.businesspal.R;
import businesspal.saikrupa.com.businesspal.adapters.PayableListAdapter;
import businesspal.saikrupa.com.businesspal.database.DatabaseHandler;
import businesspal.saikrupa.com.businesspal.helper.AddPayableHelper;

/**
 * Created by Ndroid on 12/14/2016.
 */

public class Receivable extends Fragment {

    RecyclerView rvReceivableList;
    DatabaseHandler db;
    PayableListAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receivable, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        rvReceivableList = (RecyclerView) view.findViewById(R.id.rvReceivableList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddNewPayable.class);
                i.putExtra("transaction_type","receivable");
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
        rvReceivableList.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        // use a linear layout manager
        rvReceivableList.setLayoutManager(mLinearLayoutManager);
    }

    private void loadData() {
        Log.d("Reading: ", "Reading all contacts..");
        List<AddPayableHelper> liPayable = db.getAllPayables("receivable");
        mAdapter = new PayableListAdapter(liPayable, getActivity());
        rvReceivableList.setAdapter(mAdapter);

        Log.d("contacts length: ", liPayable.size() + "");
        for (AddPayableHelper cn : liPayable) {
            String log = "Id: " + cn.getID() + " ,First Name: " + cn.getFirstName() + " ,Last Name: " + cn.getLastName() + ", phone" + cn.getPhoneNo() +
                    ", amount" + cn.getAmount() + ", address" + cn.getAddress();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }


    }

}