package assets;

/**
 *
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-5-12
 * @version 1.0
 *
 * Class for prevent cross site scripting (xss)
 *
 * */

public class XSS {
    public String hsc(String content) {
        if (content != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < content.length(); i++) {
                char c = content.charAt(i);
                switch (c) {
                    case '<':
                        sb.append("&lt;");
                        break;
                    case '>':
                        sb.append("&gt;");
                        break;
                    case '&':
                        sb.append("&amp;");
                        break;
                    case '"':
                        sb.append("&quot;");
                        break;
                    case '\'':
                        sb.append("&apos;");
                        break;
                    default:
                        sb.append(c);
                }
            }
            return sb.toString();
        }else
            return null;
    }
}