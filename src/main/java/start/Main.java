package start;

import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("views/MainWindow.fxml"));
        Parent fxmlMain = fxmlLoader.load();
        Controller mainController = fxmlLoader.getController();
        mainController.setMainStage(primaryStage);

        primaryStage.setTitle("English Dictionary");
        primaryStage.setMinHeight(320);
        primaryStage.setMinWidth(420);
        primaryStage.setScene(new Scene(fxmlMain, 387, 279));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
