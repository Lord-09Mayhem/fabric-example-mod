package com.lord_09mayhem.cursedlife;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.server.network.ServerPlayerEntity;

public class CommandInit {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("timekeeper")
                .requires(source -> source.hasPermissionLevel(2)) // OP required
                .then(CommandManager.literal("set")
                    .then(CommandManager.argument("player", StringArgumentType.word())
                        .executes(ctx -> {
                            String targetName = StringArgumentType.getString(ctx, "player");
                            ServerPlayerEntity target = ctx.getSource().getServer().getPlayerManager().getPlayer(targetName);

                            if (target == null) {
                                ctx.getSource().sendFeedback(Text.literal("Player not found!"), false);
                                return 0;
                            }

                            ItemStack speedClock = new ItemStack(Items.CLOCK);
                            speedClock.setCustomName(Text.literal("Clock of Acceleration"));

                            ItemStack slowClock = new ItemStack(Items.CLOCK);
                            slowClock.setCustomName(Text.literal("Clock of Deceleration"));

                            target.getInventory().insertStack(speedClock);
                            target.getInventory().insertStack(slowClock);

                            ctx.getSource().sendFeedback(Text.literal("Gave Time Keeper clocks to " + target.getName().getString()), false);
                            return 1;
                        })))
                .then(CommandManager.literal("remove")
                    .then(CommandManager.argument("player", StringArgumentType.word())
                        .executes(ctx -> {
                            String targetName = StringArgumentType.getString(ctx, "player");
                            ServerPlayerEntity target = ctx.getSource().getServer().getPlayerManager().getPlayer(targetName);

                            if (target == null) {
                                ctx.getSource().sendFeedback(Text.literal("Player not found!"), false);
                                return 0;
                            }

                            target.getInventory().remove(item -> 
                                item.getName().getString().contains("Clock of Acceleration") || 
                                item.getName().getString().contains("Clock of Deceleration"), 64, null);

                            // Reset tick rate if needed
                            ctx.getSource().getServer().getCommandManager().execute(ctx.getSource(), "tick entity @e 20");

                            ctx.getSource().sendFeedback(Text.literal("Removed clocks from " + target.getName().getString()), false);
                            return 1;
                        }))));
        });
    }
}
