/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Auditoria;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author julia
 */
public class AuditoriaJpaController implements Serializable {

    public AuditoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Auditoria auditoria) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(auditoria);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAuditoria(auditoria.getIdentificador()) != null) {
                throw new PreexistingEntityException("Auditoria " + auditoria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Auditoria auditoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            auditoria = em.merge(auditoria);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = auditoria.getIdentificador();
                if (findAuditoria(id) == null) {
                    throw new NonexistentEntityException("The auditoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Auditoria auditoria;
            try {
                auditoria = em.getReference(Auditoria.class, id);
                auditoria.getIdentificador();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The auditoria with id " + id + " no longer exists.", enfe);
            }
            em.remove(auditoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Auditoria> findAuditoriaEntities() {
        return findAuditoriaEntities(true, -1, -1);
    }

    public List<Auditoria> findAuditoriaEntities(int maxResults, int firstResult) {
        return findAuditoriaEntities(false, maxResults, firstResult);
    }

    private List<Auditoria> findAuditoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Auditoria.class));
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

    public Auditoria findAuditoria(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Auditoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getAuditoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Auditoria> rt = cq.from(Auditoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
