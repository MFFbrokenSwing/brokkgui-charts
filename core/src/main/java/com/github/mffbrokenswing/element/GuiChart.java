package com.github.mffbrokenswing.element;

import fr.ourten.teabeans.value.BaseProperty;
import net.voxelindustry.brokkgui.control.GuiControl;

public abstract class GuiChart extends GuiControl
{

    private BaseProperty<Float> verticalScale = new BaseProperty<>(100F, "vertical-scale");
    private BaseProperty<Float> horizontalScale = new BaseProperty<>(100F, "horizontal-scale");

    private BaseProperty<Float> verticalOffset = new BaseProperty<>(0F, "vertical-offset");
    private BaseProperty<Float> horizontalOffset = new BaseProperty<>(0F, "horizontal-offset");

    public GuiChart()
    {
        super("chart");

        this.verticalScale.setChecker(GuiChart::strictPositiveValue);
        this.horizontalScale.setChecker(GuiChart::strictPositiveValue);
    }

    private static float strictPositiveValue(float oldValue, float newValue)
    {
        return newValue > 0f ? newValue : oldValue;
    }

    // ################
    // # Scale Values #
    // ################

    /**
     * Returns the difference between the y coordinate of a point at the top of the graph and the y coordinate of a
     * point at the bot of the graph.
     * Example :
     *
     * Let two points of coordinates (x1,y1) and (x2,y2) respectively at the top and the bot of the graph
     *
     *       . = (x1, y1)
     *      |
     *      |
     *      |
     *      |
     *      |
     *      |
     *      |
     *      |______________________. = (x2, y2)
     *
     *
     * Then the vertical scale is defined as <code>y1 - y2</code>
     *
     * This code is equivalent to <code>getVerticalScaleProperty().getValue()</code>
     * @return the vertical scale of this graph
     */
    public float getVerticalScale()
    {
        return this.verticalScale.getValue();
    }

    /**
     * Returns the difference between the x coordinate of a point at the left of the graph and the x coordinate of a
     * point at the right of the graph.
     * Example :
     *
     * Let two points of coordinates (x1,y1) and (x2,y2) respectively at the left and the right of the graph
     *
     *       . = (x1, y1)
     *      |
     *      |
     *      |
     *      |
     *      |
     *      |
     *      |
     *      |______________________. = (x2, y2)
     *
     *
     * Then the vertical scale is defined as <code>x2 - x1</code>
     *
     * This code is equivalent to <code>getHorizontalScaleProperty().getValue()</code>
     * @return the horizontal scale of this graph
     */
    public float getHorizontalScale()
    {
        return this.horizontalScale.getValue();
    }

    // #################
    // # Offset Values #
    // #################

    /**
     * Returns the y coordinate of a point at the bot of the graph.
     * Example :
     *  Let 560 be the vertical offset, then any point on the first bottom line of the graph has 560 for y coordinate
     *
     * This code is equivalent to <code>getVerticalOffsetProperty().getValue()</code>
     * @return the vertical offset of this graph
     */
    public float getVerticalOffset()
    {
        return this.verticalOffset.getValue();
    }

    /**
     * Returns the x coordinate of a point at the left of the graph.
     * Example :
     *  Let 430 be the horizontal offset, then any point on the first left column of the graph has 560 for x coordinate
     *
     * This code is equivalent to <ode>getHorizontalOffsetProperty().getValue()</ode>
     * @return the horizontal offset of this graph
     */
    public float getHorizontalOffset()
    {
        return this.horizontalOffset.getValue();
    }

    // #####################
    // # Properties Access #
    // #####################

    /**
     * Return the property for the vertical scale
     *
     * @see GuiChart#getVerticalScale()
     * @return the {@link BaseProperty} for the vertical scale
     */
    public BaseProperty<Float> getVerticalScaleProperty()
    {
        return this.verticalScale;
    }

    /**
     * Returns the property for the horizontal scale
     *
     * @see GuiChart#getHorizontalScale()
     * @return the {@link BaseProperty} for the horizontal scale
     */
    public BaseProperty<Float> getHorizontalScaleProperty()
    {
        return this.horizontalScale;
    }

    /**
     * Returns the property for the vertical offset
     *
     * @see GuiChart#getVerticalOffset()
     * @return the {@link BaseProperty} for the vertical offset
     */
    public BaseProperty<Float> getVerticalOffsetProperty()
    {
        return this.verticalOffset;
    }

    /**
     * Returns the property for the horizontal offset
     *
     * @see GuiChart#getHorizontalOffset()
     * @return the {@link BaseProperty} for the horizontal offset
     */
    public BaseProperty<Float> getHorizontalOffsetProperty()
    {
        return this.horizontalOffset;
    }
}
