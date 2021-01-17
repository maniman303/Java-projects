package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import mech.*;

public class Main extends Application
{
    public static Calc calc = new Calc();

    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("calcLayout.fxml"));
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(new Scene(root, 200, 290));
        primaryStage.setMinWidth(250);
        primaryStage.setMaxWidth(500);
        primaryStage.setMinHeight(380);
        primaryStage.setMaxHeight(720);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
