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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
@Table(name = "INTERPRETE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Interprete.findAll", query = "SELECT i FROM Interprete i")
    , @NamedQuery(name = "Interprete.findByInterpreteIdIn", query = "SELECT i FROM Interprete i WHERE i.interpreteIdIn = :interpreteIdIn")
    , @NamedQuery(name = "Interprete.findByNombreReal", query = "SELECT i FROM Interprete i WHERE i.nombreReal = :nombreReal")
    , @NamedQuery(name = "Interprete.findByNombreArtistico", query = "SELECT i FROM Interprete i WHERE i.nombreArtistico = :nombreArtistico")})
public class Interprete implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interprete")
    private List<Cxi> cxiList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "INTERPRETE_ID_IN")
    private Long interpreteIdIn;
    @Basic(optional = false)
    @Column(name = "NOMBRE_REAL")
    private String nombreReal;
    @Column(name = "NOMBRE_ARTISTICO")
    private String nombreArtistico;
    @ManyToMany(mappedBy = "interpreteList")
    private List<Cancion> cancionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interprete")
    private List<Cancion> cancionList1;
    @JoinColumn(name = "PAIS_NOMBRE_PAIS", referencedColumnName = "NOMBRE_PAIS")
    @ManyToOne(optional = false)
    private Pais paisNombrePais;

    public Interprete() {
    }

    public Interprete(Long interpreteIdIn) {
        this.interpreteIdIn = interpreteIdIn;
    }

    public Interprete(Long interpreteIdIn, String nombreReal) {
        this.interpreteIdIn = interpreteIdIn;
        this.nombreReal = nombreReal;
    }

    public Long getInterpreteIdIn() {
        return interpreteIdIn;
    }

    public void setInterpreteIdIn(Long interpreteIdIn) {
        this.interpreteIdIn = interpreteIdIn;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }

    public String getNombreArtistico() {
        return nombreArtistico;
    }

    public void setNombreArtistico(String nombreArtistico) {
        this.nombreArtistico = nombreArtistico;
    }

    @XmlTransient
    public List<Cancion> getCancionList() {
        return cancionList;
    }

    public void setCancionList(List<Cancion> cancionList) {
        this.cancionList = cancionList;
    }

    @XmlTransient
    public List<Cancion> getCancionList1() {
        return cancionList1;
    }

    public void setCancionList1(List<Cancion> cancionList1) {
        this.cancionList1 = cancionList1;
    }

    public Pais getPaisNombrePais() {
        return paisNombrePais;
    }

    public void setPaisNombrePais(Pais paisNombrePais) {
        this.paisNombrePais = paisNombrePais;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (interpreteIdIn != null ? interpreteIdIn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Interprete)) {
            return false;
        }
        Interprete other = (Interprete) object;
        if ((this.interpreteIdIn == null && other.interpreteIdIn != null) || (this.interpreteIdIn != null && !this.interpreteIdIn.equals(other.interpreteIdIn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombreArtistico;
    }

    @XmlTransient
    public List<Cxi> getCxiList() {
        return cxiList;
    }

    public void setCxiList(List<Cxi> cxiList) {
        this.cxiList = cxiList;
    }
    
}
