package edu.nps.moves.net;

import java.util.*;

import edu.nps.moves.dis.*;

/**
 * Example threaded implementation of the BehaviorConsumerIF interface.<p>
 *
 * A threaded BehaviorConsumer should be used if it takes a long time to
 * process each DIS packet, or if you want to process the packets in your
 * own thread, rather than the thread used in the BehaviorProducer.
 *
 * @see BehaviorProducerIF
 * @see BehaviorConsumerIF
 *
 * @author DMcG
 */
@Deprecated
public class BehaviorConsumerThreaded implements BehaviorConsumerIF, Runnable {

    private Vector<Pdu> receivedPdus;

    public BehaviorConsumerThreaded() {
        receivedPdus = new Vector<Pdu>();
    }

    /**
     * Receives a PDU from the BehaviorProducer.
     *
     * @param pdu the ProtocolDataUnit generated by the BehaviorProducer
     */
    public void receivePdu(Pdu pdu) {
        System.out.println("Got pdu from producer");
        synchronized (receivedPdus) {
            receivedPdus.add(pdu);
        }
    }

    /**
     * Implementation of the Runnable interface
     */
    public void run() {
        while (true) {
            while (true) // Yield the thread until we receive data
            {
                synchronized (receivedPdus) {
                    if (receivedPdus.size() == 0) {
                        Thread.yield();
                    } else {
                        break;
                    }
                }
            }

            // Print out how many we received, and create a new vector.
            synchronized (receivedPdus) {
                System.out.println("PDUs received: " + receivedPdus.size());
                receivedPdus = new Vector<Pdu>();
            }

        } // end of main wait loop
    }
}