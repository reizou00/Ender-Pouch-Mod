package com.ender_pouch.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EnderPouchItem extends Item {
    // 何故かこれいるらしい。俺にゃよくわかんないね。
    public EnderPouchItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult use(Level world, Player user, InteractionHand hand) {

        user.swing(hand, true); // 使ったときのアニメーション

        if (world.isClientSide) {
            return InteractionResult.PASS;
        }

        ItemStack stack = user.getItemInHand(hand); // アイテムの実体を取得

            int damageValue = stack.getDamageValue(); // 現在の耐久値を取得
            int maxSafeDamage = stack.getMaxDamage() - 1; // 耐久値の最大値から-1した値を取得する。
            // これは最大値になるとアイテムが壊れるため、それから-1した数はまだアイテムが存在できる値であるため。

        // 耐久値を減少させる処理
        if (damageValue < maxSafeDamage) {
            stack.hurtAndBreak(1, user, null); // 耐久値を減らす
        }
        else {

            // 💥 音（ボフっぽい）
            world.playSound(null, user.blockPosition(),
                    SoundEvents.ENDER_CHEST_CLOSE,
                    SoundSource.PLAYERS,
                    0.5F, 0.6F
            );

            // 🌫 パーティクル（黒っぽい煙）
            ((ServerLevel) world).sendParticles(
                    ParticleTypes.SMOKE,
                    user.getX(),
                    user.getY() + 1,
                    user.getZ(),
                    20,
                    0.3, 0.3, 0.3,
                    0.05
            );
            return InteractionResult.FAIL;
        } // 限界値に達しそうになったら動作を終了


        // エンダーチェストを開く！
        user.openMenu(new SimpleMenuProvider(
                (id, inventory, player) ->
                        ChestMenu.threeRows(id, inventory, player.getEnderChestInventory()),
                Component.translatable("container.ender_pouch")
        ));

        // 音がないと開いたって実感湧かないでしょ？
        world.playSound(null, user.blockPosition(),
                SoundEvents.ENDER_CHEST_OPEN,
                SoundSource.PLAYERS,
                1.0F, 1.5F
        );

        return InteractionResult.SUCCESS;
    }
}