package businesspal.saikrupa.com.businesspal.helper;

/**
 * Created by Ndroid on 12/21/2016.
 */

public class AddPayableHelper {

    String payable_id;
    int payable_first_name = 0;
    int payable_last_name = 1;
    int payable_phone = 2;
    int payable_amout = 3;
    int payable_address = 4;
    int payable_date = 5;
    int credit_type=6;
    String[] payableData;

    public AddPayableHelper() {

    }

    public AddPayableHelper(String[] payableData) {
        this.payableData = new String[payableData.length];
        this.payableData = payableData;
    }

    public AddPayableHelper(String payable_id, String[] payableData) {
        this.payable_id = payable_id;
        this.payableData = new String[payableData.length];
        this.payableData = payableData;
    }

    public String getID() {
        return payable_id;
    }

    // setting id
    public void setID(String id) {
        payable_id = id;
    }

    // getting first name
    public String getFirstName() {
        return payableData[payable_first_name];
    }

    // setting first name
    public void setFirstName(String firstName) {
        payableData[payable_first_name] = firstName;
    }

    // getting last name
    public String getLastName() {
        return payableData[payable_last_name];
    }

    // setting last name
    public void setLastName(String lastName) {
        payableData[payable_last_name] = lastName;
    }

    // getting number
    public String getPhoneNo() {
        return payableData[payable_phone];
    }

    // setting number
    public void setPhoneNo(String phoneNo) {
        payableData[payable_phone] = phoneNo;
    }

    // getting amount
    public String getAmount() {
        return payableData[payable_amout];
    }

    // setting amount
    public void setAmount(String amount) {
        payableData[payable_amout] = amount;
    }

    // getting address
    public String getAddress() {
        return payableData[payable_address];
    }

    // setting address
    public void setAddress(String address) {
        payableData[payable_address] = address;
    }

    // getting address
    public String getDate() {
        return payableData[payable_date];
    }

    // setting date
    public void setDate(String date) {
        payableData[payable_date] = date;
    }
    public String getCreditType(){
        return payableData[credit_type];
    }

}
