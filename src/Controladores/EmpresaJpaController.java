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
import Entidades.Empresa;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author julia
 */
public class EmpresaJpaController implements Serializable {

    public EmpresaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresa empresa) throws PreexistingEntityException, Exception {
        if (empresa.getAlbumList() == null) {
            empresa.setAlbumList(new ArrayList<Album>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Album> attachedAlbumList = new ArrayList<Album>();
            for (Album albumListAlbumToAttach : empresa.getAlbumList()) {
                albumListAlbumToAttach = em.getReference(albumListAlbumToAttach.getClass(), albumListAlbumToAttach.getIdAlbum());
                attachedAlbumList.add(albumListAlbumToAttach);
            }
            empresa.setAlbumList(attachedAlbumList);
            em.persist(empresa);
            for (Album albumListAlbum : empresa.getAlbumList()) {
                Empresa oldEmpresaNombreEmpresaOfAlbumListAlbum = albumListAlbum.getEmpresaNombreEmpresa();
                albumListAlbum.setEmpresaNombreEmpresa(empresa);
                albumListAlbum = em.merge(albumListAlbum);
                if (oldEmpresaNombreEmpresaOfAlbumListAlbum != null) {
                    oldEmpresaNombreEmpresaOfAlbumListAlbum.getAlbumList().remove(albumListAlbum);
                    oldEmpresaNombreEmpresaOfAlbumListAlbum = em.merge(oldEmpresaNombreEmpresaOfAlbumListAlbum);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpresa(empresa.getNombreEmpresa()) != null) {
                throw new PreexistingEntityException("Empresa " + empresa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empresa empresa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa persistentEmpresa = em.find(Empresa.class, empresa.getNombreEmpresa());
            List<Album> albumListOld = persistentEmpresa.getAlbumList();
            List<Album> albumListNew = empresa.getAlbumList();
            List<String> illegalOrphanMessages = null;
            for (Album albumListOldAlbum : albumListOld) {
                if (!albumListNew.contains(albumListOldAlbum)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Album " + albumListOldAlbum + " since its empresaNombreEmpresa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Album> attachedAlbumListNew = new ArrayList<Album>();
            for (Album albumListNewAlbumToAttach : albumListNew) {
                albumListNewAlbumToAttach = em.getReference(albumListNewAlbumToAttach.getClass(), albumListNewAlbumToAttach.getIdAlbum());
                attachedAlbumListNew.add(albumListNewAlbumToAttach);
            }
            albumListNew = attachedAlbumListNew;
            empresa.setAlbumList(albumListNew);
            empresa = em.merge(empresa);
            for (Album albumListNewAlbum : albumListNew) {
                if (!albumListOld.contains(albumListNewAlbum)) {
                    Empresa oldEmpresaNombreEmpresaOfAlbumListNewAlbum = albumListNewAlbum.getEmpresaNombreEmpresa();
                    albumListNewAlbum.setEmpresaNombreEmpresa(empresa);
                    albumListNewAlbum = em.merge(albumListNewAlbum);
                    if (oldEmpresaNombreEmpresaOfAlbumListNewAlbum != null && !oldEmpresaNombreEmpresaOfAlbumListNewAlbum.equals(empresa)) {
                        oldEmpresaNombreEmpresaOfAlbumListNewAlbum.getAlbumList().remove(albumListNewAlbum);
                        oldEmpresaNombreEmpresaOfAlbumListNewAlbum = em.merge(oldEmpresaNombreEmpresaOfAlbumListNewAlbum);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = empresa.getNombreEmpresa();
                if (findEmpresa(id) == null) {
                    throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.");
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
            Empresa empresa;
            try {
                empresa = em.getReference(Empresa.class, id);
                empresa.getNombreEmpresa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Album> albumListOrphanCheck = empresa.getAlbumList();
            for (Album albumListOrphanCheckAlbum : albumListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresa (" + empresa + ") cannot be destroyed since the Album " + albumListOrphanCheckAlbum + " in its albumList field has a non-nullable empresaNombreEmpresa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empresa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empresa> findEmpresaEntities() {
        return findEmpresaEntities(true, -1, -1);
    }

    public List<Empresa> findEmpresaEntities(int maxResults, int firstResult) {
        return findEmpresaEntities(false, maxResults, firstResult);
    }

    private List<Empresa> findEmpresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresa.class));
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

    public Empresa findEmpresa(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empresa> rt = cq.from(Empresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
