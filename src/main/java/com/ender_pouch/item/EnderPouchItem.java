package com.ender_pouch.item;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class EnderPouchItem extends Item {
    // 何故かこれいるらしい。俺にゃよくわかんないね。
    public EnderPouchItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {

        if (world.isClientSide) {
            return InteractionResult.PASS;
        }

        user.openMenu(new SimpleMenuProvider(
                (id, inventory, player) ->
                        ChestMenu.threeRows(id, inventory, player.getEnderChestInventory()),
                Component.translatable("container.ender_pouch")
        ));

        world.playSound(null, user.blockPosition(),
                SoundEvents.ENDER_CHEST_OPEN,
                SoundSource.PLAYERS,
                1.0F, 1.5F
        );

        return InteractionResult.SUCCESS;
    }
}