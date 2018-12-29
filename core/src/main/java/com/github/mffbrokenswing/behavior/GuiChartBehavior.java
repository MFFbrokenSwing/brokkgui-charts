package com.github.mffbrokenswing.behavior;

import com.github.mffbrokenswing.element.GuiChart;
import fr.ourten.teabeans.binding.BaseBinding;
import fr.ourten.teabeans.value.BaseProperty;
import net.voxelindustry.brokkgui.behavior.GuiBehaviorBase;
import net.voxelindustry.brokkgui.event.GuiMouseEvent;

public class GuiChartBehavior<G extends GuiChart> extends GuiBehaviorBase<G>
{

    /* Equal to : horizontalScale / width */
    private BaseProperty<Float> xSpreadingRatio = new BaseProperty<>(1F, "x-spreading-ratio");
    /* Equal to : verticalScale / height  */
    private BaseProperty<Float> ySpreadingRatio = new BaseProperty<>(1F, "y-spreading-ratio");

    private boolean updatingDrag = false;

    private BaseProperty<Float> cachedHorizontalOffset = new BaseProperty<>(0F, "cachedHorizontalOffset");
    private BaseProperty<Float> cachedVerticalOffset = new BaseProperty<>(0F, "cachedVerticalOffset");

    public GuiChartBehavior(G model)
    {
        super(model);

        this.getModel().getEventDispatcher().addHandler(GuiMouseEvent.WHEEL, this::onMouseWheel);
        this.getModel().getEventDispatcher().addHandler(GuiMouseEvent.DRAGGING, this::onMouseDragging);
        this.getModel().getEventDispatcher().addHandler(GuiMouseEvent.DRAG_STOP, this::resetOffsetValues);

        xSpreadingRatio.bind(new BaseBinding<Float>()
        {
            {
                super.bind(
                        model.getWidthProperty(),
                        model.getHorizontalScaleProperty()
                );
            }

            @Override
            public Float computeValue()
            {
                if(model.getWidth() == 0) return 1F;
                return model.getHorizontalScale() / model.getWidth();
            }
        });

        ySpreadingRatio.bind(new BaseBinding<Float>()
        {
            {
                super.bind(
                        model.getHeightProperty(),
                        model.getVerticalScaleProperty()
                );
            }

            @Override
            public Float computeValue()
            {
                if(model.getHeight() == 0) return 1F;
                return model.getVerticalScale() / model.getHeight();
            }
        });

        getModel().getHorizontalOffsetProperty().addListener((obs, old, newValue) -> {
            if(!updatingDrag)
            {
                cachedHorizontalOffset.setValue(cachedHorizontalOffset.getValue() + newValue - old);
            }
        });

        getModel().getVerticalOffsetProperty().addListener((obs, old, newValue) -> {
            if(!updatingDrag)
            {
                cachedVerticalOffset.setValue(cachedVerticalOffset.getValue() + newValue - old);
            }
        });
    }

    private void resetOffsetValues(GuiMouseEvent.DragStop event)
    {
        cachedHorizontalOffset.setValue(getModel().getHorizontalOffset());
        cachedVerticalOffset.setValue(getModel().getVerticalOffset());
    }

    private void onMouseDragging(GuiMouseEvent.Dragging event)
    {
        this.updatingDrag = true;

        getModel().getHorizontalOffsetProperty().setValue(
                cachedHorizontalOffset.getValue() - event.getDragX() * getXSpreadingRatio()
        );
        getModel().getVerticalOffsetProperty().setValue(
                cachedVerticalOffset.getValue()  + event.getDragY() * getYSpreadingRatio()
        );

        this.updatingDrag = false;
    }

    private void onMouseWheel(GuiMouseEvent.Wheel event)
    {
        float previousXSpreadingRatio = getXSpreadingRatio();
        float previousYSpreadingRatio = getYSpreadingRatio();

        float relativeMouseX = event.getMouseX() - getModel().getxPos() - getModel().getxTranslate();
        float relativeMouseY = event.getMouseY() - getModel().getyPos() - getModel().getyTranslate() - getModel().getHeight();

        if(event.getDwheel() > 0)
        {
            getModel().getVerticalScaleProperty().setValue(
                    getModel().getVerticalScale() * 0.9f
            );
            getModel().getHorizontalScaleProperty().setValue(
                    getModel().getHorizontalScale() * 0.9f
            );
        }
        else if(event.getDwheel() < 0)
        {
            getModel().getVerticalScaleProperty().setValue(
                    getModel().getVerticalScale() * 1.1f
            );
            getModel().getHorizontalScaleProperty().setValue(
                    getModel().getHorizontalScale() * 1.1f
            );
        }

        getModel().getHorizontalOffsetProperty().setValue(
                getModel().getHorizontalOffset() + relativeMouseX * previousXSpreadingRatio - relativeMouseX * getXSpreadingRatio()
        );
        getModel().getVerticalOffsetProperty().setValue(
                getModel().getVerticalOffset() - relativeMouseY * previousYSpreadingRatio + relativeMouseY * getYSpreadingRatio()
        );
    }

    public float getXSpreadingRatio()
    {
        return this.xSpreadingRatio.getValue();
    }

    public float getYSpreadingRatio()
    {
        return this.ySpreadingRatio.getValue();
    }

}
