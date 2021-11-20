package task7;

import util.BSTOperations;
import util.BinarySearchTreeNode;

public class Main {
    private static <T extends Comparable<T>> void searchValue(BinarySearchTreeNode<T> binarySearchTree, T value) {
        if (binarySearchTree != null) {
            if (value.equals(binarySearchTree.getValue())) {
                System.out.println("Found value: " + binarySearchTree.getValue());
            } else if (binarySearchTree.getValue().compareTo(value) > 0) {
                searchValue(binarySearchTree.getLeft(), value);
            } else {
                searchValue(binarySearchTree.getRight(), value);
            }
        }
    }

    public static void main(String[] args) {
        BinarySearchTreeNode<Integer> binarySearchTree = new BinarySearchTreeNode<>(3);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 6);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 9);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 2);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 10);
        binarySearchTree = BSTOperations.insertNode(binarySearchTree, 1);

        searchValue(binarySearchTree, 5);
    }
}
