package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.controller.ScreenController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	public final static Logger logger = Logger.getLogger(Main.class.getClass().getName());

	@Override
	public void start(Stage primaryStage) throws IOException {
		Platform.setImplicitExit(false);
		
		try {
		    
		    //window data
		    primaryStage.setTitle("Nordic Digital AS camera images rename");
			primaryStage.initStyle(StageStyle.DECORATED);
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/window_icon.png")));
		    
		    //setup screens
		    ScreenController sc = new ScreenController(primaryStage);
		    sc.loadEvents();
		    sc.activate("application");

		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
