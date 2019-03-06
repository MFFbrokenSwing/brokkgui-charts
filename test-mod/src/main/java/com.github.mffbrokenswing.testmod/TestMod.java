package com.github.mffbrokenswing.testmod;

import com.github.mffbrokenswing.charts.element.GuiGraph;
import com.github.mffbrokenswing.charts.math.Graph;
import com.github.mffbrokenswing.charts.math.Point;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.voxelindustry.brokkgui.BrokkGuiPlatform;
import net.voxelindustry.brokkgui.gui.BrokkGuiScreen;
import net.voxelindustry.brokkgui.paint.Color;
import net.voxelindustry.brokkgui.panel.GuiAbsolutePane;
import net.voxelindustry.brokkgui.wrapper.impl.BrokkGuiManager;

@Mod.EventBusSubscriber(modid = "testmod", value = Side.CLIENT)
@Mod(modid = "testmod", name = "Test mod", version = "0.0.0-SNAPSHOT")
public class TestMod
{

    @SubscribeEvent
    public static void onGuiOpen(GuiOpenEvent event)
    {
        if(event.getGui() != null && event.getGui().getClass().equals(GuiInventory.class))
        {
            event.setGui(BrokkGuiManager.getBrokkGuiScreen("testmod", new TestGui()));
        }
    }


    public static class TestGui extends BrokkGuiScreen
    {

        public TestGui()
        {
            BrokkGuiPlatform.getInstance().enableRenderDebug(true);
            GuiAbsolutePane pane = new GuiAbsolutePane();
            this.setMainPanel(pane);
            pane.setWidth(500);
            pane.setHeight(500);

            Graph g = new Graph();
            g.addPoint(new Point(0, 0));
            g.addPoint(new Point(10, 20));
            g.addPoint(new Point(20, 50));
            g.addPoint(new Point(25, 20));

            GuiGraph graph = new GuiGraph();
            graph.setBackgroundColor(Color.YELLOW);
            graph.getGraphProperty().setValue(g);
            graph.setWidth(200);
            graph.setHeight(200);

            pane.addChild(graph, 100, 100);
        }

    }

}
