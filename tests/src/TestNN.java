import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Jonny on 8/15/2016.
 */

public class TestNN {
    
    //ensure the neural networks can be constructed
    @Test
    public void test_ConstructNN(){
        try {
            NeuralNet nn = new NeuralNet(3, 3, 1, 3, null);
        } catch (Exception e) {
            throw new AssertionError(e.getMessage());
        }
    }
}
