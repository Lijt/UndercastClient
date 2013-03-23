package net.minecraft.src;
//You may not release this source under any condition, it must be linked to this page
//You may recompile and publish as long as skipperguy12 and Guru_Fraser are given credit
//You may not claim this to be your own
//You may not remove these comments

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import tc.oc.AresChatHandler;
import tc.oc.AresConfig;
import tc.oc.AresCustomMethods;
import tc.oc.AresGuiListener;
import tc.oc.AresData;
import tc.oc.AresMenuButton;

import net.minecraft.client.Minecraft;
import tc.oc.server.Ares_ServerGUI;

public class mod_Ares extends BaseMod {
	protected String username = "Not_Found";
	protected Minecraft mc = Minecraft.getMinecraft();
	private boolean deathScreenActive;
	private boolean mainMenuActive;
    public static ArrayList<String> servers = new ArrayList<String>();
    public static AresConfig CONFIG;

	@Override
	public String getVersion() {
		return "1.5.1";
	}

	@Override
	public void load() {
        // Custom Config
		CONFIG = new AresConfig();

		//main hooks
		ModLoader.setInGUIHook(this, true, false);
		ModLoader.setInGameHook(this, true, false);

		ModLoader.addLocalization("keybind", "gui");
		ModLoader.addLocalization("keybind", "inGameGui");

		//load variables defaults
		new AresData();

		//hook keybinds
		ModLoader.registerKey(this, AresData.keybind, false);
		ModLoader.registerKey(this, AresData.keybind2, false);

        //Pulls servers from web for GUI Server List and sorts them
        servers = getServers();
        //keep it matching the website until we have a sort button
        //Collections.sort(servers);
        
        //start thread for the main menu button
        Thread thread = new Thread() {
			public void run() {
				while(true){
					//if the main menu is active then add a button
					if(mc.currentScreen instanceof GuiMainMenu && mc.currentScreen.buttonList.size()>0){
						//if you have not added the button already then add it
						if(!mainMenuActive){
							//edit the current multiplayer button
							GuiButton multi =((GuiButton)mc.currentScreen.buttonList.get(1));
							multi.width = (multi.width/2) - 1;
							mc.currentScreen.buttonList.set(1, multi);
							//get values
							int y = multi.yPosition;
							int x = multi.xPosition + multi.width + 2;
							int height = multi.height;
							int width = multi.width;
							//add the custom ares button
							AresMenuButton test = new AresMenuButton(-1, x, y, width, height, "oc.tc");
							mc.currentScreen.buttonList.add(test);
							mc.currentScreen.updateScreen();
							mainMenuActive=true;
						}
					}
					else{
						mainMenuActive=false;
					}
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {}
				}
			}
		};
		thread.start();
	}

	/**
	 * Finds the current list of servers from the website.
	 * @return ArrayList of server ips
	 */
    public static ArrayList<String> getServers() {
        ArrayList<String> list = new ArrayList<String>();

        try {
            URL url = new URL("https://oc.tc/play");
            URLConnection con = url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("<li>") && line.endsWith("</li>")) {
                    list.add(line.replace("<li>", "").replace("</li>", ""));
                }
            }
        } catch (Exception ignored) {
        	System.err.println("[ProjectAres]: ERROR: Could not get online list of servers");
        	//if you can't get it from the website load defaults
        	list.clear();
        	list.add("alpha.oc.tc:25565");
        	list.add("beta.oc.tc:25565");
        	list.add("gamma.oc.tc:25565");
        	list.add("delta.oc.tc:25565");
        	list.add("epsilon.oc.tc:25565");
        	list.add("zeta.oc.tc:25565");
        	list.add("eta.oc.tc:25565");
        	list.add("theta.oc.tc:25565");
        	list.add("iota.oc.tc:25565");
        	list.add("kappa.oc.tc:25565");
        	list.add("lambda.oc.tc:25565");
        	list.add("nostalgia.oc.tc:25565");
        }

        return list;
    }

	/**
	 * On client chat event this is called.
	 * Send all the info to the AresChatHandler
	 * NOTE: only sends none global ares messages
	 */
	public void clientChat(String var1) {
		Minecraft mc = ModLoader.getMinecraftInstance();
		EntityPlayer player = mc.thePlayer;
		username = mc.thePlayer.username;
		String message = StringUtils.stripControlCodes(var1);
		//stop global msg to go through
		if (!message.startsWith("<"))
			new AresChatHandler(message, username, player);
	}

	/**
	 * On game tick this is called.
	 * Draws the gui ingame based on the config file
	 */
	public boolean onTickInGame(float time, Minecraft mc) {
		//if the game over screen is active then you have died
		//if it is the first time it is active count a death
		//if it is not don't do anything
		if(mc.currentScreen instanceof GuiGameOver){
            if(!deathScreenActive){
                AresData.addDeaths(1);
                AresData.killstreak=0;
                deathScreenActive=true;
            }
            //get the title screen button
            GuiButton titleScreen = (GuiButton)mc.currentScreen.buttonList.get(1);
            //if the button is enabled and the user wants to disable it
            if(titleScreen.enabled && CONFIG.toggleTitleScreenButton){
                titleScreen.enabled = false;
                mc.currentScreen.buttonList.set(1, titleScreen);
                mc.currentScreen.updateScreen();
            }
        } else{
            deathScreenActive=false;
        }

		//get debug info for the fps
		String fps = mc.debug.split(",")[0];
		int height = CONFIG.x;
		int width = CONFIG.y;
		//if the gui is enabled display
		//if chat is open and config says yes then show gui
		if (AresData.guiShowing && (mc.inGameHasFocus || CONFIG.showGuiChat && mc.currentScreen instanceof GuiChat)) {
			//show fps
			if (CONFIG.showFPS) {
				mc.fontRenderer.drawStringWithShadow(fps, width, height,0xffff);
				height += 8;
			}
		}
		//if on Ares server then display this info.
		//if chat is open and config says yes then show gui
		if (AresData.isPlayingAres()&& AresData.guiShowing && (mc.inGameHasFocus || CONFIG.showGuiChat && mc.currentScreen instanceof GuiChat) ) {
			// Server display
			if (CONFIG.showServer) {
				mc.fontRenderer.drawStringWithShadow("Server: \u00A76"+AresData.server, width, height,16777215);
				height += 8;
			}

			// Team display (based on color)
			if (CONFIG.showTeam) {
			    mc.fontRenderer.drawStringWithShadow("Team: " + AresData.team, width, height, getTeamColors());
			    height += 8;
			}
			// Friend display:
			if (CONFIG.showFriends) {
				mc.fontRenderer.drawStringWithShadow("Friends Online: \u00A73"+AresData.getFriends(), width, height,16777215);
				height += 8;
			}
			// Map fetcher:
			if (CONFIG.showMap) {
				if (AresData.map != null) {
					mc.fontRenderer.drawStringWithShadow("Current Map: \u00A7d"+AresData.map, width, height,16777215);
					height += 8;
				} else {
					AresData.map="Fetching...";
					mc.fontRenderer.drawStringWithShadow("Current Map: \u00A78"+AresData.map, width, height,16777215);
					height += 8;
				}
			}
			//Show KD Ratio
			if (CONFIG.showKD) {
				mc.fontRenderer.drawStringWithShadow("K/D: \u00A73"+AresCustomMethods.getKD(), width, height,16777215);
				height += 8;
			}
			//show KK Ratio
			if (CONFIG.showKK) {
				mc.fontRenderer.drawStringWithShadow("K/K: \u00A73"+AresCustomMethods.getKK(), width, height,16777215);
				height += 8;
			}
			//show amount of kills
			if (CONFIG.showKills) {
				mc.fontRenderer.drawStringWithShadow("Kills: \u00A7a"+AresData.kills, width, height, 16777215);
				height += 8;
			}
			//show amount of deaths
			if (CONFIG.showDeaths) {
				mc.fontRenderer.drawStringWithShadow("Deaths: \u00A74"+AresData.deaths, width, height,16777215);
				height += 8;
			}
			// Kill Streak display
			if (CONFIG.showStreak) {
				mc.fontRenderer.drawStringWithShadow("Current Killstreak: \u00A75"+AresData.killstreak+"/"+AresData.largestKillstreak, width, height, 16777215);
				height += 8;
			}
		}
		return true;
	}

	/**
	 * Called on client connect to the server
	 * Sets variables if the server is a Ares server
	 */
	public void clientConnect(NetClientHandler var1) {
		AresData.team=AresData.Teams.Observers;
		System.out.println("Client successfully connected to "+ var1.getNetManager().getSocketAddress().toString());
		
		//if logging onto a project ares server, then enable the main mod
		if (var1.getNetManager().getSocketAddress().toString().contains(CONFIG.serverDomain)) {
			// What happens if logs into project ares
			AresData.guiShowing=true;
			System.out.println("Connected to: "+ var1.getNetManager().getSocketAddress().toString()+ " Ares mod activated!");
			AresData.team=AresData.Teams.Observers;
			AresData.isPA=true;
			AresData.server=AresCustomMethods.getServer(var1.getNetManager().getSocketAddress().toString());
			AresCustomMethods.getMap();
		} else {
			AresData.guiShowing=false;
		}
	}
	
	/**
	 * Called when client disconnects.
	 * Resets all the values
	 */
	public void onDisconnect(NetClientHandler handler) {
		AresData.isPA = false;
		AresData.guiShowing=false;
		AresData.team=AresData.Teams.Observers;
		AresData.kills=0;
		AresData.killed=0;
		AresData.deaths=0;
		AresData.killstreak=0;
		AresData.largestKillstreak=0;
		AresData.map="Attempting to fetch map...";
	}

	/**
	 * Called when a key is pressed.
	 * Used to activate the gui ect.
	 */
	public void keyboardEvent(KeyBinding keybinding) {
		
		if (mc.inGameHasFocus) {
			if (keybinding == AresData.keybind) {
                AresData.guiShowing = !AresData.guiShowing;
			} else if (keybinding == AresData.keybind2) {
				ModLoader.openGUI(mc.thePlayer, new Ares_ServerGUI(true));
			}
		}
	}
	
	/**
	 * Returns the team color hex based on the team you are on
	 * @return hex value of team color
	 */
	public int getTeamColors() {
        if (AresData.team == AresData.Teams.Red) {
            return 0x990000;
        } else if (AresData.team == AresData.Teams.Blue) {
            return 0x0033FF;
        } else if (AresData.team == AresData.Teams.Purple) {
            return 0x9933CC;
        } else if (AresData.team == AresData.Teams.Cyan) {
            return 0x00FFFF;
        } else if (AresData.team == AresData.Teams.Lime) {
            return 0x00FF00;
        } else if (AresData.team == AresData.Teams.Yellow) {
            return 0xFFFF00;
        } else if (AresData.team == AresData.Teams.Green) {
            return 0x006600;
        } else if (AresData.team == AresData.Teams.Orange) {
            return 0xFF9900;
        } else if (AresData.team == AresData.Teams.Observers) {
            return 0x00FFFF;
        } else {
            return 0x606060;
        }    
    }
}
