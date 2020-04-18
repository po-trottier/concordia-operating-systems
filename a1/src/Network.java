/**
 * Network class
 *
 * @author Kerly Titus
 */
public class Network implements Runnable {

  private static int maxNbPackets;                           /* Maximum number of simultaneous transactions handled by the network buffer */
  private static int inputIndexClient, inputIndexServer, outputIndexServer, outputIndexClient;                   /* Network buffer indices for accessing the input buffer (inputIndexClient, outputIndexServer) and output buffer (inputIndexServer, outputIndexClient) */
  private static String clientIP;                            /* IP number of the client application*/
  private static String serverIP;                            /* IP number of the server application */
  private static int portID;                                 /* Port ID of the client application */
  private static String clientConnectionStatus;              /* Client connection status - connected, disconnected, idle */
  private static String serverConnectionStatus;              /* Server connection status - connected, disconnected, idle */
  private static Transaction incomingPackets[];              /* Incoming network buffer */
  private static Transaction outgoingPackets[];              /* Outgoing network buffer */
  private static String inBufferStatus, outBufferStatus;     /* Current status of the network buffers - normal, full, empty */
  private static String networkStatus;                       /* Network status - active, inactive */

  /**
   * Constructor of the Network class
   *
   * @param
   * @return
   */
  public Network(String context) {
    int i;

    /* Initialization of the network components */
    if (context.equals("network")) {
      System.out.println("\nActivating the network ...");
      clientIP = "192.168.2.0";
      serverIP = "216.120.40.10";
      clientConnectionStatus = "idle";
      serverConnectionStatus = "idle";
      portID = 0;
      maxNbPackets = 10;
      incomingPackets = new Transaction[maxNbPackets];
      outgoingPackets = new Transaction[maxNbPackets];
      for (i = 0; i < maxNbPackets; i++) {
        incomingPackets[i] = new Transaction();
        outgoingPackets[i] = new Transaction();
      }
      inBufferStatus = "empty";
      outBufferStatus = "empty";
      inputIndexClient = 0;
      inputIndexServer = 0;
      outputIndexServer = 0;
      outputIndexClient = 0;
      networkStatus = "active";
    } else /* Activate network components for client or server */
      System.out.println("\nActivating network components for " + context + "...");
  }

  /**
   * Accessor method of Network class
   *
   * @param
   * @return clientIP
   */
  public String getClientIP() {
    return clientIP;
  }

  /**
   * Mutator method of Network class
   *
   * @param cip
   * @return
   */
  public void setClientIP(String cip) {
    clientIP = cip;
  }

  /**
   * Accessor method of Network class
   *
   * @param
   * @return serverIP
   */
  public String getServerIP() {
    return serverIP;
  }

  /**
   * Mutator method of Network class
   *
   * @param sip
   * @return
   */
  public void setServerIP(String sip) {
    serverIP = sip;
  }

  /**
   * Accessor method of Network class
   *
   * @param
   * @return clientConnectionStatus
   */
  public String getClientConnectionStatus() {
    return clientConnectionStatus;
  }

  /**
   * Mutator method of Network class
   *
   * @param connectStatus
   * @return
   */
  public void setClientConnectionStatus(String connectStatus) {
    clientConnectionStatus = connectStatus;
  }

  /**
   * Accessor method of Network class
   *
   * @param
   * @return serverConnectionStatus
   */
  public String getServerConnectionStatus() {
    return serverConnectionStatus;
  }

  /**
   * Mutator method of Network class
   *
   * @param connectStatus
   * @return
   */
  public void setServerConnectionStatus(String connectStatus) {
    serverConnectionStatus = connectStatus;
  }

  /**
   * Accessor method of Network class
   *
   * @param
   * @return portID
   */
  public int getPortID() {
    return portID;
  }

  /**
   * Mutator method of Network class
   *
   * @param pid
   * @return
   */
  public void setPortID(int pid) {
    portID = pid;
  }

  /**
   * Accessor method of Netowrk class
   *
   * @param
   * @return inBufferStatus
   */
  public String getInBufferStatus() {
    return inBufferStatus;
  }

  /**
   * Mutator method of Network class
   *
   * @param inBufStatus
   * @return
   */
  public void setInBufferStatus(String inBufStatus) {
    inBufferStatus = inBufStatus;
  }

  /**
   * Accessor method of Netowrk class
   *
   * @param
   * @return outBufferStatus
   */
  public String getOutBufferStatus() {
    return outBufferStatus;
  }

  /**
   * Mutator method of Network class
   *
   * @param outBufStatus
   * @return
   */
  public void setOutBufferStatus(String outBufStatus) {
    outBufferStatus = outBufStatus;
  }

  /**
   * Accessor method of Netowrk class
   *
   * @param
   * @return networkStatus
   */
  public String getNetworkStatus() {
    return networkStatus;
  }

  /**
   * Mutator method of Network class
   *
   * @param netStatus
   * @return
   */
  public void setNetworkStatus(String netStatus) {
    networkStatus = netStatus;
  }

  /**
   * Accessor method of Netowrk class
   *
   * @param
   * @return inputIndexClient
   */
  public int getInputIndexClient() {
    return inputIndexClient;
  }

  /**
   * Mutator method of Network class
   *
   * @param i1
   * @return
   */
  public void setInputIndexClient(int i1) {
    inputIndexClient = i1;
  }

  /**
   * Accessor method of Netowrk class
   *
   * @param
   * @return inputIndexServer
   */
  public int getInputIndexServer() {
    return inputIndexServer;
  }

  /**
   * Mutator method of Network class
   *
   * @param i2
   * @return
   */
  public void setInputIndexServer(int i2) {
    inputIndexServer = i2;
  }

  /**
   * Accessor method of Netowrk class
   *
   * @param
   * @return outputIndexServer
   */
  public int getOutputIndexServer() {
    return outputIndexServer;
  }

  /**
   * Mutator method of Network class
   *
   * @param o1
   * @return
   */
  public void setOutputIndexServer(int o1) {
    outputIndexServer = o1;
  }

  /**
   * Accessor method of Netowrk class
   *
   * @param
   * @return outputIndexClient
   */
  public int getOutputIndexClient() {
    return outputIndexClient;
  }

  /**
   * Mutator method of Network class
   *
   * @param o2
   * @return
   */
  public void setOutputIndexClient(int o2) {
    outputIndexClient = o2;
  }

  /**
   * Accessor method of Netowrk class
   *
   * @param
   * @return maxNbPackets
   */
  public int getMaxNbPackets() {
    return maxNbPackets;
  }

  /**
   * Mutator method of Network class
   *
   * @param maxPackets
   * @return
   */
  public void setMaxNbPackets(int maxPackets) {
    maxNbPackets = maxPackets;
  }

  /**
   * Transmitting the transactions from the client to the server through the network
   *
   * @param inPacket transaction transferred from the client
   * @return
   */
  public boolean send(Transaction inPacket) {
    incomingPackets[inputIndexClient].setAccountNumber(inPacket.getAccountNumber());
    incomingPackets[inputIndexClient].setOperationType(inPacket.getOperationType());
    incomingPackets[inputIndexClient].setTransactionAmount(inPacket.getTransactionAmount());
    incomingPackets[inputIndexClient].setTransactionBalance(inPacket.getTransactionBalance());
    incomingPackets[inputIndexClient].setTransactionError(inPacket.getTransactionError());
    incomingPackets[inputIndexClient].setTransactionStatus("transferred");

    // System.out.println("\nDEBUG : Network.send() - index inputIndexClient " + inputIndexClient);
    // System.out.println("\nDEBUG : Network.send() - account number " + inComingPacket[inputIndexClient].getAccountNumber());


    setInputIndexClient(((getInputIndexClient() + 1) % getMaxNbPackets()));  /* Increment the input buffer index  for the client */
    /* Check if input buffer is full */
    if (getInputIndexClient() == getOutputIndexServer()) {
      setInBufferStatus("full");

      // System.out.println("\nDEBUG : Network.send() - inComingBuffer status " + getInBufferStatus());
    } else
      setInBufferStatus("normal");

    return true;
  }

  /**
   * Transmitting the transactions from the server to the client through the network
   *
   * @param outPacket updated transaction received by the client
   * @return
   */
  public boolean receive(Transaction outPacket) {
    outPacket.setAccountNumber(outgoingPackets[outputIndexClient].getAccountNumber());
    outPacket.setOperationType(outgoingPackets[outputIndexClient].getOperationType());
    outPacket.setTransactionAmount(outgoingPackets[outputIndexClient].getTransactionAmount());
    outPacket.setTransactionBalance(outgoingPackets[outputIndexClient].getTransactionBalance());
    outPacket.setTransactionError(outgoingPackets[outputIndexClient].getTransactionError());
    outPacket.setTransactionStatus("done");

    // System.out.println("\nDEBUG : Network.receive() - index outputIndexClient " + outputIndexClient);
    // System.out.println("\nDEBUG : Network.receive() - account number " + outPacket.getAccountNumber());

    setOutputIndexClient(((getOutputIndexClient() + 1) % getMaxNbPackets())); /* Increment the output buffer index for the client */
    /* Check if output buffer is empty */
    if (getOutputIndexClient() == getInputIndexServer()) {
      setOutBufferStatus("empty");

      // System.out.println("\nDEBUG : Network.receive() - outGoingBuffer status " + getOutBufferStatus());
    } else
      setOutBufferStatus("normal");

    return true;
  }

  /**
   * Transferring the completed transactions from the server to the network buffer
   *
   * @param outPacket updated transaction transferred by the server to the network output buffer
   * @return
   */
  public boolean transferOut(Transaction outPacket) {
    outgoingPackets[inputIndexServer].setAccountNumber(outPacket.getAccountNumber());
    outgoingPackets[inputIndexServer].setOperationType(outPacket.getOperationType());
    outgoingPackets[inputIndexServer].setTransactionAmount(outPacket.getTransactionAmount());
    outgoingPackets[inputIndexServer].setTransactionBalance(outPacket.getTransactionBalance());
    outgoingPackets[inputIndexServer].setTransactionError(outPacket.getTransactionError());
    outgoingPackets[inputIndexServer].setTransactionStatus("transferred");

    // System.out.println("\nDEBUG : Network.transferOut() - index inputIndexServer " + inputIndexServer);
    // System.out.println("\nDEBUG : Network.transferOut() - account number " + outGoingPacket[inputIndexServer].getAccountNumber());

    setInputIndexServer(((getInputIndexServer() + 1) % getMaxNbPackets())); /* Increment the output buffer index for the server */
    /* Check if output buffer is full */
    if (getInputIndexServer() == getOutputIndexClient()) {
      setOutBufferStatus("full");

      // System.out.println("\nDEBUG : Network.transferOut() - outGoingBuffer status " + getOutBufferStatus());
    } else
      setOutBufferStatus("normal");

    return true;
  }

  /**
   * Transferring the transactions from the network buffer to the server
   *
   * @param inPacket transaction transferred from the input buffer to the server
   * @return
   */
  public boolean transferIn(Transaction inPacket) {
    // System.out.println("\nDEBUG : Network.transferIn - account number " + inComingPacket[outputIndexServer].getAccountNumber());
    inPacket.setAccountNumber(incomingPackets[outputIndexServer].getAccountNumber());
    inPacket.setOperationType(incomingPackets[outputIndexServer].getOperationType());
    inPacket.setTransactionAmount(incomingPackets[outputIndexServer].getTransactionAmount());
    inPacket.setTransactionBalance(incomingPackets[outputIndexServer].getTransactionBalance());
    inPacket.setTransactionError(incomingPackets[outputIndexServer].getTransactionError());
    inPacket.setTransactionStatus("received");

    // System.out.println("\nDEBUG : Network.transferIn() - index outputIndexServer " + outputIndexServer);
    // System.out.println("\nDEBUG : Network.transferIn() - account number " + inPacket.getAccountNumber());

    setOutputIndexServer(((getOutputIndexServer() + 1) % getMaxNbPackets()));  /* Increment the input buffer index for the server */
    /* Check if input buffer is empty */
    if (getOutputIndexServer() == getInputIndexClient()) {
      setInBufferStatus("empty");

      // System.out.println("\nDEBUG : Network.transferIn() - inComingBuffer status " + getInBufferStatus());
    } else
      setInBufferStatus("normal");

    return true;
  }

  /**
   * Handling of connection requests through the network
   *
   * @param IP
   * @return valid connection
   */
  public boolean connect(String IP) {
    if (getNetworkStatus().equals("active")) {
      if (getClientIP().equals(IP)) {
        setClientConnectionStatus("connected");
        setPortID(0);
      } else if (getServerIP().equals(IP)) {
        setServerConnectionStatus("connected");
      }
      return true;
    } else
      return false;
  }

  /**
   * Handling of disconnection requests through the network
   *
   * @param IP
   * @return valid disconnection
   */
  public boolean disconnect(String IP) {
    if (getNetworkStatus().equals("active")) {
      if (getClientIP().equals(IP)) {
        setClientConnectionStatus("disconnected");
      } else if (getServerIP().equals(IP)) {
        setServerConnectionStatus("disconnected");
      }
      return true;
    } else
      return false;
  }

  /**
   * Create a String representation based on the Network Object
   *
   * @return String representation
   */
  public String toString() {
    return ("\nNetwork status " + getNetworkStatus() + "Input buffer " + getInBufferStatus() + "Output buffer " + getOutBufferStatus());
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

    while (
      !getServerConnectionStatus().equals("disconnected") &&
      !getClientConnectionStatus().equals("disconnected")
    ) {
      Thread.yield();
    }

    System.out.println("\nTerminating network thread - " + " Running time " + (System.currentTimeMillis() - time) + " ms");
  }
}
