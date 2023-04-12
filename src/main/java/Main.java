import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main { 
    
    public static char[] sizeMultipliers = {'B', 'K', 'M', 'G', 'T'};
    public static void main(String[] args) {

       
//        MyThread thread = new MyThread(1);
//        MyThread thread2 = new MyThread(2);
//
//        thread.start();
//        thread2.start();

        
       String folderPath = "C:/Users/пользователь/Desktop";

        File file = new File(folderPath);

        long start = System.currentTimeMillis();
       // System.out.println(file.length());//объём ссылок на папки
       // System.out.println(getFolderSize(file));
        FolderSizeCalculator calculator = new FolderSizeCalculator(file);

        ForkJoinPool pool = new ForkJoinPool();
        long size = pool.invoke(calculator);
        String humanSize = getHumanReadableSize(size);
        System.out.println(humanSize);

        long duration = System.currentTimeMillis() - start;
        System.out.println(duration + " milliseconds");
        System.out.println(getSizeFromHumanReadable(humanSize));

    }

    public  static long getFolderSize(File folder){
        if(folder.isFile()){
            return folder.length();
        }

        long sum = 0;
        File[] files = folder.listFiles();
        for (File file : files){
            sum += getFolderSize(file);
        }
        return sum;
    }

    public static String getHumanReadableSize(long size){
        for (int i =0; i < sizeMultipliers.length; i++){
            double value = size / Math.pow(1024, i);
            if (value < 1024){
                return Math.round(value) + "" + sizeMultipliers[i] +
                        ((i > 0) ? "b" : "");
            }
        }
       return "Very big!";
     
      /*  String result = "";
        long TB = 1024*1024*1024*1024;
        long GB = 1024*1024*1024;
        long MB = 1024*1024;
       // if (size/TB>1){
         //   result = size/TB + "TB";
        //} else
        if(size/GB>1){
            result = size/GB + "GB";
        } else if(size/MB>1){
            result = size/MB + "MB";
        } else if(size/1024 >1){
            result = size/1024 + "KB";
        } else {
            result = size/8 + "B";
        }
        return result;*/
    }

    public static long getSizeFromHumanReadable(String size){
       /* long result = 0;
        long TB = 1024*1024*1024*1024;
        long GB = 1024*1024*1024;
        long MB = 1024*1024;
        long KB = 1024;
        long B = 8;
        String templateB = "[0-9]*[B]{1}";
        String templateKB = "[0-9]*[K]{1}[B]{1}";
        String templateK = "[0-9]*[K]{1}";
        String templateMB = "[0-9]*[M]{1}[B]{1}";
        String templateM = "[0-9]*[M]{1}";
        String templateGB = "[0-9]*[G]{1}[B]{1}";
        String templateG = "[0-9]*[G]{1}";
        String templateTB = "[0-9]*[T]{1}[B]{1}";
        String templateT = "[0-9]*[T]{1}";
        if (size.matches(templateB))
        {
            result = Long.parseLong(size.replaceAll("[B]{1}", ""))*B;
        } else if(size.matches(templateK)||size.matches(templateKB)){
            result = Long.parseLong(size.replaceAll("[K]{1}[B]?", ""))*KB;
        } else if(size.matches(templateM)||size.matches(templateMB)){
            result = Long.parseLong(size.replaceAll("[M]{1}[B]?", ""))*MB;
        } else if(size.matches(templateG)||size.matches(templateGB)){
            result = Long.parseLong(size.replaceAll("[G]{1}[B]?", ""))*GB;
        } else if(size.matches(templateT)||size.matches(templateTB)){
            result = Long.parseLong(size.replaceAll("[T]{1}[B]?", ""))*TB;
        }
        return result;*/
        HashMap<Character, Integer> char2multiplier = getMultipliers();
        char sizeFactor = size
                .replaceAll("[0-9\\s+]+", "")
                .charAt(0);
        int multiplier = char2multiplier.get(sizeFactor);
        long length = Long.valueOf(size.replaceAll("[^0-9]", ""))*multiplier;
        return length;
    }
    
    private static HashMap<Character, Integer> getMultipliers(){
        HashMap<Character, Integer> char2multiplier = new HashMap<>();
        for (int i = 0; i < sizeMultipliers.length; i++){
            char2multiplier.put(sizeMultipliers[i], (int) Math.pow(1024, i));
        }
        return char2multiplier;
    }
}