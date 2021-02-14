package cat.alkaid.projects.intrastat.services;

import java.util.List;
import java.lang.Long;

import javax.persistence.EntityManager;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;

import cat.alkaid.projects.intrastat.models.AuxDto;
import cat.alkaid.projects.intrastat.models.Nomenclature;

@Service
public class NomenclatureService {

	@PersistenceContext
	private EntityManager em;

	public Nomenclature findById(String codigo) {
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
			Query query = em.createQuery("SELECT COUNT(p) FROM Nomenclature p WHERE p.level > 2 and code like ?0");
			query.setParameter(0, '%' + codigo + '%');

			return (Long) query.getSingleResult();

		} catch (NoResultException nre) {
			return new Long(-1);
		}
	}

	public List<Nomenclature> searchByText(String section, String text) {
		StoredProcedureQuery storedProcedure = em.createStoredProcedureQuery("searchNomenclature", Nomenclature.class)
				.registerStoredProcedureParameter(1, String.class, ParameterMode.IN).setParameter(1, section)
				.registerStoredProcedureParameter(2, String.class, ParameterMode.IN).setParameter(2, text);

		return storedProcedure.getResultList();
	}

	/*
	 * public List<AuxDto> searchByText(String texto) { TypedQuery<AuxDto> query =
	 * em.createQuery(
	 * "SELECT NEW cat.alkaid.projects.intrastat.models.AuxDto(p.code, p.code, p.description) FROM Nomenclature p WHERE description like ?0"
	 * , AuxDto.class); query.setParameter(0, '%' + texto + '%');
	 * 
	 * return query.setFirstResult(0) // offset .setMaxResults(20) // limit
	 * .getResultList(); }
	 */

	public List<AuxDto> searchByCodigo(String codigo) {
		TypedQuery<AuxDto> query = em.createQuery(
				"SELECT NEW cat.alkaid.projects.intrastat.models.AuxDto(p.code, p.code, p.description) FROM Nomenclature p WHERE code like ?0",
				AuxDto.class);
		query.setParameter(0, '%' + codigo + '%');

		return query.setFirstResult(0) // offset
				.setMaxResults(20) // limit
				.getResultList();
	}

	public AuxDto searchById(String codigo) {
		TypedQuery<AuxDto> query = em.createQuery(
				"SELECT NEW cat.alkaid.projects.intrastat.models.AuxDto(p.code, p.code, p.description) FROM Nomenclature p WHERE code = ?0",
				AuxDto.class);
		query.setParameter(0, codigo);

		return query.getSingleResult();
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
			TypedQuery<Nomenclature> query = em
					.createQuery("SELECT p FROM Nomenclature p WHERE p.level > 2 and code like ?0", Nomenclature.class);
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
					"SELECT p FROM Nomenclature p WHERE p.level = ?0 and (section like ?1 or code like ?2)",
					Nomenclature.class);
			query.setParameter(0, nomen.getLevel() + 1);
			query.setParameter(1, nomen.getSection() + '%');
			query.setParameter(2, section + '%');
			query.setMaxResults(30);

			return query.getResultList();

		} catch (NoResultException nre) {
			return null;
		}
	}
}
