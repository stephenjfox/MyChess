package chess.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Stephen on 5/9/2014.
 */
public class ErrorLogger {

    static File errorLog = new File("chess\\logs\\errors " + new SimpleDateFormat("MMddYYYY").format(new Date()) + ".txt");
    public static void logError(String error) {

        if(!errorLog.exists()) try {
            errorLog.createNewFile();
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage()+"errorLog file couldn't be instantiated");
        }
        try(FileOutputStream fos = new FileOutputStream(errorLog, true)) {
            // Make an append-able FileOutputStream

            // Write to the File
            fos.write((String.format("%s\n%s\n\n", new Date().toString(), error)).getBytes());
            fos.flush();
        } catch (FileNotFoundException e) {
            System.err.println(e.getLocalizedMessage()+"\nThe ErrorLogger threw an FN Exception");
        } catch (IOException e) {
            System.err.println("The FOS.write() threw an IOException");
        }
    }
}
