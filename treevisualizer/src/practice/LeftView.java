package practice;

import java.util.ArrayList;
import java.util.Collections;

class Node{
    int data;
    Node left,right;
    Node(int data){
        this.data=data;
        this.left=this.right=null;
    }
}
public class LeftView {

    public static void main(String args[]){
        ArrayList<Integer> result=new ArrayList<>();
        Node root=new Node(8);
        root.left=new Node(1);               //       8
        root.right=new Node(6);             //    1       6
        root.right.right=new Node(5);      //null null  null   5
                                                //output may 8 1 5
        new LeftView().leftViewBinaryTree(root,1,result);
        for(int i:result){
            System.out.print(i+" ");
        }
        ArrayList<Integer> trianlgeResult=new ArrayList<>();
        new LeftView().triangle(root,trianlgeResult);
        System.out.println();
        for(int i:trianlgeResult){
            System.out.print(i+" ");
        }
    }
    int maxLevel=0;
    public void leftViewBinaryTree(Node root,int level,ArrayList<Integer> result){
        if(root==null) return;
        if(maxLevel<level){
            result.add(root.data);
            maxLevel=level;
        }
        leftViewBinaryTree(root.left,level+1,result);
        leftViewBinaryTree(root.right,level+1,result);//when  i reverse it becomes right view
    }
    public void triangle(Node root,ArrayList<Integer> trianlgeResult){
        leftViewBinaryTree(root,1,trianlgeResult);
        trianlgeResult.remove(0);
        Collections.reverse(trianlgeResult);

        maxLevel=0;
        rightViewBinaryTree(root,1,trianlgeResult);


    }

    private void rightViewBinaryTree(Node root, int level, ArrayList<Integer> trianlgeResult) {
        if(root==null) return;
        if(maxLevel<level){
            trianlgeResult.add(root.data);
            maxLevel=level;
        }
        rightViewBinaryTree(root.right,level+1,trianlgeResult);
        rightViewBinaryTree(root.left,level+1,trianlgeResult);
    }


}

