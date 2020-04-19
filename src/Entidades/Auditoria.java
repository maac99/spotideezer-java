/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author julia
 */
@Entity
@Table(name = "AUDITORIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Auditoria.findAll", query = "SELECT a FROM Auditoria a")
    , @NamedQuery(name = "Auditoria.findByEntidadModificada", query = "SELECT a FROM Auditoria a WHERE a.entidadModificada = :entidadModificada")
    , @NamedQuery(name = "Auditoria.findByIdentificador", query = "SELECT a FROM Auditoria a WHERE a.identificador = :identificador")
    , @NamedQuery(name = "Auditoria.findByOperacion", query = "SELECT a FROM Auditoria a WHERE a.operacion = :operacion")
    , @NamedQuery(name = "Auditoria.findByFechaHora", query = "SELECT a FROM Auditoria a WHERE a.fechaHora = :fechaHora")})
public class Auditoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "ENTIDAD_MODIFICADA")
    private String entidadModificada;
    @Id
    @Basic(optional = false)
    @Column(name = "IDENTIFICADOR")
    private String identificador;
    @Basic(optional = false)
    @Column(name = "OPERACION")
    private String operacion;
    @Basic(optional = false)
    @Column(name = "FECHA_HORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHora;

    public Auditoria() {
    }

    public Auditoria(String identificador) {
        this.identificador = identificador;
    }

    public Auditoria(String identificador, String entidadModificada, String operacion, Date fechaHora) {
        this.identificador = identificador;
        this.entidadModificada = entidadModificada;
        this.operacion = operacion;
        this.fechaHora = fechaHora;
    }

    public String getEntidadModificada() {
        return entidadModificada;
    }

    public void setEntidadModificada(String entidadModificada) {
        this.entidadModificada = entidadModificada;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identificador != null ? identificador.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Auditoria)) {
            return false;
        }
        Auditoria other = (Auditoria) object;
        if ((this.identificador == null && other.identificador != null) || (this.identificador != null && !this.identificador.equals(other.identificador))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Auditoria[ identificador=" + identificador + " ]";
    }
    
}
