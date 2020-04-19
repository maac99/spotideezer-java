/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Cancion;
import Entidades.Cancionxlistasr;
import Entidades.CancionxlistasrPK;
import Entidades.ListaR;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author julia
 */
public class CancionxlistasrJpaController implements Serializable {

    public CancionxlistasrJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cancionxlistasr cancionxlistasr) throws PreexistingEntityException, Exception {
        if (cancionxlistasr.getCancionxlistasrPK() == null) {
            cancionxlistasr.setCancionxlistasrPK(new CancionxlistasrPK());
        }
        cancionxlistasr.getCancionxlistasrPK().setListaRNickname(cancionxlistasr.getListaR().getListaRPK().getUsuarioNickname());
        cancionxlistasr.getCancionxlistasrPK().setListaRIdListar(cancionxlistasr.getListaR().getListaRPK().getIdListar());
        cancionxlistasr.getCancionxlistasrPK().setCancionTituloC(cancionxlistasr.getCancion().getCancionPK().getTituloC());
        cancionxlistasr.getCancionxlistasrPK().setCancionIdIn(cancionxlistasr.getCancion().getCancionPK().getInterpreteIdIn());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancion cancion = cancionxlistasr.getCancion();
            if (cancion != null) {
                cancion = em.getReference(cancion.getClass(), cancion.getCancionPK());
                cancionxlistasr.setCancion(cancion);
            }
            ListaR listaR = cancionxlistasr.getListaR();
            if (listaR != null) {
                listaR = em.getReference(listaR.getClass(), listaR.getListaRPK());
                cancionxlistasr.setListaR(listaR);
            }
            em.persist(cancionxlistasr);
            if (cancion != null) {
                cancion.getCancionxlistasrList().add(cancionxlistasr);
                cancion = em.merge(cancion);
            }
            if (listaR != null) {
                listaR.getCancionxlistasrList().add(cancionxlistasr);
                listaR = em.merge(listaR);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCancionxlistasr(cancionxlistasr.getCancionxlistasrPK()) != null) {
                throw new PreexistingEntityException("Cancionxlistasr " + cancionxlistasr + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cancionxlistasr cancionxlistasr) throws NonexistentEntityException, Exception {
        cancionxlistasr.getCancionxlistasrPK().setListaRNickname(cancionxlistasr.getListaR().getListaRPK().getUsuarioNickname());
        cancionxlistasr.getCancionxlistasrPK().setListaRIdListar(cancionxlistasr.getListaR().getListaRPK().getIdListar());
        cancionxlistasr.getCancionxlistasrPK().setCancionTituloC(cancionxlistasr.getCancion().getCancionPK().getTituloC());
        cancionxlistasr.getCancionxlistasrPK().setCancionIdIn(cancionxlistasr.getCancion().getCancionPK().getInterpreteIdIn());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancionxlistasr persistentCancionxlistasr = em.find(Cancionxlistasr.class, cancionxlistasr.getCancionxlistasrPK());
            Cancion cancionOld = persistentCancionxlistasr.getCancion();
            Cancion cancionNew = cancionxlistasr.getCancion();
            ListaR listaROld = persistentCancionxlistasr.getListaR();
            ListaR listaRNew = cancionxlistasr.getListaR();
            if (cancionNew != null) {
                cancionNew = em.getReference(cancionNew.getClass(), cancionNew.getCancionPK());
                cancionxlistasr.setCancion(cancionNew);
            }
            if (listaRNew != null) {
                listaRNew = em.getReference(listaRNew.getClass(), listaRNew.getListaRPK());
                cancionxlistasr.setListaR(listaRNew);
            }
            cancionxlistasr = em.merge(cancionxlistasr);
            if (cancionOld != null && !cancionOld.equals(cancionNew)) {
                cancionOld.getCancionxlistasrList().remove(cancionxlistasr);
                cancionOld = em.merge(cancionOld);
            }
            if (cancionNew != null && !cancionNew.equals(cancionOld)) {
                cancionNew.getCancionxlistasrList().add(cancionxlistasr);
                cancionNew = em.merge(cancionNew);
            }
            if (listaROld != null && !listaROld.equals(listaRNew)) {
                listaROld.getCancionxlistasrList().remove(cancionxlistasr);
                listaROld = em.merge(listaROld);
            }
            if (listaRNew != null && !listaRNew.equals(listaROld)) {
                listaRNew.getCancionxlistasrList().add(cancionxlistasr);
                listaRNew = em.merge(listaRNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CancionxlistasrPK id = cancionxlistasr.getCancionxlistasrPK();
                if (findCancionxlistasr(id) == null) {
                    throw new NonexistentEntityException("The cancionxlistasr with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CancionxlistasrPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cancionxlistasr cancionxlistasr;
            try {
                cancionxlistasr = em.getReference(Cancionxlistasr.class, id);
                cancionxlistasr.getCancionxlistasrPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cancionxlistasr with id " + id + " no longer exists.", enfe);
            }
            Cancion cancion = cancionxlistasr.getCancion();
            if (cancion != null) {
                cancion.getCancionxlistasrList().remove(cancionxlistasr);
                cancion = em.merge(cancion);
            }
            ListaR listaR = cancionxlistasr.getListaR();
            if (listaR != null) {
                listaR.getCancionxlistasrList().remove(cancionxlistasr);
                listaR = em.merge(listaR);
            }
            em.remove(cancionxlistasr);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cancionxlistasr> findCancionxlistasrEntities() {
        return findCancionxlistasrEntities(true, -1, -1);
    }

    public List<Cancionxlistasr> findCancionxlistasrEntities(int maxResults, int firstResult) {
        return findCancionxlistasrEntities(false, maxResults, firstResult);
    }

    private List<Cancionxlistasr> findCancionxlistasrEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cancionxlistasr.class));
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

    public Cancionxlistasr findCancionxlistasr(CancionxlistasrPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cancionxlistasr.class, id);
        } finally {
            em.close();
        }
    }

    public int getCancionxlistasrCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cancionxlistasr> rt = cq.from(Cancionxlistasr.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
