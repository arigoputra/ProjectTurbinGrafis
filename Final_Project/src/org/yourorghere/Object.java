package org.yourorghere;

import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;
import com.sun.opengl.util.*;
import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

public class Object implements GLEventListener, MouseListener, MouseMotionListener {
  public static void main(String[] args) {
    Frame frame = new Frame("Turbin Pesawat");
    GLCanvas canvas = new GLCanvas();

    canvas.addGLEventListener(new Object());
    frame.add(canvas);
    frame.setSize(800, 600);
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

  private float view_rotx = 20.0f, view_roty = 30.0f, view_rotz = 0.0f;
  private int  tabung1, tabung2, kerucut, propeller, 
          tabung_luar, penampang_prop1, penampang_prop2, penampang_prop3, penampang_prop4, penampang_prop5,
          penampang_prop6, penampang_prop7, penampang_prop8, penampang_prop9, penampang_prop10, 
          chamber1,chamber2,chamber3,chamber4,chamber5,chamber6,chamber7,
          turbin1,turbin2,turbin3,gardan1,gardan2,gardan3, properler,penampang_prop_end,
          propeller_utm, engine_closer;
  private float angle = 0.0f;

  private int prevMouseX, prevMouseY;
  private boolean mouseRButtonDown = false;

  public void init(GLAutoDrawable drawable) {
    
    GL gl = drawable.getGL();

    System.err.println("INIT GL IS: " + gl.getClass().getName());

    System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());

    gl.setSwapInterval(1);

    float pos[] = { 5.0f, 5.0f, 10.0f, 0.0f };
    float white[] = { 1.0f, 1.1f, 1.0f, 1.2f };
    float green[] = { 0.7f, 5.8f, 0.2f, 1.0f };
    float red[] = { 0.8f, 0.1f, 0.0f, 1.0f };
    float yellow[] = { 0.4f, 1.0f, 0.0f, 8.0f };
    float blue[] = { 0.2f, 0.2f, 1.0f, 1.0f };
    

    gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, pos, 0);
   // gl.glEnable(GL.GL_CULL_FACE);
    gl.glEnable(GL.GL_LIGHTING);
    gl.glEnable(GL.GL_LIGHT0);
    gl.glEnable(GL.GL_DEPTH_TEST);
    
    /*Make the Compressor*/
    propeller = gl.glGenLists(1);
    gl.glNewList(propeller, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, white, 0);
    Propeller(gl);
    gl.glEndList();
    
    tabung1 = gl.glGenLists(1);
    gl.glNewList(tabung1, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, red, 0);
    Tabung(gl);
    gl.glEndList();
    
    penampang_prop1 = gl.glGenLists(1);
    gl.glNewList(penampang_prop1, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, blue, 0);
      Penampang_Prop(gl, 2, 5, 7, 3);
    gl.glEndList();
    
    penampang_prop2 = gl.glGenLists(1);
    gl.glNewList(penampang_prop2, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, red, 0);
      Penampang_Prop(gl, 2, 5, 7, 4);
    gl.glEndList();
    
    penampang_prop3 = gl.glGenLists(1);
    gl.glNewList(penampang_prop3, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, pos, 0);
      Penampang_Prop(gl, 2, 5, 7, 5);
    gl.glEndList();
    
    penampang_prop4 = gl.glGenLists(1);
    gl.glNewList(penampang_prop4, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, red, 0);
      Penampang_Prop(gl, 2, 5, 7, 4);
    gl.glEndList();
    
    penampang_prop5 = gl.glGenLists(1);
    gl.glNewList(penampang_prop5, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, pos, 0);
      Penampang_Prop(gl, 2, 5, 7, 5);
    gl.glEndList();
    
    penampang_prop6 = gl.glGenLists(1);
    gl.glNewList(penampang_prop6, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, red, 0);
      Penampang_Prop(gl, 2, 5, 7, 4);
    gl.glEndList();
    
    penampang_prop7 = gl.glGenLists(1);
    gl.glNewList(penampang_prop7, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, pos, 0);
      Penampang_Prop(gl, 2, 5, 7, 5);
    gl.glEndList();
    
    penampang_prop8 = gl.glGenLists(1);
    gl.glNewList(penampang_prop8, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, red, 0);
      Penampang_Prop(gl, 2, 5, 7, 4);
    gl.glEndList();
    
    penampang_prop9 = gl.glGenLists(1);
    gl.glNewList(penampang_prop9, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, pos, 0);
      Penampang_Prop(gl, 2, 5, 7, 5);
    gl.glEndList();
    
    penampang_prop10 = gl.glGenLists(1);
    gl.glNewList(penampang_prop10, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, red, 0);
      Penampang_Prop(gl, 2, 5, 7, 6);
    gl.glEndList();
    
    tabung_luar = gl.glGenLists(1);
    gl.glNewList(tabung_luar, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, white, 0);
    Tabung_luar(gl);
    gl.glEndList();
    //--------------------------------------------------------------------------
    //Caps_UTM
    kerucut = gl.glGenLists(1);
    gl.glNewList(kerucut, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, pos, 0);
    kerucut(gl);
    gl.glEndList();
    
    propeller_utm = gl.glGenLists(1);
    gl.glNewList(propeller_utm, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, green, 0);
    Propeller_quads(gl);
    gl.glEndList();
    //--------------------------------------------------------------------------
    
    //Chamber
    chamber1 = gl.glGenLists(1);
    gl.glNewList(chamber1, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, blue, 0);
      Penampang_Prop(gl, 2, 5, 7, 3);
    gl.glEndList();
    
     chamber2 = gl.glGenLists(1);
    gl.glNewList(chamber2, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, white, 0);
      Penampang_Prop(gl, 2, 5, 7, 4);
    gl.glEndList();
    
     chamber3 = gl.glGenLists(1);
    gl.glNewList(chamber3, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, red, 0);
      Penampang_Prop(gl, 2, 5, 7, 5);
    gl.glEndList();
    
     chamber4 = gl.glGenLists(1);
    gl.glNewList(chamber4, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, pos, 0);
      Penampang_Prop(gl, 2, 5, 7, 4);
    gl.glEndList();
    
     chamber5 = gl.glGenLists(1);
    gl.glNewList(chamber5, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, blue, 0);
      Penampang_Prop(gl, 2, 5, 7, 5);
    gl.glEndList();
    
     chamber6 = gl.glGenLists(1);
    gl.glNewList(chamber6, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, pos, 0);
      Penampang_Prop(gl, 2, 5, 7, 6);
    gl.glEndList();
    
     chamber7 = gl.glGenLists(1);
    gl.glNewList(chamber7, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, red, 0);
      Penampang_Prop(gl, 2, 5, 7, 7);
    gl.glEndList();
    
     engine_closer = gl.glGenLists(1);
    gl.glNewList(engine_closer, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, white, 0);
      Engine_Closer(gl);
    gl.glEndList();
    //--------------------------------------------------------------------------  
    
    //Turbin
     gardan1 = gl.glGenLists(1);
    gl.glNewList(gardan1, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, red, 0);
    Gardan1(gl);
    gl.glEndList();
    
     gardan2 = gl.glGenLists(1);
    gl.glNewList(gardan2, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, yellow, 0);
    Gardan2(gl);
    gl.glEndList();
    
     gardan3 = gl.glGenLists(1);
    gl.glNewList(gardan3, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, blue, 0);
    Gardan1(gl);
    gl.glEndList();
    //--------------------------------------------------------------------------
    
    //Prop_Back_End
     properler = gl.glGenLists(1);
    gl.glNewList(properler, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, white, 0);
    Propellers(gl);
    gl.glEndList();
    
    penampang_prop_end = gl.glGenLists(1);
    gl.glNewList(penampang_prop_end, GL.GL_COMPILE);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, red, 0);
    Penampang_Prop(gl, 2, 5, 7, 8);
    gl.glEndList();
    
    gl.glEnable(GL.GL_NORMALIZE);
                
    drawable.addMouseListener(this);
    drawable.addMouseMotionListener(this);
  }
    
  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    GL gl = drawable.getGL();

    float h = (float)height / (float)width;
            
    gl.glMatrixMode(GL.GL_PROJECTION);

    System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
    System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
    System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));
    gl.glLoadIdentity();
    gl.glFrustum(-1.0f, 1.0f, -h, h, 5.0f, 250.0f);
    gl.glMatrixMode(GL.GL_MODELVIEW);
    gl.glLoadIdentity();
    gl.glTranslatef(-3.0f, 0.0f, -150.0f);
  }

  public void display(GLAutoDrawable drawable) {
    // Turn the propeller teeth
    angle += 0.5f;

    // Get the GL corresponding to the drawable we are animating
    GL gl = drawable.getGL();

    if ((drawable instanceof GLJPanel) &&
        !((GLJPanel) drawable).isOpaque() &&
        ((GLJPanel) drawable).shouldPreserveColorBufferIfTranslucent()) {
      gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
    } else {
      gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    }
            
    gl.glPushMatrix();
    gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
    gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);
    gl.glRotatef(view_rotz, 0.0f, 0.0f, 1.0f);
            
    // Make the Compressor
    //1
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 10.0f);
    gl.glRotatef(8.0f * angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(propeller);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 9.8f);
    gl.glRotatef(8.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(penampang_prop1);
    gl.glPopMatrix();
    //2
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 9.0f);
    gl.glRotatef(8.0f * angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(propeller);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 8.8f);
    gl.glRotatef(8.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(penampang_prop2);
    gl.glPopMatrix();
    //3
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 8.0f);
    gl.glRotatef(8.0f * angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(propeller);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 7.8f);
    gl.glRotatef(8.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(penampang_prop3);
    gl.glPopMatrix();
    //4
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 7.0f);
    gl.glRotatef(8.0f * angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(propeller);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 6.8f);
    gl.glRotatef(8.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(penampang_prop4);
    gl.glPopMatrix();
    //5
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 6.0f);
    gl.glRotatef(8.0f * angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(propeller);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 5.8f);
    gl.glRotatef(8.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(penampang_prop5);
    gl.glPopMatrix();
    //6
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 5.0f);
    gl.glRotatef(8.0f * angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(propeller);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 4.8f);
    gl.glRotatef(8.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(penampang_prop6);
    gl.glPopMatrix();
    //7
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 4.0f);
    gl.glRotatef(8.0f * angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(propeller);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 3.8f);
    gl.glRotatef(8.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(penampang_prop7);
    gl.glPopMatrix();
    //8
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 3.0f);
    gl.glRotatef(8.0f * angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(propeller);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 2.8f);
    gl.glRotatef(8.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(penampang_prop8);
    gl.glPopMatrix();
    //9
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 2.0f);
    gl.glRotatef(8.0f * angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(propeller);
    gl.glPopMatrix();    
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 1.8f);
    gl.glRotatef(8.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(penampang_prop9);
    gl.glPopMatrix();
    //10
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 1.0f);
    gl.glRotatef(8.0f * angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(propeller);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 0.8f);
    gl.glRotatef(8.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(penampang_prop10);
    gl.glPopMatrix();
    // Cylinders       
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -1.5f);
    gl.glRotatef( angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(tabung1);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 0.5f);
    gl.glRotatef( angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(tabung_luar);
    gl.glPopMatrix();
    //==========================================================================
    // Chamber 
       gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 0.0f);
    gl.glRotatef( angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(chamber1);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -0.5f);
    gl.glRotatef( angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(chamber2);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -1.0f);
    gl.glRotatef( angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(chamber3);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -1.5f);
    gl.glRotatef( angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(chamber4);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -2.0f);
    gl.glRotatef( angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(chamber5);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -2.5f);
    gl.glRotatef( angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(chamber6);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -3.0f);
    gl.glRotatef( angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(chamber7);
    gl.glPopMatrix();
    //==========================================================================
    //Turbin
    //1
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -15.7f);
    gl.glRotatef(2.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(gardan1);
    gl.glPopMatrix();
    //2
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -10.0f);
    gl.glRotatef(2.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(gardan2);
    gl.glPopMatrix();
    //3
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -5.8f);
    gl.glRotatef(2.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(gardan1);
    gl.glPopMatrix();
     //Propeller_Back
     //1
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -13.0f);
    gl.glRotatef(-15.0f * angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(propeller);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -13.0f);
    gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(penampang_prop10);
    gl.glPopMatrix();
      //2
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -14.0f);
    gl.glRotatef(0.0f * angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(propeller);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -14.0f);
    gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(penampang_prop10);
    gl.glPopMatrix();
    //3
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -15.0f);
    gl.glRotatef(-15.0f * angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(propeller);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -15.0f);
    gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(penampang_prop10);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -5.0f);
    gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(engine_closer);
    gl.glPopMatrix();
    //==========================================================================
    //Caps_UTM
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 10.5f);
    gl.glRotatef( angle -9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(kerucut);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 10.5f);
    gl.glRotatef(-10.0f * angle -34.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(propeller_utm);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, 10.0f);
    gl.glRotatef(0.0f * angle -2.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(propeller_utm);
    gl.glPopMatrix();
    //==========================================================================
    //Propeller_End
    //1
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -16.0f);
    gl.glRotatef(25.0f * angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(properler);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -16.0f);
    gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(penampang_prop_end);
    gl.glPopMatrix();
    //2
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -16.8f);
    gl.glRotatef(25.0f * angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(properler);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -16.8f);
    gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(penampang_prop_end);
    gl.glPopMatrix();
    //3
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -17.6f);
    gl.glRotatef(25.0f * angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(properler);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -17.6f);
    gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(penampang_prop_end);
    gl.glPopMatrix();
    //4
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -18.4f);
    gl.glRotatef(25.0f * angle, 0.0f, 0.0f, 1.0f);
    gl.glCallList(properler);
    gl.glPopMatrix();
    
    gl.glPushMatrix();
    gl.glTranslatef(3.0f, -2.0f, -18.4f);
    gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
    gl.glCallList(penampang_prop_end);
    gl.glPopMatrix();
    //==========================================================================
    gl.glPopMatrix();
  }

  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}
 
  public static void kerucut(GL gl) {
        
        float BODY_LENGTH = 5.0f;
        float BODY_RADIUS =2.0f;
        int SLICES = 100;
        int STACKS = 100;
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();
        glu.gluCylinder(q, BODY_RADIUS, 0, BODY_LENGTH, SLICES, STACKS);
        glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS);
        gl.glTranslatef(0.0f, 0.0f, BODY_LENGTH);
        
    }

  public static void Tabung(GL gl) {
       
        float BODY_LENGTH = 10f;
        float BODY_RADIUS = 0.5f;
        int SLICES = 100;
        int STACKS = 100;
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();

       glu.gluCylinder(q, BODY_RADIUS, BODY_RADIUS, BODY_LENGTH, SLICES, STACKS);
        glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS);
        gl.glTranslatef(0.0f, 0.0f, BODY_LENGTH);
        glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS);
    }
  
  public static void Gardan1(GL gl) {
       
        float BODY_LENGTH = 8f;
        float BODY_RADIUS = 2f;
        int SLICES = 100;
        int STACKS = 100;
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();

       glu.gluCylinder(q, BODY_RADIUS, BODY_RADIUS, BODY_LENGTH, SLICES, STACKS);
        glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS);
        gl.glTranslatef(0.0f, 0.0f, BODY_LENGTH);
        glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS);
    }
  
  public static void Gardan2(GL gl) {
       
        float BODY_LENGTH = 8f;
        float BODY_RADIUS = 1f;
        int SLICES = 100;
        int STACKS = 100;
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();

       glu.gluCylinder(q, BODY_RADIUS, BODY_RADIUS, BODY_LENGTH, SLICES, STACKS);
        glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS);
        gl.glTranslatef(0.0f, 0.0f, BODY_LENGTH);
        glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS);
    }
  
  public static void Penampang_Prop(GL gl,float BODY_LENGHT,float BODY_RADIUS,int SLICES,int STACKS) {
       
        float BODY_LENGTH ;
        
        BODY_LENGTH = 0.5f;
        BODY_RADIUS = STACKS - BODY_LENGTH;
        SLICES = 100;
        STACKS = 100;
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();
      
        glu.gluCylinder(q, BODY_RADIUS, BODY_RADIUS, BODY_LENGTH, SLICES, STACKS);
        glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS);
        gl.glTranslatef(0.0f, 0.0f, BODY_LENGTH);
        glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS);
    }
  
  public static void Propeller (GL gl) {
      gl.glBegin(GL.GL_TRIANGLES);
       
        gl.glVertex3d(8, 1.5, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(8, -1.5, 0);
        gl.glVertex3d(1.5, 8, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(-1.5, 8, 0);
        gl.glVertex3d(1.5, -8, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(-1.5, -8, 0);
        gl.glVertex3d(-8,-1.5, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(-8, 1.5, 0);
       
        gl.glEnd();
       
  }
  
  public static void Tabung_luar(GL gl) {
       
        float BODY_LENGTH = 10f;
        float BODY_RADIUS = 2f;
        int SLICES = 100;
        int STACKS = 100;
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();

       glu.gluCylinder(q, BODY_RADIUS, BODY_RADIUS, BODY_LENGTH, SLICES, STACKS);
        glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS);
        gl.glTranslatef(0.0f, 0.0f, BODY_LENGTH);
        glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS);
    }
  
   public static void Propellers (GL gl) {
      gl.glBegin(GL.GL_TRIANGLES);
       
        gl.glVertex3d(12, 1.5, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(12, -1.5, 0);
        gl.glVertex3d(1.5, 12, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(-1.5, 12, 0);
        gl.glVertex3d(1.5, -12, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(-1.5, -12, 0);
        gl.glVertex3d(-12,-1.5, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(-12, 1.5, 0);
       
        gl.glEnd();
       
  }
  
  public static void Propeller_quads (GL gl) {
      gl.glBegin(GL.GL_QUADS);
       
        gl.glVertex3d(12, 1.5, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, 0, 1.5);
        gl.glVertex3d(12, -1.5, 1.5);
        
        gl.glVertex3d(1.5, 12, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, 0, 1.5);
        gl.glVertex3d(-1.5, 12, 1.5);
        
        gl.glVertex3d(1.5, -12, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, 0, 1.5);
        gl.glVertex3d(-1.5, -12, 1.5);
        
        gl.glVertex3d(-12,-1.5, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, 0, 1.5);
        gl.glVertex3d(-12, 1.5, 1.5);
       
        gl.glEnd();
  }
  
    public static void Engine_Closer(GL gl) {
       
        float BODY_LENGTH = 5.0f;
        float BODY_RADIUS =6.0f;
        int SLICES = 100;
        int STACKS = 100;
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();
        glu.gluCylinder(q, BODY_RADIUS, 3, BODY_LENGTH, SLICES, STACKS);
       // glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS);
        gl.glTranslatef(0.0f, 0.0f, BODY_LENGTH);
    }
  // Methods required for the implementation of MouseListener
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}

  public void mousePressed(MouseEvent e) {
    prevMouseX = e.getX();
    prevMouseY = e.getY();
    if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
      mouseRButtonDown = true;
    }
  }
  
    
  public void mouseReleased(MouseEvent e) {
    if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
      mouseRButtonDown = false;
    }
  }
    
  public void mouseClicked(MouseEvent e) {}
    
  // Methods required for the implementation of MouseMotionListener
  public void mouseDragged(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();
    Dimension size = e.getComponent().getSize();

    float thetaY = 360.0f * ( (float)(x-prevMouseX)/(float)size.width);
    float thetaX = 360.0f * ( (float)(prevMouseY-y)/(float)size.height);
    
    prevMouseX = x;
    prevMouseY = y;

    view_rotx += thetaX;
    view_roty += thetaY;
  }
    
  public void mouseMoved(MouseEvent e) {}
}

