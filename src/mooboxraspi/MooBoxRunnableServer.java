package mooboxraspi;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by mpacini on 11/03/15.
 */
public class MooBoxRunnableServer implements Runnable {

    private Socket server;
    private String line, input;

    MooBoxRunnableServer(Socket server) {
        this.server = server;
    }

    public void run() {

        input = "";

        try {
            // Get input from the client
            DataInputStream in = new DataInputStream(server.getInputStream());
            PrintStream out = new PrintStream(server.getOutputStream());

            while ((line = in.readLine()) != null && !line.equals(".")) {
                input = input + line;
                out.println("I got:" + line);

                MooBoxRasPi.animServo1.run();
                MooBoxRasPi.animServo2.run();
            }

            // Now write to the client

            System.out.println("Overall message is:" + input);
            out.println("Overall message is:" + input);

            server.close();
        }
        catch (IOException ioe) {
            System.out.println("IOException on socket listen: " + ioe);
            ioe.printStackTrace();
        }
    }

}