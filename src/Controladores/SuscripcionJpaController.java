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
import Entidades.Familiar;
import Entidades.Usuario;
import Entidades.Individual;
import Entidades.Suscripcion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author julia
 */
public class SuscripcionJpaController implements Serializable {

    public SuscripcionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
/*
    public void create(Suscripcion suscripcion) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Usuario usuarioNicknameOrphanCheck = suscripcion.getUsuarioNickname();
        if (usuarioNicknameOrphanCheck != null) {
            Suscripcion oldSuscripcionOfUsuarioNickname = usuarioNicknameOrphanCheck.getSuscripcion();
            if (oldSuscripcionOfUsuarioNickname != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + usuarioNicknameOrphanCheck + " already has an item of type Suscripcion whose usuarioNickname column cannot be null. Please make another selection for the usuarioNickname field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Familiar familiar = suscripcion.getFamiliar();
            if (familiar != null) {
                familiar = em.getReference(familiar.getClass(), familiar.getSusIdSuscripcion());
                suscripcion.setFamiliar(familiar);
            }
            Usuario usuarioNickname = suscripcion.getUsuarioNickname();
            if (usuarioNickname != null) {
                usuarioNickname = em.getReference(usuarioNickname.getClass(), usuarioNickname.getNickname());
                suscripcion.setUsuarioNickname(usuarioNickname);
            }
            Individual individual = suscripcion.getIndividual();
            if (individual != null) {
                individual = em.getReference(individual.getClass(), individual.getSusIdSuscripcion());
                suscripcion.setIndividual(individual);
            }
            em.persist(suscripcion);
            if (familiar != null) {
                Suscripcion oldSuscripcionOfFamiliar = familiar.getSuscripcion();
                if (oldSuscripcionOfFamiliar != null) {
                    oldSuscripcionOfFamiliar.setFamiliar(null);
                    oldSuscripcionOfFamiliar = em.merge(oldSuscripcionOfFamiliar);
                }
                familiar.setSuscripcion(suscripcion);
                familiar = em.merge(familiar);
            }
            if (usuarioNickname != null) {
                usuarioNickname.setSuscripcion(suscripcion);
                usuarioNickname = em.merge(usuarioNickname);
            }
            if (individual != null) {
                Suscripcion oldSuscripcionOfIndividual = individual.getSuscripcion();
                if (oldSuscripcionOfIndividual != null) {
                    oldSuscripcionOfIndividual.setIndividual(null);
                    oldSuscripcionOfIndividual = em.merge(oldSuscripcionOfIndividual);
                }
                individual.setSuscripcion(suscripcion);
                individual = em.merge(individual);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSuscripcion(suscripcion.getSusIdSuscripcion()) != null) {
                throw new PreexistingEntityException("Suscripcion " + suscripcion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Suscripcion suscripcion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Suscripcion persistentSuscripcion = em.find(Suscripcion.class, suscripcion.getSusIdSuscripcion());
            Familiar familiarOld = persistentSuscripcion.getFamiliar();
            Familiar familiarNew = suscripcion.getFamiliar();
            Usuario usuarioNicknameOld = persistentSuscripcion.getUsuarioNickname();
            Usuario usuarioNicknameNew = suscripcion.getUsuarioNickname();
            Individual individualOld = persistentSuscripcion.getIndividual();
            Individual individualNew = suscripcion.getIndividual();
            List<String> illegalOrphanMessages = null;
            if (familiarOld != null && !familiarOld.equals(familiarNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Familiar " + familiarOld + " since its suscripcion field is not nullable.");
            }
            if (usuarioNicknameNew != null && !usuarioNicknameNew.equals(usuarioNicknameOld)) {
                Suscripcion oldSuscripcionOfUsuarioNickname = usuarioNicknameNew.getSuscripcion();
                if (oldSuscripcionOfUsuarioNickname != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + usuarioNicknameNew + " already has an item of type Suscripcion whose usuarioNickname column cannot be null. Please make another selection for the usuarioNickname field.");
                }
            }
            if (individualOld != null && !individualOld.equals(individualNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Individual " + individualOld + " since its suscripcion field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (familiarNew != null) {
                familiarNew = em.getReference(familiarNew.getClass(), familiarNew.getSusIdSuscripcion());
                suscripcion.setFamiliar(familiarNew);
            }
            if (usuarioNicknameNew != null) {
                usuarioNicknameNew = em.getReference(usuarioNicknameNew.getClass(), usuarioNicknameNew.getNickname());
                suscripcion.setUsuarioNickname(usuarioNicknameNew);
            }
            if (individualNew != null) {
                individualNew = em.getReference(individualNew.getClass(), individualNew.getSusIdSuscripcion());
                suscripcion.setIndividual(individualNew);
            }
            suscripcion = em.merge(suscripcion);
            if (familiarNew != null && !familiarNew.equals(familiarOld)) {
                Suscripcion oldSuscripcionOfFamiliar = familiarNew.getSuscripcion();
                if (oldSuscripcionOfFamiliar != null) {
                    oldSuscripcionOfFamiliar.setFamiliar(null);
                    oldSuscripcionOfFamiliar = em.merge(oldSuscripcionOfFamiliar);
                }
                familiarNew.setSuscripcion(suscripcion);
                familiarNew = em.merge(familiarNew);
            }
            if (usuarioNicknameOld != null && !usuarioNicknameOld.equals(usuarioNicknameNew)) {
                usuarioNicknameOld.setSuscripcion(null);
                usuarioNicknameOld = em.merge(usuarioNicknameOld);
            }
            if (usuarioNicknameNew != null && !usuarioNicknameNew.equals(usuarioNicknameOld)) {
                usuarioNicknameNew.setSuscripcion(suscripcion);
                usuarioNicknameNew = em.merge(usuarioNicknameNew);
            }
            if (individualNew != null && !individualNew.equals(individualOld)) {
                Suscripcion oldSuscripcionOfIndividual = individualNew.getSuscripcion();
                if (oldSuscripcionOfIndividual != null) {
                    oldSuscripcionOfIndividual.setIndividual(null);
                    oldSuscripcionOfIndividual = em.merge(oldSuscripcionOfIndividual);
                }
                individualNew.setSuscripcion(suscripcion);
                individualNew = em.merge(individualNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = suscripcion.getSusIdSuscripcion();
                if (findSuscripcion(id) == null) {
                    throw new NonexistentEntityException("The suscripcion with id " + id + " no longer exists.");
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
            Suscripcion suscripcion;
            try {
                suscripcion = em.getReference(Suscripcion.class, id);
                suscripcion.getSusIdSuscripcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suscripcion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Familiar familiarOrphanCheck = suscripcion.getFamiliar();
            if (familiarOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Suscripcion (" + suscripcion + ") cannot be destroyed since the Familiar " + familiarOrphanCheck + " in its familiar field has a non-nullable suscripcion field.");
            }
            Individual individualOrphanCheck = suscripcion.getIndividual();
            if (individualOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Suscripcion (" + suscripcion + ") cannot be destroyed since the Individual " + individualOrphanCheck + " in its individual field has a non-nullable suscripcion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuarioNickname = suscripcion.getUsuarioNickname();
            if (usuarioNickname != null) {
                usuarioNickname.setSuscripcion(null);
                usuarioNickname = em.merge(usuarioNickname);
            }
            em.remove(suscripcion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
*/
    public List<Suscripcion> findSuscripcionEntities() {
        return findSuscripcionEntities(true, -1, -1);
    }

    public List<Suscripcion> findSuscripcionEntities(int maxResults, int firstResult) {
        return findSuscripcionEntities(false, maxResults, firstResult);
    }

    private List<Suscripcion> findSuscripcionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Suscripcion.class));
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

    public Suscripcion findSuscripcion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Suscripcion.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuscripcionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Suscripcion> rt = cq.from(Suscripcion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
