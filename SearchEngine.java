import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num; //number of elements
    String[] wordBank = new String[100];

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            String result = "";
            for(int i = 0; i < wordBank.length; i++){
                result += wordBank[i];
            }
            return String.format("Search results: %s", result);
        } else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("word")) {
                    String wordToSearch = String.parseString(parameters[1]);
                    boolean found = false;
                    for (int i=0; i < wordBank.length; i++) {
                        if (wordBank[i] == wordToSearch) {
                            found = true;
                        }
                    }
                    if (found) {
                        return String.format("%s is found in the word bank", wordToSearch);
                    } else {
                        return String.format("%s is not found...", wordToSearch);
                    }
                }



            return String.format("Number incremented!");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("word")) {
                    String newWord = String.parseString(parameters[1]);
                    wordBank[num] = newWord;
                    num += 1;
                    return String.format(" %s is added to word-bank!", parameters[1]);
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
