package application.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.helper.ShowAlert;
import application.util.FilesListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class ApplicationController implements Initializable {

	public final static Logger logger = Logger.getLogger(ApplicationController.class.getClass().getName());

	private final DirectoryChooser directoryChooser = new DirectoryChooser();

	boolean isListening = false;

	private Thread filesListener;

	@FXML
	private TextField app_prefix_text;

	@FXML
	private MenuItem app_close;

	@FXML
	private VBox app_window;

	@FXML
	private Button app_choose_folder, app_start_btn;
	
	@FXML
	private Label app_chosen_folder;

	@FXML
	private ListView<String> app_list;

	@FXML
	public void close(ActionEvent event) {
		Window window = app_window.getScene().getWindow();
		window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	public void chooseFolder(MouseEvent event) {
		File folder = directoryChooser.showDialog(app_window.getScene().getWindow());
		if (folder != null) {
			app_chosen_folder.setText(folder.getAbsolutePath());
		}
	}
	
	@FXML
	public void start(MouseEvent event) {

		if (app_prefix_text.getText().isEmpty()) {
			ShowAlert.error("Prefix not chosen");
		} else {
			this.runListening();
		}

	}

	/**
	 * Run main listening threads
	 */
	public void runListening()
	{
		if (!isListening) {

			FilesListener filesListenerTask = new FilesListener(
				app_chosen_folder.getText(), 
				app_prefix_text.getText(),
				app_list
			);
			filesListener = new Thread(filesListenerTask);
			isListening = true;

			if (this.ifFolderChosen()) {
				app_start_btn.setText("Listening... Stop");
				try {
					filesListener.start();
					app_prefix_text.setDisable(true);
					app_list.getItems().add(0, "Started listening with prefix " + app_prefix_text.getText());
				} catch (Exception e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
			} else {
				ShowAlert.error("Please choose folder to listen!");
			}
		} else {
			isListening = false;
			app_start_btn.setText("Start listening");
			app_prefix_text.setDisable(false);
			filesListener.interrupt();
			app_list.getItems().add(0, "Stopped listening  with prefix " + app_prefix_text.getText());
		}
	}

	/**
	 * Check if foler was chosen
	 * @return boolean
	 */
	public boolean ifFolderChosen() {
		if (app_chosen_folder.getText().isEmpty()) {
			return false;
		}
		
		return true;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
