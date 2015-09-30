import java.util.Scanner;
import java.util.Stack;

/**
 * Created by netadmin on 26/09/2015.
 *

 */
public class CheckDelimiterBalance {

  private static final char LEFT_PAREN = '(';
  private static final char RIGHT_PAREN = ')';
  private static final char LEFT_BRACE = '{';
  private static final char RIGHT_BRACE = '}';
  private static final char LEFT_BRACKET = '[';
  private static final char RIGHT_BRACKET = ']';

  private static final String COMMENT_DELIM = "/";
  private static final String ASTERISX = "*";
  private static final String NEW_LINE = System.getProperty("line.separator");


  public static boolean checkDelimiterBalance(String s) {

    
/*
((( )(( )){([( )])}))
 */
    //use a stack of Chararacters to hold unbalanced parens etc

    Stack<Character> stack = new Stack<Character>();
    for (int i = 0; i < s.length(); i++) {

      if (s.charAt(i) == LEFT_PAREN) {
        stack.push(LEFT_PAREN);
      } else if (s.charAt(i) == LEFT_BRACE) {
        stack.push(LEFT_BRACE);
      } else if (s.charAt(i) == LEFT_BRACKET) {
        stack.push(LEFT_BRACKET);
      } else if (s.charAt(i) == RIGHT_PAREN) {
        if (stack.isEmpty()) {
          return false;
        }
        if (stack.pop() != LEFT_PAREN) {
          return false;
        }
      } else if (s.charAt(i) == RIGHT_BRACE) {
        if (stack.isEmpty()) {
          return false;
        }
        if (stack.pop() != LEFT_BRACE) {
          return false;
        }
      } else if (s.charAt(i) == RIGHT_BRACKET) {
        if (stack.isEmpty()) {
          return false;
        }
        if (stack.pop() != LEFT_BRACKET) {
          return false;
        }
      }

      // ignore all other characters

    }
    return stack.isEmpty();
  }


  public static boolean checkBalance(String src) {

    Stack<Character> stack = new Stack<Character>();

    boolean ignoreString = false;
    boolean ignoreComment = false;

    char ch = 0;

    for (int i = 0; i < src.length(); i++) {
      ch = src.charAt(i);

      //start of ignored String section  if not already in Comment section

      if ((ch == '\'' || ch == '"') && (ignoreString == false && ignoreComment == false)) {
        ignoreString = true;
      }
      //ignore String will only be true if not in a comment
      else if ((ch == '\'' || ch == '"') && ignoreString == true) {
        ignoreString = false;
      }

      // start of ignored Comment section if not in String section
      else if (ch == '/' && (ignoreComment == false && ignoreString == false)) {

        if (src.charAt(i + 1) == '/' || src.charAt(i + 1) == '*') {
          ignoreComment = true; //set it to true
        }

      }

      //found end of comment
      else if (ignoreString == false && ((ch == '\n' || (ch == '*' && src.charAt(i + 1) == '/'))
                                         && ignoreComment == true)) {
        ignoreComment = false;
      }

      //test the char if we are not in a String or Comment
      if (ignoreString == false && ignoreComment == false) {

        if (ch == LEFT_PAREN) {
          stack.push(LEFT_PAREN);
        } else if (ch == LEFT_BRACE) {
          stack.push(LEFT_BRACE);
        } else if (ch == LEFT_BRACKET) {
          stack.push(LEFT_BRACKET);
        } else if (ch == RIGHT_PAREN) {
          if (stack.isEmpty()) {
            return false;
          }
          if (stack.pop() != LEFT_PAREN) {
            return false;
          }
        } else if (ch == RIGHT_BRACE) {
          if (stack.isEmpty()) {
            return false;
          }
          if (stack.pop() != LEFT_BRACE) {
            return false;
          }
        } else if (ch == RIGHT_BRACKET) {
          if (stack.isEmpty()) {
            return false;
          }
          if (stack.pop() != LEFT_BRACKET) {
            return false;
          }
        }

      }
    }

    return stack.isEmpty();
  }



  public static void main(String[] args) {
    String s = "((( )(( )){([( )])}))";
    s = "((( //some ][ code \n /* ][ */ \" /* [ ]] */ \" \' /* /* ][ */  \' )(( )){([( )])}))";
    System.out.println(checkBalance(s));
  }

}
