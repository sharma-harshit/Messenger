
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Server implements Runnable
{
    ServerSocket ssock ;
    Socket sock ;
    PrintWriter pw ;
    BufferedReader brn ;
    BufferedReader brk;
    send sender;
    receive receiver;
    gui gui_obj;
    public Server(gui obj)
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
            ssock = new ServerSocket(9000);
            sock = ssock.accept();                    
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
                String s = "Server : " +gui_obj.message_server.getText();
                //sending it to client
                pw.println(s);
                pw.flush();
                //appending own  JTextArea
                gui_obj.chat_server.append("\n"+s);
                //clearing the area
                gui_obj.message_server.setText("");
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
                    //showing the received message
                    gui_obj.chat_server.append("\n"+s); 
                }
                catch (IOException ex) 
                {
                    JOptionPane.showMessageDialog(gui_obj,ex.getMessage());
                }
            }
        }  
    }
}
