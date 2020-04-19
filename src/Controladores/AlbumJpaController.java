/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Album;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Empresa;
import Entidades.Cancion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author julia
 */
public class AlbumJpaController implements Serializable {

    public AlbumJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Album album) throws PreexistingEntityException, Exception {
        if (album.getCancionList() == null) {
            album.setCancionList(new ArrayList<Cancion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa empresaNombreEmpresa = album.getEmpresaNombreEmpresa();
            if (empresaNombreEmpresa != null) {
                empresaNombreEmpresa = em.getReference(empresaNombreEmpresa.getClass(), empresaNombreEmpresa.getNombreEmpresa());
                album.setEmpresaNombreEmpresa(empresaNombreEmpresa);
            }
            List<Cancion> attachedCancionList = new ArrayList<Cancion>();
            for (Cancion cancionListCancionToAttach : album.getCancionList()) {
                cancionListCancionToAttach = em.getReference(cancionListCancionToAttach.getClass(), cancionListCancionToAttach.getCancionPK());
                attachedCancionList.add(cancionListCancionToAttach);
            }
            album.setCancionList(attachedCancionList);
            em.persist(album);
            if (empresaNombreEmpresa != null) {
                empresaNombreEmpresa.getAlbumList().add(album);
                empresaNombreEmpresa = em.merge(empresaNombreEmpresa);
            }
            for (Cancion cancionListCancion : album.getCancionList()) {
                Album oldAlbumIdAlbumOfCancionListCancion = cancionListCancion.getAlbumIdAlbum();
                cancionListCancion.setAlbumIdAlbum(album);
                cancionListCancion = em.merge(cancionListCancion);
                if (oldAlbumIdAlbumOfCancionListCancion != null) {
                    oldAlbumIdAlbumOfCancionListCancion.getCancionList().remove(cancionListCancion);
                    oldAlbumIdAlbumOfCancionListCancion = em.merge(oldAlbumIdAlbumOfCancionListCancion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlbum(album.getIdAlbum()) != null) {
                throw new PreexistingEntityException("Album " + album + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Album album) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Album persistentAlbum = em.find(Album.class, album.getIdAlbum());
            Empresa empresaNombreEmpresaOld = persistentAlbum.getEmpresaNombreEmpresa();
            Empresa empresaNombreEmpresaNew = album.getEmpresaNombreEmpresa();
            List<Cancion> cancionListOld = persistentAlbum.getCancionList();
            List<Cancion> cancionListNew = album.getCancionList();
            List<String> illegalOrphanMessages = null;
            for (Cancion cancionListOldCancion : cancionListOld) {
                if (!cancionListNew.contains(cancionListOldCancion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cancion " + cancionListOldCancion + " since its albumIdAlbum field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (empresaNombreEmpresaNew != null) {
                empresaNombreEmpresaNew = em.getReference(empresaNombreEmpresaNew.getClass(), empresaNombreEmpresaNew.getNombreEmpresa());
                album.setEmpresaNombreEmpresa(empresaNombreEmpresaNew);
            }
            List<Cancion> attachedCancionListNew = new ArrayList<Cancion>();
            for (Cancion cancionListNewCancionToAttach : cancionListNew) {
                cancionListNewCancionToAttach = em.getReference(cancionListNewCancionToAttach.getClass(), cancionListNewCancionToAttach.getCancionPK());
                attachedCancionListNew.add(cancionListNewCancionToAttach);
            }
            cancionListNew = attachedCancionListNew;
            album.setCancionList(cancionListNew);
            album = em.merge(album);
            if (empresaNombreEmpresaOld != null && !empresaNombreEmpresaOld.equals(empresaNombreEmpresaNew)) {
                empresaNombreEmpresaOld.getAlbumList().remove(album);
                empresaNombreEmpresaOld = em.merge(empresaNombreEmpresaOld);
            }
            if (empresaNombreEmpresaNew != null && !empresaNombreEmpresaNew.equals(empresaNombreEmpresaOld)) {
                empresaNombreEmpresaNew.getAlbumList().add(album);
                empresaNombreEmpresaNew = em.merge(empresaNombreEmpresaNew);
            }
            for (Cancion cancionListNewCancion : cancionListNew) {
                if (!cancionListOld.contains(cancionListNewCancion)) {
                    Album oldAlbumIdAlbumOfCancionListNewCancion = cancionListNewCancion.getAlbumIdAlbum();
                    cancionListNewCancion.setAlbumIdAlbum(album);
                    cancionListNewCancion = em.merge(cancionListNewCancion);
                    if (oldAlbumIdAlbumOfCancionListNewCancion != null && !oldAlbumIdAlbumOfCancionListNewCancion.equals(album)) {
                        oldAlbumIdAlbumOfCancionListNewCancion.getCancionList().remove(cancionListNewCancion);
                        oldAlbumIdAlbumOfCancionListNewCancion = em.merge(oldAlbumIdAlbumOfCancionListNewCancion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = album.getIdAlbum();
                if (findAlbum(id) == null) {
                    throw new NonexistentEntityException("The album with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Album album;
            try {
                album = em.getReference(Album.class, id);
                album.getIdAlbum();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The album with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cancion> cancionListOrphanCheck = album.getCancionList();
            for (Cancion cancionListOrphanCheckCancion : cancionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Album (" + album + ") cannot be destroyed since the Cancion " + cancionListOrphanCheckCancion + " in its cancionList field has a non-nullable albumIdAlbum field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empresa empresaNombreEmpresa = album.getEmpresaNombreEmpresa();
            if (empresaNombreEmpresa != null) {
                empresaNombreEmpresa.getAlbumList().remove(album);
                empresaNombreEmpresa = em.merge(empresaNombreEmpresa);
            }
            em.remove(album);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Album> findAlbumEntities() {
        return findAlbumEntities(true, -1, -1);
    }

    public List<Album> findAlbumEntities(int maxResults, int firstResult) {
        return findAlbumEntities(false, maxResults, firstResult);
    }

    private List<Album> findAlbumEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Album.class));
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

    public Album findAlbum(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Album.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlbumCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Album> rt = cq.from(Album.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
