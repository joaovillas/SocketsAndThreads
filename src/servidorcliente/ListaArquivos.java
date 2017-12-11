package servidorcliente;
import java.io.File;


public class ListaArquivos
{
    public static String[] listaArquivos()
    {
	File diretorio;
	String[] arquivos;
	
	diretorio = new File("src/ServidorArquivos");

	
            arquivos = diretorio.list();
          //  System.out.println("Arquivos no diretorio \""+ diretorio + "\":");
            
            return arquivos;
            
        }
      
   }


	