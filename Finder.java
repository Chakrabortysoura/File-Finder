import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
public class Finder {
    static HashMap<String , List<String>> priority_destination= new HashMap<>();
    static void readConfig() throws FileNotFoundException, IOException{
        if (priority_destination.isEmpty()){
            InputStreamReader config_data=null;
            BufferedReader reader=null;

            config_data=new InputStreamReader(new FileInputStream("search_priority_config.txt"));
            reader= new BufferedReader(config_data);

            String line= reader.readLine();
            while(line!=null){
                String[] line_array=line.split(":");
                priority_destination.put(line_array[0].strip(), Arrays.stream(line_array[1].split(",")).collect(
                        ArrayList::new,
                        (a, s)->a.add(s.strip()) ,
                        ArrayList::addAll));
                line= reader.readLine();
            }
            config_data.close();
            reader.close();
        }
    }
    public static void main(String args[]){
        Scanner scn=new Scanner(System.in);
        String home_path=System.getProperty("user.home");
        System.out.println(home_path);
        try{
            readConfig();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        finally{
            System.out.println("Reading config done.");
        }
        System.out.print("Which file type would you like to search? ");
        String input=scn.nextLine();
        if(priority_destination.containsKey(input)){
            System.out.println("PDF: "+priority_destination.get(input.toLowerCase()));
        }else{
            System.out.println("The defined file type is not mentioned in the configuration file.");
        }
    }
}
