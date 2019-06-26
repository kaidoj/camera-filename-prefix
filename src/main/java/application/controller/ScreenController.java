package application.controller;

import java.io.IOException;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.Main;
import application.helper.ShowAlert;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Current class controls all the screens
 * 
 * @author KaidoJ
 *
 */
public class ScreenController {

	private final static Logger logger = Logger.getLogger(ScreenController.class.getName());

	private HashMap<String, Parent> screenMap = new HashMap<>();
	private Stage main;

	/**
	 * Setup screens
	 * 
	 * @param main
	 */
	public ScreenController(Stage main) {
		this.main = main;
		
		try {
			this.addScreen("application", FXMLLoader.load(Main.class.getResource("/view/Application.fxml")));
		} catch (IOException e) { 
			logger.log(Level.SEVERE, null, e);
		}

	}

	/**
	 * Add screen to the list of screens
	 * 
	 * @param name
	 * @param root
	 */
	protected void addScreen(String name, Parent root) {
		screenMap.put(name, root);
	}

	/**
	 * Remove screen from list
	 * 
	 * @param name
	 */
	protected void removeScreen(String name) {
		screenMap.remove(name);
	}

	/**
	 * Display screen
	 * 
	 * @param name
	 */
	public void activate(String name) {
		Scene scene = new Scene(screenMap.get(name));
		scene.getStylesheets().add(Main.class.getResource("/css/application.css").toExternalForm());
		
		if (name == "application") {
			scene = this.applicationScreenExtras(scene);
		}

		main.setScene(scene);
		main.show();
	}

	/**
	 * Close window
	 */
	public void close() {
		main.close();
	}

	/**
	 * Do some extras when application screen is chosen
	 * 
	 * @param scene
	 * @return
	 */
	private Scene applicationScreenExtras(Scene scene) {
		return scene;
	}

	/**
	 * Close window event
	 * @param event
	 */
	private void closeWindowEvent(WindowEvent event) {

		System.out.println("Window close request ...");

		if (!ShowAlert.confirm("Closing program", "Are you sure you would like to exit?")) {
			System.out.println("Window not closed ...");
			event.consume();
		} else {
			Platform.exit();
			System.exit(0);
		}

	}
	
	/**
	 * Load events
	 */
	public void loadEvents() {
		main.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
	}
}
