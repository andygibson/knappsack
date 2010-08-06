#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.view;

import java.io.IOException;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import ${package}.model.BaseEntity;

/**
 * Set of JSF related utility methods to wrap a list entities in a
 * {@link SelectItem}, add error messages to the {@link FacesContext} and to
 * handle redirects.
 * 
 * @author Andy Gibson
 * 
 */
public class ViewUtil {

	/**
	 * Comparator for comparing {@link SelectItem} instances.
	 */
	private static Comparator<SelectItem> selectItemComparator = new Comparator<SelectItem>() {

		@Override
		public int compare(SelectItem item1, SelectItem item2) {
			return item1.getLabel().compareToIgnoreCase(item2.getLabel());
		}

	};

	/**
	 * Takes a list of {@link BaseEntity} items and returns a list of
	 * {@link SelectItem} instances wrapping the entities. This version does not
	 * include an item for when no item is selected
	 * 
	 * @param entities
	 *            List of {@link BaseEntity} objects
	 * @return {@link List} of entities wrapped in {@link SelectItem} objects.
	 */
	public static List<SelectItem> wrapInSelectItems(
			List<? extends BaseEntity> entities) {
		return wrapInSelectItems(entities, null);
	}

	/**
	 * Takes a list of {@link BaseEntity} items and returns a list of
	 * {@link SelectItem} instances wrapping the entities.
	 * 
	 * @param entities
	 *            List of {@link BaseEntity} objects
	 * @param noSelectText
	 *            text to use for the no selection option (null if you don't
	 *            want it added)
	 * @return {@link List} of entities wrapped in {@link SelectItem} objects.
	 */
	public static List<SelectItem> wrapInSelectItems(
			List<? extends BaseEntity> entities, String noSelectText) {

		List<SelectItem> results = new ArrayList<SelectItem>();

		if (noSelectText != null) {
			results.add(new SelectItem(null, noSelectText));
			results.get(0).setNoSelectionOption(true);

		}

		for (BaseEntity e : entities) {
			results.add(new SelectItem(e.getId(), e.getDisplayText()));
		}
		return results;
	}

	/**
	 * Redirects to a new page
	 * 
	 * @param url
	 *            URL to redirect to
	 */
	public static void redirect(String url) {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void redirectHome(){
		redirect("home.jsf");
	}

	/**
	 * Helper method to sort the list of select items.
	 * 
	 * @param items
	 *            List of {@link SelectItem} objects to be sorted using the
	 *            label
	 */
	public static void sortSelectItems(List<SelectItem> items) {
		Collections.sort(items, selectItemComparator);
	}

	/**
	 * Helper method to add a new JSF error message to the faces context.
	 * 
	 * @param text
	 *            The text for the error message
	 */
	public static void addErrorMessage(String text) {
		addErrorMessage(text, null);
	}

	/**
	 * Helper method to add a new JSF error message to the faces context.
	 * 
	 * @param text
	 *            the text for the error message
	 * @param clientId
	 *            the client Id of the JSF control this message is for
	 */
	public static void addErrorMessage(String text, String clientId) {
		FacesMessage msg = new FacesMessage(SEVERITY_ERROR, text, null);
		FacesContext.getCurrentInstance().addMessage(clientId, msg);
	}

}
