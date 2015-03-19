package me.nrubin29.jbot.broadcaster;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import me.nrubin29.jbot.broadcaster.util.JFXUtils;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class PInput extends JBotPanel {

	public PInput(final Stage stage) {
		super(stage);
		
		Text subtitle = JFXUtils.subtitle("Please select the input device you would like to use.");
		subtitle.setWrappingWidth(225);
		
		final ListView<Controller> list = new ListView<>();
		list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//		list.setMinHeight(75);
//		list.setMaxHeight(75);
//		list.setPrefHeight(75);
		
		Button next = new Button("Next");
		next.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				for (Controller controller : list.getSelectionModel().getSelectedItems()) {
					Remote.getInstance().addController(controller);
				}
				
				JFXUtils.stage(new PUpload(stage), stage);
			}
		});
		
		getChildren().addAll(
				JFXUtils.title("Input"),
				JFXUtils.region(0, 10),
				subtitle,
				JFXUtils.region(0, 10),
				list,
				JFXUtils.region(0, 10),
				next
		);
		
		list.setItems(FXCollections.observableArrayList(ControllerEnvironment.getDefaultEnvironment().getControllers()));
	}
}