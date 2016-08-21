// this is the neural net class
// // TODO: 8/15/2016  will be updated as the methods are needed

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.Vector;
import java.util.regex.Matcher;

/**
 * Created by Jonny on 8/15/2016.
 * Manages the neural net
 */

public class NeuralNet {

    private Double learningRate = 0.2;

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
                tempw.add(weights.get(i));
            }
            nl.putWeights(tempw);
        }
    }



    /* calculates the outputs from a set of inputs */
    Vector<Double> feedforward(Vector<Double> inputs){
        Vector<Double> outputs = new Vector<>();
        Vector<Double> inhid = new Vector<>();
        Double sum = 0.0;

        //input to hidden layer
        for (int i = 0; i < numNeuronsPerHiddenLyr;i++) {
            sum = 0.0;
            for (int k = 0; k < inputs.size(); k++) {
                sum += vecLayers.get(1).vecNeurons.get(k).activate(inputs);
            }
            //add bias
            sum += vecLayers.get(1).vecNeurons.get(i).vecWeights.get(vecLayers.get(0).vecNeurons.get(i).vecWeights.size()-1);
            //vecLayers.get(1).vecNeurons.get(i).activate(inputs)
            inhid.add(Sigmoid(sum));
        }

        //hidden to out layer
        for (int i = 0; i < numOutputs; i++) {
            sum = 0.0;
            for (int k = 0; k < numNeuronsPerHiddenLyr; i++) {
                sum += inhid.get(i) * vecLayers.get(numHiddenLayers+1).vecNeurons.get(i).vecWeights.get(k);
            }
            int sweights = vecLayers.get(numHiddenLayers+1).vecNeurons.get(i).vecWeights.size();
            sum += vecLayers.get(numHiddenLayers+1).vecNeurons.get(i).vecWeights.get(sweights-1);
            outputs.add(Sigmoid(sum));
        }
        return outputs;
    }

    //takes outputs generated by the feedforward
    //and adjusts weights
    NeuralNet backprop(Vector<Double> inputs, Vector<Double> outputs, Vector<Double> expected){
        Vector<Double> errh = new Vector<>();
        Vector<Double> erro = new Vector<>();
        Double terr = 0.0;
        // Calculate the output layer error
        for (int i = 0; i < numOutputs; i++) {
            terr = (expected.get(i) - outputs.get(i)) * sigmoidDerivative(outputs.get(i));
            erro.add(terr);
        }
        // Calculate the hidden layer error
        Vector<Double> tweights = new Vector<>();
        int wi;
        for (int i = 0; i < numNeuronsPerHiddenLyr; i++) {
            terr = 0.0;
            for (int k = 0; k < numOutputs; k++) {
                terr += erro.get(k) * vecLayers.get(1).vecNeurons.get(i).vecWeights.get(k); //numlayers == 1
            }
            errh.add(terr*sigmoidDerivative(vecLayers.get(1).vecNeurons.get(i).activate(inputs)));
        }

        // Update the weights for the output layer
        for (int i = 0; i < numOutputs; i++) {
            for (int k = 0; k<numNeuronsPerHiddenLyr; i++) {
                vecLayers.get(numHiddenLayers+1).vecNeurons.get(i).vecWeights.set(k,
                            learningRate *
                            erro.get(i) *
                            vecLayers.get(numNeuronsPerHiddenLyr+1).vecNeurons.get(i).activate(inputs)
                );
                //update bias
                vecLayers.get(numHiddenLayers+1).vecNeurons.get(i).vecWeights.set(numNeuronsPerHiddenLyr,
                            learningRate * erro.get(i)
                        );
            }
        }

        // Update the weights for the hidden layer
        for (int i = 0; i < numNeuronsPerHiddenLyr; i++) {
            for (int k = 0; k<numInputs;i++) {
                vecLayers.get(numHiddenLayers).vecNeurons.get(i).vecWeights.set(k,
                        learningRate *
                        errh.get(i) *
                        vecLayers.get(numNeuronsPerHiddenLyr+1).vecNeurons.get(i).activate(inputs)
                );
                //update bias
                vecLayers.get(numInputs).vecNeurons.get(i).vecWeights.set(numNeuronsPerHiddenLyr,
                        learningRate * errh.get(i)
                );
            }
        }


        //return newly weighted nn
        return this;
    }

    private static Double sigmoidDerivative(final Double val)
    {
        return (val * (1.0 - val));
    }

    //sigmoid response curve
    // originally Double Sigmoid(Double activation, Double response)
    // pretty sure the second argument is always 1 for most cases
    Double Sigmoid(Double activation){return 1/(1 + Math.pow(Math.E, (-1)*activation));}

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




}
