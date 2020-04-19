/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotideezer;

import Entidades.Cancion;
import Entidades.CancionAux;
import Entidades.ListaR;
import Facades.FacadeAgregar;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Monica
 */
public class AgregarListaRController implements Initializable {

    @FXML
    private Label lblCrearLista;
    @FXML
    private TextField txtNombreLista;
    @FXML
    private Label lblNombreLista;
    @FXML
    private Button btnCrearLista;

    private FacadeAgregar man = new FacadeAgregar();
    private CancionAux agg;
    private BuscarCancionAgregarListaController ant;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void OnClickCrear(ActionEvent event) {
        String nameL = this.txtNombreLista.getText();

        if (!nameL.isEmpty()) {
            try {
                ListaR jj = this.man.agregarListaR(nameL);
                String esto = this.man.agragrCancionListaR(getAgg(),jj );
                this.man.MostrarMensajeConfirmacion("Lista de reproducción " + nameL + " creada correctamente.");
                this.man.MostrarMensajeConfirmacion("Cancion Agregada Correctamente. " + esto);
                this.ant.Inicializar();
                this.ant.limpiarDatos();
            } catch (Exception ex) {
                this.man.MostrarMensajeAdvertencia(ex.getMessage());
            }
        } else {
            this.man.MostrarMensajeAdvertencia("Todos los campos son obligatorios.");
        }
        Stage sss = (Stage) this.btnCrearLista.getScene().getWindow();
        sss.close();
    }

    public FacadeAgregar getMan() {
        return man;
    }

    public void setMan(FacadeAgregar man) {
        this.man = man;
    }

    public CancionAux getAgg() {
        return agg;
    }

    public void setAgg(CancionAux agg) {
        this.agg = agg;
    }

    public BuscarCancionAgregarListaController getAnt() {
        return ant;
    }

    public void setAnt(BuscarCancionAgregarListaController ant) {
        this.ant = ant;
    }
    
}
