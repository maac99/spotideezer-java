/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotideezer;

import Entidades.Album;
import Entidades.Cancion;
import Entidades.CancionPK;
import Entidades.Cxi;
import Entidades.CxiPK;
import Entidades.Empresa;
import Entidades.Generoxidioma;
import Entidades.Interprete;
import Facades.FacadeAgregar;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author julia
 */
public class AgregarCancionController implements Initializable {

    @FXML
    private Label lblAgregarCancion;
    @FXML
    private TableView<Interprete> tblInterpretesSec;
    @FXML
    private TableColumn<Interprete, String> colNombreArt;
    @FXML
    private TableColumn<Interprete, String> colNombreReal;
    @FXML
    private ComboBox<Interprete> cmbInterpretePrinci;
    @FXML
    private TextField txtTitulo;
    @FXML
    private ComboBox<Generoxidioma> cmbGenero;
    @FXML
    private Label lblSeleccioneInterpretes;
    @FXML
    private Label lblInterpretePrinci;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblGenero;
    @FXML
    private Button btnGuardar;

    private FacadeAgregar man = new FacadeAgregar();
    private final ObservableList<Interprete> interpretesOL = FXCollections.observableArrayList();
    private final ObservableList<Generoxidioma> generosOL = FXCollections.observableArrayList();
    //private final ObservableList<TablePosition> selectedCells = FXCollections.observableArrayList();

    //private List<Cxi> nuevaCxi;
    //private List<Cancion> nuevasCanciones;
    private Album nuevoActual;
    private AgregarAlbumEPController ant;

    @FXML
    private Label lblDuracion;
    @FXML
    private TextField txtHora;
    @FXML
    private TextField txtSegundos;
    @FXML
    private TextField txtMinutos;
    @FXML
    private Label lblDuracion1;
    @FXML
    private Label lblDuracion11;
    @FXML
    private Label lblDuracion2;
    @FXML
    private Button btnCancelarOperacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //this.nuevasCanciones = new ArrayList<Cancion>();
        //this.nuevaCxi = new ArrayList<Cxi>();

        List<Interprete> interpretes = new ArrayList<>();
        List<Generoxidioma> generos = new ArrayList<>();
        try {
            interpretes = this.man.darListaDeInterpretes();
            generos = this.man.darGenerosEnEspañol();
        } catch (Exception e) {
            this.man.MostrarMensajeAdvertencia(e.getMessage());
        }

        this.interpretesOL.setAll(interpretes);
        PropertyValueFactory<Interprete, String> prop1 = new PropertyValueFactory<>("nombreReal");
        this.colNombreArt.setCellValueFactory(prop1);
        PropertyValueFactory<Interprete, String> prop2 = new PropertyValueFactory<>("nombreArtistico");
        this.colNombreReal.setCellValueFactory(prop2);
        this.tblInterpretesSec.setItems(interpretesOL);

        this.cmbInterpretePrinci.setItems(interpretesOL);

        this.generosOL.setAll(generos);
        this.cmbGenero.setItems(generosOL);
        this.tblInterpretesSec.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.txtHora.setText("0");
        this.txtMinutos.setText("0");
        this.txtSegundos.setText("0");

    }

    @FXML
    private void OnClickGuardar(ActionEvent event) {
        ObservableList<Interprete> selectedCells = this.tblInterpretesSec.getSelectionModel().getSelectedItems();

        if (!this.cmbInterpretePrinci.getSelectionModel().isEmpty() && !this.txtTitulo.getText().isEmpty()
                && !this.cmbGenero.getSelectionModel().isEmpty()) {

            int h = Integer.parseInt(this.txtHora.getText());
            int m = Integer.parseInt(this.txtMinutos.getText());
            int s = Integer.parseInt(this.txtSegundos.getText());

            Date duracion = this.man.getDate(h, m, s);

            Cancion nueva = new Cancion();

            nueva.setAlbumIdAlbum(nuevoActual);
            nueva.setDuracion(duracion);
            CancionPK nn = new CancionPK(this.txtTitulo.getText(), this.cmbInterpretePrinci.getValue().getInterpreteIdIn());
            nueva.setCancionPK(nn);

            nueva.setGeneroCodigoGenero(this.cmbGenero.getValue().getGenero());

            List<Cancion> Existentes = this.ant.getNuevasCanciones();
            Existentes.add(nueva);
            this.ant.setNuevasCanciones(Existentes);

            if (selectedCells.size() > 0) {

                for (Interprete selectedCell : selectedCells) {

                    CxiPK pkagg = new CxiPK(this.txtTitulo.getText(), this.cmbInterpretePrinci.getValue().getInterpreteIdIn(), selectedCell.getInterpreteIdIn());
                    Cxi agregarR = new Cxi(pkagg);
                    agregarR.setRoll("Secundario");
                    List<Cxi> Existentes2 = this.ant.getNuevaCxi();
                    Existentes2.add(agregarR);
                    this.ant.setNuevaCxi(Existentes2);
                }

            }
            this.ant.actualizarTabla();
            Stage sss = (Stage) this.btnGuardar.getScene().getWindow();
            sss.close();
        } else {
            this.man.MostrarMensajeAdvertencia("Faltan parametros para completar la operacion.");
        }

    }

    public FacadeAgregar getMan() {
        return man;
    }

    public void setMan(FacadeAgregar man) {
        this.man = man;
    }

    public Album getNuevoActual() {
        return nuevoActual;
    }

    public void setNuevoActual(Album nuevoActual) {
        this.nuevoActual = nuevoActual;
    }

    public AgregarAlbumEPController getAnt() {
        return ant;
    }

    public void setAnt(AgregarAlbumEPController ant) {
        this.ant = ant;
    }

    @FXML
    private void OnClickCancelarOperacion(ActionEvent event) {
        Stage sss = (Stage) this.btnGuardar.getScene().getWindow();
        sss.close();
        this.nuevoActual = null;
        this.ant.cancelarOpp();

    }

}
