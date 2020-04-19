/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotideezer;

import Entidades.Pais;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Monica
 */
public class SuscribirseController implements Initializable {

    @FXML
    private Button btnSuscribirse;
    @FXML
    private ComboBox<String> cmbTipoSUS;
    @FXML
    private Label lblTipoSus;
    @FXML
    private Label lblNuevoUsu;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblNickname;
    @FXML
    private Label lblPaisSUS;
    @FXML
    private ComboBox<Pais> cmbPaisSUS;
    @FXML
    private TextField txtNickname;
    @FXML
    private TextField txtNombre;
    Stage PedirTarjeta;

    private final ObservableList<Pais> paisesOL = FXCollections.observableArrayList();
    private final ObservableList<String> suscripcionesOL = FXCollections.observableArrayList();

    private FacadeAgregar man = new FacadeAgregar();
    @FXML
    private TextField txtApellidos;
    @FXML
    private Label lblApellidos;
    @FXML
    private Label lblMostrarMSM;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Pais> paises = new ArrayList<>();
        paises = this.man.darListaDePaises();
        this.paisesOL.setAll(paises);
        List<String> suscripciones = new ArrayList<>();
        String s1 = new String("Gratuita");
        String s2 = new String("Individual");

        suscripciones.add(s1);
        suscripciones.add(s2);

        this.suscripcionesOL.setAll(suscripciones);
        this.cmbPaisSUS.setItems(paisesOL);
        this.cmbTipoSUS.setItems(suscripcionesOL);
        //this.lblNuevoUsu.setText(this.man.getUser().getNickname());
    }

    @FXML
    private void OnClickSuscribirse(ActionEvent event) throws Exception {
        // Usuario u1 = man.agregarAutor(this.txtNombre.getText(), this.txtNickname.getText(), this.cmbPais.getValue().getNombrePais());

        // this.lblAgregadoSUS.setText(" " + u1.getNombre() + " con el nickname " + u1.getNickname() + ", del pais "
        // + u1.getPaisNombrePais() + " fue agregado.");
        IngresoTarjetaController controlador = new IngresoTarjetaController();

        if (!this.txtNickname.getText().isEmpty()
                && !this.txtNombre.getText().isEmpty()
                && !this.txtApellidos.getText().isEmpty()
                && !this.cmbPaisSUS.getSelectionModel().isEmpty()
                && !this.cmbTipoSUS.getSelectionModel().isEmpty()) {
            String sus = this.cmbTipoSUS.getValue();
            if (sus.compareTo("Individual") == 0) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("IngresoTarjeta.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(SpotideezerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                PedirTarjeta = new Stage();
                PedirTarjeta.setTitle("Ingresar numero de tarjeta");
                PedirTarjeta.setScene(new Scene(root));
                controlador = loader.getController();
                PedirTarjeta.show();

                /*
                
                

                System.out.println("AAAAAAAAA"+verificado);
                
                System.out.println(this.txtNickname.getText());
                System.out.println(this.txtNombre.getText());
                System.out.println(this.txtApellidos.getText());
                System.out.println(this.cmbPaisSUS.getValue().getNombrePais());
                System.out.println(this.cmbTipoSUS.getValue());
                 */
                controlador.setNombre(this.txtNombre.getText());
                controlador.setNickname(this.txtNickname.getText());
                controlador.setApellido(this.txtApellidos.getText());
                controlador.setPais(this.cmbPaisSUS.getValue().getNombrePais());
                controlador.setTipo(this.cmbTipoSUS.getValue());
                controlador.setAnt(this);
            } else {
                //boolean verifi = controlador.isVerificacion();
                /*System.out.println(this.txtNickname.getText());
                System.out.println(this.txtNombre.getText());
                System.out.println(this.txtApellidos.getText());
                System.out.println(this.cmbPaisSUS.getValue().getNombrePais());
                System.out.println(this.cmbTipoSUS.getValue());*/
                Usuario ll;
                try {
                    ll = this.man.agregarUsuario(this.txtNickname.getText(), this.txtNombre.getText(), this.txtApellidos.getText(), this.cmbPaisSUS.getValue().getNombrePais(), this.cmbTipoSUS.getValue());
                    this.limpiarDatos();
                    this.man.MostrarMensajeConfirmacion("El usuario con el Nickname"+ll.getNickname()+" fue agregado correctamente.");
                    //this.lblMostrarMSM.setText("El usuario con el "+ll.getNickname()+" fue agregado correctamente.");
                } catch (Exception e) {
                    this.limpiarDatos();
                    this.man.MostrarMensajeAdvertencia(e.getMessage());
                }
            }
        } else {
            this.man.MostrarMensajeAdvertencia("Todos los campos son obligatorios.");
        }
    }

    public FacadeAgregar getMan() {
        return man;
    }

    public void setMan(FacadeAgregar man) {
        this.man = man;
    }

    public void limpiarDatos() {
        this.txtNombre.setText("");
        this.txtApellidos.setText("");
        this.txtNickname.setText("");
        this.cmbPaisSUS.getSelectionModel().clearSelection();
        this.cmbTipoSUS.getSelectionModel().clearSelection();
    }

    public void setLblMostrarMSM(String lblMostrarMSM) {
        this.lblMostrarMSM.setText(lblMostrarMSM);
        
    }
    
}
