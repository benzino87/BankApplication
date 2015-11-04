package Project3;

/**************************************************************************
 * Child class of Account
 * @author Jason Bensel, Braedin Hendrickson
 * @version Project 3
 *************************************************************************/
public class CheckingAccount extends Account {

    /** Current value of Checking Account's monthly fee**/
    protected double monthlyFee;

    /**********************************************************************
     * Default constructor that sets the account identifier to 1
     *********************************************************************/
    public CheckingAccount(){
        this.identifier =1;
    }

    /**********************************************************************
     * Method that sets a desired monthly fee to current Checking Account
     * @param monthlyFee incoming monthly fee to be set.
     *********************************************************************/
    public void setMonthlyFee(double monthlyFee){
        this.monthlyFee = monthlyFee;
    }

    /**********************************************************************
     * Method that returns the current value of the Checking Account's
     * monthly fee.
     * @return value of current accounts monthly fee
     *********************************************************************/
    public double getMonthlyFee(){
        return monthlyFee;
    }

    /**********************************************************************
     * Overridden method that compares two objects' seperate variables and
     * returns an integer value based on which object is greater or less
     * than
     * @param account1 First desired account to be compared
     * @param account2 Second desired account to be compared
     * @return integer value based on which object has a greater variable.
     *********************************************************************/
    @Override
    public int compare(Account account1, Account account2) {
        if(account1.getAccountNumber() > account2.getAccountNumber()){
            return 1;
        }
        if(account1.getAccountNumber() < account2.getAccountNumber()){
            return -1;
        }
        if(account1.getAccountOwner().compareTo(account2.getAccountOwner())
                > 0){
            return 1;
        }
        if(account1.getAccountOwner().compareTo(account2.getAccountOwner())
                < 0){
            return -1;
        }
        if(account1.getDateOpened().before(account2.getDateOpened())){
            return 1;
        }
        if(account1.getDateOpened().after(account2.getDateOpened())){
            return -1;
        }
        return 0;
    }

    /**********************************************************************
     * Overridden method that uses parent toString but adds Checking
     * Account's monthly fee variable.
     * @return String representation of current Checking Account
     *********************************************************************/
    public String toString(){
        return super.toString() + '\t' + this.monthlyFee;
    }

}