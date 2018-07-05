/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problema.xor;

import java.util.ArrayList;

/**
 *
 * @author adacf
 */
public class NeuralNetwork {

	// User defined learning rate - used for updating the network weights
	private static final double LEARNING_RATE = 0.9f;

	// ArrayList to save all the neurons from InputLayers
	private final ArrayList<Neuron> inputLayers = new ArrayList<Neuron>();

	// ArrayList to save all the neurons from HiddenLayers
	private final ArrayList<Neuron> hiddenLayers = new ArrayList<Neuron>();

	// The NeuralNetwork just have one neuron in OutputLayer
	private Neuron outputNeuron;

	// Used to shift between all the activations functions
	private final int ActFunction;

	// The user-defined input pattern for a set of samples
	private final double[][] inputs;

	private double[][] resultOutputs = { { -1 }, { -1 }, { -1 }, { -1 } };;

	private final double[][] expectedOutputs;

	// Users defined momentum - used for updating the network weights
	private final double momentum = 0.7f;

	final double epsilon = 0.00000000001;

	public NeuralNetwork(double[][] expectedOutputs, int ActFunction, double[][] inputs) {

		this.expectedOutputs = expectedOutputs;

		this.ActFunction = ActFunction;

		this.inputs = inputs;

	}

	public void CreateLayers(int countNeuronsInput, int countNeuronsHidden) {
		int i = 0, j = 0;

		for (i = 0; i < countNeuronsInput; i++) {
			Neuron neuron = new Neuron(i, ActFunction);
			this.inputLayers.add(neuron);
		}

		for (j = 0; j < countNeuronsHidden; j++) {
			Neuron neuron = new Neuron(i, ActFunction);
			neuron.addConnection(inputLayers);
			hiddenLayers.add(neuron);
			i++;
		}

		outputNeuron = new Neuron(i, ActFunction);
		outputNeuron.addConnection(hiddenLayers);
		i++;

		// Make Bias Neurons
		Neuron biasneuron = new Neuron(i, ActFunction, -1);
		inputLayers.add(biasneuron);
		hiddenLayers.add(biasneuron);

	}

	/*
	 * public void createConnection(){
	 * 
	 * // Creates the output Neuron outputNeuron = new Neuron(0,ActFunction);
	 * 
	 * /*for(int i=0; i<inputLayers.size()-1;i++){ // Get the Input Layers
	 * for(int j=0; j<hiddenLayers.size()-1;j++){ // Get the Hidden Layer
	 * Connection c = new Connection(inputLayers.get(i), hiddenLayers.get(j));
	 * //inputLayers.get(i).addConnection(c);
	 * hiddenLayers.get(j).addConnection(c); } }
	 */

	/*
	 * for(int i=0; i< hiddenLayers.size()-1; i++){
	 * 
	 * for( int j=0; j< inputLayers.size()-1; j++){ Connection c = new
	 * Connection(inputLayers.get(j), hiddenLayers.get(i));
	 * hiddenLayers.get(j).addConnection(c); } }
	 * 
	 * for ( int i=0; i< hiddenLayers.size(); i++){ Connection c= new
	 * Connection(hiddenLayers.get(i), outputNeuron);
	 * hiddenLayers.get(i).addConnection(c); //outputNeuron.addConnection(c); }
	 * 
	 * }
	 */

	public void feedForward(double[] inputValues) {

		for (int i = 0; i < inputValues.length; i++) {
			this.inputLayers.get(i).setOutput(inputValues[i]);
		}

		for (int i = 0; i < hiddenLayers.size() - 1; i++) {
			this.hiddenLayers.get(i).calculateOutput();
		}

		outputNeuron.calculateOutput();
	}

	public void train(double[] expectedOutput) {

		// error check, normalize value ]0;1[
		for (int i = 0; i < expectedOutput.length; i++) {
			double d = expectedOutput[i];
			if (d < 0 || d > 1) {
				if (d < 0)
					expectedOutput[i] = 0 + this.epsilon;
				else
					expectedOutput[i] = 1 - this.epsilon;
			}
		}

		// BackPropagation

		ArrayList<Connection> connections = outputNeuron.getConnections();
		// OutputLayer
		for (int i = 0; i < connections.size(); i++) {
			Connection c = connections.get(i);
			Neuron neuron = c.getFrom(); // Left neuron

			double partialDerivate = -outputNeuron.getOutput() * (1 - outputNeuron.getOutput())
					* c.getFrom().getOutput() * (expectedOutput[0] - outputNeuron.getOutput());
			double deltaWeight = -LEARNING_RATE * partialDerivate;
			c.adjustWeight((deltaWeight + c.getWeight() + momentum) * c.getDeltaWeight());
			c.setDeltaWeight(deltaWeight);

		}

		// HiddenLayer
		for (int i = 0; i < hiddenLayers.size(); i++) {
			double sum = 0;
			Neuron n = hiddenLayers.get(i);
			ArrayList<Connection> connectionsHidden = n.getConnections();

			for (int j = 0; j < connectionsHidden.size(); j++) {

				Connection c = connectionsHidden.get(j);
				// Just get the connection that the neuron is sending. it's from
				// hiddenLayer to outputLayer
				if (c.getFrom() == hiddenLayers.get(i) && c.getTo() == outputNeuron) {
					sum += (-(expectedOutput[j] - outputNeuron.getOutput()) * outputNeuron.getOutput()
							* (1 - outputNeuron.getOutput()) * c.getWeight());
				}

				double partialDerivate = n.getOutput() * (1 - n.getOutput()) * c.getFrom().getOutput() * sum;
				double deltaWeight = -LEARNING_RATE * partialDerivate;
				double newWeight = c.getWeight() + deltaWeight;
				c.adjustWeight(newWeight + momentum * deltaWeight);
				c.setDeltaWeight(deltaWeight);

			}

			/*
			 * for( int j=0; j< connectionsHidden.size(); j++){ Connection c =
			 * connectionsHidden.get(j);
			 * 
			 * if( c.getTo() == hiddenLayers.get(i)){ float output =
			 * hiddenLayers.get(i).getOutput(); float deltaHidden = output * (1
			 * - output); // Calculates the derivate of a output of neuron from
			 * hiddenLayer Neuron neuron = c.getFrom(); float deltaWeight =
			 * neuron.getOutput()*deltaHidden;
			 * c.adjustWeight(deltaWeight*LEARNING_RATE); } }
			 */
		}

	}

	public void run(int maxSteps, double minError) {

		int i = 0;
		double error = 1;

		while (i < maxSteps && error > minError) {
			error = 0;
			for (int j = 0; j < this.inputs.length; j++) {

				this.feedForward(this.inputs[j]);

				this.resultOutputs[j][0] = this.outputNeuron.getOutput();

				for (int k = 0; k < expectedOutputs[j].length; k++) {
					double err = Math.pow(this.outputNeuron.getOutput() - expectedOutputs[j][k], 2);
					error += err;
				}

				this.train(this.expectedOutputs[j]);
			}
			i++;
		}

		this.printResult();
	}

	public void printResult() {

		System.out.println("-- TRAINING XOR EXAMPLE NEURAL NETWORK --");

		for (int i = 0; i < inputs.length; i++) {
			System.out.print(" Input[" + i + "]: { ");
			for (int j = 0; j < inputs[i].length; j++) {
				System.out.print(inputs[i][j] + " ");
			}

			System.out.print("}");

			System.out.print(" Expected: ");
			System.out.print(this.expectedOutputs[i][0] + " ");

			System.out.print(" Actual ");
			System.out.print(this.resultOutputs[i][0] + " ");

			System.out.println();

		}
	}

}
