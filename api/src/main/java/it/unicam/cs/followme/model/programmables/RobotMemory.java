package it.unicam.cs.followme.model.programmables;

import java.util.HashMap;
import java.util.Map;

public class RobotMemory {
    private Map<Integer, RobotState> stateMap;

    public RobotMemory() {
        stateMap = new HashMap<>();
    }

    public void recordState(int index, RobotState state) {
        stateMap.put(index, state);
    }

    public RobotState getState(int index) {
        return stateMap.get(index);
    }

    public Map<Integer, RobotState> getAllStates(){return stateMap;}
}


