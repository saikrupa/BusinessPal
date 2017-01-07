package businesspal.saikrupa.com.businesspal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import businesspal.saikrupa.com.businesspal.helper.AddPayableHelper;
import businesspal.saikrupa.com.businesspal.helper.AddTransaction;

/**
 * Created by Ndroid on 12/21/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "BusinessPal";

    // payable table name
    private static final String TABLE_PAYABLE = "payable";
    private static final String TABLE_TRANSACTIONS = "transactions";
    // Contacts Table Columns names
    private static final String KEY_TRANSACTION_ID = "id";
    private static final String KEY_FNAME = "firstName";
    private static final String KEY_LNAME = "lastName";
    private static final String KEY_PH_NO = "phoneNo";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_DATE = "date";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_TRANSACTION_TYPE = "transaction_type";
    private static final String KEY_NET_DUE_AMOUNT = "net_due_amount";
    private static final String KEY_DUE_AMOUNT = "due_amount";
    private static final String KEY_CREDIT_TYPE = "creditType";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // table query
        String CREATE_PAYABLE_TABLE = "CREATE TABLE " + TABLE_PAYABLE + "("
                + KEY_USER_ID + " INTEGER PRIMARY KEY," + KEY_FNAME + " TEXT," + KEY_LNAME + " TEXT," + KEY_PH_NO + " TEXT," + KEY_AMOUNT + " TEXT," + KEY_DATE + " TEXT,"
                + KEY_ADDRESS + " TEXT," +KEY_CREDIT_TYPE + " TEXT"+ ")";
        String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_TRANSACTIONS + "("
                + KEY_TRANSACTION_ID + " INTEGER PRIMARY KEY," + KEY_USER_ID + " INTEGER," + KEY_TRANSACTION_TYPE +
                " TEXT," + KEY_AMOUNT + " TEXT," + KEY_DATE + " TEXT," + KEY_DUE_AMOUNT + " TEXT," + KEY_NET_DUE_AMOUNT + " TEXT" + ")";
        // execute query
        Log.v("query", CREATE_PAYABLE_TABLE);
        db.execSQL(CREATE_PAYABLE_TABLE);
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYABLE);
            // Create tables again
            onCreate(db);
    }

    // doing transaction
    public boolean addAmount(AddTransaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // values.put(KEY_TRANSACTION_ID, transaction.getTransactionId()); // Contact First Name
        Log.v("database", transaction.getUserId() + "/" + transaction.getTransactionType() + "/" + transaction.getAmount() + "/" + transaction.getDueAmount());
        values.put(KEY_USER_ID, transaction.getUserId()); // Contact Last Name
        values.put(KEY_TRANSACTION_TYPE, transaction.getTransactionType()); // Contact Last Name
        values.put(KEY_AMOUNT, transaction.getAmount()); // Contact Last Name
        values.put(KEY_DATE, transaction.getDate()); // Contact Last Name
        values.put(KEY_DUE_AMOUNT, transaction.getDueAmount()); // Contact Last Name
        values.put(KEY_NET_DUE_AMOUNT, transaction.getNetDueAmount()); // Contact Last Name

        // Inserting Row
        db.insert(TABLE_TRANSACTIONS, null, values);
        db.close(); // Closing database connection
        return false;
    }

    // Getting All Transaction
    public List<AddTransaction> getAllTransaction(String user_id) {
        List<AddTransaction> transactionList = new ArrayList<AddTransaction>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTIONS + " WHERE " + KEY_USER_ID + " =?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{user_id});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding transactions to list
                transactionList.add(new AddTransaction(cursor.getString(0), new String[]{cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)}));
            } while (cursor.moveToNext());
        }

        // return contact list
        return transactionList;
    }


    // Adding new contact
    public boolean addPayable(AddPayableHelper payableHelper) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, payableHelper.getFirstName()); // Contact First Name
        values.put(KEY_LNAME, payableHelper.getLastName()); // Contact Last Name
        values.put(KEY_PH_NO, payableHelper.getPhoneNo()); // Contact Last Name
        values.put(KEY_ADDRESS, payableHelper.getAddress()); // Contact Last Name
        values.put(KEY_AMOUNT, payableHelper.getAmount()); // Contact Last Name
        values.put(KEY_DATE, payableHelper.getDate()); // Contact Last Name
        values.put(KEY_CREDIT_TYPE,payableHelper.getCreditType()); // assigning the creditType to table

        if (!hasObject(payableHelper.getPhoneNo())) {
            // Inserting Row
            db.insert(TABLE_PAYABLE, null, values);
            db.close(); // Closing database connection
            return true;
        } else {
            return false;
        }
    }

    // Getting single contact
    public AddPayableHelper getPayable(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PAYABLE, new String[]{KEY_USER_ID,
                        KEY_FNAME, KEY_LNAME, KEY_ADDRESS, KEY_AMOUNT, KEY_PH_NO, KEY_DATE}, KEY_USER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        String[] data = {cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)};
        AddPayableHelper payableHelper = new AddPayableHelper(cursor.getString(0), data);
        // return contact
        return payableHelper;
    }

    // check duplicate payable with phoneNumber
    public boolean hasObject(String phoneNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectString = "SELECT * FROM " + TABLE_PAYABLE + " WHERE " + KEY_PH_NO + " =?";

        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(selectString, new String[]{phoneNo});

        boolean hasObject = false;
        if (cursor.moveToFirst()) {
            hasObject = true;
        }
        //cursor.close();          // Dont forget to close your cursor
        //db.close();              //AND your Database!
        return hasObject;
    }

    // Getting All Contacts
    public List<AddPayableHelper> getAllPayables(String credit_type) {
        List<AddPayableHelper> payerList = new ArrayList<AddPayableHelper>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PAYABLE + " WHERE " + KEY_CREDIT_TYPE + " =?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{credit_type});

        // looping through all rows and adding to list s
        if (cursor.moveToFirst()) {
            do {
                // Adding contact to list
                payerList.add(new AddPayableHelper(cursor.getString(0), new String[]{cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)}));
            } while (cursor.moveToNext());
        }

        // return contact list
        return payerList;
    }

    // Updating single contact
    public int updatePayable(AddPayableHelper payable) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FNAME, payable.getFirstName());
        values.put(KEY_LNAME, payable.getLastName());
        values.put(KEY_PH_NO, payable.getPhoneNo());
        values.put(KEY_ADDRESS, payable.getAddress());
        values.put(KEY_AMOUNT, payable.getAmount());
        values.put(KEY_DATE, payable.getDate());

        // updating row
        return db.update(TABLE_PAYABLE, values, KEY_USER_ID + " = ?",
                new String[]{payable.getID()});
    }

    // Deleting single contact
    public void deletePayable(AddPayableHelper payable) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PAYABLE, KEY_USER_ID + " = ?",
                new String[]{String.valueOf(payable.getID())});
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PAYABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}