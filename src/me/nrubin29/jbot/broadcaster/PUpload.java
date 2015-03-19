package me.nrubin29.jbot.broadcaster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import me.nrubin29.jbot.broadcaster.util.JFXUtils;

public class PUpload extends JBotPanel {

	public PUpload(final Stage stage) {
		super(stage);
		
		Text subtitle = JFXUtils.subtitle("Select the code file you would like to run.");
		subtitle.setWrappingWidth(225);
		
		final File[] file = new File[1];
		final Text fileText = new Text("Choose file.");
		
		HBox buttons = new HBox();
		buttons.setAlignment(Pos.CENTER);
		
		Button choose = new Button("Choose");
		choose.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				FileChooser chooser = new FileChooser();
				chooser.setTitle("Choose code");
				chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Java Code", "*.java"));
				
				File f = chooser.showOpenDialog(stage);
				if (f != null) {
					file[0] = f;
					fileText.setText(file[0].getName());
				}
			}
		});
		
		Button go = new Button("Go");
		go.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (file[0] != null) {
					JFXUtils.stage(new PUploading(stage), stage);
					
					try (BufferedReader reader = new BufferedReader(new FileReader(file[0]))) {
						StringBuilder code = new StringBuilder();
						
						while (reader.ready()) {
							code.append(reader.readLine());
						}
						
						Remote.getInstance().write(file[0].getName().substring(0, file[0].getName().indexOf(".")));
						Remote.getInstance().write(code.toString());
					}
					
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		
		buttons.getChildren().addAll(choose, JFXUtils.region(10, 0), go);
		
		getChildren().addAll(
				JFXUtils.title("Upload"),
				JFXUtils.region(0, 10),
				subtitle,
				JFXUtils.region(0, 10),
				fileText,
				JFXUtils.region(0, 10),
				buttons
		);
		
		Remote.getInstance().connect();
	}
}