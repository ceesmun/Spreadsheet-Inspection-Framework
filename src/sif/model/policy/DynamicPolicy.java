/**
 * 
 */
package sif.model.policy;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import sif.model.policy.policyrule.DynamicPolicyRule;
import sif.model.policy.policyrule.IOCellInfo;
import sif.model.policy.policyrule.SanityPolicyRule;
import sif.model.policy.policyrule.configuration.ConfigurableParameter;

/**
 * @author Manuel Lemcke
 */
@XmlRootElement
@XmlSeeAlso({DynamicPolicyRule.class})
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(name = "dynamicPolicy", propOrder = {"spreadsheetFileName", "inputCells", "outputCells", "sanityRules"})
public class DynamicPolicy extends Policy {
	
	private String spreadsheetFileName = null;
	private List<IOCellInfo> inputCells;
	private List<IOCellInfo> outputCells;

	@ConfigurableParameter(parameterClass = SanityPolicyRule.class, displayedName = "Sanity rules", description = "Configures the cells relevant for the sanity checks.")
	private SanityPolicyRule sanityRules = new SanityPolicyRule();
	
	@XmlElement(name = "sanityRules", type = SanityPolicyRule.class)
	public SanityPolicyRule getSanityRules() {
		return sanityRules;
	}

	public void setSanityRules(SanityPolicyRule sanityRules) {
		this.sanityRules = sanityRules;
		add(sanityRules);
	}
	/**
	 * 
	 */
	public DynamicPolicy() {
		super();
		this.setName("");
		this.setAuthor("");
		this.setDescription("");
	}	
	
	/**
	 * @return the inputCells
	 */
	@XmlElementWrapper(name = "inputCells")
	@XmlElement(name = "inputCell", type=IOCellInfo.class)	
	public List<IOCellInfo> getInputCells() {
		return inputCells;
	}

	/**
	 * @param inputCells the inputCells to set
	 */
	public void setInputCells(List<IOCellInfo> inputCells) {
		this.inputCells = inputCells;
	}

	/**
	 * @return the outputCells
	 */
	@XmlElementWrapper(name = "outputCells")
	@XmlElement(name = "outputCell", type=IOCellInfo.class)
	public List<IOCellInfo> getOutputCells() {
		return outputCells;
	}

	/**
	 * @param outputCells the outputCells to set
	 */
	public void setOutputCells(List<IOCellInfo> outputCells) {
		this.outputCells = outputCells;
	}



//	public List<AbstractPolicyRule> getRules() {
//		List<AbstractPolicyRule> list = new ArrayList<AbstractPolicyRule>();
//		list.addAll(this.getPolicyRules().values());
//		return list;
//	}
//
//	public void setRules(List<DynamicPolicyRule> rules) {
//		for (DynamicPolicyRule rule : rules) {
//			if (rule.getName() != null && rule.getName() != "") {
//				this.abstractPolicyRules.put(rule.getName(), rule);	
//			}			
//		}
//		
//	}

	/**
	 * @return the spreadsheetFileName
	 */
	@XmlElement(name="spreadsheetFilePath")
	public String getSpreadsheetFileName() {
		return spreadsheetFileName;
	}

	/**
	 * @param spreadsheetFileName the spreadsheetFilePath to set
	 */
	public void setSpreadsheetFileName(String spreadsheetFileName) {
		this.spreadsheetFileName = spreadsheetFileName;
	}
}
