package com.game.aws.enums;

import com.game.aws.error.MineException;

public enum StatusEnum {

    GAME_PAUSED {
        @Override
        StatusEnum pause() {
            return StatusEnum.GAME_ACTIVE;
        };
        @Override
        boolean isEnded() {
            return false;
        };
    },
    GAME_ACTIVE {
        @Override
        StatusEnum pause() {
            return StatusEnum.GAME_PAUSED;
        }

        @Override
        boolean isEnded() {
            return false;
        };
    },
    WINNER {
        @Override
        StatusEnum pause() {
            throw new MineException("The game is over");
        };
        @Override
        boolean isEnded() {
            return true;
        };
    },
    FINAL {
        @Override
        StatusEnum pause() {
            throw new MineException("The game is over");
        };
        @Override
        boolean isEnded() {
            return true;
        };
    };

    abstract StatusEnum pause();
    abstract boolean isEnded();
}
