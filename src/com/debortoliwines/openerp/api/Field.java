/*
 *   This file is part of OpenERPJavaAPI.
 *
 *   OpenERPJavaAPI is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   OpenERPJavaAPI is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public License
 *   along with OpenERPJavaAPI.  If not, see <http://www.gnu.org/licenses/>.
 *
 *   Copyright 2011 De Bortoli Wines Pty Limited (Australia)
 */

package com.debortoliwines.openerp.api;

import java.util.ArrayList;
import java.util.HashMap;

/***
 * Encapsulates the HashMap object returned by OpenERP
 * @author Pieter van der Merwe
 *
 */
public class Field {
	
	public enum FieldType {
		INTEGER, CHAR, TEXT, BOOLEAN, FLOAT, DATETIME, DATE, MANY2ONE, ONE2MANY, MANY2MANY, SELECTION 
	}
	
	private final String name;
	private final HashMap<String, Object> openERPFieldData;
	
	public Field(String fieldName, HashMap<String, Object> openERPFieldData){
		this.openERPFieldData = openERPFieldData;
		this.name = fieldName;
	}

	/***
	 * Any property not covered by a get function can be fetched using this function
	 * @param propertyName Name of property to fetch, for example 'name'.
	 * @return The value associated with the property if any.
	 */
	public Object getFieldProperty(String propertyName){
		Object value = null;
		
		if (openERPFieldData.containsKey(propertyName))
			value = openERPFieldData.get(propertyName);
		
		return value;
	}

	/**
	 * Get the field name 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/***
	 * Get the field description or label
	 * @return
	 */
	public String getDescription() {
		return (String) getFieldProperty("string");
	}

	/**
	 * Get the datatype of the field.  If you want the original OpenERP type, use getFieldProperty("type")
	 * @return
	 */
	public FieldType getType() {
		String fieldType = (String) getFieldProperty("type");
		
		if (fieldType.equalsIgnoreCase("char"))
			return FieldType.CHAR;
		else if (fieldType.equalsIgnoreCase("text"))
			return FieldType.TEXT;
		else if (fieldType.equalsIgnoreCase("boolean"))
			return FieldType.BOOLEAN;
		else if (fieldType.equalsIgnoreCase("float"))
			return FieldType.FLOAT;
		else if (fieldType.equalsIgnoreCase("datetime"))
			return FieldType.DATETIME;
		else if (fieldType.equalsIgnoreCase("date"))
			return FieldType.DATE;
		else if (fieldType.equalsIgnoreCase("many2one"))
			return FieldType.MANY2ONE;
		else if (fieldType.equalsIgnoreCase("one2many"))
			return FieldType.ONE2MANY;
		else if (fieldType.equalsIgnoreCase("many2many"))
			return FieldType.MANY2MANY;
		else if (fieldType.equalsIgnoreCase("selection"))
			return FieldType.SELECTION;
		else return FieldType.CHAR;
	}

	/**
	 * Get the required property
	 * @return
	 */
	public boolean getRequired() {
		Object value = getFieldProperty("required");
		if (value == null)
			return false;
		return (Boolean) value;
	}

	/**
	 * Get the selectable property
	 * @return
	 */
	public boolean getSelectable() {
		Object value = getFieldProperty("selectable");
		if (value == null)
			return true;
		else return (Boolean) value;
	}

	public ArrayList<SelectionOption> getSelectionOptions(){
		if (this.getType() != FieldType.SELECTION)
			return null;

		ArrayList<SelectionOption> options = new ArrayList<SelectionOption>();
		Object values = getFieldProperty("selection");
		if (values instanceof Object[])
			for(Object val : (Object []) values){
				Object [] multiVal = (Object[]) val;
				options.add(new SelectionOption(multiVal[0].toString(), multiVal[1].toString()));
			}
		return options;
	}

	/**
	 * Get the size property
	 * @return
	 */
	public int getSize() {
		Object value = getFieldProperty("size");
		if (value == null)
			return 64;
		else return (Integer) value;
	}

	/**
	 * Get the help property
	 * @return
	 */
	public String getHelp() {
		return (String) getFieldProperty("help");
	}

	/**
	 * Get the store property
	 * @return
	 */
	public boolean getStore() {
		Object value = getFieldProperty("store");
		if (value == null)
			return true;
		return (Boolean) value;
	}

	/**
	 * Get the func_method property
	 * @return
	 */
	public boolean getFunc_method() {
		Object value = getFieldProperty("func_method");
		if (value == null)
			return false;
		return (Boolean) value;
	}

	/**
	 * Get the relation property
	 * @return
	 */
	public String getRelation() {
		Object value = getFieldProperty("relation");
		if (value == null)
			return "";
		return (String) value;
	}

	/**
	 * Get the readonly property
	 * @return
	 */
	public boolean getReadonly() {
		Object value = getFieldProperty("readonly");
		if (value == null)
			return false;
		else return (Boolean) (value instanceof Integer ? (Integer) value == 1: value);
	}
}
