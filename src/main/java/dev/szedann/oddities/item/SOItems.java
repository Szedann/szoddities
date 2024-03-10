package dev.szedann.oddities.item;

import dev.szedann.oddities.Szoddities;
import dev.szedann.oddities.item.custom_items.SignTemplateItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class SOItems {
    public static final Item SIGN_TEMPLATE = registerItem("sign_template", new SignTemplateItem(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Szoddities.MOD_ID, name), item);
    }

    private static void addItemsToFunctionalItemGroup(FabricItemGroupEntries entries){
        entries.add(SIGN_TEMPLATE);
    }
    public static void registerModItems(){
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(SOItems::addItemsToFunctionalItemGroup);
    }
}
