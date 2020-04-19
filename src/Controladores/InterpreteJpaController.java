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
import Entidades.Pais;
import Entidades.Cancion;
import Entidades.Interprete;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author julia
 */
public class InterpreteJpaController implements Serializable {

    public InterpreteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Interprete interprete) throws PreexistingEntityException, Exception {
        if (interprete.getCancionList() == null) {
            interprete.setCancionList(new ArrayList<Cancion>());
        }
        if (interprete.getCancionList1() == null) {
            interprete.setCancionList1(new ArrayList<Cancion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais paisNombrePais = interprete.getPaisNombrePais();
            if (paisNombrePais != null) {
                paisNombrePais = em.getReference(paisNombrePais.getClass(), paisNombrePais.getNombrePais());
                interprete.setPaisNombrePais(paisNombrePais);
            }
            List<Cancion> attachedCancionList = new ArrayList<Cancion>();
            for (Cancion cancionListCancionToAttach : interprete.getCancionList()) {
                cancionListCancionToAttach = em.getReference(cancionListCancionToAttach.getClass(), cancionListCancionToAttach.getCancionPK());
                attachedCancionList.add(cancionListCancionToAttach);
            }
            interprete.setCancionList(attachedCancionList);
            List<Cancion> attachedCancionList1 = new ArrayList<Cancion>();
            for (Cancion cancionList1CancionToAttach : interprete.getCancionList1()) {
                cancionList1CancionToAttach = em.getReference(cancionList1CancionToAttach.getClass(), cancionList1CancionToAttach.getCancionPK());
                attachedCancionList1.add(cancionList1CancionToAttach);
            }
            interprete.setCancionList1(attachedCancionList1);
            em.persist(interprete);
            if (paisNombrePais != null) {
                paisNombrePais.getInterpreteList().add(interprete);
                paisNombrePais = em.merge(paisNombrePais);
            }
            for (Cancion cancionListCancion : interprete.getCancionList()) {
                cancionListCancion.getInterpreteList().add(interprete);
                cancionListCancion = em.merge(cancionListCancion);
            }
            for (Cancion cancionList1Cancion : interprete.getCancionList1()) {
                Interprete oldInterpreteOfCancionList1Cancion = cancionList1Cancion.getInterprete();
                cancionList1Cancion.setInterprete(interprete);
                cancionList1Cancion = em.merge(cancionList1Cancion);
                if (oldInterpreteOfCancionList1Cancion != null) {
                    oldInterpreteOfCancionList1Cancion.getCancionList1().remove(cancionList1Cancion);
                    oldInterpreteOfCancionList1Cancion = em.merge(oldInterpreteOfCancionList1Cancion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findInterprete(interprete.getInterpreteIdIn()) != null) {
                throw new PreexistingEntityException("Interprete " + interprete + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Interprete interprete) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Interprete persistentInterprete = em.find(Interprete.class, interprete.getInterpreteIdIn());
            Pais paisNombrePaisOld = persistentInterprete.getPaisNombrePais();
            Pais paisNombrePaisNew = interprete.getPaisNombrePais();
            List<Cancion> cancionListOld = persistentInterprete.getCancionList();
            List<Cancion> cancionListNew = interprete.getCancionList();
            List<Cancion> cancionList1Old = persistentInterprete.getCancionList1();
            List<Cancion> cancionList1New = interprete.getCancionList1();
            List<String> illegalOrphanMessages = null;
            for (Cancion cancionList1OldCancion : cancionList1Old) {
                if (!cancionList1New.contains(cancionList1OldCancion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cancion " + cancionList1OldCancion + " since its interprete field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (paisNombrePaisNew != null) {
                paisNombrePaisNew = em.getReference(paisNombrePaisNew.getClass(), paisNombrePaisNew.getNombrePais());
                interprete.setPaisNombrePais(paisNombrePaisNew);
            }
            List<Cancion> attachedCancionListNew = new ArrayList<Cancion>();
            for (Cancion cancionListNewCancionToAttach : cancionListNew) {
                cancionListNewCancionToAttach = em.getReference(cancionListNewCancionToAttach.getClass(), cancionListNewCancionToAttach.getCancionPK());
                attachedCancionListNew.add(cancionListNewCancionToAttach);
            }
            cancionListNew = attachedCancionListNew;
            interprete.setCancionList(cancionListNew);
            List<Cancion> attachedCancionList1New = new ArrayList<Cancion>();
            for (Cancion cancionList1NewCancionToAttach : cancionList1New) {
                cancionList1NewCancionToAttach = em.getReference(cancionList1NewCancionToAttach.getClass(), cancionList1NewCancionToAttach.getCancionPK());
                attachedCancionList1New.add(cancionList1NewCancionToAttach);
            }
            cancionList1New = attachedCancionList1New;
            interprete.setCancionList1(cancionList1New);
            interprete = em.merge(interprete);
            if (paisNombrePaisOld != null && !paisNombrePaisOld.equals(paisNombrePaisNew)) {
                paisNombrePaisOld.getInterpreteList().remove(interprete);
                paisNombrePaisOld = em.merge(paisNombrePaisOld);
            }
            if (paisNombrePaisNew != null && !paisNombrePaisNew.equals(paisNombrePaisOld)) {
                paisNombrePaisNew.getInterpreteList().add(interprete);
                paisNombrePaisNew = em.merge(paisNombrePaisNew);
            }
            for (Cancion cancionListOldCancion : cancionListOld) {
                if (!cancionListNew.contains(cancionListOldCancion)) {
                    cancionListOldCancion.getInterpreteList().remove(interprete);
                    cancionListOldCancion = em.merge(cancionListOldCancion);
                }
            }
            for (Cancion cancionListNewCancion : cancionListNew) {
                if (!cancionListOld.contains(cancionListNewCancion)) {
                    cancionListNewCancion.getInterpreteList().add(interprete);
                    cancionListNewCancion = em.merge(cancionListNewCancion);
                }
            }
            for (Cancion cancionList1NewCancion : cancionList1New) {
                if (!cancionList1Old.contains(cancionList1NewCancion)) {
                    Interprete oldInterpreteOfCancionList1NewCancion = cancionList1NewCancion.getInterprete();
                    cancionList1NewCancion.setInterprete(interprete);
                    cancionList1NewCancion = em.merge(cancionList1NewCancion);
                    if (oldInterpreteOfCancionList1NewCancion != null && !oldInterpreteOfCancionList1NewCancion.equals(interprete)) {
                        oldInterpreteOfCancionList1NewCancion.getCancionList1().remove(cancionList1NewCancion);
                        oldInterpreteOfCancionList1NewCancion = em.merge(oldInterpreteOfCancionList1NewCancion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = interprete.getInterpreteIdIn();
                if (findInterprete(id) == null) {
                    throw new NonexistentEntityException("The interprete with id " + id + " no longer exists.");
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
            Interprete interprete;
            try {
                interprete = em.getReference(Interprete.class, id);
                interprete.getInterpreteIdIn();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The interprete with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cancion> cancionList1OrphanCheck = interprete.getCancionList1();
            for (Cancion cancionList1OrphanCheckCancion : cancionList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Interprete (" + interprete + ") cannot be destroyed since the Cancion " + cancionList1OrphanCheckCancion + " in its cancionList1 field has a non-nullable interprete field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pais paisNombrePais = interprete.getPaisNombrePais();
            if (paisNombrePais != null) {
                paisNombrePais.getInterpreteList().remove(interprete);
                paisNombrePais = em.merge(paisNombrePais);
            }
            List<Cancion> cancionList = interprete.getCancionList();
            for (Cancion cancionListCancion : cancionList) {
                cancionListCancion.getInterpreteList().remove(interprete);
                cancionListCancion = em.merge(cancionListCancion);
            }
            em.remove(interprete);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Interprete> findInterpreteEntities() {
        return findInterpreteEntities(true, -1, -1);
    }

    public List<Interprete> findInterpreteEntities(int maxResults, int firstResult) {
        return findInterpreteEntities(false, maxResults, firstResult);
    }

    private List<Interprete> findInterpreteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Interprete.class));
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

    public Interprete findInterprete(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Interprete.class, id);
        } finally {
            em.close();
        }
    }

    public int getInterpreteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Interprete> rt = cq.from(Interprete.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
