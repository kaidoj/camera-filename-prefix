package application.util;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javafx.scene.control.ListView;

public class FolderWatcher {

    public static void listen(String path, String prefix, boolean running, ListView<String> list) throws Exception
    {
        FileSystem fs = FileSystems.getDefault();
        FilesManager fm = new FilesManager(path, prefix, list);
        WatchService ws = fs.newWatchService();
        Path pTemp = Paths.get(path);
        pTemp.register(ws, new WatchEvent.Kind[] {ENTRY_CREATE});
        WatchKey k;

        while(true)
        {
            k = ws.take();
            //cancel folder watcher
            if (!running) {
                k.cancel();
                break;
            }

            running = true;
            for (WatchEvent<?> e : k.pollEvents())
            {
                Object c = e.context();
                System.out.printf("%s %d %s\n", e.kind(), e.count(), c.toString());
                fm.manage(c.toString());
            }
            k.reset();
        }
    }
}