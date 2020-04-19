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
@Table(name = "IDIOMA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Idioma.findAll", query = "SELECT i FROM Idioma i")
    , @NamedQuery(name = "Idioma.findByNombreIdioma", query = "SELECT i FROM Idioma i WHERE i.nombreIdioma = :nombreIdioma")})
public class Idioma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NOMBRE_IDIOMA")
    private String nombreIdioma;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idioma")
    private List<Generoxidioma> generoxidiomaList;

    public Idioma() {
    }

    public Idioma(String nombreIdioma) {
        this.nombreIdioma = nombreIdioma;
    }

    public String getNombreIdioma() {
        return nombreIdioma;
    }

    public void setNombreIdioma(String nombreIdioma) {
        this.nombreIdioma = nombreIdioma;
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
        hash += (nombreIdioma != null ? nombreIdioma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Idioma)) {
            return false;
        }
        Idioma other = (Idioma) object;
        if ((this.nombreIdioma == null && other.nombreIdioma != null) || (this.nombreIdioma != null && !this.nombreIdioma.equals(other.nombreIdioma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Idioma[ nombreIdioma=" + nombreIdioma + " ]";
    }
    
}
