# Multithreaded Network Simulation

This program was written for the COMP 346 class taught by Dr. Aiman Hanna at Concordia University.

***Written by:***
- *Nimit Jaggi (40032159)*
- *Pierre-Olivier Trottier (40059235)*

The sample data can be found in the `input` directory, and the sample outputs can be found in the `output` directory.

## Answers to Assignment Questions

### Synchronized Block VS Method

We chose to use **synchronized methods** to synchronize the query, withdraw and deposit methods because they are the only methods that access the account balances. Furthermore, using synchronized methods allows for the entire method to run synchronously which means the line where we get the balance is always going to return the same value as the value we set just before. 

### Runtime Speeds

**Busy-waiting:** 623ms \
**Semaphores:** 343ms

We can therefore see that the semaphores-based program is much more rapid than its busy-waiting counter-part. All of the CPU cycles wasted yielding and waiting for the buffers to empty/fill clearly show in the runtime difference.

