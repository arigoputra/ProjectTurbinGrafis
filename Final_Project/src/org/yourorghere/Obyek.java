package org.yourorghere;

import java.awt.*;
import java.awt.event.*;
import javax.media.opengl.*;
import com.sun.opengl.util.*;
import com.sun.opengl.util.texture.Texture;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;

public class Obyek implements  GLEventListener, MouseListener, MouseMotionListener, KeyListener{

     class vector {

        float x;
        float y;
        float z;

        public vector(float startX, float startY, float startZ) {
            x = startX;
            y = startY;
            z = startZ;
        }
//RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
        //Vector Rotasi Buat kamera
        void vectorRotation(vector reference, float angle) {
            vector temp = reference;
            float magnitude = (float) Math.sqrt(Math.pow(temp.x, 2) + Math.pow(temp.y, 2) + Math.pow(temp.z, 2));
            temp.x = temp.x / magnitude;
            temp.y = temp.y / magnitude;
            temp.z = temp.z / magnitude;
            float dot_product = (x * temp.x) + (y * temp.y) + (z * temp.z);
            float cross_product_x = (y * temp.z) - (temp.z * z);
            float cross_product_y = -((x * temp.z) - (z * temp.x));

            float cross_product_z = (x * temp.y) - (y * temp.x);
            float last_factor_rodrigues = (float) (1 - Math.cos(Math.toRadians(angle % 360)));
            x = (float) ((x * Math.cos(Math.toRadians(angle % 360)))
                    + (cross_product_x * Math.sin(Math.toRadians(angle % 360)))
                    + (dot_product * last_factor_rodrigues * x));
            y = (float) ((this.y * Math.cos(Math.toRadians(angle % 360)))
                    + (cross_product_y * Math.sin(Math.toRadians(angle % 360)))
                    + (dot_product * last_factor_rodrigues * y));
            z = (float) ((z * Math.cos(Math.toRadians(angle % 360)))
                    + (cross_product_z * Math.sin(Math.toRadians(angle % 360)))
                    + (dot_product * last_factor_rodrigues * z));
        }
    }
    vector Sumbu_z = new vector(0f, 0f, -1f);
    vector Sumbu_x = new vector(1f, 0f, 0f);
    vector Sumbu_y = new vector(0f, 1f, 0f);

    vector depanBelakang = new vector(0f, 0f, -1f);
    vector samping = new vector(1f, 0f, 0f);
    vector vertikal = new vector(0f, 1f, 0f);
    float Cx = 0, Cy = 2.5f, Cz = 0;
    float Lx = 0, Ly = 2.5f, Lz = -20f;
    float angle_depanBelakang = 0f;
    float angle_depanBelakang2 = 0f;
    float angle_samping = 0f;
    float angle_samping2 = 0f;
    float angle_vertikal = 0f;
    float angle_vertikal2 = 0f;
    float silinderAngle = 90f;
    float silinderAngleY = 0f;
    float silinderAngleZ = 0f;

    float sudut_x = 0f;
    float sudut_x2 = 0f;

    float sudut_z = 0f;
    float sudut_z2 = 0f;

    float sudut_y = 0f;
    float sudut_y2 = 0f;
    
    boolean ori = true, silinder1, silinder2, silinder3, silinder4, silinder5, silinder6 = false,
            kamera, kamera1, kamera2, kamera3, kamera4, kamera5 = false;
//================================================Kamera===============================================   
    /*
     ini adalah metod untuk melakukan pergerakan.
     magnitude adalah besarnya gerakan sedangkan direction digunakan untuk menentukan arah.
     gunakan -1 untuk arah berlawanan dengan vektor awal
     */
//Pergerakan Keyboard===========================================================
    private void vectorMovement(vector toMove, float magnitude, float direction) {
        float speedX = toMove.x * magnitude * direction;
        float speedY = toMove.y * magnitude * direction;
        float speedZ = toMove.z * magnitude * direction;
        Cx += speedX;
        Cy += speedY;
        Cz += speedZ;
        Lx += speedX;
        Ly += speedY;
        Lz += speedZ;
    }
//=============================Kamera Rotasi==========================================
    
    private void cameraRotation(vector reference, double angle) {
        float M = (float) (Math.sqrt(Math.pow(reference.x, 2) + Math.pow(reference.y, 2) + Math.pow(reference.z, 2)));//magnitud
        float Up_x1 = reference.x / M; 
        float Up_y1 = reference.y / M; //normalisasi
        float Up_z1 = reference.z / M; //vektor patokan
        float VLx = Lx - Cx;
        float VLy = Ly - Cy;
        float VLz = Lz - Cz;
        float dot_product = (VLx * Up_x1) + (VLy * Up_y1) + (VLz * Up_z1);
        float cross_product_x = (Up_y1 * VLz) - (VLy * Up_z1);
        float cross_product_y = -((Up_x1 * VLz) - (Up_z1 * VLx));
        float cross_product_z = (Up_x1 * VLy) - (Up_y1 * VLx);
        float last_factor_rodriques = (float) (1 - Math.cos(Math.toRadians(angle % 360)));
        float Lx1 = (float) ((VLx * Math.cos(Math.toRadians(angle % 360)))
                + (cross_product_x * Math.sin(Math.toRadians(angle % 360)))
                + (dot_product * last_factor_rodriques * VLx));
        float Ly1 = (float) ((VLy * Math.cos(Math.toRadians(angle % 360)))
                + (cross_product_y * Math.sin(Math.toRadians(angle % 360)))
                + (dot_product * last_factor_rodriques * VLy));
        float Lz1 = (float) ((VLz * Math.cos(Math.toRadians(angle % 360)))
                + (cross_product_z * Math.sin(Math.toRadians(angle % 360)))
                + (dot_product * last_factor_rodriques * VLz));
        Lx = Lx1 + Cx;
        Ly = Ly1 + Cy;
        Lz = Lz1 + Cz;
    }
//NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN    
    public static void main(String[] args) {
        Frame frame = new Frame("Turbin Jet Pesawat Boeing RI 973");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Object());
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

    private float view_rotx = 20.0f, view_roty = 30.0f, view_rotz = 0.0f;
    private int tabung1, tabung2, kerucut, propeller,
                tabung_luar, penampang_prop1, penampang_prop2, penampang_prop3, penampang_prop4, penampang_prop5,
                penampang_prop6, penampang_prop7, penampang_prop8, penampang_prop9, penampang_prop10,
                chamber1, chamber2, chamber3, chamber4, chamber5, chamber6, chamber7,
                turbin1, turbin2, turbin3, gardan1, gardan2, gardan3, properler, penampang_prop_end,
                propeller_utm, engine_closer, crack1, crack2, crack3, crack4, 
                cylinder_backhorn, backhorn;
    private float angle = 0.0f;

    private int prevMouseX, prevMouseY;
    private boolean mouseRButtonDown = false;
    public void init(GLAutoDrawable drawable) {

        GL gl = drawable.getGL();

        System.err.println("INIT GL IS: " + gl.getClass().getName());

        System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());

        gl.setSwapInterval(1);

        float pos[] = {5.0f, 5.0f, 10.0f, 0.0f};
        float white[] = {1.0f, 1.1f, 1.0f, 1.2f};
        float green[] = {0.7f, 5.8f, 0.2f, 1.0f};
        float red[] = {0.8f, 0.1f, 0.0f, 1.0f};
        float yellow[] = {0.4f, 1.0f, 0.0f, 8.0f};
        float blue[] = {0.3f, 0.5f, 1.0f, 1.0f};
        float grey[] = {0.2f, 6.0f, 1.0f, 10.0f};
        
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, pos, 0);
        // gl.glEnable(GL.GL_CULL_FACE);
        gl.glEnable(GL.GL_LIGHTING);
        gl.glEnable(GL.GL_LIGHT0);
        gl.glEnable(GL.GL_DEPTH_TEST);

        /*Make the Engine*/
//Propeller Utama
        kerucut = gl.glGenLists(1);
        gl.glNewList(kerucut, GL.GL_COMPILE);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, pos, 0);
        kerucut(gl);
        gl.glEndList();

        propeller_utm = gl.glGenLists(1);
        gl.glNewList(propeller_utm, GL.GL_COMPILE);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, grey, 0);
        Propeller_quads(gl);
        gl.glEndList();
        //--------------------------------------------------------------------------
// Propeller's ke 2
        propeller = gl.glGenLists(1);
        gl.glNewList(propeller, GL.GL_COMPILE);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, white, 0);
        Propeller(gl);
        gl.glEndList();
//Dalam tabung putih
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
//Luar tabung merah
        tabung_luar = gl.glGenLists(1);
        gl.glNewList(tabung_luar, GL.GL_COMPILE);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, white, 0);
        Tabung_luar(gl);
        gl.glEndList();
        //--------------------------------------------------------------------------
        //Chamber ( Belakang Propeller's ke 2 )
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
// Penutup kerucut terbuka
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
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, blue, 0);
        Propellers(gl);
        gl.glEndList();

        penampang_prop_end = gl.glGenLists(1);
        gl.glNewList(penampang_prop_end, GL.GL_COMPILE);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, red, 0);
        Penampang_Prop(gl, 2, 5, 7, 8);
        gl.glEndList();
//Crack ( Cangkang Luar )
        crack1 = gl.glGenLists(1);
        gl.glNewList(crack1, GL.GL_COMPILE);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, white, 0);
        Crack1(gl);
        gl.glEndList();

        crack2 = gl.glGenLists(1);
        gl.glNewList(crack2, GL.GL_COMPILE);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, white, 0);
        Crack2(gl);
        gl.glEndList();
        gl.glEnable(GL.GL_NORMALIZE);

        crack3 = gl.glGenLists(1);
        gl.glNewList(crack3, GL.GL_COMPILE);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, white, 0);
        Crack3(gl);
        gl.glEndList();
        
        crack4 = gl.glGenLists(1);
        gl.glNewList(crack4, GL.GL_COMPILE);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, white, 0);
        Crack4(gl);
        gl.glEndList();
//Cylinder_backHorn        
        cylinder_backhorn = gl.glGenLists(1);
        gl.glNewList(cylinder_backhorn, GL.GL_COMPILE);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, grey, 0);
        Engine_Closer(gl);
        gl.glEndList();
//Back_Horn
        backhorn = gl.glGenLists(1);
        gl.glNewList(backhorn, GL.GL_COMPILE);
        gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, pos, 0);
        Back_Horn(gl);
        gl.glEndList();

        drawable.addMouseListener(this);
        drawable.addMouseMotionListener(this);
        //drawable.addMouseWheelListener(this);
        drawable.addKeyListener(this);
        
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();

        float h = (float) height / (float) width;

        gl.glMatrixMode(GL.GL_PROJECTION);

        System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
        System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
        System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));
        gl.glLoadIdentity();
        gl.glFrustum(-1.0f, 1.0f, -h, h, 5.0f, 350.0f);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(-3.0f, 0.0f, -200.0f);
        
    }
   
    //untuk scroll mouse
    static double x = 3;
    static double y = -5;
    static double i = 3;
    static double j = 5;

    
    public void display(GLAutoDrawable drawable) {
        // Turn the propeller teeth
        angle += 5.0f;

        // Get the GL corresponding to the drawable we are animating
        GL gl = drawable.getGL();
        
        if ((drawable instanceof GLJPanel)
                && !((GLJPanel) drawable).isOpaque()
                && ((GLJPanel) drawable).shouldPreserveColorBufferIfTranslucent()) {
            gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        } else {
            gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        }

        gl.glPushMatrix();
        gl.glRotatef(view_rotx, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(view_roty, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(view_rotz, 0.0f, 0.0f, 1.0f);

        // Membuat Propeller's 2
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
        
// Cylinders pada propeller's sub ke 2      
        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, -1.5f);
        gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(tabung1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, 0.5f);
        gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(tabung_luar);
        gl.glPopMatrix();
        //==========================================================================
// Chamber 
        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, 0.0f);
        gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(chamber1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, -0.5f);
        gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(chamber2);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, -1.0f);
        gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(chamber3);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, -1.5f);
        gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(chamber4);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, -2.0f);
        gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(chamber5);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, -2.5f);
        gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(chamber6);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, -3.0f);
        gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
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
        //Propeller_Back (Setelah Gardan )
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
//Penutup kerucut terbuka
        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, -5.0f);
        gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(engine_closer);
        gl.glPopMatrix();
        //==========================================================================
//Propeller Utama
        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, 10.5f);
        gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(kerucut);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, 11.5f);
        gl.glRotatef(-50.0f * angle -45.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(propeller_utm);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, 12.0f);
        gl.glRotatef(50.0f * angle - 0.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(propeller_utm);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, 12.0f);
        gl.glRotatef(50.0f * angle - 22.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(propeller_utm);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, 12.0f);
        gl.glRotatef(50.0f * angle - 66.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(propeller_utm);
        gl.glPopMatrix();
        //==========================================================================
//Propeller Terakhir
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
        //======================================================================
//Crack's 
        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, 7.0f);
        gl.glRotatef(0.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(crack1);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, -8.0f);
        gl.glRotatef(0.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(crack2);
        gl.glPopMatrix();

        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, -33.0f);
        gl.glRotatef(0.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(crack3);
        gl.glPopMatrix();
        
        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, -37.0f);
        gl.glRotatef(0.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(crack4);
        gl.glPopMatrix();
//Cylinder sebelum BackHorn
     //1
        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, -25.5f);
        gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(gardan1);
        gl.glPopMatrix();
     //2
        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, -30.5f);
        gl.glRotatef(angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(gardan2);
        gl.glPopMatrix();
//Cylinder BackHorn (kerucut terbuka)
        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, -34.5f);
        gl.glRotatef(0.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(cylinder_backhorn);
        gl.glPopMatrix();
//Back_Horn
        gl.glPushMatrix();
        gl.glTranslatef(3.0f, -2.0f, -43.5f);
        gl.glRotatef(0.0f * angle - 9.0f, 0.0f, 0.0f, 1.0f);
        gl.glCallList(backhorn);
        gl.glPopMatrix();

        gl.glPopMatrix();
//NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN

//RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
//----------------------------kamera -------------------------------------------
        if (kamera) {
            KeyPressed(74);
        }
        if (kamera1) {
            KeyPressed(76);
        }
        if (kamera2) {
            KeyPressed(73);
        }
        gl.glFlush();
    }
//NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN
    public static void kerucut(GL gl) {

        float BODY_LENGTH = 5.0f;
        float BODY_RADIUS = 2.0f;
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

    public static void Penampang_Prop(GL gl, float BODY_LENGHT, float BODY_RADIUS, int SLICES, int STACKS) {

        float BODY_LENGTH;

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

    public static void Propeller(GL gl) {
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
        gl.glVertex3d(-8, -1.5, 1.5);
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

   
    public static void Propellers(GL gl) {
        gl.glBegin(GL.GL_TRIANGLES);
//1
        gl.glVertex3d(12, 1.5, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(12, -1.5, 0);
        gl.glVertex3d(1.5, 12, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(-1.5, 12, 0);
        gl.glVertex3d(1.5, -12, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(-1.5, -12, 0);
        gl.glVertex3d(-12, -1.5, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(-12, 1.5, 0);

        gl.glEnd();

    }


    public static void Propeller_quads(GL gl) {

        gl.glBegin(GL.GL_QUADS);
//==============================================================================
        gl.glVertex3d(12, 1.5, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, 0, -1.5);
        gl.glVertex3d(12, -1.5, 1.5);
        
        gl.glVertex3d(1.5, 12, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, 0, -1.5);
        gl.glVertex3d(-1.5, 12, 1.5);
        
        gl.glVertex3d(1.5, -12, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, 0, -1.5);
        gl.glVertex3d(-1.5, -12, 1.5);
        
        gl.glVertex3d(-12,-1.5, 1.5);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, 0, -1.5);
        gl.glVertex3d(-12, 1.5, 1.5);

            gl.glEnd();
            
        }

    public static void Engine_Closer(GL gl) {

        float BODY_LENGTH = 5.0f;
        float BODY_RADIUS = 6.0f;
        int SLICES = 100;
        int STACKS = 100;
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();
        glu.gluCylinder(q, BODY_RADIUS, 3, BODY_LENGTH, SLICES, STACKS);
        // glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS); // Tutup
        gl.glTranslatef(0.0f, 0.0f, BODY_LENGTH);
    }
    
     public static void Back_Horn(GL gl) {

        float BODY_LENGTH = 20.0f;
        float BODY_RADIUS = 0.0f;
        int SLICES = 100;
        int STACKS = 100;
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();
        glu.gluCylinder(q, BODY_RADIUS, 8, BODY_LENGTH, SLICES, STACKS);
        glu.gluDisk(q, 0.0f, BODY_RADIUS, SLICES, STACKS);
        gl.glTranslatef(0.0f, 0.0f, BODY_LENGTH);

    }
     
//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    static void Crack1(GL gl) {
        float BODY_LENGTH = 1.0f;
        float BODY_RADIUS = 1.0f;
        int SLICES = 100;
        int STACKS = 100;
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();
        glu.gluCylinder(q, 14.8, 11.5, 8.0, SLICES, STACKS);
        gl.glTranslatef(0.0f, 0.0f, -10.0f);
    }

    static void Crack2(GL gl) {
        float BODY_LENGTH = 3.0f;
        float BODY_RADIUS = 1.0f;
        int SLICES = 100;
        int STACKS = 100;
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();
        glu.gluCylinder(q, 14.8, 14.8, 15.0, SLICES, STACKS);
        gl.glTranslatef(0.0f, 0.0f, BODY_LENGTH);
    }

    static void Crack3(GL gl) {
        float BODY_LENGTH = 2.0f;
        float BODY_RADIUS = 1.0f;
        int SLICES = 100;
        int STACKS = 100;
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();
        glu.gluCylinder(q, 8.2, 14.8, 25.0, SLICES, STACKS);
        gl.glTranslatef(0.0f, 0.0f, BODY_LENGTH);
    }
    
    static void Crack4(GL gl) {
        float BODY_LENGTH = 2.0f;
        float BODY_RADIUS = 1.0f;
        int SLICES = 100;
        int STACKS = 100;
        GLU glu = new GLU();
        GLUquadric q = glu.gluNewQuadric();
        glu.gluCylinder(q, 8.0, 8.2, 5.0, SLICES, STACKS);
        gl.glTranslatef(0.0f, 0.0f, BODY_LENGTH);
    }
    
   /*  static void setengah(GL gl) {
        
        gl.glBegin(GL.GL_QUADS);
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        for (int sudut = 180; sudut <= 360; sudut += 5) {
            double radian = sudut * Math.PI / 180;
            double nextRadian = (sudut + 5) * Math.PI / 180;
            double z = 0.4 * Math.cos(radian);
            double y = 0.4 * Math.sin(radian);
            double nextz = 0.4 * Math.cos(nextRadian);
            double nexty = 0.4 * Math.sin(nextRadian);
          
            gl.glTexCoord2d(0, 0);
            gl.glVertex3d(0, y, z);
            gl.glTexCoord2d(1, 0);
            gl.glVertex3d(1.5, y, z);
            gl.glTexCoord2d(1, 1);
            gl.glVertex3d(1.5, nexty, nextz);
            gl.glTexCoord2d(0, 1);
            gl.glVertex3d(0, nexty, nextz);
            
            
        }
        gl.glEnd();
        
    }*/
    
//RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR    
    // Methods implementasi pergerakan mouse
    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

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

    public void mouseClicked(MouseEvent e) {
    }

    // Methods Implementasi Mouse Listener
    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Dimension size = e.getComponent().getSize();

        float thetaY = 360.0f * ((float) (x - prevMouseX) / (float) size.width);
        float thetaX = 360.0f * ((float) (prevMouseY - y) / (float) size.height);

        prevMouseX = x;
        prevMouseY = y;

        view_rotx += thetaX;
        view_roty += thetaY;
    }

      public void mouseMoved(MouseEvent me) {

    }

    public void mouseWheelMoved(int button, int dir, int x, int y) {
       
        if (dir < 0) {
            //scroll atas
                j -= 1;
           
        } else {
            //scroll bawah
                j += 1;  
        }
    }
     public void keyTyped(KeyEvent e){
     }

  
    public void keyPressed(KeyEvent e){
        System.out.println(e.getKeyCode());
    }

    
    public void keyReleased(KeyEvent e){}
    
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
    
    
        void KeyPressed ( int keyCode){
//huruf W
        if (keyCode == 7) {
            vectorMovement(depanBelakang, 2.5f, 1.5f);
        } //huruf S
        else if (keyCode == 83) {
            vectorMovement(depanBelakang, 2.5f, -1.5f);
        } //huruf A
        else if (keyCode == 68) {
            vectorMovement(samping, 2.5f, 1.5f);
        } //huruf D
        else if (keyCode == 65) {
            vectorMovement(samping, 2.5f, -1.5f);
        } 

        //Tombol enter
        else if (keyCode == 10) {
            if (kamera) {
                kamera = false;
            } else {
                kamera = true;
            }
        } else if (keyCode == 81) {
            if (kamera1) {
                kamera1 = false;
            } else {
                kamera1 = true;
            }
        } else if (keyCode == 87) {
            if (kamera2) {
                kamera2 = false;
            } else {
                kamera2 = true;
            }
        }   
    }
}
