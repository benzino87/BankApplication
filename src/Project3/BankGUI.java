package Project3;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.*;

// check for account integer length, all other text field lengths
// clean-up and messages, method to check for illegal characters

public class BankGUI extends JFrame {

    private JTextField aNumber, aOwner, aBal, mFee, iRate, mBal;

    private JButton addButton, deleteButton, updateButton, clearButton;

    private JRadioButton checking, savings;

    private JMenuItem loadBAction, saveBAction, loadTAction,
            saveTAction, loadXAction, saveXAction, sortNumber, sortOwner, sortOpened, quit;

    private JTable table;

    private JMenuBar menuBar;

    private BankModel bankModel;

    private Account account;

    private JDateChooser dateChooser;

    public BankGUI() {


        super("Bank");

        bankModel = new BankModel();
        ButtonListener listener = new ButtonListener();
        // Creates a menubar for a JFrame
        menuBar = new JMenuBar();

        // Add the menubar to the frame
        setJMenuBar(menuBar);

        // Define and add two drop down menu to the menubar
        JMenu fileMenu = new JMenu("File");
        JMenu sortMenu = new JMenu("Sort");
        menuBar.add(fileMenu);
        menuBar.add(sortMenu);

        // Create and add simple menu item to one of the drop down menu
        loadBAction = new JMenuItem("Load From Binary");
        saveBAction = new JMenuItem("Save to Binary");


        loadTAction = new JMenuItem("Load From Text");
        saveTAction = new JMenuItem("Save to Text");


        loadXAction = new JMenuItem("Load From XML");
        saveXAction = new JMenuItem("Save to XML");


        sortNumber = new JMenuItem("By Account Number");
        sortOwner = new JMenuItem("By Account Owner");
        sortOpened = new JMenuItem("By Date Opened");


        quit = new JMenuItem("Quit");

        ButtonGroup bg = new ButtonGroup();

        fileMenu.add(loadBAction);
        fileMenu.add(saveBAction);
        fileMenu.addSeparator();
        fileMenu.add(loadTAction);
        fileMenu.add(saveTAction);
        fileMenu.addSeparator();
        fileMenu.add(loadXAction);
        fileMenu.add(saveXAction);
        fileMenu.addSeparator();
        fileMenu.add(quit);
        sortMenu.add(sortNumber);
        sortMenu.add(sortOwner);
        sortMenu.add(sortOpened);

        loadBAction.addActionListener(listener);
        saveBAction.addActionListener(listener);
        loadTAction.addActionListener(listener);
        saveTAction.addActionListener(listener);
        loadXAction.addActionListener(listener);
        saveXAction.addActionListener(listener);
        sortNumber.addActionListener(listener);
        sortOwner.addActionListener(listener);
        sortOpened.addActionListener(listener);
        quit.addActionListener(listener);


        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        getContentPane().add(topPanel);

        // Creates new Jtable and sets names of columns
        table = new JTable();
        table.setModel(bankModel);
        for (int col = 0; col < 4; col++) {
            table.getColumnName(col);
        }
        for (int row = 0; row < bankModel.getAccounts().size(); row++) {
            for (int col = 0; col < 4; col++) {
                table.getValueAt(row, col);
            }
        }


        // Add the table to a scrolling pane
        JScrollPane scrollPane = new JScrollPane(table);

        /******************************************************************
         * Updated to get values from ArrayList in BankModel
         *****************************************************************/
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();


                    ArrayList<Account> arrtemp = bankModel.getAccounts();
                    if (arrtemp.get(row) instanceof CheckingAccount) {
                        CheckingAccount a = (CheckingAccount) arrtemp.get(row);


                        if (a.getIdentifier() == 1) {
                            checking.setSelected(true);


                            aNumber.setEnabled(true);
                            aNumber.setText("" + a.getAccountNumber());
                            aOwner.setEnabled(true);
                            aOwner.setText("" + a.getAccountOwner());
                            dateChooser.setEnabled(true);
                            dateChooser
                                    .setDate(a.getConvertGregToDate());
                            aBal.setEnabled(true);
                            aBal.setText("" + a.getAccountBalance());
                            mFee.setEnabled(true);
                            mFee.setText("" + a.getMonthlyFee());
                            iRate.setEnabled(false);
                            iRate.setText("");
                            mBal.setEnabled(false);
                            mBal.setText("");


                        }
                    }
                    if (arrtemp.get(row) instanceof SavingsAccount) {
                        SavingsAccount b = (SavingsAccount) arrtemp.get(row);

                        if (b.getIdentifier() == 2) {
                            savings.setSelected(true);

                            aNumber.setEnabled(true);
                            aNumber.setText("" + b.getAccountNumber());
                            aOwner.setEnabled(true);
                            aOwner.setText("" + b.getAccountOwner());
                            dateChooser.setEnabled(true);
                            aBal.setEnabled(true);
                            aBal.setText("" + b.getAccountBalance());
                            mFee.setEnabled(false);
                            mFee.setText("");
                            iRate.setEnabled(true);
                            iRate.setText("" + b.getInterestRate());
                            mBal.setEnabled(true);
                            mBal.setText("" + b.getMinBalance());
                        }

                    }
                }


            }

        });


        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BorderLayout());

        lowerPanel.add(scrollPane, BorderLayout.NORTH);

        JPanel RTPanel = new JPanel();
        RTPanel.setLayout(new BorderLayout());


        JPanel radioPanel = new JPanel();

        checking = new JRadioButton("checking");
        checking.addActionListener(listener);
        savings = new JRadioButton("savings");
        savings.addActionListener(listener);

        ButtonGroup group = new ButtonGroup();

        group.add(checking);

        group.add(savings);

        radioPanel.add(checking);
        radioPanel.add(savings);

        RTPanel.add(radioPanel, BorderLayout.NORTH);


        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(7, 1));

        textPanel.add(new JLabel("Account Number:"));
        aNumber = new JTextField("", 4);
        textPanel.add(aNumber);
        aNumber.setEnabled(false);

        textPanel.add(new JLabel("Pick Date:"));
        dateChooser = new JDateChooser(); // Set to a button when because of my ambition to get a calander.. no success
        //System.out.println ("RUN" + dateChooser.toString());

        textPanel.add(dateChooser);
        ((JTextField)dateChooser.getDateEditor().getUiComponent()).setText("");
        dateChooser.getDateEditor().setEnabled(false);
        JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
        editor.setEditable(false);

        textPanel.add(new JLabel("Account Owner"));
        aOwner = new JTextField("", 4);
        textPanel.add(aOwner);
        aOwner.setEnabled(false);


        textPanel.add(new JLabel("Account Balance:"));
        aBal = new JTextField("", 4);
        //aBal.setDocument();
        textPanel.add(aBal);
        aBal.setEnabled(false);

        textPanel.add(new JLabel("Monthly Fee:"));
        mFee = new JTextField("", 4);
        textPanel.add(mFee);
        mFee.setEnabled(false);

        textPanel.add(new JLabel("interest Rate:"));
        iRate = new JTextField("", 4);
        textPanel.add(iRate);
        iRate.setEnabled(false);

        textPanel.add(new JLabel("Min Bal:"));
        mBal = new JTextField("", 4);
        textPanel.add(mBal);
        mBal.setEnabled(false);

        RTPanel.add(textPanel, BorderLayout.CENTER);


        lowerPanel.add(RTPanel, BorderLayout.CENTER);


        this.addButton = new JButton("Add");
        this.addButton.addActionListener(listener);

        this.deleteButton = new JButton("Delete");
        this.deleteButton.addActionListener(listener);

        this.updateButton = new JButton("Update");
        this.updateButton.addActionListener(listener);

        this.clearButton = new JButton("Clear");
        this.clearButton.addActionListener(listener);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        buttonPanel.add(this.addButton);
        buttonPanel.add(this.deleteButton);
        buttonPanel.add(this.updateButton);
        buttonPanel.add(this.clearButton);

        lowerPanel.add(buttonPanel, BorderLayout.EAST);
        add(lowerPanel);


        //Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //setLocation(dim.width/2-topPanel.getSize().width/2,
        //dim.height/2-topPanel.getSize().height/2);


    }

    private GregorianCalendar dateChange(JDateChooser incoming) {
        Date date = incoming.getDate();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal;
    }

    /**********************************************************************
     * clears all of the text fields after a button is selected
     *********************************************************************/
    private void clearTextFields() {
        aOwner.setText("");
        aNumber.setText("");
        mBal.setText("");
        mFee.setText("");
        ((JTextField)dateChooser.getDateEditor().getUiComponent()).setText("");
        //dateOpened.setText("");
        iRate.setText("");
        aBal.setText("");
    }

    /**********************************************************************
     * Resets all of the fields after a button is selected
     *********************************************************************/
    private void resetButtonFields() {
        ButtonGroup group = new ButtonGroup();
        group.add(checking);
        group.add(savings);
        group.clearSelection();
        aNumber.setEnabled(false);
        aOwner.setEnabled(false);
        //dateOpened.setEnabled(false);
        aBal.setEnabled(false);
        mFee.setEnabled(false);
        iRate.setEnabled(false);
        mBal.setEnabled(false);
    }

    /**********************************************************************
     * Method used in buttonlistener to set checking-account data
     *********************************************************************/
    private void setCheckingAccountTextFields() {

        int i = 0;
        if (
                (aNumber.getText().matches("[0-9]+."))

                ) {
            try {
                account.setAccountNumber(Integer.parseInt(aNumber.getText()));
            } catch (NumberFormatException nme) {
                JOptionPane.showMessageDialog(null, "Invalid account number");
                i = -1;
            }
            String s = ((JTextField)dateChooser.getDateEditor().getUiComponent()).getText();
            if (s.equals("")){
                JOptionPane.showMessageDialog(null, "pick a date");
                i = -1;
            }else {
                try {
                    account.setDateOpened(dateChange(dateChooser));
                } catch (IllegalArgumentException nme) {
                    JOptionPane.showMessageDialog(null, "Invalid Date");
                }
            }
            try {
                account.setAccountOwner(aOwner.getText());
            } catch (NumberFormatException nme) {
                JOptionPane.showMessageDialog(null, "Invalid account name");
                i = -1;
            }
            try {
                account.setAccountBalance(Double.parseDouble(aBal.getText()));
            } catch (NumberFormatException nme) {
                JOptionPane.showMessageDialog(null, "Invalid account balance");
                i = -1;
            }
            try {
                ((CheckingAccount) account).setMonthlyFee(Double.parseDouble(mFee.getText()));
            } catch (NumberFormatException nme) {
                JOptionPane.showMessageDialog(null, "Invalid monthly fee");
                i = -1;
            }


            if (i != -1) {


                if ((account.number < 0) ||
                        ((account.number + "").length() > 10)) {

                    JOptionPane.showMessageDialog(null, "Invalid account number length of 10 digits and positive!");

                } else {







                    if (account.balance < 0) {

                        JOptionPane.showMessageDialog(null, "account balance needs to be positive!");
                    } else {


                        if (((CheckingAccount) account).monthlyFee < 0) {

                            JOptionPane.showMessageDialog(null, "Monthly fee needs to be positive!");

                        } else {


                            bankModel.addAccount(account);
                            //still need to fix gregorian calendar
                        }


                    }
                }
            }
        }
    }


    /**********************************************************************
     * Method used in buttonlistener to set savings-account data
     *********************************************************************/
    private void setSavingsAccountTextFields() {

        if (
                (aNumber.getText().matches("[0-9]+"))

                ) {
            int i = 0;
            try {
                account.setAccountNumber(Integer.parseInt(aNumber.getText()));
            } catch (NumberFormatException nme) {
                JOptionPane.showMessageDialog(null, "Invalid account number");
                i = -1;
            }
            String s = ((JTextField)dateChooser.getDateEditor().getUiComponent()).getText();
            if (s.equals("")){
                JOptionPane.showMessageDialog(null, "pick a date");
                i = -1;
            }else {
                try {
                    account.setDateOpened(dateChange(dateChooser));
                } catch (IllegalArgumentException nme) {
                    JOptionPane.showMessageDialog(null, "Invalid Date");
                }
            }
            try {
                account.setAccountOwner(aOwner.getText());
            } catch (NumberFormatException nme) {
                JOptionPane.showMessageDialog(null, "Invalid account name");
                i = -1;
            }
            try {
                account.setAccountBalance(Double.parseDouble(aBal.getText()));
            } catch (NumberFormatException nme) {
                JOptionPane.showMessageDialog(null, "Invalid account balance");
                i = -1;
            }
            try {
                ((SavingsAccount) account).setMinBalance(Double.parseDouble(mBal.getText()));
            } catch (NumberFormatException nme) {
                JOptionPane.showMessageDialog(null, "Invalid minimum balance");
                i = -1;
            }
            try {
                ((SavingsAccount) account).setInterestRate(Double.parseDouble(iRate.getText()));
            } catch (NumberFormatException nme) {
                JOptionPane.showMessageDialog(null, "Invalid interest rate");
                i = -1;
            }
            if (i != -1) {
                if ((account.number < 0) ||
                        ((account.number + "").length() > 10)) {

                    JOptionPane.showMessageDialog(null, "Invalid account number length of 10 digits and positive!");

                } else {

                    if (account.balance < 0) {

                        JOptionPane.showMessageDialog(null, "account balance needs to be positive!");
                    } else {

                        if(((SavingsAccount) account).interestRate<0){

                            JOptionPane.showMessageDialog(null, "Interest rate needs to be positive!");
                        }else {

                            if((((SavingsAccount) account).minBalance<0)||
                                    (((SavingsAccount) account).minBalance>account.balance)){
                                JOptionPane.showMessageDialog(null, "Min Balance needs to be positive and less than the account Balance!");
                            }else {
                                bankModel.addAccount(account);
                                //still need to fix gregorian calendar
                            }
                        }
                    }
                }
            }
        }
    }


    /**********************************************************************
     * Change all buttons so they cannot be selected when in calendar menu
     * (which I cant get working so thats cool, 2 hours down the drain)
     * <p>
     * Also, i dont even know if this works..
     *********************************************************************/
    private void setAllClickablesFalse() {
        menuBar.setEnabled(false);
        addButton.setEnabled(false);
        updateButton.setEnabled(false);
        clearButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }


    private class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            Object button = arg0.getSource();

            if (button == loadBAction) {
                String filename = JOptionPane.showInputDialog(null, "Enter File Name:");
                if (filename != null) {
                    try {
                        bankModel.readFromBinaryFile(filename);
                    } catch (FileNotFoundException error) {
                        JOptionPane.showMessageDialog(null, "No Such File");
                    }

                    // problem reading the file
                    catch (IOException error) {
                        JOptionPane.showMessageDialog(null, "Oops!  Something went wrong.");
                    }
                }
            }
            if (button == saveBAction) {
                String filename = JOptionPane.showInputDialog(null, "Enter File Name:");
                if (filename != null) {
                    try {
                        bankModel.saveToBinaryFile(filename);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            if (button == loadTAction) {
                // Need to check if file name does NOT exist
                String filename = JOptionPane.showInputDialog(null, "Enter File Name:");
                if (filename != null) {
                    try {
                        bankModel.loadTextFile(filename);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException error) {
                        JOptionPane.showMessageDialog(null, "No Such File");
                    }

                    // problem reading the file
                    catch (IOException error) {
                        JOptionPane.showMessageDialog(null, "Oops!  Something went wrong.");
                    }

                }
            }
            if (button == saveTAction) {
                String filename = JOptionPane.showInputDialog(null, "Enter File Name:");
                if (filename != null) {
                    bankModel.saveTextFile(filename);
                } else {
                    JOptionPane.showMessageDialog(null, "Not a valid file name");
                }
            }
            if (button == loadXAction) {
                //Still need this
            }
            if (button == saveXAction) {
                //And this
            }
            if (button == sortNumber) {
                bankModel.sortByAccountNumber();
            }
            if (button == sortOwner) {
                bankModel.sortByAccountName();
            }

            if (button == sortOpened) {
                bankModel.sortByDateOpened();

            }
            if (button == quit) {
                System.exit(0);
            }

            if (button == addButton) {

                if ((aNumber.getText().equals("") == true) ||
                        (aNumber.getText().length() > 10) ||
                        (Double.parseDouble(aNumber.getText())< 0)) {

                    JOptionPane.showMessageDialog(null, "Account number should be between 0 and 10 digits and positive");

                } else {



                    if (aNumber.getText().matches("[0-9]+")) {


                        if (checking.isSelected()) {
                            account = new CheckingAccount();

                            if (bankModel.findAccountNumber(Integer.parseInt(aNumber.getText())) != -1) {//checks if account number exists
                                JOptionPane.showMessageDialog(null, "Account number already exists, choose a new number");
                            } else {
                                setCheckingAccountTextFields();

                            }


                        }


                        if (savings.isSelected()) {



                                account = new SavingsAccount();


                                if (bankModel.findAccountNumber(Integer.parseInt(aNumber.getText())) != -1) {//checks if account number exists
                                    JOptionPane.showMessageDialog(null, "Account number already exists, choose a new number");
                                } else setSavingsAccountTextFields();
                            }
                        }



                }
            }
            //not finished
            if (button == deleteButton) {

                if (aNumber.getText().equals("") == true) {

                } else {
                    int accNumber;
                    accNumber = Integer.parseInt(aNumber.getText());
                    int option = JOptionPane.showConfirmDialog(null, "Are you sure you wan't to remove this account?");
                    if (option == JOptionPane.YES_OPTION) {
                        bankModel.deleteAccount(accNumber);
                        clearTextFields();
                        resetButtonFields();
                    }
                }
            }

            if (button == updateButton) {

                if ((aNumber.getText().equals("") == true) ||
                        (aNumber.getText().length() > 10) ||
                        (aNumber.getText().length() < 0)) {

                    JOptionPane.showMessageDialog(null, "Account number should be between 0 and 10 positive digits");

                } else {

                    if (aNumber.getText().matches("[0-9]+")) {
                        if (bankModel.findAccountNumber(Integer.parseInt(aNumber.getText())) == -1) {//checks if account number exists
                            JOptionPane.showMessageDialog(null, "Account number does not exists");
                        } else {

                            if (Double.parseDouble(aBal.getText())<0){
                                JOptionPane.showMessageDialog(null, "Account bal needs to be positive");
                            }else {


                                if (checking.isSelected()) {
                                    account = new CheckingAccount();

                                    bankModel.deleteAccount(Integer.parseInt(aNumber.getText()));
                                    setCheckingAccountTextFields();


                                }


                                if (savings.isSelected()) {

                                    if (Double.parseDouble(mBal.getText()) > Double.parseDouble(aBal.getText())) {
                                        JOptionPane.showMessageDialog(null, "Account Bal must be bigger than Minimum bal");
                                    } else {
                                        account = new SavingsAccount();

                                        bankModel.deleteAccount(Integer.parseInt(aNumber.getText()));
                                        setSavingsAccountTextFields();

                                        //clearTextFields();
                                        //resetButtonFields();
                                    }
                                }
                            }
                        }
                    }
                }
            }

                if (button == clearButton) {
                    clearTextFields();
                    resetButtonFields();
                }


                //Added functionality so they create new accounts as soon as these are selected
                if (button == checking) {

                    aNumber.setEnabled(true);
                    aOwner.setEnabled(true);
                    dateChooser.setEnabled(true);
                    aBal.setEnabled(true);
                    mFee.setEnabled(true);
                    iRate.setEnabled(false);
                    mBal.setEnabled(false);
                }

                if (button == savings) {

                    aNumber.setEnabled(true);
                    aOwner.setEnabled(true);
                    dateChooser.setEnabled(true);
                    aBal.setEnabled(true);
                    mFee.setEnabled(false);
                    iRate.setEnabled(true);
                    mBal.setEnabled(true);
                }

            }

        }
    }

