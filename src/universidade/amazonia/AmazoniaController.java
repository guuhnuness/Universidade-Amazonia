
package universidade.amazonia;


import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class AmazoniaController implements Initializable {
    @FXML private ImageView imgLogo;
    @FXML private TextField txtUser;
    @FXML private TextField txtPassword;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       Image img = new Image(getClass().getResource("/imagens/logosemfundo.png").toExternalForm());
       imgLogo.setImage(img);
    }
    public void login(MouseEvent event) throws IOException{
        if(txtUser.getText().equals("admin") && txtPassword.getText().equals("admin")){
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/MainScreen/main.fxml"));
            Scene scene = new Scene (root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
            ((Node)event.getSource()).getScene().getWindow().hide();
            check();
        }

        else{
            erro();
            }
        
    }
    public void erro(){
        Notifications notificacao = Notifications.create();
        Image image = new Image("/imagens/erro.png");
        notificacao.graphic(new ImageView(image));
        notificacao.title("Erro");
        notificacao.text("Usuário ou senha incorretos");
        notificacao.hideAfter(Duration.seconds(5));
        notificacao.position(Pos.BASELINE_RIGHT);
        notificacao.show();
        
        
    }
    public void check(){
        Notifications notificacao = Notifications.create();
        Image image = new Image("/imagens/sucesso.png");
        notificacao.graphic(new ImageView(image));
        notificacao.title("Sucesso");
        notificacao.text("Bem vindo");
        notificacao.hideAfter(Duration.seconds(5));
        notificacao.position(Pos.BASELINE_RIGHT);
        notificacao.show();
        
        
    }
}

