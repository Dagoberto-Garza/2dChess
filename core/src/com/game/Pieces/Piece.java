package com.game.Pieces;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.game.shapes.ShapeRendererExt;

import java.util.ArrayList;


public abstract class Piece {
    protected boolean selected = false;
    protected boolean isPlayerOne;
    public boolean dead=false;
    protected Vector2 position;
    protected Color color ;
public Piece(boolean isPlayerOne,Vector2 position){
    this.isPlayerOne = isPlayerOne;
    this.position = position;
    this.color = isPlayerOne?c1:c2;
}

     static Color c1 = Color.BLACK;
     static Color c2 = Color.WHITE;
    static Color c3 = Color.RED;

    public Color getColor(){
        return color;
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public void setPos(Vector2 position) {
        this.position=position;
    }

    public boolean isPlayerOne() {
        return isPlayerOne;
    }

    public Vector2 getPos() {
        return position;
    }
    public abstract ArrayList<Vector2> validMoves();
    public abstract void validAttack(Vector2 trg, ArrayList<Vector2> list);
    public abstract void validBlock(Vector2 trg, ArrayList<Vector2> list);
    public String toString() {

        String s = "x-"+position.x+"\ty-"+position.y+"\ttype: "+getClass()+"\tisPlayerOne? "+isPlayerOne()+"\tColor: "+color;
        return s;
    }

    public abstract void render(ShapeRendererExt sr);

    public void select() {
        selected = true;
    }

    public void deselect() {
        selected = false;

     }

    public void dispose(){
        position=null;

    }

}
