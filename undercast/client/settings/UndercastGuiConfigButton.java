/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package undercast.client.settings;

import net.minecraft.src.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import undercast.client.settings.SettingsGUI;

/**
 *
 * @author Florian
 */
public class UndercastGuiConfigButton extends GuiButton {

    public GuiScreen parentScreen;

    public UndercastGuiConfigButton(int i, int i0, int i1, int i2, int i3, String undercast_config, GuiScreen gs) {
        super(i, i0, i1, i2, i3, undercast_config);
        parentScreen = gs;
    }

    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
        if (this.enabled && this.drawButton && par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height) {
            Minecraft.getMinecraft().displayGuiScreen(new SettingsGUI(parentScreen));
        }
        return super.mousePressed(par1Minecraft, par2, par3);
    }
}