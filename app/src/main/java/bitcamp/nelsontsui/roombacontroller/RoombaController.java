package bitcamp.nelsontsui.roombacontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class RoombaController extends AppCompatActivity {

    private boolean touchUp;
    private boolean touchDown;
    private boolean touchLeft;
    private boolean touchRight;

    RoombaClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roomba_controller);

        Log.v("-- ", "onCreate");

        initClient();
        initTouchVariables();
        controlMovement();
    }

    private void initClient(){
        Log.v("-- ", "in initClient method");
        client = new RoombaClient();
        client.execute();
    }

    private void initTouchVariables(){
        touchUp = false;
        touchDown = false;
        touchLeft = false;
        touchRight = false;

        //System.out.println("Reset all touch variables");
    }

    private void controlMovement(){
        View camera = findViewById(R.id.camera);

        camera.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // ... Respond to touch events

                //Log.v("Touching screen ", "Touching");

                int action = event.getActionMasked();

                int cw = v.getWidth();  //width of the videoview
                int ch = v.getHeight(); //height of the videoview
                float x = event.getX(); //x of where you tapped
                float y = event.getY(); //y of where you tapped

                int vd = ch / 3; //vertical difference
                int hd = cw / 2; //horizontal difference

                if (action != MotionEvent.ACTION_UP) {
                    if (y >= 0 && y <= vd) {
                        //top button -- (between 0 and 0+vd)
                        //System.out.println("Forward(upward) button");
                        //Log.v("Movement ", "Up/Forward");
                        initTouchVariables();
                        touchUp = true;
                        touchDown = false;

                    } else if (y > vd && y <= 2 * vd) {
                        //left or right button
                        if (x >= 0 && x <= hd) {
                            //System.out.println("Left button");
                            //Log.v("Movement ", "Left");
                            touchLeft = true;
                            touchRight = false;

                        } else if (x > hd && x <= cw) {
                            //System.out.println("Right button");
                            //Log.v("Movement ", "Right");
                            touchLeft = false;
                            touchRight = true;

                        } else {
                            System.out.println("Invalid tap! [x-value invalid]");
                            initTouchVariables();
                        }

                    } else if (y > 2 * vd && y <= ch) {
                        //bottom button
                        //System.out.println("Back(downward) button");
                        //Log.v("Movement ", "Down");
                        initTouchVariables();
                        touchUp = false;
                        touchDown = true;

                    } else {
                        System.out.println("Invalid tap! [y-value invalid]");
                        initTouchVariables();
                    }
                } else {
                    //make everything false

                    initTouchVariables();
                    System.out.println("Touch release");
                }

                //reset all booleans
                client.resetFromUser();

                //execute based on touch variables
                if (touchUp) {
                    Log.v("Movement ", "Up/Forward");
                    client.setFromUser(RoombaClient.FORWARD, true);
                }
                if (touchDown) {
                    Log.v("Movement ", "Down/Backward");
                    client.setFromUser(RoombaClient.BACK, true);
                }
                if (touchLeft) {
                    Log.v("Movement ", "Rotate Left");
                    client.setFromUser(RoombaClient.LEFT, true);
                }
                if (touchRight) {
                    Log.v("Movement ", "Rotate Right");
                    client.setFromUser(RoombaClient.RIGHT, true);
                }


                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_roomba_controller, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
