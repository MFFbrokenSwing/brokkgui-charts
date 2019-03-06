package com.github.mffbrokenswing.charts.element;

import com.github.mffbrokenswing.charts.behavior.GuiChartBehavior;
import com.github.mffbrokenswing.charts.math.Graph;
import com.github.mffbrokenswing.charts.skin.GuiGraphSkin;
import fr.ourten.teabeans.value.BaseProperty;
import net.voxelindustry.brokkgui.skin.GuiSkinBase;

public class GuiGraph extends GuiChart
{

    private BaseProperty<Graph> graph = new BaseProperty<>(new Graph(), "graph");

    @Override
    protected GuiSkinBase<?> makeDefaultSkin()
    {
        return new GuiGraphSkin<>(this, new GuiChartBehavior<>(this));
    }

    public Graph getGraph()
    {
        return this.graph.getValue();
    }

    public BaseProperty<Graph> getGraphProperty()
    {
        return graph;
    }
}
