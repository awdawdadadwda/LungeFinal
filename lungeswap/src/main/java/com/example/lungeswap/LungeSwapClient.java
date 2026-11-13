package com.example.lungeswap;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class LungeSwapClient implements ClientModInitializer {

    // Hotbar-Slots 1-9
    private static final int SPEAR_SLOT = 9;
    private static final int MACE_SLOT = 3;

    // Verzoegerungen in Ticks (20 Ticks = 1 Sekunde)
    private static final int ATTACK_DELAY = 1;
    private static final int SWAP_BACK_DELAY = 2;

    // Seit MC 1.21.9 ist die Kategorie ein Objekt (kein String mehr)
    private static final KeyBinding.Category CATEGORY =
            KeyBinding.Category.create(Identifier.of("lungeswap", "main"));

    private static KeyBinding macroKey;
    private int stage = 0;
    private int timer = 0;

    @Override
    public void onInitializeClient() {
        macroKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.lungeswap.run",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(this::onTick);
    }

    private void onTick(MinecraftClient client) {
        if (client.player == null || client.currentScreen != null) {
            stage = 0;
            return;
        }

        if (stage == 0) {
            if (macroKey.wasPressed()) {
                // restliche gestaute Tastendruecke verwerfen
                while (macroKey.wasPressed()) { }
                client.player.getInventory().setSelectedSlot(SPEAR_SLOT - 1);
                stage = 1;
                timer = ATTACK_DELAY;
            }
            return;
        }

        // Waehrend das Makro laeuft: weitere Tastendruecke ignorieren
        while (macroKey.wasPressed()) { }

        if (--timer > 0) return;

        if (stage == 1) {
            InputUtil.Key attackKey = InputUtil.fromTranslationKey(
                    client.options.attackKey.getBoundKeyTranslationKey());
            KeyBinding.onKeyPressed(attackKey);
            stage = 2;
            timer = SWAP_BACK_DELAY;
        } else if (stage == 2) {
            client.player.getInventory().setSelectedSlot(MACE_SLOT - 1);
            stage = 0;
        }
    }
}
