import java.util.Arrays;
import java.util.Vector;

/**
 * Created by Jonny on 8/15/2016.
 * Trainer and test file for binary logical operators
 */

public class NueroTrainer {
    

    public static void main(String [] args){
        NeuralNet nn = new NeuralNet(2, 1, 1, 3, null);
        nn.init3Layer();

        //construct training set
        Double [][] inps = new Double[4][];
        Double[] outs    = new Double[4];
        inps[0] = new Double[]{1.0, 0.0};
        outs[0] = 1.0;
        inps[1] = new Double[]{0.0, 1.0};
        outs[1] = 1.0;
        inps[2] = new Double[]{1.0, 1.0};
        outs[2] = 1.0;
        inps[3] = new Double[]{0.0, 0.0};
        outs[3] = 0.0;

        //train the neural net
        nn = train(nn, inps, outs);
        //test the neural net
//        Vector<Double> inputs = new Vector<>();
//        inputs.add(1.0);
//        inputs.add(0.0);
//        Vector<Double> ans = nn.feedforward(inputs);
//        System.out.println("with an input of: " + inputs.toString() + "\nthe neural net guessed: " + ans);
    }

    private static NeuralNet train(NeuralNet nn, Double[][] inps, Double[] outs) {
        Vector<Double> input = new Vector(Arrays.asList(inps[0]));
        Vector<Double> exact= new Vector(Arrays.asList(outs[0]));

        System.out.println("TRAINING Input: " + input.toString());
        System.out.println("TRAINING Expected: " + exact.toString());
        Vector<Double> approx = nn.feedforward(input);
        System.out.println("TRAINING Actual: " + approx.toString());
        System.out.println("TRAINING Error: " + (approx.get(0) - exact.get(0)));
        return nn;
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
            nn.feedforward(input);
            System.out.println("###UPDATED###");
            System.out.println(nn.toString());

            return nn;



        } catch (Exception e) {
            throw new AssertionError(e.getMessage());
        }
    }
}
