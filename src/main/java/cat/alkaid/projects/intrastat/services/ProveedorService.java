package cat.alkaid.projects.intrastat.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import cat.alkaid.projects.intrastat.models.AuxDto;
import cat.alkaid.projects.intrastat.models.Proveedor;

@Service
@Transactional
public class ProveedorService {

    @PersistenceContext
    private EntityManager em;

    public Proveedor findById(Long id) {
        return em.find(Proveedor.class, id);
    }

    public List<Proveedor> findAll() {
        TypedQuery<Proveedor> query = em.createQuery("SELECT p FROM Proveedor p order by p.name", Proveedor.class);
        return query.getResultList();
    }

    public Proveedor findByCodigo(String codigo) {
        TypedQuery<Proveedor> query = em.createQuery("SELECT p FROM Proveedor p WHERE p.codigo = ?0", Proveedor.class);
        query.setParameter(0, codigo);
        try {
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public AuxDto searchById(Long id) {
        TypedQuery<AuxDto> query = em.createQuery("SELECT NEW cat.alkaid.projects.intrastat.models.AuxDto(p.id, p.codigo, p.name) FROM Proveedor p WHERE p.id like ?0", AuxDto.class);
        query.setParameter(0, id);
        return query.getSingleResult();
    }

    public List<AuxDto> searchByName(String name) {
        TypedQuery<AuxDto> query = em.createQuery("SELECT NEW cat.alkaid.projects.intrastat.models.AuxDto(p.id, p.codigo, p.name) FROM Proveedor p WHERE p.name like ?0", AuxDto.class);
        query.setParameter(0, "%" + name + "%");
        return query.setFirstResult(0) // offset
                .setMaxResults(20) // limit
                .getResultList();
    }

    public boolean create(Proveedor item) {
        em.persist(item);
        return true;
    }

    public boolean update(Proveedor item) {
        em.merge(item);
        return true;
    }

    public boolean delete(Long id) {
        Proveedor target = findById(id);
        if (target != null) {
            em.remove(target);
            return true;
        }

        return false;
    }

    public void addProveedor(Proveedor item) {
        em.persist(item);
    }

}
