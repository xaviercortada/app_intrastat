package cat.alkaid.projects.intrastat.services;

import java.util.List;

import cat.alkaid.projects.intrastat.models.Proveedor;

public interface ProveedorService {
	
    Proveedor findById(Long id);

    List<Proveedor> findAll();

    Proveedor findByCodigo(String codigo);

    boolean create(Proveedor item);

    boolean update(Proveedor item);

    boolean delete(Long id);

    void addProveedor(Proveedor item);


}
