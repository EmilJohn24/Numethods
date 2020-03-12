package algorithms;

import junit.framework.TestCase;
import org.junit.Test;

public class RootIterationTest {
    @Test
    public void completeTest() {
        RootIteration.Template iterTemplate = new RootIteration.Template("x", "y", "z");
        RootIteration.RootIterationGenerator iterationGenerator = new RootIteration.RootIterationGenerator(iterTemplate);
        RootIteration iter = iterationGenerator.generate();
        iter.assignTo("x", 3.0);
        assert iter.getValueOf("x") == 3.0;
        iter.assignTo("y", 4.0);
        assert iter.getValueOf("y") == 4.0;
    }
}