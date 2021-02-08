package com.game.aws.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {
    private int columns;
    private int rows;
    private int bombs;
    private String userId;
}
