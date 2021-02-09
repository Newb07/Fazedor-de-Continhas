/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;


import java.net.*;
import java.util.*;
import java.io.*;


public class SupervisoraDeConexao extends Thread {
    private double valor = 0;
    private Parceiro usuario;
    private Socket conexao;
    private ArrayList<Parceiro> usuarios;
    
    public SupervisoraDeConexao(Socket conexao, ArrayList<Parceiro> usuarios)throws Exception
    {
        if(conexao == null)
            throw new Exception("Conexao ausente");
        
        if(usuarios == null)
            throw new Exception("Usuario ausente");
        
        this.conexao = conexao;
        this.usuarios = usuarios;
    }
    
    public void run()
    {
        ObjectOutputStream transmissor;
        try
        {
            transmissor = 
            new ObjectOutputStream(
            this.conexao.getOutputStream());
        }
        catch(Exception Erro)
        {
            return;
        }
        
        ObjectInputStream receptor = null;
        try
        {
            receptor=
            new ObjectInputStream
            (this.conexao.getInputStream());
        }
        catch(Exception erro)
        {
            try
            {
                transmissor.close();
            }
            catch(Exception falha)
            {}
            return;
        }
        
        try
        {
            this.usuario=
            new Parceiro(this.conexao,
                         receptor,
                         transmissor);
        }
        catch(Exception erro)
        {}
        
        
        try
        {
            synchronized(this.usuarios)
            {
                this.usuarios.add(this.usuario);
            }
            
            for(;;)
            {
                Comunicado comunicado = this.usuario.envie();
                
                if(comunicado ==null)
                    return;
                else if(comunicado instanceof PedidoDeOperacao)
                {
                    PedidoDeOperacao pedidoDeOperacao = (PedidoDeOperacao)comunicado;
                    
                    switch(pedidoDeOperacao.getOperacao())
                    {
                        case '+':
                            this.valor += pedidoDeOperacao.getValor();
                            break;
                            
                        case'-':
                            this.valor -= pedidoDeOperacao.getValor();
                            break;
                            
                        case'*':
                            this.valor *= pedidoDeOperacao.getValor();
                            
                        case'/':
                            this.valor /= pedidoDeOperacao.getValor();
                    }
                }
                
                else if(comunicado instanceof PedidoDeResultado)
                {
                    this.usuario.receba(new Resultado(this.valor));
                }
                else if(comunicado instanceof PedidoParaSair)
                {
                    synchronized(this.usuarios)
                    {
                        this.usuarios.remove(this.usuario);
                    }
                    this.usuario.adeus();
                } 
            }
        }
        catch(Exception erro)
        {
            try
            {
                transmissor.close();
                receptor.close();
            }
            catch(Exception falha)
            {}
            return;
        }
        
    
    }
}
