import java.io.File;
public class filedlete {
    void deletefile(){
        String path="temp";
        File file = new File(path);
        File[] files = file.listFiles();
        for (File f:files)
        {
            if (f.isFile() && f.exists())
            {
                f.delete();
//                System.out.println("successfully deleted");
            }else{
//                System.out.println("cant delete a file due to open or error");
            }
        }
    }

    public static void main(String[] args) {
        new filedlete().deletefile();
    }
}