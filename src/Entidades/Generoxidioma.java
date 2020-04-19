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
@Table(name = "GENEROXIDIOMA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Generoxidioma.findAll", query = "SELECT g FROM Generoxidioma g")
    , @NamedQuery(name = "Generoxidioma.findByGeneroCodigoGenero", query = "SELECT g FROM Generoxidioma g WHERE g.generoxidiomaPK.generoCodigoGenero = :generoCodigoGenero")
    , @NamedQuery(name = "Generoxidioma.findByIdiomaNombreIdioma", query = "SELECT g FROM Generoxidioma g WHERE g.generoxidiomaPK.idiomaNombreIdioma = :idiomaNombreIdioma")
    , @NamedQuery(name = "Generoxidioma.findByAlias", query = "SELECT g FROM Generoxidioma g WHERE g.alias = :alias")})
public class Generoxidioma implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GeneroxidiomaPK generoxidiomaPK;
    @Basic(optional = false)
    @Column(name = "ALIAS")
    private String alias;
    @JoinColumn(name = "GENERO_CODIGO_GENERO", referencedColumnName = "CODIGO_GENERO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Genero genero;
    @JoinColumn(name = "IDIOMA_NOMBRE_IDIOMA", referencedColumnName = "NOMBRE_IDIOMA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Idioma idioma;

    public Generoxidioma() {
    }

    public Generoxidioma(GeneroxidiomaPK generoxidiomaPK) {
        this.generoxidiomaPK = generoxidiomaPK;
    }

    public Generoxidioma(GeneroxidiomaPK generoxidiomaPK, String alias) {
        this.generoxidiomaPK = generoxidiomaPK;
        this.alias = alias;
    }

    public Generoxidioma(long generoCodigoGenero, String idiomaNombreIdioma) {
        this.generoxidiomaPK = new GeneroxidiomaPK(generoCodigoGenero, idiomaNombreIdioma);
    }

    public GeneroxidiomaPK getGeneroxidiomaPK() {
        return generoxidiomaPK;
    }

    public void setGeneroxidiomaPK(GeneroxidiomaPK generoxidiomaPK) {
        this.generoxidiomaPK = generoxidiomaPK;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (generoxidiomaPK != null ? generoxidiomaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Generoxidioma)) {
            return false;
        }
        Generoxidioma other = (Generoxidioma) object;
        if ((this.generoxidiomaPK == null && other.generoxidiomaPK != null) || (this.generoxidiomaPK != null && !this.generoxidiomaPK.equals(other.generoxidiomaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.alias;
    }
    
}
