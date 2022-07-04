package visualList;

import logging.Log;
import logging.Logger;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VisualStack<Type> extends JFrame {
    Container co;
    Node<Type> top;
    int speed=1000;
    Logger log=new Logger();
   public VisualStack(){
       super("Stack Visualizer tool");
       setSize(400,550);
       setLocation(400,100);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        co=getContentPane();
        co.setLayout(null);
        co.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                render();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                new Thread(()->{
                    switch (e.getKeyCode()){
                        case KeyEvent.VK_SPACE:
                            String data=JOptionPane.showInputDialog(e.getComponent(),"Enter the Operation and data","Operation",JOptionPane.PLAIN_MESSAGE);
                            if (data!=null && data.length()>0){
                                String[] arr=data.split(" ");
                                if(arr.length==2){
                                    addInput(arr[0],arr[1]);
                                }else addInput(arr[0],null);
                                render();
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            speed=speed-500;
                            JOptionPane op=new JOptionPane("Speed :"+ speed);
                            JDialog jd=op.createDialog(null,"Speed");
                            jd.setModal(false);
                            jd.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            jd.setVisible(true);
                            try{Thread.sleep(speed);}catch (InterruptedException e1){e1.printStackTrace();}
                            jd.setVisible(false);
                            break;
                        case  KeyEvent.VK_UP:
                            speed=speed+500;
                            JOptionPane op2=new JOptionPane("Speed :"+speed);
                            JDialog jd2=op2.createDialog(null,"Speed");
                            jd2.setModal(false);
                            jd2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            jd2.setVisible(true);
                            try{Thread.sleep(speed);}catch (InterruptedException e1){e1.printStackTrace();}
                            jd2.setVisible(false);
                            break;


                    }

                }).start();
            }
        });
        setVisible(true);
   }
   public void render(){//caluclation in note
       int nodeHeight=length();
       int height=co.getHeight();
       int width=co.getWidth();
        Node<Type> temp=top;
        for(int i=0;i<nodeHeight;i++){
            int y= ( height / (1 + nodeHeight)) * (i + 1);
            temp.setSize((int) (width*0.5),40);
            temp.setLocation(width/2, y);
            temp=temp.next;

        }

   }
   public int length(){
        int count=0;
        for(Node<Type> temp=top;temp!=null;temp=temp.next){count++;}
        return count;
   }
   public void showOutput(String text){
        JOptionPane.showMessageDialog(this,text,"Output",JOptionPane.INFORMATION_MESSAGE);
   }

   public void visualPush(Type data){
            push( data );
            top.select();
            render();
//showOutput("pushed element"+top);
            top.deSelect();
   }
   public void push(Type data){

       log.addLog(new Log("push",data.toString()));

       Node<Type> temp=top;
       top=getNode(data);
       top.next=temp;

   }
   public Type visualPop(){
        Node<Type> temp= peekNode();
        if(temp == null){
            JOptionPane.showMessageDialog(this,"Stack UnderFLow","Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
        temp.select();
        showOutput("pop Element: "+temp.data.toString());
        Type ele=pop();
        render();
        repaint();
        return ele;

   }

   public Type pop(){
    if(isEmpty()) return null;
    Node<Type> temp=top;
    top=top.next;
    co.remove(temp);
    log.addLog(new Log("pop",null,temp.data.toString()));
    return temp.data;
   }
   public void visualPeek(){
        if(isEmpty()) return;
        top.select();;
        showOutput("Peek Element : "+peek());
        top.deSelect();
   }
   public Type peek(){
        log.addLog(new Log("Peek",top+""));
        return top.data;
   }

   public boolean isEmpty(){return top == null;}

    protected Node<Type> peekNode(){
        return top;
    }

   public Node<Type> getNode(Type data){
       Node<Type> newNode = new Node<>(data);
       co.add( newNode );
       return newNode;

   }

   public void addInput(String operation, String data){
        switch (operation){
            case "push" : visualPush( (Type)(Integer) Integer.parseInt(data)); break;
            case "pop" : visualPop(); break;
            case "peek" :visualPeek(); break;
            case "length" : length(); break;
            case "log" : showOutput(log.toString()); break;
        }
   }

    public static void main(String[] args) {
        new VisualStack<Integer>();
    }




}
