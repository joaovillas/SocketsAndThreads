package servidorcliente;

import java.net.*;
import java.io.*;
import java.util.Arrays;

public class Server {

    public static void main(String[] args) throws Exception {

        System.out.println("##########servidor##########");

        ServerSocket server = new ServerSocket(9898);

        System.out.println("Aguardando Conexao");

       

        while (true) {

        Socket socket = server.accept();

        System.out.println("Conexao estabelecida com : [" + socket.getInetAddress().getHostAddress()+":"+socket.getPort()+"]");

        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        PrintStream out = new PrintStream(output);
            
            
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
               
                String []lista ;
                lista = ListaArquivos.listaArquivos();
                
                
                for(int i=0;i<lista.length;i++){
                    
                    if(arquivo.equals(lista[i])){
                        out.println("[OK]");
                        Thread.sleep(2000);
                        transfereArquivo(socket, arquivo);
                        socket.close();
                        break;
                        
                        
                    }else if(i==lista.length-1){
                        out.println("[ ERROR ] ARQUIVO NÃO EXISTE NO DIRETÓRIO.");
                        try{
                        socket.close();
                        break;
                        
                        }catch(SocketException e){
                            socket.close();
                            break;
                        }
                    }
                }
                
                

            } else {
                out.println("Comando não existe");
            }

        }
       
               
    
    }

    public static void transfereArquivo(Socket sock, String arquivo) throws ClassNotFoundException, IOException, InterruptedException {
        
        ObjectOutputStream outa = new ObjectOutputStream(sock.getOutputStream());
        
        FileInputStream file = new FileInputStream("src/ServidorArquivos/"+arquivo);
        
        byte [] buf = new byte [4096];
        int acum =0;
       
       OutputStream output = sock.getOutputStream();
       PrintStream out = new PrintStream(output);
        
        while(true){
            int len = file.read(buf);
            try{
            if ((len*2)==-1)break;
            outa.flush();
            outa.write(buf,0,len);
            }catch(Exception e){
                break;
            }
            }
        System.out.println("Transferencia Concluida");
        try{
        sock.close();
        }catch(SocketException e){
            
        }
    }
}

