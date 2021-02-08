package com.game.aws.service;

import com.game.aws.dto.CellDTO;
import com.game.aws.dto.GameDTO;
import com.game.aws.enums.StatusEnum;
import com.game.aws.error.MineException;
import com.game.aws.model.Cell;
import com.game.aws.model.Game;
import com.game.aws.repository.MineRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MineServiceImpl implements MineService {

    @Autowired
    MineRepository mineRepository;

    @Override
    public Game getGameById(String gameId) {
        log.info("Invoking repository find by gameId {}", gameId);
        Optional<Game> response = mineRepository.findById(gameId);
        return response.orElseThrow(() -> new MineException("Game with id does not exist"));
    }


    @Override
    public Game createMine(GameDTO gameDTO) {
        log.info("Invoking repository saving game");
        return saveGame(generateInitGame(gameDTO));
    }

    @Override
    public Game generateInitGame(GameDTO gameDTO) {
        Game newGame = Game.builder().status(StatusEnum.GAME_ACTIVE).userId(gameDTO.getUserId()).build();
        newGame.initCells(gameDTO.getColumns(), gameDTO.getRows(), gameDTO.getBombs());
        return newGame;
    }

    @Override
    public List<Game> getGamesByUserId(final String userId) {
        log.info("Invoking repository find by user id {}", userId);
        return mineRepository.findByUserId(userId);
    }


    @Override
    public Game pause(String gameId) {
        Game game = getGameById(gameId);
        game.pause();
        log.info("Invoking repository to save the game mine with id {}", gameId);
        return this.mineRepository.save(game);
    }

    @Override
    public Game cellWithFlag(CellDTO dto) {
        Game game = getGameById(dto.getGameId());
        Cell cell = game.getCell(dto.getPositionX(), dto.getPositionY());
        game.flagCell(cell);
        return saveGame(game);
    }

    @Override
    public Game saveGame(final Game newGame) {
        log.info("Invoking repository to save game");
        return mineRepository.save(newGame);
    }

    @Override
    public Game recognizeCell(CellDTO dto) {
        Game game = getGameById(dto.getGameId());
        Cell cell = game.getCell(dto.getPositionX(), dto.getPositionY());
        game.recognizeCell(cell);
        return saveGame(game);
    }
}
