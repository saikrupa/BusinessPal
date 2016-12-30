package businesspal.saikrupa.com.businesspal.helper;

import android.util.Log;

import java.util.Arrays;

/**
 * Created by Ndroid on 12/23/2016.
 */

public class AddTransaction {

    String transaction_id;
    int user_id = 0;
    int transaction_type = 1;
    int amount = 2;
    int date = 3;
    int due_amount = 4;
    int net_due_amount = 5;
    String[] transactionData;

    public AddTransaction() {

    }

    public AddTransaction(String[] transactionData) {
        this.transactionData = new String[transactionData.length];
        this.transactionData = transactionData;
        Log.v("initial", Arrays.toString(transactionData));

    }

    public AddTransaction(String transaction_id, String[] transactionData) {
        this.transaction_id = transaction_id;
        this.transactionData = new String[transactionData.length];
        Log.v("data", transaction_id + "/" + Arrays.toString(transactionData));
        this.transactionData = transactionData;
    }

    public String getTransactionId() {
        return transaction_id;
    }

    public void setTransactionId(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getUserId() {
        return transactionData[user_id];
    }

    public void setUserId(String user_id) {
        this.transactionData[this.user_id] = user_id;
    }

    public String getTransactionType() {
        return transactionData[transaction_type];
    }

    public void setTransactionType(String transactionType) {
        this.transactionData[transaction_type] = transactionType;
    }

    public String getAmount() {
        return transactionData[amount];
    }

    public void setAmount(String amount) {
        this.transactionData[this.amount] = amount;
    }

    public String getDate() {
        return transactionData[date];
    }

    public void setDate(String date) {
        this.transactionData[this.date] = date;
    }

    public String getDueAmount() {
        return transactionData[due_amount];
    }

    public void setDueAmount(String dueAmount) {
        this.transactionData[this.due_amount] = dueAmount;
    }

    public String getNetDueAmount() {
        return transactionData[net_due_amount];
    }

    public void setNetDueAmount(String netDue) {
        this.transactionData[this.net_due_amount] = netDue;
    }
}
