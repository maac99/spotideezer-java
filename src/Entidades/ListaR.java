/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author julia
 */
@Entity
@Table(name = "LISTA_R")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListaR.findAll", query = "SELECT l FROM ListaR l")
    , @NamedQuery(name = "ListaR.findByIdListar", query = "SELECT l FROM ListaR l WHERE l.listaRPK.idListar = :idListar")
    , @NamedQuery(name = "ListaR.findByNombreLdr", query = "SELECT l FROM ListaR l WHERE l.nombreLdr = :nombreLdr")
    , @NamedQuery(name = "ListaR.findByTipo", query = "SELECT l FROM ListaR l WHERE l.tipo = :tipo")
    , @NamedQuery(name = "ListaR.findByUsuarioNickname", query = "SELECT l FROM ListaR l WHERE l.listaRPK.usuarioNickname = :usuarioNickname")})
public class ListaR implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ListaRPK listaRPK;
    @Basic(optional = false)
    @Column(name = "NOMBRE_LDR")
    private String nombreLdr;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "listaR")
    private List<Cancionxlistasr> cancionxlistasrList;
    @JoinColumn(name = "USUARIO_NICKNAME", referencedColumnName = "NICKNAME", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public ListaR() {
    }

    public ListaR(ListaRPK listaRPK) {
        this.listaRPK = listaRPK;
    }

    public ListaR(ListaRPK listaRPK, String nombreLdr, String tipo) {
        this.listaRPK = listaRPK;
        this.nombreLdr = nombreLdr;
        this.tipo = tipo;
    }

    public ListaR(long idListar, String usuarioNickname) {
        this.listaRPK = new ListaRPK(idListar, usuarioNickname);
    }

    public ListaRPK getListaRPK() {
        return listaRPK;
    }

    public void setListaRPK(ListaRPK listaRPK) {
        this.listaRPK = listaRPK;
    }

    public String getNombreLdr() {
        return nombreLdr;
    }

    public void setNombreLdr(String nombreLdr) {
        this.nombreLdr = nombreLdr;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public List<Cancionxlistasr> getCancionxlistasrList() {
        return cancionxlistasrList;
    }

    public void setCancionxlistasrList(List<Cancionxlistasr> cancionxlistasrList) {
        this.cancionxlistasrList = cancionxlistasrList;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (listaRPK != null ? listaRPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaR)) {
            return false;
        }
        ListaR other = (ListaR) object;
        if ((this.listaRPK == null && other.listaRPK != null) || (this.listaRPK != null && !this.listaRPK.equals(other.listaRPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombreLdr;
    }
    
}
