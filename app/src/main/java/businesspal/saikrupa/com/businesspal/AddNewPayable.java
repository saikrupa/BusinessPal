package businesspal.saikrupa.com.businesspal;


import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import businesspal.saikrupa.com.businesspal.database.DatabaseHandler;
import businesspal.saikrupa.com.businesspal.helper.AddPayableHelper;
import businesspal.saikrupa.com.businesspal.helper.CustomizeDialog;

/**
 * Created by Ndroid on 12/19/2016.
 */

public class AddNewPayable extends AppCompatActivity {

    EditText etFName;
    EditText etLName;
    EditText etAddress;
    AutoCompleteTextView actvPhoneNo;
    EditText etAmount;
    TextView tvDate;
    String creditType;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_payable);
        savedInstanceState=getIntent().getExtras();
        creditType=savedInstanceState.getString("transaction_type");
        initialize();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));


    }

    private void initialize() {

        etFName = (EditText) findViewById(R.id.etFName);
        etLName = (EditText) findViewById(R.id.etLName);
        etAddress = (EditText) findViewById(R.id.etAddress);
        actvPhoneNo = (AutoCompleteTextView) findViewById(R.id.actvNumber);
        etAmount = (EditText) findViewById(R.id.etAmount);
        tvDate = (TextView) findViewById(R.id.tvDate);
        db = new DatabaseHandler(AddNewPayable.this);

        actvPhoneNo.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.single_contact, R.id.tv_ContactNumber, getAllContactNames()));

    }

    public void add(View v) {
        String fname = etFName.getText().toString();
        String lname = etLName.getText().toString();
        String address = etAddress.getText().toString();
        String amount = etAmount.getText().toString();
        String phoneNo = actvPhoneNo.getText().toString();
        String date = tvDate.getText().toString();

        if (fname.length() > 0 && lname.length() > 0 && address.length() > 0 && amount.length() > 0 && phoneNo.length() > 0 && !date.equals("Date")) {
            boolean is_inserted = db.addPayable(new AddPayableHelper(new String[]{fname, lname, phoneNo, amount, address, date,creditType}));
            if (is_inserted) {
                Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_SHORT).show();
                //db.addAmount(new AddTransaction(new String[]{user_id+"",tag,amount,"26-12-2016",dueAmount+"",totalAmount+""}));
                finish();
            } else {
                showDialog("User already exist with same phone number");
            }

        } else {
            showDialog("You need fill all details to create a user");
        }
    }

    public void showDialog(String message) {
        CustomizeDialog cd = new CustomizeDialog(AddNewPayable.this);
        cd.setMessage(message);
        cd.show();
    }

    public void cancel(View v) {
        finish();
    }


    public void selectDate(View v) {

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            // set the value after selecting date from dialog box
            @Override
            public void onDateSet(DatePicker view, int _year, int monthOfYear,
                                  int dayOfMonth) {
                tvDate.setText(monthOfYear + 1 + "/" + dayOfMonth + "/" + _year);
                //day = dayOfMonth;
                // month = monthOfYear;
                // year = _year;
            }
        };
        Calendar cal = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,
                listener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dialog.show();

    }

    private List<String> getAllContactNames() {
        List<String> lContactNamesList = new ArrayList<>();
        try {
            // Get all Contacts
            Cursor lPeople = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if (lPeople != null) {
                while (lPeople.moveToNext()) {
                    // Add Contact's Name into the List
                    lContactNamesList.add(lPeople.getString(lPeople.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                }
            }
        } catch (NullPointerException e) {
            Log.e("getAllContactNames()", e.getMessage());
        }
        return lContactNamesList;
    }

}
