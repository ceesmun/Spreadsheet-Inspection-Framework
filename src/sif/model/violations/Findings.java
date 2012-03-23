package sif.model.violations;

import java.util.ArrayList;
import java.util.TreeMap;

import sif.model.violations.lists.ViolationList;

/***
 * Container class to store all violations that have been found during an
 * inspection.
 * 
 * @author Sebastian Zitzelsberger
 * 
 */
public class Findings {

	private TreeMap<String, ViolationList> violationLists;

	/***
	 * Creates a new empty findings object.
	 */
	public Findings() {
		violationLists = new TreeMap<String, ViolationList>();
	}

	/***
	 * Adds the given violation list.
	 * 
	 * @param violationList
	 *            The given violation list.
	 */
	public void add(ViolationList violationList) {
		violationLists.put(violationList.getPolicyRule().getClass()
				.getCanonicalName(), violationList);
	}

	/***
	 * Gets the number of all top level violations, that is the number of all
	 * violation groups plus the number of all single violations that are not
	 * part of a group.
	 * 
	 * @return The number of top level violations.
	 */
	public Integer getNumberOfTopLevelViolations() {
		Integer number = 0;
		for (ViolationList violationList : violationLists.values()) {
			number = number + violationList.getNumberOfViolations();
		}
		return number;
	}

	/***
	 * Gets the number of all violation groups.
	 * 
	 * @return The number of all violation groups.
	 */
	public Integer getNumberOfViolationGroups() {
		Integer number = 0;
		for (ViolationList violationList : violationLists.values()) {
			number = number + violationList.getNumberOfViolationGroups();
		}
		return number;
	}

	/***
	 * Gets the number of all violations.
	 * 
	 * @return The number of all violations.
	 */
	public Integer getNumberOfViolations() {
		Integer number = 0;
		for (ViolationList violationList : violationLists.values()) {
			number = number + violationList.getNumberOfSingleViolations();
		}
		return number;
	}

	/***
	 * Gets the violation lists.
	 * 
	 * @return The violation lists.
	 */
	public ArrayList<ViolationList> getViolationLists() {
		return new ArrayList<ViolationList>(violationLists.values());
	}

}
