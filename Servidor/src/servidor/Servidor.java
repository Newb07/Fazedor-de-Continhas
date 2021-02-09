/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.util.*;

public class Servidor {

    public static String PORTA_PADRAO = "5500";

    public static void main(String[] args) {
        if (args.length == 1) {
            System.err.println("Uso esperado: java Servidor [PORTA]\n");
            return;
        }

        String porta = Servidor.PORTA_PADRAO;

        if (args.length == 1) {
            porta = args[0];
        }

        ArrayList<Parceiro> usuarios
                = new ArrayList<Parceiro>();

        AceitadoraDeConexao aceitadoradeConexao = null;
        try {
            aceitadoradeConexao
                    = new AceitadoraDeConexao(porta, usuarios);
            aceitadoradeConexao.start();
        } catch (Exception erro) {
        }

        for (;;) {
            System.out.println("O servidor esta ativo! Para desativa-lo");
            System.out.println("use o comando \"desativar\"\n");
            System.out.print("> ");

            String comando = null;

            try {
                comando = Teclado.getUmString();
            } catch (Exception erro) {
            }

            if (comando.toLowerCase().equals("desativar")) {
                synchronized (usuarios) {
                    ComunicadoDeDesligamento comunicadoDeDesligamento = new ComunicadoDeDesligamento();

                    for (Parceiro usuario : usuarios) {
                        try {
                            usuario.receba(comunicadoDeDesligamento);
                            usuario.adeus();
                        } catch (Exception erro) {
                        }
                    }
                }

                System.out.println("O servidor foi desativado!\n");
                System.exit(0);

            } else {
                System.err.println("Comando invalido! \n");
            }
        }

    }

}
    
    

