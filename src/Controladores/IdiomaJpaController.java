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
import Entidades.Generoxidioma;
import Entidades.Idioma;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author julia
 */
public class IdiomaJpaController implements Serializable {

    public IdiomaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Idioma idioma) throws PreexistingEntityException, Exception {
        if (idioma.getGeneroxidiomaList() == null) {
            idioma.setGeneroxidiomaList(new ArrayList<Generoxidioma>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Generoxidioma> attachedGeneroxidiomaList = new ArrayList<Generoxidioma>();
            for (Generoxidioma generoxidiomaListGeneroxidiomaToAttach : idioma.getGeneroxidiomaList()) {
                generoxidiomaListGeneroxidiomaToAttach = em.getReference(generoxidiomaListGeneroxidiomaToAttach.getClass(), generoxidiomaListGeneroxidiomaToAttach.getGeneroxidiomaPK());
                attachedGeneroxidiomaList.add(generoxidiomaListGeneroxidiomaToAttach);
            }
            idioma.setGeneroxidiomaList(attachedGeneroxidiomaList);
            em.persist(idioma);
            for (Generoxidioma generoxidiomaListGeneroxidioma : idioma.getGeneroxidiomaList()) {
                Idioma oldIdiomaOfGeneroxidiomaListGeneroxidioma = generoxidiomaListGeneroxidioma.getIdioma();
                generoxidiomaListGeneroxidioma.setIdioma(idioma);
                generoxidiomaListGeneroxidioma = em.merge(generoxidiomaListGeneroxidioma);
                if (oldIdiomaOfGeneroxidiomaListGeneroxidioma != null) {
                    oldIdiomaOfGeneroxidiomaListGeneroxidioma.getGeneroxidiomaList().remove(generoxidiomaListGeneroxidioma);
                    oldIdiomaOfGeneroxidiomaListGeneroxidioma = em.merge(oldIdiomaOfGeneroxidiomaListGeneroxidioma);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIdioma(idioma.getNombreIdioma()) != null) {
                throw new PreexistingEntityException("Idioma " + idioma + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Idioma idioma) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Idioma persistentIdioma = em.find(Idioma.class, idioma.getNombreIdioma());
            List<Generoxidioma> generoxidiomaListOld = persistentIdioma.getGeneroxidiomaList();
            List<Generoxidioma> generoxidiomaListNew = idioma.getGeneroxidiomaList();
            List<String> illegalOrphanMessages = null;
            for (Generoxidioma generoxidiomaListOldGeneroxidioma : generoxidiomaListOld) {
                if (!generoxidiomaListNew.contains(generoxidiomaListOldGeneroxidioma)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Generoxidioma " + generoxidiomaListOldGeneroxidioma + " since its idioma field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Generoxidioma> attachedGeneroxidiomaListNew = new ArrayList<Generoxidioma>();
            for (Generoxidioma generoxidiomaListNewGeneroxidiomaToAttach : generoxidiomaListNew) {
                generoxidiomaListNewGeneroxidiomaToAttach = em.getReference(generoxidiomaListNewGeneroxidiomaToAttach.getClass(), generoxidiomaListNewGeneroxidiomaToAttach.getGeneroxidiomaPK());
                attachedGeneroxidiomaListNew.add(generoxidiomaListNewGeneroxidiomaToAttach);
            }
            generoxidiomaListNew = attachedGeneroxidiomaListNew;
            idioma.setGeneroxidiomaList(generoxidiomaListNew);
            idioma = em.merge(idioma);
            for (Generoxidioma generoxidiomaListNewGeneroxidioma : generoxidiomaListNew) {
                if (!generoxidiomaListOld.contains(generoxidiomaListNewGeneroxidioma)) {
                    Idioma oldIdiomaOfGeneroxidiomaListNewGeneroxidioma = generoxidiomaListNewGeneroxidioma.getIdioma();
                    generoxidiomaListNewGeneroxidioma.setIdioma(idioma);
                    generoxidiomaListNewGeneroxidioma = em.merge(generoxidiomaListNewGeneroxidioma);
                    if (oldIdiomaOfGeneroxidiomaListNewGeneroxidioma != null && !oldIdiomaOfGeneroxidiomaListNewGeneroxidioma.equals(idioma)) {
                        oldIdiomaOfGeneroxidiomaListNewGeneroxidioma.getGeneroxidiomaList().remove(generoxidiomaListNewGeneroxidioma);
                        oldIdiomaOfGeneroxidiomaListNewGeneroxidioma = em.merge(oldIdiomaOfGeneroxidiomaListNewGeneroxidioma);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = idioma.getNombreIdioma();
                if (findIdioma(id) == null) {
                    throw new NonexistentEntityException("The idioma with id " + id + " no longer exists.");
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
            Idioma idioma;
            try {
                idioma = em.getReference(Idioma.class, id);
                idioma.getNombreIdioma();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The idioma with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Generoxidioma> generoxidiomaListOrphanCheck = idioma.getGeneroxidiomaList();
            for (Generoxidioma generoxidiomaListOrphanCheckGeneroxidioma : generoxidiomaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Idioma (" + idioma + ") cannot be destroyed since the Generoxidioma " + generoxidiomaListOrphanCheckGeneroxidioma + " in its generoxidiomaList field has a non-nullable idioma field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(idioma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Idioma> findIdiomaEntities() {
        return findIdiomaEntities(true, -1, -1);
    }

    public List<Idioma> findIdiomaEntities(int maxResults, int firstResult) {
        return findIdiomaEntities(false, maxResults, firstResult);
    }

    private List<Idioma> findIdiomaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Idioma.class));
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

    public Idioma findIdioma(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Idioma.class, id);
        } finally {
            em.close();
        }
    }

    public int getIdiomaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Idioma> rt = cq.from(Idioma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
