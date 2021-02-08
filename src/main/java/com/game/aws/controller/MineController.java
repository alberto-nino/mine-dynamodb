package com.game.aws.controller;

import com.game.aws.dto.CellDTO;
import com.game.aws.dto.GameDTO;
import com.game.aws.model.Game;
import com.game.aws.service.MineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/mine")
@Slf4j
public class MineController {

    @Autowired
    MineService mineService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Game createMine(@RequestBody final GameDTO newGameRequest){
        log.info("request call service mine with userId {}", newGameRequest.getUserId());
        return mineService.createMine(newGameRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Game saveMine(@RequestBody final Game game) {
        log.info("request save mine with userId {}", game.getUserId());
        return mineService.saveGame(game);
    }

    @GetMapping("/{userid}")
    @ResponseStatus(HttpStatus.OK)
    public List<Game> getMineByUserId(@PathVariable("userid") final String userId) {
        log.info("request obtain user by id with  {}", userId);
        return mineService.getGamesByUserId(userId);
    }

    @GetMapping("/loading/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public Game loadMine(@PathVariable("gameId") final String gameId){
        log.info("request load mine game with gameId  {}", gameId);
        return mineService.getGameById(gameId);
    }

    @PutMapping("/pause/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public Game pauseMine(@PathVariable("gameId") final String gameId){
        log.info("request pause mine game with gameId  {}", gameId);
        return mineService.pause(gameId);
    }

    @PutMapping("/game/flag")
    @ResponseStatus(HttpStatus.OK)
    public Game flagCell(@RequestBody final CellDTO dto){
        log.info("request identify cell mine game with dto  {}", dto);
        return mineService.cellWithFlag(dto);
    }

    @PutMapping("/game/identify")
    @ResponseStatus(HttpStatus.OK)
    public Game recognizeCell(@RequestBody final CellDTO dto){
        log.info("request identify cell mine game with dto  {}", dto);
        return mineService.recognizeCell(dto);
    }
}
