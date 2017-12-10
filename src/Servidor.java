
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

public class Servidor {
    String [] lista_arquivos ;
    ListaArquivos a = new ListaArquivos();

    public static void main(String[] args) throws IOException {

        // Criando servidor
        Servidor server = new Servidor();

        server.waitForClient();

    }

    public void waitForClient() throws IOException {
        // Checa se a transferencia foi completada com sucesso

        ServerSocket serversock = new ServerSocket(13267);
        Socket sock = serversock.accept();
        
         BufferedReader fromClient = 
                new BufferedReader(new InputStreamReader(sock.getInputStream()));
         System.out.println(fromClient);
         String command = fromClient.readLine();
         System.out.println("Comando é "+command);
        
        if (command.equalsIgnoreCase("INDEX")) {
            lista_arquivos = ListaArquivos.listaArquivos();
            System.out.println(Arrays.toString(lista_arquivos));
        } else if (command.equalsIgnoreCase("GET")) {
            transfereArquivo(sock);
        }else{
            System.out.println("Comando inválido");
        }
        
    }

    public static void transfereArquivo(Socket sock) {
        try {

            FileInputStream fileIn = null;
            OutputStream socketOut = null;
            ServerSocket servsock = null;
             BufferedReader doCliente = 
                new BufferedReader(new InputStreamReader(sock.getInputStream()));

            // Abrindo porta para conexao de clients
            System.out.println("Porta de conexao aberta 13267");

            // Cliente conectado
            System.out.println("Conexao recebida pelo cliente");

            // Criando tamanho de leitura
            byte[] cbuffer = new byte[1024];
            int bytesRead;

            // Criando arquivo que sera transferido pelo servidor
            String arquivo_nome = doCliente.readLine();
            try {
                File file = new File("src/ServidorArquivos/" + arquivo_nome + ".txt");
                fileIn = new FileInputStream(file);

                System.out.println("Lendo arquivo...");

            } catch (FileNotFoundException e) {

                //RETORNA AO USUARIO <<-------
                System.out.println("Arquivo não encontrado");
            } finally {
                // Criando canal de transferencia
                socketOut = sock.getOutputStream();

                // Lendo arquivo criado e enviado para o canal de transferencia
                System.out.println("Enviando Arquivo...");

                while ((bytesRead = fileIn.read(cbuffer)) > 0) {
                    socketOut.write(cbuffer, 0, bytesRead);
                    socketOut.flush();

                }

                System.out.println("Arquivo Enviado!");
            }

        } catch (IOException e) {
        }

    }
}
