import java.net.Socket

public class Server {

    Socket socket;
    //String host
    //int port

    public Server() {
    }

    public void StartServer() {
        try {
            socket = new Socket("localhost", 8080);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void Write(String msg){

        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.write(msg);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}