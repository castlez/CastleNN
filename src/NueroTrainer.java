import java.util.Vector;

/**
 * Created by Jonny on 8/15/2016.
 * Trainer and test file for binary logical operators
 */

public class NueroTrainer {
    

    public static void main(String [] args){
        NeuralNet nn = new NeuralNet(2, 1, 1, 3, null);
        nn.init3Layer();
        //nn = initNN(nn);
        //nn = testGetWeightsFromNeuralNet(nn);
    }

    public static void train(Vector<Double> trainingset, Vector<Double> solns){
        System.out.println("trainging set: " + trainingset.toString());
        System.out.println("Solution set:" + solns.toString());
    }

    public static NeuralNet testGetWeightsFromNeuralNet(NeuralNet nn){
        System.out.println("number of weights: " + nn.GetNumberOfWeights());
        return nn;
    }

    public static NeuralNet initNN(NeuralNet nn){
        try {
            // make 3 layers with 3 inputs each
            Vector<NeuronLayer> layers = new Vector<NeuronLayer>();
            layers.add(new NeuronLayer(3,3));
            System.out.println("###LAYER MADE###");
            layers.add(new NeuronLayer(3,3));
            System.out.println("###LAYER MADE###");
            layers.add(new NeuronLayer(3,3));
            System.out.println("###LAYER MADE###");

            //make neural net with 3 inputs, 3 outputs, 1 hidden layer and 3 neurons per layer
            nn = new NeuralNet(3, 3, 1, 3, layers);
            System.out.println("###NET MADE###");
            System.out.println(nn.toString());


            //make an input to test the update
            Vector<Double> input = new Vector<Double>();
            input.add(0.4);
            input.add(0.9);
            input.add(0.1);

            //update
            nn.Update(input);
            System.out.println("###UPDATED###");
            System.out.println(nn.toString());

            return nn;



        } catch (Exception e) {
            throw new AssertionError(e.getMessage());
        }
    }
}
