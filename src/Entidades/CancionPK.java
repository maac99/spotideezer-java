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
public class CancionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "TITULO_C")
    private String tituloC;
    @Basic(optional = false)
    @Column(name = "INTERPRETE_ID_IN")
    private long interpreteIdIn;

    public CancionPK() {
    }

    public CancionPK(String tituloC, long interpreteIdIn) {
        this.tituloC = tituloC;
        this.interpreteIdIn = interpreteIdIn;
    }

    public String getTituloC() {
        return tituloC;
    }

    public void setTituloC(String tituloC) {
        this.tituloC = tituloC;
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
        hash += (tituloC != null ? tituloC.hashCode() : 0);
        hash += (int) interpreteIdIn;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CancionPK)) {
            return false;
        }
        CancionPK other = (CancionPK) object;
        if ((this.tituloC == null && other.tituloC != null) || (this.tituloC != null && !this.tituloC.equals(other.tituloC))) {
            return false;
        }
        if (this.interpreteIdIn != other.interpreteIdIn) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.tituloC;
    }
    
}
