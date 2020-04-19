/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Pais;
import Entidades.Familiar;
import Entidades.Suscripcion;
import Entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import Entidades.Registro;
import Entidades.ListaR;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author julia
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
/*
    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getUsuarioList() == null) {
            usuario.setUsuarioList(new ArrayList<Usuario>());
        }
        if (usuario.getUsuarioList1() == null) {
            usuario.setUsuarioList1(new ArrayList<Usuario>());
        }
        if (usuario.getRegistroList() == null) {
            usuario.setRegistroList(new ArrayList<Registro>());
        }
        if (usuario.getListaRList() == null) {
            usuario.setListaRList(new ArrayList<ListaR>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais paisNombrePais = usuario.getPaisNombrePais();
            if (paisNombrePais != null) {
                paisNombrePais = em.getReference(paisNombrePais.getClass(), paisNombrePais.getNombrePais());
                usuario.setPaisNombrePais(paisNombrePais);
            }
            Familiar familiar = usuario.getFamiliar();
            if (familiar != null) {
                familiar = em.getReference(familiar.getClass(), familiar.getSusIdSuscripcion());
                usuario.setFamiliar(familiar);
            }
            Familiar familiar1 = usuario.getFamiliar1();
            if (familiar1 != null) {
                familiar1 = em.getReference(familiar1.getClass(), familiar1.getSusIdSuscripcion());
                usuario.setFamiliar1(familiar1);
            }
            Familiar familiar2 = usuario.getFamiliar2();
            if (familiar2 != null) {
                familiar2 = em.getReference(familiar2.getClass(), familiar2.getSusIdSuscripcion());
                usuario.setFamiliar2(familiar2);
            }
            Familiar familiar3 = usuario.getFamiliar3();
            if (familiar3 != null) {
                familiar3 = em.getReference(familiar3.getClass(), familiar3.getSusIdSuscripcion());
                usuario.setFamiliar3(familiar3);
            }
            Suscripcion suscripcion = usuario.getSuscripcion();
            if (suscripcion != null) {
                suscripcion = em.getReference(suscripcion.getClass(), suscripcion.getSusIdSuscripcion());
                usuario.setSuscripcion(suscripcion);
            }
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : usuario.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getNickname());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            usuario.setUsuarioList(attachedUsuarioList);
            List<Usuario> attachedUsuarioList1 = new ArrayList<Usuario>();
            for (Usuario usuarioList1UsuarioToAttach : usuario.getUsuarioList1()) {
                usuarioList1UsuarioToAttach = em.getReference(usuarioList1UsuarioToAttach.getClass(), usuarioList1UsuarioToAttach.getNickname());
                attachedUsuarioList1.add(usuarioList1UsuarioToAttach);
            }
            usuario.setUsuarioList1(attachedUsuarioList1);
            List<Registro> attachedRegistroList = new ArrayList<Registro>();
            for (Registro registroListRegistroToAttach : usuario.getRegistroList()) {
                registroListRegistroToAttach = em.getReference(registroListRegistroToAttach.getClass(), registroListRegistroToAttach.getRegistroPK());
                attachedRegistroList.add(registroListRegistroToAttach);
            }
            usuario.setRegistroList(attachedRegistroList);
            List<ListaR> attachedListaRList = new ArrayList<ListaR>();
            for (ListaR listaRListListaRToAttach : usuario.getListaRList()) {
                listaRListListaRToAttach = em.getReference(listaRListListaRToAttach.getClass(), listaRListListaRToAttach.getListaRPK());
                attachedListaRList.add(listaRListListaRToAttach);
            }
            usuario.setListaRList(attachedListaRList);
            em.persist(usuario);
            if (paisNombrePais != null) {
                paisNombrePais.getUsuarioList().add(usuario);
                paisNombrePais = em.merge(paisNombrePais);
            }
            if (familiar != null) {
                Usuario oldUsuarioNickname3OfFamiliar = familiar.getUsuarioNickname3();
                if (oldUsuarioNickname3OfFamiliar != null) {
                    oldUsuarioNickname3OfFamiliar.setFamiliar(null);
                    oldUsuarioNickname3OfFamiliar = em.merge(oldUsuarioNickname3OfFamiliar);
                }
                familiar.setUsuarioNickname3(usuario);
                familiar = em.merge(familiar);
            }
            if (familiar1 != null) {
                Usuario oldUsuarioNickname2OfFamiliar1 = familiar1.getUsuarioNickname2();
                if (oldUsuarioNickname2OfFamiliar1 != null) {
                    oldUsuarioNickname2OfFamiliar1.setFamiliar1(null);
                    oldUsuarioNickname2OfFamiliar1 = em.merge(oldUsuarioNickname2OfFamiliar1);
                }
                familiar1.setUsuarioNickname2(usuario);
                familiar1 = em.merge(familiar1);
            }
            if (familiar2 != null) {
                Usuario oldUsuarioNicknameOfFamiliar2 = familiar2.getUsuarioNickname();
                if (oldUsuarioNicknameOfFamiliar2 != null) {
                    oldUsuarioNicknameOfFamiliar2.setFamiliar2(null);
                    oldUsuarioNicknameOfFamiliar2 = em.merge(oldUsuarioNicknameOfFamiliar2);
                }
                familiar2.setUsuarioNickname(usuario);
                familiar2 = em.merge(familiar2);
            }
            if (familiar3 != null) {
                Usuario oldUsuarioNickname1OfFamiliar3 = familiar3.getUsuarioNickname1();
                if (oldUsuarioNickname1OfFamiliar3 != null) {
                    oldUsuarioNickname1OfFamiliar3.setFamiliar3(null);
                    oldUsuarioNickname1OfFamiliar3 = em.merge(oldUsuarioNickname1OfFamiliar3);
                }
                familiar3.setUsuarioNickname1(usuario);
                familiar3 = em.merge(familiar3);
            }
            if (suscripcion != null) {
                Usuario oldUsuarioNicknameOfSuscripcion = suscripcion.getUsuarioNickname();
                if (oldUsuarioNicknameOfSuscripcion != null) {
                    oldUsuarioNicknameOfSuscripcion.setSuscripcion(null);
                    oldUsuarioNicknameOfSuscripcion = em.merge(oldUsuarioNicknameOfSuscripcion);
                }
                suscripcion.setUsuarioNickname(usuario);
                suscripcion = em.merge(suscripcion);
            }
            for (Usuario usuarioListUsuario : usuario.getUsuarioList()) {
                usuarioListUsuario.getUsuarioList().add(usuario);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            for (Usuario usuarioList1Usuario : usuario.getUsuarioList1()) {
                usuarioList1Usuario.getUsuarioList().add(usuario);
                usuarioList1Usuario = em.merge(usuarioList1Usuario);
            }
            for (Registro registroListRegistro : usuario.getRegistroList()) {
                Usuario oldUsuarioOfRegistroListRegistro = registroListRegistro.getUsuario();
                registroListRegistro.setUsuario(usuario);
                registroListRegistro = em.merge(registroListRegistro);
                if (oldUsuarioOfRegistroListRegistro != null) {
                    oldUsuarioOfRegistroListRegistro.getRegistroList().remove(registroListRegistro);
                    oldUsuarioOfRegistroListRegistro = em.merge(oldUsuarioOfRegistroListRegistro);
                }
            }
            for (ListaR listaRListListaR : usuario.getListaRList()) {
                Usuario oldUsuarioOfListaRListListaR = listaRListListaR.getUsuario();
                listaRListListaR.setUsuario(usuario);
                listaRListListaR = em.merge(listaRListListaR);
                if (oldUsuarioOfListaRListListaR != null) {
                    oldUsuarioOfListaRListListaR.getListaRList().remove(listaRListListaR);
                    oldUsuarioOfListaRListListaR = em.merge(oldUsuarioOfListaRListListaR);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getNickname()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getNickname());
            Pais paisNombrePaisOld = persistentUsuario.getPaisNombrePais();
            Pais paisNombrePaisNew = usuario.getPaisNombrePais();
            Familiar familiarOld = persistentUsuario.getFamiliar();
            Familiar familiarNew = usuario.getFamiliar();
            Familiar familiar1Old = persistentUsuario.getFamiliar1();
            Familiar familiar1New = usuario.getFamiliar1();
            Familiar familiar2Old = persistentUsuario.getFamiliar2();
            Familiar familiar2New = usuario.getFamiliar2();
            Familiar familiar3Old = persistentUsuario.getFamiliar3();
            Familiar familiar3New = usuario.getFamiliar3();
            Suscripcion suscripcionOld = persistentUsuario.getSuscripcion();
            Suscripcion suscripcionNew = usuario.getSuscripcion();
            List<Usuario> usuarioListOld = persistentUsuario.getUsuarioList();
            List<Usuario> usuarioListNew = usuario.getUsuarioList();
            List<Usuario> usuarioList1Old = persistentUsuario.getUsuarioList1();
            List<Usuario> usuarioList1New = usuario.getUsuarioList1();
            List<Registro> registroListOld = persistentUsuario.getRegistroList();
            List<Registro> registroListNew = usuario.getRegistroList();
            List<ListaR> listaRListOld = persistentUsuario.getListaRList();
            List<ListaR> listaRListNew = usuario.getListaRList();
            List<String> illegalOrphanMessages = null;
            if (familiarOld != null && !familiarOld.equals(familiarNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Familiar " + familiarOld + " since its usuarioNickname3 field is not nullable.");
            }
            if (familiar1Old != null && !familiar1Old.equals(familiar1New)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Familiar " + familiar1Old + " since its usuarioNickname2 field is not nullable.");
            }
            if (familiar2Old != null && !familiar2Old.equals(familiar2New)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Familiar " + familiar2Old + " since its usuarioNickname field is not nullable.");
            }
            if (familiar3Old != null && !familiar3Old.equals(familiar3New)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Familiar " + familiar3Old + " since its usuarioNickname1 field is not nullable.");
            }
            if (suscripcionOld != null && !suscripcionOld.equals(suscripcionNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Suscripcion " + suscripcionOld + " since its usuarioNickname field is not nullable.");
            }
            for (Registro registroListOldRegistro : registroListOld) {
                if (!registroListNew.contains(registroListOldRegistro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Registro " + registroListOldRegistro + " since its usuario field is not nullable.");
                }
            }
            for (ListaR listaRListOldListaR : listaRListOld) {
                if (!listaRListNew.contains(listaRListOldListaR)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ListaR " + listaRListOldListaR + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (paisNombrePaisNew != null) {
                paisNombrePaisNew = em.getReference(paisNombrePaisNew.getClass(), paisNombrePaisNew.getNombrePais());
                usuario.setPaisNombrePais(paisNombrePaisNew);
            }
            if (familiarNew != null) {
                familiarNew = em.getReference(familiarNew.getClass(), familiarNew.getSusIdSuscripcion());
                usuario.setFamiliar(familiarNew);
            }
            if (familiar1New != null) {
                familiar1New = em.getReference(familiar1New.getClass(), familiar1New.getSusIdSuscripcion());
                usuario.setFamiliar1(familiar1New);
            }
            if (familiar2New != null) {
                familiar2New = em.getReference(familiar2New.getClass(), familiar2New.getSusIdSuscripcion());
                usuario.setFamiliar2(familiar2New);
            }
            if (familiar3New != null) {
                familiar3New = em.getReference(familiar3New.getClass(), familiar3New.getSusIdSuscripcion());
                usuario.setFamiliar3(familiar3New);
            }
            if (suscripcionNew != null) {
                suscripcionNew = em.getReference(suscripcionNew.getClass(), suscripcionNew.getSusIdSuscripcion());
                usuario.setSuscripcion(suscripcionNew);
            }
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getNickname());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            usuario.setUsuarioList(usuarioListNew);
            List<Usuario> attachedUsuarioList1New = new ArrayList<Usuario>();
            for (Usuario usuarioList1NewUsuarioToAttach : usuarioList1New) {
                usuarioList1NewUsuarioToAttach = em.getReference(usuarioList1NewUsuarioToAttach.getClass(), usuarioList1NewUsuarioToAttach.getNickname());
                attachedUsuarioList1New.add(usuarioList1NewUsuarioToAttach);
            }
            usuarioList1New = attachedUsuarioList1New;
            usuario.setUsuarioList1(usuarioList1New);
            List<Registro> attachedRegistroListNew = new ArrayList<Registro>();
            for (Registro registroListNewRegistroToAttach : registroListNew) {
                registroListNewRegistroToAttach = em.getReference(registroListNewRegistroToAttach.getClass(), registroListNewRegistroToAttach.getRegistroPK());
                attachedRegistroListNew.add(registroListNewRegistroToAttach);
            }
            registroListNew = attachedRegistroListNew;
            usuario.setRegistroList(registroListNew);
            List<ListaR> attachedListaRListNew = new ArrayList<ListaR>();
            for (ListaR listaRListNewListaRToAttach : listaRListNew) {
                listaRListNewListaRToAttach = em.getReference(listaRListNewListaRToAttach.getClass(), listaRListNewListaRToAttach.getListaRPK());
                attachedListaRListNew.add(listaRListNewListaRToAttach);
            }
            listaRListNew = attachedListaRListNew;
            usuario.setListaRList(listaRListNew);
            usuario = em.merge(usuario);
            if (paisNombrePaisOld != null && !paisNombrePaisOld.equals(paisNombrePaisNew)) {
                paisNombrePaisOld.getUsuarioList().remove(usuario);
                paisNombrePaisOld = em.merge(paisNombrePaisOld);
            }
            if (paisNombrePaisNew != null && !paisNombrePaisNew.equals(paisNombrePaisOld)) {
                paisNombrePaisNew.getUsuarioList().add(usuario);
                paisNombrePaisNew = em.merge(paisNombrePaisNew);
            }
            if (familiarNew != null && !familiarNew.equals(familiarOld)) {
                Usuario oldUsuarioNickname3OfFamiliar = familiarNew.getUsuarioNickname3();
                if (oldUsuarioNickname3OfFamiliar != null) {
                    oldUsuarioNickname3OfFamiliar.setFamiliar(null);
                    oldUsuarioNickname3OfFamiliar = em.merge(oldUsuarioNickname3OfFamiliar);
                }
                familiarNew.setUsuarioNickname3(usuario);
                familiarNew = em.merge(familiarNew);
            }
            if (familiar1New != null && !familiar1New.equals(familiar1Old)) {
                Usuario oldUsuarioNickname2OfFamiliar1 = familiar1New.getUsuarioNickname2();
                if (oldUsuarioNickname2OfFamiliar1 != null) {
                    oldUsuarioNickname2OfFamiliar1.setFamiliar1(null);
                    oldUsuarioNickname2OfFamiliar1 = em.merge(oldUsuarioNickname2OfFamiliar1);
                }
                familiar1New.setUsuarioNickname2(usuario);
                familiar1New = em.merge(familiar1New);
            }
            if (familiar2New != null && !familiar2New.equals(familiar2Old)) {
                Usuario oldUsuarioNicknameOfFamiliar2 = familiar2New.getUsuarioNickname();
                if (oldUsuarioNicknameOfFamiliar2 != null) {
                    oldUsuarioNicknameOfFamiliar2.setFamiliar2(null);
                    oldUsuarioNicknameOfFamiliar2 = em.merge(oldUsuarioNicknameOfFamiliar2);
                }
                familiar2New.setUsuarioNickname(usuario);
                familiar2New = em.merge(familiar2New);
            }
            if (familiar3New != null && !familiar3New.equals(familiar3Old)) {
                Usuario oldUsuarioNickname1OfFamiliar3 = familiar3New.getUsuarioNickname1();
                if (oldUsuarioNickname1OfFamiliar3 != null) {
                    oldUsuarioNickname1OfFamiliar3.setFamiliar3(null);
                    oldUsuarioNickname1OfFamiliar3 = em.merge(oldUsuarioNickname1OfFamiliar3);
                }
                familiar3New.setUsuarioNickname1(usuario);
                familiar3New = em.merge(familiar3New);
            }
            if (suscripcionNew != null && !suscripcionNew.equals(suscripcionOld)) {
                Usuario oldUsuarioNicknameOfSuscripcion = suscripcionNew.getUsuarioNickname();
                if (oldUsuarioNicknameOfSuscripcion != null) {
                    oldUsuarioNicknameOfSuscripcion.setSuscripcion(null);
                    oldUsuarioNicknameOfSuscripcion = em.merge(oldUsuarioNicknameOfSuscripcion);
                }
                suscripcionNew.setUsuarioNickname(usuario);
                suscripcionNew = em.merge(suscripcionNew);
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.getUsuarioList().remove(usuario);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    usuarioListNewUsuario.getUsuarioList().add(usuario);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                }
            }
            for (Usuario usuarioList1OldUsuario : usuarioList1Old) {
                if (!usuarioList1New.contains(usuarioList1OldUsuario)) {
                    usuarioList1OldUsuario.getUsuarioList().remove(usuario);
                    usuarioList1OldUsuario = em.merge(usuarioList1OldUsuario);
                }
            }
            for (Usuario usuarioList1NewUsuario : usuarioList1New) {
                if (!usuarioList1Old.contains(usuarioList1NewUsuario)) {
                    usuarioList1NewUsuario.getUsuarioList().add(usuario);
                    usuarioList1NewUsuario = em.merge(usuarioList1NewUsuario);
                }
            }
            for (Registro registroListNewRegistro : registroListNew) {
                if (!registroListOld.contains(registroListNewRegistro)) {
                    Usuario oldUsuarioOfRegistroListNewRegistro = registroListNewRegistro.getUsuario();
                    registroListNewRegistro.setUsuario(usuario);
                    registroListNewRegistro = em.merge(registroListNewRegistro);
                    if (oldUsuarioOfRegistroListNewRegistro != null && !oldUsuarioOfRegistroListNewRegistro.equals(usuario)) {
                        oldUsuarioOfRegistroListNewRegistro.getRegistroList().remove(registroListNewRegistro);
                        oldUsuarioOfRegistroListNewRegistro = em.merge(oldUsuarioOfRegistroListNewRegistro);
                    }
                }
            }
            for (ListaR listaRListNewListaR : listaRListNew) {
                if (!listaRListOld.contains(listaRListNewListaR)) {
                    Usuario oldUsuarioOfListaRListNewListaR = listaRListNewListaR.getUsuario();
                    listaRListNewListaR.setUsuario(usuario);
                    listaRListNewListaR = em.merge(listaRListNewListaR);
                    if (oldUsuarioOfListaRListNewListaR != null && !oldUsuarioOfListaRListNewListaR.equals(usuario)) {
                        oldUsuarioOfListaRListNewListaR.getListaRList().remove(listaRListNewListaR);
                        oldUsuarioOfListaRListNewListaR = em.merge(oldUsuarioOfListaRListNewListaR);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getNickname();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getNickname();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Familiar familiarOrphanCheck = usuario.getFamiliar();
            if (familiarOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Familiar " + familiarOrphanCheck + " in its familiar field has a non-nullable usuarioNickname3 field.");
            }
            Familiar familiar1OrphanCheck = usuario.getFamiliar1();
            if (familiar1OrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Familiar " + familiar1OrphanCheck + " in its familiar1 field has a non-nullable usuarioNickname2 field.");
            }
            Familiar familiar2OrphanCheck = usuario.getFamiliar2();
            if (familiar2OrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Familiar " + familiar2OrphanCheck + " in its familiar2 field has a non-nullable usuarioNickname field.");
            }
            Familiar familiar3OrphanCheck = usuario.getFamiliar3();
            if (familiar3OrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Familiar " + familiar3OrphanCheck + " in its familiar3 field has a non-nullable usuarioNickname1 field.");
            }
            Suscripcion suscripcionOrphanCheck = usuario.getSuscripcion();
            if (suscripcionOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Suscripcion " + suscripcionOrphanCheck + " in its suscripcion field has a non-nullable usuarioNickname field.");
            }
            List<Registro> registroListOrphanCheck = usuario.getRegistroList();
            for (Registro registroListOrphanCheckRegistro : registroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Registro " + registroListOrphanCheckRegistro + " in its registroList field has a non-nullable usuario field.");
            }
            List<ListaR> listaRListOrphanCheck = usuario.getListaRList();
            for (ListaR listaRListOrphanCheckListaR : listaRListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the ListaR " + listaRListOrphanCheckListaR + " in its listaRList field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais paisNombrePais = usuario.getPaisNombrePais();
            if (paisNombrePais != null) {
                paisNombrePais.getUsuarioList().remove(usuario);
                paisNombrePais = em.merge(paisNombrePais);
            }
            List<Usuario> usuarioList = usuario.getUsuarioList();
            for (Usuario usuarioListUsuario : usuarioList) {
                usuarioListUsuario.getUsuarioList().remove(usuario);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            List<Usuario> usuarioList1 = usuario.getUsuarioList1();
            for (Usuario usuarioList1Usuario : usuarioList1) {
                usuarioList1Usuario.getUsuarioList().remove(usuario);
                usuarioList1Usuario = em.merge(usuarioList1Usuario);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
*/
    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
