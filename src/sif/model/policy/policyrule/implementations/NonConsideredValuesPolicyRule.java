package sif.model.policy.policyrule.implementations;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

import sif.model.policy.policyrule.MonolithicPolicyRule;
import sif.model.policy.policyrule.PolicyRuleType;
import sif.model.policy.policyrule.configuration.ConfigurableParameter;
import sif.utilities.XML_Constants;

/***
 * A policy rule to find non considered constants.
 *
 * @author Sebastian Beck
 *
 */
@XmlType(name = XML_Constants.NAME_NON_CONSIDERED_VALUES_POLICY, propOrder = {
		"ignoredCells",
		"ignoredWorksheets"
	})
@XmlAccessorType(XmlAccessType.NONE)
public class NonConsideredValuesPolicyRule extends MonolithicPolicyRule {
	@ConfigurableParameter(parameterClass = String[].class, displayedName = "Ignored Cells.", description = "Defines the cells that should be ignored by the inspection.")
	private String[] ignoredCells = {};
	@ConfigurableParameter(parameterClass = String[].class, displayedName = "Ignored worksheets.", description = "Defines the worksheets that should be ignored by the inspection.")
	private String[] ignoredWorksheets = {};
	
	public NonConsideredValuesPolicyRule() {
		super();
		setAuthor("Sebastian Beck");
		setName("Policy Rule: Non considered values");
		setDescription("Checks if a constant value is not referenced.");
		setType(PolicyRuleType.STATIC);
	}

	@XmlElementWrapper(name = XML_Constants.NAME_IGNORED_CELLS_WRAPPER, required = false)
	@XmlElements({ @XmlElement(name = XML_Constants.NAME_IGNORED_CELLS_VALUE, type = String.class) })
	public String[] getIgnoredCells() {
		return ignoredCells;
	}

	public void setIgnoredCells(String[] ignoredCells) {
		this.ignoredCells = ignoredCells;
	}

	@XmlElementWrapper(name = XML_Constants.NAME_IGNORED_WORKSHEETS_WRAPPER, required = false)
	@XmlElements({ @XmlElement(name = XML_Constants.NAME_IGNORED_WORKSHEETS_VALUE, type = String.class) })
	public String[] getIgnoredWorksheets() {
		return ignoredWorksheets;
	}

	public void setIgnoredWorksheets(String[] ignoredWorksheets) {
		this.ignoredWorksheets = ignoredWorksheets;
	}

}