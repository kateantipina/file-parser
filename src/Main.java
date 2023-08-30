
public class Main {
    public static void main(String[] args) {
        Post post = new Post("data/", "name_as dame_doc");
        try {
            post.getPost();
        }
        catch(Exception ex)
        {
            ex.getStackTrace();
        }

        System.out.println(post.toString());
        try {
            post.writeInFile();
        }
        catch(Exception ex)
        {
            ex.getStackTrace();
        }
    }

}