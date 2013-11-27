import java.io.File;
import java.util.Date;

/**
 * espmail
 * User: Luis Cubillo
 */
public class ModifiedInTheFuture {

   public static void main(String [] argv){
      File ficherito = new File("201308456");

      if(ficherito.exists() && ficherito.isDirectory()){
         System.out.println(new Date(ficherito.lastModified()));
         long manyana = 24*3600*1000;
         ficherito.setLastModified(System.currentTimeMillis()+ manyana);
      }
      System.out.println(new Date(ficherito.lastModified()));
   }
}
