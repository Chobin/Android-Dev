package com.stevenauger.threebyfour;

import java.util.ArrayList;

/**
 * Created by Steven on 12-10-2014.
 */
public class GameObject {

    private static GameObject instance;
    private ArrayList<GLDrawnComponent> drawnComponents;
    private GLTouchComponent glTouch;

    private GameObject(){
        drawnComponents = new ArrayList<GLDrawnComponent>();

    }

    public static synchronized GameObject getInstance(){
        if(instance == null){
            instance = new GameObject();
        }
        return instance;
    }
    //hold a list of all objects

    //
    public void AddDrawnComponent(GLDrawnComponent component)
    {
        drawnComponents.add(component);
    }


    public void Draw()
    {
        //draw all objects
        for (GLDrawnComponent drawnComponent : drawnComponents) {
            drawnComponent.draw();
        }

    }

    public void Update()
    {
        //update game logic
    }

}
