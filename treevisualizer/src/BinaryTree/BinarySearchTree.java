package BinaryTree;

import visualList.VisualStack;

import javax.swing.*;

public class BinarySearchTree extends BinaryTree
{
    public void add(int data) {
        root = add( root , data );
    }
    private Node add( Node root , int data ) {
        if (root == null) return getNode(data);

        if (data < root.data) root.left = add( root.left , data );
        else root.right = add( root.right , data );

        return root;
    }
    public int getTreeHeight() {
        return getTreeHeight( root );
    }
    private int getTreeHeight( Node root ) {
        if (root == null) return 0;

        int leftHeight = getTreeHeight( root.left );
        int rightHeight = getTreeHeight( root.right );

        return 1 + Math.max( leftHeight, rightHeight );
    }

    public Node find(int data){
        Node dataNode= find(root, data);
        showOutput((dataNode==null)?"Element not found "+ data:"Found element "+data);
        if(dataNode!=null) dataNode.highlight();
        return dataNode;
    }
    protected Node find(Node root, int data){
        if(root==null) return null;

        root.highlight();
        try{Thread.sleep(1000);}catch (InterruptedException e){e.printStackTrace();}
        if(root.data==data) return root;
        root.highlight();
        try{Thread.sleep(1000);}catch (InterruptedException e){e.printStackTrace();}

        if(data < root.data) return find(root.left, data);
        return find(root.right, data);

    }

    public void delete(int data){
        root = delete(root, data);
        deSelectAll(root);
        repaint();
    }

    protected Node delete(Node root, int data){
        if(root == null) return null;

        if(data < root.data) root.left = delete(root.left, data);
        else if(data > root.data) root.right = delete(root.right, data);
        else{
            root.highlight();
            try{Thread.sleep(speed);}catch (InterruptedException e){e.printStackTrace();}

            if(root.left == null){
                co.remove( root );
                return root.right;//sometimes may null not affecting the properties
            }
            if(root.right == null){
                co.remove( root );
                return root.left;//same as previous
            }
            root.data = min(root.right);
            root.setText( root.data +"");

            try{Thread.sleep(speed);}catch (InterruptedException e){e.printStackTrace();}
            root.right=delete(root.right,root.data);

        }
        return root;

    }

    protected int min( Node root ){
        int minimum = root.data;
        for( Node temp=root; temp!=null;temp=temp.left){minimum=temp.data;}
        return minimum;
    }

    public void inOrder(){
        if(root==null) return;
        JLabel out=getOuputChanger();
        VisualStack<Node> stack=new VisualStack<Node>();
        out.setText("Inorder :");
        Node curr = root;
        showOutput("Start Inorder Traversal");
        while (stack.length()>0||curr!=null){
            while (curr != null){
                stack.visualPush( curr );
                curr = curr.left;
                try{Thread.sleep(speed);}catch (InterruptedException e){e.printStackTrace();}
            }
            curr=stack.visualPop();
            try{Thread.sleep(speed);}catch (InterruptedException e){e.printStackTrace();}
            out.setText( out.getText()+" "+curr.data);
            curr.highlight();
            curr=curr.right;

        }
        stack.dispose();
    }


    public static void main(String[] args) {
        BinarySearchTree bst=new BinarySearchTree();
        bst.add( 5 );
        bst.add( 1 );
        bst.add( 3 );
        bst.add( 7 );
        bst.add( 6 );
        bst.add( 8 );
    }

}
