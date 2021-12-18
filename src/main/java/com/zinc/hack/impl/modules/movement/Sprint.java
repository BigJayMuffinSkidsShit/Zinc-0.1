package com.zinc.hack.impl.modules.movement;

import com.zinc.hack.api.module.Module;

/**
 * @author fuckyouthinkimboogieman
 */

public class Sprint extends Module {

    public Sprint() {
        super("Sprint", "You can't press control? Whats next...", Category.Movement);
    }

    public void onUpdate() { mc.player.setSprinting(true); }
}
