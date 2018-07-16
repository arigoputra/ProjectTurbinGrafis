package org.yourorghere;

import java.applet.*;
import java.awt.*;
import java.io.*;
import javax.media.opengl.*;
import com.sun.opengl.util.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import org.yourorghere.Obyek;

public class Final_Project extends Applet {
  private Animator animator;

  public void init() {
      
   Frame frame = new Frame("Turbin Jet Pesawat Boeing RI 973");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Obyek());
        frame.add(canvas);
        frame.setSize(900, 720);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                new Thread(new Runnable() {
                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        frame.show();
        animator.start();
  }

  public void start() {
    animator.start();
  }

  public void stop() {
    animator.stop();
  }
}