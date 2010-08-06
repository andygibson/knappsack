#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.view;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named("helloBean")
@RequestScoped
public class HelloBean {

	
	private String name;

	public String getMessage() {
		if (name == null) {
			return "Enter your name above";
		}
		return "Hello there " + name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHelloMessage() {
		return "Hello World!";
	}
}
