import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {

  // The one bit of state on the server: a number that will be manipulated by
  // various requests.
  ArrayList<String> items = new ArrayList<String>();

  public String handleRequest(URI url) {
    String item_list = "";
    if (url.getPath().equals("/")) {
      for (String string : items) {
        item_list += " " + string;
      }
      return item_list;
    } else {
      if (url.getPath().contains("/add")) {
        String[] parameters = url.getQuery().split("=");
        if (parameters[0].equals("s")) {
          items.add(parameters[1]);
          return String.format("Item %s add!", parameters[1]);
        }
      }

      if (url.getPath().contains("/search")) {
        String[] parameters = url.getQuery().split("=");
        if (parameters[0].equals("s")) {
          String result = "";
          for (String string : items) {
            if (string.contains(parameters[1])) {
              result += string;
            }
          }
          return result;
        }
      }
      return "404 not found";
    }
  }
}

class SearchEngine {

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      System.out.println(
        "Missing port number! Try any number between 1024 to 49151"
      );
      return;
    }

    int port = Integer.parseInt(args[0]);

    Server.start(port, new Handler());
  }
}
