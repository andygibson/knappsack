#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import junit.framework.Assert;

import ${package}.HomeController;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

public class HomeControllerTest {

	@Test
	public void testController() {
		HomeController controller = new HomeController();
		Model model = new ExtendedModelMap();
		Assert.assertEquals("home",controller.home(model));
		
		Object message = model.asMap().get("controllerMessage");
		Assert.assertEquals("This is the message from the controller!",message);
		
	}
}
