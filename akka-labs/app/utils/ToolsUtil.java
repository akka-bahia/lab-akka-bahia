package utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by w6c on 28/07/2014.
 */
public class ToolsUtil {

    //Objecto possibilita ler infos do arquivo application.conf
    final static Config config = ConfigFactory.load();

    final static String accessKey = config.getString("accessKey");
    final static String secretKey = config.getString("secretKey");

    //Cliente autenticado do Serviço Amazon AWS
    public static AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

    //#############################   EhCache built-in Play    ############################################

    /**
     *
     * @param contentType
     * @return Extensao do arquivo
     */
    public static String capturaExtensaoDoMimeType(String contentType) {
        //ex contentType = image/jpeg
        String delimiter = "/";
        String extensao = contentType.split(delimiter)[1].toString();

        return extensao.trim().toLowerCase();
    }

    /**
     *
      * @param nomeArquivo
     * @return Extensao do arquivo
     */
    public static String capturaExtensaoNomeArquivo(String nomeArquivo) {
        String delimiter = "\\.";

        String extensao;
        String[] arrayString;
        arrayString = nomeArquivo.split(delimiter);
        int tamanhoArray = arrayString.length; //nomeImagem.split(".").length;
        extensao = arrayString[tamanhoArray - 1].toString(); //nomeImagem.split(".")[tamanhoSplit].trim().toString();

        return extensao.toLowerCase();
    }

    /**
     *
     * @param filePath - Path completo até o arquivo
     * @return Mime Type do arquivo
     */
    public static String detectMimeTypeOfFile(String filePath){

        String fileMimeType =  null;
        try {
            Path pathAux = FileSystems.getDefault().getPath(filePath);

            fileMimeType = Files.probeContentType(pathAux);

            if (fileMimeType == null) {
                System.err.format("'%s' has an" + " unknown filetype.%n", pathAux);
            }

        } catch (IOException x) {
            System.err.println(x);
        }

        return fileMimeType;
    }
}
