package com.vensai.core.interfaces;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.vensai.core.interfaces.impl.WebtableImpl;
import com.vensai.core.interfaces.impl.internal.ImplementedBy;

/**
 * Interface that wraps a WebElement in CheckBox functionality.
 */
@ImplementedBy(WebtableImpl.class)
public interface Webtable extends Element {
	/**
	 * @summary - Get the row count of the Webtable
	 */
	int getRowCount();

	/**
	 * @summary - Click cell in the specified row and Column in a Webtable
	 */
	void clickCell(int row, int column);

	/**
	 * @summary - Get Row number where text is found in a specific column and
	 *          starting row and case can be ignored
	 */
	int getRowWithCellText(String text, int columnPosition, int startRow, boolean exact);

		//------------------------------------------------------
	    /**
     * Get the column count for the Webtable on a specified Row
     */
    int getColumnCount(WebDriver driver, int row);

    /**
     * Get cell data of the specified row and Column in a Webtable
     */
    String getCellData( WebDriver driver, int row, int column);
    

    /**
     * Return the Cell of the specified row and Column in a Webtable
     */
    WebElement getCell( WebDriver driver, int row, int column);
    WebElement getCell( SearchContext driver, int row, int column);

    /**
     * Click cell in the specified row and Column in a Webtable
     */
    void clickCell( WebDriver driver, int row, int column);
    
    
    /**
     * Get Row number where text is found
     */
    int getRowWithCellText(WebDriver driver, String text);

    /**
     * Get Row number where text is found in a specific column
     */    
    int getRowWithCellText( WebDriver driver, String text, int columnPosition);

    /**
     * Get Row number where text is found in a specific column and starting row
     */    
    int getRowWithCellText( WebDriver driver, String text, int columnPosition, int startRow);
    
    /**
     * Get Column number where text is found
     */  
    int getColumnWithCellText(WebDriver driver, String text);
    
    /**
     * Get Column number where text is found in a specific row
     */  
    int getColumnWithCellText(WebDriver driver, String text, int rowPosition);
    
    /**
     * Get Row number where text is found within a specific column - using 'contains'
     */    
    int getRowThatContainsCellText( SearchContext driver, String text, int columnPosition);

}