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
import Entidades.Cancion;
import Entidades.Genero;
import java.util.ArrayList;
import java.util.List;
import Entidades.Generoxidioma;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author julia
 */
public class GeneroJpaController implements Serializable {

    public GeneroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Genero genero) throws PreexistingEntityException, Exception {
        if (genero.getCancionList() == null) {
            genero.setCancionList(new ArrayList<Cancion>());
        }
        if (genero.getGeneroxidiomaList() == null) {
            genero.setGeneroxidiomaList(new ArrayList<Generoxidioma>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cancion> attachedCancionList = new ArrayList<Cancion>();
            for (Cancion cancionListCancionToAttach : genero.getCancionList()) {
                cancionListCancionToAttach = em.getReference(cancionListCancionToAttach.getClass(), cancionListCancionToAttach.getCancionPK());
                attachedCancionList.add(cancionListCancionToAttach);
            }
            genero.setCancionList(attachedCancionList);
            List<Generoxidioma> attachedGeneroxidiomaList = new ArrayList<Generoxidioma>();
            for (Generoxidioma generoxidiomaListGeneroxidiomaToAttach : genero.getGeneroxidiomaList()) {
                generoxidiomaListGeneroxidiomaToAttach = em.getReference(generoxidiomaListGeneroxidiomaToAttach.getClass(), generoxidiomaListGeneroxidiomaToAttach.getGeneroxidiomaPK());
                attachedGeneroxidiomaList.add(generoxidiomaListGeneroxidiomaToAttach);
            }
            genero.setGeneroxidiomaList(attachedGeneroxidiomaList);
            em.persist(genero);
            for (Cancion cancionListCancion : genero.getCancionList()) {
                Genero oldGeneroCodigoGeneroOfCancionListCancion = cancionListCancion.getGeneroCodigoGenero();
                cancionListCancion.setGeneroCodigoGenero(genero);
                cancionListCancion = em.merge(cancionListCancion);
                if (oldGeneroCodigoGeneroOfCancionListCancion != null) {
                    oldGeneroCodigoGeneroOfCancionListCancion.getCancionList().remove(cancionListCancion);
                    oldGeneroCodigoGeneroOfCancionListCancion = em.merge(oldGeneroCodigoGeneroOfCancionListCancion);
                }
            }
            for (Generoxidioma generoxidiomaListGeneroxidioma : genero.getGeneroxidiomaList()) {
                Genero oldGeneroOfGeneroxidiomaListGeneroxidioma = generoxidiomaListGeneroxidioma.getGenero();
                generoxidiomaListGeneroxidioma.setGenero(genero);
                generoxidiomaListGeneroxidioma = em.merge(generoxidiomaListGeneroxidioma);
                if (oldGeneroOfGeneroxidiomaListGeneroxidioma != null) {
                    oldGeneroOfGeneroxidiomaListGeneroxidioma.getGeneroxidiomaList().remove(generoxidiomaListGeneroxidioma);
                    oldGeneroOfGeneroxidiomaListGeneroxidioma = em.merge(oldGeneroOfGeneroxidiomaListGeneroxidioma);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGenero(genero.getCodigoGenero()) != null) {
                throw new PreexistingEntityException("Genero " + genero + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Genero genero) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genero persistentGenero = em.find(Genero.class, genero.getCodigoGenero());
            List<Cancion> cancionListOld = persistentGenero.getCancionList();
            List<Cancion> cancionListNew = genero.getCancionList();
            List<Generoxidioma> generoxidiomaListOld = persistentGenero.getGeneroxidiomaList();
            List<Generoxidioma> generoxidiomaListNew = genero.getGeneroxidiomaList();
            List<String> illegalOrphanMessages = null;
            for (Cancion cancionListOldCancion : cancionListOld) {
                if (!cancionListNew.contains(cancionListOldCancion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cancion " + cancionListOldCancion + " since its generoCodigoGenero field is not nullable.");
                }
            }
            for (Generoxidioma generoxidiomaListOldGeneroxidioma : generoxidiomaListOld) {
                if (!generoxidiomaListNew.contains(generoxidiomaListOldGeneroxidioma)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Generoxidioma " + generoxidiomaListOldGeneroxidioma + " since its genero field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Cancion> attachedCancionListNew = new ArrayList<Cancion>();
            for (Cancion cancionListNewCancionToAttach : cancionListNew) {
                cancionListNewCancionToAttach = em.getReference(cancionListNewCancionToAttach.getClass(), cancionListNewCancionToAttach.getCancionPK());
                attachedCancionListNew.add(cancionListNewCancionToAttach);
            }
            cancionListNew = attachedCancionListNew;
            genero.setCancionList(cancionListNew);
            List<Generoxidioma> attachedGeneroxidiomaListNew = new ArrayList<Generoxidioma>();
            for (Generoxidioma generoxidiomaListNewGeneroxidiomaToAttach : generoxidiomaListNew) {
                generoxidiomaListNewGeneroxidiomaToAttach = em.getReference(generoxidiomaListNewGeneroxidiomaToAttach.getClass(), generoxidiomaListNewGeneroxidiomaToAttach.getGeneroxidiomaPK());
                attachedGeneroxidiomaListNew.add(generoxidiomaListNewGeneroxidiomaToAttach);
            }
            generoxidiomaListNew = attachedGeneroxidiomaListNew;
            genero.setGeneroxidiomaList(generoxidiomaListNew);
            genero = em.merge(genero);
            for (Cancion cancionListNewCancion : cancionListNew) {
                if (!cancionListOld.contains(cancionListNewCancion)) {
                    Genero oldGeneroCodigoGeneroOfCancionListNewCancion = cancionListNewCancion.getGeneroCodigoGenero();
                    cancionListNewCancion.setGeneroCodigoGenero(genero);
                    cancionListNewCancion = em.merge(cancionListNewCancion);
                    if (oldGeneroCodigoGeneroOfCancionListNewCancion != null && !oldGeneroCodigoGeneroOfCancionListNewCancion.equals(genero)) {
                        oldGeneroCodigoGeneroOfCancionListNewCancion.getCancionList().remove(cancionListNewCancion);
                        oldGeneroCodigoGeneroOfCancionListNewCancion = em.merge(oldGeneroCodigoGeneroOfCancionListNewCancion);
                    }
                }
            }
            for (Generoxidioma generoxidiomaListNewGeneroxidioma : generoxidiomaListNew) {
                if (!generoxidiomaListOld.contains(generoxidiomaListNewGeneroxidioma)) {
                    Genero oldGeneroOfGeneroxidiomaListNewGeneroxidioma = generoxidiomaListNewGeneroxidioma.getGenero();
                    generoxidiomaListNewGeneroxidioma.setGenero(genero);
                    generoxidiomaListNewGeneroxidioma = em.merge(generoxidiomaListNewGeneroxidioma);
                    if (oldGeneroOfGeneroxidiomaListNewGeneroxidioma != null && !oldGeneroOfGeneroxidiomaListNewGeneroxidioma.equals(genero)) {
                        oldGeneroOfGeneroxidiomaListNewGeneroxidioma.getGeneroxidiomaList().remove(generoxidiomaListNewGeneroxidioma);
                        oldGeneroOfGeneroxidiomaListNewGeneroxidioma = em.merge(oldGeneroOfGeneroxidiomaListNewGeneroxidioma);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = genero.getCodigoGenero();
                if (findGenero(id) == null) {
                    throw new NonexistentEntityException("The genero with id " + id + " no longer exists.");
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
            Genero genero;
            try {
                genero = em.getReference(Genero.class, id);
                genero.getCodigoGenero();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The genero with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cancion> cancionListOrphanCheck = genero.getCancionList();
            for (Cancion cancionListOrphanCheckCancion : cancionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Genero (" + genero + ") cannot be destroyed since the Cancion " + cancionListOrphanCheckCancion + " in its cancionList field has a non-nullable generoCodigoGenero field.");
            }
            List<Generoxidioma> generoxidiomaListOrphanCheck = genero.getGeneroxidiomaList();
            for (Generoxidioma generoxidiomaListOrphanCheckGeneroxidioma : generoxidiomaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Genero (" + genero + ") cannot be destroyed since the Generoxidioma " + generoxidiomaListOrphanCheckGeneroxidioma + " in its generoxidiomaList field has a non-nullable genero field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(genero);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Genero> findGeneroEntities() {
        return findGeneroEntities(true, -1, -1);
    }

    public List<Genero> findGeneroEntities(int maxResults, int firstResult) {
        return findGeneroEntities(false, maxResults, firstResult);
    }

    private List<Genero> findGeneroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Genero.class));
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

    public Genero findGenero(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Genero.class, id);
        } finally {
            em.close();
        }
    }

    public int getGeneroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Genero> rt = cq.from(Genero.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
