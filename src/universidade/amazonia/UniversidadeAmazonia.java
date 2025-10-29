
package universidade.amazonia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Administrator
 */
public class UniversidadeAmazonia extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
          
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);     
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Universidade Amazonia");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
