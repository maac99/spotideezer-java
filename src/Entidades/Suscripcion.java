/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author julia
 */
@Entity
@Table(name = "SUSCRIPCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Suscripcion.findAll", query = "SELECT s FROM Suscripcion s")
    , @NamedQuery(name = "Suscripcion.findBySusIdSuscripcion", query = "SELECT s FROM Suscripcion s WHERE s.susIdSuscripcion = :susIdSuscripcion")
    , @NamedQuery(name = "Suscripcion.findBySusFechaInicio", query = "SELECT s FROM Suscripcion s WHERE s.susFechaInicio = :susFechaInicio")
    , @NamedQuery(name = "Suscripcion.findBySusFechaUltimaRenovacion", query = "SELECT s FROM Suscripcion s WHERE s.susFechaUltimaRenovacion = :susFechaUltimaRenovacion")
    , @NamedQuery(name = "Suscripcion.findBySusTipoSus", query = "SELECT s FROM Suscripcion s WHERE s.susTipoSus = :susTipoSus")})
public class Suscripcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SUS_ID_SUSCRIPCION")
    private Long susIdSuscripcion;
    @Basic(optional = false)
    @Column(name = "SUS_FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date susFechaInicio;
    @Basic(optional = false)
    @Column(name = "SUS_FECHA_ULTIMA_RENOVACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date susFechaUltimaRenovacion;
    @Basic(optional = false)
    @Column(name = "SUS_TIPO_SUS")
    private String susTipoSus;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "suscripcion")
    private Familiar familiar;
    @JoinColumn(name = "USUARIO_NICKNAME", referencedColumnName = "NICKNAME")
    @OneToOne(optional = false)
    private Usuario usuarioNickname;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "suscripcion")
    private Individual individual;

    public Suscripcion() {
    }

    public Suscripcion(Long susIdSuscripcion) {
        this.susIdSuscripcion = susIdSuscripcion;
    }

    public Suscripcion(Long susIdSuscripcion, Date susFechaInicio, Date susFechaUltimaRenovacion, String susTipoSus) {
        this.susIdSuscripcion = susIdSuscripcion;
        this.susFechaInicio = susFechaInicio;
        this.susFechaUltimaRenovacion = susFechaUltimaRenovacion;
        this.susTipoSus = susTipoSus;
    }

    public Long getSusIdSuscripcion() {
        return susIdSuscripcion;
    }

    public void setSusIdSuscripcion(Long susIdSuscripcion) {
        this.susIdSuscripcion = susIdSuscripcion;
    }

    public Date getSusFechaInicio() {
        return susFechaInicio;
    }

    public void setSusFechaInicio(Date susFechaInicio) {
        this.susFechaInicio = susFechaInicio;
    }

    public Date getSusFechaUltimaRenovacion() {
        return susFechaUltimaRenovacion;
    }

    public void setSusFechaUltimaRenovacion(Date susFechaUltimaRenovacion) {
        this.susFechaUltimaRenovacion = susFechaUltimaRenovacion;
    }

    public String getSusTipoSus() {
        return susTipoSus;
    }

    public void setSusTipoSus(String susTipoSus) {
        this.susTipoSus = susTipoSus;
    }

    public Familiar getFamiliar() {
        return familiar;
    }

    public void setFamiliar(Familiar familiar) {
        this.familiar = familiar;
    }

    public Usuario getUsuarioNickname() {
        return usuarioNickname;
    }

    public void setUsuarioNickname(Usuario usuarioNickname) {
        this.usuarioNickname = usuarioNickname;
    }

    public Individual getIndividual() {
        return individual;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
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
        if (!(object instanceof Suscripcion)) {
            return false;
        }
        Suscripcion other = (Suscripcion) object;
        if ((this.susIdSuscripcion == null && other.susIdSuscripcion != null) || (this.susIdSuscripcion != null && !this.susIdSuscripcion.equals(other.susIdSuscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Suscripcion[ susIdSuscripcion=" + susIdSuscripcion + " ]";
    }
    
}
