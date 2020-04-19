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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
@Table(name = "REGISTRO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registro.findAll", query = "SELECT r FROM Registro r")
    , @NamedQuery(name = "Registro.findByCancionTituloC", query = "SELECT r FROM Registro r WHERE r.registroPK.cancionTituloC = :cancionTituloC")
    , @NamedQuery(name = "Registro.findByCancionInterpreteIdIn", query = "SELECT r FROM Registro r WHERE r.registroPK.cancionInterpreteIdIn = :cancionInterpreteIdIn")
    , @NamedQuery(name = "Registro.findByUsuarioNickname", query = "SELECT r FROM Registro r WHERE r.registroPK.usuarioNickname = :usuarioNickname")
    , @NamedQuery(name = "Registro.findByTipo", query = "SELECT r FROM Registro r WHERE r.registroPK.tipo = :tipo")
    , @NamedQuery(name = "Registro.findByFechahora", query = "SELECT r FROM Registro r WHERE r.fechahora = :fechahora")})
public class Registro implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RegistroPK registroPK;
    @Basic(optional = false)
    @Column(name = "FECHAHORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahora;
    @JoinColumns({
        @JoinColumn(name = "CANCION_TITULO_C", referencedColumnName = "TITULO_C", insertable = false, updatable = false)
        , @JoinColumn(name = "CANCION_INTERPRETE_ID_IN", referencedColumnName = "INTERPRETE_ID_IN", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Cancion cancion;
    @JoinColumn(name = "USUARIO_NICKNAME", referencedColumnName = "NICKNAME", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Registro() {
    }

    public Registro(RegistroPK registroPK) {
        this.registroPK = registroPK;
    }

    public Registro(RegistroPK registroPK, Date fechahora) {
        this.registroPK = registroPK;
        this.fechahora = fechahora;
    }

    public Registro(String cancionTituloC, long cancionInterpreteIdIn, String usuarioNickname, String tipo) {
        this.registroPK = new RegistroPK(cancionTituloC, cancionInterpreteIdIn, usuarioNickname, tipo);
    }

    public RegistroPK getRegistroPK() {
        return registroPK;
    }

    public void setRegistroPK(RegistroPK registroPK) {
        this.registroPK = registroPK;
    }

    public Date getFechahora() {
        return fechahora;
    }

    public void setFechahora(Date fechahora) {
        this.fechahora = fechahora;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
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
        hash += (registroPK != null ? registroPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registro)) {
            return false;
        }
        Registro other = (Registro) object;
        if ((this.registroPK == null && other.registroPK != null) || (this.registroPK != null && !this.registroPK.equals(other.registroPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Registro[ registroPK=" + registroPK + " ]";
    }
    
}
