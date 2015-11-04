package Project3;

import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**************************************************************************
 * Bank model class that extends an AbstractTableModel to handle all GUI
 * operations involving: adding, removing, updating, and sorting accounts.
 * @author Jason Bensel, Braedin Hendrickson
 * @version Project 3
 *************************************************************************/
public class BankModel extends AbstractTableModel {

    /** Current ArrayList that holds all accounts**/
    private ArrayList<Account> acts;
    /** Current value of table column names**/
    private String[] columnNames = {"Number", "Account Owner",
            "Date Opened", "Current Balance"};

    /**********************************************************************
     * Default constructor that instantiates a new ArrayList
     *********************************************************************/
    public BankModel(){
        acts = new ArrayList<>();
    }

    /**********************************************************************
     * Helper method used by bankModel and gui designed to find position of
     * account number and return an int value based on if the account
     * number exists or not.
     * @param number Incoming account number
     * @return Position in ArrayList of desired account
     *********************************************************************/
    public int findAccountNumber(int number){
        Account temp;
        for(int scan = 0; scan < acts.size(); scan++){
            temp = acts.get(scan);
            if(number == temp.getAccountNumber()){
                return scan;
            }
        }
        return -1;
    }

    /**********************************************************************
     * Uses helper method to find make sure the account number does not
     * already exist, then adds account to ArrayList and updates the
     * JTable.
     * @param newAcc desired account to be added to the ArrayList
     *********************************************************************/
    public void addAccount(Account newAcc) {
        if (findAccountNumber(newAcc.getAccountNumber()) == -1) {
            acts.add(newAcc);
            fireTableDataChanged();
        }
    }


    /**********************************************************************
     * Uses helper method to scan the ArrayList to search for desired
     * account number, then deletes that Account and updates the JTable.
     * @param number Desired Account number to be deleted
     *********************************************************************/
    public int deleteAccount(int number) {
        int temp = findAccountNumber(number);
        if(temp != -1) {
            acts.remove(temp);
            fireTableDataChanged();
        }
        return 1;
    }

    /**********************************************************************
     * Method used by the GUI to get the ArrayList for mouseListener
     * actions
     * @return Current ArrayList
     *********************************************************************/
    public ArrayList<Account> getAccounts(){
        return acts;
    }


    /**********************************************************************
     * Method used by GUI to sort the accounts by order of the account
     * number then updates the JTable.
     *********************************************************************/
    public void sortByAccountNumber(){
        Collections.sort(acts, new Account() {
            @Override
            public int compare(Account account1, Account account2) {
                if(account1.getAccountNumber() >
                        account2.getAccountNumber()){
                    return 1;
                }
                if(account1.getAccountNumber() <
                        account2.getAccountNumber()){
                    return -1;
                }
                return 0;
            }
        });
        fireTableDataChanged();
    }

    /**********************************************************************
     * Method used by GUI to sort the accounts by order of the account
     * name then updates the JTable.
     *********************************************************************/
    public void sortByAccountName(){
        Collections.sort(acts, new Account() {
            @Override
            public int compare(Account account1, Account account2) {
                if(account1.getAccountOwner().compareTo
                        (account2.getAccountOwner()) < 0){
                    return -1;
                }
                if(account1.getAccountOwner().compareTo
                        (account2.getAccountOwner()) > 0){
                    return 1;
                }
                return 0;
            }
        });
        fireTableDataChanged();
    }

    /**********************************************************************
     * Method used by GUI to sort the accounts by order of the account's
     * opened date then updates the JTable.
     *********************************************************************/
    public void sortByDateOpened(){
        Collections.sort(acts, new Account() {
            @Override
            public int compare(Account account1, Account account2) {
                if(account1.getDateOpened().after
                        (account2.getDateOpened())){
                    return 1;
                }
                if(account1.getDateOpened().before
                        (account2.getDateOpened())){
                    return -1;
                }
                return 0;
            }
        });
        fireTableDataChanged();
    }

    /**********************************************************************
     * Saves ArrayList to a text-based file.
     * @param filename desired name of file.
     *********************************************************************/
    public void saveTextFile(String filename) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter
                    (new FileWriter(filename)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        for(Account account: acts){
            //Automatically calls account's toString
            out.println(account);
        }
        out.close();
    }

    /**********************************************************************
     * Loads the file name and populates the JTable accordingly
     * @param filename desired file to be loaded
     *********************************************************************/
    public void loadTextFile(String filename) throws ParseException ,
            FileNotFoundException, IOException{

            // open the data file
            Scanner fileReader = new Scanner(new File(filename));

            // read one String in of data and an int
            ArrayList<Account> acnts = new ArrayList<>();
            while(fileReader.hasNextLine()) {
                acnts.add(convertStringToAccount(fileReader.nextLine()));
            }

            acts = acnts;
            fireTableDataChanged();


    }

    /**********************************************************************
     * Saves the ArrayList to a binary based file.
     * @param filename desired name of file
     * @throws IOException
     *********************************************************************/
    public void saveToBinaryFile(String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename+".dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(acts);
    }

    /**********************************************************************
     * Loads the binary file then populates the JTable accordingly
     * @param filename desired file to be loaded
     * @throws FileNotFoundException
     * @throws IOException
     *********************************************************************/
    public void readFromBinaryFile(String filename) throws
            FileNotFoundException, IOException{
        try {
            FileInputStream fis = new FileInputStream(filename + "dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Account> read = (ArrayList<Account>)ois.readObject();
            acts = read;
            fireTableDataChanged();
        }
       catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**********************************************************************
     * Helper method designed to convert toString back to an account by
     * splitting the string at each 'tab' position then stores the values
     * in an array.
     * @param incoming desired string to be converted
     * @return returns an Account with all the variables in the current
     * string.
     *********************************************************************/
    private Account convertStringToAccount(String incoming)
            throws ParseException {
        String[] sTemp;
        int number;
        String owner;
        double balance;
        GregorianCalendar dateOpened = new GregorianCalendar();
        double monthlyFee;
        double insterestRate;
        double minimumBalance;

        sTemp = incoming.split("\\t");
        //Checks length of string to determine if it is a Checking or
        //Savings account.
        if (sTemp.length == 5) {
            CheckingAccount temp = new CheckingAccount();
            for (int pos = 0; pos < sTemp.length - 1; pos++) {
                switch (pos) {
                    case 0:
                        number = Integer.parseInt(sTemp[0]);
                        temp.setAccountNumber(number);
                        break;
                    case 1:
                        owner = sTemp[1];
                        temp.setAccountOwner(owner);
                        break;
                    case 2:
                        String dateString = sTemp[2];
                        SimpleDateFormat format = new SimpleDateFormat
                                ("MM/dd/yyyy");
                        Date date = format.parse(dateString);
                        dateOpened.setTime(date);
                        temp.setDateOpened(dateOpened);
                        break;
                    case 3:
                        balance = Double.parseDouble(sTemp[3]);
                        temp.setAccountBalance(balance);
                        break;
                    case 4:
                        monthlyFee = Double.parseDouble(sTemp[4]);
                        temp.setMonthlyFee(monthlyFee);
                }
            }
            return temp;
        }
        if (sTemp.length == 6) {
            SavingsAccount temp = new SavingsAccount();
            for (int pos = 0; pos < sTemp.length - 1; pos++) {
                switch (pos) {
                    case 0:
                        number = Integer.parseInt(sTemp[0]);
                        temp.setAccountNumber(number);
                        break;
                    case 1:
                        owner = sTemp[1];
                        temp.setAccountOwner(owner);
                        break;
                    case 2:
                        String dateString = sTemp[2];
                        SimpleDateFormat format = new SimpleDateFormat
                                ("MM/dd/yyyy");
                        Date date = format.parse(dateString);
                        dateOpened.setTime(date);
                        temp.setDateOpened(dateOpened);
                    case 3:
                        balance = Double.parseDouble(sTemp[3]);
                        temp.setAccountBalance(balance);
                        break;
                    case 4:
                        minimumBalance = Double.parseDouble(sTemp[4]);
                        temp.setMinBalance(minimumBalance);
                        break;
                    case 5:
                        insterestRate = Double.parseDouble(sTemp[5]);
                        temp.setInterestRate(insterestRate);
                        break;

                }
            }
            return temp;
        }
        return null;
    }

    /**********************************************************************
     * Method used to determine the size of the ArrayList.
     * @return integer value based on ArrayList size.
     *********************************************************************/
    @Override
    public int getRowCount() {
        return this.acts.size();
    }

    /**********************************************************************
     * Method used to determine the number of columns the GUI will
     * represent in the JTable
     * @return int value based on desired columns.
     *********************************************************************/
    @Override
    public int getColumnCount() {
        return 4;
    }

    /**********************************************************************
     * Method used to determine the names of columns the GUI will
     * represent in the JTable.
     * @param index current position in the array.
     * @return Name of each column at position index.
     *********************************************************************/
    @Override
    public String getColumnName(int index){
        return columnNames[index];
    }

    /**********************************************************************
     * Method used by the GUI that places account variables in the
     * appropriate columns in the JTable
     * @param row position in the ArrayList
     * @param col position of the JTable column
     * @return Account variable (Determined by 'col')
     *********************************************************************/
    @Override
    public Object getValueAt(int row, int col) {
        Account acc = acts.get(row);

        switch (col){
            case 0:
                return acc.getAccountNumber();
            case 1:
                return acc.getAccountOwner();
            case 2:
                return acc.getDate();
            case 3:
                return acc.getAccountBalance();
        }
        return null;
    }
}