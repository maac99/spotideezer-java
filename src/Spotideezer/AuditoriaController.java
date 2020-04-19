/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Spotideezer;

import Entidades.Auditoria;
import Facades.FacadeAgregar;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Monica
 */
public class AuditoriaController implements Initializable {


    private FacadeAgregar man = new FacadeAgregar();
    private final ObservableList<Auditoria> AuditoriasOL = FXCollections.observableArrayList();
    @FXML
    private Label lblAuditoria;
    @FXML
    private TableView<Auditoria> tableAuditoria;
    @FXML
    private TableColumn<Auditoria, String> colEntidad;
    @FXML
    private TableColumn<Auditoria, String> colID;
    @FXML
    private TableColumn<Auditoria, String> colOperacion;
    @FXML
    private TableColumn<Auditoria, Date> colFecha;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    public void Inicializar(){
        List<Auditoria> registro = new ArrayList<>();
        registro = this.man.darTablaAuditoria();
        this.AuditoriasOL.setAll(registro);
        this.tableAuditoria.setItems(this.AuditoriasOL);
        
        

        
        PropertyValueFactory<Auditoria, String> prop1 = new PropertyValueFactory<>("entidadModificada");
        this.colEntidad.setCellValueFactory(prop1);
        PropertyValueFactory<Auditoria, String> prop2 = new PropertyValueFactory<>("identificador");
        this.colID.setCellValueFactory(prop2);
        PropertyValueFactory<Auditoria, String> prop3 = new PropertyValueFactory<>("operacion");
        this.colOperacion.setCellValueFactory(prop3);
        PropertyValueFactory<Auditoria, Date> prop4 = new PropertyValueFactory<>("fechaHora");
        this.colFecha.setCellValueFactory(prop4);
        
    }
    

    public FacadeAgregar getMan() {
        return man;
    }

    public void setMan(FacadeAgregar man) {
        this.man = man;
    }
    
}
