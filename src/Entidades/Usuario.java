/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author julia
 */
@Entity
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByNickname", query = "SELECT u FROM Usuario u WHERE u.nickname = :nickname")
    , @NamedQuery(name = "Usuario.findByNombreU", query = "SELECT u FROM Usuario u WHERE u.nombreU = :nombreU")
    , @NamedQuery(name = "Usuario.findByApellidoU", query = "SELECT u FROM Usuario u WHERE u.apellidoU = :apellidoU")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NICKNAME")
    private String nickname;
    @Basic(optional = false)
    @Column(name = "NOMBRE_U")
    private String nombreU;
    @Basic(optional = false)
    @Column(name = "APELLIDO_U")
    private String apellidoU;
    
    @JoinTable(name = "SIGUE", joinColumns = {
        @JoinColumn(name = "USUARIO_NICKNAME", referencedColumnName = "NICKNAME")}, inverseJoinColumns = {
        @JoinColumn(name = "USUARIO_NICKNAME1", referencedColumnName = "NICKNAME")})
    @ManyToMany
    private List<Usuario> usuarioList;
    @ManyToMany(mappedBy = "usuarioList")
    private List<Usuario> usuarioList1;
    @JoinColumn(name = "PAIS_NOMBRE_PAIS", referencedColumnName = "NOMBRE_PAIS")
    @ManyToOne(optional = false)
    private Pais paisNombrePais;
    /*
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuarioNickname3")
    private Familiar familiar;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuarioNickname2")
    private Familiar familiar1;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuarioNickname")
    private Familiar familiar2;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuarioNickname1")
    private Familiar familiar3;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Registro> registroList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<ListaR> listaRList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuarioNickname")
    private Suscripcion suscripcion;
*/
    public Usuario() {
    }

    public Usuario(String nickname) {
        this.nickname = nickname;
    }

    public Usuario(String nickname, String nombreU, String apellidoU) {
        this.nickname = nickname;
        this.nombreU = nombreU;
        this.apellidoU = apellidoU;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNombreU() {
        return nombreU;
    }

    public void setNombreU(String nombreU) {
        this.nombreU = nombreU;
    }

    public String getApellidoU() {
        return apellidoU;
    }

    public void setApellidoU(String apellidoU) {
        this.apellidoU = apellidoU;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList1() {
        return usuarioList1;
    }

    public void setUsuarioList1(List<Usuario> usuarioList1) {
        this.usuarioList1 = usuarioList1;
    }

    public Pais getPaisNombrePais() {
        return paisNombrePais;
    }

    public void setPaisNombrePais(Pais paisNombrePais) {
        this.paisNombrePais = paisNombrePais;
    }
/*
    public Familiar getFamiliar() {
        return familiar;
    }

    public void setFamiliar(Familiar familiar) {
        this.familiar = familiar;
    }

    public Familiar getFamiliar1() {
        return familiar1;
    }

    public void setFamiliar1(Familiar familiar1) {
        this.familiar1 = familiar1;
    }

    public Familiar getFamiliar2() {
        return familiar2;
    }

    public void setFamiliar2(Familiar familiar2) {
        this.familiar2 = familiar2;
    }

    public Familiar getFamiliar3() {
        return familiar3;
    }

    public void setFamiliar3(Familiar familiar3) {
        this.familiar3 = familiar3;
    }

    @XmlTransient
    public List<Registro> getRegistroList() {
        return registroList;
    }

    public void setRegistroList(List<Registro> registroList) {
        this.registroList = registroList;
    }

    @XmlTransient
    public List<ListaR> getListaRList() {
        return listaRList;
    }

    public void setListaRList(List<ListaR> listaRList) {
        this.listaRList = listaRList;
    }

    public Suscripcion getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(Suscripcion suscripcion) {
        this.suscripcion = suscripcion;
    }
*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nickname != null ? nickname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.nickname == null && other.nickname != null) || (this.nickname != null && !this.nickname.equals(other.nickname))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nickname;
    }
    
}
