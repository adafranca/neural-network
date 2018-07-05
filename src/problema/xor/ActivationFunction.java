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
public final class ActivationFunction {
    
    private double x;
    private int choice;
    
    public ActivationFunction(int choice){
        this.choice = choice;
    }
    
    double linear(double x){
        return x;
    }
    
    double sigmoid(double x){
        return (1.0/(1.0 + (Math.exp(-x))));
    }
    
    double hyperbolicTangent(double x){
        return ((Math.exp(x*2.0)-1.0)/(Math.exp(x*2.0)+1.0));
    }
    
    public double getActivationFunction(double x){
        
        if( choice == 0)
            return linear(x);
        if( choice == 1)
            return sigmoid(x);
        if( choice == 2)
            return hyperbolicTangent(x);
        
        return linear(x);
    }

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(float x) {
        this.x = x;
    }
    
}
