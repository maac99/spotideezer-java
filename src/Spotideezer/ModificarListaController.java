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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ModificarListaController implements Initializable {

    @FXML
    private Label lblBuscarCancion;
    @FXML
    private ComboBox<ListaR> cmbListasR;
    @FXML
    private TableView<CancionAux> tblCanciones;
    @FXML
    private TableColumn<CancionAux, String> colOrden;
    @FXML
    private TableColumn<CancionAux, String> colTitulo;
    @FXML
    private TableColumn<CancionAux, String> colAlbum;
    @FXML
    private TableColumn<CancionAux, String> colInterprete;
    @FXML
    private TextField txtNombre;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnElegir;

    private Stage Reproducir;
    private final ObservableList<CancionAux> cancionesOL = FXCollections.observableArrayList();
    private final ObservableList<ListaR> listasOL = FXCollections.observableArrayList();
    public ArrayList<CancionAux> canciones = new ArrayList<>();
    public ArrayList<ListaR> listas = new ArrayList<>();
    private FacadeAgregar man = new FacadeAgregar();
    private boolean elegido = false;
    @FXML
    private Button btnReproducirL;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tblCanciones.setEditable(true);
        this.colOrden.setCellFactory(TextFieldTableCell.<CancionAux>forTableColumn());
        // TextFieldTableCell.
        //this.colOrden.setCellFactory(TableCell.<CancionAux>forTableColumn());
        this.colOrden.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<CancionAux, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<CancionAux, String> t) {
                int max = canciones.get(canciones.size() - 1).getOrden();
                int orden = Integer.parseInt(t.getNewValue());
                if (orden <= max) {
                    ((CancionAux) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())).setOrdens(t.getNewValue());
                    // System.out.println("POOOOOOOOOOOOOOOOOOOOOOOOP"+ this.getClass());
                    //System.out.println("Aaa" +t.getTableView().getSelectionModel().getSelectedItem().getOrdens());
                    CheckandSort();
                } else {
                    man.MostrarMensajeAdvertencia("El numero no esta dentro del rango posible");
                    cancionesOL.setAll(canciones);
                    tblCanciones.setItems(cancionesOL);
                }

            }
        });
    }

    public void Inicializar() {

        this.listas = (ArrayList<ListaR>) this.man.darListasRUsuario();
        this.listasOL.setAll(listas);
        this.cmbListasR.setItems(listasOL);

    }

    @FXML
    private void OnClickGuardar(ActionEvent event) {
        if (this.elegido && !this.txtNombre.getText().isEmpty() && !this.cmbListasR.getSelectionModel().isEmpty()) {

            
            int res = this.man.actualizarListaR(this.cmbListasR.getValue().getNombreLdr(), this.txtNombre.getText(), this.canciones);
            this.man.MostrarMensajeConfirmacion("Se actualizo la lista de reproduccion");
            /*
            this.cmbListasR.getSelectionModel().clearSelection();
            this.listas.clear();
            this.listasOL.clear();
            this.cmbListasR.setItems(null);
            
            this.listas = (ArrayList<ListaR>) this.man.darListasRUsuario();
            
            this.listasOL.setAll(listas);
            this.cmbListasR.setItems(listasOL);
            //this.Inicializar();
            */
            
            for (ListaR lista : listas) {
                if(lista.getListaRPK().getIdListar()==this.cmbListasR.getValue().getListaRPK().getIdListar()){
                    lista.setNombreLdr(this.txtNombre.getText());
                   // System.out.println("AAAAAAAAAAAAAAAAAA"+lista.getNombreLdr());
                }         
            }
            
            
            this.cmbListasR.getSelectionModel().clearSelection();
            this.listasOL.clear();
            this.cmbListasR.setItems(null);
            this.listasOL.setAll(this.listas);
            this.cmbListasR.setItems(listasOL);
            this.txtNombre.setText("");
            this.tblCanciones.setItems(null);

        } else {
            this.man.MostrarMensajeAdvertencia("Faltan Parametros o no se ha realizado alguna modificacion");
        }

    }

    @FXML
    private void OnClickElegir(ActionEvent event) {

        if (!this.cmbListasR.getSelectionModel().isEmpty()) {
            this.canciones = (ArrayList<CancionAux>) this.man.darCancionesDeListaRUsuario(this.cmbListasR.getValue().getNombreLdr());

            PropertyValueFactory<CancionAux, String> prop1 = new PropertyValueFactory<>("ordens");
            this.colOrden.setCellValueFactory(prop1);
            PropertyValueFactory<CancionAux, String> prop3 = new PropertyValueFactory<>("titulo_c");
            this.colTitulo.setCellValueFactory(prop3);
            PropertyValueFactory<CancionAux, String> prop2 = new PropertyValueFactory<>("titulo_alabum");
            this.colAlbum.setCellValueFactory(prop2);
            PropertyValueFactory<CancionAux, String> prop4 = new PropertyValueFactory<>("nombre_artistico");
            this.colInterprete.setCellValueFactory(prop4);

            this.cancionesOL.setAll(canciones);
            this.tblCanciones.setItems(cancionesOL);
            this.elegido = true;
            for (CancionAux cancione : canciones) {
                cancione.setOrdens("" + cancione.getOrden());
            }
            this.txtNombre.setText(this.cmbListasR.getValue().getNombreLdr());
        } else {
            this.man.MostrarMensajeAdvertencia("Debe elegir una lista de reproduccion");
        }
    }

    public void CheckandSort() {
        CancionAux cancion = this.tblCanciones.getSelectionModel().getSelectedItem();
        int orden = 0;
        int max = this.canciones.get(this.canciones.size() - 1).getOrden();

        //System.out.println("El orden maximo es" + max);
        try {
            // checking valid integer using parseInt() method

            orden = Integer.parseInt(cancion.getOrdens());
            //System.out.println("El orden deseado es" + orden);
            if ((orden > 0)) {
                if ((orden <= max)) {
                    for (int i = 0; i < this.canciones.size(); i++) {
                        if ((orden - 1) == i) {
                            this.canciones.remove(cancion.getOrden() - 1);
                            this.canciones.add(i, cancion);
                            //System.out.println(" KK" + i);
                        }
                    }
                    int j = 1;
                    for (CancionAux cancione : this.canciones) {
                        cancione.setOrden(j);
                        cancione.setOrdens("" + j);
                        j++;
                    }
                    this.cancionesOL.setAll(canciones);
                    this.tblCanciones.setItems(cancionesOL);

                    PropertyValueFactory<CancionAux, String> prop1 = new PropertyValueFactory<>("ordens");
                    this.colOrden.setCellValueFactory(prop1);
                    PropertyValueFactory<CancionAux, String> prop3 = new PropertyValueFactory<>("titulo_c");
                    this.colTitulo.setCellValueFactory(prop3);
                    PropertyValueFactory<CancionAux, String> prop2 = new PropertyValueFactory<>("titulo_alabum");
                    this.colAlbum.setCellValueFactory(prop2);
                    PropertyValueFactory<CancionAux, String> prop4 = new PropertyValueFactory<>("nombre_artistico");
                    this.colInterprete.setCellValueFactory(prop4);

                } else {
                    this.man.MostrarMensajeAdvertencia("Numero invalido");
                }
            } else {
                this.man.MostrarMensajeAdvertencia("Numero invalido");
                /*int j = 1;
                for (CancionAux cancione : this.canciones) {
                    cancione.setOrden(j);
                    cancione.setOrdens("" + j);
                    j++;
                }
                this.cancionesOL.setAll(canciones);
                this.tblCanciones.setItems(cancionesOL);

                PropertyValueFactory<CancionAux, String> prop1 = new PropertyValueFactory<>("ordens");
                this.colOrden.setCellValueFactory(prop1);
                PropertyValueFactory<CancionAux, String> prop3 = new PropertyValueFactory<>("titulo_c");
                this.colTitulo.setCellValueFactory(prop3);
                PropertyValueFactory<CancionAux, String> prop2 = new PropertyValueFactory<>("titulo_alabum");
                this.colAlbum.setCellValueFactory(prop2);
                PropertyValueFactory<CancionAux, String> prop4 = new PropertyValueFactory<>("nombre_artistico");
                this.colInterprete.setCellValueFactory(prop4);*/
            }
        } catch (NumberFormatException e) {
            this.man.MostrarMensajeAdvertencia("Numero no ingresado");

        }

    }

    public FacadeAgregar getMan() {
        return man;
    }

    public void setMan(FacadeAgregar man) {
        this.man = man;
    }

    public boolean isElegido() {
        return elegido;
    }

    public void setElegido(boolean elegido) {
        this.elegido = elegido;
    }

    @FXML
    private void OnClickReproducirrrrrrrr(ActionEvent event) {
        if (!this.cmbListasR.getSelectionModel().isEmpty() && this.elegido) {

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
            controlador.setCancionr(tblCanciones.getSelectionModel().getSelectedItem());

            boolean reac = this.man.existeReaccion(tblCanciones.getSelectionModel().getSelectedItem());

            controlador.setReaccion(reac);

            controlador.Inicializar();
            Reproducir.show();
        } else {
            this.man.MostrarMensajeAdvertencia("Debe realizar una busqueda y selecionar una cancion.");
        }
    }

}
