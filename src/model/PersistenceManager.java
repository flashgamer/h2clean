package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Jon on 10/11/16.
 */
class PersistenceManager {
    private static final Logger LOGGER = Logger.getLogger("PersistenceManager");

    private List<Location> model;
    // --Commented out by Inspection (11/16/16, 3:09 PM):private List<Account> modeled;
    // --Commented out by Inspection (11/16/16, 3:09 PM):private List<Report> modeling;

// --Commented out by Inspection START (11/16/16, 3:09 PM):
//    /**
//     * Allows for persistence.
//     * @param m Model that is passed in
//     */
//    public PersistenceManager(List<Location> m) {
//        model = m;
//    }
// --Commented out by Inspection STOP (11/16/16, 3:09 PM)

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
        String ct;
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
