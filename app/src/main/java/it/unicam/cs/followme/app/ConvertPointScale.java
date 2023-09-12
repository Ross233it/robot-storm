package it.unicam.cs.followme.app;

import it.unicam.cs.followme.model.hardware.ProgrammableObject;

/**
 * Consente di convertire le coordinate del programma nei valori assoluti
 * della gui
 * @param <C>
 */

@FunctionalInterface
public interface ConvertPointScale<Double, P extends ProgrammableObject, A extends CartesianAxisManager> {
    Double getScaledPoint(Double axesZero, P programmable, A axes);
}
