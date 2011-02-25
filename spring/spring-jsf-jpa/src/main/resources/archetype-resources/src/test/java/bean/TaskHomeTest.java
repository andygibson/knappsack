#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bean;

import ${package}.model.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration("/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TaskHomeTest {
	
	@Autowired
	private TaskHome taskHome;
		
	@Test
	public void shouldGetCorrectMessage() {			
		String message = taskHome.getMessage();
		Assert.assertEquals("Hello from Spring",message);
	}
	
	@Test
	public void shouldSaveTaskAndResetTaskInstanceOnBean() {		
		Task oldTask = taskHome.getTask();
		taskHome.getTask().setDescription("Sample Description");
		taskHome.saveTask();
		Assert.assertNotNull("Saved task ID is null,probably not saved",oldTask.getId());
		Assert.assertNull("Task has not been reset",taskHome.getTask().getDescription());
		Assert.assertNull("Task has not been reset",taskHome.getTask().getId());
		Assert.assertNotSame("Task object has not been replaced",oldTask, taskHome.getTask());
	}
}
