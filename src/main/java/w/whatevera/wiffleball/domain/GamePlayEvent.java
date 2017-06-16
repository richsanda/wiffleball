package w.whatevera.wiffleball.domain;

/**
 * Created by rich on 6/16/17.
 */
public enum GamePlayEvent {

    START_GAME,
    SET_PITCHER,
    WALK,
    SINGLE,
    DOUBLE,
    TRIPLE,
    HOME_RUN,
    ERROR_REACH,
    ERROR_ADVANCE,
    STRIKEOUT_SWINGING,
    STRIKEOUT_LOOKING,
    STRIKEOUT_BOTH,
    FLY_OUT,
    POP_OUT,
    GROUND_OUT,
    LINE_OUT,
    DOUBLE_PLAY,
    OUT,
    UNDO,
    SKIP,
    FINALIZE,
    REPLACE_PLAYER;
}
