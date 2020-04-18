/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor {

  boolean [] philosophers;
  boolean [] chopsticks;
  boolean isTalking;
  /**
   * Constructor
   */
  public Monitor(int piNumberOfPhilosophers) {
    // TODO: set appropriate number of chopsticks based on the # of philosophers

    chopsticks = new boolean[piNumberOfPhilosophers];
    philosophers = new boolean[piNumberOfPhilosophers];
    isTalking = true;

    for(int i = 0; i < chopsticks.length; i++)
      chopsticks[i] = true;
  }

  /**
   * Grants request (returns) to eat when both chopsticks/forks are available.
   * Else forces the philosopher to wait()
   */
  public synchronized void pickUp(final int piTID) {

    while (!(chopsticks[(piTID-1)%(chopsticks.length)] && chopsticks[(piTID)%(chopsticks.length)])) {
      try {
        this.wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    chopsticks[(piTID)%(chopsticks.length)] = false;
    chopsticks[(piTID-1)%(chopsticks.length)] = false;
  }

  /**
   * When a given philosopher's done eating, they put the chopstiks/forks down
   * and let others know they are available.
   */
  public synchronized void putDown(final int piTID) {

    chopsticks[(piTID-1)%(chopsticks.length)] = chopsticks[(piTID)%(chopsticks.length)] = true;

    this.notifyAll();
  }

  /**
   * Only one philopher at a time is allowed to philosophy
   * (while she is not eating).
   */
  public synchronized void requestTalk() {

    {
      while (!isTalking) {
        try {
          this.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      isTalking = true;
    }
  }

  /**
   * When one philosopher is done talking stuff, others
   * can feel free to start talking.
   */
  public synchronized void endTalk() {

    isTalking = false;
    this.notifyAll();
  }
}
