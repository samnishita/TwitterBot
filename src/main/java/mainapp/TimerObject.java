/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainapp;

import java.util.Timer;

/**
 *
 * @author samnishita
 */
public class TimerObject {
     Timer timer;
    
    public TimerObject(){
        timer = new Timer();
    }
    
    public Timer getTimer(){
        return timer;
    }
    public Timer createTimer(){
        return timer = new Timer();
    }
}
