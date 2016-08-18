/**
 * Created by Jonny on 8/15/2016.
 * manages a neuron
 */

import java.util.Collection;
import java.util.Random;
import java.util.Vector;
public class Neuron {

    //number of inputs
    Integer numInputs;

    //weights
    Vector<Double> vecWeights;

    //initialize all weights to random values
    Neuron(Integer numInputs) {
        vecWeights = new Vector<>();
        this.numInputs = numInputs;
        for(int i = 0; i <numInputs+1;i++) {
            vecWeights.add(new Random().nextDouble());
        }
    }

    @Override
    public String toString(){
        String outString = "{" +
                "N_" + this.hashCode() +
                ":\n\tins=" + this.numInputs +
                "\n\tweights=[" + vecWeights +
                "]}";
        return outString;
    }

    public Vector<Double> getWeights() {
        return vecWeights;
    }

    public void putWeights(Vector<Double> weights) {
        vecWeights = weights;
    }
}
