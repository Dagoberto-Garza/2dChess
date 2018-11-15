package com.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.Board;
import com.game.Pieces.*;
import com.game.Player;
import com.game.shapes.EMath;
import com.game.shapes.Rect;
import com.game.shapes.ShapeRendererExt;
import com.game.timers.Delta;

import java.util.ArrayList;
import java.util.EmptyStackException;

import static com.game.Main.*;

public class GameState extends State {

  public static  Board board = new Board();
    ShapeRendererExt shape;
    int numMoves = 0;
    public static boolean placing = false;
    public static Piece selected = null;
    Delta dClick = new Delta(8*ft);
    public static Player player1 = new Player();
    public static Player player2 = new Player();
    public GameState() {
        super(new GameStateManager());
        shape = new ShapeRendererExt();
        shape.setAutoShapeType(true);
        init();
    }

    @Override
    protected void handleInput(float dt) {
        dClick.update(dt);
        if (dClick.isDone()) {
            if (Gdx.input.isButtonPressed(0)) {
                Vector2 seg = screen.cpy().scl(1 / 8f);
                Vector2 ind = mousePos.cpy();
                Vector2 pos = new Vector2(ind.x / seg.x,  ind.y / seg.y);
              //  System.out.println(pos.toString());

                if(!placing){//selecting
                    board.select(pos);

                }else {
                    if(EMath.floor(pos).equals(selected.getPos())) {
                        selected.deselect();
                        selected=null;
                        placing=false;
                    }else {
                        Piece temp = selected;
                        boolean invalid = true;
                        ArrayList<Vector2> list = selected.validMoves();

                        for (Vector2 v : list) {
                            if (EMath.floor(pos).equals(v)) {
                                board.place(selected, pos);
                                selected = null;
                                placing = false;
                                invalid = false;
                            }

                        }
                        if (invalid) {
                            selected.deselect();
                            selected = null;
                            placing = false;
                        } else {
                            if (temp instanceof Pawn) {
                                Pawn p = (Pawn) temp;
                                p.beenMoved = true;
                            }
                        }
                    }
                }
                dClick.reset();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput(dt);

    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shape.begin();
        shape.set(ShapeRenderer.ShapeType.Filled);
        board.display(shape);
        shape.end();
    }

    @Override
    public void dispose() {

    }
    public void init() {
        ArrayList<Piece> lis1 = new ArrayList<>();
        ArrayList<Piece> lis2 = new ArrayList<>();

        for(int x = 0; x<8;x++){
            lis1.add(new Pawn(true,new Vector2(x,1)));
            lis2.add(new Pawn(false,new Vector2(x,6)));
        }
        lis1.add(new Rook(true,new Vector2(0,0)));
        lis2.add(new Rook(false,new Vector2(0,7)));
        lis1.add(new Bishop(true,new Vector2(2,0)));
        lis2.add(new Bishop(false,new Vector2(2,7)));
        lis1.add(new Knight(true,new Vector2(1,0)));
        lis2.add(new Knight(false,new Vector2(1,7)));
        lis1.add(new Queen(true,new Vector2(3,0)));
        lis2.add(new Queen(false,new Vector2(3,7)));
        lis1.add(new King(true,new Vector2(4,0)));
        lis2.add(new King(false,new Vector2(4,7)));
        lis1.add(new Bishop(true,new Vector2(5,0)));
        lis2.add(new Bishop(false,new Vector2(5,7)));
        lis1.add(new Knight(true,new Vector2(6,0)));
        lis2.add(new Knight(false,new Vector2(6,7)));
        lis1.add(new Rook(true,new Vector2(7,0)));
        lis2.add(new Rook(false,new Vector2(7,7)));








        for(Piece p:lis1){
            board.place(p, EMath.floor(p.getPos()));
        }
        for(Piece p:lis2){
            board.place(p,EMath.floor(p.getPos()));
        }

        player1.init(true,lis1);
        player2.init(false,lis2);

    }

}
