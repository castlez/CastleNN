import javax.swing.plaf.basic.BasicSpinnerUI;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by Jonny on 8/15/2016.
 * Manages a layer of neurons
 */
public class NeuronLayer {
    // number of neurons in this layer
    Integer numNeurons;

    //layer of neurons
    Vector<Neuron> vecNeurons;

    NeuronLayer(Integer numNeurons, Integer inputsPerNeuron){
        vecNeurons = new Vector<Neuron>();
        this.numNeurons = numNeurons;
        for (int i = 0; i < numNeurons;i++){
            vecNeurons.add(new Neuron(inputsPerNeuron));
        }
    }
    @Override
    public String toString(){
        if (vecNeurons.size() < 1) {
            return "empty neural net";
        }
        String outString = "(" + vecNeurons.get(0).toString();
        for (int i = 1; i < vecNeurons.size(); i++) {
            outString += "\n" + vecNeurons.get(i).toString();
        }
        return outString + ")";
    }

    public Vector<Double> getWeights() {
        Vector<Double> weights = new Vector<Double>();
        for (Neuron n : vecNeurons) {
            weights.addAll(n.getWeights());
        }
        return weights;
    }

    //replaces the weights with new ones
    void putWeights(Vector<Double> weights){
        for (Neuron n : vecNeurons) {
            Vector<Double> tempw = new Vector<Double>();
            for (int i = 0; i < n.getWeights().size();i++) { //might want to find a better way to do this
                tempw.add(weights.get(i));
            }
            n.putWeights(tempw);
        }
    }
}
