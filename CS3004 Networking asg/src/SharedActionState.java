import java.net.*;
import java.io.*;

public class SharedActionState{

    private SharedActionState mySharedObj;
    private SharedActionState client1obj;
    private String myThreadName;
    private double mySharedVariable;
    private double Client1G;
    private double Client2G;
    private double Client3G;
    private boolean accessing=false; // true a thread has a lock, false otherwise
    private int threadsWaiting=0; // number of waiting writers

// Constructor

   SharedActionState(double Client1, double Client2, double Client3) {

        Client1G = Client1;
        Client2G = Client2;
        Client3G = Client3;

    }

//Attempt to aquire a lock

    public synchronized void acquireLock() throws InterruptedException{
        Thread me = Thread.currentThread(); // get a ref to the current thread
        System.out.println(me.getName()+" is attempting to acquire a lock!");
        ++threadsWaiting;
        while (accessing) {  // while someone else is accessing or threadsWaiting > 0
            System.out.println(me.getName()+" waiting to get a lock as someone else is accessing...");
            //wait for the lock to be released - see releaseLock() below
            wait();
        }
        // nobody has got a lock so get one
        --threadsWaiting;
        accessing = true;
        System.out.println(me.getName()+" got a lock!");
    }

    // Releases a lock to when a thread is finished

    public synchronized void releaseLock() {
        //release the lock and tell everyone
        accessing = false;
        notifyAll();
        Thread me = Thread.currentThread(); // get a ref to the current thread
        System.out.println(me.getName()+" released a lock!");
    }

    /* The processInput method */

    public synchronized String processInput(String myThreadName, String theInput) {
        System.out.println(myThreadName + " received "+ theInput);
        String theOutput = null;

        // Check what the client said
        if (theInput.equalsIgnoreCase("1")) {// Command 1 ADD
            //Correct request
            double x = 100;
            if (myThreadName.equals("ActionServerThread1")) {//Client 1

                Client1G = Client1G + x;

                System.out.println(myThreadName + " Added " + Client1G);
                System.out.println("Balance in server = " + mySharedVariable);
                theOutput = "Deposit action completed.  Account balance now = " + Client1G;
            }
            else if (myThreadName.equals("ActionServerThread2")) {//Client 2

                Client2G = Client2G + x;

                System.out.println(myThreadName + " Added " + Client2G);
                System.out.println("Balance in server = " + mySharedVariable);
                theOutput = "Deposit action completed.  Account balance now = " + Client2G;

            }
            else if (myThreadName.equals("ActionServerThread3")) {//Client 3

                Client3G = Client3G + x;

                System.out.println(myThreadName + " Added " + Client3G);
                System.out.println("Balance in server = " + mySharedVariable);
                theOutput = "Deposit action completed.  Account balance now = " + Client3G;
            }

            else {System.out.println("Error - thread call not recognised.");}
        }

        else if (theInput.equalsIgnoreCase("2")) { // Command 2 Substract
            //Correct request
            double x = 100;
            if (myThreadName.equals("ActionServerThread1")) {//Client 1

                Client1G = Client1G - x;

                System.out.println(myThreadName + " Substracted " + Client1G);
                System.out.println("Balance in server = " + mySharedVariable);
                theOutput = "Withdraw action completed.  Account balance now = " + Client1G;
            }
            else if (myThreadName.equals("ActionServerThread2")) {//Client 2

                Client2G = Client2G - x;

                System.out.println(myThreadName + " Substracted " + Client2G);
                System.out.println("Balance in server = " + mySharedVariable);
                theOutput = "Withdraw action completed.  Account balance now = " + Client2G;

            }
            else if (myThreadName.equals("ActionServerThread3")) {//Client 3

                Client3G = Client3G - x;

                System.out.println(myThreadName + " Substracted " + Client3G);
                System.out.println("Balance in server = " + mySharedVariable);
                theOutput = "Withdraw action completed.  Account balance now = " + Client3G;
            }

            else {System.out.println("Error - thread call not recognised.");}
        }

        else if (theInput.equalsIgnoreCase("3")) { // Command 3 Show balance
            //Correct request
            if (myThreadName.equals("ActionServerThread1")) {//Client 1

                System.out.println(myThreadName + " Show Balance" + Client1G);
                theOutput = "Show balance action completed.  Account balance = " + Client1G;

            }
            else if (myThreadName.equals("ActionServerThread2")) {//Client 2

                System.out.println(myThreadName + " Show Balance" + Client2G);
                theOutput = "Show balance action completed.  Account balance = " + Client2G;

            }
            else if (myThreadName.equals("ActionServerThread3")) {//Client 3

                System.out.println(myThreadName + " Show Balance" + Client3G);
                theOutput = "Show balance action completed.  Account balance = " + Client3G;

            }

            else {System.out.println("Error - thread call not recognised.");}
        }

        else if (theInput.equalsIgnoreCase("4")) { // command 4 firss type of transfer
            //Correct request
            double x = 100;

            if (myThreadName.equals("ActionServerThread1")) {//Client A

                Client1G = Client1G - x;
                Client2G = Client2G + x;

                System.out.println(myThreadName + " Transferd from you " + Client1G + "To Client 2 " );
                System.out.println("Balance in server = " + mySharedVariable);
                theOutput = "Transfer action completed.  Account balance now = " + Client1G;
            }
            else if (myThreadName.equals("ActionServerThread2")) {//Client B

                Client2G = Client2G - x;
                Client1G = Client1G + x;


                System.out.println(myThreadName + " Transferd from " + Client2G + "To client 1") ;
                System.out.println("Balance in server = " + mySharedVariable);
                theOutput = "Transfer action completed.  Account balance now = " + Client2G;

            }
            else if (myThreadName.equals("ActionServerThread3")) {//Client C

                Client3G = Client3G - x;
                Client2G = Client2G + x;

                System.out.println(myThreadName + " Transferd from " + Client3G + "To client 2") ;
                System.out.println("Balance in server = " + mySharedVariable);
                theOutput = "Transfer action completed.  Account balance now = " + Client3G;
            }

            else {System.out.println("Error - thread call not recognised.");}
        }

        else if (theInput.equalsIgnoreCase("5")) { // command 4 firss type of transfer
            //Correct request
            double x = 100;

            if (myThreadName.equals("ActionServerThread1")) {//Client A

                Client1G = Client1G - x;
                Client3G = Client3G + x;

                System.out.println(myThreadName + " Transferd from you " + Client1G + "To Client 3 " );
                System.out.println("Balance in server = " + mySharedVariable);
                theOutput = "Transfer action completed.  Account balance now = " + Client1G;
            }
            else if (myThreadName.equals("ActionServerThread2")) {//Client B

                Client2G = Client2G - x;
                Client3G = Client3G + x;

                System.out.println(myThreadName + " Transferd from " + Client2G + "To client 3") ;
                System.out.println("Balance in server = " + mySharedVariable);
                theOutput = "Transfer action completed.  Account balance now = " + Client2G;

            }
            else if (myThreadName.equals("ActionServerThread3")) {//Client C

                Client3G = Client3G - x;
                Client1G = Client1G + x;

                System.out.println(myThreadName + " Transferd from " + Client3G + "To client 1") ;
                System.out.println("Balance in server = " + mySharedVariable);
                theOutput = "Transfer action completed.  Account balance now = " + Client3G;
            }

            else {System.out.println("Error - thread call not recognised.");}
        }

        else { //incorrect request
            theOutput = myThreadName + " received incorrect request - only understand \"Do my action!\"";

        }

        //Return the output message to the ActionServer
        System.out.println(theOutput);
        return theOutput;
    }
}

