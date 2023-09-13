package it.unicam.cs.followme.model.environment;

/**
 * L'interfaccia astrae il concetto di forma intesa come oggetto geometrico nello spazio.
 * La forma ha un suo stato rappresentato da una label, delle dimensioni
 * specifiche e una posizione all'interno di uno spazio.
 *
 * @Param <P> parametro generico identifica una posizione nello spazio
 */
public interface Shape<C> extends Positionable<C>{
     /**
     * Data una posizione nello spazioe, rileva se il punto è interno o esterno alla forma
     * @Param <P> parametro generico identifica una posizione nello spazio
     * @return True se il punto è interno alla forma
     * @return False sei ll punto è esterno alla forma
     */
        Boolean isInternal(C point);

        String getLabel();

        C getPosition();

}
