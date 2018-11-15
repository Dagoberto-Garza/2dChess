package com.game.Pieces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.game.shapes.Circle;
import com.game.shapes.ShapeRendererExt;
import com.game.states.GameState;

import java.util.ArrayList;

import static com.game.Main.screen;

public class Pawn extends Piece {
    public boolean beenMoved = false;

    public Pawn(boolean isPlayerOne, Vector2 position) {
        super(isPlayerOne, position);
    }

    @Override
    public ArrayList<Vector2> validMoves() {
        ArrayList<Vector2> list = new ArrayList<>();
        Vector2 v = new Vector2(position.x, position.y);
        Vector2 trg= v.cpy().add(0, 1 * (isPlayerOne ? 1 : -1));
        validBlock(trg,list);
        if (!beenMoved) {
            trg=v.cpy().add(0, 2 * (isPlayerOne ? 1 : -1));
            validBlock(trg,list);
        }
        trg = v.cpy().add(1, 1 * (isPlayerOne ? 1 : -1));
        validAttack(trg, list);
        trg = v.cpy().add(-1, 1 * (isPlayerOne ? 1 : -1));
        validAttack(trg, list);
        return list;
    }

    public void validAttack(Vector2 trg, ArrayList<Vector2> list) {
        Piece tmp = GameState.board.at(trg);
        if (tmp != null && tmp.isPlayerOne != isPlayerOne) {
            list.add(trg);
        }
    }

    public void validBlock(Vector2 trg, ArrayList<Vector2> list) {
        Piece tmp = GameState.board.at(trg);
        if (tmp == null) {
            list.add(trg);

        }
    }
    public void render(ShapeRendererExt sr) {
        Vector2 scr = screen.cpy().scl(1f / 8f);
        Vector2 fn = position.cpy().scl(scr).add(scr.cpy().scl(.5f));
        if (selected)
            sr.setColor(c3);
        else
            sr.setColor(getColor());
        sr.circle(new Circle(fn, (scr.x / 2) - 20));
    }


}
