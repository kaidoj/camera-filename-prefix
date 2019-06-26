package application.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class FilesManager {

    public final static Logger logger = Logger.getLogger(FilesListener.class.getClass().getName());

    private String path;
    private String prefix;
    private String filename;
    private String prefixPath;
    private String message;

    @FXML
    private ListView<String> list; 

    public FilesManager(String path, String prefix, ListView<String> list){
        this.path = path;
        this.prefix = prefix;
        this.list = list;

        try {
            this.prefixPath = this.createPrefixDir();
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Starts all the file manage methods
     * 
     * @param filename
     */
    public void manage(String filename) {
        this.filename = filename;
        try {
            this.writeFile();
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Creates direcotry with prefix name
     * 
     * @return string
     * @throws Exception
     */
    public String createPrefixDir() throws Exception {

        File file = new File(this.path + File.separator + this.prefix);
        if (!file.exists()) {
            if (!file.mkdir()) {
                throw new Exception("Coult not create directory");
            }
        }

        return file.getAbsolutePath();
    }

    /**
     * Write copy of a file to prefix directory
     * 
     * @return boolean
     * @throws InterruptedException
     * @throws IOException
     */
    public boolean writeFile() throws InterruptedException, IOException {
        String prefixedFilename = this.prefix + "_" + this.filename;
        logger.info(this.prefixPath + File.separator + this.filename);
        File file1 = new File(this.path + File.separator + this.filename);

        //skip if directory
        if (file1.isDirectory()) {
            return false;
        }

        //source file must always exist
        if (!file1.exists()) {
            throw new IOException("Source file does not exist");
        }

        File file2 = new File(this.prefixPath + File.separator + prefixedFilename);
        logger.info(file2.getAbsolutePath());

        //give time for windows to unlock file
        Thread.sleep(10);

        try {
            Files.copy(file1.toPath(), file2.toPath());
            message = file1.getName() + " copied to " + file2.getAbsolutePath();
        } catch (Exception e) {
            message = file1.getName() + " COULD NOT BE copied to " + file2.getAbsolutePath();
        }

        Platform.runLater(()  -> this.list.getItems().add(0,message));

        return true;
    }

}