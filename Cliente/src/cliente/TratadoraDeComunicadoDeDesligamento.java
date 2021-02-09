/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;
import java.net.*;
public class TratadoraDeComunicadoDeDesligamento extends Thread {
    private Parceiro servidor;
    
    public TratadoraDeComunicadoDeDesligamento(Parceiro servidor) throws Exception
    {
        if(servidor == null)
            throw new Exception("Porta Invalida");
        
        this.servidor = servidor;
    }
    
    public void run()
    {
        for(;;)
        {
            try
            {
                if(this.servidor.espie() instanceof ComunicadoDeDesligamento)
                {
                    System.out.println("\n O servidor vai ser desligado agora: ");
                    System.err.println("Volte mais tarde!\n");
                }
            }
            catch(Exception erro)
            {}
        }
    }
}
