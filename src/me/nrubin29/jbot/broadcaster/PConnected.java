package me.nrubin29.jbot.broadcaster;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import me.nrubin29.jbot.broadcaster.util.JFXUtils;

public class PConnected extends JBotPanel {

	public PConnected(final Stage stage, boolean success) {
		super(stage);
		
		if (success) {
			Button button = new Button("Start");
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					JFXUtils.stage(new PInput(stage), stage);
				}
			});
			
			getChildren().addAll(JFXUtils.title("Connected"), JFXUtils.region(0, 10), button);
		}
		
		else {
			Text subtitle = JFXUtils.subtitle("Could not find the robot. Please make sure JBot-Receiver is running without errors and try again.");
			subtitle.setWrappingWidth(225);
			
			Button button = new Button("Retry");
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					JFXUtils.stage(new PSearching(stage), stage);
				}
			});
			
			getChildren().addAll(JFXUtils.title("Error"), JFXUtils.region(0, 10), subtitle, JFXUtils.region(0, 10), button);
		}
	}
}