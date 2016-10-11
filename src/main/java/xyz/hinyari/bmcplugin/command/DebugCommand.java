package xyz.hinyari.bmcplugin.command;

import xyz.hinyari.bmcplugin.BMCPlayer;
import xyz.hinyari.bmcplugin.Utils.BMCUtils;
import xyz.hinyari.bmcplugin.BMCPlugin;
import xyz.hinyari.bmcplugin.rank.Rank;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import xyz.hinyari.bmcplugin.Utils.BMCBoolean;
import xyz.hinyari.bmcplugin.Utils.BMCHelp;
import xyz.hinyari.bmcplugin.event.ScoutEvent;

public class DebugCommand extends SubCommandAbst {

    public static final String COMMAND_NAME = "debug";

    private final BMCPlugin plugin;
    private final BMCHelp bmcHelp;
    private final BMCBoolean bmcBoolean;
    private final BMCUtils utils;

    public DebugCommand(BMCPlugin plugin) {
        this.plugin = plugin;
        this.bmcBoolean = plugin.bmcBoolean;
        this.bmcHelp = plugin.bmcHelp;
        this.utils = plugin.utils;
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public boolean runCommand(BMCPlayer bmcPlayer, String label, String[] args) {
        ItemStack item = bmcPlayer.getItemInMainHand();
        Player player = bmcPlayer.getPlayer();
        if (!(bmcPlayer.hasPermission("plugin.debug"))) {
            return bmcPlayer.noperm();
        }

        if (args.length == 1) return bmcHelp.Debughelp(bmcPlayer);
        else if (args.length >= 2) {
            if (args[1].equalsIgnoreCase("itemhand")) {
                if (item.getType() != null && item.getTypeId() != 0) {
                    if (item.getItemMeta().getDisplayName() == null) {
                        if (!(item.getDurability() == 0)) {
                            bmcPlayer.msg(item.getType().toString() + ", " + item.getDurability());
                        } else {
                            bmcPlayer.msg(item.getType().toString() + ", " + "0");
                        }
                    } else {
                        String displayname = item.getItemMeta().getDisplayName();
                        if (!(item.getDurability() == 0)) {
                            bmcPlayer.msg(item.getType().toString() + ", " + item.getDurability() + ", " + displayname);
                        } else {
                            bmcPlayer.msg(item.getType().toString() + ", " + "0" + ", " + displayname);
                        }
                    }
                } else {
                    bmcPlayer.msg("手に何も持っていません。");
                }
            } else if (args[1].equalsIgnoreCase("rank")) {
                if (args[2].equalsIgnoreCase("reset")) {
                    bmcPlayer.msg("スコアをリセットしました。");
                    bmcPlayer.getScoreboard().setRank(Rank.RED);
                } else {
                    return bmcHelp.Debughelp(bmcPlayer);
                }
            } else if (args[1].equalsIgnoreCase("kome")) {
                if (args[2].equalsIgnoreCase("hunger")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 60, 100));
                } else if (args[2].equalsIgnoreCase("get")) {
                    player.getInventory().addItem(utils.getKoshihikari());
                    return true;
                }
            } else if (args[1].equalsIgnoreCase("namereset")) {
                player.setDisplayName(player.getName());
            } else if (args[1].equalsIgnoreCase("ench")) {
                if (args.length == 2) return DebugEnchCommandHelp(bmcPlayer);

                ItemMeta meta = item.getItemMeta();

                if (item.getType() != null && item.getTypeId() != 0) {
                    if (args[2].equalsIgnoreCase("fall")) {
                        if (item.getType() == Material.DIAMOND_BOOTS) {
                            Enchantment ench = Enchantment.PROTECTION_FALL;
                            if (item.containsEnchantment(ench)) {
                                bmcPlayer.msg("Already item has " + ench.getName() + " Enchant!");
                                return false;
                            }

                            item.addUnsafeEnchantment(ench, 10);
                            bmcPlayer.msg("正常にエンチャントメントが実行されました。");
                        } else {
                            bmcPlayer.msg("ダイヤモンドのブーツである必要があります。");
                        }
                    } else if (args[2].equalsIgnoreCase("fire")) {
                        if (item.getType() == Material.DIAMOND_CHESTPLATE || item.getType() == Material.IRON_CHESTPLATE) {
                            Enchantment ench = Enchantment.PROTECTION_FIRE;
                            if (item.containsEnchantment(ench)) {
                                bmcPlayer.msg("Already item has " + ench.getName() + " Enchant!");
                                return false;
                            }

                            item.addUnsafeEnchantment(ench, 10);
                            bmcPlayer.msg("正常にエンチャントメントが実行されました。");
                        } else {
                            bmcPlayer.msg("ダイヤ・鉄のチェストプレートである必要があります。");
                        }
                    } else if (args[2].equalsIgnoreCase("smelt")) {
                        if (meta.hasLore()) {
                            bmcPlayer.msg("このアイテムにエンチャントをつけることは出来ません。");
                            return false;
                        }

                        if (!(bmcBoolean.isApplyTool(item.getType()))) {
                            bmcPlayer.msg("このアイテムにエンチャントをつけることは出来ません。");
                            return false;
                        }

                        Enchantment ench = Enchantment.SILK_TOUCH;
                        if (item.containsEnchantment(ench)) {
                            bmcPlayer.msg("シルクタッチと一緒にすることは出来ません。");
                            return false;
                        }

                        if (plugin.autoSmelt.lore.isEmpty()) {
                            plugin.autoSmelt.lore.add(0, "§4Auto Smelt");
                            meta.setLore(plugin.autoSmelt.lore);
                            item.setItemMeta(meta);
                            bmcPlayer.msg("正常にエンチャントメントが実行されました。");
                        } else {
                            meta.setLore(plugin.autoSmelt.lore);
                            item.setItemMeta(meta);
                            bmcPlayer.msg("正常にエンチャントメントが実行されました。");
                        }
                    } else if (args[2].equalsIgnoreCase("unbreaking")) {
                        if (args.length == 3) {
                            bmcPlayer.errmsg("引数指定が間違っています。");
                        } else if (args.length == 4) {
                            item.getItemMeta().addEnchant(Enchantment.DURABILITY, Integer.valueOf(args[3]), true);
                            player.updateInventory();
                        } else {
                            bmcPlayer.errmsg("引数指定が間違っています。");
                            return true;
                        }
                    } else return DebugEnchCommandHelp(bmcPlayer);
                } else {
                    bmcPlayer.msg("エンチャントしたいアイテムを手に持つ必要があります。");
                }
            } else if (args[1].equalsIgnoreCase("grapple")) {
                ScoutEvent.im.setDisplayName(ScoutEvent.GRAPPLE_NAME);
                ScoutEvent.grappleItem.setItemMeta(ScoutEvent.im);
                player.getInventory().addItem(ScoutEvent.grappleItem);
            } else return bmcHelp.Debughelp(bmcPlayer);
        }
        return false;
    }

    private boolean DebugEnchCommandHelp(BMCPlayer bmcPlayer) {
        bmcPlayer.msg("Useful: " + "/plugin debug ench <fall/fire/smelt>");
        return false;
    }
}