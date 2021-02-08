package com.game.aws.service;

import com.game.aws.dto.CellDTO;
import com.game.aws.dto.GameDTO;
import com.game.aws.model.Game;

import java.util.List;

public interface MineService {

    Game saveGame(Game newGame);

    List<Game> getGamesByUserId(String userId);

    Game createMine(GameDTO newGameRequest) ;

    Game generateInitGame(GameDTO newGameRequest);

    Game getGameById(String gameId);

    Game pause(String gameId);

    Game cellWithFlag(CellDTO cellRequest);

    Game recognizeCell(CellDTO cellRequest);
}
