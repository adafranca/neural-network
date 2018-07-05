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
public class Neuron {

	private int id;
	private String type;
	private ArrayList<Connection> connections; // Conections with others neurons
	private double output;
	private boolean bias = false; // Used to shift the decision boundary left or
									// right
	private final ActivationFunction activationFunction;

	// A regular Neuron
	public Neuron(int id, int choiceActivationFunction) {
		this.id = id;
		this.connections = new ArrayList<>();
		this.activationFunction = new ActivationFunction(choiceActivationFunction);
	}

	// A bias Neuron
	public Neuron(int id, int choiceActivationFunction, int output) {
		this.id = id;
		this.output = output;
		this.connections = new ArrayList<>();
		this.activationFunction = new ActivationFunction(choiceActivationFunction);
		bias = true;		
	}

	// Function to calculate output of this neuron
	// It's the sum of all inputs * weight of connections
	// Compute Sj = Wij * Aij + W0j * bias
	public void calculateOutput() {

		if (!isBias()) {
			double sum = 0;
			double bias = 0;

			for (int i = 0; i < this.getConnections().size(); i++) {
				Connection c = this.getConnections().get(i);
				Neuron from = c.getFrom();

				if (from.isBias()) {
					bias = from.getOutput() * c.getWeight();					
				} else {
					sum += from.getOutput() * c.getWeight();
				}
			}
			
			output = this.activationFunction.getActivationFunction(sum);
		}
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the connections
	 */
	public ArrayList<Connection> getConnections() {
		return connections;
	}

	/**
	 * @param connections
	 *            the connections to set
	 */
	public void setConnections(ArrayList<Connection> connections) {
		this.connections = connections;
	}

	/*
	 * public void addConnection(Connection neuron){
	 * this.connections.add(neuron); }
	 */

	public void addConnection(ArrayList<Neuron> inNeurons) {
		for (Neuron n : inNeurons) {
			Connection con = new Connection(n, this);
			this.connections.add(con);
		}
	}

	/**
	 * @return the output
	 */
	public double getOutput() {
		return output;
	}

	/**
	 * @param output
	 *            the output to set
	 */
	public void setOutput(double output) {
		this.output = output;
	}

	/**
	 * @return the bias
	 */
	public boolean isBias() {
		return bias;
	}

	/**
	 * @param bias
	 *            the bias to set
	 */
	public void setBias(boolean bias) {
		this.bias = bias;
	}

}
