/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotideezer;

import Entidades.Album;
import Entidades.Cancion;
import Entidades.Cxi;
import Entidades.Empresa;
import Facades.FacadeAgregar;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author julia
 */
public class AgregarAlbumEPController implements Initializable {

    @FXML
    private Label lblAgregarAlbum;
    @FXML
    private Label lblTipo;
    @FXML
    private Label lblNombreAlbum;
    @FXML
    private Label lblFechaLanamiento1;
    @FXML
    private Label lblEmpresa;
    @FXML
    private ComboBox<String> cmbTipo;
    @FXML
    private TextField txtNombreAlbum;
    @FXML
    private DatePicker dtpFechaLanzamiento;
    @FXML
    private ComboBox<Empresa> cmbEmpresa;
    @FXML
    private TableView<Cancion> tblCanciones;
    @FXML
    private TableColumn<Cancion, String> colTitulo;
    @FXML
    private Button btnAgregarCancion;
    @FXML
    private Button btnGuardarAlbum;
    @FXML
    private Label lblCancionesAlbum;

    private FacadeAgregar man = new FacadeAgregar();
    private final ObservableList<Cancion> cancionesOL = FXCollections.observableArrayList();
    private final ObservableList<String> tipoOL = FXCollections.observableArrayList();
    private final ObservableList<Empresa> empresasOL = FXCollections.observableArrayList();

    private Stage AgregarCancion;

    private boolean AlbumSaved;
    private Album creado;
    private List<Cxi> nuevaCxi = new ArrayList<Cxi>();
    private List<Cancion> nuevasCanciones = new ArrayList<Cancion>();
    @FXML
    private Button btnCancelarOperacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        List<Cancion> canciones = new ArrayList<>();
        this.cancionesOL.setAll(canciones);
        PropertyValueFactory<Cancion, String> prop1 = new PropertyValueFactory<>("cancionPK");
        this.colTitulo.setCellValueFactory(prop1);
        this.tblCanciones.setItems(cancionesOL);

        List<String> tipos = new ArrayList<>();
        tipos.add("Album");
        tipos.add("EP");
        this.tipoOL.setAll(tipos);
        this.cmbTipo.setItems(tipoOL);

        List<Empresa> empresas = new ArrayList<>();
        empresas = this.man.darEmpresas();
        this.empresasOL.setAll(empresas);
        this.cmbEmpresa.setItems(empresasOL);
        this.AlbumSaved = false;

    }

    @FXML
    private void OnClickAgregarCancion(ActionEvent event) {
        if (isAlbumSaved()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("AgregarCancion.fxml"));
            Parent root = null;

            try {
                root = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(SpotideezerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            AgregarCancion = new Stage();
            AgregarCancion.setTitle("Agregar Cancion");
            AgregarCancion.setScene(new Scene(root));
            AgregarCancionController controlador = loader.getController();
            controlador.setMan(this.man);
            //controlador.setNuevaCxi(this.nuevaCxi);
            //controlador.setNuevasCanciones(this.nuevasCanciones);
            controlador.setNuevoActual(this.creado);
            controlador.setAnt(this);

            AgregarCancion.show();
        } else {
            this.man.MostrarMensajeAdvertencia("Debe crear un album.");
        }
    }

    @FXML
    private void OnClickGuardar(ActionEvent event) {

        if (!isAlbumSaved() && !this.cmbTipo.getSelectionModel().isEmpty() && !this.txtNombreAlbum.getText().isEmpty()
                && this.dtpFechaLanzamiento.getValue() != null && !this.cmbEmpresa.getSelectionModel().isEmpty()) {

            try {
                LocalDate fechita = this.dtpFechaLanzamiento.getValue();
                Album nu = this.man.agregarAlbum(this.cmbTipo.getValue(), this.txtNombreAlbum.getText(), fechita, this.cmbEmpresa.getValue());
                this.man.MostrarMensajeConfirmacion("Album creado correctamente.");
                this.setAlbumSaved(true);
                this.setCreado(nu);
                this.btnGuardarAlbum.setText("Guardar Canciones");
            } catch (Exception e) {
                this.man.MostrarMensajeAdvertencia(e.getMessage());
            }

        } else if (AlbumSaved && this.nuevasCanciones.size() > 0) {
            try {

                for (Cancion nuevasCancione : nuevasCanciones) {

                }

                boolean agg = this.man.agregarCanciones(this.nuevaCxi, this.nuevasCanciones);
                if (agg) {
                    this.man.MostrarMensajeConfirmacion("Canciones agregadas correctamente al album");
                }
                this.AlbumSaved = false;
                this.creado = null;
                this.nuevaCxi.clear();
                this.nuevasCanciones.clear();
                this.btnGuardarAlbum.setText("Guardar álbum");
                this.txtNombreAlbum.setText("");
                this.cmbTipo.getSelectionModel().clearSelection();
                this.cmbEmpresa.getSelectionModel().clearSelection();
                this.dtpFechaLanzamiento.setValue(null);
                this.tblCanciones.setItems(null);
            } catch (Exception e) {
                this.man.MostrarMensajeAdvertencia(e.getMessage());
            }

        } else {
            this.man.MostrarMensajeAdvertencia("Faltan parametros para realizar la operación.");
        }
    }

    public void actualizarTabla() {

        PropertyValueFactory<Cancion, String> prop1 = new PropertyValueFactory<>("cancionPK");
        this.colTitulo.setCellValueFactory(prop1);
        this.cancionesOL.setAll(this.nuevasCanciones);
        this.tblCanciones.setItems(cancionesOL);
    }

    public FacadeAgregar getMan() {
        return man;
    }

    public void setMan(FacadeAgregar man) {
        this.man = man;
    }

    public boolean isAlbumSaved() {
        return AlbumSaved;
    }

    public void setAlbumSaved(boolean AlbumSaved) {
        this.AlbumSaved = AlbumSaved;
    }

    public Album getCreado() {
        return creado;
    }

    public void setCreado(Album creado) {
        this.creado = creado;
    }

    public List<Cxi> getNuevaCxi() {
        return nuevaCxi;
    }

    public void setNuevaCxi(List<Cxi> nuevaCxi) {
        this.nuevaCxi = nuevaCxi;
    }

    public List<Cancion> getNuevasCanciones() {
        return nuevasCanciones;
    }

    public void setNuevasCanciones(List<Cancion> nuevasCanciones) {
        this.nuevasCanciones = nuevasCanciones;
    }

    @FXML
    private void OnClickCancelarOperacion(ActionEvent event) {
        this.cancelarOpp();
    }

    public void cancelarOpp() {
        if (!isAlbumSaved()) {
            this.txtNombreAlbum.setText("");
            this.cmbTipo.getSelectionModel().clearSelection();
            this.cmbEmpresa.getSelectionModel().clearSelection();
            this.dtpFechaLanzamiento.setValue(null);
            this.tblCanciones.setItems(null);
            Stage sss = (Stage) this.btnCancelarOperacion.getScene().getWindow();
            sss.close();
        } else {
            this.AlbumSaved = false;
            this.creado = null;
            nuevaCxi.clear();
            nuevasCanciones.clear();
            this.btnGuardarAlbum.setText("Guardar álbum");

            this.txtNombreAlbum.setText("");
            this.cmbTipo.getSelectionModel().clearSelection();
            this.cmbEmpresa.getSelectionModel().clearSelection();
            this.dtpFechaLanzamiento.setValue(null);
            this.tblCanciones.setItems(null);

            Stage sss = (Stage) this.btnCancelarOperacion.getScene().getWindow();
            sss.close();
        }
    }
}
