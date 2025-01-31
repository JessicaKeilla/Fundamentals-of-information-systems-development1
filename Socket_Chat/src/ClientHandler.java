import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable
{
    public static ArrayList<ClientHandler> clientHandlers=new ArrayList<>();

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;

    public ClientHandler (Socket socket)
    {
        try
        {
            this.socket = socket;
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.clientUsername=bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER: " +clientUsername + "Connected");

        } catch (Exception e) {
            closeEverything(socket,bufferedReader,bufferedWriter);
        }

    }


    @Override
    public void run()
    {
        String messageFromClient;
        while (socket.isConnected())
        {
            try
            {
                messageFromClient=bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket,bufferedReader,bufferedWriter);
                break;

            }
        }

    }
    public  void broadcastMessage(String messageToSend)
    {
        for(ClientHandler clientHandlers :clientHandlers)
        {
            try
            {
                if (!clientHandlers.clientUsername.equals(clientUsername))
                {
                    clientHandlers.bufferedWriter.write(messageToSend);
                    clientHandlers.bufferedWriter.newLine();
                    clientHandlers.bufferedWriter.flush();
                }
            } catch (Exception e) {
                closeEverything(socket,bufferedReader,bufferedWriter);

            }
        }
    }
    public void removeClientHandler()
    {

        clientHandlers.remove(this);
        broadcastMessage("SERVER: " +clientUsername + "Disconnected");
    }
    public void closeEverything(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter)
    {
        try
        {

            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
