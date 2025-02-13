import java.util.Stack;

public class HtmlParser {
    private static final String MALFORMED_HTML = "malformed HTML";
    private static final String EMPTY_STRING = "";
    
    private final Stack<String> tagStack;
    private HtmlNode deepestText;
    
    public HtmlParser() {
        this.tagStack = new Stack<>();
        this.deepestText = null;
    }

    public String findDeepestText(String htmlContent) {
        // Reset state for new parsing
        tagStack.clear();
        deepestText = null;
        
        System.out.println("Starting HTML parsing...");
        System.out.println("HTML received on HtmlParser: " + htmlContent);
        
        String[] lines = htmlContent.split("\n");
        for (String originalLine : lines) {
            String line = originalLine.trim();
            if (line.isEmpty()) continue;
            
            int pos = 0;
            while (pos < line.length()) {
                String element;
                if (line.charAt(pos) == '<') {
                    int endPos = line.indexOf('>', pos) + 1;
                    if (endPos == 0) break;
                    element = line.substring(pos, endPos).trim();
                    pos = endPos;
                } else {
                    int endPos = line.indexOf('<', pos);
                    if (endPos == -1) endPos = line.length();
                    element = line.substring(pos, endPos).trim();
                    pos = endPos;
                }
                
                if (element.isEmpty()) continue;
                
                NodeType type = getNodeType(element);
                System.out.println("Element: '" + element + "' -> Type: " + type);
                
                switch (type) {
                    case OPENING_TAG:
                        String tag = extractTag(element);
                        System.out.println("Opening tag found: '" + tag + "'");
                        tagStack.push(tag);
                        System.out.println("Current stack depth: " + tagStack.size());
                        break;
                        
                    case CLOSING_TAG:
                        String closingTag = extractTag(element);
                        System.out.println("Closing tag found: '" + closingTag + "'");
                        if (tagStack.isEmpty() || !tagStack.peek().equals(closingTag)) {
                            System.out.println("Malformed HTML detected: stack empty or tag mismatch");
                            return MALFORMED_HTML;
                        }
                        tagStack.pop();
                        break;
                        
                    case TEXT:
                        if (!element.trim().isEmpty()) {
                            int currentDepth = tagStack.size();
                            System.out.println("Text found at depth " + currentDepth + ": '" + element + "'");
                            if (deepestText == null || currentDepth > deepestText.depth) {
                                System.out.println("New deepest text found!");
                                deepestText = new HtmlNode(element, currentDepth, type);
                            }
                        }
                        break;
                }
            }
        }
        
        if (!tagStack.isEmpty()) {
            System.out.println("Stack not empty at end: " + tagStack);
            return MALFORMED_HTML;
        }
        
        return deepestText != null ? deepestText.content : EMPTY_STRING;
    }

    private NodeType getNodeType(String line) {
        // Ignore DOCTYPE
        if (line.startsWith("<!DOCTYPE")) {
            return NodeType.TEXT;
        }
        
        // Ignore comments
        if (line.startsWith("<!--")) {
            return NodeType.TEXT;
        }
        
        // Handle self-closing tags
        if (line.contains("/>") || 
            line.startsWith("<meta") || 
            line.startsWith("<link") || 
            line.startsWith("<img") || 
            line.startsWith("<br") || 
            line.startsWith("<hr")) {
            return NodeType.TEXT;
        }
        
        // Original logic for other tags
        if (line.startsWith("</")) {
            return NodeType.CLOSING_TAG;
        } else if (line.startsWith("<") && line.endsWith(">")) {
            return NodeType.OPENING_TAG;
        } else {
            return NodeType.TEXT;
        }
    }

    private String extractTag(String line) {
        int closeIndex = line.indexOf('>');
        if (closeIndex == -1) return EMPTY_STRING;
        
        if (line.startsWith("</")) {
            return line.substring(2, closeIndex).trim();
        }
        return line.substring(1, closeIndex).trim();
    }
}
