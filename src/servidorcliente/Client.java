package servidorcliente;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception {

        System.out.println("##########Cliente##########");

        System.out.println("Iniciando conexao com o servidor");

        Socket socket = new Socket("localhost", 9898);

        System.out.println("Conexao Establish");

        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        PrintStream out = new PrintStream(output);

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("Digite um comando: ");
            String mensagem = sc.nextLine();

            

            if (mensagem.equalsIgnoreCase("index")) {
                
                out.println(mensagem);
                
                String received = in.readLine();
            
                
                System.out.println("Lista de arquivos :" + received);
           
            } else if (mensagem.equalsIgnoreCase("get")) {
                
                System.out.println("Digite o nome do arquivo");
                String arquivo = sc.nextLine();
                
                out.println(mensagem);
                System.out.println("enviou mensagem");
                out.println(arquivo);
                System.out.println("enviou arquivo");
                
                getFileFromServeR(socket, arquivo);
            }else{
           
            out.println(mensagem);
            String received = in.readLine();
            System.out.println("Mensagem do servidor:" + received);

            }
        }
    }

    private static void getFileFromServeR(Socket sockServer, String arquivo) throws Exception {

        FileOutputStream fos = null;
        InputStream is = null;

        Scanner sc = new Scanner(System.in);
        is = sockServer.getInputStream();

        // Cria arquivo local no cliente
        //
        fos = new FileOutputStream(new File("src/DestinoArquivos/" + arquivo ));

        System.out.println("Arquivo Local Criado /src/DestinoArquivos/" + arquivo);

        // Prepara variaveis para transferencia
        byte[] cbuffer = new byte[1024];
        int bytesRead;
        int i =0;
        // Copia conteudo do canal
        while ((bytesRead = is.read(cbuffer)) != 1 || i!=100) {
            fos.write(cbuffer, 0, bytesRead);
            fos.flush();
            System.out.println("Baixando ainda!");
            
            
            if(i==100){
                break;
            }
            
        }
        System.out.println("Transferencia concluida!");
        
    }
    
            
           
}
