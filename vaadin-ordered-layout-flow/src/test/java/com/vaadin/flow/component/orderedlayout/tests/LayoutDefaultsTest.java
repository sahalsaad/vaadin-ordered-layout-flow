package com.vaadin.flow.component.orderedlayout.tests;

import java.util.stream.Stream;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import org.junit.Assert;
import org.junit.Test;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class LayoutDefaultsTest {

    @Test
    public void testHorizontalLayout_byDefault_spacingIsOn() {
        Assert.assertTrue("Spacing should be on by default",
                new HorizontalLayout().isSpacing());
        Assert.assertFalse("Padding shouldn't be on by default",
                new HorizontalLayout().isPadding());
        Assert.assertFalse("Margin shouldn't be on by default",
                new HorizontalLayout().isMargin());
    }

    @Test
    public void testVerticalLayout_byDefault_spacingAndPaddingIsOn() {
        Assert.assertTrue("Padding should be on by default",
                new VerticalLayout().isPadding());
        Assert.assertTrue("Spacing should be on by default",
                new VerticalLayout().isSpacing());
        Assert.assertFalse("Margin shouldn't be on by default",
                new VerticalLayout().isMargin());
    }

    @Test
    public void create_Layout() {
        // Just testing that creating layout actually compiles and doesn't
        // throw. Test is on purpose, so that the implementation not
        // accidentally removed.
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addClickListener(event -> {
        });

        FlexLayout flexLayout = new FlexLayout();
        flexLayout.addClickListener(event -> {
        });

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addClickListener(event -> {
        });
    }

    @Test
    public void defaultAlignmentValues() {
        VerticalLayout verticalLayout = new VerticalLayout();
        Assert.assertEquals(Alignment.STRETCH,
                verticalLayout.getDefaultHorizontalComponentAlignment());

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Assert.assertEquals(Alignment.STRETCH,
                horizontalLayout.getDefaultVerticalComponentAlignment());

    }

    @Test
    public void expandable_Layout() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addAndExpand(new Label("Foo"), new Label("bar"));
        testExpandableComponent(horizontalLayout.getWidth(),
                horizontalLayout.getChildren());

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addAndExpand(new Label("Foo"), new Label("bar"));
        testExpandableComponent(verticalLayout.getHeight(),
                verticalLayout.getChildren());
    }

    @Test
    public void testFlexLayout_setAndUnsetAlignContent() {
        FlexLayout layout = new FlexLayout();
        FlexLayout.ContentAlignment contentAlignment =
                FlexLayout.ContentAlignment.CENTER;
        layout.setAlignContent(contentAlignment);

        Assert.assertEquals("should set align-content",
                layout.getAlignContent(), contentAlignment);

        layout.setAlignContent(null);
        Assert.assertEquals("should return stretch if no align-content set",
                layout.getAlignContent(), FlexLayout.ContentAlignment.STRETCH);
    }

    @Test
    public void testFlexLayout_setAndRemoveFlexBasis() {
        FlexLayout layout = new FlexLayout();
        Div div = new Div();
        layout.add(div);
        layout.setFlexBasis("10px", div);

        Assert.assertEquals("should set flex-basis",
                layout.getFlexBasis(div), "10px");

        layout.setFlexBasis(null, div);
        Assert.assertNull("should remove flex-basis from component",
                layout.getFlexBasis(div));
    }

    @Test
    public void testFlexLayout_setAndUnsetFlexDirection() {
        FlexLayout layout = new FlexLayout();
        FlexComponent.FlexDirection direction = FlexComponent.FlexDirection.ROW_REVERSE;
        layout.setFlexDirection(direction);

        Assert.assertEquals("should set flex-direction",
                layout.getFlexDirection(layout), direction);

        layout.setFlexDirection(null);
        Assert.assertEquals("should return row if no flex-direction set",
                layout.getFlexDirection(layout), FlexLayout.FlexDirection.ROW);
    }

    @Test
    public void testFlexLayout_setFlexShrink() {
        FlexLayout layout = new FlexLayout();
        Div div = new Div();
        layout.add(div);
        layout.setFlexShrink(2, div);

        Assert.assertEquals("should set flex-shrink",
                layout.getFlexShrink(div), 2, 0);
    }

    @Test
    public void testFlexLayout_getFlexShrink_returnOneIfNotSet() {
        FlexLayout layout = new FlexLayout();
        Div div = new Div();
        layout.add(div);

        Assert.assertEquals("should return 1 if flex-shirk not set",
                layout.getFlexShrink(div), 1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFlexLayout_setFlexShrink_throwExceptionIfNegative() {
        FlexLayout layout = new FlexLayout();
        Div div = new Div();
        layout.add(div);
        layout.setFlexShrink(-1, div);
    }

    @Test
    public void testFlexLayout_setAndUnsetOrder() {
        FlexLayout layout = new FlexLayout();
        Div div = new Div();
        layout.add(div);

        Assert.assertEquals("should return 0 if no order set",
                layout.getOrder(div), 0);

        layout.setOrder(1, div);
        Assert.assertEquals("should set order",
                layout.getOrder(div), 1);

        layout.setOrder(0, div);
        Assert.assertEquals("should unset order",
                layout.getOrder(div), 0);
    }

    private void testExpandableComponent(String size,
            Stream<Component> components) {
        Assert.assertEquals(size, "100%");

        components.forEach(component -> Assert.assertEquals(
                component.getElement().getStyle().get("flex-grow"), "1.0"));
    }
}
