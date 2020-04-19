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
import Entidades.Usuario;
import Entidades.Cancionxlistasr;
import Entidades.ListaR;
import Entidades.ListaRPK;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author julia
 */
public class ListaRJpaController implements Serializable {

    public ListaRJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
/*
    public void create(ListaR listaR) throws PreexistingEntityException, Exception {
        if (listaR.getListaRPK() == null) {
            listaR.setListaRPK(new ListaRPK());
        }
        if (listaR.getCancionxlistasrList() == null) {
            listaR.setCancionxlistasrList(new ArrayList<Cancionxlistasr>());
        }
        listaR.getListaRPK().setUsuarioNickname(listaR.getUsuario().getNickname());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = listaR.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getNickname());
                listaR.setUsuario(usuario);
            }
            List<Cancionxlistasr> attachedCancionxlistasrList = new ArrayList<Cancionxlistasr>();
            for (Cancionxlistasr cancionxlistasrListCancionxlistasrToAttach : listaR.getCancionxlistasrList()) {
                cancionxlistasrListCancionxlistasrToAttach = em.getReference(cancionxlistasrListCancionxlistasrToAttach.getClass(), cancionxlistasrListCancionxlistasrToAttach.getCancionxlistasrPK());
                attachedCancionxlistasrList.add(cancionxlistasrListCancionxlistasrToAttach);
            }
            listaR.setCancionxlistasrList(attachedCancionxlistasrList);
            em.persist(listaR);
            if (usuario != null) {
                usuario.getListaRList().add(listaR);
                usuario = em.merge(usuario);
            }
            for (Cancionxlistasr cancionxlistasrListCancionxlistasr : listaR.getCancionxlistasrList()) {
                ListaR oldListaROfCancionxlistasrListCancionxlistasr = cancionxlistasrListCancionxlistasr.getListaR();
                cancionxlistasrListCancionxlistasr.setListaR(listaR);
                cancionxlistasrListCancionxlistasr = em.merge(cancionxlistasrListCancionxlistasr);
                if (oldListaROfCancionxlistasrListCancionxlistasr != null) {
                    oldListaROfCancionxlistasrListCancionxlistasr.getCancionxlistasrList().remove(cancionxlistasrListCancionxlistasr);
                    oldListaROfCancionxlistasrListCancionxlistasr = em.merge(oldListaROfCancionxlistasrListCancionxlistasr);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findListaR(listaR.getListaRPK()) != null) {
                throw new PreexistingEntityException("ListaR " + listaR + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ListaR listaR) throws IllegalOrphanException, NonexistentEntityException, Exception {
        listaR.getListaRPK().setUsuarioNickname(listaR.getUsuario().getNickname());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ListaR persistentListaR = em.find(ListaR.class, listaR.getListaRPK());
            Usuario usuarioOld = persistentListaR.getUsuario();
            Usuario usuarioNew = listaR.getUsuario();
            List<Cancionxlistasr> cancionxlistasrListOld = persistentListaR.getCancionxlistasrList();
            List<Cancionxlistasr> cancionxlistasrListNew = listaR.getCancionxlistasrList();
            List<String> illegalOrphanMessages = null;
            for (Cancionxlistasr cancionxlistasrListOldCancionxlistasr : cancionxlistasrListOld) {
                if (!cancionxlistasrListNew.contains(cancionxlistasrListOldCancionxlistasr)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cancionxlistasr " + cancionxlistasrListOldCancionxlistasr + " since its listaR field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getNickname());
                listaR.setUsuario(usuarioNew);
            }
            List<Cancionxlistasr> attachedCancionxlistasrListNew = new ArrayList<Cancionxlistasr>();
            for (Cancionxlistasr cancionxlistasrListNewCancionxlistasrToAttach : cancionxlistasrListNew) {
                cancionxlistasrListNewCancionxlistasrToAttach = em.getReference(cancionxlistasrListNewCancionxlistasrToAttach.getClass(), cancionxlistasrListNewCancionxlistasrToAttach.getCancionxlistasrPK());
                attachedCancionxlistasrListNew.add(cancionxlistasrListNewCancionxlistasrToAttach);
            }
            cancionxlistasrListNew = attachedCancionxlistasrListNew;
            listaR.setCancionxlistasrList(cancionxlistasrListNew);
            listaR = em.merge(listaR);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getListaRList().remove(listaR);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getListaRList().add(listaR);
                usuarioNew = em.merge(usuarioNew);
            }
            for (Cancionxlistasr cancionxlistasrListNewCancionxlistasr : cancionxlistasrListNew) {
                if (!cancionxlistasrListOld.contains(cancionxlistasrListNewCancionxlistasr)) {
                    ListaR oldListaROfCancionxlistasrListNewCancionxlistasr = cancionxlistasrListNewCancionxlistasr.getListaR();
                    cancionxlistasrListNewCancionxlistasr.setListaR(listaR);
                    cancionxlistasrListNewCancionxlistasr = em.merge(cancionxlistasrListNewCancionxlistasr);
                    if (oldListaROfCancionxlistasrListNewCancionxlistasr != null && !oldListaROfCancionxlistasrListNewCancionxlistasr.equals(listaR)) {
                        oldListaROfCancionxlistasrListNewCancionxlistasr.getCancionxlistasrList().remove(cancionxlistasrListNewCancionxlistasr);
                        oldListaROfCancionxlistasrListNewCancionxlistasr = em.merge(oldListaROfCancionxlistasrListNewCancionxlistasr);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ListaRPK id = listaR.getListaRPK();
                if (findListaR(id) == null) {
                    throw new NonexistentEntityException("The listaR with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ListaRPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ListaR listaR;
            try {
                listaR = em.getReference(ListaR.class, id);
                listaR.getListaRPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The listaR with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cancionxlistasr> cancionxlistasrListOrphanCheck = listaR.getCancionxlistasrList();
            for (Cancionxlistasr cancionxlistasrListOrphanCheckCancionxlistasr : cancionxlistasrListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ListaR (" + listaR + ") cannot be destroyed since the Cancionxlistasr " + cancionxlistasrListOrphanCheckCancionxlistasr + " in its cancionxlistasrList field has a non-nullable listaR field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuario = listaR.getUsuario();
            if (usuario != null) {
                usuario.getListaRList().remove(listaR);
                usuario = em.merge(usuario);
            }
            em.remove(listaR);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
*/
    public List<ListaR> findListaREntities() {
        return findListaREntities(true, -1, -1);
    }

    public List<ListaR> findListaREntities(int maxResults, int firstResult) {
        return findListaREntities(false, maxResults, firstResult);
    }

    private List<ListaR> findListaREntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListaR.class));
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

    public ListaR findListaR(ListaRPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListaR.class, id);
        } finally {
            em.close();
        }
    }

    public int getListaRCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListaR> rt = cq.from(ListaR.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
