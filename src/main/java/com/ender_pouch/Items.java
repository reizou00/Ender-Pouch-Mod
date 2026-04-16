package com.ender_pouch;

import com.ender_pouch.item.EnderPouchItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.function.Function;


public class Items {
    // アイテムを登録するメソッド
    public static <T extends Item> T registerItem(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        // アイテムのキーを作成
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("reizo", name));

        // アイテムのインスタンスを作成
        T item = itemFactory.apply(settings.setId(itemKey));

        // アイテムを登録する。
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

            return item;
        }

        // アイテムのデータを設定して登録する
        public static final Item ENDER_POUCH = registerItem(
                "ender_pouch",
                EnderPouchItem::new,
                new Item.Properties().stacksTo(1).durability(30)
                );

    // なぜだか分からんがクラスを静的に初期化しなきゃならんラシイ。
    public static void initialize() {

        // アイテムをグループに追加する
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
                .register((itemGroup) -> itemGroup.accept(Items.ENDER_POUCH));
    }
}
