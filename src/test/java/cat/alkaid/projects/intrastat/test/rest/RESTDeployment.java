package cat.alkaid.projects.intrastat.test.rest;

import org.jboss.shrinkwrap.api.spec.WebArchive;

import cat.alkaid.projects.intrastat.model.Category;
import cat.alkaid.projects.intrastat.service.CategoryService;
import cat.alkaid.projects.intrastat.test.IntrastatDeployment;

public class RESTDeployment {
	public static WebArchive deployment(){
		return IntrastatDeployment.deployment()
				.addPackage(Category.class.getPackage())
				.addPackage(CategoryService.class.getPackage());
	}

}
