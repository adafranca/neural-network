/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problema.xor;

import java.util.Random;

/**
 *
 * @author adacf
 */
public class Connection {

	private Neuron from;
	private Neuron to;
	private double DeltaWeight = 0; // for momentum
	private double weight;

	public Connection(Neuron from, Neuron to) {
		this.from = from;
		this.to = to;		
		this.weight = (new Random().nextDouble() * 2 - 1);		
	}

	/**
	 * @return the from
	 */
	public Neuron getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(Neuron from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public Neuron getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(Neuron to) {
		this.to = to;
	}

	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void adjustWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return the prevDeltaWeight
	 */
	public double getDeltaWeight() {
		return DeltaWeight;
	}

	/**
	 * @param prevDeltaWeight
	 *            the prevDeltaWeight to set
	 */
	public void setDeltaWeight(double DeltaWeight) {
		this.DeltaWeight = DeltaWeight;
	}
}
