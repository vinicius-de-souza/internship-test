public class HtmlNode {
    String content;
    int depth;
    NodeType type;

    HtmlNode(String content, int depth, NodeType type) {
        this.content = content;
        this.depth = depth;
        this.type = type;
    }
}
