package pe.com.abaris.vehiculos.accesodatos;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import pe.com.abaris.vehiculos.accesodatos.exceptions.NonexistentEntityException;
import pe.com.abaris.vehiculos.accesodatos.exceptions.PreexistingEntityException;
import pe.com.abaris.vehiculos.modelo.Vehiculo;

/**
 * Contiene la interaccion con la fuente de datos de vehiculos
 */
public class VehiculoDaoImpl implements Serializable {

    private static final String PERSISTENCE_UNIT = "VehiculosWebPU";
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public VehiculoDaoImpl(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }

    public VehiculoDaoImpl(String persistenceUnitName) {
        this(null,
                Persistence
                .createEntityManagerFactory(
                persistenceUnitName == null
                ? PERSISTENCE_UNIT : persistenceUnitName));
    }

    public VehiculoDaoImpl() {
        this(null);
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void agregar(Vehiculo vehiculo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vehiculo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (buscar(vehiculo.getId()) != null) {
                throw new PreexistingEntityException("Vehiculo " + vehiculo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void editar(Vehiculo vehiculo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vehiculo = em.merge(vehiculo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = vehiculo.getId();
                if (buscar(id) == null) {
                    throw new NonexistentEntityException("The vehiculo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void eliminar(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vehiculo vehiculo;
            try {
                vehiculo = em.getReference(Vehiculo.class, id);
                vehiculo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vehiculo with id " + id + " no longer exists.", enfe);
            }
            em.remove(vehiculo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vehiculo> buscar() {
        return buscar(true, -1, -1);
    }

    public List<Vehiculo> buscar(int maxResults, int firstResult) {
        return buscar(false, maxResults, firstResult);
    }

    public Vehiculo buscar(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vehiculo.class, id);
        } finally {
            em.close();
        }
    }

    public int contar() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Vehiculo as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    private List<Vehiculo> buscar(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Vehiculo as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}
