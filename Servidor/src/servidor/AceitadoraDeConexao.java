/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.net.*;
import java.util.*;

public class AceitadoraDeConexao extends Thread {
    private ServerSocket pedido;
    private ArrayList<Parceiro> usuarios;
    
    public AceitadoraDeConexao(String porta, ArrayList<Parceiro> usuarios)throws Exception
    {
        if(porta == null)
            throw new Exception("Porta Ausente");
        
        try
        {
          this.pedido = new ServerSocket(Integer.parseInt(porta));  
        }
        catch(Exception erro)
        {
            throw new Exception("Porta Invalida");
        }
        
        if(usuarios == null)
            throw new Exception("Usuario ausente");
        
        this.usuarios = usuarios;
    }
    
    public void run()
    {
        for(;;)
        {
            Socket conexao = null;
            try
            {
                conexao = this.pedido.accept();
            }
            catch(Exception erro)
            {continue;}
            
            SupervisoraDeConexao supervisoraDeConexao = null;
            try
            {
            supervisoraDeConexao = new SupervisoraDeConexao(conexao, usuarios);
            }
            catch(Exception erro)
            {}
        }
        
        
    }
}
