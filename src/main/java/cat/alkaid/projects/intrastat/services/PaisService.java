package cat.alkaid.projects.intrastat.services;

import java.util.List;

import org.springframework.stereotype.Service;

import cat.alkaid.projects.intrastat.models.Pais;
import cat.alkaid.projects.intrastat.models.Proveedor;

@Service
public interface PaisService {
	

    List<Pais> findAll();



}
