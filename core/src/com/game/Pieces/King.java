package com.game.Pieces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.game.shapes.Arc;
import com.game.shapes.ShapeRendererExt;
import com.game.states.GameState;

import java.util.ArrayList;

import static com.game.Main.screen;

public class King extends Piece{
    public King(boolean isPlayerOne, Vector2 position) {
        super(isPlayerOne,position);
    }

    @Override
    public ArrayList<Vector2> validMoves() {
        ArrayList<Vector2> list = new ArrayList<>();
        Vector2 v = position.cpy();
        Vector2 trg= null;
        for(int i=-1;i<2;i++){
            for(int j=-1;j<2;j++){
                    trg = v.cpy().add(i, j);
                    validAttack(trg, list);
                    validBlock(trg, list);
            }
        }
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

    @Override
    public void render(ShapeRendererExt sr) {
        Vector2 scr= screen.cpy().scl(1f/8f);
        Vector2 fn= position.cpy().scl(scr).add(scr.cpy().scl(.5f));
        if(selected)
            sr.setColor(c3);
        else
            sr.setColor(getColor());
        sr.arc(new Arc(fn,40f,180f,180f,10,1f));

    }
}
