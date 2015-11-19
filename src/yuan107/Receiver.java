package yuan107;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Receiver extends Application {
	DatagramSocket listener;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("receiver.fxml"));
		primaryStage.setTitle("Hello World");
		primaryStage.setScene(new Scene(root, 640, 360));
		primaryStage.show();

		listener = new DatagramSocket(6969);

		Thread response = new Thread(() -> {
			while (listener != null) {
				byte[] buffer = new byte[1024];
				DatagramPacket data = new DatagramPacket(buffer, buffer.length);
				try {
					listener.receive(data);
					System.out.println("Response Received!!!" + data.getData());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		response.setDaemon(true);
		response.start();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
