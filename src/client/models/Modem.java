package client.models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public interface Modem {
    /**
     * The method to broadcast a message to the sockets
     * @param socket - the socket of the receiver
     * @param message - The message to broadcast
     * @param alias - The alias of the user
     * @throws Exception
     */
    default public boolean broadcast(Socket socket, String message, String alias) {
        if (!socket.isClosed()) try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String fullMessage = "[" + alias + "]: " + message;
            byte[] bytes = fullMessage.getBytes(StandardCharsets.UTF_8);
            out.writeInt(bytes.length); // Write the length of the message
            out.write(bytes);
            out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * The method to broadcast a message to the sockets
     * @param socket - the socket of the receiver
     * @param message - The message to broadcast
     * @throws Exception
     */
    default public boolean broadcast(Socket socket, String message) {
        if (!socket.isClosed()) try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
            out.writeInt(bytes.length); // Write the length of the message
            out.write(bytes);
            out.flush();
        }
        catch (SocketException se) {
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * The method to broadcast a message to the sockets
     * @param socket - the socket of the receiver
     * @param number - The number in int to broadcast
     * @throws Exception
     */
    default public boolean broadcast(Socket socket, int number) {
        if (!socket.isClosed()) try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeInt(number); // Write the length of the message
            out.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Receive the Element requested by the Client
     * @param socket - the socket of the requester
     * @return the Element name
     * @throws Exception
     */
    default public String receive(Socket socket) {
        String message = "";
        if (!socket.isClosed()) try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            int length = in.readInt(); // Read the length of the incoming message
            byte[] bytes = new byte[length];
            in.readFully(bytes); // Read the bytes into the byte array
            message = new String(bytes, StandardCharsets.UTF_8);
        }
        catch (EOFException eof) {
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return message;
    }

    /**
     * Receive the Element requested by the Client
     * @param socket - the socket of the requester
     * @return the Element name
     * @throws Exception
     */
    default public int receiveInt(Socket socket) {
        int number = -1;
        if (!socket.isClosed()) try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            number = in.readInt();
        }
        catch (Exception e) {
            if (!(e instanceof EOFException) && !(e instanceof SocketException))
                e.printStackTrace();
        }
        return number;
    }
}
