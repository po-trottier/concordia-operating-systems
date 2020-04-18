/**
 * @author Kerly Titus
 */
public class Main {

  /**
   * main class
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {

    // TODO : implement all the operations of main class

    // Initialize all the threads
    Thread network = new Thread(new Network("network"));           /* Activate the network */
    network.start();
    Thread server = new Thread(new Server());                             /* Start the server */
    server.start();
    Thread clientSend = new Thread(new Client("sending"));        /* Start the sending client */
    clientSend.start();
    Thread clientReceive = new Thread(new Client("receiving"));   /* Start the receiving client */
    clientReceive.start();
  }
}
