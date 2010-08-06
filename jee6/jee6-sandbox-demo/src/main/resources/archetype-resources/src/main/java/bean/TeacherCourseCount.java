#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bean;

public class TeacherCourseCount {

	private Long id;
	private String name;
	private Integer count;

	public TeacherCourseCount(Long id,String name, Integer count) {
		super();
		this.id = id;
		this.name = name;
		this.count = count;
	}

	public Long getId() {
		return id;
	}
	
	public Integer getCount() {
		return count;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setCount(Integer count) {
		this.count = count;
	}

	public void setName(String name) {
		this.name = name;
	}

}
