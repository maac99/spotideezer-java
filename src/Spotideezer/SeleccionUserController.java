/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotideezer;

import Entidades.CancionAux;
import Entidades.Usuario;
import Facades.FacadeAgregar;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Monica
 */
public class SeleccionUserController implements Initializable {

    @FXML
    private ComboBox<Usuario> cmbUsuarios;
    @FXML
    private Button btnIngresar;
    @FXML
    private Label lblUsuario;

    private Stage Menu;

    private FacadeAgregar man = new FacadeAgregar();
    private final ObservableList<Usuario> usuariosOL = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = this.man.darListaDeUsuarios();
        this.usuariosOL.setAll(usuarios);
        this.cmbUsuarios.setItems(usuariosOL);
    }

    @FXML
    private void OnClickIngresar(ActionEvent event) {
        
        if(!this.cmbUsuarios.getSelectionModel().isEmpty())
        {
        Usuario user = this.cmbUsuarios.getValue();
        this.man.setUser(user);
        
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("OpcionesUser.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SpotideezerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        OpcionesUserController as = loader.getController();
        as.setMan(man);
        
        Menu = new Stage();
        Menu.setTitle("Menu de usuario");
        Menu.setScene(new Scene(root));
        OpcionesUserController controlador = loader.getController();
        controlador.setMan(this.man);
        Stage sss = (Stage) this.btnIngresar.getScene().getWindow();
        sss.close();
        Menu.show();
        }
        else{
            this.man.MostrarMensajeAdvertencia("Debe seleccionar un usuario para continuar.");
        }

    }

    public FacadeAgregar getMan() {
        return man;
    }

    public void setMan(FacadeAgregar man) {
        this.man = man;
    }

}