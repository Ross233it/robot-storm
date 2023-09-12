package it.unicam.cs.followme.model.timeManagment;

/**
 * La classe timer ha la responsabilità di scandire il tempo nell'ambiente
 */
public class SimulationTimer extends Thread {
    private int currentTime;
    private Integer timeSpeedInMillis;
    private Integer simDuration;

    /**
    * Ritorna un timer che scandisce il tempo ad intervalli regolari
    * @param timeSpeedInMillis imposta la durata dell'unita di tempo - 1000 = 1 secondo
    */
    public SimulationTimer( Integer timeSpeedInMillis, Integer simDuration) {
        this.currentTime = 0;
        this.timeSpeedInMillis = timeSpeedInMillis;
        this.simDuration = simDuration;
    }

    /**
     * Consente di lanciare un Thread dedicato al computo del tempo.
     */
    @Override
    public void run() {}
    /**
     * Ritorna il tempo registrato dal timer corrente, il tempo è espresso in numeri
     * interi che rappresentano le unitò temporali della simulazione.
     * @return currentTime unità di tempo registrate dal timer.
     */
    public int getTime(){return this.currentTime;}


    public void updateTime(){currentTime++;}

    /**
     * Setta la velocità di esecuzione della simulazione espressa in millisecondi.
     * @param timeUnit velocità della simulazione in millisecondi.
     */
    public void setTimeUnit(Integer timeUnit){this.timeSpeedInMillis = timeUnit;}
}
