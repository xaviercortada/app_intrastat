package cat.alkaid.projects.intrastat.test;

import javax.annotation.Resource;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

import cat.alkaid.projects.intrastat.util.Resources;

public class IntrastatDeployment {
	public static WebArchive deployment(){
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				//.addPackage(Resources.class.getPackage())
				.addAsResource("META-INF/test-persistence.xml","META-INF/persistence.xml")
				.addAsResource("test-import.sql","import.sql")
				.addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml")
				.addAsWebInfResource("test-ds.xml");			
		
	}

}
