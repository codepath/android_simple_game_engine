android_simple_game_engine
==========================

Simple 2D Android Game Framework.

## Usage

Change the "main" game activity to extend from `GameActivity`:

```java
public class MyGameActivity extends GameActivity {
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyGamePanel panel = new MyGamePanel(this);
	 	setContentView(panel);
	}

}
```

### GamePanel

Create the game panel which will manage the various aspects of your game (input => update state => render):

```java
public class MyGamePanel extends AbstractGamePanel {
	public MyGamePanel(Context context) {
		super(context);
	}

	// This method is called when the game first launches. Use this to
	// initialize variables and set starting values.
	public void onStart() {
		// Initialize your game
	}

	// This method is called once a "tick" to redraw the canvas,
	// so you can do things like draw the game actors.
	public void redrawCanvas(Canvas canvas) {
		// Draw things here using the canvas
	}

	// This method is called once a "tick", and it is a good place to
	// implement the game logic.
	public void onTimer() {
		// Game Logic Here
	}

	// This method is called whenever a keyboard button is pressed
	// within your game. The keyCode represents the key the actual key pushed.
	// You can check which keyCode using 'KeyEvent' constants.
	// i.e keyCode == KeyEvent.KEYCODE_N
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("GameActivity", keyCode);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("GameActivity", event.getX() + ", " + event.getY());
    return true;
	}
}
```

### Creating your Actors

Anything object nside a game world that behaviors or logic is called an Actor. A player or an enemy or a ship, et al.
To create an actor, simply create a class extending from one of the actor base types. 
There are a few base types available for an Actor:

 * `Actor` - Simplest type which provides "enabled" and "visible" properties as well as a few key methods.
 * `SimpleMovingActor` - Basic actor with x+y coordinates, width and height and velocity supported.
 * `SpriteMovingActor` - Extends from `SimpleMovingActor`, bitmap based (rather than painted) actors.
 * `AnimatedMovingActor`- Extends from `SpriteMovingActor`, animated (multiple frame) sprite based actors.
 
Creating the actor just looks like:

```java
public class HeroActor extends SimpleMovingActor {
	public HeroActor(int x, int y) {
    // x, y and then width,height of actor
		super(x, y, 25, 25);
	}
  
  // Setup the color and style of the "paint" for the actor.
	@Override
	public void stylePaint(Paint paint) {
		paint.setColor(Color.GREEN);
		paint.setStyle(Paint.Style.FILL);
		// p.setStyle(Paint.Style.STROKE);
		// p.setStrokeWidth(5);
	}
  
  // Specify how to draw the actor on the panel
  // getRect() returns the rectangle containing the actor
  // getPaint() returns the primary paint to use when drawing the actor
	@Override
	public void draw(Canvas canvas) {
	  canvas.drawRect(getRect(), getPaint());
	}
}
```

You can then add an actor to the panel:

```java
public class MyGamePanel extends AbstractGamePanel {

  HeroActor player;

	public MyGamePanel(Context context) {
		super(context);
	}

	// This method is called when the game first launches. Use this to
	// initialize variables and set starting values.
	public void onStart() {
    // sets initial position of actor
		player = new HeroActor(50, 50);
	}

	// This method is called once a "tick" to redraw the canvas,
	// so you can do things like draw the game actors.
	public void redrawCanvas(Canvas canvas) {
		player.draw(canvas);
	}

	// This method is called once a "tick", and it is a good place to
	// implement the game logic.
	public void onTimer() {
		if (player.something()) {
      // do something
    } else {
      // something else
    }
	}

}
```

Now the actor will be painted on the board.
