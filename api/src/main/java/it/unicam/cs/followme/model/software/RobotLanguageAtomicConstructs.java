package it.unicam.cs.followme.model.software;

import it.unicam.cs.followme.model.common.SpeedVector;
import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.common.Utilities;
import it.unicam.cs.followme.model.environment.BidimensionalSpace;
import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.model.hardware.ProgrammableObject;
import it.unicam.cs.followme.model.hardware.Robot;

import java.util.List;
import java.util.stream.Stream;

/**
 * Questa classe ha la responsabilità di implementare concretaemnte alcune istruzioni atomiche
 * che un oggetto programmabile può eseguire con riferimento al Linguaggio dei Robot
 * Le stringhe vengono inserite in una mappa che verra utilizzata per
 * da passare ai robot per l'effettiva esecuzione
 */

    public class RobotLanguageAtomicConstructs{

        /**
         * Muove progressivamente un robot nella direzione ed alla velocità indicati nei parametri.
         * @param args array contentente x e y di direzione e velocità di spostamento
         * @param programmable il robot destinatario del comando.
         */
        public static void move(double[] args, ProgrammableObject programmable){
            SpeedVector speedVector;
            if(args.length == 0){speedVector = (SpeedVector) programmable.getDirection();}
            else{speedVector = new SpeedVector(args[0], args[1], args[2]);}
            programmable.setDirection(speedVector);
            programmable.setPosition(calculateNewPosition(speedVector, programmable));
        }

        /**
         * Muove un oggetto in modo randomico considerati due valori min e max rispettivamente
         * per le coordinate x e y di direzione con una velocità espressa in metri al secondo.
         * @param args array contenente le coordinate x e y e la velocità di spostamento
         * @param programmable ammessi oggetti programmabili
         */
        public static  void moverandom(double[] args, ProgrammableObject programmable){
            double[] xRange = Utilities.sortTwoDouble(args[0], args[1]);
            double[] yRange = Utilities.sortTwoDouble(args[2], args[3]);
            args[0] = Utilities.randomScaledNumber(xRange[0],  xRange[1]);
            args[1] = Utilities.randomScaledNumber(yRange[0],  yRange[1]);
            args[2] = args[4];
            move(args, programmable);
        }

        /**
         * Modifica la condizione corrente con quella passata come parametro quindi
         * la segnala ritornando la stringa della nuova label.
         * @param label da segnalare
         * @param programmable il programmabile destinatario del comando
         */
        public static void signal(String label, ProgrammableObject programmable){
            programmable.setLabel(label);
        }

        /**
         * Se l'oggetto programmabile sta segnalando una specifica condizione passata come
         * parametro, interrompe la segnalazione e ritorna la stringa vuota che rappresenta
         * la sua label
         * @param programmable il programmabile destinatario del comando
         */
        public static void unsignal( ProgrammableObject programmable){
            programmable.setLabel("");
        }


    /**
         * Verifica i programmabili vicini in un certo raggio e muove il robot destinatario della media
         * fra le x e le y dei vicini.
         * @param parameters label da verificare - distanza di pertinenza - velocità di movimento
         * @param environment istanza dell'ambiente
         * @param robot il robot destinatario del comando.
         */
        public static <T> void follow( T[] parameters, BidimensionalSpace environment, Robot robot) {
            String labelToCheck = (String) parameters[0];
            double[] args = (double[]) parameters[1];
            double distanceToCheck = args[0];
            Stream<Robot>neighbours = environment.getNeighbours(robot.getPosition(), labelToCheck, distanceToCheck);
            Double newX = averageX(neighbours) != 0 ? averageX(neighbours) : Utilities.randomScaledNumber(-distanceToCheck, distanceToCheck);
            System.out.println(robot.getId() +" newX " + newX);
            Double newY = averageY(neighbours) != 0 ? averageY(neighbours) : Utilities.randomScaledNumber(-distanceToCheck, distanceToCheck);
            System.out.println(robot.getId() + " newY " + newX);
            double[] param = {newX,  newY, args[2]};
            move(param, robot);
        }

        /**
         * ferma un oggetto programmabile nella sua posizione corrente
         * @param programmable il programmabile destinatario del comando
         */
        public static void stop(Object empty, ProgrammableObject programmable){
            programmable.setPosition(programmable.getPosition());
        }

        private static TwoDimensionalPoint calculateNewPosition(SpeedVector speedVector, ProgrammableObject programmable){
            Double vector = Utilities.getDiagonal(speedVector.getX(), speedVector.getY());
            Double newX = Utilities.getSideFromDiagonal(speedVector.getSpeed(), vector,  speedVector.getX());
            Double newY = Utilities.getSideFromDiagonal(speedVector.getSpeed(), vector,  speedVector.getY());
            return new TwoDimensionalPoint(newX+((TwoDimensionalPoint) programmable.getPosition()).getX(),
                                           newY+((TwoDimensionalPoint) programmable.getPosition()).getY());
        }

        /**
         * Calcola la posizione media di x su una serie di robot
         * @param neighbours
         * @return
         */
        private static double averageX(Stream<Robot> neighbours){
            return  neighbours.mapToDouble(t->t.getPosition().getX()).average().orElse(0.0);
        }

        /**
         * Calcola la posizione media di y su una serie di robot
         * @param neighbours
         * @return
         */
        private static double averageY(Stream<Robot> neighbours){
            return  neighbours.mapToDouble(t->t.getPosition().getY()).average().orElse(0.0);
        }

}