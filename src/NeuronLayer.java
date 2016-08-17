import javax.swing.plaf.basic.BasicSpinnerUI;
import java.util.Vector;

/**
 * Created by Jonny on 8/15/2016.
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

}
