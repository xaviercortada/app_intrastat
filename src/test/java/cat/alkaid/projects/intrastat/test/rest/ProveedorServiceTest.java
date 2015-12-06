package cat.alkaid.projects.intrastat.test.rest;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import cat.alkaid.projects.intrastat.model.Proveedor;
import cat.alkaid.projects.intrastat.service.ProveedorService;

@RunWith(Arquillian.class)
public class ProveedorServiceTest {
	
	@Deployment
	public static WebArchive deployment(){
		return RESTDeployment.deployment();
	}
	
	@Inject
	ProveedorService service;
	
	@Test
	@InSequence(1)
	public void testProveedorFindAll(){
		List<Proveedor> list = service.findAll();
		assertNotNull(list);
	}
	
	@Test
	@InSequence(10)
	public void addItem(){
		Proveedor Proveedor = new Proveedor();
		Proveedor.setCodigo("test");
		Proveedor.setDocumento("90000000A");
		Proveedor.setName("test");
		
		service.addProveedor(Proveedor);
	}
	
	@Test
	@InSequence(15)
	public void findItem(){
		Proveedor temp = service.findByCodigo("test");
		assertNotNull(temp);
	}
	

	@Test
	@InSequence(20)
	public void deleteItem(){
		Proveedor cat = service.findByCodigo("test");	
		service.delete(cat.getId());

		Proveedor temp = service.findByCodigo("test");
		assertNull(temp);
	}
}


