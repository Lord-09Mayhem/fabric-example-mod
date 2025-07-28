package com.lord_09mayhem.cursedlife.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.text.Text;

public class ClockOfAcceleration extends Item {
    private static final long COOLDOWN = 5 * 60 * 20; // 5 minutes in ticks
    private static final int DURATION = 30 * 20; // 30 seconds in ticks

    public ClockOfAcceleration(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            if (user.getItemCooldownManager().isCoolingDown(this)) {
                user.sendMessage(Text.literal("This clock is cooling down!"), false);
                return ActionResult.FAIL;
            }

            user.getServer().getCommandManager().execute(user.getCommandSource(), "tick entity @e 10");

            world.getServer().getScheduler().schedule(() -> {
                try {
                    user.getServer().getCommandManager().execute(user.getCommandSource(), "tick entity @e 20");
                } catch (Exception ignored) {}
            }, DURATION);

            user.sendMessage(Text.literal("Time sped up for 30 seconds!"), false);
            user.getItemCooldownManager().set(this, (int) COOLDOWN);
        }

        return ActionResult.SUCCESS;
    }
}
