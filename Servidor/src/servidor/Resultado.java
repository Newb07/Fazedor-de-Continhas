/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

/**
 *
 * @author guipa
 */
public class Resultado extends Comunicado{
    private double valorRsultante;
    
    public Resultado(double valorResultante)
    {
        this.valorRsultante = valorResultante;
    }
    
    public double getValorResultante()
    {
        return this.valorRsultante;
    }
    public String toString()
    {
        return("" + this.valorRsultante);
    }
}
