/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problema.xor;

/**
 *
 * @author adacf
 */
public class ProblemaXOR {

    public static double XOR_input[][] = {{ 0.0, 0.0 }, { 0.0, 1.0 }, { 1.0, 0.0 }, { 1.0, 1.0 }};
    public static double XOR_ideal[][] = { { 0.0 }, { 1.0 }, { 1.0 }, { 0.0 }};
    
    public static void main(String[] args) {
        // TODO code application logic here
        int activationFunction = 1; // 0 - Linear, 1 - Sigmoid, 2 - Hyperbolic Targent
        int maxRuns = 50000;
        double minError = 0.001;
        
        NeuralNetwork network = new NeuralNetwork(XOR_ideal, activationFunction, XOR_input);
        network.CreateLayers(2, 4);  // 2 neurons to InputLayer , 4 neurons to HiddenLayer
        //network.createConnection();
        network.run(maxRuns, minError);
        
    }
    
}
