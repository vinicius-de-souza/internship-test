import java.io.IOException;

public class HtmlAnalyzer {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.exit(1);
        }

        String url = args[0];
        HtmlParser parser = new HtmlParser();

        try {
            String htmlContent = UrlFetcher.fetchContent(url);
            String result = parser.findDeepestText(htmlContent);
            System.out.println(result);
        } catch (IOException e) {
            System.out.println("URL connection error");
            System.exit(1);
        }
    }
}

