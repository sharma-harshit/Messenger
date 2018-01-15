
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Client implements Runnable
{
    Socket sock ;
    PrintWriter pw ;
    BufferedReader brn ;
    BufferedReader brk;
    gui gui_obj;
    send sender;
    receive receiver;
    public Client(gui obj)
    {
       this.gui_obj=obj;
    }
    @Override
    public void run()
    {
        sender = new send();
        receiver = new receive();
       try 
        {
            sock = new Socket("192.168.43.106",9000);                    
            pw = new PrintWriter(sock.getOutputStream());  
            brn = new BufferedReader( new InputStreamReader( sock.getInputStream() ) );  
            brk = new BufferedReader(new InputStreamReader(System.in));
        } 
        catch (IOException ex) 
        {
            JOptionPane.showMessageDialog(gui_obj,ex.getMessage());
        }
//        send sender = new send();
//        new Thread(sender).start();
//        receive receiver = new receive();
        new Thread(receiver).start();
    }
    class send implements Runnable
    {
        @Override
        public void run() 
        {
            String s = "Client : " +  gui_obj.message_client.getText();
            //sending to server
            pw.println(s);
            pw.flush();
            //appending to own text area
            gui_obj.chat_client.append("\n"+s);
            //clearing the area
            gui_obj.message_client.setText("");
        }        
    }  
    
    class receive implements Runnable
    {
        @Override
        public void run() 
        {
            String s=" ";
            while(s!="exit")
            {
                try 
                {
                    s = brn.readLine();
                    //appending to text area
                    gui_obj.chat_client.append("\n"+s);
                }
                catch (IOException ex) 
                {
                    JOptionPane.showMessageDialog(gui_obj,ex.getMessage());
                }
            }
        }  
    }
}
