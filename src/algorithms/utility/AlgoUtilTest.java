package algorithms.utility;

import junit.framework.Test;
import junit.framework.TestSuite; 
import junit.framework.TestCase; 

/** 
* AlgoUtil Tester. 
* 
* @author <Authors name> 
* @since <pre>03/10/2020</pre> 
* @version 1.0 
*/ 
public class AlgoUtilTest extends TestCase {
    //NOTE: This class has been moved to the same package as AlgoUtil
    public AlgoUtilTest(String name) {
    super(name);
    }

    public void setUp() throws Exception {
    super.setUp();
    }

    public void tearDown() throws Exception {
    super.tearDown();
    }

    /**
    *
    * Method: truncate(double value, double decimalCount)
    *
    */
    public void testTruncate() throws Exception {
    //TODO: Test goes here...
        assert AlgoUtil.truncate(3.1415, 2) == 3.14;
        assert AlgoUtil.truncate(3.9235634, 4) == 3.9235;
        assert AlgoUtil.truncate(3.393939, 5) == 3.39393;
        assert AlgoUtil.truncate(1.0/7.0, 5) == 0.14285;
        assert AlgoUtil.truncate(1.0/11.0, 5) == 0.09090;
        //ADD: Added negative value test
        assert AlgoUtil.truncate(-2.4142, 2) == -2.41;
        assert AlgoUtil.truncate(-0.3123456, 5) == -0.31234;
    }



    public static Test suite() {
    return new TestSuite(AlgoUtilTest.class);
    }
} 
