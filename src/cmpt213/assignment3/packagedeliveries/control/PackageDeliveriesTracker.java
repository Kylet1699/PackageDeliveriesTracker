package cmpt213.assignment3.packagedeliveries.control;

import cmpt213.assignment3.packagedeliveries.gson.extras.RuntimeTypeAdapterFactory;
import cmpt213.assignment3.packagedeliveries.model.Package;
import cmpt213.assignment3.packagedeliveries.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.*;

/**
 * A class that holds package information in a list of packages
 * @author Kyle Tseng
 */
public class PackageDeliveriesTracker {
    static RuntimeTypeAdapterFactory<Package> packageAdapterFactory = RuntimeTypeAdapterFactory.of(Package.class, "type", true)
            .registerSubtype(Perishable.class, Perishable.class.getName())
            .registerSubtype(Electronic.class, Electronic.class.getName())
            .registerSubtype(Book.class, Book.class.getName());

    static Gson myGson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
        @Override
        public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
            jsonWriter.value(localDateTime.toString());
        }
        @Override
        public LocalDateTime read(JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString());
        }
    }).registerTypeAdapterFactory(packageAdapterFactory).setPrettyPrinting().create();

    private static ArrayList<Package> packageList = new ArrayList<>();
    private static LocalDateTime now = LocalDateTime.now();

    public PackageDeliveriesTracker(ArrayList<Package> packageList) {
        this.packageList = packageList;
    }



    public void saveToJson() {
        try (FileWriter writer = new FileWriter("./list.json")) {
            myGson.toJson(packageList, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove a package from the package list
     * @param thisPackage A package to be removed from the list
     */
    public void removePackage(Package thisPackage) {
        packageList.remove(thisPackage);
    }

    /**
     * Update delivered state of a package
     * @param thisPackage A package to update the delivered state
     * @param isDelivered A boolean used to set the delivered state
     */
    public void updateDelivered(Package thisPackage, boolean isDelivered) {
        thisPackage.setDelivered(isDelivered);
    }

    /**
     * Add a package to the package list
     * @param name
     * @param note
     * @param price
     * @param weight
     * @param deliveryDate
     * @param fee
     * @param author
     * @param expiryDate
     * @param type
     */
    public void addPackage(String name,
                            String note,
                            Double price,
                            Double weight,
                            LocalDateTime deliveryDate,
                            Double fee,
                            String author,
                            LocalDateTime expiryDate,
                            Object type)
    {
        packageList.add(PackageFactory.createPackage(
                name,
                note,
                price,
                weight,
                deliveryDate,
                fee,
                author,
                expiryDate,
                type.toString()));
    }

    /**
     * Sort the package list
     */
    public void sortPackages() {
        Collections.sort(packageList);
    }

    /**
     * Load the JSON file to poppulate the package list
     */
    public void readFromJson() {
        File f = new File("./list.json");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        try {
            Reader reader = new FileReader("./list.json");
            Type arrayListType = new TypeToken<ArrayList<Package>>(){}.getType();
            packageList = myGson.fromJson(reader, arrayListType);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get a list of all packages
     * @return An arraylist of all packages
     */
    public ArrayList<Package> getPackageList() {
        int index = 1;
        ArrayList<Package> allList = new ArrayList<>();
        if (packageList.isEmpty()) {
            return packageList;
        }
        for (Package thisPackage : packageList) {
            thisPackage.setId(index);
            index++;
        }
        return packageList;
    }

    /**
     * Get a list of overdue packages that are not delivered
     * @return An arraylist of overdue packages
     */
    public ArrayList<Package> getOverdue() {
        sortPackages();
        int index = 1;
        ArrayList<Package> overdueList = new ArrayList<>();
        if (packageList.isEmpty()) {
            return overdueList;
        }
        for (Package thisPackage : packageList) {
            if (thisPackage.isDelivered() == "No" && thisPackage.getDeliveryDate().isBefore(now)) {
                thisPackage.setId(index);
                overdueList.add(thisPackage);
                index++;
            }
        }
        return overdueList;
    }

    /**
     * Get a list of upcoming packages that are not delivered
     * @return An arraylist of upcoming packages
     */
    public ArrayList<Package> getUpcoming() {
        sortPackages();
        int index = 1;
        ArrayList<Package> upcomingList = new ArrayList<>();
        if (packageList.isEmpty()) {
            return upcomingList;
        }
        for (Package thisPackage : packageList) {
            if (thisPackage.isDelivered() == "No" && thisPackage.getDeliveryDate().isAfter(now)) {
                thisPackage.setId(index);
                upcomingList.add(thisPackage);
                index++;
            }
        }
        return upcomingList;
    }
}
