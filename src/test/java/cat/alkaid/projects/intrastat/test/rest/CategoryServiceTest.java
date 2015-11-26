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

import cat.alkaid.projects.intrastat.model.Category;
import cat.alkaid.projects.intrastat.service.CategoryService;

@RunWith(Arquillian.class)
public class CategoryServiceTest {
	
	@Deployment
	public static WebArchive deployment(){
		return RESTDeployment.deployment();
	}
	
	@Inject
	CategoryService service;
	
	@Test
	@InSequence(1)
	public void testCategoryFindAll(){
		List<Category> list = service.findAll();
		assertNotNull(list);
	}
	
	@Test
	@InSequence(10)
	public void addItem(){
		Category category = new Category();
		category.setCodigo("test");
		category.setDescripcion("test");
		category.setName("test");
		
		service.addCategory(category);
	}
	
	@Test
	@InSequence(15)
	public void findItem(){
		Category temp = service.findByCodigo("test");
		assertNotNull(temp);
	}
	

	@Test
	@InSequence(20)
	public void deleteItem(){
		Category cat = service.findByCodigo("test");	
		service.delete(cat.getId());

		Category temp = service.findByCodigo("test");
		assertNull(temp);
	}
}


