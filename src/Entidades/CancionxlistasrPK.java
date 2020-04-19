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
public class CancionxlistasrPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CANCION_TITULO_C")
    private String cancionTituloC;
    @Basic(optional = false)
    @Column(name = "CANCION_ID_IN")
    private long cancionIdIn;
    @Basic(optional = false)
    @Column(name = "LISTA_R_ID_LISTAR")
    private long listaRIdListar;
    @Basic(optional = false)
    @Column(name = "LISTA_R_NICKNAME")
    private String listaRNickname;

    public CancionxlistasrPK() {
    }

    public CancionxlistasrPK(String cancionTituloC, long cancionIdIn, long listaRIdListar, String listaRNickname) {
        this.cancionTituloC = cancionTituloC;
        this.cancionIdIn = cancionIdIn;
        this.listaRIdListar = listaRIdListar;
        this.listaRNickname = listaRNickname;
    }

    public String getCancionTituloC() {
        return cancionTituloC;
    }

    public void setCancionTituloC(String cancionTituloC) {
        this.cancionTituloC = cancionTituloC;
    }

    public long getCancionIdIn() {
        return cancionIdIn;
    }

    public void setCancionIdIn(long cancionIdIn) {
        this.cancionIdIn = cancionIdIn;
    }

    public long getListaRIdListar() {
        return listaRIdListar;
    }

    public void setListaRIdListar(long listaRIdListar) {
        this.listaRIdListar = listaRIdListar;
    }

    public String getListaRNickname() {
        return listaRNickname;
    }

    public void setListaRNickname(String listaRNickname) {
        this.listaRNickname = listaRNickname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cancionTituloC != null ? cancionTituloC.hashCode() : 0);
        hash += (int) cancionIdIn;
        hash += (int) listaRIdListar;
        hash += (listaRNickname != null ? listaRNickname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CancionxlistasrPK)) {
            return false;
        }
        CancionxlistasrPK other = (CancionxlistasrPK) object;
        if ((this.cancionTituloC == null && other.cancionTituloC != null) || (this.cancionTituloC != null && !this.cancionTituloC.equals(other.cancionTituloC))) {
            return false;
        }
        if (this.cancionIdIn != other.cancionIdIn) {
            return false;
        }
        if (this.listaRIdListar != other.listaRIdListar) {
            return false;
        }
        if ((this.listaRNickname == null && other.listaRNickname != null) || (this.listaRNickname != null && !this.listaRNickname.equals(other.listaRNickname))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.CancionxlistasrPK[ cancionTituloC=" + cancionTituloC + ", cancionIdIn=" + cancionIdIn + ", listaRIdListar=" + listaRIdListar + ", listaRNickname=" + listaRNickname + " ]";
    }
    
}
