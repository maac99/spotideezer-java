/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author julia
 */
@Embeddable
public class RegistroPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CANCION_TITULO_C")
    private String cancionTituloC;
    @Basic(optional = false)
    @Column(name = "CANCION_INTERPRETE_ID_IN")
    private long cancionInterpreteIdIn;
    @Basic(optional = false)
    @Column(name = "USUARIO_NICKNAME")
    private String usuarioNickname;
    @Basic(optional = false)
    @Column(name = "TIPO")
    private String tipo;

    public RegistroPK() {
    }

    public RegistroPK(String cancionTituloC, long cancionInterpreteIdIn, String usuarioNickname, String tipo) {
        this.cancionTituloC = cancionTituloC;
        this.cancionInterpreteIdIn = cancionInterpreteIdIn;
        this.usuarioNickname = usuarioNickname;
        this.tipo = tipo;
    }

    public String getCancionTituloC() {
        return cancionTituloC;
    }

    public void setCancionTituloC(String cancionTituloC) {
        this.cancionTituloC = cancionTituloC;
    }

    public long getCancionInterpreteIdIn() {
        return cancionInterpreteIdIn;
    }

    public void setCancionInterpreteIdIn(long cancionInterpreteIdIn) {
        this.cancionInterpreteIdIn = cancionInterpreteIdIn;
    }

    public String getUsuarioNickname() {
        return usuarioNickname;
    }

    public void setUsuarioNickname(String usuarioNickname) {
        this.usuarioNickname = usuarioNickname;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cancionTituloC != null ? cancionTituloC.hashCode() : 0);
        hash += (int) cancionInterpreteIdIn;
        hash += (usuarioNickname != null ? usuarioNickname.hashCode() : 0);
        hash += (tipo != null ? tipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RegistroPK)) {
            return false;
        }
        RegistroPK other = (RegistroPK) object;
        if ((this.cancionTituloC == null && other.cancionTituloC != null) || (this.cancionTituloC != null && !this.cancionTituloC.equals(other.cancionTituloC))) {
            return false;
        }
        if (this.cancionInterpreteIdIn != other.cancionInterpreteIdIn) {
            return false;
        }
        if ((this.usuarioNickname == null && other.usuarioNickname != null) || (this.usuarioNickname != null && !this.usuarioNickname.equals(other.usuarioNickname))) {
            return false;
        }
        if ((this.tipo == null && other.tipo != null) || (this.tipo != null && !this.tipo.equals(other.tipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.RegistroPK[ cancionTituloC=" + cancionTituloC + ", cancionInterpreteIdIn=" + cancionInterpreteIdIn + ", usuarioNickname=" + usuarioNickname + ", tipo=" + tipo + " ]";
    }
    
}
