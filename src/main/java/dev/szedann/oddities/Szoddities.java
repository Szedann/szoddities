package dev.szedann.oddities;

import dev.szedann.oddities.item.SOItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.item.BowItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Szoddities implements ModInitializer {
	public static final String MOD_ID = "szoddities";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		SOItems.registerModItems();
	}
}