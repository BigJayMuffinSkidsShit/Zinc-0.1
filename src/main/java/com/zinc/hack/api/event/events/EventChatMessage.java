package com.zinc.hack.api.event.events;

import com.zinc.hack.api.event.Event;

/**
 * @author fuckyouthinkimboogieman
 */

public class EventChatMessage extends Event {
    private final String message;

    public EventChatMessage(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
}
