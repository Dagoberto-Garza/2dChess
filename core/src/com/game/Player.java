package com.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.game.Pieces.Piece;
import com.game.shapes.ShapeRendererExt;

import java.util.ArrayList;

public class Player {
    boolean turn=false;
    ArrayList<Piece> pieces = new ArrayList<>();
    public Player(){

    }
    public void init(boolean turn,ArrayList<Piece> list){
        this.turn = turn;
        pieces = list;

    }
    public void render(ShapeRendererExt sr){
        sr.set(ShapeRenderer.ShapeType.Filled);
        for(Piece p: pieces) {

                if(!p.dead)
                p.render(sr);
        }
    }
}
