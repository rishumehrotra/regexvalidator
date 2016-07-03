import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

/**
 * Created by rishumehrotra on 03/07/16.
 */
public class PatternVerifierTest {

    private static final String resourcesDirectory="src/test/resources";

    @Test(dataProvider = "validExpressions")
    public void testValidExpression(String expression) throws Exception
    {
        Assert.assertTrue(Validator.patternVerifier(expression));
    }

    @Test(dataProvider = "invalidExpressions")
    public void testInvalidExpression(String expression) throws Exception {
        Assert.assertFalse(Validator.patternVerifier(expression));
    }

    @DataProvider(name="validExpressions")
    public Object [][] provideValidData() throws Exception {

        List<String> data = FileUtils.readLines(new File(this.getClass().getResource("/validExpressions.txt").getFile()));
        Object [][] testData = new Object[data.size()][1];

        for(int i=0;i<testData.length;i++)
        {
            testData[i][0]=data.get(i);
        }

        return testData;

    }

    @DataProvider(name="invalidExpressions")
    public Object [][] provideInValidData() throws Exception {

        List<String> data = FileUtils.readLines(new File(this.getClass().getResource("/invalidExpressions.txt").getFile()));

        Object [][] testData = new Object[data.size()][1];

        for(int i=0;i<testData.length;i++)
        {
            testData[i][0]=data.get(i);
        }

        return testData;
    }
}
