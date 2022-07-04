package BinaryTree;

public class ArrayBinaryTree {
    Node[] tree=null;
    public ArrayBinaryTree(int height){ tree=new Node[(int)(Math.pow(2,height)-1)];
    //-1 for there is no two root
    }
    public void initWithTree(BinaryTree binaryTree){
        Node root=binaryTree.root;
        tree[0]=root;
        for(int i=0;i<tree.length;i++){
            Node parent=tree[i];
            if(parent==null) continue;
            if(getLeftChildIndex(i)>=tree.length) break;
            //leftchild index big then rightchild index definetly big
            tree[getLeftChildIndex(i)]=parent.left;
            tree[getRightChildIndex(i)]=parent.right;

        }

    }
    public int getLeftChildIndex(int parentIndex){
        return 2*parentIndex+1;
    }
    public int getRightChildIndex(int parentIndex){
        return 2*parentIndex+2;
    }
    public int getParentIndex(int childIndex){
        return childIndex/2;
    }
    public Node getParentNode(int childIndex){
        return tree[getParentIndex(childIndex)];
    }
    public Node getLastNode(){
        for(int i=tree.length-1;i>=0;i--){
            if(tree[i]!=null) return tree[i];
        }return null;
    }

    public int getLastNodeIndex(){
        for(int i=tree.length-1 ; i>=0; i--){
            if(tree[i] != null) return i;
        }return -1;
    }





}
