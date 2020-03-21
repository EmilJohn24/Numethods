package algorithms;

import org.junit.Test;

public class RootIterationTest {
    @Test
    public void completeTest() {
        DataSet.Template iterTemplate = new DataSet.Template("x", "y", "z");
        DataSet.DataSetGenerator iterationGenerator = new DataSet.DataSetGenerator(iterTemplate);
        DataSet iter = iterationGenerator.create(3.0, 4.0, 5.0);
        assert iter.getValueOf("x") == 3.0;
        assert iter.getValueOf("y") == 4.0;
    }
}