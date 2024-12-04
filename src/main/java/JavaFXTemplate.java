import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class JavaFXTemplate extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try{
			primaryStage.setTitle("Three-Card Poker");

			Parent root = FXMLLoader.load(getClass().getResource("/FXML/menu.fxml"));
			Scene s1 = new Scene(root, 600, 400);
			s1.getStylesheets().add("styles/menu.css");
			primaryStage.setScene(s1);
			primaryStage.show();
		}
		catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}

	}
}

