import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;

/**
 * Server class
 *
 * @author Kerly Titus
 */

public class Server implements Runnable {

  int numberOfTransactions;         /* Number of transactions handled by the server */
  int numberOfAccounts;             /* Number of accounts stored in the server */
  int maxNbAccounts;                /* maximum number of transactions */
  Transaction transaction;         /* Transaction being processed */
  Network network;                  /* Server object to handle network operations */
  Accounts[] account;               /* Accounts to be accessed or updated */

  /**
   * Constructor method of Client class
   *
   * @param
   * @return
   */
  public Server() {
    System.out.println("\nInitializing the server ...");
    numberOfTransactions = 0;
    numberOfAccounts = 0;
    maxNbAccounts = 100;
    transaction = new Transaction();
    account = new Accounts[maxNbAccounts];
    network = new Network("server");
    System.out.println("\nInitializing the Accounts database ...");
    initializeAccounts();
    System.out.println("\nConnecting server to network ...");
    if (!(network.connect(network.getServerIP()))) {
      System.out.println("\nTerminating server application, network unavailable");
      System.exit(0);
    }
  }

  /**
   * Accessor method of Server class
   *
   * @param
   * @return numberOfTransactions
   */
  public int getNumberOfTransactions() {
    return numberOfTransactions;
  }

  /**
   * Mutator method of Server class
   *
   * @param nbOfTrans
   * @return
   */
  public void setNumberOfTransactions(int nbOfTrans) {
    numberOfTransactions = nbOfTrans;
  }

  /**
   * Accessor method of Server class
   *
   * @param
   * @return numberOfAccounts
   */
  public int getNumberOfAccounts() {
    return numberOfAccounts;
  }

  /**
   * Mutator method of Server class
   *
   * @param nbOfAcc
   * @return
   */
  public void setNumberOfAccounts(int nbOfAcc) {
    numberOfAccounts = nbOfAcc;
  }

  /**
   * Accessor method of Server class
   *
   * @param
   * @return maxNbAccounts
   */
  public int getmMxNbAccounts() {
    return maxNbAccounts;
  }

  /**
   * Mutator method of Server class
   *
   * @param nbOfAcc
   * @return
   */
  public void setMaxNbAccounts(int nbOfAcc) {
    maxNbAccounts = nbOfAcc;
  }

  /**
   * Initialization of the accounts from an input file
   *
   * @param
   * @return
   */
  public void initializeAccounts() {
    Scanner inputStream = null; /* accounts input file stream */
    int i = 0;                  /* index of accounts array */

    try {
      inputStream = new Scanner(new FileInputStream("src/Data/account.txt"));
    } catch (FileNotFoundException e) {
      System.out.println("File account.txt was not found or could not be opened.");
      System.exit(0);
    }
    while (inputStream.hasNextLine()) {
      try {
        account[i] = new Accounts();
        account[i].setAccountNumber(inputStream.next());    /* Read account number */
        account[i].setAccountType(inputStream.next());      /* Read account type */
        account[i].setFirstName(inputStream.next());        /* Read first name */
        account[i].setLastName(inputStream.next());         /* Read last name */
        account[i].setBalance(inputStream.nextDouble());    /* Read account balance */
      } catch (InputMismatchException e) {
        System.out.println("Line " + i + "file account.txt invalid input");
        System.exit(0);
      }
      i++;
    }
    setNumberOfAccounts(i);      /* Record the number of accounts processed */

    // System.out.println("\nDEBUG : Server.initializeAccounts() " + getNumberOfAccounts() + " accounts processed");

    inputStream.close();
  }

  /**
   * Find and return the index position of an account
   *
   * @param accNumber
   * @return account index position or -1
   */
  public int findAccount(String accNumber) {
    int i = 0;

    /* Find account */
    while (account != null && account[i] != null && !(account[i].getAccountNumber().equals(accNumber)))
      i++;
    if (i == getNumberOfAccounts())
      return -1;
    else
      return i;
  }

  /**
   * Processing of the transactions
   *
   * @param trans
   * @return
   */
  public boolean processTransactions(Transaction trans) {
    int accIndex;              /* Index position of account to update */
    double newBalance;    /* Updated account balance */

    /* Process the accounts until the client disconnects */
    while ((!network.getClientConnectionStatus().equals("disconnected"))) {

      if (!network.getInBufferStatus().equals("empty")) {

        network.transferIn(trans);                              /* Transfer a transaction from the network input buffer */

        accIndex = findAccount(trans.getAccountNumber());
        /* Process deposit operation */
        switch (trans.getOperationType()) {
          case "DEPOSIT":
            newBalance = deposit(accIndex, trans.getTransactionAmount());
            trans.setTransactionBalance(newBalance);
            trans.setTransactionStatus("done");
            break;
          case "WITHDRAW":
            newBalance = withdraw(accIndex, trans.getTransactionAmount());
            trans.setTransactionBalance(newBalance);
            trans.setTransactionStatus("done");
            break;
          case "QUERY":
            newBalance = query(accIndex);
            trans.setTransactionBalance(newBalance);
            trans.setTransactionStatus("done");
            break;
        }

        while((network.getOutBufferStatus().equals("full"))) /* Alternatively,  busy-wait until the network output buffer is available */
          Thread.yield();

        network.transferOut(trans);                                /* Transfer a completed transaction from the server to the network output buffer */
        setNumberOfTransactions((getNumberOfTransactions() + 1));  /* Count the number of transactions processed */
      }
    }

    // System.out.println("\nDEBUG : Server.processTransactions() - " + getNumberOfTransactions() + " accounts updated");

    return true;
  }

  /**
   * Processing of a deposit operation in an account
   *
   * @param i, amount
   * @return balance
   */
  public double deposit(int i, double amount) {
    double curBalance;      /* Current account balance */

    curBalance = account[i].getBalance();          /* Get current account balance */
    account[i].setBalance(curBalance + amount);     /* Deposit amount in the account */
    return account[i].getBalance();                /* Return updated account balance */
  }

  /**
   * Processing of a withdrawal operation in an account
   *
   * @param i, amount
   * @return balance
   */
  public double withdraw(int i, double amount) {
    double curBalance;      /* Current account balance */

    curBalance = account[i].getBalance();          /* Get current account balance */
    account[i].setBalance(curBalance - amount);     /* Withdraw amount in the account */
    return account[i].getBalance();                /* Return updated account balance */
  }

  /**
   * Processing of a query operation in an account
   *
   * @param i
   * @return balance
   */
  public double query(int i) {
    double curBalance;      /* Current account balance */

    curBalance = account[i].getBalance();          /* Get current account balance */
    return curBalance;                              /* Return current account balance */
  }

  /**
   * Create a String representation based on the Server Object
   *
   * @return String representation
   */
  public String toString() {
    return ("\nserver IP " + network.getServerIP() + "connection status " + network.getServerConnectionStatus() + "Number of accounts " + getNumberOfAccounts());
  }

  // TODO : implement the method Run() to execute the server thread				 																*

  /**
   * Code for the run method
   *
   * @param
   * @return
   */
  public void run() {
    long time = System.currentTimeMillis();

    while (!network.getNetworkStatus().equals("active"))
      Thread.yield();

    processTransactions(transaction);
    network.disconnect(network.getServerIP());

    System.out.println("\nTerminating server thread - " + " Running time " + (System.currentTimeMillis() - time) + " ms");
  }
}

