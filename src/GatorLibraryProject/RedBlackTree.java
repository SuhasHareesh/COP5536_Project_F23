package src.GatorLibraryProject;
// public class treesRedBlack {


//     private int color_flip_count    = 0;
    
//     public int GetColorFlipCount() {
//         return color_flip_count;
//     }
// }


// import java.util.NoSuchElementException;


// TODO
// Revamp this whole class to suit the need of this assignment

public class RedBlackTree<T extends Comparable<T>> {

  private static final boolean RED      = true;
  private static final boolean BLACK    = false;

  private Node root;

  private class Node {
    T key;
    boolean color; 
    Node left, right;

    Node(T key) {
      this.key = key;
      this.color = RED;
    }
  }

  public void insert(T key) {
    root = insertHelper(root, key);
    root.color = BLACK;
  }

  private Node insertHelper(Node h, T key) {
    if (h == null) {
      return new Node(key);
    }

    if (key.compareTo(h.key) < 0) {
      h.left = insertHelper(h.left, key);
    } else if (key.compareTo(h.key) > 0) {
      h.right = insertHelper(h.right, key);
    } else {
      // ignore duplicate keys
      return h;
    }

    if (isRed(h.right) && !isRed(h.left)) {
      h = rotateLeft(h);
    }
    if (isRed(h.left) && isRed(h.left.left)) {
      h = rotateRight(h);
    }

    if (isRed(h.left) && isRed(h.right)) { 
      flipColors(h);
    }

    return h;
  }

  public boolean contains(T key) {
    return containsHelper(root, key);
  }

  private boolean containsHelper(Node x, T key) {
    if (x == null) {
      return false;
    }

    int cmp = key.compareTo(x.key);

    if (cmp < 0) {
      return containsHelper(x.left, key);
    } else if (cmp > 0) {
      return containsHelper(x.right, key);
    } else { 
      return true;
    }
  }

  // other methods like delete, min, max etc.

  // helper methods
  private boolean isRed(Node x) {
    if (x == null) {
      return false; 
    }
    return x.color == RED;
  }

  private Node rotateLeft(Node h) {
    Node x = h.right;
    h.right = x.left;
    x.left = h;
    x.color = h.color;
    h.color = RED;
    return x;
  }

  private Node rotateRight(Node h) {
    Node x = h.left;
    h.left = x.right;
    x.right = h;
    x.color = h.color;
    h.color = RED;
    return x;
  }

  private void flipColors(Node h) {
    h.color = !h.color;
    h.left.color = !h.left.color;
    h.right.color = !h.right.color;
  }
}