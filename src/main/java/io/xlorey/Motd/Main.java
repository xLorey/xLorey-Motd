package io.xlorey.Motd;

import io.xlorey.FluxLoader.annotations.SubscribeEvent;
import io.xlorey.FluxLoader.plugin.Plugin;
import io.xlorey.FluxLoader.server.api.ServerUtils;
import zombie.core.raknet.UdpConnection;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Main entry point of the plugin
 */
public class Main extends Plugin {
    /**
     * Initializing the plugin
     */
    @Override
    public void onInitialize() {
        saveDefaultConfig();
    }

    /**
     * Handling the player's full connection event
     * @param playerData player data
     * @param playerConnection player connection data
     * @param username player nickname
     */
    @SubscribeEvent(eventName="onPlayerFullyConnected")
    public void onPlayerFullyConnectedHandler(ByteBuffer playerData, UdpConnection playerConnection, String username){
        List<Object> motdLines = getConfig().getList("motdLines");

        for (Object line : motdLines) {
            String lineText = (String) line;

            lineText = lineText.replace("<USERNAME>", playerConnection.username);
            lineText = lineText.replace("<IP>", playerConnection.ip);
            lineText = lineText.replace("<STEAMID>", String.valueOf(playerConnection.steamID));
            lineText = lineText.replace("<SPACE_SYMBOL>", "\u200B");

            ServerUtils.sendServerChatMessage(playerConnection, lineText);
        }
    }
}