package me.nrubin29.jbot.broadcaster;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JBotPanel extends VBox {
	
	private Stage stage;

	public JBotPanel(Stage stage) {
		this.stage = stage;
		
		setMinSize(stage.getWidth() / 3, stage.getHeight() / 3);
		setMaxSize(stage.getWidth() / 3, stage.getHeight() / 3);
		setPrefSize(stage.getWidth() / 3, stage.getHeight() / 3);
		
		setAlignment(Pos.CENTER);
		
//		getScene().setFill(Color.GRAY);
	}
	
	public Stage getStage() {
		return stage;
	}
}