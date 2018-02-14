package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by root on 02-01-18.
 */

public class VirtualJoystick{


    private String touchKnobImage;
    private String touchBackgroundImage;
    private Drawable touchBackground;
    private int sizeWidth;
    private int sizeHeight;
    private int posX;
    private int posY;

    private Touchpad touchpad;
    private TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchKnob;

    public VirtualJoystick()
    {

    }

    public VirtualJoystick( String touchKnobImage, String touchBackgroundImage,int sizeWidth, int sizeHeight,int posX,int posY){
        this.touchKnobImage=touchKnobImage;
        this.touchBackgroundImage=touchBackgroundImage;
        this.sizeWidth=sizeWidth;
        this.sizeHeight=sizeHeight;
        this.posX=posX;
        this.posY=posY;


    }

    public Touchpad create(){

        touchpadSkin = new Skin();
        touchpadSkin.add("touchKnob", new Texture(touchKnobImage));
        touchpadStyle = new TouchpadStyle();

        touchKnob = touchpadSkin.getDrawable("touchKnob");


        if (touchBackgroundImage==""){
            Pixmap background = new Pixmap(sizeWidth, sizeHeight, Pixmap.Format.RGBA8888);
            background.setBlending(Pixmap.Blending.None);
            background.setColor(1, 1, 1, .09f);
            background.fillCircle(100, 100, 100);

            touchpadStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(background)));

        }else {

            touchpadSkin.add("touchBackground", new Texture(touchBackgroundImage));
            touchBackground = touchpadSkin.getDrawable("touchBackground");
            touchpadStyle.background = touchBackground;

        }

        touchpadStyle.knob = touchKnob;
        touchpad = new Touchpad(10, touchpadStyle);
        touchpad.setBounds(posX, posY, sizeWidth, sizeHeight);

        return touchpad;


    }
    public void addActor(Stage stage){
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);
    }


}