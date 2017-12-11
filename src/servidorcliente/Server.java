package servidorcliente;

import java.net.*;
import java.io.*;
import java.util.Arrays;

public class Server {

    public static void main(String[] args) throws Exception {

        System.out.println("##########servidor##########");

        ServerSocket server = new ServerSocket(9898);

        System.out.println("Aguardando Conexao");

        Socket socket = server.accept();

        System.out.println("Conexao estabelecida com :" + socket.getInetAddress());

        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        PrintStream out = new PrintStream(output);

        while (true) {

            String comando = in.readLine();
            System.out.println("Comando do cliente:" + comando);

            if (comando.equalsIgnoreCase("index")) {

                String[] lista;
                lista = ListaArquivos.listaArquivos();
                if (lista.length==0) {
                    out.println("Diretorio vazio");
                } else {
                    out.println(Arrays.toString(lista));
                }
            } else if (comando.equalsIgnoreCase("get")) {
                String arquivo = in.readLine();
                System.out.println(arquivo);
                transfereArquivo(socket, arquivo);

            } else {
                out.println("Comando não existe");
            }

        }

    }

    public static void transfereArquivo(Socket sock, String arquivo) throws ClassNotFoundException, IOException {
        

            FileInputStream fileIn = null;
            OutputStream socketOut = null;

            // Criando tamanho de leitura
            byte[] cbuffer = new byte[1024];
            int bytesRead;

            // Criando arquivo que sera transferido pelo servidor
            try {
                File file = new File("src/ServidorArquivos/" + arquivo);
                fileIn = new FileInputStream(file);

            } catch (NullPointerException e) {

                //RETORNA AO USUARIO <<-------
                System.out.println("Arquivo não encontrado");
            } finally {
                // Criando canal de transferencia
                socketOut = sock.getOutputStream();

                // Lendo arquivo criado e enviado para o canal de transferencia
                while ((bytesRead = fileIn.read(cbuffer)) > 0) {
                    socketOut.write(cbuffer, 0, bytesRead);
                    socketOut.flush();

                }

            }

        
    }

}
