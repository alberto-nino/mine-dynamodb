package com.game.aws.service;


import com.game.aws.dto.CellDTO;
import com.game.aws.dto.GameDTO;
import com.game.aws.enums.StatusEnum;
import com.game.aws.model.Cell;
import com.game.aws.model.Game;
import com.game.aws.repository.MineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
public class GameServiceTest {

    @Autowired
    private MineService mineService;

    @MockBean
    private MineRepository mineRepository;

    private GameDTO gameDTO;

    private Game gameCreated;

    private final String USER_ID = "USER";

    private Cell cellWithBomb;

    private Cell cellWitValue;

    private Cell cellBlank;

    @BeforeEach
    public void setupTest() {
        gameDTO = GameDTO.builder().bombs(5).columns(10).rows(10).userId(USER_ID).build();
        gameCreated = mineService.generateInitGame(gameDTO);
        this.gameCreated.setCreationTime(new Date());
        
        cellWithBomb = gameCreated.getCells().stream().filter(c -> c.isBomb()).findFirst().orElseThrow();
        cellWitValue = gameCreated.getCells().stream().filter(c -> !c.isBomb() && c.getValue()>0).findFirst().orElseThrow();
        cellBlank = gameCreated.getCells().stream().filter(c -> !c.isBomb() && c.getValue()==0).findFirst().orElseThrow();
    }

    @Test
    void validGame(){
        Mockito.when(mineRepository.save(any(Game.class))).thenReturn(this.gameCreated);
        
        Game game = mineService.createMine(gameDTO);

        assertNotNull(game);
        assertEquals(gameDTO.getRows() * gameDTO.getColumns(), game.getCells().size());
        assertEquals(gameDTO.getBombs(), game.bombsAmount());
        assertEquals(gameDTO.getUserId(), game.getUserId());
    }

    
    @Test
    void pauseGame(){
        String gameId = UUID.randomUUID().toString();
        this.gameCreated.setId(gameId);
        Mockito.when(mineRepository.findById(eq(gameId))).thenReturn(Optional.of(gameCreated));
        Mockito.when(mineRepository.save(eq(gameCreated))).thenReturn(gameCreated);
        Game gamePaused = mineService.pause(gameId);

        assertEquals(gamePaused.getStatus(), StatusEnum.GAME_PAUSED);
    }


    @Test
    void flagCell(){
        String gameId = UUID.randomUUID().toString();
        this.gameCreated.setId(gameId);
        CellDTO cellRequest = CellDTO.builder().gameId(gameId).positionX(0).positionY(0).build();
        Mockito.when(mineRepository.findById(eq(gameId))).thenReturn(Optional.of(gameCreated));
        Mockito.when(mineRepository.save(eq(gameCreated))).thenReturn(gameCreated);
        
        assertTrue(mineService.cellWithFlag(cellRequest).getCell(0, 0).isFlagged());
    }

    @Test
    void unplugCell(){
        String gameId = UUID.randomUUID().toString();
        this.gameCreated.setId(gameId);
        gameCreated.getCell(0,0).flag();

        CellDTO cellRequest = CellDTO.builder().gameId(gameId).positionX(0).positionY(0).build();
        Mockito.when(mineRepository.findById(eq(gameId))).thenReturn(Optional.of(gameCreated));
        Mockito.when(mineRepository.save(eq(gameCreated))).thenReturn(gameCreated);
        
        assertFalse(mineService.cellWithFlag(cellRequest).getCell(0, 0).isFlagged());
    }


    @Test
    void recognizeCellThatIsBombChangeGameStatusFinal(){
        String gameId = UUID.randomUUID().toString();
        this.gameCreated.setId(gameId);
        Mockito.when(mineRepository.findById(eq(gameId))).thenReturn(Optional.of(gameCreated));
        Mockito.when(mineRepository.save(eq(gameCreated))).thenReturn(gameCreated);
        
        Game game = mineService.recognizeCell(CellDTO.builder().gameId(gameId).positionX(cellWithBomb.getPositionX()).positionY(cellWithBomb.getPositionY()).build());

        assertEquals(game.getStatus(), StatusEnum.FINAL);
    }

    @Test
    void recognizeNotBombCellsAndYouAWinner(){
        String gameId = UUID.randomUUID().toString();
        gameDTO = GameDTO.builder().bombs(1).columns(2).rows(2).userId(USER_ID).build();
        gameCreated = mineService.generateInitGame(gameDTO);
        gameCreated.setId(gameId);
        Mockito.when(mineRepository.findById(eq(gameId))).thenReturn(Optional.of(gameCreated));
        Mockito.when(mineRepository.save(eq(gameCreated))).thenReturn(gameCreated);

        Game finalScore = gameCreated.getCells().stream().filter(c -> !c.isBomb()).map(notBomb -> {
            Game game = mineService.recognizeCell(CellDTO.builder().gameId(gameId).positionX(notBomb.getPositionX()).positionY(notBomb.getPositionY()).build());
            return game;
        }).reduce((a, b) -> a).orElseThrow();
        
        assertEquals(finalScore.getStatus(), StatusEnum.WINNER);
    }

}
