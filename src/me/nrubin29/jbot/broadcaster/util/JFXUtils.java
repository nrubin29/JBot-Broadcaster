package me.nrubin29.jbot.broadcaster.util;

import java.util.concurrent.CountDownLatch;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import me.nrubin29.jbot.broadcaster.JBotPanel;

public final class JFXUtils {

    public static Region region(double width, double height) {
        Region region = new Region();
        
        region.setMinSize(width, height);
        region.setPrefSize(width, height);
        region.setMaxSize(width, height);
        
        return region;
    }

    public static Region spacer(boolean h) {
        Region region = new Region();

        if (h) {
            HBox.setHgrow(region, Priority.ALWAYS);
        }
        
        else {
            VBox.setVgrow(region, Priority.ALWAYS);
        }

        return region;
    }

    public static RowConstraints rowConstraints(int percent) {
        RowConstraints constraints = new RowConstraints();
        
        constraints.setPercentHeight(percent);
        
        return constraints;
    }

    public static ColumnConstraints columnConstraints(int percent) {
        ColumnConstraints constraints = new ColumnConstraints();
        
        constraints.setPercentWidth(percent);
        
        return constraints;
    }
    
    public static GridPane align(JBotPanel content, Stage stage) {
    	GridPane main = new GridPane();
    	
    	main.add(region(stage.getWidth() / 3, stage.getHeight() / 3), 0, 0);
    	main.add(region(stage.getWidth() / 3, stage.getHeight() / 3), 1, 0);
    	main.add(region(stage.getWidth() / 3, stage.getHeight() / 3), 2, 0);
    	
    	main.add(region(stage.getWidth() / 3, stage.getHeight() / 3), 0, 1);
    	main.add(content, 1, 1);
    	main.add(region(stage.getWidth() / 3, stage.getHeight() / 3), 2, 1);
    	
    	main.add(region(stage.getWidth() / 3, stage.getHeight() / 3), 0, 2);
    	main.add(region(stage.getWidth() / 3, stage.getHeight() / 3), 1, 2);
    	
    	ImageView imageView = new ImageView(new Image("img/jbot.png", 200, 200, true, true));
    	main.add(imageView, 2, 2);
		
		return main;
    }
    
    public static void stage(JBotPanel content, Stage stage) {
    	stage.setScene(new Scene(JFXUtils.align(content, stage)));
        stage.getScene().setFill(Color.WHITESMOKE);
    }
    
    public static Text title(String text) {
    	Text title = new Text(text);
    	title.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 36));
    	title.setTextAlignment(TextAlignment.CENTER);
    	return title;
    }
    
    public static Text subtitle(String text) {
    	Text title = new Text(text);
    	title.setFont(Font.font("Verdana", FontWeight.LIGHT, FontPosture.ITALIC, 12));
    	title.setTextAlignment(TextAlignment.CENTER);
    	return title;
    }

    /**
     * Runs the specified {@link Runnable} on the
     * JavaFX application thread and waits for completion.
     *
     * @param action the {@link Runnable} to run
     * @throws NullPointerException if {@code action} is {@code null}
     */
    public static void runAndWait(final Runnable action) {
        if (action == null)
            throw new NullPointerException("action");

        // run synchronously on JavaFX thread
        if (Platform.isFxApplicationThread()) {
            action.run();
            return;
        }

        // queue on JavaFX thread and wait for completion
        final CountDownLatch doneLatch = new CountDownLatch(1);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    action.run();
                } finally {
                    doneLatch.countDown();
                }
            }
        });

        try {
            doneLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}