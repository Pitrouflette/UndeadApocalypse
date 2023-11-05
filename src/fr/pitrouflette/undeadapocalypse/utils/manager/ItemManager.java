package fr.pitrouflette.undeadapocalypse.utils.manager;

import fr.pitrouflette.undeadapocalypse.Main;
import fr.pitrouflette.undeadapocalypse.utils.ItemBuilder;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class ItemManager {

    public ItemStack getItem(String string){

        ItemStack itemStackGlowing = new ItemBuilder(Main.getPlugin(Main.class).getMaterial(string))
                .setDisplayName(Main.getPlugin(Main.class).getName(string))
                .setLore((ArrayList<String>) Main.getPlugin(Main.class).getLore(string))
                .setModel(Main.getPlugin(Main.class).getCustomModel(string))
                .addEnchant(Enchantment.CHANNELING, 1)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        ItemStack itemStack = new ItemBuilder(Main.getPlugin(Main.class).getMaterial(string))
                .setDisplayName(Main.getPlugin(Main.class).getName(string))
                .setLore((ArrayList<String>) Main.getPlugin(Main.class).getLore(string))
                .setModel(Main.getPlugin(Main.class).getCustomModel(string))
                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        if(Main.getPlugin(Main.class).getItem().getString("items.modified-item-list").contains(string)){
            if(string.equals("sword")){
                if(Main.getPlugin(Main.class).isGlowing(string)){
                    return modifyDiamondSword(itemStackGlowing, Main.getPlugin(Main.class).getItem().getInt("items." + string + ".modifier"));
                }else{
                    return modifyDiamondSword(itemStack, Main.getPlugin(Main.class).getItem().getInt("items." + string + ".modifier"));
                }
            }else{
                if(Main.getPlugin(Main.class).isGlowing(string)){
                    return modifyArmor(itemStackGlowing, Main.getPlugin(Main.class).getItem().getInt("items." + string + ".modifier"));
                }else{
                    return modifyArmor(itemStack, Main.getPlugin(Main.class).getItem().getInt("items." + string + ".modifier"));
                }
            }
        }

        if(Main.getPlugin(Main.class).isGlowing(string)){
            return itemStackGlowing;
        }else{
            return itemStack;
        }
    }

    public static ItemStack modifyArmor(ItemStack armorPiece, Integer amount) {
        UUID MODIFIER_UUID = UUID.randomUUID();
        ItemMeta meta = armorPiece.getItemMeta();
        assert meta != null;
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(MODIFIER_UUID, "Custom armor modifier", amount, AttributeModifier.Operation.ADD_NUMBER));
        armorPiece.setItemMeta(meta);
        return armorPiece;
    }

    public static ItemStack modifyDiamondSword(ItemStack diamondSword, Integer amout) {
        UUID MODIFIER_UUID = UUID.randomUUID();
        UUID MODIFIER_UUID2 = UUID.randomUUID();
        ItemMeta meta = diamondSword.getItemMeta();
        assert meta != null;
        meta.addAttributeModifier(org.bukkit.attribute.Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(MODIFIER_UUID, "Custom damage modifier", amout, AttributeModifier.Operation.ADD_NUMBER));
        meta.addAttributeModifier(org.bukkit.attribute.Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(MODIFIER_UUID2, "Custom damage modifier2", amout, AttributeModifier.Operation.ADD_NUMBER));
        diamondSword.setItemMeta(meta);
        return diamondSword;
    }
}