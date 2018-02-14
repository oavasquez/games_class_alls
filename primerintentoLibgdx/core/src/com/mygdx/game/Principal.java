package com.mygdx.game;


/**
 * Created by root on 02-01-18.
 */

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.physics.box2d.BodyDef;

import com.mygdx.game.utils.Constants;
import com.mygdx.game.utils.OrthogonalTiledMapRendererWithSprites;
import com.mygdx.game.utils.TiledObjectUtil;


import java.awt.Frame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;

import static com.mygdx.game.utils.Constants.PPM;


public class Principal implements ApplicationListener {
    private OrthographicCamera camera;
    private Stage stage;
    private SpriteBatch batch;
    public Touchpad touchpad;
    //private Sprite playerSprite;
    //private Player player;
    private Body body;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Texture tex;

    private TextureRegion textureRegion;
    private Animation animation;

    private MapLayer objectLayer;
    private Texture texture;
    private Sprite sprite;


    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private float gravity = 9.8f/60;
    private VirtualJoystick virtualJoystick;
    private final float SCALE = 3.0f;
    private BitmapFont font;
    boolean useStage = true;

    private ArrayList<TiledMapTileLayer.Cell> waterCellsInScene ;
    private ArrayList<TiledMapTileLayer.Cell> a1CellsInScene ;
    private Map<String,TiledMapTile> Tilesw;
    private Map<String,TiledMapTile> Tilesa1;
    private Map<String,TiledMapTile> Tilesa2;
    private Map<String,TiledMapTile> Tilesa3;
    private Map<String,TiledMapTile> Tilesa4;
    private Map<String,TiledMapTile> Tilesa5;
    private Map<String,TiledMapTile> Tilesa6;
    private Map<String,TiledMapTile> Tilesa7;
    private Map<String,TiledMapTile> Tilesa8;
    private Map<String,TiledMapTile> Tilesa9;
    private Map<String,TiledMapTile> Tilesb1;
    private Map<String,TiledMapTile> Tilesb2;
    private Map<String,TiledMapTile> Tilesb3;
    private Map<String,TiledMapTile> Tilesb4;
    float elapsedSinceAnimation = 0.0f;

    @Override
    public void create() {
        batch = new SpriteBatch();
        //Create camera
        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth()/SCALE , Gdx.graphics.getHeight()/SCALE);
        camera.update();

        world = new World(new Vector2(0f,0f),false);
        b2dr=new Box2DDebugRenderer();

        font = new BitmapFont();






        //Gdx.app.log("rutadelarchivo", String.format("La ruta es %s",Gdx.files.internal("mario.tmx").file().getAbsolutePath()));
        tiledMap =new TmxMapLoader().load("mundoC.tmx");
        //tiledMapRenderer =new OrthogonalTiledMapRenderer(tiledMap);
        tiledMapRenderer = new OrthogonalTiledMapRendererWithSprites(tiledMap);

        TiledMapTileSet tileset =  tiledMap.getTileSets().getTileSet("tiledmap");

        //Gdx.app.log("water","Nombre "+tileset.getProperties().getValues().next().toString());
        //Gdx.app.log("water","Nombre "+tileset.getProperties().get("agua").toString());




        Tilesw = new HashMap<String,TiledMapTile>();
        Tilesa1 = new HashMap<String,TiledMapTile>();
        Tilesa2 = new HashMap<String,TiledMapTile>();
        Tilesa3 = new HashMap<String,TiledMapTile>();
        Tilesa4 = new HashMap<String,TiledMapTile>();
        Tilesa5 = new HashMap<String,TiledMapTile>();
        Tilesa6 = new HashMap<String,TiledMapTile>();
        Tilesa7 = new HashMap<String,TiledMapTile>();
        Tilesa8 = new HashMap<String,TiledMapTile>();
        Tilesa9 = new HashMap<String,TiledMapTile>();
        Tilesb1 = new HashMap<String,TiledMapTile>();
        Tilesb2 = new HashMap<String,TiledMapTile>();
        Tilesb3 = new HashMap<String,TiledMapTile>();
        Tilesb4 = new HashMap<String,TiledMapTile>();

        for(TiledMapTile tile:tileset){

            Object property=null;

            if(tile.getProperties().containsKey("agua")){
                property = tile.getProperties().get("agua");
                Tilesw.put(property.toString(),tile);}
            if(tile.getProperties().containsKey("A1")){
                property = tile.getProperties().get("A1");
                Tilesa1.put(property.toString(),tile);}
            if(tile.getProperties().containsKey("A2")){
                property = tile.getProperties().get("A2");
                Tilesa2.put(property.toString(),tile);}
            if(tile.getProperties().containsKey("A3")){
                property = tile.getProperties().get("A3");
                Tilesa3.put(property.toString(),tile);}
            if(tile.getProperties().containsKey("A4")){
                property = tile.getProperties().get("A4");
                Tilesa4.put(property.toString(),tile);}
            if(tile.getProperties().containsKey("A5")){
                property = tile.getProperties().get("A5");
                Tilesa5.put(property.toString(),tile);}
            if(tile.getProperties().containsKey("A6")){
                property = tile.getProperties().get("A6");
                Tilesa6.put(property.toString(),tile);}
            if(tile.getProperties().containsKey("A7")){
                property = tile.getProperties().get("A7");
                Tilesa7.put(property.toString(),tile);}
            if(tile.getProperties().containsKey("A8")){
                property = tile.getProperties().get("A8");
                Tilesa8.put(property.toString(),tile);}
            if(tile.getProperties().containsKey("A9")){
                property = tile.getProperties().get("A9");
                Tilesa9.put(property.toString(),tile);}

            if(tile.getProperties().containsKey("B1")){
                property = tile.getProperties().get("B1");
                Tilesb1.put(property.toString(),tile);}
            if(tile.getProperties().containsKey("B2")){
                property = tile.getProperties().get("B2");
                Tilesb2.put(property.toString(),tile);}

            if(tile.getProperties().containsKey("B3")){
                property = tile.getProperties().get("B3");
                Tilesb3.put(property.toString(),tile);}

            if(tile.getProperties().containsKey("B4")){
                property = tile.getProperties().get("B4");
                Tilesb4.put(property.toString(),tile);}



            if(property != null){
                //Gdx.app.log("water", String.format("Entro %d, %s",tile.getId(), property.toString() ));

            }


        }

        waterCellsInScene = new ArrayList<TiledMapTileLayer.Cell>();
        //a1CellsInScene = new ArrayList<TiledMapTileLayer.Cell>();

        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("agua");
        //Gdx.app.log("water",String.format("h=%d, w=%d",layer.getHeight(),layer.getWidth()));

        for(int x = 0; x < layer.getWidth();x++){

            for(int y = 0; y < layer.getHeight();y++){

                Cell cell = layer.getCell(x,y);



                //Gdx.app.log("water",String.format("x=%d, y=%d",x,y));


                    Object property = null;
                    if (cell.getTile().getProperties().containsKey("agua")){
                        property= cell.getTile().getProperties().get("agua");
                        //waterCellsInScene.add(cell);
                    }
                    if (cell.getTile().getProperties().containsKey("A1")){
                        property= cell.getTile().getProperties().get("A1");
                       // a1CellsInScene.add(cell);
                    }

                    if (cell.getTile().getProperties().containsKey("A2")){
                        property= cell.getTile().getProperties().get("A2");}
                    if (cell.getTile().getProperties().containsKey("A3")){
                        property= cell.getTile().getProperties().get("A3");}
                    if (cell.getTile().getProperties().containsKey("A4")){
                        property= cell.getTile().getProperties().get("A4");}
                    if (cell.getTile().getProperties().containsKey("A5")){
                        property= cell.getTile().getProperties().get("A5");}
                    if (cell.getTile().getProperties().containsKey("A6")){
                        property= cell.getTile().getProperties().get("A6");}
                    if (cell.getTile().getProperties().containsKey("A7")){
                        property= cell.getTile().getProperties().get("A7");}
                    if (cell.getTile().getProperties().containsKey("A8")){
                        property= cell.getTile().getProperties().get("A8");}
                    if (cell.getTile().getProperties().containsKey("A9")){
                        property= cell.getTile().getProperties().get("A9");}



                    if (cell.getTile().getProperties().containsKey("B1")){
                        property= cell.getTile().getProperties().get("B1");}
                    if (cell.getTile().getProperties().containsKey("B2")){
                        property= cell.getTile().getProperties().get("B2");}

                    if (cell.getTile().getProperties().containsKey("B3")){
                        property= cell.getTile().getProperties().get("B3");}
                    if (cell.getTile().getProperties().containsKey("B4")){
                        property= cell.getTile().getProperties().get("B4");}





                    if(property != null){

                        //Gdx.app.log("water",String.format("property %s",property.toString()));
                        waterCellsInScene.add(cell);

                    }


            }

        }

        body = createBox(world,440,250,16,16,false,true);
        //createBox(world,40,20,16,16,true,true);

        //createCircle( );

        virtualJoystick = new VirtualJoystick("touchKnob.png","",200,200,25,25);
        touchpad =virtualJoystick.create();

        stage = new Stage();
        virtualJoystick.addActor(stage);

        //player = new Player("block.png",10,10,1f);



        tex = new Texture("block.png");
        objectLayer = tiledMap.getLayers().get("player-layer");
        textureRegion = new TextureRegion(tex,16,16);
        TextureMapObject tmo = new TextureMapObject(textureRegion);

        tmo.setX(440);
        tmo.setY(250);
        objectLayer.getObjects().add(tmo);







        //playerSprite=player.create();

        TiledObjectUtil.parseTiledObjectLayer(world,tiledMap.getLayers().get("collision-layer").getObjects());

    }

    @Override
    public void dispose() {
        world.dispose();


    }

    @Override
    public void render() {

        world.step(1/60f,6,2);


        Gdx.gl.glClearColor(0.901f, 0.901f, 0.901f, 0.01f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);




       // body.setTransform(new Vector2(body.getPosition().x + touchpad.getKnobPercentX()/16.0f,body.getPosition().y + touchpad.getKnobPercentY()/16.0f),body.getAngle());





        //Gdx.app.log("camara position ",String.format("position x=%2.4f y=%2.4f",camera.position.x,camera.position.y));
       // Gdx.app.log("body position ",String.format("position x=%2.4f y=%2.4f",body.getPosition().x,body.getPosition().y));


        TextureMapObject character = (TextureMapObject)tiledMap.getLayers().get("player-layer").getObjects().get(0);

        character.setX(character.getX()+ touchpad.getKnobPercentX()*2f);
        character.setY(character.getY()+ touchpad.getKnobPercentY()*2f);



        //    float x = character.getX();
        //  float y = character.getY();





        //body.setTransform(x  / 2, y  / 2, 0);


        /*
        Vector3 position=camera.position;
        position.x=body.getPosition().x*32;
        position.y=body.getPosition().y*32;
        camera.position.set(position);

        camera.update();*/


        Vector3 position=camera.position;
        position.x=character.getX();
        position.y=character.getY();
        camera.position.set(position);

        camera.update();




        tiledMapRenderer.setView(camera);

        tiledMapRenderer.render();

        batch.setProjectionMatrix(camera.combined);

        //Draw
        batch.begin();
        //playerSprite.draw(batch);
        batch.draw(tex,body.getPosition().x*32-(tex.getWidth()/2),body.getPosition().y*32-(tex.getHeight()/2));

        font.setColor(0, 0, 1, 1);
        font.getData().setScale(1);
        font.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond(), camera.position.x-170, camera.position.y+100);
        batch.end();

        b2dr.render(world,camera.combined.scl(PPM));

        elapsedSinceAnimation += Gdx.graphics.getDeltaTime();

        if(elapsedSinceAnimation > 0.5f){

            //updateWaterAnimations(a1CellsInScene);
            updateWaterAnimations(waterCellsInScene);

            elapsedSinceAnimation = 0.0f;

        }


        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void resize(int width, int height) {
    }

    public void update(float delta){

    }

    private void updateWaterAnimations(ArrayList<TiledMapTileLayer.Cell> CellsInScene){

        for(TiledMapTileLayer.Cell cell : CellsInScene){

            String property =  null;

            if (cell.getTile().getProperties().containsKey("agua")){
                property= cell.getTile().getProperties().get("agua").toString();
                newCellAnimation(property,cell, Tilesw);
            }
            if (cell.getTile().getProperties().containsKey("A1")){
                property= cell.getTile().getProperties().get("A1").toString();
                newCellAnimation(property,cell,Tilesa1);
            }
            if (cell.getTile().getProperties().containsKey("A2")){
                property= cell.getTile().getProperties().get("A2").toString();
                newCellAnimation(property,cell,Tilesa2);
            }if (cell.getTile().getProperties().containsKey("A3")){
                property= cell.getTile().getProperties().get("A3").toString();
                newCellAnimation(property,cell,Tilesa3);
            }if (cell.getTile().getProperties().containsKey("A4")){
                property= cell.getTile().getProperties().get("A4").toString();
                newCellAnimation(property,cell,Tilesa4);
            }if (cell.getTile().getProperties().containsKey("A5")){
                property= cell.getTile().getProperties().get("A5").toString();
                newCellAnimation(property,cell,Tilesa5);
            }if (cell.getTile().getProperties().containsKey("A6")){
                property= cell.getTile().getProperties().get("A6").toString();
                newCellAnimation(property,cell,Tilesa6);
            }if (cell.getTile().getProperties().containsKey("A7")){
                property= cell.getTile().getProperties().get("A7").toString();
                newCellAnimation(property,cell,Tilesa7);
            }if (cell.getTile().getProperties().containsKey("A8")){
                property= cell.getTile().getProperties().get("A8").toString();
                newCellAnimation(property,cell,Tilesa8);
            }if (cell.getTile().getProperties().containsKey("A9")){
                property= cell.getTile().getProperties().get("A9").toString();
                newCellAnimation(property,cell,Tilesa9);
            }if (cell.getTile().getProperties().containsKey("B1")){
                property= cell.getTile().getProperties().get("B1").toString();
                newCellAnimation(property,cell,Tilesb1);
            }if (cell.getTile().getProperties().containsKey("B2")){
                property= cell.getTile().getProperties().get("B2").toString();
                newCellAnimation(property,cell,Tilesb2);
            }if (cell.getTile().getProperties().containsKey("B3")){
                property= cell.getTile().getProperties().get("B3").toString();
                newCellAnimation(property,cell,Tilesb3);
            }if (cell.getTile().getProperties().containsKey("B4")){
                property= cell.getTile().getProperties().get("B4").toString();
                newCellAnimation(property,cell,Tilesb4);
            }





        }

    }

    public void newCellAnimation( String property, Cell cell, Map<String,TiledMapTile> Tiles){
        Integer currentAnimationFrame = Integer.parseInt(property);



        currentAnimationFrame++;

        if(currentAnimationFrame > Tiles.size())

            currentAnimationFrame = 1;


       // Gdx.app.log("water",String.format("currentAnimationFrame = %d, TilsSirze= %d",currentAnimationFrame,Tiles.size()));
        TiledMapTile newTile = Tiles.get(currentAnimationFrame.toString());

        cell.setTile(newTile);

    }








    public  Body createBox( World world, float x, float y, int width, int height, boolean isStatic, boolean fixedRotation) {
        Body pBody;
        BodyDef def = new BodyDef();

        if(isStatic)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = fixedRotation;
        pBody = world.createBody(def);
        //pBody.setUserData("wall");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);


        pBody.createFixture(shape,1.0f);
        shape.dispose();
        return pBody;
    }

    public  Body createCircle( ) {


        CircleShape Shape = new CircleShape();
        Shape.setRadius(8 / Constants.PPM);
        Shape.setPosition(new Vector2(50 / Constants.PPM, 50 / Constants.PPM));

        Body pBody;
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        pBody = world.createBody(bdef);


        pBody.createFixture(Shape,1.0f);
        Shape.dispose();
        return pBody;




    }
}