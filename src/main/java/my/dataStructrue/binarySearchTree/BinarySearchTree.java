package my.dataStructrue.binarySearchTree;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.BiConsumer;

public class BinarySearchTree<K extends Comparable<K>, V> {

    private Node<K, V> root;
    private int count;

    public BinarySearchTree() {
        root = null;
        count = 0;
    }

    public  void preOrder(BiConsumer<K,V> consumer) {
        this.preOrder(root,consumer);
    }
    public  void midOrder(BiConsumer<K,V> consumer) {
        this.midOrder(root,consumer);
    }
    public  void postOrder(BiConsumer<K,V> consumer) {
        this.postOrder(root,consumer);
    }

    public void levelOrder(BiConsumer<K, V> consumer) {
        ArrayBlockingQueue<Node<K,V>> queue=new ArrayBlockingQueue<>(this.count);
        queue.add(root);
        while (!queue.isEmpty()){
            Node<K, V> node = queue.remove();
            consumer.accept(node.key,node.value);

            if (node.left!=null){
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }


    }


    /**
     * 在c++中删除树，整个左右节点都 删除了，再删除根节点
     * @param root
     * @param consumer
     */
    private void postOrder(Node<K, V> root, BiConsumer<K,V> consumer) {
        if (root == null) {
            return;
        }
        this.postOrder(root.left,consumer);
        this.postOrder(root.right,consumer);
        consumer.accept(root.key,root.value);
    }

    private void midOrder(Node<K, V> root, BiConsumer<K,V> consumer) {
        if (root == null) {
            return;
        }
        this.midOrder(root.left,consumer);
        consumer.accept(root.key,root.value);
        this.midOrder(root.right,consumer);
    }

    private void preOrder(Node<K, V> root, BiConsumer<K,V> consumer) {
        if (root == null) {
            return;
        }
        consumer.accept(root.key,root.value);
        this.preOrder(root.left,consumer);
        this.preOrder(root.right,consumer);
    }



    public Optional<V> search(K key) {
        return this.search(root, key);
    }

    private Optional<V> search(Node<K, V> root, K key) {
        if (root == null) {
            return Optional.empty();
        }

        int i = key.compareTo(root.key);
        if (i == 0) {
            return Optional.ofNullable(root.value);
        } else if (i < 0) {
            return search(root.left, key);
        } else {
            return search(root.right, key);
        }
    }

    public boolean contain(K key) {
        return this.contain(root, key);
    }

    private boolean contain(Node<K, V> root, K key) {

        if (root == null) {
            return false;
        }
        int i = key.compareTo(root.key);
        if (i == 0) {
            return true;
        }

        if (i < 0) {
            return this.contain(root.left, key);
        } else {
            return this.contain(root.right, key);
        }

    }

    public void insert(K key, V value) {
        this.root = this.insert(root, key, value);

    }

    private Node<K, V> insert(Node<K, V> root, K key, V value) {
        if (root == null) {
            root = new Node<>(key, value);
            count++;
            return root;
        }

        int i = key.compareTo(root.key);
        if (i == 0) {
            root.value = value;
        } else if (i < 0) {
            root.left = insert(root.left, key, value);
        } else {
            root.right = insert(root.right, key, value);
        }
        return root;

    }

    @Override
    public String toString() {
        System.out.println("----");
        this.midOrder(root,(k, v) -> System.out.print(k+" "));
        System.out.println();
        System.out.println("----");
        return "";

    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return root == null;
    }


    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;


        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }
}
