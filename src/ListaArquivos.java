import java.io.File;


public class ListaArquivos
{
    public static String[] listaArquivos()
    {
        String nomeDiretorio;
	File diretorio;
	String[] arquivos;
	
	diretorio = new File("src/ServidorArquivos");

	
            arquivos = diretorio.list();
            System.out.println("Arquivos no diretorio \""+ diretorio + "\":");
            for (int i = 0; i< arquivos.length; i++)
                System.out.println(" "+arquivos[i]);
	return arquivos;
        }
       
    }
    

	