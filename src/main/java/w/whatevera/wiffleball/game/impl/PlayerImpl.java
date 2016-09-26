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

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        return name.equals(((Player)o).getName());
    }
}
