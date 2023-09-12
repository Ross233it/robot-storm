package it.unicam.cs.followme.model.hardware;

import java.util.Map;

/**
 * Astrae il concetto di memoria secondaria di un oggetto programmabile,in cui è
 * possibile archiviare informazioni, fra cui lo stato dell'oggetto nel tempo;
 */
public interface Memory<I,C> {

    void saveInMemory(I index , C obj);

    Map<I,C> getFromMemory();
}
