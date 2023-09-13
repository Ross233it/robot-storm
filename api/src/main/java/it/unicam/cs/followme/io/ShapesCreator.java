package it.unicam.cs.followme.io;

import it.unicam.cs.followme.model.common.TwoDimensionalPoint;
import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.model.environment.StaticCircle;
import it.unicam.cs.followme.model.environment.StaticRectangle;
import it.unicam.cs.followme.utilities.ShapeData;

public class ShapesCreator implements ShapeBuilder{
    @Override
    public Shape createShape(ShapeData shapeData) {
        TwoDimensionalPoint position = new TwoDimensionalPoint(shapeData.args()[0], shapeData.args()[1]);
        if(shapeData.shape().equals("CIRCLE")){
            return new StaticCircle(position, shapeData.label(), shapeData.args()[2]);
        }else   if(shapeData.shape().equals("RECTANGLE")) {
            return new StaticRectangle<TwoDimensionalPoint>(position, shapeData.label(), shapeData.args()[2], shapeData.args()[3]);
        }
        return null;
    }
}
