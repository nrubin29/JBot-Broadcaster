package me.nrubin29.jbot.broadcaster;

import javafx.event.EventHandler;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import me.nrubin29.jbot.broadcaster.util.JFXUtils;

public class PUploading extends JBotPanel {

	public PUploading(final Stage stage) {
		super(stage);
		
		Text subtitle = JFXUtils.subtitle("Uploading, compiling, and running your code. Please wait.");
		subtitle.setWrappingWidth(225);
		
		ProgressIndicator loading = new ProgressIndicator();
		loading.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				JFXUtils.stage(new PRunning(stage), stage);
			}
		});
		
		getChildren().addAll(JFXUtils.title("Uploading"), JFXUtils.region(0, 10), subtitle, JFXUtils.region(0, 10), loading);
		
		Remote.getInstance().startReading(stage);
	}
}