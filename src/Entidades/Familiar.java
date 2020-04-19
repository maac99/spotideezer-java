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
@Table(name = "FAMILIAR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Familiar.findAll", query = "SELECT f FROM Familiar f")
    , @NamedQuery(name = "Familiar.findBySusIdSuscripcion", query = "SELECT f FROM Familiar f WHERE f.susIdSuscripcion = :susIdSuscripcion")})
public class Familiar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SUS_ID_SUSCRIPCION")
    private Long susIdSuscripcion;
    @JoinColumn(name = "SUS_ID_SUSCRIPCION", referencedColumnName = "SUS_ID_SUSCRIPCION", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Suscripcion suscripcion;
    @JoinColumn(name = "USUARIO_NICKNAME3", referencedColumnName = "NICKNAME")
    @OneToOne(optional = false)
    private Usuario usuarioNickname3;
    @JoinColumn(name = "USUARIO_NICKNAME2", referencedColumnName = "NICKNAME")
    @OneToOne(optional = false)
    private Usuario usuarioNickname2;
    @JoinColumn(name = "USUARIO_NICKNAME", referencedColumnName = "NICKNAME")
    @OneToOne(optional = false)
    private Usuario usuarioNickname;
    @JoinColumn(name = "USUARIO_NICKNAME1", referencedColumnName = "NICKNAME")
    @OneToOne(optional = false)
    private Usuario usuarioNickname1;

    public Familiar() {
    }

    public Familiar(Long susIdSuscripcion) {
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

    public Usuario getUsuarioNickname3() {
        return usuarioNickname3;
    }

    public void setUsuarioNickname3(Usuario usuarioNickname3) {
        this.usuarioNickname3 = usuarioNickname3;
    }

    public Usuario getUsuarioNickname2() {
        return usuarioNickname2;
    }

    public void setUsuarioNickname2(Usuario usuarioNickname2) {
        this.usuarioNickname2 = usuarioNickname2;
    }

    public Usuario getUsuarioNickname() {
        return usuarioNickname;
    }

    public void setUsuarioNickname(Usuario usuarioNickname) {
        this.usuarioNickname = usuarioNickname;
    }

    public Usuario getUsuarioNickname1() {
        return usuarioNickname1;
    }

    public void setUsuarioNickname1(Usuario usuarioNickname1) {
        this.usuarioNickname1 = usuarioNickname1;
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
        if (!(object instanceof Familiar)) {
            return false;
        }
        Familiar other = (Familiar) object;
        if ((this.susIdSuscripcion == null && other.susIdSuscripcion != null) || (this.susIdSuscripcion != null && !this.susIdSuscripcion.equals(other.susIdSuscripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Familiar[ susIdSuscripcion=" + susIdSuscripcion + " ]";
    }
    
}
