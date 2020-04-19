/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Cxi;
import Entidades.CxiPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Interprete;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author julia
 */
public class CxiJpaController implements Serializable {

    public CxiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cxi cxi) throws PreexistingEntityException, Exception {
        if (cxi.getCxiPK() == null) {
            cxi.setCxiPK(new CxiPK());
        }
        cxi.getCxiPK().setInterpreteIdIn(cxi.getInterprete().getInterpreteIdIn());
        cxi.getCxiPK().setCancionTituloC(cxi.getCancion().getCancionPK().getTituloC());
        cxi.getCxiPK().setCancionIdIn(cxi.getCancion().getCancionPK().getInterpreteIdIn());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Interprete interprete = cxi.getInterprete();
            if (interprete != null) {
                interprete = em.getReference(interprete.getClass(), interprete.getInterpreteIdIn());
                cxi.setInterprete(interprete);
            }
            em.persist(cxi);
            if (interprete != null) {
                interprete.getCxiList().add(cxi);
                interprete = em.merge(interprete);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCxi(cxi.getCxiPK()) != null) {
                throw new PreexistingEntityException("Cxi " + cxi + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cxi cxi) throws NonexistentEntityException, Exception {
        cxi.getCxiPK().setInterpreteIdIn(cxi.getInterprete().getInterpreteIdIn());
        cxi.getCxiPK().setCancionTituloC(cxi.getCancion().getCancionPK().getTituloC());
        cxi.getCxiPK().setCancionIdIn(cxi.getCancion().getCancionPK().getInterpreteIdIn());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cxi persistentCxi = em.find(Cxi.class, cxi.getCxiPK());
            Interprete interpreteOld = persistentCxi.getInterprete();
            Interprete interpreteNew = cxi.getInterprete();
            if (interpreteNew != null) {
                interpreteNew = em.getReference(interpreteNew.getClass(), interpreteNew.getInterpreteIdIn());
                cxi.setInterprete(interpreteNew);
            }
            cxi = em.merge(cxi);
            if (interpreteOld != null && !interpreteOld.equals(interpreteNew)) {
                interpreteOld.getCxiList().remove(cxi);
                interpreteOld = em.merge(interpreteOld);
            }
            if (interpreteNew != null && !interpreteNew.equals(interpreteOld)) {
                interpreteNew.getCxiList().add(cxi);
                interpreteNew = em.merge(interpreteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CxiPK id = cxi.getCxiPK();
                if (findCxi(id) == null) {
                    throw new NonexistentEntityException("The cxi with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CxiPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cxi cxi;
            try {
                cxi = em.getReference(Cxi.class, id);
                cxi.getCxiPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cxi with id " + id + " no longer exists.", enfe);
            }
            Interprete interprete = cxi.getInterprete();
            if (interprete != null) {
                interprete.getCxiList().remove(cxi);
                interprete = em.merge(interprete);
            }
            em.remove(cxi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cxi> findCxiEntities() {
        return findCxiEntities(true, -1, -1);
    }

    public List<Cxi> findCxiEntities(int maxResults, int firstResult) {
        return findCxiEntities(false, maxResults, firstResult);
    }

    private List<Cxi> findCxiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cxi.class));
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

    public Cxi findCxi(CxiPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cxi.class, id);
        } finally {
            em.close();
        }
    }

    public int getCxiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cxi> rt = cq.from(Cxi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
