package application.helper;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class ShowAlert {

	public static void error(String message) {
		Alert errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setHeaderText("Viga");
		errorAlert.setContentText(message);
		errorAlert.showAndWait();
	}

	public static void success(String message) {
		Alert errorAlert = new Alert(AlertType.INFORMATION);
		errorAlert.setHeaderText("Edukas");
		errorAlert.setContentText(message);
		errorAlert.showAndWait();
	}

	public static Boolean confirm(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.getButtonTypes().remove(ButtonType.OK);
		alert.getButtonTypes().add(ButtonType.CANCEL);
		alert.getButtonTypes().add(ButtonType.YES);
		alert.setHeaderText(title);
		alert.setContentText(String.format(message));
		Optional<ButtonType> res = alert.showAndWait();

		if (res.isPresent()) {
			if (res.get().equals(ButtonType.CANCEL)) {
				return false;
			}
		}
		
		return true;
	}

	public static void somethingWrongError() {
		error("Midagi läks valesti. Palun proovi uuesti.");
	}
}
