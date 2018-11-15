package com.game.Pieces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.game.shapes.Rect;
import com.game.shapes.ShapeRendererExt;
import com.game.states.GameState;

import java.util.ArrayList;

import static com.game.Main.screen;

public class Rook extends Piece{
    public Rook(boolean isPlayerOne, Vector2 position) {
        super(isPlayerOne,position);
    }
    boolean blocked=true;
    boolean attack=true;
    @Override
    public ArrayList<Vector2> validMoves() {
        ArrayList<Vector2> list = new ArrayList<>();

        for(float x = position.x+1; x< 8; x++){
            if(attack && blocked)validAttack(new Vector2(x, position.y), list);
            if(blocked) validBlock(new Vector2(x, position.y), list);
        }
        blocked=true;
        attack=true;
        for(float y = position.y+1; y< 8; y++){
            if(attack && blocked)validAttack(new Vector2(position.x, y), list);
            if(blocked) validBlock(new Vector2(position.x, y), list);
        }
        blocked=true;
        attack=true;
        for(float y = position.y-1; y>=0; y--){
            if(attack && blocked)validAttack(new Vector2(position.x, y), list);
            if(blocked) validBlock(new Vector2(position.x, y), list);
        }
        blocked=true;
        attack=true;

        for(float x = position.x-1; x>=0; x--){
            if(attack && blocked)validAttack(new Vector2(x, position.y), list);
            if(blocked) validBlock(new Vector2(x, position.y), list);
        }
        blocked=true;
        attack=true;
        return list;
    }


    public void validAttack(Vector2 trg, ArrayList<Vector2> list) {
        Piece tmp = GameState.board.at(trg);
        if (tmp != null && tmp.isPlayerOne != isPlayerOne) {
            list.add(trg);
            attack=false;
        }
    }

    public void validBlock(Vector2 trg, ArrayList<Vector2> list) {
        Piece tmp = GameState.board.at(trg);
        if (tmp == null) {
            list.add(trg);
        }else{
            blocked=false;
        }
    }

    @Override
    public void render(ShapeRendererExt sr) {
        Vector2 scr= screen.cpy().scl(1f/8f);
        Vector2 index= position.cpy().scl(scr).add(12.5f,12.5f);
        if(selected)
            sr.setColor(c3);
        else
            sr.setColor(getColor());
        sr.rect(Rect.rect(index,75,75));
    }
}
