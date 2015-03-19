package me.nrubin29.jbot.broadcaster;

import java.util.HashMap;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import me.nrubin29.jbot.broadcaster.util.JFXUtils;
import net.java.games.input.Component;

public class PRunning extends JBotPanel {
	
	private HashMap<Component, Text> values;

	public PRunning(final Stage stage) {
		super(stage);
		
		this.values = new HashMap<>();
		
		Text subtitle = JFXUtils.subtitle("The values from the joystick are listed below.");
		subtitle.setWrappingWidth(225);
		
		getChildren().addAll(
				JFXUtils.title("Running"),
				JFXUtils.region(0, 10),
				subtitle,
				JFXUtils.region(0, 10)
		);
		
		Remote.getInstance().startWriting(this);
	}
	
	public void update(Component component) {
		if (!values.containsKey(component)) {
			values.put(component, new Text());
			getChildren().add(values.get(component));
		}
		
		values.get(component).setText(component.getName() + ": " + component.getPollData());
	}
}