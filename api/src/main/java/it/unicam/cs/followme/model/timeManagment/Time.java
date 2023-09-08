package it.unicam.cs.followme.model.timeManagment;

/**
 * Questa classe definisce una zona di mutua esclusione dove viene
 * registrata una variabile condivisa time che tiene traccia del progredire del tempo
 */
//TODO Verificare le eccezioni non gestite
public class Time {
    private int time = 0;
    private boolean available = false;

    /**
     * Ritorna il valore della variabile time
     * @return time il valore della variabile condivisa
     */
    public synchronized int get() {
//            while (available == false) {
//                try { wait(); } catch(InterruptedException e){}
//            }
            //available = false;
            notifyAll();
            return time;
        }
 //   public int get(){return time;}


    /**
     * Modifica il valore della variabile time
     * @param value il valore da impostare per la variabile condivisa
     */
    public synchronized void put(int value) {
//            while (available == true) {
//                try { wait(); } catch(InterruptedException e){}
//            }
            time = value;
            //available = true;
            notifyAll();
        }
    }


