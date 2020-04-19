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
import Entidades.Genero;
import Entidades.Generoxidioma;
import Entidades.GeneroxidiomaPK;
import Entidades.Idioma;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author julia
 */
public class GeneroxidiomaJpaController implements Serializable {

    public GeneroxidiomaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Generoxidioma generoxidioma) throws PreexistingEntityException, Exception {
        if (generoxidioma.getGeneroxidiomaPK() == null) {
            generoxidioma.setGeneroxidiomaPK(new GeneroxidiomaPK());
        }
        generoxidioma.getGeneroxidiomaPK().setGeneroCodigoGenero(generoxidioma.getGenero().getCodigoGenero());
        generoxidioma.getGeneroxidiomaPK().setIdiomaNombreIdioma(generoxidioma.getIdioma().getNombreIdioma());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Genero genero = generoxidioma.getGenero();
            if (genero != null) {
                genero = em.getReference(genero.getClass(), genero.getCodigoGenero());
                generoxidioma.setGenero(genero);
            }
            Idioma idioma = generoxidioma.getIdioma();
            if (idioma != null) {
                idioma = em.getReference(idioma.getClass(), idioma.getNombreIdioma());
                generoxidioma.setIdioma(idioma);
            }
            em.persist(generoxidioma);
            if (genero != null) {
                genero.getGeneroxidiomaList().add(generoxidioma);
                genero = em.merge(genero);
            }
            if (idioma != null) {
                idioma.getGeneroxidiomaList().add(generoxidioma);
                idioma = em.merge(idioma);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGeneroxidioma(generoxidioma.getGeneroxidiomaPK()) != null) {
                throw new PreexistingEntityException("Generoxidioma " + generoxidioma + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Generoxidioma generoxidioma) throws NonexistentEntityException, Exception {
        generoxidioma.getGeneroxidiomaPK().setGeneroCodigoGenero(generoxidioma.getGenero().getCodigoGenero());
        generoxidioma.getGeneroxidiomaPK().setIdiomaNombreIdioma(generoxidioma.getIdioma().getNombreIdioma());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Generoxidioma persistentGeneroxidioma = em.find(Generoxidioma.class, generoxidioma.getGeneroxidiomaPK());
            Genero generoOld = persistentGeneroxidioma.getGenero();
            Genero generoNew = generoxidioma.getGenero();
            Idioma idiomaOld = persistentGeneroxidioma.getIdioma();
            Idioma idiomaNew = generoxidioma.getIdioma();
            if (generoNew != null) {
                generoNew = em.getReference(generoNew.getClass(), generoNew.getCodigoGenero());
                generoxidioma.setGenero(generoNew);
            }
            if (idiomaNew != null) {
                idiomaNew = em.getReference(idiomaNew.getClass(), idiomaNew.getNombreIdioma());
                generoxidioma.setIdioma(idiomaNew);
            }
            generoxidioma = em.merge(generoxidioma);
            if (generoOld != null && !generoOld.equals(generoNew)) {
                generoOld.getGeneroxidiomaList().remove(generoxidioma);
                generoOld = em.merge(generoOld);
            }
            if (generoNew != null && !generoNew.equals(generoOld)) {
                generoNew.getGeneroxidiomaList().add(generoxidioma);
                generoNew = em.merge(generoNew);
            }
            if (idiomaOld != null && !idiomaOld.equals(idiomaNew)) {
                idiomaOld.getGeneroxidiomaList().remove(generoxidioma);
                idiomaOld = em.merge(idiomaOld);
            }
            if (idiomaNew != null && !idiomaNew.equals(idiomaOld)) {
                idiomaNew.getGeneroxidiomaList().add(generoxidioma);
                idiomaNew = em.merge(idiomaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                GeneroxidiomaPK id = generoxidioma.getGeneroxidiomaPK();
                if (findGeneroxidioma(id) == null) {
                    throw new NonexistentEntityException("The generoxidioma with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(GeneroxidiomaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Generoxidioma generoxidioma;
            try {
                generoxidioma = em.getReference(Generoxidioma.class, id);
                generoxidioma.getGeneroxidiomaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The generoxidioma with id " + id + " no longer exists.", enfe);
            }
            Genero genero = generoxidioma.getGenero();
            if (genero != null) {
                genero.getGeneroxidiomaList().remove(generoxidioma);
                genero = em.merge(genero);
            }
            Idioma idioma = generoxidioma.getIdioma();
            if (idioma != null) {
                idioma.getGeneroxidiomaList().remove(generoxidioma);
                idioma = em.merge(idioma);
            }
            em.remove(generoxidioma);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Generoxidioma> findGeneroxidiomaEntities() {
        return findGeneroxidiomaEntities(true, -1, -1);
    }

    public List<Generoxidioma> findGeneroxidiomaEntities(int maxResults, int firstResult) {
        return findGeneroxidiomaEntities(false, maxResults, firstResult);
    }

    private List<Generoxidioma> findGeneroxidiomaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Generoxidioma.class));
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

    public Generoxidioma findGeneroxidioma(GeneroxidiomaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Generoxidioma.class, id);
        } finally {
            em.close();
        }
    }

    public int getGeneroxidiomaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Generoxidioma> rt = cq.from(Generoxidioma.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
