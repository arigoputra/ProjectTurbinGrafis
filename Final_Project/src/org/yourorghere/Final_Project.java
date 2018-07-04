package org.yourorghere;

import java.applet.*;
import java.awt.*;
import java.io.*;
import javax.media.opengl.*;
import com.sun.opengl.util.*;
import org.yourorghere.Object;

public class Final_Project extends Applet {
  private Animator animator;

  public void init() {
    setLayout(new BorderLayout());
    GLCanvas canvas = new GLCanvas();
    canvas.addGLEventListener(new Object());
    canvas.setSize(getSize());
    add(canvas, BorderLayout.CENTER);
    animator = new FPSAnimator(canvas, 70);
  }

  public void start() {
    animator.start();
  }

  public void stop() {
    animator.stop();
  }
}
