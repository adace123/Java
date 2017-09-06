//Aaron Feigenbaum
//SMC - CS 20B
//Project 1 - Text File Analyzer

import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class FileStatistics {

    public static void main(String[] args) {
        FileHandler handler = new FileHandler();
        handler.getCategoryTable().addCategory("Letters", "[A-Za-z]");
        handler.getCategoryTable().addCategory("White Space", "\\s");
        handler.getCategoryTable().addCategory("Digits", "\\d");
        handler.getCategoryTable().addCategory("Other", "[^\\d\\s\\w]");
        handler.readFile("src/data.dat");
        System.out.println(handler.dumpResults());

    }
}

class FileHandler {

    private CategoryTable table;
    private File file;
    private String fileString = "";

    public FileHandler() {
        this.table = new CategoryTable();
    }

    //read the file passed in and store contents as a string
    public void readFile(String filePath) {

        this.file = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(this.file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                this.fileString += line;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    //print out file stats
    public String dumpResults() {
        try {
            String results = "";

            results += "Statistics for file: " + this.getFile().getName() + "\n";
            results += "-----------------------------------" + "\n\n";
            results += "Total # of characters: " + this.getTotalNumCharacters() + "\n";
            results += "Total # of words: "  + this.getNumWords() + "\n\n";
            results += "Category       How Many?       % of file\n";
            results += this.table.processString(this.fileString);

            return results;
        } catch(Exception e) {
            System.out.println("Error. File path not specified.");
            System.exit(1);
            return null;
        }
    }
    
    public int getTotalNumCharacters() {
        return this.fileString.length();
    }

    public CategoryTable getCategoryTable() {
        return this.table;
    }

    public File getFile() {
        return this.file;
    }

    public int getNumWords() {
      //regex looks for either a space or non alphanumeric character
      //followed by alphabetic character
        return Arrays.asList(this.fileString.split("\\s|[^A-Za-z\\d](?=[A-Za-z])")).size();
    }

}

class CategoryTable {

    private List<Category> categories;
    private int totalNumCategories = 0;

    public CategoryTable() {
        this.categories = new ArrayList<>();
    }

    //add new Category object to list
    public void addCategory(String name, String criteria) {
        this.categories.add(new Category(name, criteria));
        this.totalNumCategories++;
    }

    //loop through categories in list and build up stats string
    public String processString(String data) {
        String statistics = "";
        for(Category c: this.categories) {
            //each category object searches for matches in string based on its respective criteria
            c.findPattern(data);
            String percentage = String.format("%.2f", (double) c.getHowMany()/data.length() * 100) + "%\n";
            statistics += c.getName() + String.format("%" + (21 - c.getName().length()) + "s"," ")
                       + c.getHowMany() + String.format("%" + (4 + c.getName().length()) + "s"," ") + percentage;
        }
        return statistics;
    }

    public void clearAllCategories() {
        this.categories.clear();
    }

    public int getTotalNumCategories() {
        return this.totalNumCategories;
    }

    public List<Category> getCategoryList() {
        return this.categories;
    }
    
    //can only return category in list if valid index passed in
    public Category getCategory(int index) {
        return index >=0 && index <= this.categories.size() - 1 ? this.categories.get(index) : null;
    }
}

class Category {

    private String name;
    private int HowMany;
    private String criteriaMatch;

    public Category(String name, String criteria) {
        this.name = name;
        this.criteriaMatch = criteria;
    }

    // public boolean characterMatchesCriteria(String ch) {
    //   return ch.equals(this.criteriaMatch);
    // }

    //use regex to find matches in string based on criteria and increment HowMany if match found
    public void findPattern(String search) {
        Pattern p = Pattern.compile(this.criteriaMatch);
        Matcher m = p.matcher(search);
        while(m.find()) {
            this.HowMany++;
        }
    }

    public String getName() {
        return this.name;
    }

    public int getHowMany() {
        return this.HowMany;
    }


}
