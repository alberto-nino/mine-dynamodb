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
public class Cell {

    private int positionX;
    private int positionY;
    private long value;
    private boolean flagged;
    private boolean bomb;
    private boolean recognized;

    public void flag() {
        this.flagged = !this.flagged;
    }

	public boolean isAdjacentTo(Cell cell) {
		return (
            Math.abs(this.getPositionX()-cell.getPositionX()) <=1 &&
            Math.abs(this.getPositionY()-cell.getPositionY()) <=1 &&
            !(this.getPositionX() == cell.getPositionX() && this.getPositionY() == cell.getPositionY())
        );
    }

}
