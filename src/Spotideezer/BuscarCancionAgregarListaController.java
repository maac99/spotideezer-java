/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotideezer;

import Entidades.CancionAux;
import Entidades.ListaR;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Monica
 */
public class BuscarCancionAgregarListaController implements Initializable {

    private FacadeAgregar man = new FacadeAgregar();

    @FXML
    private Button btnAgregarALista;
    @FXML
    private TableView<CancionAux> tblResultadosB;
    @FXML
    private TableColumn<CancionAux, String> colTitulo;
    @FXML
    private TableColumn<CancionAux, String> colAlbum;
    @FXML
    private TableColumn<CancionAux, String> colInterprete;
    @FXML
    private Label lblBuscarCancion;
    @FXML
    private Button btnBuscar;
    @FXML
    private TextField txtBuscador;
    @FXML
    private Button btnCrearListaNueva;
    @FXML
    private ComboBox<ListaR> cmbListasR;
    @FXML
    private Button btnReproducir;

    private Stage NuevaLista;
    private Stage Reproducir;
    private final ObservableList<CancionAux> cancionesOL = FXCollections.observableArrayList();
    private final ObservableList<ListaR> ListasOL = FXCollections.observableArrayList();
    private boolean ya;

    public boolean isYa() {
        return ya;
    }

    public void setYa(boolean ya) {
        this.ya = ya;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO/*

    }

    public void Inicializar() {
        List<ListaR> resultados = new ArrayList<>();
        resultados = this.man.darListasRUsuario();
        //System.out.println(resultados.size());
        this.ListasOL.setAll(resultados);
        this.cmbListasR.setItems(ListasOL);
        PropertyValueFactory<CancionAux, String> prop1 = new PropertyValueFactory<>("titulo_c");
        this.colTitulo.setCellValueFactory(prop1);
        PropertyValueFactory<CancionAux, String> prop2 = new PropertyValueFactory<>("titulo_alabum");
        this.colAlbum.setCellValueFactory(prop2);
        PropertyValueFactory<CancionAux, String> prop3 = new PropertyValueFactory<>("nombre_artistico");
        this.colInterprete.setCellValueFactory(prop3);
        this.tblResultadosB.setItems(cancionesOL);
    }

    @FXML
    private void OnClickBuscar(ActionEvent event) {
        String busqueda = this.txtBuscador.getText();
        List<CancionAux> resultados = new ArrayList<>();
        try {
            resultados = this.man.buscarCanciones(busqueda);
            setYa(true);
            cancionesOL.setAll(resultados);
            PropertyValueFactory<CancionAux, String> prop1 = new PropertyValueFactory<>("titulo_c");
            this.colTitulo.setCellValueFactory(prop1);
            PropertyValueFactory<CancionAux, String> prop2 = new PropertyValueFactory<>("titulo_alabum");
            this.colAlbum.setCellValueFactory(prop2);
            PropertyValueFactory<CancionAux, String> prop3 = new PropertyValueFactory<>("nombre_artistico");
            this.colInterprete.setCellValueFactory(prop3);
            this.tblResultadosB.setItems(cancionesOL);
        } catch (Exception ex) {
            this.man.MostrarMensajeAdvertencia(ex.getMessage());
        }

    }

    @FXML
    private void OnClickAgregar(ActionEvent event) {
        try {

            if (isYa() && !this.cmbListasR.getSelectionModel().isEmpty() && !this.tblResultadosB.getSelectionModel().isEmpty()) {
                String esto = this.man.agragrCancionListaR(this.tblResultadosB.getSelectionModel().getSelectedItem(), this.cmbListasR.getValue());
                this.man.MostrarMensajeConfirmacion("Cancion Agregada Correctamente. " + esto);
                this.limpiarDatos();
                //setYa(false);
            } else {

                this.man.MostrarMensajeAdvertencia("Faltan Parametros para realizar la operacion.");
            }
        } catch (Exception ex) {

            this.man.MostrarMensajeAdvertencia(ex.getMessage());
        }
    }

    @FXML
    private void OnClickNuevaLista(ActionEvent event) {
        if (isYa() && !this.tblResultadosB.getSelectionModel().isEmpty()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AgregarListaR.fxml"));
            Parent root = null;

            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(SpotideezerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            NuevaLista = new Stage();
            NuevaLista.setTitle("Crear nueva Lista de Reproducción");
            NuevaLista.setScene(new Scene(root));
            AgregarListaRController controlador = loader.getController();
            controlador.setMan(this.man);
            NuevaLista.show();

            controlador.setAgg(this.tblResultadosB.getSelectionModel().getSelectedItem());
            controlador.setAnt(this);
        } else {

            this.man.MostrarMensajeAdvertencia("Faltan Parametros para realizar la operacion.");
        }

    }

    public FacadeAgregar getMan() {
        return man;
    }

    public void setMan(FacadeAgregar man) {
        this.man = man;
    }

    public void limpiarDatos() {
        this.cmbListasR.getSelectionModel().clearSelection();
        this.tblResultadosB.getSelectionModel().clearSelection();
    }

    @FXML
    private void OnClickReproducirrrrrrrr(ActionEvent event) {
        if (isYa() && !this.tblResultadosB.getSelectionModel().isEmpty()) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ReproducirCancion.fxml"));
            Parent root = null;

            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(SpotideezerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Reproducir = new Stage();
            Reproducir.setTitle("Crear nueva Lista de Reproducción");
            Reproducir.setScene(new Scene(root));
            ReproducirCancionController controlador = loader.getController();
            controlador.setMan(this.man);
            controlador.setCancionr(tblResultadosB.getSelectionModel().getSelectedItem());

            boolean reac = this.man.existeReaccion(tblResultadosB.getSelectionModel().getSelectedItem());

            controlador.setReaccion(reac);

            controlador.Inicializar();
            Reproducir.show();
        } else {
            this.man.MostrarMensajeAdvertencia("Debe realizar una busqueda y selecionar una cancion.");
        }
    }
}
