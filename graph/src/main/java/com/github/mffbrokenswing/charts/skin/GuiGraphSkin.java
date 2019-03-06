package com.github.mffbrokenswing.charts.skin;

import com.github.mffbrokenswing.charts.behavior.GuiChartBehavior;
import com.github.mffbrokenswing.charts.element.GuiGraph;
import com.github.mffbrokenswing.charts.math.Point;
import net.voxelindustry.brokkgui.internal.EGuiRenderMode;
import net.voxelindustry.brokkgui.internal.IGuiRenderer;
import net.voxelindustry.brokkgui.paint.Color;
import net.voxelindustry.brokkgui.paint.RenderPass;
import net.voxelindustry.brokkgui.skin.GuiBehaviorSkinBase;

public class GuiGraphSkin<G extends GuiGraph, B extends GuiChartBehavior<G>> extends GuiBehaviorSkinBase<G, B>
{

    public GuiGraphSkin(G model, B behavior)
    {
        super(model, behavior);
    }

    @Override
    public void render(RenderPass pass, IGuiRenderer renderer, int mouseX, int mouseY)
    {
        super.render(pass, renderer, mouseX, mouseY);

        if(pass == RenderPass.MAIN)
        {
            this.renderGraph(renderer);
        }

    }

    private void renderGraph(IGuiRenderer renderer)
    {
        // For color
        // TODO Ask for a method to change color
        renderer.getHelper().drawColoredCircle(renderer, 0, 0, 0, 0, Color.BLACK);

        // ---- Axes -----

        // Y axis
        if(getModel().getHorizontalOffset() <= 0 && getModel().getHorizontalOffset() + getModel().getHorizontalScale() >= 0)
        {
            Point bot = prepareForRendering(new Point(0, getModel().getVerticalOffset()));
            Point top = prepareForRendering(new Point(0, getModel().getVerticalOffset() + getModel().getVerticalScale()));
            drawAxe(renderer, bot, top);
        }

        // X axis
        if(getModel().getVerticalOffset() <= 0 && getModel().getVerticalOffset() + getModel().getVerticalScale() >= 0)
        {
            Point left = prepareForRendering(new Point(getModel().getHorizontalOffset(), 0));
            Point right = prepareForRendering(new Point(getModel().getHorizontalOffset() + getModel().getHorizontalScale(), 0));
            drawAxe(renderer, left, right);
        }

        // ----- Graph -----

        renderer.getHelper().beginScissor();
        renderer.getHelper().scissorBox(
                getModel().getxPos() + getModel().getxTranslate(),
                getModel().getyPos() + getModel().getyTranslate(),
                getModel().getxPos() + getModel().getxTranslate() + getModel().getWidth(),
                getModel().getyPos() + getModel().getyTranslate() + getModel().getHeight()
        );
        renderer.beginDrawing(EGuiRenderMode.LINE_STRIP, false);

        getModel().getGraph().getPoints().forEach(p -> {
            Point toRender = prepareForRendering(p);
            renderer.addVertex(toRender.getX(), toRender.getY(), getModel().getzLevel());
        });

        renderer.endDrawing();

        renderer.getHelper().endScissor();
    }

    private void drawAxe(IGuiRenderer renderer, Point p1, Point p2)
    {
        renderer.beginDrawing(EGuiRenderMode.LINES, false);
        renderer.addVertex(p1.getX(), p1.getY(), getModel().getzLevel());
        renderer.addVertex(p2.getX(), p2.getY(), getModel().getzLevel());
        renderer.endDrawing();
    }

    private Point prepareForRendering(Point p)
    {
        float graphX = getModel().getxPos() + getModel().getxTranslate();
        float graphY = getModel().getyPos() + getModel().getyTranslate();

        float xRatio = getBehavior().getXSpreadingRatio();
        float yRatio = getBehavior().getYSpreadingRatio();

        float x = graphX + (p.getX() - getModel().getHorizontalOffset()) / xRatio;
        float y = graphY + getModel().getHeight() + (getModel().getVerticalOffset() - p.getY()) / yRatio;
        return new Point(x, y);
    }

}
