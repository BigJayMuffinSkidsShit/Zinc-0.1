package com.zinc.hack.api.event.events;

import com.zinc.hack.api.event.Event;

/**
 * @author fuckyouthinkimboogieman
 */

public class EventKey extends Event {

    private final int key;

    public EventKey(int key) { this.key = key; }

    public int getKey() { return key; }
}
