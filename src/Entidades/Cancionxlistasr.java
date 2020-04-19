/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author julia
 */
@Entity
@Table(name = "CANCIONXLISTASR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cancionxlistasr.findAll", query = "SELECT c FROM Cancionxlistasr c")
    , @NamedQuery(name = "Cancionxlistasr.findByCancionTituloC", query = "SELECT c FROM Cancionxlistasr c WHERE c.cancionxlistasrPK.cancionTituloC = :cancionTituloC")
    , @NamedQuery(name = "Cancionxlistasr.findByCancionIdIn", query = "SELECT c FROM Cancionxlistasr c WHERE c.cancionxlistasrPK.cancionIdIn = :cancionIdIn")
    , @NamedQuery(name = "Cancionxlistasr.findByListaRIdListar", query = "SELECT c FROM Cancionxlistasr c WHERE c.cancionxlistasrPK.listaRIdListar = :listaRIdListar")
    , @NamedQuery(name = "Cancionxlistasr.findByListaRNickname", query = "SELECT c FROM Cancionxlistasr c WHERE c.cancionxlistasrPK.listaRNickname = :listaRNickname")
    , @NamedQuery(name = "Cancionxlistasr.findByOrden", query = "SELECT c FROM Cancionxlistasr c WHERE c.orden = :orden")})
public class Cancionxlistasr implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CancionxlistasrPK cancionxlistasrPK;
    @Basic(optional = false)
    @Column(name = "ORDEN")
    private BigInteger orden;
    @JoinColumns({
        @JoinColumn(name = "CANCION_TITULO_C", referencedColumnName = "TITULO_C", insertable = false, updatable = false)
        , @JoinColumn(name = "CANCION_ID_IN", referencedColumnName = "INTERPRETE_ID_IN", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Cancion cancion;
    @JoinColumns({
        @JoinColumn(name = "LISTA_R_ID_LISTAR", referencedColumnName = "ID_LISTAR", insertable = false, updatable = false)
        , @JoinColumn(name = "LISTA_R_NICKNAME", referencedColumnName = "USUARIO_NICKNAME", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private ListaR listaR;

    public Cancionxlistasr() {
    }

    public Cancionxlistasr(CancionxlistasrPK cancionxlistasrPK) {
        this.cancionxlistasrPK = cancionxlistasrPK;
    }

    public Cancionxlistasr(CancionxlistasrPK cancionxlistasrPK, BigInteger orden) {
        this.cancionxlistasrPK = cancionxlistasrPK;
        this.orden = orden;
    }

    public Cancionxlistasr(String cancionTituloC, long cancionIdIn, long listaRIdListar, String listaRNickname) {
        this.cancionxlistasrPK = new CancionxlistasrPK(cancionTituloC, cancionIdIn, listaRIdListar, listaRNickname);
    }

    public CancionxlistasrPK getCancionxlistasrPK() {
        return cancionxlistasrPK;
    }

    public void setCancionxlistasrPK(CancionxlistasrPK cancionxlistasrPK) {
        this.cancionxlistasrPK = cancionxlistasrPK;
    }

    public BigInteger getOrden() {
        return orden;
    }

    public void setOrden(BigInteger orden) {
        this.orden = orden;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }

    public ListaR getListaR() {
        return listaR;
    }

    public void setListaR(ListaR listaR) {
        this.listaR = listaR;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cancionxlistasrPK != null ? cancionxlistasrPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cancionxlistasr)) {
            return false;
        }
        Cancionxlistasr other = (Cancionxlistasr) object;
        if ((this.cancionxlistasrPK == null && other.cancionxlistasrPK != null) || (this.cancionxlistasrPK != null && !this.cancionxlistasrPK.equals(other.cancionxlistasrPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Cancionxlistasr[ cancionxlistasrPK=" + cancionxlistasrPK + " ]";
    }
    
}
