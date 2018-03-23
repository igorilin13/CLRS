package igorilin13.com.github.main.datastructures.tree;

import java.util.LinkedHashMap;
import java.util.List;

public class TreeWalkResult<K> {
    private final List<TreeNode<K>> walk;

    TreeWalkResult(List<TreeNode<K>> walk) {
        this.walk = walk;
    }

    public LinkedHashMap<K, TreeNode<K>> toMap() {
        LinkedHashMap<K, TreeNode<K>> result = new LinkedHashMap<>();
        for (TreeNode<K> node : walk) {
            result.put(node.getKey(), node);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (TreeNode<K> node : walk) {
            res.append(node.getKey().toString());
        }
        return res.toString();
    }
}
