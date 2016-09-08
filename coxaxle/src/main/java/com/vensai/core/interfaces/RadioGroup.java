package com.vensai.core.interfaces;

import java.util.List;

import com.vensai.core.interfaces.impl.RadioGroupImpl;
import com.vensai.core.interfaces.impl.internal.ImplementedBy;

/**
 * Interface for a select element.
 */
@ImplementedBy(RadioGroupImpl.class)
public interface RadioGroup extends Element {

	/**
	 * @author - Sabitha
	 * @summary - Sets the number of radio buttons found in the group
	 */
	// public void setNumberOfRadioButtons();

	/**
	 * @author - Sabitha
	 * @summary - Returns the number of radio buttons found in the group
	 * @return int - number of radio buttons found in the group
	 */
	// public int getNumberOfRadioButtons();

	/**
	 * @author - Sabitha
	 * @summary - Allows a radio button to be selected by its index within the
	 *          group
	 */
	public void selectByIndex(int index);

	/**
	 * @author - Sabitha
	 * @summary - Returns a List<String> of all options in the radio group
	 * @return List<String> - all options in the radio group
	 */
	public List<String> getAllOptions();

	/**
	 * @author - Sabitha
	 * @summary - Allows a radio button to be selected by its value/option text
	 */
	public void selectByOption(String option);

	/**
	 * @author - Sabitha
	 * @summary - Sets the number of values/options found in the radio group
	 */
	// public void setNumberOfOptions();

	/**
	 * @author - Sabitha
	 * @summary - Returns the number of values/options found in the radio group
	 * @return int - number of values/options found in the radio group
	 */
	public int getNumberOfOptions();

	/**
	 * @author - Sabitha
	 * @summary - Sets the value/option of the selected radio button
	 */
	// public void setSelectedOption();

	/**
	 * @author - Sabitha
	 * @summary - Returns the value/option of the selected radio button
	 * @return String - value/option of the selected radio button
	 */
	public String getSelectedOption();

	/**
	 * @author - Sabitha
	 * @summary - Returns the index of the selected radio button
	 * @return int - index of the selected radio button
	 */
	public int getSelectedIndex();

}