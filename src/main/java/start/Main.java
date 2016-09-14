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
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("views/MainWindow1.fxml"));
        Parent fxmlMain = fxmlLoader.load();
        MainController mainMainController = fxmlLoader.getController();
        mainMainController.setMainStage(primaryStage);

        primaryStage.setTitle("English Dictionary");
        //primaryStage.setResizable(false);
        primaryStage.setMinHeight(320);
        primaryStage.setMinWidth(420);
        primaryStage.setMaxWidth(320);
        primaryStage.setMaxHeight(380);
        primaryStage.setScene(new Scene(fxmlMain, 387, 279));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
