import algorithms.utility.AlgoUtil;
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
} 



public static Test suite() { 
return new TestSuite(AlgoUtilTest.class); 
} 
} 
