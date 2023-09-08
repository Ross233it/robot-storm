package it.unicam.cs.followme.model.timeManagment;

import it.unicam.cs.followme.model.common.Coordinates;

public class ExecutionUnits<Integer, C extends Coordinates, L> {
    private C currentPosition;
    private L currentLabel;
    private Integer id;

    public ExecutionUnits(C currentPosition, L currentLabel, Integer id) {
        this.currentPosition = currentPosition;
        this.currentLabel = currentLabel;
        this.id = id;
    }

    public C getCurrentPosition() {
        return currentPosition;
    }

    public L getCurrentLabel() {
        return currentLabel;
    }

    public Integer getId() {
        return id;
    }
}
