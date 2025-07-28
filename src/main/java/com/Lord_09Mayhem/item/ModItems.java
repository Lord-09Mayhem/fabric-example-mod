package com.lord_09mayhem.cursedlife;

import com.lord_09mayhem.cursedlife.item.ClockOfAcceleration;
import com.lord_09mayhem.cursedlife.item.ClockOfDeceleration;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item CLOCK_ACCEL = new ClockOfAcceleration(new Item.Settings().maxCount(1));
    public static final Item CLOCK_SLOW = new ClockOfDeceleration(new Item.Settings().maxCount(1));

    public static void register() {
        Registry.register(Registries.ITEM, new Identifier("cursedlife", "clock_accel"), CLOCK_ACCEL);
        Registry.register(Registries.ITEM, new Identifier("cursedlife", "clock_slow"), CLOCK_SLOW);
    }
}
