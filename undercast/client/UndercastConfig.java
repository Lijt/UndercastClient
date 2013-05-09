package undercast.client;

import net.minecraft.src.ModLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;


public class UndercastConfig {
    private static Properties defaults = new Properties();
    private static String CONFIG_PATH;
    private Properties config;
    private static final String FILE_NAME = "UndercastMod.cfg";
    
    // update this value to change the config version.
    private static int version = 5;

    // main variables
    public static boolean showFPS;
    public static boolean showKills;
    public static boolean showDeaths;
    public static boolean showKilled;
    public static boolean showServer;
    public static boolean showTeam;
    public static boolean showKD;
    public static boolean showKK;
    public static boolean showFriends;
    public static boolean showMap;
    public static boolean showNextMap;
    public static boolean showStreak;
    public static boolean showGuiChat;
    public static boolean showGuiMulti;
    public static boolean showPlayingTime;
    public static int x;
    public static int y;
    public static boolean toggleTitleScreenButton;
    public static boolean filterTips;
    public static boolean fullBright;
    public static boolean matchOnServerJoin;
    public static boolean enableButtonTooltips;
    public static boolean showAchievements;
    public static boolean showKillAchievements;
    public static boolean showDeathAchievements;
    public static int configVersion;

    /**
     * Default values created when class is first referenced
     */
    static {
        try {
        CONFIG_PATH = ModLoader.getMinecraftInstance().getMinecraftDir().getCanonicalPath() + File.separatorChar + "config" + File.separatorChar + "UndercastClient" + File.separatorChar;
        } catch(Exception e) {
            System.out.println("[UndercastMod]: Failed to get config path.");
        }
        defaults.setProperty("showFPS", "true");
        defaults.setProperty("showKills", "true");
        defaults.setProperty("showDeaths", "true");
        defaults.setProperty("showKilled", "true");
        defaults.setProperty("showServer", "true");
        defaults.setProperty("showTeam", "true");
        defaults.setProperty("showKD", "true");
        defaults.setProperty("showKK", "true");
        defaults.setProperty("showFriends", "false");
        defaults.setProperty("showMap", "true");
        defaults.setProperty("showNextMap", "true");
        defaults.setProperty("showStreak", "true");
        defaults.setProperty("showGuiChat", "true");
        defaults.setProperty("showGuiMulti", "true");
        defaults.setProperty("showPlayingTime", "false");
        defaults.setProperty("X", "2");
        defaults.setProperty("Y", "2");
        defaults.setProperty("toggleTitleScreenButton", "true");
        defaults.setProperty("filterTips", "true");
        defaults.setProperty("fullBright", "true");
        defaults.setProperty("matchOnServerJoin", "false");
        defaults.setProperty("enableButtonTooltips", "true");
        defaults.setProperty("showAchievements", "false");
        defaults.setProperty("showKillAchievements", "true");
        defaults.setProperty("showDeathAchievements", "true");
        // if the value is missing, it should force an update. Don't change it.
        defaults.setProperty("configVersion", "0");
    }

    public UndercastConfig() {
        System.out.println("[UndercastMod]: Attempting to load/create the configuration.");
        loadConfig();
        loadConfigData();
    }

    /**
     * Attempts to find a config
     * If there is one load it
     * If there is not one create one
     */
    private void loadConfig() {
        config = new Properties(defaults);

        try {
            File cfg = new File(CONFIG_PATH + FILE_NAME);

            if(cfg.exists()) {
                System.out.println("[UndercastMod]: Config file found, loading...");
                config.load(new FileInputStream(CONFIG_PATH + FILE_NAME));
            } else {
                System.out.println("[UndercastMod]: No config file found, creating...");
                createConfig(cfg);
            }
        } catch (Exception e) {
            displayErrorMessage(e.toString());
        }
    }

    /**
     * Creates a config properties of default values
     * Then saves the config to the config location
     *
     * @param cfg config file
     */
    private void createConfig(File cfg) {
        File folder = new File(CONFIG_PATH);
        if(!folder.exists()) {
            System.out.println("[UndercastMod]: No folder found, creating...");
            folder.mkdir();
        }
        try {
            cfg.createNewFile();

            config.setProperty("showFPS", "true");
            config.setProperty("showKills", "true");
            config.setProperty("showDeaths", "true");
            config.setProperty("showKilled", "true");
            config.setProperty("showServer", "true");
            config.setProperty("showTeam", "true");
            config.setProperty("showKD", "true");
            config.setProperty("showKK", "true");
            config.setProperty("showFriends", "false");
            config.setProperty("showMap", "true");
            config.setProperty("showNextMap", "true");
            config.setProperty("showStreak", "true");
            config.setProperty("showGuiChat", "true");
            config.setProperty("showGuiMulti", "true");
            config.setProperty("showPlayingTime", "false");
            config.setProperty("X", "2");
            config.setProperty("Y", "2");
            config.setProperty("toggleTitleScreenButton", "true");
            config.setProperty("filterTips", "true");
            config.setProperty("fullBright", "true");
            config.setProperty("matchOnServerJoin", "false");
            config.setProperty("enableButtonTooltips", "true");
            config.setProperty("showAchievements", "false");
            config.setProperty("showKillAchievements", "true");
            config.setProperty("showDeathAchievements", "true");
            config.setProperty("configVersion", ""+version);

            config.store(new FileOutputStream(CONFIG_PATH + FILE_NAME),"This is the Unoffical Project Ares Mod Config" + "\nCustomize it to your taste" + "\nkeyGui = Ingame Stats" +"\nkeyGui2 = Ingame Server Menu" + "\nkeyGui3 = Full Bright\n");
        } catch (Exception e) {
            displayErrorMessage(e.toString());
        }
    }

    /**
     * Loads the property data into the local data
     */
    public void loadConfigData() {
        System.out.println("[UndercastMod]: Loading Config to Local Data");
        showFPS = this.getBoolProperty("showFPS");
        showKills = this.getBoolProperty("showKills");
        showDeaths = this.getBoolProperty("showDeaths");
        showKilled = this.getBoolProperty("showKilled");
        showServer = this.getBoolProperty("showServer");
        showTeam = this.getBoolProperty("showTeam");
        showKD = this.getBoolProperty("showKD");
        showKK = this.getBoolProperty("showKK");
        showFriends = this.getBoolProperty("showFriends");
        showNextMap = this.getBoolProperty("showNextMap");
        showMap = this.getBoolProperty("showMap");
        showStreak = this.getBoolProperty("showStreak");
        showGuiChat = this.getBoolProperty("showGuiChat");
        showGuiMulti = this.getBoolProperty("showGuiMulti");
        showPlayingTime = this.getBoolProperty("showPlayingTime");
        x = this.getIntProperty("X");
        y = this.getIntProperty("Y");
        toggleTitleScreenButton = this.getBoolProperty("toggleTitleScreenButton");
        filterTips = this.getBoolProperty("filterTips");
        fullBright = this.getBoolProperty("fullBright");
        matchOnServerJoin = this.getBoolProperty("matchOnServerJoin");
        enableButtonTooltips = this.getBoolProperty("enableButtonTooltips");
        showAchievements = this.getBoolProperty("showAchievements");
        showKillAchievements = this.getBoolProperty("showKillAchievements");
        showDeathAchievements = this.getBoolProperty("showDeathAchievements");
        configVersion = this.getIntProperty("configVersion");
        
        checkForConfigUpdate();
    }

    public void setProperty(String prop, String value) {
        config.setProperty(prop, value);
        saveConfig();
    }

    public void setProperty(String prop, float value) {
        String s = String.valueOf(value);
        config.setProperty(prop, s);
        saveConfig();
    }

    public void setProperty(String prop, int value) {
        String s = String.valueOf(value);
        config.setProperty(prop, s);
        saveConfig();
    }

    public void setProperty(String prop, boolean value) {
        String s = String.valueOf(value);
        config.setProperty(prop, s);
        saveConfig();
    }

    public String getStringProperty(String prop) {
        return config.getProperty(prop);
    }

    public float getFloatProperty(String prop) {
        String s = config.getProperty(prop);
        return Float.parseFloat(s);
    }

    public int getIntProperty(String prop) {
        String s = config.getProperty(prop);
        return Integer.parseInt(s);
    }

    public boolean getBoolProperty(String prop) {
        String s = config.getProperty(prop);
        return Boolean.parseBoolean(s);
    }

    public static String getDefaultPropertyValue(String prop) {
        return defaults.getProperty(prop);
    }

    public static float getDefaultFloatProperty(String prop) {
        String s = defaults.getProperty(prop);
        return Float.parseFloat(s);
    }

    public static int getDefaultIntProperty(String prop) {
        String s = defaults.getProperty(prop);
        return Integer.parseInt(s);
    }

    public static boolean getDefaultBoolProperty(String prop) {
        String s = defaults.getProperty(prop);
        return Boolean.parseBoolean(s);
    }

    public void saveConfig() {
        try {
            config.store(new FileOutputStream(CONFIG_PATH + FILE_NAME), null);
            config.load(new FileInputStream(CONFIG_PATH + FILE_NAME));
        } catch (Exception e) {
            displayErrorMessage(e.toString());
        }
    }

    private void displayErrorMessage(String error) {
        System.out.println("[UndercastMod]: ERROR: " + error);
    }
    
    /***
     * Checks if the config version has changed and adds the options which are new.
     */
    private void checkForConfigUpdate(){
        if(version != configVersion){
            System.out.println("[UndercastMod]: Updating the config...");
            switch(configVersion){
            case 0:
                // add you additional options.
                if(fullBright == true){ // do not overwrite the setting, if it isn't the default value
                    config.setProperty("fullBright", "true");
                }
                if(matchOnServerJoin == false){ // do not overwrite the setting, if it isn't the default value
                    config.setProperty("matchOnServerJoin", "false");
                }
            case 1:
                if(showNextMap == true){
                    config.setProperty("showNextMap", "true");
                }
            case 2:
                if(enableButtonTooltips == true) {
                    config.setProperty("enableButtonTooltips", "true");
                }
            case 3:
                if(showPlayingTime == false) {
                    config.setProperty("showPlayingTime", "false");
                }
            case 4:
                if(showAchievements == false) {
                    config.setProperty("showAchievments", "false");
                }
                if(showKillAchievements == true) {
                    config.setProperty("showKillAchievements", "true");
                }
                if(showDeathAchievements == true) {
                    config.setProperty("showDeathAchievements", "true");
                }
            case 5:
                // for the next version.
            }
            config.setProperty("configVersion", ""+version);
            saveConfig();	
        }
    }
}
