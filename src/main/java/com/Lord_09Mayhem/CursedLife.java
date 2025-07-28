package com.lord_09mayhem.cursedlife;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CursedLife implements ModInitializer {
    public static final String MOD_ID = "cursedlife";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
public void onInitialize() {
    LOGGER.info("Cursed Life mod initialized!");
    CommandInit.register(); // Register commands
}