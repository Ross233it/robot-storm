package it.unicam.cs.followme.io;

import it.unicam.cs.followme.model.environment.Shape;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import it.unicam.cs.followme.utilities.ShapeData;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import java.util.stream.Collectors;

/**
 * Questa classe ha il ruolo di creare le forme conformemente a quanto
 * richiesto dalla simulazione partendo da un file di cui effettua il parsing.
 */
public class ShapeLoader {
    private final Path environmentPath;
    private final FollowMeParser parser;
    private final ShapeBuilder shapeBuilder;

    public ShapeLoader(FollowMeParser parser, Path environmentPath, ShapeBuilder shapeBuilder) {
        this.parser = parser;
        this.environmentPath = environmentPath;
        this.shapeBuilder = shapeBuilder;
    }

    /**
     * Recupera le forme e crea nuovi oggetti
     * @return parsedSahpes una lista oggetti forma
     */
    public List<Shape> loadShapes() {
        List<ShapeData> parsedShapes = parseShapes();
        return parsedShapes.stream()
                .map(shapeBuilder::createShape)
                .collect(Collectors.toList());
    }

    /**
     * Effettua il parse delle forme statiche presenti in un file di testo
     * @return parsedShapes un lista di forme conformi al linguaggio
     */
    private List<ShapeData> parseShapes() {
        try {
            return parser.parseEnvironment(environmentPath);
        } catch (IOException | FollowMeParserException e) {
            throw new RuntimeException(e);
        }
    }
}
