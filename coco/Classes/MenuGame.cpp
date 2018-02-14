//
// Created by oavasquez on 01-25-18.
//

#include "MenuGame.h"
#include "HelloWorldScene.h"
#include "Definition.h"
#define MENU_SOUND            "menu-sound.mp3"

#include "SimpleAudioEngine.h"
using namespace CocosDenshion;

USING_NS_CC;


Scene* MenuGame::createScene()
{
    // 'scene' is an autorelease object
    auto scene = Scene::create();

    // 'layer' is an autorelease object
    auto layer = MenuGame::create();

    // add layer as a child to scene
    scene->addChild(layer);

    // return the scene
    return scene;
}


bool MenuGame::init()
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
    auto playItem = MenuItemImage::create( "PlayButton.png", "", CC_CALLBACK_1( MenuGame::GoToGameScene, this ) );
    playItem->setPosition( Point( visibleSize.width / 2 + origin.x, visibleSize.height / 2 + origin.y-30 ) );

    auto menu = Menu::create( playItem, NULL );
    menu->setPosition( Point::ZERO );

    this->addChild( menu );

    auto TitleLabel = Label::createWithTTF( "Katya Lizeth quieres jugar?", "fonts/Pacifico.ttf", visibleSize.height * 0.1 );
    TitleLabel->setColor( Color3B::WHITE );
    TitleLabel->setPosition( Point( visibleSize.width / 2 + origin.x, visibleSize.height * 0.75 + origin.y ) );

    this->addChild( TitleLabel, 10000 );


    SimpleAudioEngine::getInstance()->preloadBackgroundMusic(MENU_SOUND);
    SimpleAudioEngine::getInstance()->playBackgroundMusic(MENU_SOUND, true);

    return true;
}

void MenuGame::GoToGameScene( cocos2d::Ref *sender )

{
    SimpleAudioEngine::getInstance()->stopBackgroundMusic();
    auto scene = HelloWorld::createScene();

    Director::getInstance( )->replaceScene( TransitionFade::create( TRANSITION_TIME, scene ) );
}

