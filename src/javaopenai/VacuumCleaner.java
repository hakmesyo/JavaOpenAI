/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaopenai;

import java.util.ArrayList;
import java.util.List;
import processing.core.PApplet;

/**
 *
 * @author elcezerilab
 */
public class VacuumCleaner extends PApplet {

    MainProgram mp;
    List<Garbage> lstGarbage = new ArrayList();
    Agent agent;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PApplet.main(new String[]{"javaopenai.VacuumCleaner"});
    }

    public void runApplet(MainProgram c) {
//        PApplet.main(new String[]{"--present","processing.Deneme"});
        PApplet.main(new String[]{"--present", "javaopenai.VacuumCleaner"});
        mp = c;
    }

    public void settings() {
        size(800, 600, P2D);
    }

    public void setup() {
        orientation(LANDSCAPE);
        hint(DISABLE_DEPTH_MASK);
        agent = new Agent(this);
    }

    public void draw() {
        background(0);
        stroke(255, 0, 0);
        //rect(mouseX,mouseY,100,100);
        Garbage.addNewGarbage(this);
        lstGarbage.forEach(e -> e.draw(this));
        stroke(0, 255, 0);
        fill(0, 255, 0);
        rect(width / 2 - 5, 0, 10, height);

        //agent.randomWalk();
        agent.swipeHorizontal();
    }

}

class Agent {

    static long prev = System.currentTimeMillis();
    static VacuumCleaner ref;
    int rx = 50, px = rx / 2, py = rx / 2;
    String location = "A";
    int delay=5;
    int dx = 5;
    int dy = 0;


    public Agent(VacuumCleaner refx) {
        ref = refx;
    }

    private void draw() {
        ref.stroke(255);
        ref.fill(150);
        ref.ellipse(px, py, rx, rx);
    }

    private void clearGarbage() {
        List<Garbage> lst_detected = new ArrayList();
        ref.lstGarbage.forEach(e -> {
            if (px-rx < e.px && px+rx > e.px && py-rx < e.py && py+rx > e.py) {
                lst_detected.add(e);
            }
        });
        for (int i = 0; i < lst_detected.size(); i++) {
            ref.lstGarbage.remove(lst_detected.get(i));
        }
    }

    public void swipeHorizontal() {
        if (System.currentTimeMillis() - prev > delay) {
            prev = System.currentTimeMillis();
            clearGarbage();
            processIt();
        }
        draw();
    }

    private void processIt() {
        if (location.equals("A")
                && px + dx + rx / 2 < ref.width / 2
                && px + dx + rx / 2 > 0
                && py + dy + rx / 2 < ref.height
                && px + dy + rx / 2 > 0) {
            px += dx;
            if (dx > 0 && (ref.width / 2) - (px + rx / 2) < rx / 2 + 5) {
                dx = -dx;
                px += dx;
                py += rx;
            }
            if (dx < 0 && px  < rx/2) {
                dx = -dx;
                px += dx;
                py += rx;
            }
            if (ref.height-(py+rx/2)<rx/2+5) {
                changeRoom();
            }
        }
        if (location.equals("B")
                && px + dx + rx / 2 < ref.width
                && px + dx + rx / 2 > ref.width/2
                && py + dy + rx / 2 < ref.height
                && px + dy + rx / 2 > 0) {
            px += dx;
            if (dx > 0 && (ref.width) - (px + rx / 2) < rx / 2 + 5) {
                dx = -dx;
                px += dx;
                py += rx;
            }
            if (dx <0 && px < ref.width/2+rx/2) {
                dx = -dx;
                px += dx;
                py += rx;
            }
            if (ref.height-(py+rx/2)<rx/2+5) {
                changeRoom();
            }
        }
    }

    public void changeRoom() {
        location = (location.equals("A")) ? "B" : "A";
        px = (location.equals("A")) ? rx / 2+10 : ref.width / 2 + rx / 2+10;
        py = rx / 2;
        dx=5;
    }

}

class Garbage {

    static long prev = System.currentTimeMillis();
    static VacuumCleaner ref;
    int px, py, rx;

    public Garbage() {
        px = (int) (Math.random() * ref.width);
        py = (int) (Math.random() * ref.height);
        rx = (int) (5 + Math.random() * 10);
    }

    public void draw(VacuumCleaner ref) {
        ref.stroke(255);
        ref.fill(150);
        ref.ellipse(px, py, rx, rx);
    }

    public static void addNewGarbage(VacuumCleaner refx) {
        ref = refx;
        if (System.currentTimeMillis() - prev > 500) {
            prev = System.currentTimeMillis();
            refx.lstGarbage.add(new Garbage());
        }
    }
    
//    public void randomWalk() {
//        int delta = 50;
//        if (System.currentTimeMillis() - prev > 100) {
//            prev = System.currentTimeMillis();
//            clearGarbage();
//            int dx = (int) (-delta / 2 + Math.random() * delta);
//            int dy = (int) (-delta / 2 + Math.random() * delta);
//            if (location.equals("A")
//                    && px + dx < ref.width / 2
//                    && px + dx > 0
//                    && py + dy < ref.height
//                    && px + dy > 0) {
//                px += dx;
//                py += dy;
//            }
//            if (location.equals("B")
//                    && px + dx < ref.width
//                    && px + dx > ref.width / 2
//                    && py + dy < ref.height
//                    && px + dy > 0) {
//                px += dx;
//                py += dy;
//            }
//        }
//        draw();
//    }
}
