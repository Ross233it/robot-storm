package it.unicam.cs.followme.io;

import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.utilities.ShapeData;

@FunctionalInterface
public interface ShapeBuilder {
    Shape createShape(ShapeData shapeData);
}