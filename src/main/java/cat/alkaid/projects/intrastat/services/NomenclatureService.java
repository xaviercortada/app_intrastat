package cat.alkaid.projects.intrastat.services;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import cat.alkaid.projects.intrastat.models.Nomenclature;

public class NomenclatureService {

	@PersistenceContext
	private EntityManager em;

	public Nomenclature findByCodigo(String codigo) {
		TypedQuery<Nomenclature> query = em.createQuery("SELECT p FROM Nomenclature p WHERE p.code = ?0",
				Nomenclature.class);
		query.setParameter(0, codigo);
		try {
			return query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public List<Nomenclature> findByLevel(int level) {
		TypedQuery<Nomenclature> query = em.createQuery("SELECT p FROM Nomenclature p WHERE p.level = ?0",
				Nomenclature.class);
		query.setParameter(0, level);
		try {
			return query.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public List<Nomenclature> findCapitulosBySeccion(String codigo) {
		try {
			List<Nomenclature> secciones = findByLevel(1);
			int codigoIni = Integer.parseInt(codigo.substring(0, 2));
			int codigoFin = 999;
			String bottom = "";
			for (Nomenclature nomenclature : secciones) {
				int n = Integer.parseInt(nomenclature.getCode().substring(0, 2));
				if (n > codigoIni && n < codigoFin) {
					codigoFin = n;
					bottom = nomenclature.getCode();
				}
			}
			TypedQuery<Nomenclature> query = em.createQuery(
					"SELECT p FROM Nomenclature p WHERE p.level = 2 and p.code between ?0 and ?1", Nomenclature.class);
			query.setParameter(0, codigo);
			query.setParameter(1, bottom);

			return query.getResultList();

		} catch (NoResultException nre) {
			return null;
		}
	}

	public List<Nomenclature> findItemsByText(String codigo, String texto) {
		try {
			TypedQuery<Nomenclature> query = em.createQuery(
					"SELECT p FROM Nomenclature p WHERE p.level > 2 and p.code like ?0 and description like ?1",
					Nomenclature.class);
			query.setParameter(0, codigo + '%');
			query.setParameter(1, '%' + texto + '%');

			return query.getResultList();

		} catch (NoResultException nre) {
			return null;
		}
	}

	public Long countItems(String texto) {
		try {
			Query query = em
					.createQuery("SELECT COUNT(p) FROM Nomenclature p WHERE p.level > 2 and description like ?0");
			query.setParameter(0, '%' + texto + '%');

			return (Long) query.getSingleResult();

		} catch (NoResultException nre) {
			return new Long(-1);
		}
	}

	public Long countItemsByCodigo(String codigo) {
		try {
			Query query = em
					.createQuery("SELECT COUNT(p) FROM Nomenclature p WHERE p.level > 2 and code like ?0");
			query.setParameter(0, '%' + codigo + '%');

			return (Long) query.getSingleResult();

		} catch (NoResultException nre) {
			return new Long(-1);
		}
	}

	public List<Nomenclature> findItems(String texto, int firstR, int maxR) {
		try {
			TypedQuery<Nomenclature> query = em.createQuery(
					"SELECT p FROM Nomenclature p WHERE p.level > 2 and description like ?0", Nomenclature.class);
			query.setParameter(0, '%' + texto + '%');
			query.setFirstResult(firstR);
			query.setMaxResults(maxR);

			return query.getResultList();

		} catch (NoResultException nre) {
			return null;
		}
	}

	public List<Nomenclature> findItemsByCodigo(String codigo, int firstR, int maxR) {
		try {
			TypedQuery<Nomenclature> query = em.createQuery(
					"SELECT p FROM Nomenclature p WHERE p.level > 2 and code like ?0", Nomenclature.class);
			query.setParameter(0, '%' + codigo + '%');
			query.setFirstResult(firstR);
			query.setMaxResults(maxR);

			return query.getResultList();

		} catch (NoResultException nre) {
			return null;
		}
	}

	public List<Nomenclature> findDetailItemsByCodigo(String codigo) {
		try {
			Nomenclature nomen = em.find(Nomenclature.class, codigo);
			
			String section = nomen.getSection().replace(" ", "");

			TypedQuery<Nomenclature> query = em.createQuery(
					"SELECT p FROM Nomenclature p WHERE p.level = ?0 and (section like ?1 or code like ?2)", Nomenclature.class);
			query.setParameter(0, nomen.getLevel()+1);
			query.setParameter(1, nomen.getSection() + '%');
			query.setParameter(2, section + '%');
			query.setMaxResults(30);

			return query.getResultList();

		} catch (NoResultException nre) {
			return null;
		}
	}
}
