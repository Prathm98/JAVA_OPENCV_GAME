import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
public class wall extends Thread implements KeyListener{
    Mat matrix = null;
    String file;
    int i=3;
    Mat image;
    Image temp = null;

    JFrame f = new JFrame("Wall Game");
    JLabel scr;
    JButton player,but,obst1,back,start,exit,about,help,scoreboard,go,noobject,highscore,back1,helpback,newgame,pause,resume;
    int count=0,pos=1,count1=0,mov=0,p1=0,p2=0,first=0,obst=0,distance=0,x=725,position=2,score=0,over=0,objdet=0,wait=0,time=100;
    public wall() {
        f.setSize(1250, 700);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setLayout(null);

        //For no object
        noobject = new JButton();
        noobject.setBounds(170,220,900,200);
        noobject.setVisible(false);
        noobject.setContentAreaFilled(false);
        noobject.setBorder(BorderFactory.createEmptyBorder());
        Image noobj= Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/nocamera.png"));
        noobject.setIcon(new ImageIcon(noobj));
        f.add(noobject);

        //FOR SCORE
        scr = new JLabel("0");
        scr.setBounds(900,280,350,45);
        scr.setFont(new Font("arial", Font.BOLD, 50));
        scr.setHorizontalAlignment(SwingConstants.CENTER);
        scr.setForeground(Color.WHITE);
        scr.setVisible(false);
        f.add(scr);

        //For high score
        highscore = new JButton();
        highscore.setBounds(250,50,400,100);
        highscore.setVisible(false);
        highscore.setContentAreaFilled(false);
        highscore.setBorder(BorderFactory.createEmptyBorder());
        Image hgc= Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/highscore.png"));
        highscore.setIcon(new ImageIcon(hgc));
        f.add(highscore);

        //FOR GAMEOVER
        go = new JButton();
        go.setBounds(0, 0, 900, 700);
        go.setContentAreaFilled(false);
        go.setBorder(BorderFactory.createEmptyBorder());
        Image go1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/go.png"));
        go.setIcon(new ImageIcon(go1));
        go.setVisible(false);
        f.add(go);

        //FOR PLAYER
        player = new JButton();
        player.setBounds(700, 500, 150, 150);
        player.setContentAreaFilled(false);
        player.setBorder(BorderFactory.createEmptyBorder());
        Image st3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("player/right/0.png"));
        player.setIcon(new ImageIcon(st3));
        player.setVisible(false);
        f.add(player);

        //FOR OBSTACLE
        obst1 = new JButton();
        obst1.setBounds(75, 200, 100, 100);
        obst1.setContentAreaFilled(false);
        obst1.setBorder(BorderFactory.createEmptyBorder());
        Image pl1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/fire.png"));
        obst1.setIcon(new ImageIcon(pl1));
        obst1.setVisible(false);
        f.add(obst1);

        //FOR BACKGROUND
        but = new JButton();
        but.setBounds(0, 0, 900, 700);
        but.setContentAreaFilled(false);
        but.setBorder(BorderFactory.createEmptyBorder());
        Image st = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("loc/loc1/0.png"));
        but.setIcon(new ImageIcon(st));
        but.setVisible(false);
        f.add(but);

        //FOR RESUME BUTTON
        resume = new JButton();
        resume.setBounds(950, 360, 200, 90);
        resume.setContentAreaFilled(false);
        resume.setBorder(BorderFactory.createEmptyBorder());
        Image rsm = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/resume1.png"));
        resume.setIcon(new ImageIcon(rsm));
        resume.setVisible(false);
        f.add(resume);

        //FOR PAUSE BUTTON
        pause = new JButton();
        pause.setBounds(950, 360, 200, 90);
        pause.setContentAreaFilled(false);
        pause.setBorder(BorderFactory.createEmptyBorder());
        Image pus = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/pause1.png"));
        pause.setIcon(new ImageIcon(pus));
        pause.setVisible(false);
        f.add(pause);

        //FOR NEWGAME BUTTON
        newgame = new JButton();
        newgame.setBounds(950, 460, 200, 90);
        newgame.setContentAreaFilled(false);
        newgame.setBorder(BorderFactory.createEmptyBorder());
        Image ngm = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/newgame1.png"));
        newgame.setIcon(new ImageIcon(ngm));
        newgame.setVisible(false);
        f.add(newgame);

        //FOR START BUTTON
        start = new JButton();
        start.setBounds(850, 120, 200, 90);
        start.setContentAreaFilled(false);
        start.setBorder(BorderFactory.createEmptyBorder());
        Image str = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/start.png"));
        start.setIcon(new ImageIcon(str));
        f.add(start);

        //FOR ABOUT BUTTON
        about = new JButton();
        about.setBounds(850, 230, 200, 90);
        about.setContentAreaFilled(false);
        about.setBorder(BorderFactory.createEmptyBorder());
        Image abt = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/about.png"));
        about.setIcon(new ImageIcon(abt));
        f.add(about);

        //FOR HELP BUTTON
        help = new JButton();
        help.setBounds(850, 340, 200, 90);
        help.setContentAreaFilled(false);
        help.setBorder(BorderFactory.createEmptyBorder());
        Image hlp = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/help.png"));
        help.setIcon(new ImageIcon(hlp));
//        f.add(help);

        //FOR EXIT BUTTON
        exit = new JButton();
        exit.setBounds(850, 340, 200, 90);
        exit.setContentAreaFilled(false);
        exit.setBorder(BorderFactory.createEmptyBorder());
        Image ext = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/exit.png"));
        exit.setIcon(new ImageIcon(ext));
        f.add(exit);

        //FOR scoreboard
        scoreboard = new JButton();
        scoreboard.setBounds(900, 0, 350, 700);
        scoreboard.setContentAreaFilled(false);
        scoreboard.setBorder(BorderFactory.createEmptyBorder());
        Image scrb = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/scoreboard2.png"));
        scoreboard.setIcon(new ImageIcon(scrb));
        scoreboard.setVisible(false);
        f.add(scoreboard);

        //BACK button for about
        helpback= new JButton();
        helpback.setBounds(20,10,200,80);
        helpback.setVisible(false);
        helpback.setContentAreaFilled(false);
        helpback.setBorder(BorderFactory.createEmptyBorder());
        Image hlpb= Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/back.png"));
        helpback.setIcon(new ImageIcon(hlpb));
        f.add(helpback);

        //FOR ABOUT BACKGROUND
        back1 = new JButton();
        back1.setBounds(0, 0, 1250, 700);
        back1.setContentAreaFilled(false);
        back1.setBorder(BorderFactory.createEmptyBorder());
        Image bck = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/aboutinfo.png"));
        back1.setIcon(new ImageIcon(bck));
        back1.setVisible(false);
        f.add(back1);

        //FOR HOMEPAGE BACKGROUND
        back = new JButton();
        back.setBounds(0, 0, 1250, 700);
        back.setContentAreaFilled(false);
        back.setBorder(BorderFactory.createEmptyBorder());
        Image bck1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/main.png"));
        back.setIcon(new ImageIcon(bck1));
        f.add(back);

        //FOR FRAME ICON
        Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("data/icon.png"));
        f.setIconImage(img);

        but.addKeyListener(this);
        exit.addActionListener(new action());
        start.addActionListener(new action());
        helpback.addActionListener(new action());
        about.addActionListener(new action());
        newgame.addActionListener(new action());
        pause.addActionListener(new action());
        resume.addActionListener(new action());
    }
    class action implements ActionListener {
        public void actionPerformed(ActionEvent e){
            if (e.getSource() == start) {
                start.setVisible(false);
                help.setVisible(false);
                about.setVisible(false);
                newgame.setVisible(true);
                but.setVisible(true);
                exit.setBounds(960,560,200,90);
                scoreboard.setVisible(true);
                pause.setVisible(true);
                player.setVisible(true);
                obst1.setVisible(true);
                scr.setVisible(true);
                car.start();
                detect.start();
            }
            if (e.getSource() == about) {
                start.setVisible(false);
                help.setVisible(false);
                about.setVisible(false);
                exit.setVisible(false);
                Image bck = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/aboutinfo.png"));
                back1.setVisible(true);
                helpback.setVisible(true);
                back1.setIcon(new ImageIcon(bck));
            }
            if (e.getSource() == helpback) {
                start.setVisible(true);
                help.setVisible(true);
                about.setVisible(true);
                exit.setVisible(true);
                back1.setVisible(false);
                helpback.setVisible(false);
            }
            if (e.getSource() == pause) {
                over = 2;
                pause.setVisible(false);
                resume.setVisible(true);
            }
            if (e.getSource() == resume) {
                over = 0;
                pause.setVisible(true);
                resume.setVisible(false);
            }
            if (e.getSource() == newgame) {
                if(over == 1){
                    count=0;pos=1;count1=0;mov=0;p1=0;p2=0;first=0;obst=0;distance=0;x=725;position=2;score=0;over=0;wait=0;time=100;
                    go.setVisible(false);
                    highscore.setVisible(false);
                }else{
                    over = 2;
                    int confirm = JOptionPane.showOptionDialog(f, "Your all progress will be lost!!\nDo you want to start new game??", "Start a New game?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (confirm == JOptionPane.YES_OPTION) {
                        count=0;pos=1;count1=0;mov=0;p1=0;p2=0;first=0;obst=0;distance=0;x=725;position=2;score=0;over=0;wait=0;time=100;
                        go.setVisible(false);
                        highscore.setVisible(false);
                    }else{
                        over = 0;
                    }
                }
            }

//            if (e.getSource() == help) {}
            if (e.getSource() == exit) {
                over = 2;
                int confirm = JOptionPane.showOptionDialog(f, "Do you want to exit?", "Wants to exit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }else{
                    over = 0;
                }
            }
        }
    }
        Thread car= new Thread(){
            public void run() {
                while (true) {
                    if(objdet == 0) {
                        wait++;
                        try {
                            Thread.sleep(100);
                        }catch (Exception e){}
                        if(wait == 100) {
                            noobject.setVisible(true);
                        }
                        continue;
                    }
                    noobject.setVisible(false);

                    if(over == 0) {
                        count++;
                        count1++;
                        distance += 9;
                    }
                    if (count == 24)
                        count = 0;
                    if (count1 == 13)
                        count1 = 0;
                    if(count%3==0) {
                        if(over == 0) {
                            if(count%3 == 0)
                                score++;
                        }
                        scr.setText(""+score);
                    }
                    if(score>50 && score<80) time = 90;
                    if(score>100 && score<120) time = 80;
                    if(score>120 && score<150) time = 65;
                    if(score>150 && score<230) time = 50;
                    if(score>230 && score<300) time = 35;
                    if(score>300) time = 25;
                    if (distance > 705) {
                        obst1.setVisible(true);
                        distance = -99;
                        Random r = new Random();
                        if(r.nextInt(10)%2 != 0){
                            x=75;
                            position = 1;
                        }else{
                            x=725;
                            position = 2;
                        }
                    }

                    if(distance >430 && distance < 530 ){
                        if((position == 1 && pos == 2 && (mov==0||mov==1) && p1==1)||(position == 2 && pos == 1 && (mov==0||mov==1) && p2==1)) {

                            try{
                                File f = new File("temp\\data\\scr.txt");
                                if(f.exists()){
                                    FileInputStream f1 = new FileInputStream(f);
                                    BufferedInputStream b1 = new BufferedInputStream(f1);
                                    int i,t=0;
                                    while((i = b1.read()) != -1){
                                        if((char)i == 'a'){t += 0;}
                                        if((char)i == 'b'){t += 1;}
                                        if((char)i == 'c'){t += 2;}
                                        if((char)i == 'd'){t += 3;}
                                        if((char)i == 'e'){t += 4;}
                                        if((char)i == 'f'){t += 5;}
                                        if((char)i == 'g'){t += 6;}
                                        if((char)i == 'h'){t += 7;}
                                        if((char)i == 'i'){t += 8;}
                                        if((char)i == 'j'){t += 9;}
                                        t*=10;
                                    }
                                    String s = String.valueOf(t/10);
                                    StringBuilder sb=new StringBuilder(s);
                                    s = sb.reverse().toString();
                                    t = Integer.parseInt(s);
//                                System.out.print(t);
                                    if(t<score || t==0) {
                                        highscore.setVisible(true);
                                        FileOutputStream f2 = new FileOutputStream("temp\\data\\scr.txt");
                                        int score1 = score;
                                        while(score1>=1){
                                            if(score1%10 == 0) f2.write('a');
                                            if(score1%10 == 1) f2.write('b');
                                            if(score1%10 == 2) f2.write('c');
                                            if(score1%10 == 3) f2.write('d');
                                            if(score1%10 == 4) f2.write('e');
                                            if(score1%10 == 5) f2.write('f');
                                            if(score1%10 == 6) f2.write('g');
                                            if(score1%10 == 7) f2.write('h');
                                            if(score1%10 == 8) f2.write('i');
                                            if(score1%10 == 9) f2.write('j');
                                            score1/=10;
                                        }
                                    }
                                }else {
                                    try{
                                        File f1 = new File("temp\\data\\scr.txt");
                                        f1.createNewFile();
                                    }catch(Exception e){}
                                    highscore.setVisible(true);
                                    FileOutputStream f2 = new FileOutputStream("temp\\data\\scr.txt");
                                    int score1 = score;
                                    while(score1>=1){
                                        if(score1%10 == 0) f2.write('a');
                                        if(score1%10 == 1) f2.write('b');
                                        if(score1%10 == 2) f2.write('c');
                                        if(score1%10 == 3) f2.write('d');
                                        if(score1%10 == 4) f2.write('e');
                                        if(score1%10 == 5) f2.write('f');
                                        if(score1%10 == 6) f2.write('g');
                                        if(score1%10 == 7) f2.write('h');
                                        if(score1%10 == 8) f2.write('i');
                                        if(score1%10 == 9) f2.write('j');
                                        score1/=10;
                                    }
                                }
                            }catch(Exception e){System.out.println(e);}

                            over = 1;
                            new filedlete().deletefile();
                            go.setVisible(true);
                        }
                    }

//                    System.out.println(position+" "+pos+" "+mov);
                    //FOR BACKGROUND MOVEMENT
                    Image abt1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("loc/loc1/" + count + ".png"));
                    but.setIcon(new ImageIcon(abt1));

                    //FOR MOVEMENT OF OBSTACLES
                    Image pl1;

                    obst1.setBounds(x,distance,100,100);
                    if(distance%2==0) {
                        pl1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/fire.png"));
                        obst1.setIcon(new ImageIcon(pl1));
                    }else{
                        pl1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("data/fire1.png"));
                        obst1.setIcon(new ImageIcon(pl1));
                    }

                    //FOR MOVEMENT OF PLAYER EITHER ON LEFT OR RIGHT
                    Image abt;
                    if(pos==2){
                        if(p1==0) {
                            p2=0;
                            if (mov == 6) {
                                player.setBounds(50, 500, 150, 150);
                                p1=1;mov=0;
                            }
                            if (mov == 5) {
                                player.setBounds(150, 500, 150, 150);
                                mov=6;
                            }
                            if (mov == 4) {
                                player.setBounds(250, 500, 150, 150);
                                mov=5;
                            }
                            if (mov == 3) {
                                player.setBounds(350, 500, 150, 150);
                                mov=4;
                            }
                            if (mov == 2) {
                                player.setBounds(450, 500, 150, 150);
                                mov=3;
                            }
                            if (mov == 1) {
                                player.setBounds(550, 500, 150, 150);
                                mov=2;
                            }
                        }
                        //FOR RUNNING OF PLAYER
                        abt = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("player/left/" + count1 + ".png"));
                        player.setIcon(new ImageIcon(abt));
                    }else{
                        if(pos==1) {
                            if(p2==0) {
                                p1=0;
                                if (mov == 6) {
                                    player.setBounds(700, 500, 150, 150);
                                    p2=1;mov=0;
                                }
                                if (mov == 5) {
                                    player.setBounds(600, 500, 150, 150);
                                    mov=6;
                                }
                                if (mov == 4) {
                                    player.setBounds(500, 500, 150, 150);
                                    mov=5;
                                }
                                if (mov == 3) {
                                    player.setBounds(400, 500, 150, 150);
                                    mov=4;
                                }
                                if (mov == 2) {
                                    player.setBounds(300, 500, 150, 150);
                                    mov=3;
                                }
                                if (mov == 1) {
                                    player.setBounds(200, 500, 150, 150);
                                    mov=2;
                                }
                            }
                        }
                        //FOR RUNNING OF PLAYER
                        abt = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("player/right/" + count1 + ".png"));
                        player.setIcon(new ImageIcon(abt));

                    }
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                    }
                }
            }
        };
//        car.start();
        Thread detect= new Thread(){
            public void run() {
                while (true) {
                    matrix = new Mat();
                    try {
                        VideoCapture capture = new VideoCapture(0);
                        capture.read(matrix);
                        if (capture.isOpened()) {
                            if (capture.read(matrix)) {
                                BufferedImage image = new BufferedImage(matrix.width(), matrix.height(), BufferedImage.TYPE_3BYTE_BGR);
                                WritableRaster raster = image.getRaster();
                                DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
                                byte[] data = dataBuffer.getData();
                                matrix.get(0, 0, data);
                            }
                        }
                    } catch (Exception e) {
//                        System.out.println("From thread : " + e);
                    }
                    String file;
                    file = "temp//1.jpg";
                    Imgcodecs imageCodecs = new Imgcodecs();
                    imageCodecs.imwrite(file, matrix);
                    file = "temp//1.jpg";
                    image = Imgcodecs.imread(file);
                    Mat dst = new Mat();
                    Imgproc.medianBlur(image, dst, 15);
                    Imgcodecs.imwrite("temp//2.jpg", dst);
                    Mat dst1 = Imgcodecs.imread("temp//2.jpg");
                    Mat im = new Mat();
                    Imgproc.medianBlur(dst1, im, 3);
                    Mat gray = new Mat(im.rows(), im.cols(), CvType.CV_8SC1);
                    try {
                        Imgproc.cvtColor(im, gray, Imgproc.COLOR_RGB2GRAY);
                    }catch(Exception e){}
                    Mat circles = new Mat();
                    try {
                        Imgproc.HoughCircles(gray, circles, Imgproc.CV_HOUGH_GRADIENT, 2, 10, 160, 50, 0, 0);
                    }catch(Exception e){}
                    org.opencv.core.Point pt = new Point();
                    for (int i = 0; i < circles.cols(); i++) {
                        objdet = 1;
                        double data[] = circles.get(0, i);
                        pt.x = data[0];
                        pt.y = data[1];
                        if (pt.x > 250 ) {
                            if(over == 0) {
                                if (first == 0) {
                                    pos = 2;
                                    mov = 1;
                                    first = 1;
                                } else {
                                    if ((pos == 2 && p1 == 1) || (pos == 1 && p2 == 1)) {
                                        pos = 2;
                                        mov = 1;
                                    }
                                }
                            }
                        } else {
                            if(over == 0) {
                                if (first == 0) {
                                    pos = 1;
                                    mov = 1;
                                    first = 1;
                                } else {
                                    if ((pos == 2 && p1 == 1) || (pos == 1 && p2 == 1)) {
                                        pos = 1;
                                        mov = 1;
                                    }
                                }
                            }
                        }
                        double rho = data[2];
                        Imgproc.circle(im, pt, (int) rho, new Scalar(0, 200, 0), 5);
                        break;
                    }
                    Imgcodecs.imwrite("temp/" + i++ + ".jpg", im);
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                    }
                }
            }
        };
//        detect.start();

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        File f = new File("temp//");
        if(f.exists() && f.isDirectory()) {
            try{
                File f1 = new File("temp//data//");
                if(f1.exists() && f1.isDirectory()) {

                }else{
                    f1.mkdir();
                }
            }catch(Exception e){}
        }else{
            try{
                f.mkdir();
                File f1 = new File("temp//data//");
                f1.mkdir();
            }catch(Exception e){}
        }
        new filedlete().deletefile();
        new wall();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //TO AVOID THE DIRECT MOVEMENT OF PLAYER WITHOUT REACHING EITHER SIDE
        if(first==0) {
            if(over == 0) {
                if (e.getKeyChar() == '4') {
                    pos = 2;
                    mov = 1;
                }
                if (e.getKeyChar() == '6') {
                    pos = 1;
                    mov = 1;
                }
                first = 1;
            }
        }else {
            if(over == 0) {
                if ((pos == 2 && p1 == 1) || (pos == 1 && p2 == 1)) {
                    if (e.getKeyChar() == '4') {
                        pos = 2;
                        mov = 1;
                    }
                    if (e.getKeyChar() == '6') {
                        pos = 1;
                        mov = 1;
                    }
                }
            }
        }
    }
    @Override
    public void keyPressed(KeyEvent e) { }
    @Override
    public void keyReleased(KeyEvent e) { }
}
