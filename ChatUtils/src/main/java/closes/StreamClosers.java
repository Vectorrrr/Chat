package closes;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

/**
 * This class util class for closing streams
 * Created by igladush on 11.03.16.
 */
public class StreamClosers {
    private static final String CLOSE_ERROR = "When I close stream I have Error";

    public static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                System.err.println(CLOSE_ERROR);
                e.printStackTrace();
            }
        }
    }
    public static void closeStream(Socket socket){
        if(socket!=null && !socket.isClosed()){
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(CLOSE_ERROR);
                e.printStackTrace();
            }
        }
    }
}
