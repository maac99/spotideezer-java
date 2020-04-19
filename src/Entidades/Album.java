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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "ALBUM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Album.findAll", query = "SELECT a FROM Album a")
    , @NamedQuery(name = "Album.findByIdAlbum", query = "SELECT a FROM Album a WHERE a.idAlbum = :idAlbum")
    , @NamedQuery(name = "Album.findByTituloAlbum", query = "SELECT a FROM Album a WHERE a.tituloAlbum = :tituloAlbum")
    , @NamedQuery(name = "Album.findByFechaLanzamiento", query = "SELECT a FROM Album a WHERE a.fechaLanzamiento = :fechaLanzamiento")
    , @NamedQuery(name = "Album.findByTipo", query = "SELECT a FROM Album a WHERE a.tipo = :tipo")})
public class Album implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ALBUM")
    private Long idAlbum;
    @Basic(optional = false)
    @Column(name = "TITULO_ALBUM")
    private String tituloAlbum;
    @Column(name = "FECHA_LANZAMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLanzamiento;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "albumIdAlbum")
    private List<Cancion> cancionList;
    @JoinColumn(name = "EMPRESA_NOMBRE_EMPRESA", referencedColumnName = "NOMBRE_EMPRESA")
    @ManyToOne(optional = false)
    private Empresa empresaNombreEmpresa;

    public Album() {
    }

    public Album(Long idAlbum) {
        this.idAlbum = idAlbum;
    }

    public Album(Long idAlbum, String tituloAlbum, String tipo) {
        this.idAlbum = idAlbum;
        this.tituloAlbum = tituloAlbum;
        this.tipo = tipo;
    }

    public Long getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Long idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getTituloAlbum() {
        return tituloAlbum;
    }

    public void setTituloAlbum(String tituloAlbum) {
        this.tituloAlbum = tituloAlbum;
    }

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public List<Cancion> getCancionList() {
        return cancionList;
    }

    public void setCancionList(List<Cancion> cancionList) {
        this.cancionList = cancionList;
    }

    public Empresa getEmpresaNombreEmpresa() {
        return empresaNombreEmpresa;
    }

    public void setEmpresaNombreEmpresa(Empresa empresaNombreEmpresa) {
        this.empresaNombreEmpresa = empresaNombreEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlbum != null ? idAlbum.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Album)) {
            return false;
        }
        Album other = (Album) object;
        if ((this.idAlbum == null && other.idAlbum != null) || (this.idAlbum != null && !this.idAlbum.equals(other.idAlbum))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Album[ idAlbum=" + idAlbum + " ]";
    }
    
}
