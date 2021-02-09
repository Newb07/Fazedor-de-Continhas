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
public class PedidoDeOperacao extends Comunicado {
    private char operacao;
    private double valor;

    public PedidoDeOperacao(char operacao, double valor) {
        this.operacao = operacao;
        this.valor = valor;
    }

    public char getOperacao() {
        return operacao;
    }


    public double getValor() {
        return valor;
    }
    
   public String ToString()
   {
       return("" + this.operacao+this.valor);
   }
}
