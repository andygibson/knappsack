#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.view;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import ${package}.bean.SchoolDao;
import ${package}.bean.SearchCriteria;
import ${package}.model.Student;

/**
 * This is the backing bean used for the student search page. It contains the
 * pagination information, the {@link SearchCriteria} instance and is
 * responsible for fetching the results.
 * 
 * @author Andy Gibson
 * 
 */
@Named("studentSearch")
@RequestScoped
public class StudentSearch {

	private List<Student> results;

	@Inject
	private SchoolDao schoolDao;

	private int firstResult = 0;
	private int pageSize = 10;
	private boolean nextPageAvailable;

	// holds the search criteria information the user entered
	private SearchCriteria criteria = new SearchCriteria();

	public SearchCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(SearchCriteria criteria) {
		this.criteria = criteria;
	}

	public void refresh() {
		results = null;
	}

	public List<Student> getResults() {
		if (results == null) {
			results = schoolDao.searchStudents(criteria, firstResult,
					pageSize + 1);

			// check to see if we have more results after this page
			if (results.size() > pageSize) {
				nextPageAvailable = true;
				results = results.subList(0, 10);
			}
		}
		return results;
	}

	/**
	 * This value change listener is needed to post back the onkey up changes on
	 * the first name search. It jsut invalidates the results so they are
	 * refetched on the page render.
	 * 
	 * @param e
	 *            {@link ValueChangeEvent} item
	 */
	public void firstNameChangeListener(ValueChangeEvent e) {
		results = null;
	}

	/**
	 * @return Whether there are more results available
	 */
	public boolean isNextPageAvailable() {
		getResults();
		return nextPageAvailable;
	}

	public int getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Moves to the first page of results
	 */
	public void first() {
		firstResult = 0;
		refresh();
	}

	/**
	 * Moves the firstResult value to the next page of results
	 */
	public void next() {
		if (nextPageAvailable) {
			firstResult += pageSize;
		}
		refresh();
	}

	/**
	 *  Moves to the previous page of results
	 */
	public void previous() {

		firstResult -= pageSize;

		if (firstResult < 0) {
			firstResult = 0;
		}
		refresh();
	}

}
