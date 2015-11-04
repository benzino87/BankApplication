package Project3;

/**************************************************************************
 * Child of class Account
 * @author Jason Bensel, Braedin Hendrickson
 * @version Project 3
 *************************************************************************/
public class SavingsAccount extends Account {

    /** Current value of Savings Account's minimum balance **/
    protected double minBalance;
    /** Current value of Savings Account's interest rate **/
    protected double interestRate;

    /**********************************************************************
     * Default constructor that sets the account identifier to 2.
     *********************************************************************/
    public SavingsAccount(){
        this.identifier =2;
    }

    /**********************************************************************
     * Method that sets a desired interest rate.
     * @param interestRate Value of interest rate to be set
     *********************************************************************/
    public void setInterestRate(double interestRate){
        this.interestRate = interestRate;
    }

    /**********************************************************************
     * Method that returns the current value of the Savings Account's
     * interest rate.
     * @return Current value of interest rate
     *********************************************************************/
    public double getInterestRate(){
        return this.interestRate;
    }

    /**********************************************************************
     * Method that sets a desired minimum balance.
     * @param minBalanace Value of required minimum balance to be set
     *********************************************************************/
    public void setMinBalance(double minBalanace){
        this.minBalance = minBalanace;
    }

    /**********************************************************************
     * Method that returns the current value of the Savings Account's
     * minimum balance.
     * @return Current value of minimum balance.
     *********************************************************************/
    public double getMinBalance(){
        return this.minBalance;
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
     * Overridden method that uses parent toString but adds Savings
     * Account's minimum balance and interest rate variables.
     * @return String representation of current Savings Account
     *********************************************************************/
    public String toString(){
        return super.toString() + '\t' + this.minBalance + '\t' +
                this.interestRate;
    }
}