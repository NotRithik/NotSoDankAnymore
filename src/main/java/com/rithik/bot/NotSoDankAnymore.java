package com.rithik.bot;

import com.rithik.browserdriver.BrowserDriver;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class NotSoDankAnymore {
    public static JDA jda;

    static String clientKey = "Retracted";

    static String email = "email", password = "pass";
    static String channelUrl = "ChannelURL";

    public static void main(String[] args) throws LoginException {
        jda = JDABuilder.createDefault(clientKey).build();
        jda.getPresence().setActivity(Activity.playing("the memer"));
        jda.addEventListener(new DankEventListener());

        BrowserDriver discordDriver = new BrowserDriver();

        discordDriver.login(5, email, password);
        discordDriver.selectServerChannel(channelUrl);

        discordDriver.startBegging();
    }
}
