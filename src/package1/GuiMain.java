package package1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GuiMain extends Application {
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("/package1/Input.fxml"));
		Scene scene = new Scene(root,400,500);
		SceneController controller = SceneController.getInstance();
		controller.setScene(scene);
		primaryStage.setScene(scene);
		
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to visible bounds of the main screen
        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());
		
        primaryStage.setTitle("Signal Flow Graph");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
