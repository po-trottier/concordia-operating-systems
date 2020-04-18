import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;

/**
 * Client class
 *
 * @author Kerly Titus
 */

public class Client implements Runnable {

  private static int numberOfTransactions;      /* Number of transactions to process */
  private static int maxNbTransactions;         /* Maximum number of transactions */
  private static Transaction[] transactions;    /* Transactions to be processed */
  private static Network network;               /* Client object to handle network operations */
  private String clientOperation;               /* sending or receiving */

  /**
   * Constructor method of Client class
   *
   * @param
   * @return
   */
  public Client(String operation) {
    if (operation.equals("sending")) {
      // Initialize Client Sending
      System.out.println("\nInitializing client sending application ...");
      clientOperation = operation;
      // Get transactions
      System.out.println("\nInitializing the transactions ... ");
      numberOfTransactions = 0;
      maxNbTransactions = 100;
      transactions = new Transaction[maxNbTransactions];
      readTransactions();
      // Connect to Network
      System.out.println("\nConnecting client to network ...");
      network = new Network("client");
      String cip = network.getClientIP();
      if (!(network.connect(cip))) {
        System.out.println("\nTerminating client application, network unavailable");
        System.exit(0);
      }
    } else if (operation.equals("receiving")) {
      System.out.println("\nInitializing client receiving application ...");
      clientOperation = operation;
    }
  }

  /**
   * Accessor method of Client class
   *
   * @param
   * @return numberOfTransactions
   */
  public int getNumberOfTransactions() {
    return numberOfTransactions;
  }

  /**
   * Mutator method of Client class
   *
   * @return
   * @param nbOfTrans
   */
  public void setNumberOfTransactions(int nbOfTrans)
  {
    numberOfTransactions = nbOfTrans;
  }

  /**
   * Accessor method of Client class
   *
   * @param
   * @return clientOperation
   */
  public String getClientOperation() {
    return clientOperation;
  }

  /**
   * Mutator method of Client class
   *
   * @param operation
   * @return
   */
  public void setClientOperation(String operation) {
    clientOperation = operation;
  }

  /**
   * Reading of the transactions from an input file
   *
   * @param
   * @return
   */
  public void readTransactions() {
    Scanner inputStream = null;     /* Transactions input file stream */
    int i = 0;                      /* Index of transactions array */

    try {
      inputStream = new Scanner(new FileInputStream("src/Data/transaction.txt"));
    } catch (FileNotFoundException e) {
      System.out.println("File transaction.txt was not found");
      System.out.println("or could not be opened.");
      System.exit(0);
    }
    while (inputStream.hasNextLine()) {
      try {
        transactions[i] = new Transaction();
        transactions[i].setAccountNumber(inputStream.next());            /* Read account number */
        transactions[i].setOperationType(inputStream.next());            /* Read transaction type */
        transactions[i].setTransactionAmount(inputStream.nextDouble());  /* Read transaction amount */
        transactions[i].setTransactionStatus("pending");                 /* Set current transaction status */
        i++;
        setNumberOfTransactions(i);
      } catch (InputMismatchException e) {
        System.out.println("Line " + i + "file transactions.txt invalid input");
        System.exit(0);
      }
    }

    // System.out.println("\nDEBUG : Client.readTransactions() - " + getNumberOfTransactions() + " transactions processed");

    inputStream.close();

  }

  /**
   * Sending the transactions to the server
   *
   * @param
   * @return
   */
  public void sendTransactions() {
    int i = 0;     /* index of transaction array */

    while (i < getNumberOfTransactions()) {
      while(network.getInBufferStatus().equals("full"))     /* Alternatively, busy-wait until the network input buffer is available */
        Thread.yield();
      network.send(transactions[i]);    /* Transmit current transaction */
      transactions[i].setTransactionStatus("sent");   /* Set current transaction status */
      i++;
    }

  }

  /**
   * Receiving the completed transactions from the server
   *
   * @param transact
   * @return
   */
  public void receiveTransactions(Transaction transact) {
    int i = 0;     /* Index of transaction array */

    while (i < getNumberOfTransactions()) {
      while( network.getOutBufferStatus().equals("empty"))  	/* Alternatively, busy-wait until the network output buffer is available */
        Thread.yield();
      network.receive(transact);                                /* Receive updated transaction from the network buffer */
      System.out.println(transact);                                /* Display updated transaction */
      i++;
    }
  }

  /**
   * Create a String representation based on the Client Object
   *
   * @param
   * @return String representation
   */
  public String toString() {
    return ("client IP " + network.getClientIP() + " Connection status" + network.getClientConnectionStatus() + "Number of transactions " + getNumberOfTransactions());
  }

  /**
   * Code for the run method
   *
   * @param
   * @return
   */
  public void run() {
    long time = System.currentTimeMillis();

    while(!network.getServerConnectionStatus().equals("connected"))
      Thread.yield();

    if (getClientOperation().equals("sending")) {
      sendTransactions();
    } else if (getClientOperation().equals("receiving")) {
      Transaction dummy = new Transaction();
      receiveTransactions(dummy);
      network.disconnect(network.getClientIP());
    } else {
      System.out.println("ERROR: Wrong client operation type!");
      System.exit(1);
    }

    System.out.println("\nTerminating " + clientOperation + " client thread - " + " Running time " + (System.currentTimeMillis() - time) + " ms");
  }
}
