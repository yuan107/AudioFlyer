package yuan107;/**
 * Created by Michael on 11/19/2015.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.*;

public class Sender extends Application {
	DatagramSocket broadcastSocket;

	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("sender.fxml"));
		primaryStage.setTitle("Hello World");
		primaryStage.setScene(new Scene(root, 640, 360));
		primaryStage.show();

		findReciever();

		Thread response = new Thread(() -> {
			if(broadcastSocket != null){
				byte[] buffer = new byte[1024];
				DatagramPacket data = new DatagramPacket(buffer, buffer.length);
				try {
					broadcastSocket.receive(data);
					System.out.println("Response Received!!!");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		response.setDaemon(true);
		response.start();
	}

	public void findReciever(){
		//Send out a broadcast udp asking for the AudioFlyer Receiver
		try {
			broadcastSocket = new DatagramSocket(6969);
			//Find broadcast channel
			InetAddress broadcast = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getInterfaceAddresses().get(0).getBroadcast();
			broadcastSocket.setBroadcast(true);
			broadcastSocket.connect(broadcast, 6969);
			//Ask for AudioFlyer Servers
			byte[] buffer = String.format("AudioFlyer Server Call @%s:%d", InetAddress.getLocalHost().getHostAddress(), 6666).getBytes();
			broadcastSocket.send(new DatagramPacket(buffer, buffer.length));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		launch(args);
	}
}
