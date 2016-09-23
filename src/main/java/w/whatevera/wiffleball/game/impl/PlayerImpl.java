package w.whatevera.wiffleball.game.impl;

import w.whatevera.wiffleball.game.Player;

/**
 * Created by rich on 9/9/16.
 */
public class PlayerImpl implements Player {

    private String name;

    public PlayerImpl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
