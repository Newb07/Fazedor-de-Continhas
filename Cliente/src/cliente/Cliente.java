/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.net.*;
import java.io.*;

public class Cliente {

    public static final String HOST_PADRAO = "localhost";
    public static final int PORTA_PADRAO = 5500;

    public static void main(String[] args) {
        if (args.length > 2) {
            System.err.println("Uso esperado: java cliente [HOST [PORTA]\n");
            return;
        }

        Socket conexao = null;
        try {
            String host = Cliente.HOST_PADRAO;
            int porta = Cliente.PORTA_PADRAO;

            if (args.length > 0) {
                host = args[0];
            }
            if (args.length == 2) {
                porta = Integer.parseInt(args[1]);
            }

            conexao = new Socket(host, porta);

        } catch (Exception erro) {
            System.err.println("Indique o servidor e a porta corretos");
        }

        ObjectOutputStream transmissor = null;
        try {
            transmissor
                    = new ObjectOutputStream(
                            conexao.getOutputStream());
        } catch (Exception erro) {
            System.err.println("Indique o servidor e a porta corretor! \n");
            return;
        }

        ObjectInputStream receptor = null;
        try {
            receptor
                    = new ObjectInputStream(
                            conexao.getInputStream());
        } catch (Exception erro) {
            System.err.println("Indique o servidor e a porta corretos!\n");
            return;
        }

        Parceiro servidor = null;
        try {
            servidor
                    = new Parceiro(conexao, receptor, transmissor);
        } catch (Exception erro) {
            System.err.println("Indique o servidor e a porta corretors! \n");
        }

        TratadoraDeComunicadoDeDesligamento tradadoraDeComunicadoDeDesligamento = null;
        try {
            tradadoraDeComunicadoDeDesligamento = new TratadoraDeComunicadoDeDesligamento(servidor);
        } catch (Exception erro) {
        }

        tradadoraDeComunicadoDeDesligamento.start();

        char opcao = ' ';
        do {
            System.out.print("Sua opcao (+,-,*,/,=, [T]erminar)?");

            try {
                opcao = Character.toUpperCase(Teclado.getUmChar());
            } catch (Exception erro) {
                System.err.println("Opcao invalida!\n");
                continue;
            }

            if ("+_*/=T".indexOf(opcao) == -1) {
                System.err.println("Opcão invalida!\n");
                continue;
            }

            try {
                double valor = 0;
                if ("+-*/".indexOf(opcao) != -1) {
                    System.out.print("Valor? ");
                    try {
                        valor = Teclado.getUmDouble();
                        System.out.println();

                        if (opcao == '/' && valor == 0) {
                            System.err.println("Valor Invalido! \n");
                            continue;
                        }
                    } catch (Exception erro) {
                        System.err.println("Valor Invalido! \n");
                        continue;
                    }
                    servidor.receba(new PedidoDeOperacao(opcao, valor));
                } else if (opcao == '=') {
                    servidor.receba(new PedidoDeResultado());
                    Comunicado comunicado = null;
                    do {
                        comunicado = (Comunicado) servidor.espie();
                    } while (!(comunicado instanceof Resultado));
                    Resultado resultado = (Resultado) servidor.envie();
                    System.out.println("Resultado atual: " + resultado.getValorResultante() + "\n");
                }
            } catch (Exception erro) {
                System.err.println("Erro de comunicadcao com o servidor");
                System.err.println("Tente novamente!");
                System.err.println("Caso o erro persista, termine o programa");
                System.err.println("e volte a tentar mais tarde!\n");
            }
        } while (opcao != 'T');

        try {
            servidor.receba(new PedidoParaSair());
        } catch (Exception erro) {
        }

        System.out.println("Obrigado por usar este programa");
        System.exit(0);

    }

}
