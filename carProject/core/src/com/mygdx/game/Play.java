package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Play extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture tex;
	private Texture cars;
	private OrthographicCamera camera;
	private final float SCALE = 3.0f;
	private Skin skin;
	private Pixmap pixmap;


	private Stage stage;
	private Sprite spriteVol;
	private Sprite spriteCars;
    private float rotate;
	
	@Override
	public void create () {
		batch = new SpriteBatch();


		camera = new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth()/SCALE , Gdx.graphics.getHeight()/SCALE);
		camera.update();




        //pixmap= getPixmapCircle(50, Color.WHITE,true);
        //tex=new Texture(pixmap);
        tex = new Texture("gaming1.png");
        spriteVol=new Sprite(tex,0,0,100,100);
        spriteVol.setPosition(150,0);
        cars=new Texture("cars1.png");
        spriteCars= new Sprite(cars,0,0,100,100);
        spriteCars.setPosition(150,100);
        //tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);

        if (Gdx.input.isTouched()){

            Vector3 touchpos = new Vector3();
            touchpos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchpos);
            rotate+= Gdx.input.getDeltaX();

            if(rotate>-75.0 && rotate<75.0){
            spriteVol.setRotation(rotate);
            spriteCars.setRotation(-spriteVol.getRotation()*0.20f);
            }


            Gdx.app.log("Cars",String.format("esta tocando rotate= %2.4f ",rotate));
        }


        if(!Gdx.input.isTouched()){
            rotate=spriteVol.getRotation();
            if(rotate>0){

                rotate--;

            }
            if(rotate<0){
                rotate++;
            }
            if(rotate>-1 && rotate<1){
                rotate=0;
            }
            spriteVol.setRotation(rotate);
            spriteCars.setRotation(-spriteVol.getRotation()*0.20f);
        }





        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        //batch.draw(tex,150,0);
        //batch.draw(cars,150,90);
        spriteCars.draw(batch);
        spriteVol.draw(batch);
        batch.end();






	}
	
	@Override
	public void dispose () {
		batch.dispose();
		tex.dispose();
	}

    public static Pixmap getPixmapCircle(int radius, Color color, boolean isFilled) {
        Pixmap pixmap=new Pixmap(2*radius+1, 2*radius+1, Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(color);
        if(isFilled)
            pixmap.fillCircle(radius, radius, radius);
        else
            pixmap.drawCircle(radius, radius, radius);
        pixmap.drawLine(radius, radius, 2*radius, radius);
        pixmap.setFilter(Pixmap.Filter.NearestNeighbour);
        return pixmap;
    }
}
