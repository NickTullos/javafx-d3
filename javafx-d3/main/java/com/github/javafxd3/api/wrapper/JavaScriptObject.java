package com.github.javafxd3.api.wrapper;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.web.WebEngine;
import netscape.javascript.JSObject;

/**
 * Base class for all JavaScript wrappers
 *
 */
public class JavaScriptObject {

	// #region ATTRIBUTES

	/**
	 * The JavaFx web engine
	 */
	protected WebEngine webEngine;

	/**
	 * The wrapped JavaScript object
	 */
	private JSObject jsObject;

	// #end region

	// #region CONSTRUCTORS

	/**
	 * Constructor
	 * 
	 * @param webEngine
	 */
	public JavaScriptObject(WebEngine webEngine) {
		this.webEngine = webEngine;
	}

	// #end region

	// #region METHODS

	// #region CALL

	/**
	 * Invokes the method with the given name and arguments and returns the
	 * result as Boolean
	 * 
	 * @param methodName
	 * @param args
	 * @return
	 */
	protected Boolean callForBoolean(String methodName, Object... args) {
		Boolean result = (Boolean) jsObject.call(methodName, args);
		return result;
	}

	/**
	 * Invokes the method with the given name and arguments and returns the
	 * result as Boolean
	 * 
	 * @param methodName
	 * @param args
	 * @return
	 */
	protected Integer callForInteger(String methodName, Object... args) {
		Object resultObject = jsObject.call(methodName, args);
		boolean isInteger = resultObject instanceof Integer;
		if (isInteger) {
			Integer result = (Integer) resultObject;
			return result;
		}

		boolean isString = resultObject instanceof String;
		if (isString) {
			String stringResult = (String) resultObject;
			Integer integerResult;
			try {
				integerResult = Integer.parseInt(stringResult);
			} catch (NumberFormatException exception) {
				integerResult = null;
			}
			return integerResult;
		}

		String message = "The object type" + resultObject.getClass().getName() + " is not yet implemented";
		throw new IllegalStateException(message);
	}

	/**
	 * Invokes the method with the given name and arguments and returns the
	 * result as String
	 * 
	 * @param methodName
	 * @param args
	 * @return
	 */
	protected String callForString(String methodName, Object... args) {
		String result = (String) jsObject.call(methodName, args);
		return result;
	}

	/**
	 * Invokes the method with the given name and arguments and returns the
	 * result as String
	 * 
	 * @param methodName
	 * @param args
	 * @return
	 */
	protected String callForString(String methodName, JSObject... args) {

		Object[] objectArray = (Object[]) args;
		Object resultObj = jsObject.call(methodName, objectArray);
		String result = (String) resultObj;
		return result;
	}

	/**
	 * Invokes the method with the given name and arguments and returns the
	 * result as Double
	 * 
	 * @param methodName
	 * @param args
	 * @return
	 */
	protected Double callForDouble(String methodName, Object... args) {
		Object result = jsObject.call(methodName, args);
		boolean isDouble = result instanceof Double;
		if (isDouble) {
			Double doubleResult = (Double) result;
			return doubleResult;
		}

		boolean isInteger = result instanceof Integer;
		if (isInteger) {
			Double doubleResult = Double.parseDouble("" + result);
			return doubleResult;
		}

		boolean isString = result instanceof String;
		if (isString) {
			String stringResult = (String) result;
			Double doubleResult;
			try {
				doubleResult = Double.parseDouble(stringResult);
			} catch (NumberFormatException exception) {
				doubleResult = Double.NaN;
			}
			return doubleResult;
		}

		String message = "The object type" + result.getClass().getName() + " is not yet implemented";
		throw new IllegalStateException(message);

	}

	/**
	 * Invokes the method with the given name and arguments and returns the
	 * result as JSObject
	 * 
	 * @param methodName
	 * @param args
	 * @return
	 */
	protected JSObject call(String methodName, Object... args) {
		Object resultObj = jsObject.call(methodName, args);
		boolean isJsObject = resultObj instanceof JSObject;
		if (isJsObject) {
			JSObject result = (JSObject) resultObj;
			return result;
		}

		boolean isString = resultObj instanceof String;
		if (isString) {
			String result = (String) resultObj;
			boolean isUndefined = result.equals("undefined");
			if (isUndefined) {
				return null;
			} else {
				String message = "A result of type String with the value " + result + "' could not be processed.";
				throw new IllegalStateException(message);
			}
		}

		String typeString = "null";
		if (resultObj != null) {
			typeString = resultObj.getClass().getName();
		}
		String message = "The result type '" + typeString + "' is not yet implemented for method '" + methodName + "'";
		throw new IllegalStateException(message);
	}

	protected Object callThis(Object... args) {
		String varNamePrefix = "temp__var__";

		// store args as member and create variable name list
		List<String> varNames = new ArrayList<>();
		for (int index = 0; index < args.length; index++) {
			String varName = varNamePrefix + index;
			varNames.add(varName);
			jsObject.setMember(varName, args[index]);
		}

		// eval command
		String command = "this(" + String.join(",", varNames) + ");";
		Object result = eval(command);

		// remove temporary members
		for (String varName : varNames) {
			jsObject.removeMember(varName);
		}

		return result;
	}

	protected Integer callThisForInteger(Object... args) {
		Object result = callThis(args);
		return (Integer) result;
	}

	protected Boolean callThisForBoolean(Object... args) {
		Object result = callThis(args);
		return (Boolean) result;
	}

	protected String callThisForString(Object... args) {
		Object result = callThis(args);
		return (String) result;
	}

	protected Double callThisForDouble(Object... args) {
		Object result = callThis(args);
		return (Double) result;
	}

	protected JSObject callThisForJsObject(Object... args) {
		Object result = callThis(args);
		return (JSObject) result;
	}

	// #end region

	// #region GET MEMBER

	/**
	 * Gets the member with the given name
	 * 
	 * @param name
	 * @return
	 */
	public JSObject getMember(String name) {
		JSObject result = (JSObject) jsObject.getMember(name);
		return result;
	}

	/**
	 * Gets the member with the given name as Integer
	 * 
	 * @param name
	 * @return
	 */
	protected Integer getMemberForInteger(String name) {
		Integer result = (Integer) jsObject.getMember(name);
		return result;
	}

	/**
	 * Gets the member with the given name as Boolean
	 * 
	 * @param name
	 * @return
	 */
	protected Boolean getMemberForBoolean(String name) {
		Boolean result = (Boolean) jsObject.getMember(name);
		return result;
	}

	/**
	 * Gets the member with the given name as Double
	 * 
	 * @param name
	 * @return
	 */
	protected Double getMemberForDouble(String name) {
		Object resultObj = jsObject.getMember(name);
		boolean isDouble = resultObj instanceof Double;
		if (isDouble) {
			Double result = (Double) resultObj;
			return result;
		}
		
		boolean isNumber = resultObj instanceof Number;
		if (isNumber){
			Double result = Double.parseDouble("" + resultObj);
			return result;
		}
		
		String message = "Could not retrieve value of type " + resultObj.getClass().getName() +" as double.";
		throw new IllegalStateException(message);
		
	}

	/**
	 * Gets the member with the given name as Float
	 * 
	 * @param name
	 * @return
	 */
	protected Float getMemberForFloat(String name) {
		Float result = (Float) jsObject.getMember(name);
		return result;
	}

	/**
	 * Gets the member with the given name as String
	 * 
	 * @param name
	 * @return
	 */
	protected String getMemberForString(String name) {
		String result = (String) jsObject.getMember(name);
		return result;
	}

	// #end region

	// #region EVAL

	/**
	 * Evaluates the given java script command and returns the result as Object
	 * 
	 * @param command
	 * @return
	 */
	public Object eval(String command) {
		Object result = jsObject.eval(command);
		return result;
	};

	/**
	 * Evaluates the given java script command and returns the result as Boolean
	 * 
	 * @param command
	 * @return
	 */
	public Boolean evalForBoolean(String command) {
		Boolean result = (Boolean) jsObject.eval(command);
		return result;
	};

	/**
	 * Evaluates the given java script command and returns the result as Integer
	 * 
	 * @param command
	 * @return
	 */
	public Integer evalForInteger(String command) {
		Integer result = (Integer) jsObject.eval(command);
		return result;
	};

	/**
	 * Evaluates the given java script command and returns the result as Double
	 * 
	 * @param command
	 * @return
	 */
	public Double evalForDouble(String command) {
		Double result = (Double) jsObject.eval(command);
		return result;
	};

	/**
	 * Evaluates the given java script command and returns the result as String
	 * 
	 * @param command
	 * @return
	 */
	public String evalForString(String command) {
		Object resultObj = jsObject.eval(command);
		String result = (String) resultObj;
		return result;
	};

	/**
	 * Evaluates the given java script command and returns the result as
	 * JSObject
	 * 
	 * @param command
	 * @return
	 */
	public JSObject evalForJsObject(String command) {
		Object resultObj = jsObject.eval(command);
		JSObject result = (JSObject) resultObj;
		return result;
	};

	// #end region

	// #region CAST

	/**
	 * @return
	 */
	public <T> T cast() {
		throw new IllegalStateException("not yet implemented");
	}

	// #end region

	// #end region

	// #region ACCESSORS

	/**
	 * Returns the wrapped JSObject
	 * 
	 * @return
	 */
	public JSObject getJsObject() {
		return jsObject;
	}

	/**
	 * Sets the wrapped JSObject
	 * 
	 * @param wrappedJsObject
	 */
	public void setJsObject(JSObject wrappedJsObject) {
		this.jsObject = wrappedJsObject;
	}

	/**
	 * @return
	 */
	public WebEngine getWebEngine() {
		return webEngine;
	}

	// #end region

}
