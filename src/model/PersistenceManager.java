package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jon on 10/11/16.
 */
public class PersistenceManager {
    private static Logger LOGGER = Logger.getLogger("PersistenceManager");

    private List<Location> model;
    private List<Account> modeled;
    private List<Report> modeling;

    /**
     * Allows for persistence.
     * @param m Model that is passed in
     */
    public PersistenceManager(List<Location> m) {
        model = m;
    }

//    public PersistenceManager(List<Report> m) {
//        modeling = m;
//    }
//
//    public PersistenceManager(List<Account> m) {
//        modeled = m;
//    }
//
    /**
     * Saves file to Json
     * @param file File to be saved
     */
    public void saveToJson(File file) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            Gson gson = new Gson();
            String str = gson.toJson(model);
            pw.println(str);
            pw.close();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception working with Json Save File", e);
        }
    }

    /**
     * Loads from Json file
     * @param file File to load from
     */
    public void loadFromJsonfile(File file) {
        String ct = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            Type collectionType = new TypeToken<List<Location>>(){}.getType();
            Gson gson = new Gson();

            ct = br.readLine();

            model = gson.fromJson(ct, collectionType);

            br.close();
        } catch (IOException ex) {
              LOGGER.log(Level.SEVERE, "Exception working with Json load file", ex);
        }
    }
}
