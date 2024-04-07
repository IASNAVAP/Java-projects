import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class snakegame extends JPanel implements ActionListener,KeyListener{
    private class tile{
        int x;
        int y;

     tile(int x, int y){
            this.x = x;
            this.y=y;
        }
    }
    int boardwidth;
    int  boardheight;
    int tilesize= 25;

    tile snakehead;
    ArrayList<tile> snakebody;
    tile food;
    Random random;

    //logic
    Timer gameloop;
    int velx;
    int vely;
    boolean gameOver= false;


    snakegame(int boardwidth, int boardheight){
        this.boardwidth=boardwidth;
        this.boardheight=boardheight;
        setPreferredSize(new Dimension(this.boardwidth,this.boardheight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        snakehead=new tile(5,5);
        snakebody= new ArrayList<tile>();
        food = new tile(10,10);
        random=new Random();
        placefood();
        velx=0;
        vely=0;
        gameloop = new Timer(100,this);
        gameloop.start();
    }
     
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        //food
        g.setColor(Color.red);
      
        g.fill3DRect(food.x*tilesize, food.y*tilesize, tilesize, tilesize,true);
        //snake
        g.setColor(Color.green);
        
        g.fill3DRect(snakehead.x*tilesize, snakehead.y*tilesize, tilesize, tilesize,true);
         //snakebody
         for(int i =0; i<snakebody.size();i++){
            tile snakepart= snakebody.get(i);
            g.fill3DRect(snakepart.x*tilesize, snakepart.y*tilesize, tilesize, tilesize,true);
         }

         g.setFont(new Font("Arial",Font.PLAIN,16));
         if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game Over! your score is:"+String.valueOf(snakebody.size()),tilesize-16,tilesize);
         }
         else{
            g.drawString("score: "+String.valueOf(snakebody.size()),tilesize-16,tilesize);
         }
    }

    public void placefood(){
        food.x=random.nextInt(boardwidth/tilesize);
        food.x=random.nextInt(boardheight/tilesize);
    }


    public boolean coll(tile a, tile b) {
        return a.x==b.x && a.y == b.y;
    }
    public void move(){

        if(coll(snakehead,food)){
            snakebody.add(new tile(food.x,food.y));
            placefood();
        }
        
        for(int i = snakebody.size()-1;i >=0;i--){
            tile snakepart=snakebody.get(i);
            if(i==0){
                snakepart.x=snakehead.x;
                snakepart.y=snakehead.y;
            }else{
                tile prevsp=snakebody.get(i-1);
                snakepart.x=prevsp.x;
                snakepart.y=prevsp.y;
            }
        }

       snakehead.x+=velx;
       snakehead.y+=vely;

       for(int i = 0; i < snakebody.size();i++){
        tile snakepart = snakebody.get(i);
        if(coll(snakehead,snakepart)){
            gameOver=true;
        }
        }

        if(snakehead.x*tilesize<0 || snakehead.x*tilesize>boardwidth || 
        snakehead.y*tilesize<0 || snakehead.y*tilesize>boardheight){
            gameOver=true;
        } 

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
      repaint();
      if(gameOver){
        gameloop.stop();
      }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()== KeyEvent.VK_UP && vely !=1){
            velx=0;
            vely=-1;
        }
        else if(e.getKeyCode()== KeyEvent.VK_DOWN && vely!=-1){
            velx=0;
            vely=1;
        }
        else if(e.getKeyCode()== KeyEvent.VK_LEFT && velx !=1){
            velx=-1;
            vely=0;
        }
        else if(e.getKeyCode()== KeyEvent.VK_RIGHT&& velx !=-1){
            velx=1;
            vely=0;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


    @Override
    public void keyReleased(KeyEvent e) {
    }




    
}
