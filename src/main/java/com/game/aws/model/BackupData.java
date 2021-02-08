package com.game.aws.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@DynamoDBDocument
@NoArgsConstructor
public class BackupData {
    private int columns;
    private int rows;
    private int bombs;
}
