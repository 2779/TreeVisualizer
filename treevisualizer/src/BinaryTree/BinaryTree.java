package BinaryTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree extends JFrame implements InputAdder
{
    public Node root=null;
    Container co;
    InputAdder inputAdder;
    int speed=1000;
    public BinaryTree(){
        super("Binary Tree Visualizer");//title
        inputAdder=this;
        setSize(500,500);
        co=new JPanel(){
            @Override
            public void paint(Graphics g){
                super.paint(g);
                if(root==null) return;
                Queue<Node> queue=new LinkedList<Node>();
                queue.add(root);
                while (queue.size()>0){
                    Node current=queue.poll();
                    if(current.left!=null){//if left is not null then curren is at centre form the edge of the cureent to cureent left edge draw line
                        g.setColor(new Color(0, 0, 0, 50));
                        g.drawLine(current.getX() + 25, current.getY() + 50, current.left.getX() + 25, current.left.getY());
                        queue.add(current.left);
                    }
                    if(current.right!=null){
                        g.setColor(new Color(0, 0, 0, 50));
                        g.drawLine(current.getX() + 25, current.getY() + 50, current.right.getX() + 25, current.right.getY());
                        queue.add(current.right);

                    }

                }
                g.dispose();
            }
        };
        co.setLayout(null);
        Container content=getContentPane();
        content.setLayout(null);
        content.add(co);
        content.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e) {
               co.setSize(content.getSize());
               render();
            }
        });
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                new Thread(()->{
                    switch (e.getKeyCode()){
                        case KeyEvent.VK_SPACE:
                            String data=JOptionPane.showInputDialog(e.getComponent(),"Enter Operation and data","Operation",JOptionPane.PLAIN_MESSAGE);
                            if(data != null && data.length() > 0){
                                String arr[]=data.split(" ");
                                if(arr.length==2) inputAdder.addInput(arr[0], Integer.parseInt(arr[1]) );
                                else inputAdder.addInput(arr[0], 0);
                                render();}
                        case KeyEvent.VK_D:
                            if(e.isControlDown()) deSelectAll(root);
                            break;
                        case KeyEvent.VK_DOWN:
                            setSpeed(speed-500);
                            JOptionPane op=new JOptionPane("Speed :"+speed);
                            JDialog jd = op.createDialog(null, "speed");
                            jd.setModal(false);
                            jd.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            jd.setVisible(true);
                            try{Thread.sleep(1000);}catch (InterruptedException e1){e1.printStackTrace();}
                            jd.setVisible(false);
                            break;
                        case KeyEvent.VK_UP:
                            setSpeed(speed+500);
                            JOptionPane op2=new JOptionPane("Speed :"+speed);
                            JDialog jd2 = op2.createDialog(null, "speed");
                            jd2.setModal(false);
                            jd2.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                            jd2.setVisible(true);
                            try{Thread.sleep(1000);}catch (InterruptedException e2){e2.printStackTrace();}
                            jd2.setVisible(false);
                            break;
                        case KeyEvent.VK_A:
                            if (e.isControlDown()) selectAll(root);
                            break;
                    }
                }).start();
            }
        });

        setVisible(true);
    }

    public void delete(int data){
        root=delete(root, data);
        repaint();    }
    protected Node delete(Node root, int data){//because it is for complete binary tree
        ArrayBinaryTree arrayBinaryTree=new ArrayBinaryTree(getTreeHeight());
        arrayBinaryTree.initWithTree(this);

        int childIndex = arrayBinaryTree.getLastNodeIndex();
        Node parent = arrayBinaryTree.getParentNode(childIndex);
        Node child = arrayBinaryTree.tree[childIndex];
        if( childIndex%2 == 0){
            co.remove( child );
            parent.right = null;
        }
        else{
            co.remove( child );
            parent.left = null;
        }
        Node dataNode= find(data);
        dataNode.data=child.data;
        dataNode.setText( dataNode.data+"" );
        return root;

    }

    public void render()
    {
        if (root == null) return;
        int nodeHeight = getTreeHeight();
        int height = co.getHeight();
        int width = co.getWidth();
        Node[] nodesList = getNodeList();
        int ind = 0;
        for(int i = 0; i < nodeHeight; i++){//calculation in note and image in project folder
           int y= ( height / ( nodeHeight + 1)) * (i + 1);
           int nodeWidth = (int) Math.pow(2, i);
           for(int j = 0; j < nodeWidth; j++){
               Node currentNode = nodesList[ ind++ ];
               if(currentNode == null) continue;
               int x = (width / (nodeWidth + 1)) * (j+1);
               currentNode.setLocation(x, y);

           }

        }

    }

    public Node find(int data){
        Node node=find(root,data);
        if(node != null){
            showOutput("Found the element:"+data);
            node.isHighLighted=true;
            node.highlight();
        }
        else{
            showOutput("ELement "+data+" Not Found");
        }
        return node;

    }
    protected Node find(Node root, int data){
        if(root==null) return null;



        root.highlight();
        try{Thread.sleep(speed);}catch (InterruptedException e){e.printStackTrace();}
        if(root.data==data) return root;
        root.highlight();

        Node left=find(root.left, data);
        if(left != null) return left;//when it return if the left is not null return that left to node likewise right

        root.highlight();//when poping we need to highlight
        try{Thread.sleep(speed);}catch (InterruptedException e){e.printStackTrace();}
        root.highlight();

        Node right=find(root.right, data);
        if(right != null) return right;

        root.highlight();//when poping we need to highlight
        try{Thread.sleep(speed);}catch (Exception e){e.printStackTrace();}
        root.highlight();

        return null;




    }
    public Node getNode(int i){
        Node n=new Node(i);
        co.add(n);
        return n;

    }

    public void add(int i){
        if(root==null) root= getNode(i);
        else{
            Queue<Node> queue=new LinkedList<>();
            queue.add(root);
            while(queue.size()>0){
                Node currentNode=queue.poll();
                if(currentNode.left==null){
                    currentNode.left=getNode(i);
                    break;
                }else if(currentNode.right==null){
                    currentNode.right=getNode(i);
                    break;
                }else{
                    queue.add(currentNode.left);
                    queue.add(currentNode.right);
                }
            }


        }
    }

    public void inOrder(){
        JLabel out = getOuputChanger();
        out.setText("Inorder : " );
        inOrder(root, out);


    }
    protected void inOrder(Node root, JLabel out){
        if(root == null) return;

        inOrder(root.left, out);

        root.highlight();
        out.setText(out.getText()+" "+root.data);
        try{Thread.sleep(speed);}catch (InterruptedException e){e.printStackTrace();}

        inOrder(root.right, out);

    }

    public void preOrder(){
        JLabel out=getOuputChanger();
        out.setText("preorder : ");
        preOrder(root, out);
    }
    protected void preOrder(Node root, JLabel out){
        root.highlight();
        out.setText(out.getText()+ " "+root.data);
        try{Thread.sleep(speed);}catch (InterruptedException e){e.printStackTrace();}

        preOrder(root.left, out);
        preOrder(root.right, out);
    }

    public void postOrder(){
        JLabel out=getOuputChanger();
        out.setText("postorder :");
        postOrder(root, out);
    }
    protected void postOrder(Node root, JLabel out){
        if(root==null) return;

        postOrder(root.left, out);
        postOrder(root.right, out);

        root.highlight();
        out.setText(out.getText()+" "+root.data);
        try{Thread.sleep(speed);}catch (InterruptedException e){e.printStackTrace();}


    }

    public int getTreeHeight() {
        int count=0;
        for(Node temp=root; temp!=null;temp=temp.left){
            count++;
        }return count;
    }

    public void getVisualTreeHeight(){
        JLabel out = getOuputChanger();
        out.setText("Height of the Tree : 0");
        int height = getVisualTreeHeight(root,out);

    }
    private int getVisualTreeHeight(Node root, JLabel out){
        if(root == null) return 0;

        root.highlight();
        try {Thread.sleep(speed);} catch (InterruptedException e) {e.printStackTrace();}

        int leftHeight=getVisualTreeHeight(root.left, out);
        int rightHeight=getVisualTreeHeight(root.right, out);

        if(leftHeight>=rightHeight && root.left !=null){
            deSelectAll(root.right);
            int height=leftHeight+1;
            out.setText("Height of the Tree: "+height);
            return height;
        }
        if(root.right != null){
            deSelectAll(root.left);
            int height=rightHeight+1;
            out.setText("Height of the Tree: "+height);
            return height;
        }
        return 1;
    }

    protected Node[] getNodeList() {
        if(root==null) return null;
        ArrayBinaryTree arrayBinaryTree=new ArrayBinaryTree(getTreeHeight());
        arrayBinaryTree.initWithTree(this);
        return arrayBinaryTree.tree;


    }



    public void setSpeed(int speed){
        this.speed=speed;
    }


    public void selectAll(Node root){
        if(root==null) return;
        root.isHighLighted=false;
        root.highlight();
        selectAll(root.left);
        selectAll(root.right);

    }

    public void deSelectAll(Node root){
        if(root==null) return;

        root.isHighLighted=true;
        root.highlight();
        deSelectAll(root.left);
        deSelectAll(root.right);

    }

    public void showOutput(String text){
        JOptionPane.showMessageDialog(this,text,"Output",JOptionPane.INFORMATION_MESSAGE);
    }

    public JLabel getOuputChanger(){
        JLabel text=new JLabel();
        new Thread(() -> {
            JOptionPane.showMessageDialog(this,text,"Output",JOptionPane.INFORMATION_MESSAGE);
            deSelectAll(root);

        }).start();
        return text;
    }















    @Override
    public void addInput(String opertaion, int data) {
        switch (opertaion){
            case "find" : find(data); break;
            case "add"  : add(data); break;
            case "height" :getVisualTreeHeight();break;
            case "speed" : setSpeed(data); break;
            case "inorder" : inOrder(); break;
            case "postorder" : postOrder(); break;
            case "preorder" : preOrder(); break;
            case "delete" : delete(data); break;
        }

    }

    public static void main(String[] args) {
        BinaryTree bTree=new BinaryTree();
        for(int i=1;i<10;i++){
            bTree.add(i);
        }
        System.out.println(bTree.getTreeHeight());


    }
}
