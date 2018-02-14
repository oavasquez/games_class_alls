#ifndef __HELLOWORLD_SCENE_H__
#define __HELLOWORLD_SCENE_H__

#include "cocos2d.h"
using namespace cocos2d;



class HelloWorld : public cocos2d::Scene
{

private:
    Sprite* _player;
    int _monstersDestroyed;
    unsigned  score;
    cocos2d::Label *scoreLabel;
    cocos2d::Label *mensajeLabel;
    void GoToGameScene( cocos2d::Ref *sender );




public:
    static cocos2d::Scene* createScene();

    virtual bool init();
    
    // a selector callback
    void menuCloseCallback(cocos2d::Ref* pSender);
    void addMonster (float dt);
    void addHeart(float dt);



    bool onTouchBegan(Touch *touch, Event *unused_event);
    bool onContactBegan(PhysicsContact &contact);
    
    // implement the "static create()" method manually
    CREATE_FUNC(HelloWorld);
};

#endif // __HELLOWORLD_SCENE_H__
