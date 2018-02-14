package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

/**
 * Created by root on 02-02-18.
 */

public class Player {
    private String playerImage;
    private int posX;
    private int posY;
    private float playerSpeed;

    private Texture playerTexture;
    private Sprite playerSprite;

    private TiledMapTileLayer collisionLayer;


    public Player(){

    }

    public Player(String playerImage,int posX, int posY, float playerSpeed){
        this.playerImage=playerImage;
        this.posX=posX;
        this.posY=posY;
        this.playerSpeed=playerSpeed;

    }

    public Sprite create(){
        playerTexture = new Texture(Gdx.files.internal(playerImage));
        playerSprite = new Sprite(playerTexture);
        //Set position to centre of the screen
        playerSprite.setPosition(posX+playerSprite.getWidth()/2, posY+playerSprite.getHeight()/2);
        return playerSprite;
    }

    public void positionPlayer(Touchpad touchpad){

        playerSprite.setX(playerSprite.getX() + touchpad.getKnobPercentX()*playerSpeed);
        playerSprite.setY(playerSprite.getY() + touchpad.getKnobPercentY()*playerSpeed);

    }




}
