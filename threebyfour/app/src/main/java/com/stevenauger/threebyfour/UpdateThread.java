package com.stevenauger.threebyfour;

/**
 * Created by Steven on 11-10-2014.
 */
public class UpdateThread implements Runnable {
    private boolean running;
    public UpdateThread()
    {
        running = true;
    }


    @Override
    public void run() {

        while(running) {
            GameObject inst = GameObject.getInstance();
            inst.Update();
        }
    }
    public void StopRunning()
    {
        running = false;
    }
}
