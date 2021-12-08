package picotoorganizer;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.ImageIcon;
/**
 *
 * @author Pablo
 */
public class PicotoOrganizer {

    /**
     * @param args the command line arguments
     */

   public static void main(String[] args){
       //Organizer es la BD con la que va a operar el programa
       try{
          VentanaPrincipal estancia = new VentanaPrincipal();
          //estancia.setIconImage(new ImageIcon(getClass().getResource("/picotoorganizer/icons/agent.png")).getImage());
          estancia.setVisible(true);
       }
      catch(Exception e){
         e.printStackTrace();
      }
   }
}
    
    

