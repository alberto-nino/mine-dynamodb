package com.game.aws.repository;
import com.game.aws.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

import java.util.List;

@EnableScan
public interface MineRepository extends CrudRepository<Game, String> {

    List<Game> findByUserId(String userId);
}
