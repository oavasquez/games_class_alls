//
// Created by oavasquez on 01-25-18.
//

#ifndef PROJ_ANDROID_STUDIO_MENUGAME_H
#define PROJ_ANDROID_STUDIO_MENUGAME_H
#include "cocos2d.h"


class MenuGame : public cocos2d::Layer
{
public:
    static cocos2d::Scene* createScene();
    virtual bool init();

    // a selector callback
    void menuCloseCallback(cocos2d::Ref* pSender);

    // implement the "static create()" method manually
    CREATE_FUNC(MenuGame);

    cocos2d::Sprite *mySprite;

private:
    void GoToGameScene( cocos2d::Ref *sender );



};


#endif //PROJ_ANDROID_STUDIO_MENUGAME_H



