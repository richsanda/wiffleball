package w.whatevera.wiffleball.game.impl;

import w.whatevera.wiffleball.game.BaseRunner;
import w.whatevera.wiffleball.game.Player;

/**
 * Created by rich on 9/23/16.
 */
public class BaseRunnerImpl implements BaseRunner {

    private Player runner;
    private Player pitcher;

    public BaseRunnerImpl(Player runner, Player pitcher) {
        this.runner = runner;
        this.pitcher = pitcher;
    }

    @Override
    public Player getRunner() {
        return runner;
    }

    @Override
    public Player getPitcher() {
        return pitcher;
    }
}
