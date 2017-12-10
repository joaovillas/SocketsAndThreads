import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Cliente {
	public static void main(String[] args) throws IOException {

		//Criando Classe cliente para receber arquivo
		Cliente cliente = new Cliente();

		Socket sockServer = new Socket("127.0.0.1",13267);
		
               
                
                String command = JOptionPane.showInputDialog("Digite o comando[cliente]") ;
                
                 DataOutputStream paraServidor = 
                new DataOutputStream(sockServer.getOutputStream());
                 
                 paraServidor.writeBytes(command);
                 System.out.println("Comando enviado ao servidor");
                
                
                if (command.equalsIgnoreCase("GET")){
                
                    cliente.getFileFromServeR(sockServer);
                    
                }else if(command.equalsIgnoreCase("INDEX")){
                    
                    System.out.println("INDICE");
                    
                }
                
                
                
        
        }
        
        private void getIndex(Socket sockServer){
             
            try{
            BufferedReader doServidor = 
                new BufferedReader(new InputStreamReader(sockServer.getInputStream()));
            
            String index = doServidor.readLine();
                System.out.println(index);
            
            
            }catch(Exception e){
                
            }  
              
        }
        
        
        
        
        
        
        
        
	private void getFileFromServeR(Socket sockServer) {
		
		FileOutputStream fos = null;
		InputStream is = null;
                
                Scanner sc = new Scanner(System.in);
                
                String arquivo = JOptionPane.showInputDialog("Nome do arquivo [cliente]");
                
                
                
                
		try {
			// Criando conexão com o servidor
			System.out.println("Conectando com Servidor porta 13267");
			
			is = sockServer.getInputStream();

			// Cria arquivo local no cliente
			                //
                        fos = new FileOutputStream(new File("src/Destino de Arquivos/"+arquivo+".txt"));
                        
			System.out.println("Arquivo Local Criado /src/Destino de Arquivos/"+arquivo+".txt");
			
			// Prepara variaveis para transferencia
			byte[] cbuffer = new byte[1024];
			int bytesRead;

			// Copia conteudo do canal
			System.out.println("Recebendo arquivo...");
			while ((bytesRead = is.read(cbuffer)) != -1) {
				fos.write(cbuffer, 0, bytesRead);
				fos.flush();
			}
			
			System.out.println("Arquivo recebido!");
                        
                        
		} catch (IOException e) {
		}

	}
}
