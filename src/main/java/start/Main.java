package start;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("views/MainWindow22.fxml"));
        Parent fxmlMain = fxmlLoader.load();
        MainController mainMainController = fxmlLoader.getController();
        mainMainController.setMainStage(primaryStage);

        primaryStage.setTitle("English Dictionary");

        primaryStage.setMinWidth(413);
        primaryStage.setMinHeight(370);
        primaryStage.setMaxWidth(800);
        primaryStage.setMaxHeight(400);


        primaryStage.setScene(new Scene(fxmlMain));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
