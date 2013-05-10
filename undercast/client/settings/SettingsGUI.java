package undercast.client.settings;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ModLoader;
import net.minecraft.src.mod_Undercast;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
public class SettingsGUI extends GuiScreen {
	public String[] toggleSettings = new String[]{ "showFPS", "showKills", "showDeaths", "showKilled", "showServer", "showTeam", "showKD", "showKK", "showFriends", "showMap",
												   "showNextMap", "showStreak", "showGuiChat", "showGuiMulti", "showPlayingTime", "toggleTitleScreenButton", "filterTips", 
												   "fullBright", "matchOnServerJoin", "enableButtonTooltips"};
	public String[] enabledStrings = new String[]{ "FPS Shown", "Kills shown", "Deaths shown", "Killed shown", "Server shown", "Team shown", "KD Shown", "KK Shown", "Friends shown", "Current map shown",
			   										"Next map shown", "Killstreak shown", "Chat gui shown", "Multi gui shown", "Playing time shown", "Title screen button", "Tips filtered", 
			   										"Full bright enabled", "/match on server join", "Button tooltips shown"};
	public String[] disabledStrings = new String[]{ "FPS Hidden", "Kills hidden", "Deaths hidden", "Killed hidden", "Server hidden", "Team hidden", "KD Hidden", "KK Hidden", "Friends hidden", "Current map hidden",
			   										"Next map hidden", "Killstreak hidden", "Chat gui hidden", "Multi gui hidden", "Playing time hidden", "No title screen button", "No tips filtered", 
			   										"Full bright disabled", "No /match on server join", "Button tooltips hidden"};
	@Override
	public void initGui() {
		// Add buttons		
		int x = width / 2 - 75;
		int y = height / 2;
		this.buttonList.add(new GuiButton(1, x, y-50, 150, 20, "Overlay Settings"));
		this.buttonList.add(new GuiButton(2, x, y-20, 150, 20, "General Settings"));
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		
		int y = height / 2;
		drawCenteredString(fontRenderer, "Undercast mod settings", width / 2, y-80, 0x4444bb);

		// Draw label at top of screen
		
		// Draw buttons
		super.drawScreen(par1, par2, par3);
	}
	
	@Override
	protected void keyTyped(char par1, int par2) {
		if (par2 == Keyboard.KEY_ESCAPE) {
			mc.displayGuiScreen(null);
			return;
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton guibutton) {
		if (guibutton.id == 1) {
			ModLoader.openGUI(mc.thePlayer, new OverlaySettings());
		}
		if (guibutton.id == 2) {
			ModLoader.openGUI(mc.thePlayer, new GeneralSettings());
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
}