package it.unicam.cs.followme.model.hardware;

import java.util.HashMap;
import java.util.Map;

/**
 * Questa classe ha la responsabilità di registrare gli stati dei robot
 * nel corso dell'evoluzione della simulazione.
 */
public class RobotMemory implements Memory<Integer, RobotState> {
    private Map<Integer, RobotState> stateMap;

    public RobotMemory() {
        stateMap = new HashMap<>();
    }

    /**
     * Salva un nuovo stato del robot nella memoria
     * @param index l'indice che identifica lo stato in ordine cronologico
     * @param state lo stato del robot che viene registrato
     */
    @Override
    public void saveInMemory(Integer index, RobotState state) {
        stateMap.put(index, state);
    }


    /**
     * Memorizza le istruzioni avvenute nella memoria del robot.
     * @param time
     */
    public void saveInMemory(int time, Robot robot){
        robot.getMemory().saveInMemory(
               time, new RobotState(robot.getId(), robot.getPosition(),
               robot.getDirection(),
               robot.getLabel()));
    }
    /**
     * Ritorna l'intero contenuto della memoria
     * @return stateMap la mappa degli stati dei robots
     */
    @Override
    public Map<Integer, RobotState> getFromMemory(){return stateMap;}

    /**
     * Recupera un record specifico in base all'indice
     * @param index l'indice dello stato da recuperare
     * @return record lo stato del robot recuperato
     */
    public RobotState getState(Integer index) {
        return stateMap.get(index);
    }
}


