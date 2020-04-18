# Multithreaded Network Simulation

This program was written for the COMP 346 class taught by Dr. Aiman Hanna at Concordia University.

***Written by:***
- *Nimit Jaggi (40032159)*
- *Pierre-Olivier Trottier (40059235)*

---

The sample data can be found in the `Data` directory, and the sample outputs can be found in the `Output/OUTPUT.md` directory.

This small program starts 4 different threads: 

1) 1 network thread that holds buffers to transfer to and from the server
1) 1 server thread that processes all the data
1) 2 client threads that send and receive the data respectively

The program should also intelligently wait for the appropriate resources to be available before it connects a thread or starts processing data.