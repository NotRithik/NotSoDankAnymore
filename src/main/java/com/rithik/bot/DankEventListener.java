package com.rithik.bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DankEventListener extends ListenerAdapter {

    static final String dankMemerUserID = "270904126974590976";
    boolean expectTimeLeft = true;
    static final Pattern boldSecondsLeft =
            Pattern.compile("(\\*\\*(\\d{1,2}|\\d\\.\\d) seconds?\\*\\*)", Pattern.MULTILINE);

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.println("DankListener added!");
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getMessage().getAuthor().getId().equals(dankMemerUserID)) {
            Message receivedMessage = event.getMessage();

            //Are we expecting an embed from Dank Memer telling us the cooldown before running a command again?
            if (expectTimeLeft) {
                float timeLeft = getTimeLeft(receivedMessage);
            }
        }
    }

    private float getTimeLeft(Message receivedMessage) {
        float timeRemaining = -1;

        //Check for embeds
        if (receivedMessage.getEmbeds().size() > 0) {
            MessageEmbed embed = receivedMessage.getEmbeds().get(0);

            Matcher matcher = boldSecondsLeft.matcher(embed.getDescription());
            if (matcher.find()) {
                timeRemaining = Float.parseFloat(matcher.group(2));
            }
        }

        return timeRemaining;
    }

}

