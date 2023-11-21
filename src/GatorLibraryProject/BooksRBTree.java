package src.GatorLibraryProject;

import java.util.ArrayList;
import java.util.List;

public class BooksRBTree {
    
    private boolean RED   = false;
    private boolean BLACK = true;

    private Node    root;
    private int     color_flip_count = 0;

    private class Node {
        Books   key;
        
        Node    left;
        Node    right;
        Node    parent;
        
        boolean color;

        Node(Books pKey) {
            key     = pKey;
            color   = RED;
        }
    }

    private class NullNode extends Node{
        private NullNode() {
            super(null);
            color = BLACK;
        }
    }

    private boolean IsNodeRed(Node pNode) {
        return pNode.color == RED;
    }

    private void RotateRight(Node pNode) {
        Node parent = pNode.parent;
        Node left_c = pNode.left;

        pNode.left = left_c.right;
        if (left_c.right != null) {
            left_c.right.parent = pNode;
        }

        left_c.right = pNode;
        pNode.parent = left_c;

        ReplaceChildren (parent, pNode, left_c);
    }

    private void RotateLeft(Node pNode) {
        Node parent     = pNode.parent;
        Node right_c    = pNode.right;

        pNode.right = right_c.left;
        if (right_c.left != null) {
            right_c.left.parent = pNode;
        }

        right_c.left = pNode;
        pNode.parent = right_c;

        ReplaceChildren(parent, pNode, right_c);
    }

    private void ReplaceChildren(Node pParent, Node pOldChild, Node pNewChild) {
        if (pParent == null) {
            root = pNewChild;
        } else if (pParent.left == pOldChild) {
            pParent.left = pNewChild;
        } else if (pParent.right == pOldChild) {
            pParent.right = pNewChild;
        } else {
            throw new IllegalStateException("Node is not a child of it's parents");
        }

        if (pNewChild != null) {
            pNewChild.parent = pParent;
        }
    }

    private Node SearchBook(int pBookID, Node pNode) {
        if (pNode == null) {
            return null;
        }

        if (pNode.key.GetBookID() == pBookID) {
            return pNode;
        } else if (pNode.key.GetBookID() < pBookID) {
            return SearchBook(pBookID, pNode.right);
        } else {
            return SearchBook(pBookID, pNode.left);
        }
    }

    private Node GetAuntNode(Node pNode) {
        Node parent = pNode.parent;

        if (pNode == parent.left) {
            return parent.right;
        } else if (pNode == parent.right) {
            return parent.left;
        } else {
            throw new IllegalStateException("The node is not a child of it's Parent :(");
        }
    }

    private Node GetSiblingNode(Node pNode) {
        if (pNode == pNode.parent.left) {
            return pNode.parent.right;
        } else if (pNode == pNode.parent.right) {
            return pNode.parent.left;
        } else {
            throw new IllegalStateException("Unable to get the sibling.");
        }
    }

    private void FixRBTreePropsInsertion(Node pNode) {
        Node parent = pNode.parent;

        if (parent == null) {
            return;
        }

        if (parent.color == BLACK) {
            return;
        }

        Node grandparent = parent.parent;

        if (grandparent == null) {
            parent.color = BLACK;
            return;
        }

        Node Aunt = GetAuntNode(parent);

        if (Aunt != null && Aunt.color == RED) {
            parent.color = BLACK;
            grandparent.color = RED;
            Aunt.color = BLACK;

            color_flip_count++;

            FixRBTreePropsInsertion(grandparent);
        } else if (parent == grandparent.left) {
            if (pNode == parent.right) {
                RotateLeft(parent);
                parent = pNode;
            }

            RotateRight(grandparent);

            parent.color = BLACK;
            grandparent.color = RED;

            color_flip_count++;
        } else {

            if (pNode == parent.left) {
                RotateRight(parent);
                parent = pNode;
            }

            RotateLeft(grandparent);

            parent.color = BLACK;
            grandparent.color = RED;
        }
    }

    private void InsertBookHelper(Books pBook) {
        Node root_node = root;
        Node parent = null;

        while (root_node != null) {
            parent = root_node;
            if (pBook.GetBookID() < root_node.key.GetBookID()) {
                root_node = root_node.left;
            } else if (pBook.GetBookID() > root_node.key.GetBookID()) {
                root_node = root_node.right;
            } else {
                throw new IllegalStateException("Library Already Contains a book with the same BookID");
            }
        }

        Node new_node = new Node(pBook);

        if (parent == null) {
            root = new_node;
        } else if (pBook.GetBookID() < parent.key.GetBookID()) {
            parent.left = new_node;
        } else {
            parent.right = new_node;
        }

        new_node.parent = parent;

        FixRBTreePropsInsertion(new_node);
    }

    private Node DeleteNodeWZeroOneChild(Node pNode) {
        if (pNode.left != null) {
            ReplaceChildren(pNode.parent, pNode, pNode.left);
            return pNode.left;
        } else if (pNode.right != null) {
            ReplaceChildren(pNode.parent, pNode, pNode.right);
            return pNode.right;
        } else {
            Node new_node;
            if (!IsNodeRed(pNode)) {
                new_node = new NullNode();
            } else {
                new_node = null;
            }

            ReplaceChildren(pNode.parent, pNode, new_node);
            return new_node;
        }
    }

    private Node FindMin(Node pNode){
        while (pNode != null) {
            pNode = pNode.left;
        }

        return pNode;
    }

    private void HandleRedSib(Node pNode, Node pSibNode) {
        pSibNode.color = BLACK;
        pNode.parent.color = RED;

        color_flip_count++;

        if (pNode == pNode.parent.left) {
            RotateLeft(pNode.parent);
        } else {
            RotateRight(pNode.parent);
        }
    }

    private void HandleBlackSibWOneRedChild(Node pNode, Node pSibNode) {
        boolean is_node_left_c = pNode == pNode.parent.left;

        if (is_node_left_c && !IsNodeRed(pSibNode.right)) {
            pSibNode.left.color = BLACK;
            pSibNode.color = RED;
            RotateRight(pSibNode);
            pSibNode = pNode.parent.right;
        } else if (!is_node_left_c && IsNodeRed(pSibNode.left)) {
            pSibNode.right.color = BLACK;
            pSibNode.color = RED;
            RotateLeft(pSibNode);
            pSibNode = pNode.parent.left;
        }

        pSibNode.color = pNode.parent.color;
        pNode.parent.color = BLACK;

        if (is_node_left_c) {
            pSibNode.right.color = BLACK;
            RotateLeft(pNode.parent);
        } else {
            pSibNode.left.color = BLACK;
            RotateRight(pNode.parent);
        }
    }

    private void FixRBTreePropsDeletion(Node pNode) {
        if (pNode == root) {
            return;
        }

        Node sibling_node = GetSiblingNode(pNode);

        if (IsNodeRed(sibling_node)) {
            HandleRedSib(pNode,sibling_node);
            sibling_node = GetSiblingNode(pNode);
        }

        if (!IsNodeRed(sibling_node.left) && !IsNodeRed(sibling_node.right)) {
            sibling_node.color = RED;

            if (IsNodeRed(pNode.parent)) {
                pNode.parent.color = BLACK;
            } else {
                FixRBTreePropsDeletion(pNode.parent);
            }
        } else {
            HandleBlackSibWOneRedChild(pNode, sibling_node);
        }
    }

    private void DeleteBookHelper(Books pBook) {

        Node curr_node = root;

        while (curr_node != null && curr_node.key.GetBookID() != pBook.GetBookID()) {
            if (pBook.GetBookID() < curr_node.key.GetBookID()) {
                curr_node = curr_node.left;
            } else {
                curr_node = curr_node.right;
            }
        }

        if (curr_node == null) {
            return;
        }

        Node    to_be_moved_up;
        boolean deleted_node_col;

        if (curr_node.left == null || curr_node.right == null) {
            to_be_moved_up = DeleteNodeWZeroOneChild(curr_node);
            deleted_node_col = curr_node.color;
        } else {
            Node next_inorder = FindMin(curr_node.right);
            curr_node.key = next_inorder.key;

            to_be_moved_up = DeleteNodeWZeroOneChild(next_inorder);
            deleted_node_col = next_inorder.color;
        }

        if (deleted_node_col == BLACK) {
            FixRBTreePropsDeletion(to_be_moved_up);

            if (to_be_moved_up.getClass() == NullNode.class) {
                ReplaceChildren(to_be_moved_up.parent, to_be_moved_up, null);
            }
        }
    }

    private void GetBooksInRangeHelper(Node pNode, int pStartID, int pEndID, List<Books> bookList) {
        if (pNode == null) {
            return; 
        }
        
        if (pNode.key.GetBookID() >= pStartID) {
            GetBooksInRangeHelper(pNode.left, pStartID, pEndID, bookList);
        }
        
        if (pNode.key.GetBookID() >= pStartID && pNode.key.GetBookID() <= pEndID) {
            bookList.add(pNode.key);
        }

        if (pNode.key.GetBookID() <= pEndID) {
            GetBooksInRangeHelper(pNode.right, pStartID, pEndID, bookList); 
        }
    }

    public Books[] GetBooksInRange(int pStartID, int pEndID) {
        List<Books> books = new ArrayList<>();
    
        GetBooksInRangeHelper(root, pStartID, pEndID, books);
        
        return books.toArray(new Books[0]);
    }
      
    private void FindTie(Node pNode, int pTargetID, int pDifference, Node pClosest, Node pTiedNode) {
    
        if(pNode == null) {
            return;
        }
        
        int currentDiff = Math.abs(pNode.key.GetBookID() - pTargetID);
        
        if(currentDiff == pDifference && pNode != pClosest) {
            pTiedNode = pNode;
        }
        
        FindTie(pNode.left, pTargetID, pDifference, pClosest, pTiedNode);
        FindTie(pNode.right, pTargetID, pDifference, pClosest, pTiedNode);
    
    }

    public Books[] FindClosestBooks(int pTargetID) {

        Node closest_node = root;
        Node curr_node = root;
        
        int closestDiff = Integer.MAX_VALUE;
        
        while(curr_node != null) {
            int current_difference = Math.abs(curr_node.key.GetBookID() - pTargetID);
            
            if(current_difference < closestDiff) {
                closestDiff = current_difference;
                closest_node = curr_node;
            }
            
            if(pTargetID < curr_node.key.GetBookID()) {
                curr_node = curr_node.left; 
            } else {
                curr_node = curr_node.right;
            }
        }
      
        List<Books> result = new ArrayList<>();
        result.add(closest_node.key);
        
        // Check if we have a tie
        Node tieNode = null; 
        int diff = closestDiff;
        
        FindTie(root, pTargetID, diff, closest_node, tieNode);
        
        return result.toArray(new Books[0]);
    }

    public Books GetBook(int pBookID) {
        return SearchBook(pBookID, root).key;
    }

    public void InsertBook(Books pBook) {
        InsertBookHelper(pBook);
    }

    public void DeleteBook(Books pBook) {
        DeleteBookHelper(pBook);
    }

    public int GetColorFlipCount() {
        return color_flip_count;
    }

}
