package com.example.demo.common;

import com.example.demo.pojo.Volunteering;
import com.huaban.analysis.jieba.JiebaSegmenter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.*;

@Slf4j
/*算法不想写注释了,我相信你的实力ヾ(≧▽≦*)o*/
public class ACTrie {

    private static JiebaSegmenter segment = new JiebaSegmenter();
    private Boolean failureStatesConstructed = false;   //是否建立了failure表
    private Node root;                                  //根结点

    public ACTrie() {
        this.root = new Node(true);
    }

    public static List<String> getSignalWord(String words) {
        return segment.sentenceProcess(words);
    }


    public static void main(String[] args) {
        ACTrie trie = new ACTrie();
        trie.addKeyword("导航");
        trie.addKeyword("酒店");
        trie.addKeyword("希尔顿酒店");
        System.out.println(getSignalWord("hello my world deal world"));


        //匹配text，并返回匹配到的pattern
        Collection<Patten_String> PattenStrings = trie.parseText("导航导航到附近的希尔顿酒店");
        for (Patten_String PattenString : PattenStrings) {
            System.out.println(PattenString.start + " " + PattenString.end + "\t" + PattenString.getKeyword());
        }
    }

    public Map<String, Float> getTF(Volunteering x, List<String> keywords, Map<String, Integer> count_all) {
        String content = x.getContent() == null ? "" : x.getContent();
        String title = x.getTitle() == null ? "" : x.getTitle();
        int SUM = getSignalWord(title).size() + getSignalWord(content).size();
        Map<String, Integer> count = new HashMap<>();
        Map<String, Float> tf = new HashMap<>();

        Collection<ACTrie.Patten_String> PattenStrings = parseText(x.getTitle());
        for (val str : PattenStrings) {
            count.put(str.getKeyword(), count.getOrDefault(str.getKeyword(), 0) + 5);
        }

        PattenStrings = parseText(x.getContent());
        for (val str : PattenStrings) {
            count.put(str.getKeyword(), count.getOrDefault(str.getKeyword(), 0) + 1);
        }

        for (val str : keywords) {
            if (count.getOrDefault(str, 0) != 0) count_all.put(str, count.getOrDefault(str, 0) + 1);
            tf.put(str, ((float) count.getOrDefault(str, 0) / SUM));
        }
        return tf;
    }


    /**
     * 添加一个模式串(内部使用字典树构建)
     */
    public void addKeyword(String keyword) {
        if (keyword == null || keyword.length() == 0) {
            return;
        }
        Node currentState = this.root;
        for (Character character : keyword.toCharArray()) {
            currentState = currentState.insert(character);
        }
        currentState.addPattenString(keyword);
    }


    /**
     * 用ac自动机做匹配，返回text里包含的pattern及其在text里的起始位置
     */
    public Collection<Patten_String> parseText(String text) {
        checkForConstructedFailureStates();
        text = (text == null) ? "" : text;

        Node currentState = this.root;
        List<Patten_String> collectedPattenStrings = new ArrayList<>();
        for (int position = 0; position < text.length(); position++) {
            Character character = text.charAt(position);
            currentState = currentState.nextState(character);
            Collection<String> PattenStrings = currentState.PattenString();
            if (PattenStrings == null || PattenStrings.isEmpty()) {
                continue;
            }
            //如果找到的node的PattenString非空，说明有pattern被匹配到了
            for (String PattenString : PattenStrings) {
                collectedPattenStrings.add(new Patten_String(position - PattenString.length() + 1, position, PattenString));
            }
        }
        return collectedPattenStrings;//返回匹配到的所有pattern
    }

    /**
     * 建立Fail表(核心,BFS遍历)
     */
    private void constructFailureStates() {
        Queue<Node> queue = new LinkedList<>();

        for (Node depthOneState : this.root.children()) {
            depthOneState.setFailure(this.root);
            queue.add(depthOneState);
        }
        this.failureStatesConstructed = true;

        while (!queue.isEmpty()) {
            Node parentNode = queue.poll();

            for (Character transition : parentNode.getTransitions()) {
                Node childNode = parentNode.find(transition);
                queue.add(childNode);
                Node failNode = parentNode.getFailure().nextState(transition);
                childNode.setFailure(failNode);

                childNode.addPattenString(failNode.PattenString());
            }
        }
    }

    /**
     * 检查是否建立了Fail表(若没建立，则建立)
     */
    private void checkForConstructedFailureStates() {
        if (!this.failureStatesConstructed) {
            constructFailureStates();
        }
    }

    private static class Node {
        private Map<Character, Node> map;
        private List<String> PattenStrings;
        private Node failure;               //fail指针指向的node
        private Boolean isRoot = false;     //是否为根结点

        public Node() {
            map = new HashMap<>();
            PattenStrings = new ArrayList<>();
        }

        public Node(Boolean isRoot) {
            this();
            this.isRoot = isRoot;
        }

        public Node insert(Character character) {
            Node node = this.map.get(character);
            if (node == null) {
                node = new Node();
                map.put(character, node);
            }
            return node;
        }

        public void addPattenString(String keyword) {
            PattenStrings.add(keyword);
        }

        public void addPattenString(Collection<String> keywords) {
            PattenStrings.addAll(keywords);
        }

        public Node find(Character character) {
            return map.get(character);
        }


        /**
         * 利用父节点fail node来寻找子节点的fail node
         * parseText时找下一个匹配的node
         */
        private Node nextState(Character transition) {
            Node state = this.find(transition);
            if (state != null) {
                return state;
            }
            if (this.isRoot) {
                return this;
            }
            return this.failure.nextState(transition);
        }


        public Collection<Node> children() {
            return this.map.values();
        }

        public Node getFailure() {
            return failure;
        }

        public void setFailure(Node node) {
            failure = node;
        }

        public Set<Character> getTransitions() {
            return map.keySet();
        }


        public Collection<String> PattenString() {
            return this.PattenStrings == null ? Collections.emptyList() : this.PattenStrings;
        }
    }

    public static class Patten_String {
        private final String keyword;   //匹配到的模式串
        private final int start;        //起点
        private final int end;          //终点

        public Patten_String(final int start, final int end, final String keyword) {
            this.start = start;
            this.end = end;
            this.keyword = keyword;
        }

        public String getKeyword() {
            return this.keyword;
        }

        @Override
        public String toString() {
            return super.toString() + "=" + this.keyword;
        }
    }
}

