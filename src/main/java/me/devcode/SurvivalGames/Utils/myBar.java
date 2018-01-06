package me.devcode.SurvivalGames.Utils;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
 
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;
 
 
 
public class myBar {
   
   
   
   
    public static void sendTitel(Player p, String message) {
        if(message == null) message ="";
        message = ChatColor.translateAlternateColorCodes('&', message);
        message = message.replaceAll("PLAYER", p.getName());
        PlayerConnection con = ((CraftPlayer)p).getHandle().playerConnection;
        IChatBaseComponent chat = ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.TITLE, chat);
        con.sendPacket(packet);
       
    }
   
    public static void subTitel(Player p, String message) {
       
        if(message == null) message ="";
        message = ChatColor.translateAlternateColorCodes('&', message);
        message = message.replaceAll("PLAYER", p.getName());
        PlayerConnection con = ((CraftPlayer)p).getHandle().playerConnection;
        IChatBaseComponent chat = ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chat);
        con.sendPacket(packet);
    }
   
public static void endTitel(Player p, String message) {
       
       
        PlayerConnection con = ((CraftPlayer)p).getHandle().playerConnection;
        IChatBaseComponent chat = ChatSerializer.a("{\"text\": \"" + message + "\"}");
        PacketPlayOutChat packet = new PacketPlayOutChat(chat, (byte) 2);
        con.sendPacket(packet);
    }
 
 
}
