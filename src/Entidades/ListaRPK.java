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
public class ListaRPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "ID_LISTAR")
    private long idListar;
    @Basic(optional = false)
    @Column(name = "USUARIO_NICKNAME")
    private String usuarioNickname;

    public ListaRPK() {
    }

    public ListaRPK(long idListar, String usuarioNickname) {
        this.idListar = idListar;
        this.usuarioNickname = usuarioNickname;
    }

    public long getIdListar() {
        return idListar;
    }

    public void setIdListar(long idListar) {
        this.idListar = idListar;
    }

    public String getUsuarioNickname() {
        return usuarioNickname;
    }

    public void setUsuarioNickname(String usuarioNickname) {
        this.usuarioNickname = usuarioNickname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idListar;
        hash += (usuarioNickname != null ? usuarioNickname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaRPK)) {
            return false;
        }
        ListaRPK other = (ListaRPK) object;
        if (this.idListar != other.idListar) {
            return false;
        }
        if ((this.usuarioNickname == null && other.usuarioNickname != null) || (this.usuarioNickname != null && !this.usuarioNickname.equals(other.usuarioNickname))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.ListaRPK[ idListar=" + idListar + ", usuarioNickname=" + usuarioNickname + " ]";
    }
    
}
