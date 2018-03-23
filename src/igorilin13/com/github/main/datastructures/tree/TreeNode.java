package igorilin13.com.github.main.datastructures.tree;

public class TreeNode<K> {
    private K key;
    private TreeNode<K> parent;
    private TreeNode<K> left;
    private TreeNode<K> right;

    TreeNode(K key) {
        this.key = key;
    }

    void setParent(TreeNode<K> parent) {
        this.parent = parent;
    }

    void setLeft(TreeNode<K> left) {
        this.left = left;
    }

    void setRight(TreeNode<K> right) {
        this.right = right;
    }

    public K getKey() {
        return key;
    }

    public TreeNode<K> getParent() {
        return parent;
    }

    public TreeNode<K> getLeft() {
        return left;
    }

    public TreeNode<K> getRight() {
        return right;
    }
}
