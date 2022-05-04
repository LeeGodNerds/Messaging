package me.crypnotic.messagechannel.minestom;

import me.crypnotic.messagechannel.api.MessageChannelAPI;
import me.crypnotic.messagechannel.api.access.IMessageChannel;
import me.crypnotic.messagechannel.api.access.IRelay;
import me.crypnotic.messagechannel.api.exception.MessageChannelException;
import me.crypnotic.messagechannel.api.pipeline.PipelineMessage;
import me.crypnotic.messagechannel.core.MessageChannelCore;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerPluginMessageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class MessageChannelMinestom implements IRelay {

    private IMessageChannel core;

    @Override
    public boolean send(PipelineMessage message, byte[] data) {
        final Collection<@NotNull Player> onlinePlayers = MinecraftServer.getConnectionManager().getOnlinePlayers();
        if (!onlinePlayers.isEmpty()) {
            onlinePlayers
                    .stream()
                    .findFirst()
                    .ifPresent(player -> player.sendPluginMessage("messagechannel:proxy", data));
        }
        return false;
    }

    @Override
    public boolean broadcast(PipelineMessage message, byte[] data) {
        return false;
    }

    @Override
    public boolean isProxy() {
        return false;
    }

    public void initialize() {
        this.core = new MessageChannelCore(this);

        try {
            MessageChannelAPI.setCore(core);
        } catch (MessageChannelException exception) {
            exception.printStackTrace();
        }

        MinecraftServer.getGlobalEventHandler().addListener(PlayerPluginMessageEvent.class, event -> {
            if (event.getIdentifier().equals("messagechannel:server")) {
                core.getPipelineRegistry().receive(event.getMessage());
            }
        });
    }

}
