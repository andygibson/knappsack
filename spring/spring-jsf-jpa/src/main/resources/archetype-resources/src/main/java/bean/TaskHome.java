#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.bean;

import java.util.List;

import ${package}.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("taskHome")
public class TaskHome {

	private static final Logger logger = LoggerFactory.getLogger(TaskHome.class);

	private Task task = new Task();
	private List<Task> tasks;

	@Autowired
	private TaskDao taskDao;

	
	public String getMessage() {
		logger.debug("Returning message from task home bean");
		return "Hello from Spring";
	}	

	public Task getTask() {
		return task;
	}

	public void saveTask() {
		taskDao.save(task);
		task = new Task();
		invalidateTasks();
	}

	private void invalidateTasks() {
		tasks = null;
	}

	public List<Task> getTasks() {
		if (tasks == null) {
			tasks = taskDao.list();
		}
		return tasks;
		
	}
}
