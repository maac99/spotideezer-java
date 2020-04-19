/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotideezer;

import Entidades.Usuario;
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
 * @author julia
 */
public class IngresoTarjetaController implements Initializable {

    @FXML
    private Label lblIngresarNum;
    @FXML
    private TextField txtIngresarNum;
    @FXML
    private Button btnValidar;

    private FacadeAgregar man = new FacadeAgregar();
    private boolean verificacion = true;
    private String Nickname;
    private String nombre;
    private String apellido;
    private String pais;
    private String tipo;
    private Usuario us;
    private SuscribirseController ant;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void OnClickValidar(ActionEvent event) throws Exception {
        if (!this.txtIngresarNum.getText().isEmpty()) {
            String num = this.txtIngresarNum.getText();
            this.setVerificacion(this.man.verificarTarjeta(num));

            if (!isVerificacion()) {
                this.man.MostrarMensajeAdvertencia("Numero de tarjeta inválido.");
            } else {
                Usuario ff;
                try {
                    ff = this.man.agregarUsuario(this.Nickname, this.nombre, this.apellido, this.pais, this.tipo);
                    this.setUs(ff);
                    this.ant.limpiarDatos();
                    this.man.MostrarMensajeConfirmacion("El usuario con el "+ff.getNickname()+" fue agregado correctamente.");
                    //this.ant.setLblMostrarMSM("El usuario con el "+ff.getNickname()+" fue agregado correctamente.");
                } catch (Exception e) {
                    this.ant.limpiarDatos();
                    this.man.MostrarMensajeAdvertencia(e.getMessage());
                }

            }
        } else {
            this.man.MostrarMensajeAdvertencia("Todos los campos son obligatorios.");
        }
        Stage sss = (Stage) this.btnValidar.getScene().getWindow();
        sss.close();
    }

    public FacadeAgregar getMan() {
        return man;
    }

    public void setMan(FacadeAgregar man) {
        this.man = man;
    }

    public boolean isVerificacion() {
        return verificacion;
    }

    public void setVerificacion(boolean verificacion) {
        this.verificacion = verificacion;
    }

    public Usuario getUs() {
        return us;
    }

    public void setUs(Usuario us) {
        this.us = us;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String Nickname) {
        this.Nickname = Nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public SuscribirseController getAnt() {
        return ant;
    }

    public void setAnt(SuscribirseController ant) {
        this.ant = ant;
    }

}
