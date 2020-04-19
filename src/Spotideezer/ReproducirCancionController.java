/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotideezer;

import Entidades.CancionAux;
import Facades.FacadeAgregar;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Monica
 */
public class ReproducirCancionController implements Initializable {
    
    @FXML
    private Label lblReproducirCancion;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblInterprete;
    @FXML
    private Label lblAlbum;
    @FXML
    private Label lblParaTitulo;
    @FXML
    private Label lblParaInterprete;
    @FXML
    private Label lblParaAlbum;
    @FXML
    private Button btnMeGusta;
    
    private CancionAux cancionr;
    private boolean reaccion;
    
    private FacadeAgregar man = new FacadeAgregar();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void Inicializar() {
        
        try {
            this.man.registrarReproduccion(this.cancionr);
            //this.man.MostrarMensajeConfirmacion(""+reaccion);
            this.lblParaTitulo.setText(this.cancionr.getTitulo_c());
            this.lblParaInterprete.setText(this.cancionr.getNombre_artistico());
            this.lblParaAlbum.setText(this.cancionr.getTitulo_alabum());
            //this.btnMeGusta.setVisible(false);
            if (this.reaccion) {
                this.btnMeGusta.setText("No me gusta");
            } else {
                this.btnMeGusta.setText("Me gusta");
            }
        } catch (Exception e) {
            this.man.MostrarMensajeAdvertencia(e.getMessage());
        }
        
    }
    
    public CancionAux getCancionr() {
        return cancionr;
    }
    
    public void setCancionr(CancionAux cancionr) {
        this.cancionr = cancionr;
    }
    
    @FXML
    private void OnClickMeGusta(ActionEvent event) {
        try {
            boolean rea = this.man.actualizarReaccion(this.reaccion, this.cancionr);

            //Verificar si ya existe reaccion de me gusta
            if (rea) {
                if (this.reaccion) {
                    this.reaccion = false;
                    this.btnMeGusta.setText("Me gusta");
                } else {
                    this.reaccion = true;
                    this.btnMeGusta.setText("No me gusta");
                }
            } else {
                this.man.MostrarMensajeAdvertencia("No se cambio la reaccion.");
            }
        } catch (Exception e) {
            this.man.MostrarMensajeAdvertencia(e.getMessage());
        }
        
    }
    
    public boolean isReaccion() {
        return reaccion;
    }
    
    public void setReaccion(boolean reaccion) {
        this.reaccion = reaccion;
    }
    
    public FacadeAgregar getMan() {
        return man;
    }
    
    public void setMan(FacadeAgregar man) {
        this.man = man;
    }
    
}
