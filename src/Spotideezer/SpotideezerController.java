/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotideezer;

import Entidades.Auditoria;
import Entidades.CancionAux;
import Entidades.Empresa;
import Entidades.Generoxidioma;
import Entidades.Interprete;
import Facades.FacadeAgregar;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
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
 *
 * @author Sala BD
 */
public class SpotideezerController implements Initializable {

    @FXML
    private Button btnAdmin;
    @FXML
    private Button btnUser;
    @FXML
    private Label lblIngreso;
    @FXML
    private Label lblBienvenida;
    @FXML
    private Button btnSus;
    @FXML
    private Label lblCrearCuenta;
    //Pantalla.
    private Stage Menu;
    private Stage Ingreso;
    private Stage Suscripcion;

    private FacadeAgregar man = new FacadeAgregar();
    private boolean ingresado = false;
    private boolean ingresadoadmin = false;
    private boolean ingresadouser = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //List<Empresa> re = this.man.darEmpresas();
        //System.out.println(re.size());
        //this.man.MostrarMensajeConfirmacion(""+this.man.darNumAlbum());
        /*
        Empresa en = new Empresa("Astralwerks");
        Date input = new Date();
        LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        this.man.MostrarMensajeConfirmacion(""+this.man.agregarAlbum("Album", "Mai Lof", date, en).getTituloAlbum());
        
        
        List<Generoxidioma> res = this.man.darGenerosEnEspañol();
        
        this.man.MostrarMensajeConfirmacion(""+res.size());
*/
    }

    //INGRESO.
    @FXML
    private void OnClickAdministrador(ActionEvent event) throws IOException {
        ingresado = true;
        ingresadoadmin = true;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("OpcionesAdmin.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException ex) {
            this.man.MostrarMensajeAdvertencia(ex.getMessage());
            Logger.getLogger(SpotideezerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        OpcionesAdminController controlador = loader.getController();
        controlador.setMan(this.man);
        Menu = new Stage();
        Menu.setTitle("Opciones Administrador");
        Menu.setScene(new Scene(root));

        Menu.show();
    }

    @FXML
    private void OnClickUsuario(ActionEvent event) {
        //this.man.MostrarMensajeConfirmacion("Tole TAMOO");
        ingresado = true;
        ingresadouser = true;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("SeleccionUser.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException ex) {
            this.man.MostrarMensajeAdvertencia(ex.getMessage());
            Logger.getLogger(SpotideezerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        SeleccionUserController controlador = loader.getController();
        controlador.setMan(this.man);
        //System.out.println("estesi"+this.man.getUser().getNickname());
        Ingreso = new Stage();
        Ingreso.setTitle("Usuario");
        Ingreso.setScene(new Scene(root));

        Ingreso.show();
    }

    @FXML
    private void OnClickSuscribirse(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Suscribirse.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SpotideezerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Suscripcion = new Stage();
        Suscripcion.setTitle("Suscripción");
        Suscripcion.setScene(new Scene(root));
        SuscribirseController controlador = loader.getController();
        controlador.setMan(this.man);
        Suscripcion.show();
    }
}
