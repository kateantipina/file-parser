import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Post {
    private String path;
    private String header;
    private String textPost = "";
    Post(String path, String header){
        this.path = path;
        this.header = header;
    }

    private String getDatePost(){
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd.MM");
        return df.format(date);
    }

    private File[] getListFile(String path){

        File folder = new File(path);
        return folder.listFiles();
    }

    private int parseFile(String regExp, String lineInFile){
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(lineInFile);

        if (matcher.find()) {
            return matcher.start();
        }
        else{
            return -1;
        }

    }

    private void formTextPost(String text){
        textPost += text;
    }
    public void getPost() throws IOException {
        String currentDate = getDatePost();
        File[] files = getListFile(path);

        String line;

        String pointOrTest = "";

        String member = "";
        String library = "";

        for (File fl : files) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(fl.getAbsolutePath()), "windows-1251"));//важна кодировка

                member = "";

                formTextPost("." + fl.getName() + " " + currentDate + " " + header + " ");
                while ((line = bufferedReader.readLine()) != null) {

                    String regExp = "П\\.\\d+";

                    if (parseFile(regExp, line) != -1)
                    {
                        pointOrTest = line.substring(parseFile(regExp, line));
                        formTextPost(pointOrTest + "\n");
                    }
                    else {
                        regExp = "ПРОВЕРКА";
                        if (parseFile(regExp, line) != -1) {
                            pointOrTest = line.substring(parseFile(regExp, line));
                            formTextPost(pointOrTest + "\n");
                        }
                    }


                            regExp = "M=";
                            if (parseFile(regExp, line) != -1) {
                                member = line.substring(parseFile(regExp, line) + 2).trim();
                            }

                            regExp = "^I=";
                            String regExp2 = "\\s.+$";
                            if ((parseFile(regExp, line) != -1) && (parseFile(regExp2, line) != -1)) {
                                library = line.replaceAll(regExp, "").replaceAll(regExp2, "");
                                formTextPost("                " + library + "(" + member + ")\n");
                            }
                        }

                }
        }

    public void writeInFile() throws IOException {
        FileWriter writer = new FileWriter("out/catalog.txt", false);
        writer.write(textPost);
        writer.flush();
    }


    public String toString(){
        return textPost;
    }

}
