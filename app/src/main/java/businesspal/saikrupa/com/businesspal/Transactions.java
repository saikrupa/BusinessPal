package businesspal.saikrupa.com.businesspal;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import businesspal.saikrupa.com.businesspal.adapters.TransactionListAdapter;
import businesspal.saikrupa.com.businesspal.database.DatabaseHandler;
import businesspal.saikrupa.com.businesspal.helper.AddPayableHelper;
import businesspal.saikrupa.com.businesspal.helper.AddTransaction;

/**
 * Created by Ndroid on 12/24/2016.
 */

public class Transactions extends AppCompatActivity {
    DatabaseHandler db;
    RecyclerView rvPayableList;
    TransactionListAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    int totalAmount;
    int user_id;
    TextView tvAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_details_list);
        savedInstanceState = getIntent().getExtras();
        totalAmount = Integer.parseInt(savedInstanceState.getString("amount"));
        user_id = Integer.parseInt(savedInstanceState.getString("user_id"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        TextView tvName = (TextView) toolbar.findViewById(R.id.tvName);
        tvName.setText(savedInstanceState.getString("name"));
        tvAmount = (TextView) toolbar.findViewById(R.id.tvAmount);
        tvAmount.setText(totalAmount + "");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        rvPayableList = (RecyclerView) findViewById(R.id.rvPayableList);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispalyDialog();
            }
        });

        db = new DatabaseHandler(Transactions.this);
    }

    private void dispalyDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.pay_amount);
        final EditText etAmount = (EditText) dialog.findViewById(R.id.etAmount);
        final EditText etDate = (EditText) dialog.findViewById(R.id.etDate);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    // set the value after selecting date from dialog box
                    @Override
                    public void onDateSet(DatePicker view, int _year, int monthOfYear,
                                          int dayOfMonth) {
                        etDate.setText(monthOfYear + 1 + "/" + dayOfMonth + "/" + _year);
                        //day = dayOfMonth;
                        // month = monthOfYear;
                        // year = _year;
                    }
                };
                Calendar cal = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(Transactions.this, listener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        Button btnLoan = (Button) dialog.findViewById(R.id.btnLoan);
        Button btnPay = (Button) dialog.findViewById(R.id.btnPay);
        btnLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = etAmount.getText().toString();
                String date = etDate.getText().toString();
                if (amount.length() > 0 && !date.equals("")) {
                    Toast.makeText(getApplicationContext(), amount + "Take as loan", Toast.LENGTH_SHORT).show();
                    addDataToTransactionsTable(date, amount, "add");
                    dialog.dismiss();
                } else
                    Toast.makeText(getApplicationContext(), "Fill all details", Toast.LENGTH_SHORT).show();

            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = etAmount.getText().toString();
                String date = etDate.getText().toString();
                if (amount.length() > 0 && !date.equals("")) {
                    if (totalAmount >= Integer.parseInt(amount)) {
                        addDataToTransactionsTable(date, amount, "pay");
                        Toast.makeText(getApplicationContext(), amount + "paid from existing balance", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else
                        etAmount.setError("Enter less amount");
                } else
                    Toast.makeText(getApplicationContext(), "Fill all details", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.transactions, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) Transactions.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(Transactions.this.getComponentName()));
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
        rvPayableList.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        // use a linear layout manager
        rvPayableList.setLayoutManager(mLinearLayoutManager);
    }

    private void addDataToTransactionsTable(String date, String amount, String tag) {
        int variableAmount = Integer.parseInt(amount);
        int dueAmount = totalAmount;
        if (tag.equals("add"))
            totalAmount = totalAmount + variableAmount;
        else
            totalAmount = totalAmount - variableAmount;
        AddPayableHelper payable = db.getPayable(user_id);
        payable.setAmount(totalAmount + "");
        db.updatePayable(payable);
        tvAmount.setText(totalAmount + "");
        db.addAmount(new AddTransaction(new String[]{user_id + "", tag, amount, date, dueAmount + "", totalAmount + ""}));
        loadData();

    }

    private void loadData() {
        Log.d("Reading: ", "Reading all contacts..");
        List<AddTransaction> liTransactions = db.getAllTransaction(user_id + "");
        mAdapter = new TransactionListAdapter(liTransactions, Transactions.this);
        rvPayableList.setAdapter(mAdapter);
        Log.d("contacts length: ", liTransactions.size() + "");
        for (AddTransaction cn : liTransactions) {
            // Writing Contacts to log
            //Log.d("Name: ", log);
        }
    }
}
