#include "HelloWorldScene.h"

#include "Definition.h"
#include "GameEnd.h"
#include "SimpleAudioEngine.h"
using namespace CocosDenshion;

#define BACKGROUND_MUSIC_SFX  "background-music.mp3"
#define PEW_PEW_SFX           "pew-pew-lei.mp3"
#define DEAD_SOUND            "dead-sound.mp3"
#define FAIL_SOUND            "fail-sound.mp3"


USING_NS_CC;



Scene* HelloWorld::createScene()
{
    auto scene = Scene::createWithPhysics();
    scene->getPhysicsWorld()->setGravity(Vec2(0,0));

    //scene->getPhysicsWorld()->setDebugDrawMask(PhysicsWorld::DEBUGDRAW_ALL);

    auto layer = HelloWorld::create();

    scene->addChild(layer);

    return scene;
}


enum class PhysicsCategory
{
    None = 0,
    Projectile = 0x1, //2
    Monster = 0x2  //1

};

// Print useful error message instead of segfaulting when files are not there.
static void problemLoading(const char* filename)
{
    printf("Error while loading: %s\n", filename);
    printf("Depending on how you compiled you might have to add 'Resources/' in front of filenames in HelloWorldScene.cpp\n");
}

// on "init" you need to initialize your instance
bool HelloWorld::init()
{
    //////////////////////////////
    // 1. super init first
    CCLOG( "Iniciando Juego" );
    if ( !Scene::init() )
    {
        return false;
    }
    // 2
    auto origin = Director::getInstance()->getVisibleOrigin();
    auto winSize = Director::getInstance()->getVisibleSize();
    auto origin1 = Vec2(origin.x , origin.y + 25);

    CCLOG("height = %20.2f,  width=%20.2f", winSize.height, winSize.width);
    CCLOG("x = %20.2f,  y=%20.2f", origin.x, origin.y);



// 3
    auto background = DrawNode::create();
    background->drawSolidRect(origin1, winSize, Color4F(0.09f, 0.22f, 0.4f, 1.0f));


    this->addChild(background);
// 4
    _player = Sprite::create("player.png");
    _player->setPosition(Vec2(winSize.width * 0.1, winSize.height * 0.5));
    this->addChild(_player);

    srand((unsigned int)time(nullptr));
    this->schedule(schedule_selector(HelloWorld::addMonster), 1.5);
    this->schedule(schedule_selector(HelloWorld::addHeart), 1.5);



    auto eventListener = EventListenerTouchOneByOne::create();
    eventListener->onTouchBegan = CC_CALLBACK_2(HelloWorld::onTouchBegan, this);
    eventListener->setSwallowTouches(true);
    this->getEventDispatcher()->addEventListenerWithSceneGraphPriority(eventListener, _player);

    auto contactListener = EventListenerPhysicsContact::create();
    contactListener->onContactBegin = CC_CALLBACK_1(HelloWorld::onContactBegan, this);
    this->getEventDispatcher()->addEventListenerWithSceneGraphPriority(contactListener, this);

    _monstersDestroyed = 0;

    score=0;

    __String *tempScore = __String::createWithFormat( "%i/20", score );

    scoreLabel = Label::createWithTTF( tempScore->getCString( ), "fonts/Marker Felt.ttf", winSize.height * 0.1 );
    scoreLabel->setColor( Color3B::WHITE );
    scoreLabel->setPosition( Point( winSize.width / 2 + origin.x, winSize.height * 0.75 + origin.y ) );

    this->addChild( scoreLabel, 10000 );

    mensajeLabel = Label::createWithTTF( "Destruye los fantamas y llega hasta 20 \n evita las calaveras o perderas puntos", "fonts/Marker Felt.ttf", winSize.height * 0.1 );
    mensajeLabel->setColor( Color3B::WHITE );
    mensajeLabel->setPosition( Point( winSize.width / 2 + origin.x, winSize.height * 0.75 + origin.y-50 ) );

    this->addChild( mensajeLabel, 10000 );

    auto action=FadeOut::create(5);
    mensajeLabel->runAction(action);




    SimpleAudioEngine::getInstance()->preloadEffect(PEW_PEW_SFX );
    SimpleAudioEngine::getInstance()->preloadEffect(DEAD_SOUND);
    SimpleAudioEngine::getInstance()->preloadEffect(FAIL_SOUND);
    SimpleAudioEngine::getInstance()->preloadBackgroundMusic(BACKGROUND_MUSIC_SFX);
    SimpleAudioEngine::getInstance()->setEffectsVolume(1.0);



    SimpleAudioEngine::getInstance()->setBackgroundMusicVolume(0.08);
    SimpleAudioEngine::getInstance()->playBackgroundMusic(BACKGROUND_MUSIC_SFX, true);


    this->scheduleUpdate();

    return true;

}

void HelloWorld::addMonster(float dt) {
    auto monster = Sprite::create("monster.png");




    auto monsterContentSize = monster->getContentSize();
    auto selfContentSize = this->getContentSize();
    int minY = monsterContentSize.height+10;
    int maxY = selfContentSize.height - monsterContentSize.height;
    CCLOG("MOSTER minY= %i ,maxY=%i, monsterContenSize.heigth=%4.2f, monsterContenSize.with=%4.2f",minY ,maxY,monsterContentSize.height,selfContentSize.height);
    int rangeY = maxY - minY;
    int randomY = (rand() % rangeY) + minY;
    CCLOG("MOSTER randomY=%i ",randomY);

    monster->setPosition(Vec2(selfContentSize.width + monsterContentSize.width/2, randomY));

    // 1
    auto monsterSize = monster->getContentSize();
    auto physicsBody = PhysicsBody::createBox(Size(monsterSize.width , monsterSize.height),
                                              PhysicsMaterial(0.1f, 1.0f, 0.0f));
    // 2
    physicsBody->setDynamic(true);
    // 3
    //physicsBody->setCategoryBitmask((int)PhysicsCategory::Monster);
    physicsBody->setCategoryBitmask(0x1);
    //physicsBody->setCollisionBitmask((int)PhysicsCategory::None);
    physicsBody->setCollisionBitmask(0x1);
    physicsBody->setContactTestBitmask(0x2);
    monster->setPhysicsBody(physicsBody);

    this->addChild(monster);


    // 2
    int minDuration = 2.0;
    int maxDuration = 4.0;
    int rangeDuration = maxDuration - minDuration;
    int randomDuration = (rand() % rangeDuration) + minDuration;

    // 3
    auto actionMove = MoveTo::create(randomDuration, Vec2(-monsterContentSize.width/2, randomY));
    auto actionRemove = RemoveSelf::create();


    monster->runAction(Sequence::create(actionMove,actionRemove, nullptr));
}

void HelloWorld::addHeart(float dt){
    auto heart = Sprite::create("heart.png");




    auto heartContentSize = heart->getContentSize();
    auto selfContentSize = this->getContentSize();
    int minY = heartContentSize.height;
    int maxY = selfContentSize.height - heartContentSize.height;
    int rangeY = maxY - minY;
    int randomY = (rand() % rangeY) + minY;

    heart->setPosition(Vec2(selfContentSize.width + heartContentSize.width/2, randomY));

    // 1
    auto heartSize = heart->getContentSize();
    auto physicsBody = PhysicsBody::createBox(Size(heartSize.width , heartSize.height),
                                              PhysicsMaterial(0.0, 0.0, 0.0));
    // 2
    physicsBody->setDynamic(true);
    // 3
    //physicsBody->setCategoryBitmask((int)PhysicsCategory::Monster);
    physicsBody->setCategoryBitmask(0x1);
    //physicsBody->setCollisionBitmask((int)PhysicsCategory::None);
    physicsBody->setCollisionBitmask(0x3);
    physicsBody->setContactTestBitmask(0x2);
    heart->setPhysicsBody(physicsBody);

    this->addChild(heart);


    // 2
    int minDuration = 2.0;
    int maxDuration = 5.0;
    int rangeDuration = maxDuration - minDuration;
    int randomDuration = (rand() % rangeDuration) + minDuration;

    // 3
    auto actionMove = MoveTo::create(randomDuration, Vec2(-heartContentSize.width/2, randomY));
    auto actionRemove = RemoveSelf::create();


    heart->runAction(Sequence::create(actionMove,actionRemove, nullptr));

}

bool HelloWorld::onTouchBegan(Touch *touch, Event *unused_event) {
    // 1  - Just an example for how to get the  _player object
    //auto node = unused_event->getCurrentTarget();

    // 2


    Vec2 touchLocation = touch->getLocation();
    Vec2 offset = touchLocation - _player->getPosition();

    // 3
    if (offset.x < 0) {
        return true;
    }

    // 4
    auto projectile = Sprite::create("projectile.png");
    projectile->setPosition(_player->getPosition());

    auto projectileSize = projectile->getContentSize();
    auto physicsBody = PhysicsBody::createCircle(projectileSize.width/2 );

    physicsBody->setDynamic(true);

    physicsBody->setCategoryBitmask(0x2);
    physicsBody->setCollisionBitmask(0x2);
    physicsBody->setContactTestBitmask(0x1);
    projectile->setPhysicsBody(physicsBody);

    this->addChild(projectile);
    SimpleAudioEngine::getInstance()->playEffect(PEW_PEW_SFX);


    // 5
    offset.normalize();
    auto shootAmount = offset * 1000;

    // 6
    auto realDest = shootAmount + projectile->getPosition();

    // 7
    auto actionMove = MoveTo::create(2.0f, realDest);
    auto actionRemove = RemoveSelf::create();
    projectile->runAction(Sequence::create(actionMove,actionRemove, nullptr));


    return true;
}

bool HelloWorld::onContactBegan(cocos2d::PhysicsContact &contact) {



    auto nodeA = contact.getShapeA()->getBody()->getNode();
    auto nodeB = contact.getShapeB()->getBody()->getNode();

    if(nodeA!=NULL && nodeB!=NULL){
        auto spriteABody=contact.getShapeA()->getBody();
        auto spriteBBody=contact.getShapeB()->getBody();



        if(score<10){
            this->schedule(schedule_selector(HelloWorld::addHeart), 1.5);

        }
        if(score>10){
            this->schedule(schedule_selector(HelloWorld::addHeart), 0.5);

        }
        if(score>15){
            this->schedule(schedule_selector(HelloWorld::addHeart), 0.1);

        }
        if(score<15){
            this->schedule(schedule_selector(HelloWorld::addHeart), 0.5);

        }


        nodeA->removeFromParent();
        nodeB->removeFromParent();


        //CCLOG( "COLLISION HAS OCCURED" );

        //PhysicsBody *a = contact.getShapeA()->getBody();
        //PhysicsBody *b = contact.getShapeB()->getBody();

        // check if the bodies have collided
        if ( ( 0x1 == spriteABody->getCollisionBitmask() && 0x2 == spriteBBody->getCollisionBitmask() ) || ( 0x2 == spriteABody->getCollisionBitmask() && 0x1 == spriteBBody->getCollisionBitmask() ) )
        {
            //CCLOG( "COLISION ENTRE ENEMIGOS" );
            SimpleAudioEngine::getInstance()->playEffect(DEAD_SOUND);


            score++;

            __String *tempScore = __String::createWithFormat( "%i/20", score );

            scoreLabel->setString( tempScore->getCString( ) );

            CCLOG( "COLISION ENTRE ENEMIGOS");

        }
        if ( ( 0x2 == spriteABody->getCollisionBitmask() && 0x3 == spriteBBody->getCollisionBitmask() ) || ( 0x3 == spriteABody->getCollisionBitmask() && 0x2 == spriteBBody->getCollisionBitmask() ) )
        {
            CCLOG( "COLISION ENTRE CORAZON " );
            SimpleAudioEngine::getInstance()->playEffect(FAIL_SOUND);

            if(score>0){

                if(score>5) {
                    score=score-5;
                } else{
                    score=score-1;
                }
                __String *tempScore = __String::createWithFormat("%i/20", score);

                scoreLabel->setString(tempScore->getCString());
            }
        }

        if(score>=20){
            HelloWorld::GoToGameScene(this);


        }


    }
    this->scheduleUpdate();

    return true;
}

void HelloWorld::GoToGameScene( cocos2d::Ref *sender )
{
    auto scene = GameEnd::createScene();

    Director::getInstance( )->replaceScene( TransitionFade::create( TRANSITION_TIME, scene ) );
}




void HelloWorld::menuCloseCallback(Ref* pSender)
{
    //Close the cocos2d-x game scene and quit the application
    Director::getInstance()->end();

    #if (CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    exit(0);
#endif

    /*To navigate back to native iOS screen(if present) without quitting the application  ,do not use Director::getInstance()->end() and exit(0) as given above,instead trigger a custom event created in RootViewController.mm as below*/

    //EventCustom customEndEvent("game_scene_close_event");
    //_eventDispatcher->dispatchEvent(&customEndEvent);


}
