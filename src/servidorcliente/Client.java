package servidorcliente;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception {

        System.out.println("##########Cliente##########");

        System.out.println("Iniciando conexao com o servidor");
try{
        Socket socket = new Socket("localhost", 9898);

        System.out.println("Conexao Establish");

        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        PrintStream out = new PrintStream(output);

        Scanner sc = new Scanner(System.in);
        
        boolean conti = true;
        
        while (conti == true) {

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
                out.println(arquivo);
                
                String existente = in.readLine();
                
                if(existente.equalsIgnoreCase("[OK]")){
                System.out.println(existente);
                getFileFromServeR(socket, arquivo);
                
                socket.close();
                conti=false;
                break;
              
                }else{
                    
                    System.out.println(existente); 
                    System.out.println("Conexão com o servidor encerrada");
                    socket.close();
                    conti=false;
                    
                }
            }else{
           
            out.println(mensagem);
            String received = in.readLine();
            System.out.println("Mensagem do servidor:" + received);

            }
        }
}catch(ConnectException e){
    System.out.println("Conexão Recusada ! Servidor Indisponível");
}
    }

    private static void getFileFromServeR(Socket sockServer, String arquivo) throws Exception {
        
        ObjectInputStream in = new ObjectInputStream(sockServer.getInputStream());
        FileOutputStream file = new FileOutputStream("src/DestinoArquivos/"+arquivo);
        
        byte [] buf = new byte [4096];
        while(true){
            int len = in.read(buf);
            //Thread.sleep(1000);
            
            if (len==-1)break;
            file.write(buf,0,len);
            file.flush();
            
        }
        System.out.println("Transferencia concluida");
        
    }
}
        
    
    
            
           

