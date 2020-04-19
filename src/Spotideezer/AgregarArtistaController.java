/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotideezer;

import Entidades.Interprete;
import Entidades.Pais;
import Facades.FacadeAgregar;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Monica
 */
public class AgregarArtistaController implements Initializable {

    private SpotideezerController SP;
    private FacadeAgregar man = new FacadeAgregar();
    @FXML
    private Label lblAgregar;
    @FXML
    private Label lblNombreReal;
    @FXML
    private Button btnGuardar;
    @FXML
    private TextField txtNombreArt;
    @FXML
    private TextField txtNombreReal;
    @FXML
    private ComboBox<Pais> cmbPais;
    @FXML
    private Label lblPais;
    @FXML
    private Label lblNombreArt;
    @FXML
    private Label lblAgregado;

    private final ObservableList<Pais> paisesOL = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        List<Pais> Paises = this.man.darListaDePaises();
        paisesOL.setAll(Paises);
        this.cmbPais.setItems(paisesOL);

    }

    @FXML
    private void OnClickGuardar(ActionEvent event) {

        if (!this.txtNombreReal.getText().isEmpty()
                && !this.txtNombreArt.getText().isEmpty()
                && !this.cmbPais.getSelectionModel().isEmpty()) {
            Interprete I1 = this.man.agregarAutor(this.txtNombreReal.getText(), this.txtNombreArt.getText(), this.cmbPais.getValue().getNombrePais());
            //this.lblAgregado.setText("El usuario " + I1.getNombreReal() + " conocido como " + I1.getNombreArtistico() + " del pais "
                    //+ I1.getPaisNombrePais() + " fue agregado.");
            this.man.MostrarMensajeConfirmacion("El usuario " + I1.getNombreReal() + " conocido como " + I1.getNombreArtistico() + " del pais "
                    + I1.getPaisNombrePais() + " fue agregado.");
            this.txtNombreReal.setText("");
            this.txtNombreArt.setText("");
            this.cmbPais.getSelectionModel().clearSelection();
        } else {
            this.man.MostrarMensajeAdvertencia("Todos los campos son obligatorios.");
           
        }
    }

    public FacadeAgregar getMan() {
        return this.man;
    }

    public void setMan(FacadeAgregar man) {
        this.man = man;
    }

}
