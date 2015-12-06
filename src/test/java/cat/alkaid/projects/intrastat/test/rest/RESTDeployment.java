package cat.alkaid.projects.intrastat.test.rest;

import org.jboss.shrinkwrap.api.spec.WebArchive;

import cat.alkaid.projects.intrastat.model.Proveedor;
import cat.alkaid.projects.intrastat.service.ProveedorService;
import cat.alkaid.projects.intrastat.test.IntrastatDeployment;

public class RESTDeployment {
	public static WebArchive deployment(){
		return IntrastatDeployment.deployment()
				.addPackage(Proveedor.class.getPackage())
				.addPackage(ProveedorService.class.getPackage());
	}

}
