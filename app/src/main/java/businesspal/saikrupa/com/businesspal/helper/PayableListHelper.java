package businesspal.saikrupa.com.businesspal.helper;

/**
 * Created by Ndroid on 12/22/2016.
 */

public class PayableListHelper {


    private int payable_id = 0;
    private int payable_first_name = 1;
    private int payable_last_name = 2;
    private int payable_phone = 3;
    private int payable_amout = 4;
    private int payable_address = 5;

    private String[] payableList;


    public PayableListHelper() {

    }

    public PayableListHelper(String[] payableList) {
        this.payableList = payableList;
    }

    public String getPayableId() {
        return payableList[payable_id];
    }

    public void setPayableId(String id) {
        payableList[payable_id] = id;
    }

    public String getFirstName() {
        return payableList[payable_first_name];
    }

    // setting first name
    public void setFirstName(String firstName) {
        payableList[payable_first_name] = firstName;
    }

    // getting last name
    public String getLastName() {
        return payableList[payable_last_name];
    }

    // setting last name
    public void setLastName(String lastName) {
        payableList[payable_last_name] = lastName;
    }

    // getting number
    public String getPhoneNo() {
        return payableList[payable_phone];
    }

    // setting number
    public void setPhoneNo(String phoneNo) {
        payableList[payable_phone] = phoneNo;
    }

    // getting amount
    public String getAmount() {
        return payableList[payable_amout];
    }

    // setting amount
    public void setAmount(String amount) {
        payableList[payable_amout] = amount;
    }

    // getting address
    public String getAddress() {
        return payableList[payable_address];
    }

    // setting address
    public void setAddress(String address) {
        payableList[payable_address] = address;
    }


}
