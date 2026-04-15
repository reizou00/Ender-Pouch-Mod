package com.ender_pouch;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnderPouch implements ModInitializer {

	// ModIDをセット！
	public static final String MOD_ID = "ender_pouch";

	@Override
	public void onInitialize() {
			Items.initialize();
	}
}