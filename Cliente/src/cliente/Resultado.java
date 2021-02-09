/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

/**
 *
 * @author guipa
 */
public class Resultado extends Comunicado {
    private double valorResultante;
    
    public Resultado(double valorResultante)
    {
        this.valorResultante = valorResultante;
    }
    
    public double getValorResultante()
    {
        return this.valorResultante;
    }
    
    public String toString()
    {
        return ("" + this.valorResultante);
    }
}
