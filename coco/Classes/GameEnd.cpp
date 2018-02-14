//
// Created by oavasquez on 01-26-18.
//

#include "GameEnd.h"
#include "MenuGame.h"
#include "HelloWorldScene.h"
#include "Definition.h"
#define WINS_SOUND            "wins-sound.mp3"
#include "SimpleAudioEngine.h"
using namespace CocosDenshion;

USING_NS_CC;


Scene* GameEnd::createScene()
{
    // 'scene' is an autorelease object
    auto scene = Scene::create();

    // 'layer' is an autorelease object
    auto layer = GameEnd::create();

    // add layer as a child to scene
    scene->addChild(layer);

    // return the scene
    return scene;
}

bool GameEnd::init()
{
    //////////////////////////////
    // 1. super init first
    if ( !Layer::init() )
    {
        return false;
    }



    Size visibleSize = Director::getInstance()->getVisibleSize();
    Vec2 origin = Director::getInstance()->getVisibleOrigin();

    auto background = DrawNode::create();
    background->drawSolidRect(origin, visibleSize, Color4F(0.3, 0.63, 1.0, 1.0));

    this->addChild(background);

    /*
    auto backgroundSprite = Sprite::create( "Background.png" );
    backgroundSprite->setPosition( Point( visibleSize.width / 2 + origin.x, visibleSize.height / 2 + origin.y ) );

    this->addChild( backgroundSprite );

    auto titleSprite = Sprite::create( "Title.png" );
    titleSprite->setPosition( Point( visibleSize.width / 2 + origin.x, visibleSize.height - titleSprite->getContentSize( ).height ) );


    this->addChild( titleSprite );


    */
    auto TitleLabel = Label::createWithTTF( "Felicidades Katya\nhas ganado un premio?", "fonts/Pacifico.ttf", visibleSize.height * 0.1 );
    TitleLabel->setColor( Color3B::WHITE );
    TitleLabel->setPosition( Point( visibleSize.width / 2 + origin.x, visibleSize.height * 0.75 + origin.y ) );

    this->addChild( TitleLabel, 10000 );

    auto gift = Sprite::create("gift.png");
    gift->setPosition(Point( Point( visibleSize.width / 2 + origin.x, visibleSize.height * 0.40 + origin.y )));
    this->addChild(gift);

    auto playItem = MenuItemImage::create( "close.png", "", CC_CALLBACK_1( GameEnd::GoToGameScene, this ) );
    playItem->setPosition( Point( visibleSize.width / 2 + origin.x, visibleSize.height * 0.10 + origin.y ) );

    auto menu = Menu::create( playItem, NULL );
    menu->setPosition( Point::ZERO );

    this->addChild( menu );




    SimpleAudioEngine::getInstance()->playBackgroundMusic(WINS_SOUND, true);

    return true;
}

void GameEnd::GoToGameScene( cocos2d::Ref *sender )
{
    auto scene = MenuGame::createScene();

    Director::getInstance( )->replaceScene( TransitionFade::create( TRANSITION_TIME, scene ) );
}


