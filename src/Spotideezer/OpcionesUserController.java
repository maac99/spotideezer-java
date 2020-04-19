/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotideezer;

import Entidades.CancionAux;
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
public class OpcionesUserController implements Initializable {

    private FacadeAgregar man = new FacadeAgregar();
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnRepro;
    @FXML
    private Label lblMenuUser;

    //Pantallas.
    private Stage Buscador_ListaR;
    private Stage Modificar;
    private Stage Reproducir;


    @FXML
    private void OnClickBuscarCancion(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("BuscarCancionAgregarLista.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SpotideezerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        BuscarCancionAgregarListaController er = loader.getController();
        er.setMan(this.man);
        er.Inicializar();
        Buscador_ListaR = new Stage();
        Buscador_ListaR.setTitle("Buscar canción");
        Buscador_ListaR.setScene(new Scene(root));
        Buscador_ListaR.show();
    }

    @FXML
    private void OnClickModificarLista(ActionEvent event) {
         FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModificarLista.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SpotideezerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ModificarListaController er = loader.getController();
        er.setMan(this.man);
        er.Inicializar();
        Modificar = new Stage();
        Modificar.setTitle("Buscar canción");
        Modificar.setScene(new Scene(root));
        Modificar.show();
    }

    @FXML
    private void OnClickReproducirCancion(ActionEvent event) {
        
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
