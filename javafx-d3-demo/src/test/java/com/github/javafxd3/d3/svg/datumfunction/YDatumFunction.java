package com.github.javafxd3.d3.svg.datumfunction;

import com.github.javafxd3.d3.core.Value;
import com.github.javafxd3.d3.functions.DatumFunction;

import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

/**
 * A datum function that returns the y coordinate as double
 *  
 */
public class YDatumFunction implements DatumFunction<Double> {
	
	//#region ATTRIBUTES
	
	private WebEngine webEngine;	
	
	//#end region
	
	//#region CONSTRUCTORS
	
	/**
	 * @param webEngine
	 */
	public YDatumFunction(WebEngine webEngine){
		this.webEngine=webEngine;
		
	}
	
	//#end region
	
	//#region METHODS

	@Override
	public Double apply(Object context, Object datum, int index) {
		
		JSObject jsObject = (JSObject) datum;
		Value value = new Value(webEngine, jsObject);	
		Double y = value.asCoords().y();	
		return y;
	}
	
	//#end region

}
