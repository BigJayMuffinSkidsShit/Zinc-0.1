package com.zinc.hack.api.friends;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fuckyouthinkimboogieman
 */

public final class FriendManager extends ArrayList<String> {

    private static final FriendManager INSTANCE = new FriendManager();

    public FriendManager register() {
        return this;
    }

    public static FriendManager getInstance() {
        return INSTANCE;
    }

    public boolean is(String name) {
        return stream().anyMatch(friend -> friend.equalsIgnoreCase(name));
    }

}
