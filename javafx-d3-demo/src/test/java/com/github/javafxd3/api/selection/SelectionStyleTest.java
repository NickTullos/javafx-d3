package com.github.javafxd3.api.selection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.javafxd3.api.core.Selection;
import com.github.javafxd3.api.core.Value;
import com.github.javafxd3.api.functions.DatumFunction;
import com.github.javafxd3.api.wrapper.D3NodeFactory;
import com.github.javafxd3.api.wrapper.Element;


@SuppressWarnings("javadoc")
public class SelectionStyleTest extends AbstractSelectionTest {

	/**
	 * 
	 */
	private static final String VALUE = "0.75";
	private static final String STYLE_CAMEL = "opacity";
	private static final String STYLE_HTML = "opacity";
	private static final Double DELTA = 1e-4;

	@Override
	@Test
	public void doTest() {
		testSetterConstantString();
		testSetterConstantDouble();
		testSetterFunction();
		testSetterFunctionImportant();
		testGetter();

	}

	protected void testSetterFunction() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		final double value = 0.5;
		selection.style(STYLE_HTML, new DatumFunction<Double>() {
			@Override
			public Double apply(final Object context, final Object datum, final int index) {
				return value;
			}
		});
		assertEquals(Double.toString(value), getElementStyle(0, STYLE_CAMEL));

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.style(STYLE_HTML, new DatumFunction<Double>() {
			@Override
			public Double apply(final Object context, final Object datum, final int index) {
				return value;
			}
		});
		assertEquals(Double.toString(value), getElementStyle(0, STYLE_CAMEL));
		assertEquals(Double.toString(value), getElementStyle(1, STYLE_CAMEL));
		assertEquals(Double.toString(value), getElementStyle(2, STYLE_CAMEL));

	}

	protected void testSetterFunctionImportant() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		final double value = 1.54;
		selection.style(SelectionStyleTest.STYLE_HTML, new DatumFunction<Double>() {
			@Override
			public Double apply(final Object context, final Object datum, final int index) {
				return value;
			}
		}, true);
		assertEquals("1.54", getElementStyle(0, SelectionStyleTest.STYLE_CAMEL));

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.style(SelectionStyleTest.STYLE_HTML, new DatumFunction<Double>() {
			@Override
			public Double apply(final Object context, final Object datum, final int index) {
				return value;
			}
		});
		assertEquals("1.54", getElementStyle(0, SelectionStyleTest.STYLE_CAMEL));
		assertEquals("1.54", getElementStyle(1, SelectionStyleTest.STYLE_CAMEL));
		assertEquals("1.54", getElementStyle(2, SelectionStyleTest.STYLE_CAMEL));

	}

	protected void testSetterConstantString() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		String value = "1.54";
		selection.style(SelectionStyleTest.STYLE_HTML, value);
		assertEquals(value, getElementStyle(0, SelectionStyleTest.STYLE_CAMEL));

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.style(SelectionStyleTest.STYLE_HTML, value);
		assertEquals(value, getElementStyle(0, SelectionStyleTest.STYLE_CAMEL));
		assertEquals(value, getElementStyle(1, SelectionStyleTest.STYLE_CAMEL));
		assertEquals(value, getElementStyle(2, SelectionStyleTest.STYLE_CAMEL));

	}

	protected void testSetterConstantDouble() {
		// works with single selection
		Selection selection = givenASimpleSelection(new Label());
		double value = 0.5;
		selection.style("opacity", value);
		assertEquals(value, Double.parseDouble(getElementStyle(0, "opacity")),DELTA);

		// works with multiple selection
		Selection selection2 = givenAMultipleSelection(new Label(), new Label(), new Label());
		selection2.style("opacity", value);
		assertEquals(value, Double.parseDouble(getElementStyle(0, "opacity")),DELTA);
		assertEquals(value, Double.parseDouble(getElementStyle(1, "opacity")),DELTA);
		assertEquals(value, Double.parseDouble(getElementStyle(2, "opacity")),DELTA);
	}

	protected void testGetter() {
		// with single selection
		Label label = new Label();
		label.getStyle().setProperty(SelectionStyleTest.STYLE_CAMEL, SelectionStyleTest.VALUE);
		Selection selection = givenASimpleSelection(label);
		//Object object = JsoInspector.convertToInspectableObject(label.getStyle());
		assertEquals(SelectionStyleTest.VALUE, selection.style(SelectionStyleTest.STYLE_HTML));

		// with multiple selection, should return the first element
		Selection selection2 = givenAMultipleSelection(
				createLabel(SelectionStyleTest.STYLE_CAMEL, SelectionStyleTest.VALUE),
				createLabel(SelectionStyleTest.STYLE_CAMEL, "start"),
				createLabel(SelectionStyleTest.STYLE_CAMEL, "blah"));
		//object = JsoInspector.convertToInspectableObject(selection2.node());

		assertEquals(SelectionStyleTest.VALUE, selection2.style(SelectionStyleTest.STYLE_HTML));

	}

	private D3NodeFactory createLabel(final String style, final String value) {
		Label l = new Label();
		l.getStyle().setProperty(style, value);
		return l;
	}
}
