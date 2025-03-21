import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
public class Finder {
    static HashMap<String , List<String>> priority_destination= new HashMap<>();
    static HashMap<String, List<String>> search_results=new HashMap<>();
    static void readConfig() throws FileNotFoundException, IOException{
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
    public static void main(String args[]){
        Scanner scn=new Scanner(System.in);
        String home_path=System.getProperty("user.home");
        System.out.println(home_path);
        try{
            loadHistory();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        finally{
            System.out.println("Reading config done.");
        }
        System.out.print("What file would you like to search for? ");
        String input=scn.nextLine();
        if(search_results.containsKey(input)){
            System.out.println("PDF: "+search_results.get(input.toLowerCase()));
        }else{
            System.out.println("The defined file type is not mentioned in the configuration file.");
        }
    }
}
