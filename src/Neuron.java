/**
 * Created by Jonny on 8/15/2016.
 */

import java.util.Random;
import java.util.Vector;
public class Neuron {

    //number of inputs
    Integer numInputs;

    //weights
    Vector<Double> vecWeights;

    //initialize all weights to random values
    Neuron(Integer numInputs) {
        for(int i = 0; i <numInputs+1;i++) {
            vecWeights.add(new Random().nextDouble());
        }
    }
}
