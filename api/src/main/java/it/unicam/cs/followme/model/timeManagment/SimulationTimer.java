package it.unicam.cs.followme.model.timeManagment;

/**
 * La classe timer ha la responsabilità di scandire il tempo nell'ambiente
 * valorizza una variabile Time time che tiene traccia delle unità di tempo trascorse.
 */
public class SimulationTimer extends Thread {
    private int currentTime;
    private Integer timeSpeedInMillis;

    /**
    * Ritorna un timer che scandisce il tempo ad intervalli regolari
    * @param timeSpeedInMillis imposta la durata dell'unita di tempo - 1000 = 1 secondo
    */
    public SimulationTimer( Integer timeSpeedInMillis) {
        this.currentTime = 0;
        this.timeSpeedInMillis = timeSpeedInMillis;
    }

    /**
     * Consente di lanciare un Thread dedicato al computo del tempo.
     */
    @Override
    public void run() {
            try {Thread.sleep(timeSpeedInMillis);
                 currentTime++;
                 //todo remove print
                System.out.println("Thread is running " + currentTime);
            }
            catch (InterruptedException e) {throw new RuntimeException(e);}
        }


    /**
     * Ritorna il tempo registrato dal timer corrente, il tempo è espresso in numeri
     * interi che rappresentano le unitò temporali della simulazione.
     * @return currentTime unità di tempo registrate dal timer.
     */
    public int getTime(){return this.currentTime;}

    public void setTimeUnit(Integer timeUnit){this.timeSpeedInMillis = timeUnit;}
}
