package me.nrubin29.jbot.broadcaster;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.stage.Stage;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;

public class Remote {

	private static Remote instance = new Remote();
	
	public static Remote getInstance() {
		return instance;
	}
	
	private ArrayList<Controller> controllers;
	
	private Socket socket;
	private BufferedReader reader;
	private BufferedWriter writer;
	
	private Remote() {
		this.controllers = new ArrayList<>();
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public void addController(Controller controller) {
		controllers.add(controller);
	}
	
	public void write(String str) {
		try {
			writer.write(str);
			writer.newLine();
			writer.flush();
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void connect() {
		try {
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void startReading(final Stage stage) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						String line = reader.readLine();
						
						if (line == null) {
							System.exit(0);
						}
						
						if (line.equals("ready")) {
//							Platform.runLater(new Runnable() {
//								@Override
//								public void run() {
//									JFXUtils.stage(new PRunning(stage), stage);
//								}
//							});
						}
					}
					
					catch (SocketException e) {
						System.exit(0);
					}
					
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	public void startWriting(final PRunning pRunning) {
		new Thread(new Runnable() {
			public void run() {
				try {
					HashMap<Controller, String> lastData = new HashMap<>();
					for (Controller controller : controllers) {
						lastData.put(controller, "");
					}
					
					while (true) {
						int i = 0;
						
						for (final Controller controller : controllers) {
							if (!controller.poll()) {
								throw new IllegalStateException("Could not poll controller!");
							}
							
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									for (Component component : controller.getComponents()) {
										pRunning.update(component);
									}
								}
							});
							
							float x = controller.getComponent(Identifier.Axis.X).getPollData();
							float y = controller.getComponent(Identifier.Axis.Y).getPollData();
							String output = i + " " + x + " " + y;
							
							if (!output.equals(lastData.get(controller))) {
								System.out.println(output);
								write(output);
								lastData.put(controller, output);
							}
							
							i++;
						}
						
						i = 0;
						Thread.sleep(25);
					}
				}
				
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}