package Project3;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

/**************************************************************************
 * Parent class for checking and savings accounts that has the default
 * requirements for an account.
 * @author Jason Bensel, Braedin Hendrickson
 * @version Project 3
 *************************************************************************/
public abstract class Account implements Serializable, Comparator<Account> {

    /** Current value of account number **/
    protected int number;
    /** Current name of account owner   **/
    protected String owner;
    /** Current opened date of account  **/
    protected GregorianCalendar dateOpened;
    /** Current opened date of account(converted to string) **/
    protected String date;
    /** Current value of account balance**/
    protected double balance;
    /** Current value of account identifier **/
    protected int identifier;

    /**********************************************************************
     * Default Constructor
     *********************************************************************/
    public Account(){
    }

    /**********************************************************************
     * Method that sets desired account number.
     * @param number incoming integer to be set as account number
     *********************************************************************/
    public void setAccountNumber(int number){
        this.number = number;
    }

    /**********************************************************************
     * Method that returns current account number.
     * @return number of current account
     *********************************************************************/
    public int getAccountNumber(){
        return this.number;
    }

    /**********************************************************************
     * Method that sets desired account owner.
     * @param owner incoming String to be set as account owner
     *********************************************************************/
    public void setAccountOwner(String owner){
        this.owner = owner;
    }

    /**********************************************************************
     * Method that returns current account owner.
     * @return owner of current account
     *********************************************************************/
    public String getAccountOwner(){
        return this.owner;
    }

    /**********************************************************************
     * Method that sets desired opened date of account.
     * @param dateOpened incoming date to be set as account date opened
     *********************************************************************/
    public void setDateOpened(GregorianCalendar dateOpened){
        this.dateOpened = dateOpened;
        Date date = dateOpened.getTime();
        this.date = new SimpleDateFormat("MM/dd/yyyy").format(date);
    }

    /**********************************************************************
     * Method that returns account opening date.
     * @return opened date of current account
     *********************************************************************/
    public GregorianCalendar getDateOpened(){
        return this.dateOpened;
    }

    /**********************************************************************
     * Method that sets the desired balance of the current account
     * @param balance incoming double to be set as account balance
     *********************************************************************/
    public void setAccountBalance(double balance){
        this.balance = balance;
    }

    /**********************************************************************
     *  Method to return balance of current account.
     * @return balance of current account.
     *********************************************************************/
    public double getAccountBalance() {
        return this.balance;
    }

    /**********************************************************************
     * Method used by GUI class to convert the Gregorian Calendar date of
     * this object to a Date object so it can be properly updated in the
     * JDateChooser.
     * @return converted GregorianCalendar date to Date object
     *********************************************************************/
    public Date getConvertGregToDate(){
        Date date = dateOpened.getTime();
        return date;
    }

    /**********************************************************************
     * Method to return the date in string form.
     * @return string date of account's date opened
     *********************************************************************/
    public String getDate(){
        return this.date;
    }

    /**********************************************************************
     * Method used to return the identifier of each type of account
     * 1 = Checking
     * 2 = Savings
     * @return integer based identifier for each account
     *********************************************************************/
    public int getIdentifier(){
        return this.identifier;
    }

    /**********************************************************************
     * Method used to convert all account instance variables to a string
     * so it can be properly represented in the JTable.
     * @return String representation of account
     *********************************************************************/
    public String toString(){
        return "" +this.number + '\t' + this.owner + '\t' + this.date + '\t' + this.balance;
    }

}
