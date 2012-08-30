package nc.noumea.mairie.model.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nc.noumea.mairie.model.bean.Siserv;

import org.springframework.stereotype.Service;

@Service
public class SiservService implements ISiservService {

	@PersistenceContext
	transient EntityManager entityManager;

	@Override
	public Siserv getDirection(String servi) {
		Siserv res = null;
		if (servi.length() == 4 && estAlphabetique(servi)
				&& !servi.substring(1, 2).equals("A")) {
			String codeDirection = servi.substring(0, 2) + "AA";
			Query query = entityManager.createQuery(
					"select serv from Siserv serv "
							+ "where  servi=:codeDirection)", Siserv.class);
			query.setParameter("codeDirection", codeDirection);
			List<Siserv> lserv = query.getResultList();

			for (Siserv serv : lserv)
				res = serv;
		}

		return res;
	}

	public static boolean estAlphabetique(String param) {
		if (param == null || param.length() == 0)
			return false;

		for (int i = 0; i < param.length(); i++) {

			char aChar = param.charAt(i);
			// Si le caract�re en cours n'est pas une lettre
			if (!Character.isLetter(aChar))
				return false;
		}
		return true;
	}

	@Override
	public Siserv getSection(String servi) {
		Siserv res = null;
		if (servi.length() == 4 && estAlphabetique(servi)
				&& !servi.substring(3, 4).equals("A")) {
			Query query = entityManager.createQuery(
					"select serv from Siserv serv "
							+ "where  servi=:codeSection)", Siserv.class);
			query.setParameter("codeSection", servi);
			List<Siserv> lserv = query.getResultList();

			for (Siserv serv : lserv)
				res = serv;
		}

		return res;
	}

	@Override
	public Siserv getDivision(String servi) {
		Siserv res = null;
		if (servi.length() == 4 && estAlphabetique(servi)
				&& !servi.substring(2, 3).equals("A")) {
			String codeDivision = servi.substring(0, 3) + "A";
			Query query = entityManager.createQuery(
					"select serv from Siserv serv "
							+ "where  servi=:codeDivision)", Siserv.class);
			query.setParameter("codeDivision", codeDivision);
			List<Siserv> lserv = query.getResultList();

			for (Siserv serv : lserv)
				res = serv;
		}
		return res;
	}

	@Override
	public Siserv getService(String servi) {
		Siserv res = null;
		Query query = entityManager.createQuery("select serv from Siserv serv "
				+ "where  servi=:service)", Siserv.class);
		query.setParameter("service", servi);
		List<Siserv> lserv = query.getResultList();

		for (Siserv serv : lserv)
			res = serv;
		return res;
	}
}
