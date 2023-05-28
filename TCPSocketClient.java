import java.io.*;
import java.net.Socket;
import java.util.stream.Collectors;

public class TCPSocketClient {
    public static void main(String[] args) {
        // Get the server hostname from the command line
        if (args.length != 2 && args.length != 3) {
            System.out.println("Usage: java TCPSocketClient <host> <port> [<message>]");
            System.exit(0);
        }

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        // Connect to the server using try-with-resources to auto close the connection.
        try (Socket socket = new Socket(hostname, port)) {

            // Send message to server
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            if (args.length == 3) {
                String message = args[2];
                String sendMessage = message + "\n";
                bw.write(sendMessage);
                bw.flush();
                System.out.println("Message sent to the server: " + sendMessage);
            }

            // Get the return message from the server
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            // Making sure to read all lines of the BufferedReader and not just the first one.
            String lines = br.lines().map(line -> line + "\n").collect(Collectors.joining());

            System.out.println("Message received from the server: " + lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
