import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 * Created by rishumehrotra on 03/07/16.
 */
public class DirectoryReaderTest {

    @Test
    public void readCorrectDirectory() throws Exception {

        String expression = Validator.formExpression(this.getClass().getResource("/testDirectory").getFile());
        Assert.assertTrue(Validator.patternVerifier(expression));
    }

    @Test
    public void readSingleInput() throws Exception {

        String expression = Validator.formExpression(this.getClass().getResource("/SingleFileInput.txt").getFile());
        org.testng.Assert.assertTrue(Validator.patternVerifier(expression));
    }

    @Test
    public void readFromNull() throws Exception {

        String expression = Validator.formExpression("someString.txt");
    }
}
