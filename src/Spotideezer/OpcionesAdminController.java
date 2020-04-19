/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotideezer;

import Facades.FacadeAgregar;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Monica
 */
public class OpcionesAdminController implements Initializable {

    private FacadeAgregar man = new FacadeAgregar();
    //Elementos de la pantalla.
    @FXML
    private Label lblMenuAdmin;
    @FXML
    private Button btnAgregarA;
    @FXML
    private Button btnAgregarAl;
    @FXML
    private Button btnAuditoria;

    //Pantallas.
    private Stage AgregarArtista;
    private Stage AgregarAlbum;
    private Stage Auditoria;

    /**
     * Initializes the controller class.
     */
    @FXML
    private void OnClickAgregarArtista(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AgregarArtista.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SpotideezerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AgregarArtista = new Stage();
        AgregarArtista.setTitle("Agregar Artista");
        AgregarArtista.setScene(new Scene(root));
        AgregarArtistaController controlador = loader.getController();
        controlador.setMan(this.man);
        AgregarArtista.show();
    }

    @FXML
    private void OnClickAgregarAlbum(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AgregarAlbumEP.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SpotideezerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AgregarAlbum = new Stage();
        AgregarAlbum.setTitle("Agregar Album");
        AgregarAlbum.setScene(new Scene(root));
        AgregarAlbumEPController controlador = loader.getController();
        controlador.setMan(this.man);
        AgregarAlbum.show();

    }

    @FXML
    private void OnClickVerAuditoria(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Auditoria.fxml"));
        Parent root = null;

        try {
            root = loader.load();
            AuditoriaController controlador = loader.getController();
        controlador.setMan(this.man);
        controlador.Inicializar();

        } catch (IOException ex) {
            Logger.getLogger(SpotideezerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Auditoria = new Stage();
        Auditoria.setTitle("Ver Auditoria");
        Auditoria.setScene(new Scene(root));
        AuditoriaController controlador = loader.getController();
        controlador.setMan(this.man);
        Auditoria.show();
    }

    public FacadeAgregar getMan() {
        return man;
    }

    public void setMan(FacadeAgregar man) {
        this.man = man;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
