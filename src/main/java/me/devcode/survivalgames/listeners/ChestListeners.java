package me.devcode.survivalgames.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import lombok.Getter;
import me.devcode.survivalgames.SurvivalGames;
import me.devcode.survivalgames.utils.Status;
@Getter
public class ChestListeners implements Listener{

    private HashMap<Location, Inventory> sgChests = new HashMap<>();
    private ArrayList<Location> placed = new ArrayList<>();
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(SurvivalGames.plugin.status == Status.END || SurvivalGames.plugin.status == Status.LOBBY) {
            e.setCancelled(true);
            return;
        }
        if(SurvivalGames.plugin.status == Status.INGAME || SurvivalGames.plugin.status == Status.DEATHMATCH || SurvivalGames.plugin.status == Status.NODAMAGE) {
            if(e.isCancelled()) {
                e.setCancelled(false);
            }

            if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if(e.getClickedBlock() != null) {
                    if(e.getClickedBlock().getType() == Material.CHEST) {
                        if(placed.contains(e.getClickedBlock().getLocation())) {
                            return;
                        }

                        e.setCancelled(true);
                        if(sgChests.containsKey(e.getClickedBlock().getLocation())) {
                            player.openInventory(sgChests.get(e.getClickedBlock().getLocation()));
                            return;
                        }
                        Random ran = new Random();
                        int i = 19;
                        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST);
                        List<ItemStack> items = new ArrayList<>();
                       SurvivalGames.plugin.cfg.getStringList("items").forEach(all -> {
                            int ID = 0;
                            int subID = 0;
                            int amount = 0;
                            int chance = 0;
                            int enchantID = 0;
                            int enchantAmount = 0;
                            if(all.contains(":")) {
                                String[] array = all.split(":");
                                ID = Integer.valueOf(array[0]);
                                String a = array[1];
                                a = a.substring(0, 1);
                                subID = Integer.valueOf(a);
                            }
                            String[] array = all.split(", ");
                            amount = Integer.valueOf(array[1]);
                            chance = Integer.valueOf(array[2]);
                            enchantID = Integer.valueOf(array[3]);
                            enchantAmount = Integer.valueOf(array[4]);
                            ItemStack stack = new ItemStack(ID, amount, (short) subID);
                            stack.addUnsafeEnchantment(Enchantment.getById(enchantID), enchantAmount);


                            for(int i2 =0; i2 < chance; i2++) {
                                items.add(stack);
                            }
                        });
                        while(i != 0) {
                            i--;
                            int chestrandom = ran.nextInt(27);
                            int sizeitems = ran.nextInt(items.size());
                            inv.setItem(chestrandom, items.get(sizeitems));

                        }
                        sgChests.put(e.getClickedBlock().getLocation(), inv);
                        e.getPlayer().openInventory(sgChests.get(e.getClickedBlock().getLocation()));
                    }
                }
            }
        }
    }
}
