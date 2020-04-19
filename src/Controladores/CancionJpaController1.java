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
import Entidades.Album;
import Entidades.Cancion;
import Entidades.CancionPK;
import Entidades.Genero;
import Entidades.Interprete;
import java.util.ArrayList;
import java.util.List;
import Entidades.Registro;
import Entidades.Cancionxlistasr;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author julia
 */
public class CancionJpaController1 implements Serializable {

    public CancionJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cancion cancion) throws PreexistingEntityException, Exception {
        if (cancion.getCancionPK() == null) {
            cancion.setCancionPK(new CancionPK());
        }
        if (cancion.getInterpreteList() == null) {
            cancion.setInterpreteList(new ArrayList<Interprete>());
        }
        if (cancion.getRegistroList() == null) {
            cancion.setRegistroList(new ArrayList<Registro>());
        }
        if (cancion.getCancionxlistasrList() == null) {
            cancion.setCancionxlistasrList(new ArrayList<Cancionxlistasr>());
        }
        cancion.getCancionPK().setInterpreteIdIn(cancion.getInterprete().getInterpreteIdIn());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Album albumIdAlbum = cancion.getAlbumIdAlbum();
            if (albumIdAlbum != null) {
                albumIdAlbum = em.getReference(albumIdAlbum.getClass(), albumIdAlbum.getIdAlbum());
                cancion.setAlbumIdAlbum(albumIdAlbum);
            }
            Genero generoCodigoGenero = cancion.getGeneroCodigoGenero();
            if (generoCodigoGenero != null) {
                generoCodigoGenero = em.getReference(generoCodigoGenero.getClass(), generoCodigoGenero.getCodigoGenero());
                cancion.setGeneroCodigoGenero(generoCodigoGenero);
            }
            Interprete interprete = cancion.getInterprete();
            if (interprete != null) {
                interprete = em.getReference(interprete.getClass(), interprete.getInterpreteIdIn());
                cancion.setInterprete(interprete);
            }
            List<Interprete> attachedInterpreteList = new ArrayList<Interprete>();
            for (Interprete interpreteListInterpreteToAttach : cancion.getInterpreteList()) {
                interpreteListInterpreteToAttach = em.getReference(interpreteListInterpreteToAttach.getClass(), interpreteListInterpreteToAttach.getInterpreteIdIn());
                attachedInterpreteList.add(interpreteListInterpreteToAttach);
            }
            cancion.setInterpreteList(attachedInterpreteList);
            List<Registro> attachedRegistroList = new ArrayList<Registro>();
            for (Registro registroListRegistroToAttach : cancion.getRegistroList()) {
                registroListRegistroToAttach = em.getReference(registroListRegistroToAttach.getClass(), registroListRegistroToAttach.getRegistroPK());
                attachedRegistroList.add(registroListRegistroToAttach);
            }
            cancion.setRegistroList(attachedRegistroList);
            List<Cancionxlistasr> attachedCancionxlistasrList = new ArrayList<Cancionxlistasr>();
            for (Cancionxlistasr cancionxlistasrListCancionxlistasrToAttach : cancion.getCancionxlistasrList()) {
                cancionxlistasrListCancionxlistasrToAttach = em.getReference(cancionxlistasrListCancionxlistasrToAttach.getClass(), cancionxlistasrListCancionxlistasrToAttach.getCancionxlistasrPK());
                attachedCancionxlistasrList.add(cancionxlistasrListCancionxlistasrToAttach);
            }
            cancion.setCancionxlistasrList(attachedCancionxlistasrList);
            em.persist(cancion);
            if (albumIdAlbum != null) {
                albumIdAlbum.getCancionList().add(cancion);
                albumIdAlbum = em.merge(albumIdAlbum);
            }
            if (generoCodigoGenero != null) {
                generoCodigoGenero.getCancionList().add(cancion);
                generoCodigoGenero = em.merge(generoCodigoGenero);
            }
            if (interprete != null) {
                interprete.getCancionList().add(cancion);
                interprete = em.merge(interprete);
            }
            for (Interprete interpreteListInterprete : cancion.getInterpreteList()) {
                interpreteListInterprete.getCancionList().add(cancion);
                interpreteListInterprete = em.merge(interpreteListInterprete);
            }
            for (Registro registroListRegistro : cancion.getRegistroList()) {
                Cancion oldCancionOfRegistroListRegistro = registroListRegistro.getCancion();
                registroListRegistro.setCancion(cancion);
                registroListRegistro = em.merge(registroListRegistro);
                if (oldCancionOfRegistroListRegistro != null) {
                    oldCancionOfRegistroListRegistro.getRegistroList().remove(registroListRegistro);
                    oldCancionOfRegistroListRegistro = em.merge(oldCancionOfRegistroListRegistro);
                }
            }
            for (Cancionxlistasr cancionxlistasrListCancionxlistasr : cancion.getCancionxlistasrList()) {
                Cancion oldCancionOfCancionxlistasrListCancionxlistasr = cancionxlistasrListCancionxlistasr.getCancion();
                cancionxlistasrListCancionxlistasr.setCancion(cancion);
                cancionxlistasrListCancionxlistasr = em.merge(cancionxlistasrListCancionxlistasr);
                if (oldCancionOfCancionxlistasrListCancionxlistasr != null) {
                    oldCancionOfCancionxlistasrListCancionxlistasr.getCancionxlistasrList().remove(cancionxlistasrListCancionxlistasr);
                    oldCancionOfCancionxlistasrListCancionxlistasr = em.merge(oldCancionOfCancionxlistasrListCancionxlistasr);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCancion(cancion.getCancionPK()) != null) {
                throw new PreexistingEntityException("Cancion " + cancion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cancion cancion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        cancion.getCancionPK().setInterpreteIdIn(cancion.getInterprete().getInterpreteIdIn());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancion persistentCancion = em.find(Cancion.class, cancion.getCancionPK());
            Album albumIdAlbumOld = persistentCancion.getAlbumIdAlbum();
            Album albumIdAlbumNew = cancion.getAlbumIdAlbum();
            Genero generoCodigoGeneroOld = persistentCancion.getGeneroCodigoGenero();
            Genero generoCodigoGeneroNew = cancion.getGeneroCodigoGenero();
            Interprete interpreteOld = persistentCancion.getInterprete();
            Interprete interpreteNew = cancion.getInterprete();
            List<Interprete> interpreteListOld = persistentCancion.getInterpreteList();
            List<Interprete> interpreteListNew = cancion.getInterpreteList();
            List<Registro> registroListOld = persistentCancion.getRegistroList();
            List<Registro> registroListNew = cancion.getRegistroList();
            List<Cancionxlistasr> cancionxlistasrListOld = persistentCancion.getCancionxlistasrList();
            List<Cancionxlistasr> cancionxlistasrListNew = cancion.getCancionxlistasrList();
            List<String> illegalOrphanMessages = null;
            for (Registro registroListOldRegistro : registroListOld) {
                if (!registroListNew.contains(registroListOldRegistro)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Registro " + registroListOldRegistro + " since its cancion field is not nullable.");
                }
            }
            for (Cancionxlistasr cancionxlistasrListOldCancionxlistasr : cancionxlistasrListOld) {
                if (!cancionxlistasrListNew.contains(cancionxlistasrListOldCancionxlistasr)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cancionxlistasr " + cancionxlistasrListOldCancionxlistasr + " since its cancion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (albumIdAlbumNew != null) {
                albumIdAlbumNew = em.getReference(albumIdAlbumNew.getClass(), albumIdAlbumNew.getIdAlbum());
                cancion.setAlbumIdAlbum(albumIdAlbumNew);
            }
            if (generoCodigoGeneroNew != null) {
                generoCodigoGeneroNew = em.getReference(generoCodigoGeneroNew.getClass(), generoCodigoGeneroNew.getCodigoGenero());
                cancion.setGeneroCodigoGenero(generoCodigoGeneroNew);
            }
            if (interpreteNew != null) {
                interpreteNew = em.getReference(interpreteNew.getClass(), interpreteNew.getInterpreteIdIn());
                cancion.setInterprete(interpreteNew);
            }
            List<Interprete> attachedInterpreteListNew = new ArrayList<Interprete>();
            for (Interprete interpreteListNewInterpreteToAttach : interpreteListNew) {
                interpreteListNewInterpreteToAttach = em.getReference(interpreteListNewInterpreteToAttach.getClass(), interpreteListNewInterpreteToAttach.getInterpreteIdIn());
                attachedInterpreteListNew.add(interpreteListNewInterpreteToAttach);
            }
            interpreteListNew = attachedInterpreteListNew;
            cancion.setInterpreteList(interpreteListNew);
            List<Registro> attachedRegistroListNew = new ArrayList<Registro>();
            for (Registro registroListNewRegistroToAttach : registroListNew) {
                registroListNewRegistroToAttach = em.getReference(registroListNewRegistroToAttach.getClass(), registroListNewRegistroToAttach.getRegistroPK());
                attachedRegistroListNew.add(registroListNewRegistroToAttach);
            }
            registroListNew = attachedRegistroListNew;
            cancion.setRegistroList(registroListNew);
            List<Cancionxlistasr> attachedCancionxlistasrListNew = new ArrayList<Cancionxlistasr>();
            for (Cancionxlistasr cancionxlistasrListNewCancionxlistasrToAttach : cancionxlistasrListNew) {
                cancionxlistasrListNewCancionxlistasrToAttach = em.getReference(cancionxlistasrListNewCancionxlistasrToAttach.getClass(), cancionxlistasrListNewCancionxlistasrToAttach.getCancionxlistasrPK());
                attachedCancionxlistasrListNew.add(cancionxlistasrListNewCancionxlistasrToAttach);
            }
            cancionxlistasrListNew = attachedCancionxlistasrListNew;
            cancion.setCancionxlistasrList(cancionxlistasrListNew);
            cancion = em.merge(cancion);
            if (albumIdAlbumOld != null && !albumIdAlbumOld.equals(albumIdAlbumNew)) {
                albumIdAlbumOld.getCancionList().remove(cancion);
                albumIdAlbumOld = em.merge(albumIdAlbumOld);
            }
            if (albumIdAlbumNew != null && !albumIdAlbumNew.equals(albumIdAlbumOld)) {
                albumIdAlbumNew.getCancionList().add(cancion);
                albumIdAlbumNew = em.merge(albumIdAlbumNew);
            }
            if (generoCodigoGeneroOld != null && !generoCodigoGeneroOld.equals(generoCodigoGeneroNew)) {
                generoCodigoGeneroOld.getCancionList().remove(cancion);
                generoCodigoGeneroOld = em.merge(generoCodigoGeneroOld);
            }
            if (generoCodigoGeneroNew != null && !generoCodigoGeneroNew.equals(generoCodigoGeneroOld)) {
                generoCodigoGeneroNew.getCancionList().add(cancion);
                generoCodigoGeneroNew = em.merge(generoCodigoGeneroNew);
            }
            if (interpreteOld != null && !interpreteOld.equals(interpreteNew)) {
                interpreteOld.getCancionList().remove(cancion);
                interpreteOld = em.merge(interpreteOld);
            }
            if (interpreteNew != null && !interpreteNew.equals(interpreteOld)) {
                interpreteNew.getCancionList().add(cancion);
                interpreteNew = em.merge(interpreteNew);
            }
            for (Interprete interpreteListOldInterprete : interpreteListOld) {
                if (!interpreteListNew.contains(interpreteListOldInterprete)) {
                    interpreteListOldInterprete.getCancionList().remove(cancion);
                    interpreteListOldInterprete = em.merge(interpreteListOldInterprete);
                }
            }
            for (Interprete interpreteListNewInterprete : interpreteListNew) {
                if (!interpreteListOld.contains(interpreteListNewInterprete)) {
                    interpreteListNewInterprete.getCancionList().add(cancion);
                    interpreteListNewInterprete = em.merge(interpreteListNewInterprete);
                }
            }
            for (Registro registroListNewRegistro : registroListNew) {
                if (!registroListOld.contains(registroListNewRegistro)) {
                    Cancion oldCancionOfRegistroListNewRegistro = registroListNewRegistro.getCancion();
                    registroListNewRegistro.setCancion(cancion);
                    registroListNewRegistro = em.merge(registroListNewRegistro);
                    if (oldCancionOfRegistroListNewRegistro != null && !oldCancionOfRegistroListNewRegistro.equals(cancion)) {
                        oldCancionOfRegistroListNewRegistro.getRegistroList().remove(registroListNewRegistro);
                        oldCancionOfRegistroListNewRegistro = em.merge(oldCancionOfRegistroListNewRegistro);
                    }
                }
            }
            for (Cancionxlistasr cancionxlistasrListNewCancionxlistasr : cancionxlistasrListNew) {
                if (!cancionxlistasrListOld.contains(cancionxlistasrListNewCancionxlistasr)) {
                    Cancion oldCancionOfCancionxlistasrListNewCancionxlistasr = cancionxlistasrListNewCancionxlistasr.getCancion();
                    cancionxlistasrListNewCancionxlistasr.setCancion(cancion);
                    cancionxlistasrListNewCancionxlistasr = em.merge(cancionxlistasrListNewCancionxlistasr);
                    if (oldCancionOfCancionxlistasrListNewCancionxlistasr != null && !oldCancionOfCancionxlistasrListNewCancionxlistasr.equals(cancion)) {
                        oldCancionOfCancionxlistasrListNewCancionxlistasr.getCancionxlistasrList().remove(cancionxlistasrListNewCancionxlistasr);
                        oldCancionOfCancionxlistasrListNewCancionxlistasr = em.merge(oldCancionOfCancionxlistasrListNewCancionxlistasr);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CancionPK id = cancion.getCancionPK();
                if (findCancion(id) == null) {
                    throw new NonexistentEntityException("The cancion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CancionPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancion cancion;
            try {
                cancion = em.getReference(Cancion.class, id);
                cancion.getCancionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cancion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Registro> registroListOrphanCheck = cancion.getRegistroList();
            for (Registro registroListOrphanCheckRegistro : registroListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cancion (" + cancion + ") cannot be destroyed since the Registro " + registroListOrphanCheckRegistro + " in its registroList field has a non-nullable cancion field.");
            }
            List<Cancionxlistasr> cancionxlistasrListOrphanCheck = cancion.getCancionxlistasrList();
            for (Cancionxlistasr cancionxlistasrListOrphanCheckCancionxlistasr : cancionxlistasrListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cancion (" + cancion + ") cannot be destroyed since the Cancionxlistasr " + cancionxlistasrListOrphanCheckCancionxlistasr + " in its cancionxlistasrList field has a non-nullable cancion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Album albumIdAlbum = cancion.getAlbumIdAlbum();
            if (albumIdAlbum != null) {
                albumIdAlbum.getCancionList().remove(cancion);
                albumIdAlbum = em.merge(albumIdAlbum);
            }
            Genero generoCodigoGenero = cancion.getGeneroCodigoGenero();
            if (generoCodigoGenero != null) {
                generoCodigoGenero.getCancionList().remove(cancion);
                generoCodigoGenero = em.merge(generoCodigoGenero);
            }
            Interprete interprete = cancion.getInterprete();
            if (interprete != null) {
                interprete.getCancionList().remove(cancion);
                interprete = em.merge(interprete);
            }
            List<Interprete> interpreteList = cancion.getInterpreteList();
            for (Interprete interpreteListInterprete : interpreteList) {
                interpreteListInterprete.getCancionList().remove(cancion);
                interpreteListInterprete = em.merge(interpreteListInterprete);
            }
            em.remove(cancion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cancion> findCancionEntities() {
        return findCancionEntities(true, -1, -1);
    }

    public List<Cancion> findCancionEntities(int maxResults, int firstResult) {
        return findCancionEntities(false, maxResults, firstResult);
    }

    private List<Cancion> findCancionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cancion.class));
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

    public Cancion findCancion(CancionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cancion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCancionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cancion> rt = cq.from(Cancion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
