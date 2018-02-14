//
// Created by oavasquez on 01-26-18.
//

#ifndef PROJ_ANDROID_STUDIO_GAMEEND_H
#define PROJ_ANDROID_STUDIO
#include "cocos2d.h"


class GameEnd : public cocos2d::Layer
{
public:
    static cocos2d::Scene* createScene();
    virtual bool init();

    // a selector callback


    // implement the "static create()" method manually
    CREATE_FUNC(GameEnd);

    cocos2d::Sprite *mySprite;

private:
    void GoToGameScene( cocos2d::Ref *sender );



};


#endif //PROJ_ANDROID_STUDIO_GAMEEND_H
