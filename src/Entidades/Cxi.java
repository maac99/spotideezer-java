/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author julia
 */
@Entity
@Table(name = "CXI")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cxi.findAll", query = "SELECT c FROM Cxi c")
    , @NamedQuery(name = "Cxi.findByCancionTituloC", query = "SELECT c FROM Cxi c WHERE c.cxiPK.cancionTituloC = :cancionTituloC")
    , @NamedQuery(name = "Cxi.findByCancionIdIn", query = "SELECT c FROM Cxi c WHERE c.cxiPK.cancionIdIn = :cancionIdIn")
    , @NamedQuery(name = "Cxi.findByInterpreteIdIn", query = "SELECT c FROM Cxi c WHERE c.cxiPK.interpreteIdIn = :interpreteIdIn")
    , @NamedQuery(name = "Cxi.findByRoll", query = "SELECT c FROM Cxi c WHERE c.roll = :roll")})
public class Cxi implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CxiPK cxiPK;
    @Basic(optional = false)
    @Column(name = "ROLL")
    private String roll;
    @JoinColumns({
        @JoinColumn(name = "CANCION_TITULO_C", referencedColumnName = "TITULO_C", insertable = false, updatable = false)
        , @JoinColumn(name = "CANCION_ID_IN", referencedColumnName = "INTERPRETE_ID_IN", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Cancion cancion;
    @JoinColumn(name = "INTERPRETE_ID_IN", referencedColumnName = "INTERPRETE_ID_IN", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Interprete interprete;

    public Cxi() {
    }

    public Cxi(CxiPK cxiPK) {
        this.cxiPK = cxiPK;
    }

    public Cxi(CxiPK cxiPK, String roll) {
        this.cxiPK = cxiPK;
        this.roll = roll;
    }

    public Cxi(String cancionTituloC, long cancionIdIn, long interpreteIdIn) {
        this.cxiPK = new CxiPK(cancionTituloC, cancionIdIn, interpreteIdIn);
    }

    public CxiPK getCxiPK() {
        return cxiPK;
    }

    public void setCxiPK(CxiPK cxiPK) {
        this.cxiPK = cxiPK;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }

    public Interprete getInterprete() {
        return interprete;
    }

    public void setInterprete(Interprete interprete) {
        this.interprete = interprete;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cxiPK != null ? cxiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cxi)) {
            return false;
        }
        Cxi other = (Cxi) object;
        if ((this.cxiPK == null && other.cxiPK != null) || (this.cxiPK != null && !this.cxiPK.equals(other.cxiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Cxi[ cxiPK=" + cxiPK + " ]";
    }
    
}
