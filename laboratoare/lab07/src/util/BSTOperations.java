package util;

public class BSTOperations {
    public static <T extends Comparable<T>> BinarySearchTreeNode<T> insertNode(BinarySearchTreeNode<T> node, T value) {
        if (node == null) {
            node = new BinarySearchTreeNode<>(value);
            return node;
        }

        if (node.getValue().compareTo(value) >= 0) {
            node.setLeft(insertNode(node.getLeft(), value));
        } else if (node.getValue().compareTo(value) <= 0) {
            node.setRight(insertNode(node.getRight(), value));
        }

        return node;
    }
}
