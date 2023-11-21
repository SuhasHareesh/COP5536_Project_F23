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
        }
    }

    private class NullNode extends Node{
        private NullNode() {
            super(null);
            color = BLACK;
        }
    }

    private boolean IsNodeRed(Node pNode) {
       return pNode != null && pNode.color == RED;
    } 

    /**
     * @brief This method is used to make a Right Rotation.
     * @param pNode the node where the rotation should take place.
     */
    private void RotateRight(Node pNode) {
        Node parent = pNode.parent;
        Node left_c = pNode.left;

        pNode.left = left_c.right;

        // Set the parent node of the left node to the right of the tree.

        if (left_c.right != null) {
            left_c.right.parent = pNode;
        }

        left_c.right = pNode;
        pNode.parent = left_c;

        ReplaceChildren (parent, pNode, left_c);
    }

    /**
     * @brief This method is used to make a Left Rotation.
     * @param pNode the node where the rotation should take place.
     */
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

    /**
     * @brief This method is used to replace the child to the parent.
     * @param pParent the parent node
     * @param pOldChild the old child node
     * @param pNewChild the new child.
     */
    private void ReplaceChildren(Node pParent, Node pOldChild, Node pNewChild) {

        // Sets the root node of the node.

        if (pParent == null) {
            root = pNewChild;

        // Set the left and right of the node s parent.

        } else if (pParent.left == pOldChild) {
            pParent.left = pNewChild;

        // Set the right node of the node s parent to the new child.

        } else if (pParent.right == pOldChild) {
            pParent.right = pNewChild;
        } else {
            throw new IllegalStateException("Node is not a child of it's parents");
        }

        // Set the parent of the new child.

        if (pNewChild != null) {
            pNewChild.parent = pParent;
        }
    }

    /**
     * @brief This method is used to search the tree recursively for the book.
     * @param pBookID The book ID to be searched
     * @param pNode The node from where to search (Usually the root)
     * @return The node where the book ID was found or null if the book was not found.
     */
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

    /**
     * @brief This method returns the Aunt/Uncle of a given node.
     * @param pNode The parent node of the node searching for its aunt.
     * @return The node.
     */
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

    /**
     * @brief This method returns the Sibling node of a node.
     * @param pNode The node searching for its sibling.
     * @return The sibling node.
     */
    private Node GetSiblingNode(Node pNode) {
        // Returns the sibling of the node.
        if (pNode == pNode.parent.left) {
            return pNode.parent.right;
        } else if (pNode == pNode.parent.right) {
            return pNode.parent.left;
        } else {
            throw new IllegalStateException("Unable to get the sibling.");
        }
    }

    /**
     * @brief Method to fix the Red Black Tree properties after insertion.
     * @param pNode The node where the property might have been violated.
     */
    private void FixRBTreePropsInsertion(Node pNode) {
        Node parent = pNode.parent;

        // Returns the parent node.
        if (parent == null) {
            return;
        }

        // Returns true if the parent is black.
        if (parent.color == BLACK) {
            return;
        }

        Node grandparent = parent.parent;

        // Sets the color of the parent.
        if (grandparent == null) {
            parent.color = BLACK;
            return;
        }

        Node Aunt = GetAuntNode(parent);

        // Rotates the Aunt and its children.
        if (Aunt != null && Aunt.color == RED) {
            parent.color = BLACK;
            grandparent.color = RED;
            Aunt.color = BLACK;

            color_flip_count++;

            FixRBTreePropsInsertion(grandparent);
        // Rotate the parent node and its children
        } else if (parent == grandparent.left) {
            // Rotate the parent node to the right
            if (pNode == parent.right) {
                RotateLeft(parent);
                parent = pNode;
            }

            RotateRight(grandparent);

            parent.color = BLACK;
            grandparent.color = RED;

            color_flip_count++;
        } else {

            // Rotate the parent node to the right
            if (pNode == parent.left) {
                RotateRight(parent);
                parent = pNode;
            }

            RotateLeft(grandparent);

            parent.color = BLACK;
            grandparent.color = RED;

            color_flip_count++;
        }
    }

    /**
     * @brief Helper Method for insertion of a new book into the tree.
     * @param pBook The book that needs to be inserted.
     */ 
    private void InsertBookHelper(Books pBook) {
        Node curr_node = root;
        Node parent = null;

        // Find the parent Book with the same BookID
        while (curr_node != null) {
            parent = curr_node;
            // This method is called by the Library to check if the book is in the Library already contains a book with the same BookID.
            if (pBook.GetBookID() < curr_node.key.GetBookID()) {
                curr_node = curr_node.left;
            // This method is called when the library contains a book with the same BookID.
            } else if (pBook.GetBookID() > curr_node.key.GetBookID()) {
                curr_node = curr_node.right;
            } else {
                throw new IllegalStateException("Library Already Contains a book with the same BookID");
            }
        }

        Node new_node = new Node(pBook);
        new_node.color = RED;

        // Set the parent node to the new node.
        if (parent == null) {
            root = new_node;
        // Set the left and right nodes of the book
        } else if (pBook.GetBookID() < parent.key.GetBookID()) {
            parent.left = new_node;
        } else {
            parent.right = new_node;
        }

        new_node.parent = parent;

        FixRBTreePropsInsertion(new_node);
    }

    /**
     * @brief Method to Delete Node with Zero or One Child
     * @param pNode The node where the deleteion needs to take place
     * @return The node after deletion.
     */
    private Node DeleteNodeWZeroOneChild(Node pNode) {
        if (pNode.left != null) {
            ReplaceChildren(pNode.parent, pNode, pNode.left);
            return pNode.left;
        } else if (pNode.right != null) {
            ReplaceChildren(pNode.parent, pNode, pNode.right);
            return pNode.right;
        } else {
            Node new_node = pNode.color == BLACK ? new NullNode() : null;

            ReplaceChildren(pNode.parent, pNode, new_node);
            return new_node;
        }
    }

    /**
     * @brief Method to find the minimum in the RBTree.
     * @param pNode The node to search from.
     * @return The minimum node.
     */
    private Node FindMin(Node pNode){
        while (pNode.left != null) {
            pNode = pNode.left;
        }

        return pNode;
    }

    /**
     * @brief Method to Handle Red Sibling after deletion
     * @param pNode The node
     * @param pSibNode The sibling node.
     */
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

    /**
     * @brief Method to Handle Siblings with at least One Red Child.
     * @param pNode The node 
     * @param pSibNode the sibling node.
     */
    private void HandleBlackSibWOneRedChild(Node pNode, Node pSibNode) {
        boolean is_node_left_c = pNode == pNode.parent.left;


        if (is_node_left_c && !IsNodeRed(pSibNode.right)) {
            pSibNode.left.color = BLACK;
            pSibNode.color = RED;
            RotateRight(pSibNode);
            pSibNode = pNode.parent.right;

            color_flip_count++;

        } else if (!is_node_left_c && !IsNodeRed(pSibNode.left)) {
            pSibNode.right.color = BLACK;
            pSibNode.color = RED;
            RotateLeft(pSibNode);
            pSibNode = pNode.parent.left;

            color_flip_count++;
        }

        pSibNode.color = pNode.parent.color;
        pNode.parent.color = BLACK;

        color_flip_count++;

        // Rotate the node to the left or right
        if (is_node_left_c) {
            pSibNode.right.color = BLACK;
            RotateLeft(pNode.parent);
        } else {
            pSibNode.left.color = BLACK;
            RotateRight(pNode.parent);
        }
    }
    /**
     * @brief The function to fix the Red Black Tree Property after deletion.
     * @param pNode The node where the property might have been violated.
     */
    private void FixRBTreePropsDeletion(Node pNode) {
        // Set color to BLACK.
        if (pNode == root) {
            pNode.color = BLACK;
            return;
        }

        Node sibling_node = GetSiblingNode(pNode);

        // Handle red siblings of the node.
        if (IsNodeRed(sibling_node)) {
            HandleRedSib(pNode,sibling_node);
            sibling_node = GetSiblingNode(pNode);
        }

        // If the node is not red then make the node is red.
        if (!IsNodeRed(sibling_node.left) && !IsNodeRed(sibling_node.right)) {
            sibling_node.color = RED;

            // Fix the node to Black if red color.
            if (IsNodeRed(pNode.parent)) {
                pNode.parent.color = BLACK;
            } else {
                FixRBTreePropsDeletion(pNode.parent);
            }
        } else {
            HandleBlackSibWOneRedChild(pNode, sibling_node);
        }
    }

    /**
     * @brief Helper function to the DeleteBook function.
     * @param pBook The book to be deleted.
     */
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

    /**
     * @brief The helper function to get the books in range.
     * @param pNode Root node to start the search.
     * @param pStartID startID of the book.
     * @param pEndID endID of the book.
     * @param bookList The arraylist of books in the range.
     */
    private void GetBooksInRangeHelper(Node pNode, int pStartID, int pEndID, List<Books> bookList) {
        // Returns the node that this node is part of.
        if (pNode == null) {
            return; 
        }
        
        // Get all books in the node.
        if (pNode.key.GetBookID() >= pStartID) {
            GetBooksInRangeHelper(pNode.left, pStartID, pEndID, bookList);
        }
        
        // Add a book to the book list.
        if (pNode.key.GetBookID() >= pStartID && pNode.key.GetBookID() <= pEndID) {
            bookList.add(pNode.key);
        }

        // Get all books in the range of the books in the range.
        if (pNode.key.GetBookID() <= pEndID) {
            GetBooksInRangeHelper(pNode.right, pStartID, pEndID, bookList); 
        }
    }
    /**
     * @brief This gets the books in the given range of book IDs
     * @param pStartID startID of the book.
     * @param pEndID endID of the book.
     * @return an array of books in the range.
     */
    public Books[] GetBooksInRange(int pStartID, int pEndID) {
        List<Books> books = new ArrayList<>();
    
        GetBooksInRangeHelper(root, pStartID, pEndID, books);
        
        return books.toArray(new Books[0]);
    }

    /**
     * @brief Method to find the previous book in the tree in order.
     * @param pTargetID target ID of the book.
     * @return the Node prior to the target ID.
     */ 
    private Node FindPrevious(int pTargetID) {
        Node result = null;
        Node curr_node = root;

        // Find the node that is the book that is the current node.
        while (curr_node != null) {
            // Get the node that is the target of the current node.
            if (pTargetID > curr_node.key.GetBookID()) {
                result = curr_node;
                curr_node = curr_node.right;
            } else {
                curr_node = curr_node.left;
            }
        }

        return result;
    }

    /**
     * @brief Method to find the next book in the tree in order.
     * @param pTargetID target ID of the book.
     * @return the Node next to the target ID.
     */
    private Node FindNext(int pTargetID) {
        Node result = null;
        Node curr_node = root;


        // Find the node that is the first node in the tree.
        while (curr_node != null) {

            // Get the node that is the current node.
            if (pTargetID < curr_node.key.GetBookID()) {
                result = curr_node;
                curr_node = curr_node.left;
            } else {
                curr_node = curr_node.right;
            }
        }

        return result;
    }

    /**
     * @brief External method to find the closest books to the given target book ID.
     * @param pTargetID target book ID.
     * @return An array of Books objects closest to the target ID.
     */

    public Books[] FindClosestBooks(int pTargetID) {

        Node prev_node = FindPrevious(pTargetID);
        Node next_node = FindNext(pTargetID);
        List<Books> result = new ArrayList<>();

        // Add the key to the result set.

        if (Math.abs(pTargetID - prev_node.key.GetBookID()) > Math.abs(pTargetID - next_node.key.GetBookID())) {
            result.add(next_node.key);

        // Add the key to the result set.

        } else if (Math.abs(pTargetID - prev_node.key.GetBookID()) < Math.abs(pTargetID - next_node.key.GetBookID())) {
            result.add(prev_node.key);
        } else {
            result.add(prev_node.key);
            result.add(next_node.key);
        }
        
        return result.toArray(new Books[0]);
    }


    /**
     * @brief External method to search if the book is in the library and get the book object of that.
     * @param pBookID the book ID of the book being searched for.
     * @return the Books object.
     */
    public Books GetBook(int pBookID) {
        Node search = SearchBook(pBookID, root);
        

        // Returns the key of the search.

        if (search == null) {
            return null;
        } else {
            return search.key;
        }
    }

    /**
     * @brief External method to Insert a new book into the tree.
     * @param pBook The book to be inserted.
     */
    public void InsertBook(Books pBook) {
        InsertBookHelper(pBook);
    }

    /**
     * @brief External method for deleting a book from the tree.
     * @param pBook the book to be deleted.
     */
    public void DeleteBook(Books pBook) {
        DeleteBookHelper(pBook);
    }

    /**
     * @brief External Method to get color flip count.
     * @return int value of color flip count.
     */
    public int GetColorFlipCount() {
        return color_flip_count;
    }

}
