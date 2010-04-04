package com.leonardoborges.fallingdreams.models.pieces;

import com.leonardoborges.fallingdreams.models.Matrix;

enum Shape {
    I, J, L, O, 
    S, T, Z 
}

public class PieceFactory {
	public static AbstractPiece getRandomPiece(Matrix matrix) {
		int randomPiece = (int)(Math.random() * 7);

		switch (Shape.values()[randomPiece]) {
		case I:
			return new IPiece(matrix);
		case J:
			return new JPiece(matrix);
		case L:
			return new LPiece(matrix);
		case O:
			return new OPiece(matrix);
		case S:
			return new SPiece(matrix);
		case T:
			return new TPiece(matrix);
		case Z:
			return new ZPiece(matrix);	
		default:
			return new IPiece(matrix);
		}
	}
}
