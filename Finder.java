import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

class Search implements Runnable{
    String base_dir, r_path, search_term;
    Search(String base_dir,String r_path, String search_term){
        this.base_dir=base_dir;
        this.r_path=r_path;
        this.search_term=search_term;
    }
    public void run(){
        System.out.println("The base directory for the search is: "+base_dir);
        String[] contains=new File(base_dir+"/"+r_path).list();
        if (contains!=null){
            for(String i: contains){
                if(new File(base_dir+"/"+r_path+"/"+i).isDirectory() && i.charAt(0)!='.') {
                    System.out.println(base_dir+"/"+i);
                }else if (!new File(base_dir+"/"+r_path+"/"+i).isHidden()){
                    System.out.println(i);
                }
            }
        }
    }
}
public class Finder {
    static HashMap<String , List<String>> priority_destination= new HashMap<>();
    static HashMap<String, List<String>> search_results=new HashMap<>();
    static void readConfig() throws IOException{
        //Function that reads from the  config file
        //this Config file stores the priority search for the specific file extensions when searched

        if (priority_destination.isEmpty()){
            BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream("search_priority_config.txt")));

            String line= reader.readLine();
            while(line!=null){
                String[] line_array=line.split(":");
                priority_destination.put(line_array[0].strip(), Arrays.stream(line_array[1].split(",")).collect(
                        ArrayList::new,
                        (a, s)->a.add(s.strip()) ,
                        ArrayList::addAll));
                line= reader.readLine();
            }
            reader.close();
        }
    }
    static void loadHistory() throws IOException{
        //Function that reads from the previously searched results for a specific keyword

        if(search_results.isEmpty()){
            BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream("source.txt")));

            String line=reader.readLine();
            while(line!=null){
                String[] line_array=line.split(":");
                search_results.put(line_array[0].strip().toLowerCase(), Arrays.stream(line_array[1].split(",")).collect(
                        ArrayList::new,
                        (a, s)-> a.add(s.strip()),
                        ArrayList::addAll
                ));
                line=reader.readLine();
            }
        }
    }
    public static void main(String[] args){
        String home_path=System.getProperty("user.home");
        String search_term=args[0]; //The search always starts from the user's home directory and all the paths are checked with priority from the config file in mind

        Thread t1 = new Thread(new Search(home_path, "", search_term));
        t1.start();
        try{
            t1.join();
        }catch(InterruptedException e){
            System.out.println("Thread execution was interrupted");
        }
    }
}
