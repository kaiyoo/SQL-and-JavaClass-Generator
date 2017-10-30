package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		AnchorPane root = FXMLLoader.load(
				getClass().getResource("/main/main.fxml")
				);
		Scene scene = new Scene(root);
		
		stage.setTitle("SGWannabe");
		stage.setScene(scene);
		stage.show();
	
	}
}
