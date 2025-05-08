package hus.daa.app.algorithm.tst;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TernarySearchTree {
    private TSTNode root;

    public TernarySearchTree() {
        this.root = null;
    }

    // Chèn từ vào TST
    public void insert(String word) {
        if (word == null || word.isEmpty())
            return;
        root = insert(root, word, 0);
    }

    private TSTNode insert(TSTNode node, String word, int index) {
        if (node == null) {
            node = new TSTNode(word.charAt(index));
        }

        if (word.charAt(index) < node.data) {
            node.left = insert(node.left, word, index);
        } else if (word.charAt(index) > node.data) {
            node.right = insert(node.right, word, index);
        } else {
            if (index < word.length() - 1) {
                node.middle = insert(node.middle, word, index + 1);
            } else {
                node.isEndOfWord = true;
            }
        }

        return node;
    }

    // Tìm kiếm từ trong TST
    public boolean search(String word) {
        if (word == null || word.isEmpty())
            return false;
        TSTNode node = searchNode(root, word, 0);
        return (node != null && node.isEndOfWord);
    }

    private TSTNode searchNode(TSTNode node, String word, int index) {
        if (node == null)
            return null;

        if (word.charAt(index) < node.data) {
            return searchNode(node.left, word, index);
        } else if (word.charAt(index) > node.data) {
            return searchNode(node.right, word, index);
        } else {
            if (index == word.length() - 1)
                return node;
            return searchNode(node.middle, word, index + 1);
        }
    }

    // Lấy tất cả từ có tiền tố prefix
    public List<String> getWordsWithPrefix(String prefix) {
        List<String> result = new ArrayList<>();
        if (prefix == null || prefix.isEmpty())
            return result;

        TSTNode subtreeRoot = searchPrefix(root, prefix, 0);
        if (subtreeRoot == null)
            return result;

        if (subtreeRoot.isEndOfWord)
            result.add(prefix);

        collectWords(subtreeRoot.middle, prefix, result);
        return result;
    }

    private TSTNode searchPrefix(TSTNode node, String prefix, int index) {
        if (node == null)
            return null;

        if (prefix.charAt(index) < node.data) {
            return searchPrefix(node.left, prefix, index);
        } else if (prefix.charAt(index) > node.data) {
            return searchPrefix(node.right, prefix, index);
        } else {
            if (index == prefix.length() - 1)
                return node;
            return searchPrefix(node.middle, prefix, index + 1);
        }
    }

    private void collectWords(TSTNode node, String prefix, List<String> result) {
        if (node == null)
            return;

        collectWords(node.left, prefix, result);

        String newPrefix = prefix + node.data;
        if (node.isEndOfWord)
            result.add(newPrefix);

        collectWords(node.middle, newPrefix, result);
        collectWords(node.right, prefix, result);
    }
}
