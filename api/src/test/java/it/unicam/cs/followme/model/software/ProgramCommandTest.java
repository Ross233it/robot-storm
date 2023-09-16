package it.unicam.cs.followme.model.software;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class ProgramCommandTest {
    private Double[] args = {1.0, -5.0, 10.3};
    private String stringArg = "argument";
    ProgramCommand<Double[]> command1 = new ProgramCommand("MOVE", (Object[]) args);
    ProgramCommand command2 = new ProgramCommand("MOVE RANDOM");
    ProgramCommand<String> command3 = new ProgramCommand("SIGNAL", stringArg);


    @Test
    void getInstruction() {
        assertEquals("MOVE", command1.getInstruction());
        assertEquals("MOVE RANDOM", command2.getInstruction());
        assertEquals("SIGNAL", command3.getInstruction());
    }

    @Test
    void getParameters() {
        assertFalse(Arrays.equals(this.args, command1.getParameter()));
        assertEquals("argument", command3.getParameter());
    }
}