package me.nrubin29.jbot.broadcaster;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javafx.application.Platform;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import me.nrubin29.jbot.broadcaster.util.JFXUtils;

public class PSearching extends JBotPanel {

	public PSearching(final Stage stage) {
		super(stage);
		
		Text subtitle = JFXUtils.subtitle("Searching for a robot. Please make sure JBot-Receiever is running without errors.");
		subtitle.setWrappingWidth(225);
		
		getChildren().addAll(JFXUtils.title("Searching"), JFXUtils.region(0, 10), subtitle, JFXUtils.region(0, 10), new ProgressIndicator());
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Process process = Runtime.getRuntime().exec("arp -i en0 -a -n");
					process.waitFor();
					BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
					
					while (reader.ready()) {
						String ip = reader.readLine();
						ip = ip.substring(3, ip.indexOf(')'));
						System.out.println(ip);
						
						try {
							Socket socket = new Socket();
							socket.connect(new InetSocketAddress(ip, 1234), 1000);
							Remote.getInstance().setSocket(socket);
							
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									JFXUtils.stage(new PConnected(stage, true), stage);
								}
							});
							
							break;
						}
						
						catch (ConnectException | SocketTimeoutException ignored) {
							
						}
					}
					
					if (Remote.getInstance().getSocket() == null) {
						System.err.println("Socket!");
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								JFXUtils.stage(new PConnected(stage, false), stage);
							}
						});
					}
				}
				
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}