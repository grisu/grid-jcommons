package au.org.arcs.jcommons.dependencies;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class DependencyManager {
	
    private static HttpClient httpClient = new HttpClient();
	
    public static void checkForDependency(String className, String urlToDownload, File targetFile) throws IOException {
    	
    	try {
			Class classObject = Class.forName(className);
		} catch (ClassNotFoundException e) {

			// means we need to download the jar file
			downloadJar(urlToDownload, targetFile);
			
			ClasspathHacker.addFile(targetFile);
			
		}
    	
    }
	
	public static void downloadJar(String url, File targetFile) throws IOException {
		
		System.out.println("Downloading dependency jar: "+url);
        //create a method instance
        GetMethod getMethod = new GetMethod(url);

        try{

          //execute the method
          int statusCode =
                 httpClient.executeMethod(getMethod);

          //TODO: check statusCode = 200
          //get the resonse as an InputStream
          InputStream in =
                 getMethod.getResponseBodyAsStream();

          byte[] b = new byte[1024];
          int len;

          OutputStream out = new FileOutputStream(targetFile);
          while ((len = in.read(b)) != -1) {
                   //write byte to file
                   out.write(b, 0, len);
          }


          in.close();

         }finally{
           //release the connection
           getMethod.releaseConnection();
         }


		
	}
	

}
