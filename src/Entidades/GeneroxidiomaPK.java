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
public class GeneroxidiomaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "GENERO_CODIGO_GENERO")
    private long generoCodigoGenero;
    @Basic(optional = false)
    @Column(name = "IDIOMA_NOMBRE_IDIOMA")
    private String idiomaNombreIdioma;

    public GeneroxidiomaPK() {
    }

    public GeneroxidiomaPK(long generoCodigoGenero, String idiomaNombreIdioma) {
        this.generoCodigoGenero = generoCodigoGenero;
        this.idiomaNombreIdioma = idiomaNombreIdioma;
    }

    public long getGeneroCodigoGenero() {
        return generoCodigoGenero;
    }

    public void setGeneroCodigoGenero(long generoCodigoGenero) {
        this.generoCodigoGenero = generoCodigoGenero;
    }

    public String getIdiomaNombreIdioma() {
        return idiomaNombreIdioma;
    }

    public void setIdiomaNombreIdioma(String idiomaNombreIdioma) {
        this.idiomaNombreIdioma = idiomaNombreIdioma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) generoCodigoGenero;
        hash += (idiomaNombreIdioma != null ? idiomaNombreIdioma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GeneroxidiomaPK)) {
            return false;
        }
        GeneroxidiomaPK other = (GeneroxidiomaPK) object;
        if (this.generoCodigoGenero != other.generoCodigoGenero) {
            return false;
        }
        if ((this.idiomaNombreIdioma == null && other.idiomaNombreIdioma != null) || (this.idiomaNombreIdioma != null && !this.idiomaNombreIdioma.equals(other.idiomaNombreIdioma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.GeneroxidiomaPK[ generoCodigoGenero=" + generoCodigoGenero + ", idiomaNombreIdioma=" + idiomaNombreIdioma + " ]";
    }
    
}
