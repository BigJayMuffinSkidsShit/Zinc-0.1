package com.zinc.hack.api.event;

/**
 * @author fuckyouthinkimboogieman
 */

public class Event {
    private boolean cancelled = false;

    public boolean isCancelled() { return cancelled; }

    public void setCancelled(boolean cancelled) { this.cancelled = cancelled; }

    public void cancel() { this.cancelled = true; }
}
