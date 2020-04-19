/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Controladores.exceptions.IllegalOrphanException;
import Controladores.exceptions.NonexistentEntityException;
import Controladores.exceptions.PreexistingEntityException;
import Entidades.Familiar;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Entidades.Suscripcion;
import Entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author julia
 */
public class FamiliarJpaController implements Serializable {

    public FamiliarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
/*
    public void create(Familiar familiar) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Suscripcion suscripcionOrphanCheck = familiar.getSuscripcion();
        if (suscripcionOrphanCheck != null) {
            Familiar oldFamiliarOfSuscripcion = suscripcionOrphanCheck.getFamiliar();
            if (oldFamiliarOfSuscripcion != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Suscripcion " + suscripcionOrphanCheck + " already has an item of type Familiar whose suscripcion column cannot be null. Please make another selection for the suscripcion field.");
            }
        }
        Usuario usuarioNickname3OrphanCheck = familiar.getUsuarioNickname3();
        if (usuarioNickname3OrphanCheck != null) {
            Familiar oldFamiliarOfUsuarioNickname3 = usuarioNickname3OrphanCheck.getFamiliar();
            if (oldFamiliarOfUsuarioNickname3 != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + usuarioNickname3OrphanCheck + " already has an item of type Familiar whose usuarioNickname3 column cannot be null. Please make another selection for the usuarioNickname3 field.");
            }
        }
        Usuario usuarioNickname2OrphanCheck = familiar.getUsuarioNickname2();
        if (usuarioNickname2OrphanCheck != null) {
            Familiar oldFamiliarOfUsuarioNickname2 = usuarioNickname2OrphanCheck.getFamiliar();
            if (oldFamiliarOfUsuarioNickname2 != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + usuarioNickname2OrphanCheck + " already has an item of type Familiar whose usuarioNickname2 column cannot be null. Please make another selection for the usuarioNickname2 field.");
            }
        }
        Usuario usuarioNicknameOrphanCheck = familiar.getUsuarioNickname();
        if (usuarioNicknameOrphanCheck != null) {
            Familiar oldFamiliarOfUsuarioNickname = usuarioNicknameOrphanCheck.getFamiliar();
            if (oldFamiliarOfUsuarioNickname != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + usuarioNicknameOrphanCheck + " already has an item of type Familiar whose usuarioNickname column cannot be null. Please make another selection for the usuarioNickname field.");
            }
        }
        Usuario usuarioNickname1OrphanCheck = familiar.getUsuarioNickname1();
        if (usuarioNickname1OrphanCheck != null) {
            Familiar oldFamiliarOfUsuarioNickname1 = usuarioNickname1OrphanCheck.getFamiliar();
            if (oldFamiliarOfUsuarioNickname1 != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Usuario " + usuarioNickname1OrphanCheck + " already has an item of type Familiar whose usuarioNickname1 column cannot be null. Please make another selection for the usuarioNickname1 field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Suscripcion suscripcion = familiar.getSuscripcion();
            if (suscripcion != null) {
                suscripcion = em.getReference(suscripcion.getClass(), suscripcion.getSusIdSuscripcion());
                familiar.setSuscripcion(suscripcion);
            }
            Usuario usuarioNickname3 = familiar.getUsuarioNickname3();
            if (usuarioNickname3 != null) {
                usuarioNickname3 = em.getReference(usuarioNickname3.getClass(), usuarioNickname3.getNickname());
                familiar.setUsuarioNickname3(usuarioNickname3);
            }
            Usuario usuarioNickname2 = familiar.getUsuarioNickname2();
            if (usuarioNickname2 != null) {
                usuarioNickname2 = em.getReference(usuarioNickname2.getClass(), usuarioNickname2.getNickname());
                familiar.setUsuarioNickname2(usuarioNickname2);
            }
            Usuario usuarioNickname = familiar.getUsuarioNickname();
            if (usuarioNickname != null) {
                usuarioNickname = em.getReference(usuarioNickname.getClass(), usuarioNickname.getNickname());
                familiar.setUsuarioNickname(usuarioNickname);
            }
            Usuario usuarioNickname1 = familiar.getUsuarioNickname1();
            if (usuarioNickname1 != null) {
                usuarioNickname1 = em.getReference(usuarioNickname1.getClass(), usuarioNickname1.getNickname());
                familiar.setUsuarioNickname1(usuarioNickname1);
            }
            em.persist(familiar);
            if (suscripcion != null) {
                suscripcion.setFamiliar(familiar);
                suscripcion = em.merge(suscripcion);
            }
            if (usuarioNickname3 != null) {
                usuarioNickname3.setFamiliar(familiar);
                usuarioNickname3 = em.merge(usuarioNickname3);
            }
            if (usuarioNickname2 != null) {
                usuarioNickname2.setFamiliar(familiar);
                usuarioNickname2 = em.merge(usuarioNickname2);
            }
            if (usuarioNickname != null) {
                usuarioNickname.setFamiliar(familiar);
                usuarioNickname = em.merge(usuarioNickname);
            }
            if (usuarioNickname1 != null) {
                usuarioNickname1.setFamiliar(familiar);
                usuarioNickname1 = em.merge(usuarioNickname1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFamiliar(familiar.getSusIdSuscripcion()) != null) {
                throw new PreexistingEntityException("Familiar " + familiar + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Familiar familiar) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Familiar persistentFamiliar = em.find(Familiar.class, familiar.getSusIdSuscripcion());
            Suscripcion suscripcionOld = persistentFamiliar.getSuscripcion();
            Suscripcion suscripcionNew = familiar.getSuscripcion();
            Usuario usuarioNickname3Old = persistentFamiliar.getUsuarioNickname3();
            Usuario usuarioNickname3New = familiar.getUsuarioNickname3();
            Usuario usuarioNickname2Old = persistentFamiliar.getUsuarioNickname2();
            Usuario usuarioNickname2New = familiar.getUsuarioNickname2();
            Usuario usuarioNicknameOld = persistentFamiliar.getUsuarioNickname();
            Usuario usuarioNicknameNew = familiar.getUsuarioNickname();
            Usuario usuarioNickname1Old = persistentFamiliar.getUsuarioNickname1();
            Usuario usuarioNickname1New = familiar.getUsuarioNickname1();
            List<String> illegalOrphanMessages = null;
            if (suscripcionNew != null && !suscripcionNew.equals(suscripcionOld)) {
                Familiar oldFamiliarOfSuscripcion = suscripcionNew.getFamiliar();
                if (oldFamiliarOfSuscripcion != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Suscripcion " + suscripcionNew + " already has an item of type Familiar whose suscripcion column cannot be null. Please make another selection for the suscripcion field.");
                }
            }
            if (usuarioNickname3New != null && !usuarioNickname3New.equals(usuarioNickname3Old)) {
                Familiar oldFamiliarOfUsuarioNickname3 = usuarioNickname3New.getFamiliar();
                if (oldFamiliarOfUsuarioNickname3 != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + usuarioNickname3New + " already has an item of type Familiar whose usuarioNickname3 column cannot be null. Please make another selection for the usuarioNickname3 field.");
                }
            }
            if (usuarioNickname2New != null && !usuarioNickname2New.equals(usuarioNickname2Old)) {
                Familiar oldFamiliarOfUsuarioNickname2 = usuarioNickname2New.getFamiliar();
                if (oldFamiliarOfUsuarioNickname2 != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + usuarioNickname2New + " already has an item of type Familiar whose usuarioNickname2 column cannot be null. Please make another selection for the usuarioNickname2 field.");
                }
            }
            if (usuarioNicknameNew != null && !usuarioNicknameNew.equals(usuarioNicknameOld)) {
                Familiar oldFamiliarOfUsuarioNickname = usuarioNicknameNew.getFamiliar();
                if (oldFamiliarOfUsuarioNickname != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + usuarioNicknameNew + " already has an item of type Familiar whose usuarioNickname column cannot be null. Please make another selection for the usuarioNickname field.");
                }
            }
            if (usuarioNickname1New != null && !usuarioNickname1New.equals(usuarioNickname1Old)) {
                Familiar oldFamiliarOfUsuarioNickname1 = usuarioNickname1New.getFamiliar();
                if (oldFamiliarOfUsuarioNickname1 != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Usuario " + usuarioNickname1New + " already has an item of type Familiar whose usuarioNickname1 column cannot be null. Please make another selection for the usuarioNickname1 field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (suscripcionNew != null) {
                suscripcionNew = em.getReference(suscripcionNew.getClass(), suscripcionNew.getSusIdSuscripcion());
                familiar.setSuscripcion(suscripcionNew);
            }
            if (usuarioNickname3New != null) {
                usuarioNickname3New = em.getReference(usuarioNickname3New.getClass(), usuarioNickname3New.getNickname());
                familiar.setUsuarioNickname3(usuarioNickname3New);
            }
            if (usuarioNickname2New != null) {
                usuarioNickname2New = em.getReference(usuarioNickname2New.getClass(), usuarioNickname2New.getNickname());
                familiar.setUsuarioNickname2(usuarioNickname2New);
            }
            if (usuarioNicknameNew != null) {
                usuarioNicknameNew = em.getReference(usuarioNicknameNew.getClass(), usuarioNicknameNew.getNickname());
                familiar.setUsuarioNickname(usuarioNicknameNew);
            }
            if (usuarioNickname1New != null) {
                usuarioNickname1New = em.getReference(usuarioNickname1New.getClass(), usuarioNickname1New.getNickname());
                familiar.setUsuarioNickname1(usuarioNickname1New);
            }
            familiar = em.merge(familiar);
            if (suscripcionOld != null && !suscripcionOld.equals(suscripcionNew)) {
                suscripcionOld.setFamiliar(null);
                suscripcionOld = em.merge(suscripcionOld);
            }
            if (suscripcionNew != null && !suscripcionNew.equals(suscripcionOld)) {
                suscripcionNew.setFamiliar(familiar);
                suscripcionNew = em.merge(suscripcionNew);
            }
            if (usuarioNickname3Old != null && !usuarioNickname3Old.equals(usuarioNickname3New)) {
                usuarioNickname3Old.setFamiliar(null);
                usuarioNickname3Old = em.merge(usuarioNickname3Old);
            }
            if (usuarioNickname3New != null && !usuarioNickname3New.equals(usuarioNickname3Old)) {
                usuarioNickname3New.setFamiliar(familiar);
                usuarioNickname3New = em.merge(usuarioNickname3New);
            }
            if (usuarioNickname2Old != null && !usuarioNickname2Old.equals(usuarioNickname2New)) {
                usuarioNickname2Old.setFamiliar(null);
                usuarioNickname2Old = em.merge(usuarioNickname2Old);
            }
            if (usuarioNickname2New != null && !usuarioNickname2New.equals(usuarioNickname2Old)) {
                usuarioNickname2New.setFamiliar(familiar);
                usuarioNickname2New = em.merge(usuarioNickname2New);
            }
            if (usuarioNicknameOld != null && !usuarioNicknameOld.equals(usuarioNicknameNew)) {
                usuarioNicknameOld.setFamiliar(null);
                usuarioNicknameOld = em.merge(usuarioNicknameOld);
            }
            if (usuarioNicknameNew != null && !usuarioNicknameNew.equals(usuarioNicknameOld)) {
                usuarioNicknameNew.setFamiliar(familiar);
                usuarioNicknameNew = em.merge(usuarioNicknameNew);
            }
            if (usuarioNickname1Old != null && !usuarioNickname1Old.equals(usuarioNickname1New)) {
                usuarioNickname1Old.setFamiliar(null);
                usuarioNickname1Old = em.merge(usuarioNickname1Old);
            }
            if (usuarioNickname1New != null && !usuarioNickname1New.equals(usuarioNickname1Old)) {
                usuarioNickname1New.setFamiliar(familiar);
                usuarioNickname1New = em.merge(usuarioNickname1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = familiar.getSusIdSuscripcion();
                if (findFamiliar(id) == null) {
                    throw new NonexistentEntityException("The familiar with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Familiar familiar;
            try {
                familiar = em.getReference(Familiar.class, id);
                familiar.getSusIdSuscripcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The familiar with id " + id + " no longer exists.", enfe);
            }
            Suscripcion suscripcion = familiar.getSuscripcion();
            if (suscripcion != null) {
                suscripcion.setFamiliar(null);
                suscripcion = em.merge(suscripcion);
            }
            Usuario usuarioNickname3 = familiar.getUsuarioNickname3();
            if (usuarioNickname3 != null) {
                usuarioNickname3.setFamiliar(null);
                usuarioNickname3 = em.merge(usuarioNickname3);
            }
            Usuario usuarioNickname2 = familiar.getUsuarioNickname2();
            if (usuarioNickname2 != null) {
                usuarioNickname2.setFamiliar(null);
                usuarioNickname2 = em.merge(usuarioNickname2);
            }
            Usuario usuarioNickname = familiar.getUsuarioNickname();
            if (usuarioNickname != null) {
                usuarioNickname.setFamiliar(null);
                usuarioNickname = em.merge(usuarioNickname);
            }
            Usuario usuarioNickname1 = familiar.getUsuarioNickname1();
            if (usuarioNickname1 != null) {
                usuarioNickname1.setFamiliar(null);
                usuarioNickname1 = em.merge(usuarioNickname1);
            }
            em.remove(familiar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
*/
    public List<Familiar> findFamiliarEntities() {
        return findFamiliarEntities(true, -1, -1);
    }

    public List<Familiar> findFamiliarEntities(int maxResults, int firstResult) {
        return findFamiliarEntities(false, maxResults, firstResult);
    }

    private List<Familiar> findFamiliarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Familiar.class));
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

    public Familiar findFamiliar(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Familiar.class, id);
        } finally {
            em.close();
        }
    }

    public int getFamiliarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Familiar> rt = cq.from(Familiar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
