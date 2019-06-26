package application.util;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class FilesListener implements Runnable {

    public final static Logger logger = Logger.getLogger(FilesListener.class.getClass().getName());
    private volatile boolean running = true;
    protected String path;
    private String prefix;
    private Thread worker;
    boolean isRunning = false;

    @FXML
    private ListView<String> list;
    
    public FilesListener(String path, String prefix, ListView<String> list) {
        this.path = path;
        this.prefix = prefix;
        this.list = list;
    }

    /**
     * Listen for file changes and copy/rename etc
     * @throws Exception
     */
    public void listenAndModify() throws Exception
    {
        logger.info(path);
        FolderWatcher.listen(path, prefix, running, list); 
    }

    @Override
    public void run() {
        logger.info("Run");

        while(!Thread.currentThread().isInterrupted()) {
            try {
                listenAndModify();
            } catch (Exception e) {
                running = false;
                logger.info("problem");
            }
        }
    }

    public void start() {
        worker = new Thread(this);
        worker.start();
    }
}