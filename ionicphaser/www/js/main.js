//var scaleRatio = window.devicePixelRatio / 3;
//var game = new Phaser.Game(350, 490, Phaser.AUTO, 'gameDiv');
//var game = new Phaser.Game(window.innerWidth, window.innerHeight , Phaser.AUTO, 'gameDiv');
var game = new Phaser.Game(380,600, Phaser.AUTO, 'gameDiv');
 console.log(window.devicePixelRatio);
 console.log(window.innerWidth);



//Add all states


//Start the first state
game.state.add('play',playState);






var bootState = {
    create: function(){
        
        game.physics.startSystem(Phaser.Physics.ARCADE);
        game.state.add('load',loadState);
        game.state.start("load");
    }
    
};


var loadState={
    preload: function(){
        var loadingLabel=game.add.text(80,150,'loandin...',{font:'30px Courier',fill:'#000000'});

        game.load.image('sky', 'assets/sky.png');
        game.load.image('ground', 'assets/platform.png');
        game.load.image('star', 'assets/star.png');
        game.load.spritesheet('dude', 'assets/dude.png', 32, 48);
        game.load.image('jumpbutton', 'assets/jump.png');
        game.load.image('playbutton', 'assets/playbutton.png');
        game.load.image('backgroundplay', 'assets/backgroundplay.png');
        game.load.spritesheet('gamepad', 'assets/gamepad/gamepad_spritesheet.png', 100, 100);
        game.load.spritesheet('starwins', 'assets/starwins.png');
      
        
     
    },
    create:function(){
        
        game.state.add('menu',menuState);
        game.state.start("menu");
        
    }
    
    
};

var menuState={
    create: function(){
        var background = game.add.tileSprite(0, 0, 600, 900, 'backgroundplay');
        var loadingLabel=game.add.text(30,150,'Mi primer juego',{font:'35px Arial',fill:'#36db39'});
        
        var accionButton = game.add.button(game.world.centerX -150, 250, 'playbutton', this.enterButton, this, 2, 1, 0);
       
       
        
    },
    enterButton: function(){
        this.star();
    }
    ,
    star: function(){
        game.state.add('play',playState);
        game.state.start("play");
        
        
    }
    
};

var platform;
var player;
var cursors;

var stars;
var score = 0;
var scoreText;


var playState={

    create:function(){

        
    
        game.add.sprite(0, 0, 'sky');

        //  The platforms group contains the ground and the 2 ledges we can jump on
        platforms = game.add.group();

        //  We will enable physics for any object that is created in this group
        platforms.enableBody = true;

        // Here we create the ground.
        var ground = platforms.create(0, game.world.height - 150, 'ground');

        //  Scale it to fit the width of the game (the original sprite is 400x32 in size)
        ground.scale.setTo(2, 5);

        //  This stops it from falling away when you jump on it
        ground.body.immovable = true;

        //  Now let's create two ledges
        var ledge = platforms.create(400, 400, 'ground');
        ledge.body.immovable = true;

        ledge = platforms.create(-150, 250, 'ground');
        ledge.body.immovable = true;

        // The player and its settings
        player = game.add.sprite(32, game.world.height - 250, 'dude');

        //  We need to enable physics on the player
        game.physics.arcade.enable(player);

        //  Player physics properties. Give the little guy a slight bounce.
        player.body.bounce.y = 0.2;
        player.body.gravity.y = 300;
        player.body.collideWorldBounds = true;

        //  Our two animations, walking left and right.
        player.animations.add('left', [0, 1, 2, 3], 10, true);
        player.animations.add('right', [5, 6, 7, 8], 10, true);

        //  Finally some stars to collect
        stars = game.add.group();

        //  We will enable physics for any star that is created in this group
        stars.enableBody = true;

        //  Here we'll create 12 of them evenly spaced apart
        for (var i = 0; i < 5; i++)
        {
            //  Create a star inside of the 'stars' group
            var star = stars.create(i * 70, 0, 'star');

            //  Let gravity do its thing
            star.body.gravity.y = 300;

            //  This just gives each star a slightly random bounce value
            star.body.bounce.y = 0.7 + Math.random() * 0.2;
        }

        //  The score
        scoreText = game.add.text(16, 16, 'score: 0', { fontSize: '32px', fill: '#000' });

        
        this.gamepad = this.game.plugins.add(Phaser.Plugin.VirtualGamepad);
        
        // Add a joystick to the game (only one is allowed right now)
        this.joystick = this.gamepad.addJoystick(100, 510, 1.2, 'gamepad');
        
        
        // Add a button to the game (only one is allowed right now)
        this.button = this.gamepad.addButton(280, 510, 1.0, 'gamepad');
        
        
    },
    update: function(){
        var hitPlatform = game.physics.arcade.collide(player, platforms);
        game.physics.arcade.collide(stars, platforms);

        //  Checks to see if the player overlaps with any of the stars, if he does call the collectStar function
        game.physics.arcade.overlap(player, stars, this.collectStar, null, this);

        //  Reset the players velocity (movement)
        player.body.velocity.x = 0;


        if (this.joystick.properties.inUse) {
            if(this.joystick.properties.up){

            } 
            if(this.joystick.properties.down){

            } 
            if(this.joystick.properties.left){
                player.body.velocity.x = -150;

                player.animations.play('left');

            }
            if(this.joystick.properties.right){
                player.body.velocity.x = 150;

                player.animations.play('right');

            }
           
        }else{
            player.animations.stop();

            player.frame = 4;
        } 
     
        
        if (this.button.isDown && player.body.touching.down && hitPlatform){
            player.body.velocity.y = -350;
        }

        if(stars.countLiving()==0){
            game.state.add('wins', winsState);
            game.state.start("wins");

        }  
    },
    collectStar: function(player, star) {
        
        // Removes the star from the screen
        star.kill();
        
        //  Add and update the score
        score += 10;
        scoreText.text = 'Score: ' + score;
       
    
    }
    
    
    
};

var winsState={
    create: function(){
        var background = game.add.tileSprite(0, 0, 600, 900, 'backgroundplay');
        game.add.sprite(60, 100, 'starwins');
        var loadingLabel=game.add.text(120,210,'Ganaste',{font:'35px Arial',fill:'#ffffff'});
        var accionButton = game.add.button(game.world.centerX -150, 350, 'playbutton', this.enterButton, this, 2, 1, 0);
       

    },
    enterButton: function(){
        this.star();
    }
    ,
    star: function(){
        game.state.add('play',playState);
        game.state.start("play");
        
        
    }
};


game.state.add('boot', bootState);
game.state.start("boot");
