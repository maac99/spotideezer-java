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
public class CxiPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CANCION_TITULO_C")
    private String cancionTituloC;
    @Basic(optional = false)
    @Column(name = "CANCION_ID_IN")
    private long cancionIdIn;
    @Basic(optional = false)
    @Column(name = "INTERPRETE_ID_IN")
    private long interpreteIdIn;

    public CxiPK() {
    }

    public CxiPK(String cancionTituloC, long cancionIdIn, long interpreteIdIn) {
        this.cancionTituloC = cancionTituloC;
        this.cancionIdIn = cancionIdIn;
        this.interpreteIdIn = interpreteIdIn;
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

    public long getInterpreteIdIn() {
        return interpreteIdIn;
    }

    public void setInterpreteIdIn(long interpreteIdIn) {
        this.interpreteIdIn = interpreteIdIn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cancionTituloC != null ? cancionTituloC.hashCode() : 0);
        hash += (int) cancionIdIn;
        hash += (int) interpreteIdIn;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CxiPK)) {
            return false;
        }
        CxiPK other = (CxiPK) object;
        if ((this.cancionTituloC == null && other.cancionTituloC != null) || (this.cancionTituloC != null && !this.cancionTituloC.equals(other.cancionTituloC))) {
            return false;
        }
        if (this.cancionIdIn != other.cancionIdIn) {
            return false;
        }
        if (this.interpreteIdIn != other.interpreteIdIn) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.CxiPK[ cancionTituloC=" + cancionTituloC + ", cancionIdIn=" + cancionIdIn + ", interpreteIdIn=" + interpreteIdIn + " ]";
    }
    
}
