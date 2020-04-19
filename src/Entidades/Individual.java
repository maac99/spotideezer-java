/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author julia
 */
@Entity
@Table(name = "INDIVIDUAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Individual.findAll", query = "SELECT i FROM Individual i")
    , @NamedQuery(name = "Individual.findBySusIdSuscripcion", query = "SELECT i FROM Individual i WHERE i.susIdSuscripcion = :susIdSuscripcion")})
public class Individual implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SUS_ID_SUSCRIPCION")
    private Long susIdSuscripcion;
    @JoinColumn(name = "SUS_ID_SUSCRIPCION", referencedColumnName = "SUS_ID_SUSCRIPCION", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Suscripcion suscripcion;

    public Individual() {
    }

    public Individual(Long susIdSuscripcion) {
        this.susIdSuscripcion = susIdSuscripcion;
    }

    public Long getSusIdSuscripcion() {
        return susIdSuscripcion;
    }

    public void setSusIdSuscripcion(Long susIdSuscripcion) {
        this.susIdSuscripcion = susIdSuscripcion;
    }

    public Suscripcion getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(Suscripcion suscripcion) {
        this.suscripcion = suscripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (susIdSuscripcion != null ? susIdSuscripcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Individual)) {
            return false;
        }
        Individual other = (Individual) object;
        if ((this.susIdSuscripcion == null && other.susIdSuscripcion != null) || (this.susIdSuscripcion != null && !this.susIdSuscripcion.equals(other.susIdSuscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Individual[ susIdSuscripcion=" + susIdSuscripcion + " ]";
    }
    
}
