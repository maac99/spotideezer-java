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
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "GENERO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Genero.findAll", query = "SELECT g FROM Genero g")
    , @NamedQuery(name = "Genero.findByCodigoGenero", query = "SELECT g FROM Genero g WHERE g.codigoGenero = :codigoGenero")})
public class Genero implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CODIGO_GENERO")
    private Long codigoGenero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "generoCodigoGenero")
    private List<Cancion> cancionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "genero")
    private List<Generoxidioma> generoxidiomaList;

    public Genero() {
    }

    public Genero(Long codigoGenero) {
        this.codigoGenero = codigoGenero;
    }

    public Long getCodigoGenero() {
        return codigoGenero;
    }

    public void setCodigoGenero(Long codigoGenero) {
        this.codigoGenero = codigoGenero;
    }

    @XmlTransient
    public List<Cancion> getCancionList() {
        return cancionList;
    }

    public void setCancionList(List<Cancion> cancionList) {
        this.cancionList = cancionList;
    }

    @XmlTransient
    public List<Generoxidioma> getGeneroxidiomaList() {
        return generoxidiomaList;
    }

    public void setGeneroxidiomaList(List<Generoxidioma> generoxidiomaList) {
        this.generoxidiomaList = generoxidiomaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoGenero != null ? codigoGenero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Genero)) {
            return false;
        }
        Genero other = (Genero) object;
        if ((this.codigoGenero == null && other.codigoGenero != null) || (this.codigoGenero != null && !this.codigoGenero.equals(other.codigoGenero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Genero[ codigoGenero=" + codigoGenero + " ]";
    }
    
}
