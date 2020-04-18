import common.BaseThread;

/**
 * Class Philosopher.
 * Outlines main subroutines of our virtual philosopher.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Philosopher extends BaseThread {
  /**
   * Max time an action can take (in milliseconds)
   */
  public static final long TIME_TO_WASTE = 1000;

  /**
   * The act of eating.
   * - Print the fact that a given phil (their TID) has started eating.
   * - yield
   * - Then sleep() for a random interval.
   * - yield
   * - The print that they are done eating.
   */
  public void eat() {
    try {
      // STUDENT
      System.out.println("Philosopher " + getTID() + " started eating");
      Thread.yield();
      sleep((long) (Math.random() * TIME_TO_WASTE));
      Thread.yield();
      System.out.println("Philosopher " + getTID() + " is done eating");
    } catch (InterruptedException e) {
      System.err.println("Philosopher.eat():");
      DiningPhilosophers.reportException(e);
      System.exit(1);
    }
  }

  /**
   * The act of thinking.
   * - Print the fact that a given phil (their TID) has started thinking.
   * - yield
   * - Then sleep() for a random interval.
   * - yield
   * - The print that they are done thinking.
   */
  public void think() {
    try {
      // STUDENT
      System.out.println("Philosopher " + getTID() + " started thinking");
      Thread.yield();
      sleep((long) (Math.random() * TIME_TO_WASTE));
      Thread.yield();
      System.out.println("Philosopher " + getTID() + " is done thinking");
    } catch (InterruptedException e) {
      System.err.println("Philosopher.think():");
      DiningPhilosophers.reportException(e);
      System.exit(1);
    }
  }

  /**
   * The act of talking.
   * - Print the fact that a given phil (their TID) has started talking.
   * - yield
   * - Say something brilliant at random
   * - yield
   * - The print that they are done talking.
   */
  public void talk() {
    // STUDENT
    System.out.println("Philosopher " + getTID() + " started talking");
    Thread.yield();
    saySomething();
    Thread.yield();
    System.out.println("Philosopher " + getTID() + " is done talking");
  }

  /**
   * Prints out a phrase from the array of phrases at random.
   * Feel free to add your own phrases.
   */
  public void saySomething() {
    String[] phrases = {
        "The unexamined life is not worth living",
        "Whereof one cannot speak, thereof one must be silent",
        "Entities should not be multiplied unnecessarily",
        "The life of man (in a state of nature) is solitary, poor, nasty, brutish, and short",
        "I think therefore I am",
        "He who thinks great thoughts, often makes great errors",
        "God is dead! He remains dead! And we have killed him.",
        "There is but one truly serious philosophical problem, and that is suicide",
        "Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
        "You know, true is false and false is true if you think of it",
        "2 + 2 = 5 for extremely large values of 2...",
        "If thee cannot speak, thee must be silent",
        "My number is " + getTID()
    };
    System.out.println(
        "Philosopher " + getTID() + " says: " + phrases[(int) (Math.random() * phrases.length)]
    );
  }

  /**
   * No, this is not the act of running, just the overridden Thread.run()
   */
  public void run() {
    for (int i = 0; i < DiningPhilosophers.DINING_STEPS; i++) {
      DiningPhilosophers.soMonitor.pickUp(getTID());
      eat();
      DiningPhilosophers.soMonitor.putDown(getTID());
      think();
      // STUDENT
      // There is a 50% chance the philosopher will talk
      if (Math.random() >= 0.5) {
        // STUDENT
        DiningPhilosophers.soMonitor.requestTalk();
        talk();
        DiningPhilosophers.soMonitor.endTalk();
      }
      Thread.yield();
    }
  }
}
