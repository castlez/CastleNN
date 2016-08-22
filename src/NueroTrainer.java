import java.util.Arrays;
import java.util.Vector;

/**
 * Created by Jonny on 8/15/2016.
 * Trainer and test file for binary logical operators
 */

public class NueroTrainer {

    private static int trainingReps = 10000;

    public static void main(String [] args){
        NeuralNet nn = new NeuralNet(3, 1, 1, 3, null);
        nn.init3Layer();

        //construct training set
        Double [][] inps = new Double[4][];

        inps = new Double[][]{
            new Double[]{0.0,0.0,0.0},
            new Double[]{0.0,0.0,1.0},
            new Double[]{0.0,1.0,0.0},
            new Double[]{0.0,1.0,1.0},
            new Double[]{1.0,0.0,0.0},
            new Double[]{1.0,1.0,0.0},
            new Double[]{1.0,1.0,1.0}
            };
        Vector<Double> testin = new Vector(Arrays.asList(new Double[]{1.0,0.0,1.0}));

        Double[] outs    = new Double[]{0.0,1.0,1.0,1.0,1.0,1.0,1.0};

        //train the neural net
        nn = train(nn, inps, outs);
        Vector<Double> result = nn.feedforward(testin);
        System.out.println("Lets see what it learned!\nExpected: " +
                           "1.0 \n" +
                           "Acual: " + result.get(0)
        );
    }

    private static NeuralNet train(NeuralNet nn, Double[][] inps, Double[] outs) {
        Vector<Vector<Double>> input = new Vector<>();
        for (int i = 0; i < inps.length; i++) {
            input.add(new Vector(Arrays.asList(inps[i])));
        }

        Vector<Vector<Double>> exact= new Vector<>();
        for (int i = 0; i < outs.length; i++) {
            exact.add(new Vector(Arrays.asList(outs[i])));
        }

        int sample = 0;

        for (int epoch = 0; epoch < trainingReps;epoch++) {
            if (sample == input.size()) {
                sample = 0;
            }
            System.out.println("TRAINING Input: " + input.get(sample).toString());
            System.out.println("TRAINING Expected: " + exact.get(sample).toString() + "\nTraining...");
            Vector<Double> approx = nn.feedforward(input.get(sample));
            nn = nn.backprop(input.get(sample),approx,exact.get(sample));
            System.out.println("TRAINING Actual: " + approx.toString());
            System.out.println("TRAINING Error: " + (approx.get(0) - exact.get(sample).get(0)) + "\n");
            sample++;
        }

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
