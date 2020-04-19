/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author julia
 */
@Entity
@Table(name = "CANCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cancion.findAll", query = "SELECT c FROM Cancion c")
    , @NamedQuery(name = "Cancion.findByTituloC", query = "SELECT c FROM Cancion c WHERE c.cancionPK.tituloC = :tituloC")
    , @NamedQuery(name = "Cancion.findByDuracion", query = "SELECT c FROM Cancion c WHERE c.duracion = :duracion")
    , @NamedQuery(name = "Cancion.findByInterpreteIdIn", query = "SELECT c FROM Cancion c WHERE c.cancionPK.interpreteIdIn = :interpreteIdIn")})
public class Cancion implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cancion")
    private List<Cxi> cxiList;

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CancionPK cancionPK;
    @Basic(optional = false)
    @Column(name = "DURACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date duracion;
    @JoinTable(name = "CXI", joinColumns = {
        @JoinColumn(name = "CANCION_TITULO_C", referencedColumnName = "TITULO_C")
        , @JoinColumn(name = "CANCION_ID_IN", referencedColumnName = "INTERPRETE_ID_IN")}, inverseJoinColumns = {
        @JoinColumn(name = "INTERPRETE_ID_IN", referencedColumnName = "INTERPRETE_ID_IN")})
    @ManyToMany
    private List<Interprete> interpreteList;
    @JoinColumn(name = "ALBUM_ID_ALBUM", referencedColumnName = "ID_ALBUM")
    @ManyToOne(optional = false)
    private Album albumIdAlbum;
    @JoinColumn(name = "GENERO_CODIGO_GENERO", referencedColumnName = "CODIGO_GENERO")
    @ManyToOne(optional = false)
    private Genero generoCodigoGenero;
    @JoinColumn(name = "INTERPRETE_ID_IN", referencedColumnName = "INTERPRETE_ID_IN", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Interprete interprete;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cancion")
    private List<Registro> registroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cancion")
    private List<Cancionxlistasr> cancionxlistasrList;

    public Cancion() {
    }

    public Cancion(CancionPK cancionPK) {
        this.cancionPK = cancionPK;
    }

    public Cancion(CancionPK cancionPK, Date duracion) {
        this.cancionPK = cancionPK;
        this.duracion = duracion;
    }

    public Cancion(String tituloC, long interpreteIdIn) {
        this.cancionPK = new CancionPK(tituloC, interpreteIdIn);
    }

    public CancionPK getCancionPK() {
        return cancionPK;
    }

    public void setCancionPK(CancionPK cancionPK) {
        this.cancionPK = cancionPK;
    }

    public Date getDuracion() {
        return duracion;
    }

    public void setDuracion(Date duracion) {
        this.duracion = duracion;
    }

    @XmlTransient
    public List<Interprete> getInterpreteList() {
        return interpreteList;
    }

    public void setInterpreteList(List<Interprete> interpreteList) {
        this.interpreteList = interpreteList;
    }

    public Album getAlbumIdAlbum() {
        return albumIdAlbum;
    }

    public void setAlbumIdAlbum(Album albumIdAlbum) {
        this.albumIdAlbum = albumIdAlbum;
    }

    public Genero getGeneroCodigoGenero() {
        return generoCodigoGenero;
    }

    public void setGeneroCodigoGenero(Genero generoCodigoGenero) {
        this.generoCodigoGenero = generoCodigoGenero;
    }

    public Interprete getInterprete() {
        return interprete;
    }

    public void setInterprete(Interprete interprete) {
        this.interprete = interprete;
    }

    @XmlTransient
    public List<Registro> getRegistroList() {
        return registroList;
    }

    public void setRegistroList(List<Registro> registroList) {
        this.registroList = registroList;
    }

    @XmlTransient
    public List<Cancionxlistasr> getCancionxlistasrList() {
        return cancionxlistasrList;
    }

    public void setCancionxlistasrList(List<Cancionxlistasr> cancionxlistasrList) {
        this.cancionxlistasrList = cancionxlistasrList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cancionPK != null ? cancionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cancion)) {
            return false;
        }
        Cancion other = (Cancion) object;
        if ((this.cancionPK == null && other.cancionPK != null) || (this.cancionPK != null && !this.cancionPK.equals(other.cancionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Cancion[ cancionPK=" + cancionPK + " ]";
    }

    @XmlTransient
    public List<Cxi> getCxiList() {
        return cxiList;
    }

    public void setCxiList(List<Cxi> cxiList) {
        this.cxiList = cxiList;
    }
    
}
