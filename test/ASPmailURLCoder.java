import com.espmail.utils.Base64Coder;

/**
 * aspmail
 * User: Luis Cubillo
 */
public class ASPmailURLCoder {



   public static void main(String[] args){

      if(args.length < 2 ){
         System.err.println("Uso:\n   java aspmailURLCoder tipo cadena\n   tipo:\n      1 - Codificar.\n      2 - Decodificar");
         System.exit(-1);
      }
      int tipo = Integer.parseInt(args[0]);
      if(tipo == 1 ){
         System.out.println( args[1] + " = " + Base64Coder.encodeString(args[1]));
      }else if(tipo == 2){
         System.out.println( args[1] + " = " + new String(Base64Coder.decode(args[1])));
      }
   }
}
