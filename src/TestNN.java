
import java.util.DoubleSummaryStatistics;
import java.util.Vector;

/**
 * Created by Jonny on 8/15/2016.
 */

public class TestNN {
    
    //ensure the neural networks can be constructed
    public static void main(String [] args){
        try {
            // make 3 layers with 3 inputs each
            Vector<NeuronLayer> layers = new Vector<NeuronLayer>();
            layers.add(new NeuronLayer(3,3));
            System.out.println("LAYER MADE");
            layers.add(new NeuronLayer(3,3));
            System.out.println("LAYER MADE");
            layers.add(new NeuronLayer(3,3));
            System.out.println("LAYER MADE");

            //make neural net with 3 inputs, 3 outputs, 1 hidden layer and 3 neurons per layer
            NeuralNet nn = new NeuralNet(3, 3, 1, 3, layers);
            System.out.println("NET MADE");


            //make an input to test the update
            Vector<Double> input = new Vector<Double>();
            input.add(2.4);
            input.add(2.9);
            input.add(2.1);

            //update
            nn.Update(input);
            System.out.println("UPDATED");


        } catch (Exception e) {
            throw new AssertionError(e.getMessage());
        }
    }
}
