package me.nrubin29.jbot.broadcaster;

import javafx.application.Application;
import javafx.stage.Stage;
import me.nrubin29.jbot.broadcaster.util.JFXUtils;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.setWidth(640);
        stage.setMinWidth(640);
        stage.setHeight(480);
        stage.setMinHeight(480);
        stage.setTitle("JBot-Broadcaster");
        JFXUtils.stage(new PSearching(stage), stage);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}