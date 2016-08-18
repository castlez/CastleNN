// this is the neural net class 
// // TODO: 8/15/2016  will be updated as the methods are needed

import java.util.StringJoiner;
import java.util.Vector;
import java.util.regex.Matcher;

/**
 * Created by Jonny on 8/15/2016.
 * Manages the neural net
 */

public class NeuralNet {

    private Integer numInputs;

    private Integer numOutputs;

    private Integer numHiddenLayers;

    private Integer numNeuronsPerHiddenLyr;

    private Vector<NeuronLayer> vecLayers;

    //if you pass null in for vecLayers, you must call init to build the neural network
    public NeuralNet(Integer numInputs, Integer numOutputs, Integer numHiddenLayers, Integer numNeuronsPerHiddenLyr, Vector<NeuronLayer> vecLayers) {
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
        this.numHiddenLayers = numHiddenLayers;
        this.numNeuronsPerHiddenLyr = numNeuronsPerHiddenLyr;
        this.vecLayers = vecLayers!=null ? vecLayers : new Vector<NeuronLayer>();
    }

    //initializes a 3 layer neural net
    public void init3Layer() {
        //create input layer
        NeuronLayer templayer = new NeuronLayer(numInputs, 1);
        vecLayers.add(templayer);

        //the hidden layer
        templayer = new NeuronLayer(numNeuronsPerHiddenLyr, numInputs);
        vecLayers.add(templayer);

        //output layer
        templayer = new NeuronLayer(numOutputs, numNeuronsPerHiddenLyr);
        vecLayers.add(templayer);
    }

    Vector<Double> getWeights(){
        Vector<Double> myweights = new Vector<Double>();
        for (NeuronLayer layer : vecLayers) {
            myweights.addAll(layer.getWeights());
        }
        return myweights;
    }

    //returns the total number of weights in the net
    Integer GetNumberOfWeights(){
        Integer numweights = 0;
        for (NeuronLayer nl : vecLayers) {
            numweights += nl.getWeights().size();
        }
        return numweights;
    }



    //replaces the weights with new ones
    void putWeights(Vector<Double> weights){
        for (NeuronLayer nl : vecLayers) {
            Vector<Double> tempw = new Vector<Double>();
            for (int i = 0; i < nl.getWeights().size()/nl.numNeurons;i++) { //might want to find a better way to do this
                tempw.add(weights.remove(0));
            }
            nl.putWeights(tempw);
        }
    }



    /* calculates the outputs from a set of inputs */
    Vector<Double> Update(Vector<Double> inputs){

        //stores the resultant outputs from each layer
        Vector<Double> outputs = new Vector<Double>();

        Integer cWeight = 0;

        //first check that we have the correct amount of inputs
        if (inputs.size() != numInputs) {
            //just return an empty vector if incorrect.
            return outputs;
        }

        //For each layer....
        for (int i=0; i<numHiddenLayers + 1; ++i) {
            if ( i > 0 ) {
                inputs = outputs;
            }

            outputs = new Vector<Double>();;

            cWeight = 0;

            //for each neuron sum the (inputs * corresponding weights).Throw
            //the total at our sigmoid function to get the output.
            for (int j=0; j<vecLayers.get(i).numNeurons; ++j) {

                double netinput = 0;
                int NumInputs = vecLayers.get(i).vecNeurons.get(j).numInputs;

                //for each weight
                for (int k=0; k<NumInputs - 1; ++k) {
                    //sum the weights x inputs
                    netinput += vecLayers.get(i).vecNeurons.get(j).vecWeights.get(k) * inputs.get(cWeight++);
                }

                //add in the bias
                //todo: bias: netinput += vecLayers.get(i).vecNeurons.get(j).vecWeights.get(NumInputs-1) * CParams::dBias;

                //we can store the outputs from each layer as we generate them.
                //The combined activation is first filtered through the sigmoid
                //function
                outputs.add(Sigmoid(netinput));
                cWeight = 0;
            }
        }
        return outputs;
    }

    @Override
    public String toString(){
        if (vecLayers.size() < 1) {
            return "empty neural net";
        }
        String outString = vecLayers.get(0).toString();
        for (int i = 1; i < vecLayers.size(); i++) {
            outString += "\n\n" + vecLayers.get(i).toString();
        }
        return outString;
    }

    //sigmoid response curve
    // originally Double Sigmoid(Double activation, Double response)
    // pretty sure the second argument is always 1 for most cases
    Double Sigmoid(Double activation){return 1/(1 + Math.pow(Math.E, (-1)*activation));}


}
