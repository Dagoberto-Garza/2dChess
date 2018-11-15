package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.game.Pieces.Pawn;
import com.game.Pieces.Piece;
import com.game.shapes.EMath;
import com.game.shapes.Rect;
import com.game.shapes.ShapeRendererExt;
import com.game.states.GameState;

import java.util.ArrayList;
import java.util.Arrays;

import static com.game.Main.*;
import static com.game.states.GameState.selected;


public class Board {
    int n = 8;
    int m = 8;
    private int[][] board = new int[n][m];
    private Piece[][] board2 = new Piece[n][n];

    private ArrayList<Piece> pieces = new ArrayList<>(32);

    public Board() {

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                board[x][y] = 0;
                board2[x][y] = null;
            }
        }

    }

    public void display(ShapeRendererExt shape) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                shape.set(ShapeType.Filled);
                shape.setColor(((i+j)%2)==0?new Color(.2f,.2f,.2f,1)
                        :new Color(.8f,.8f,.8f,1));
                shape.rect((WIDTH / n) * i, (HIGHT / m) * j,
                        WIDTH / n, HIGHT / m);


            }
        }
        if(selected!=null){
            shape.setColor(Color.BLUE);
            for(Vector2 v: selected.validMoves()){
                Vector2 s=screen.cpy().scl(1/8f) ;
                shape.rect(Rect.rect(v.scl(s),s));
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                shape.set(ShapeType.Line);
                shape.setColor(Color.WHITE);
                shape.rect((WIDTH / n) * i, (HIGHT / m) * j,
                        WIDTH / n, HIGHT / m);
                shape.set(ShapeType.Filled);

                if (board2[i][j] != null) {
                    board2[i][j].render(shape);
                }
            }
        }
    }

    public void place(Piece p, Vector2 pos) {
        int x = (int) pos.x;
        int y = (int) pos.y;
        Vector2 pos2 = p.getPos().cpy();
        if (!p.getPos().equals(EMath.floor(pos))) {
            p.setPos(new Vector2(x, y));
            board2[x][y] = p;
            board2[(int) pos2.x][(int) pos2.y] = null;
          //  System.out.println(board2[x][y].toString());
            p.deselect();

        }else{
            p.setPos(new Vector2(x, y));
            board2[x][y] = p;
            p.deselect();

        }
        if (board2[x][y] == null) {
            p.setPos(new Vector2(x, y));
            board2[x][y] = p;
            p.deselect();

        } else {
            Vector2 p1 = new Vector2(x, y);
            at(p1).dead = true;
            p.setPos(p1);
            board2[x][y] = p;
            p.deselect();
        }


    }

    public boolean hasWon() {

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int b = board[i][j];
                if (b != 3) {
                    if (j <= 3) {
                        if (b == board[i][j + 1] && b == board[i][j + 2] && b == board[i][j + 3]) {
                            return true;
                        }
                    }
                    if (i <= 2) {
                        if (b == board[i + 1][j] && b == board[i + 2][j] && b == board[i + 3][j]) {
                            return true;
                        }
                    }
                    if (j <= 3 && i <= 2) {
                        if (b == board[i + 1][j + 1] && b == board[i + 2][j + 2] && b == board[i + 3][j + 3]) {
                            return true;
                        }
                    }
                    if (j >= 3 && i <= 2) {
                        if (b == board[i + 1][j - 1] && b == board[i + 2][j - 2] && b == board[i + 3][j - 3]) {
                            return true;
                        }
                    }

                }

            }
        }

        return false;
    }


    public void print() {
        for (Piece p : pieces) {
            System.out.println(p.toString());
        }
    }


    public void pieceSelect(Vector2 piecePos) {

        for (Piece p : pieces) {
            if (p.getPos().equals(piecePos)) {
                p.setColor(new Color(.3f, .3f, 1, 1));

                if (Gdx.input.isButtonPressed(0)) {
                    System.out.println("out");
                    Vector2 pos = mousePos.cpy().scl((1 / screen.x), (1 / screen.y)).scl(8);
                    pos.set(EMath.mod(EMath.floor(pos), 8));
                    pos.y = 7 - pos.y;
                    p.setPos(pos);
                    //place(p);
                }

            }
        }
    }

    public void select(Vector2 ind) {
        Piece piece = board2[(int) ind.x][(int) ind.y];

        if (piece != null) {
            piece.select();
            GameState.placing = true;
            selected = piece;
        }
    }

    public Piece at(Vector2 p) {
        try {
            return board2[(int) p.x][(int) p.y];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
}



