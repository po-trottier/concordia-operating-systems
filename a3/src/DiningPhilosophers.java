////////////////////////////////////////////////////////////////////
//
//  Written by:
//    - Pierre-Olivier Trottier (40059235)
//    - Nimit Jaggi (40032159)
//
////////////////////////////////////////////////////////////////////

/**
 * Class DiningPhilosophers
 * The main starter.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class DiningPhilosophers {
  /**
   * This default may be overridden from the command line
   */
  public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 4;

  /**
   * Dining "iterations" per philosopher thread
   * while they are socializing there
   */
  public static final int DINING_STEPS = 10;

  /**
   * Our shared monitor for the philosphers to consult
   */
  public static Monitor soMonitor = null;

  /**
   * Main system starts up right here
   */
  public static void main(String[] argv) {
    try {
      int iPhilosophers = DEFAULT_NUMBER_OF_PHILOSOPHERS;

      // STUDENT
      // Set the number of philosophers via the Command Line arguments if an argument is present
      if (argv.length > 0) {
        try {
          iPhilosophers = Integer.parseInt(argv[0]);
        } catch (NumberFormatException e) {
          System.err.println("main():");
          reportException(e);
          System.exit(1);
        }
      }

      // Make the monitor aware of how many philosophers there are
      soMonitor = new Monitor(iPhilosophers);

      // Space for all the philosophers
      Philosopher aoPhilosophers[] = new Philosopher[iPhilosophers];

      System.out.println(iPhilosophers + " philosopher(s) came in for a dinner.\n");

      // Let 'em sit down
      for (int j = 0; j < iPhilosophers; j++) {
        aoPhilosophers[j] = new Philosopher();
        aoPhilosophers[j].start();
      }

      // Main waits for all its children to die...
      // I mean, philosophers to finish their dinner.
      for (int j = 0; j < iPhilosophers; j++)
        aoPhilosophers[j].join();

      System.out.println("\nAll philosophers have left. System terminates normally.");
    } catch (InterruptedException e) {
      System.err.println("main():");
      reportException(e);
      System.exit(1);
    }
  }

  /**
   * Outputs exception information to STDERR
   *
   * @param poException Exception object to dump to STDERR
   */
  public static void reportException(Exception poException) {
    System.err.println("Caught exception : " + poException.getClass().getName());
    System.err.println("Message          : " + poException.getMessage());
    System.err.println("Stack Trace      : ");
    poException.printStackTrace(System.err);
  }
}
