package cmpt213.assignment3.packagedeliveries.control;

import cmpt213.assignment3.packagedeliveries.gson.extras.RuntimeTypeAdapterFactory;
import cmpt213.assignment3.packagedeliveries.model.*;
import cmpt213.assignment3.packagedeliveries.model.Package;
import cmpt213.assignment3.packagedeliveries.view.TextUI;
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

    public void main(String[] args) {
        readFromJson();

        TextUI textUI = new TextUI();
        textUI.addMenu();

        boolean isChoosing = true;
        while(isChoosing) {
            textUI.displayMenu();
            System.out.println("Choose an option by entering 1-7\n");
            int input = textUI.getInput();
            switch (input) {
                case 1:
                    listAllPackages();
                    break;
                case 2:
                    addPackage();
                    break;
                case 3:
                    removePackage();
                    break;
                case 4:
                    listOverdue();
                    break;
                case 5:
                    listUpcoming();
                    break;
                case 6:
                    markAsDelivered();
                    break;
                case 7:
                    isChoosing = false;
                    saveToJson();
                    break;
            }
        }
    }

    private void saveToJson() {
        try (FileWriter writer = new FileWriter("./list.json")) {
            myGson.toJson(packageList, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void markAsDelivered() {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int markInput = -1;
        int packageListIndex = 0;
        int undeliveredIndex = 1;
        if (packageList.isEmpty()) {
            System.out.println("There is no package in the list. \n");
            return;
        } else {
            sortPackages();
            for (Package thisPackage : packageList) {
                if (thisPackage.isDelivered() == "No") {
                    System.out.println("Package number: " + undeliveredIndex);
                    map.put(undeliveredIndex, packageListIndex);
                    undeliveredIndex++;
                    System.out.println(thisPackage);
                }
                packageListIndex++;
            }
        }
        while (markInput != 0) {
            Scanner console = new Scanner (System.in);

            System.out.println("Enter the package number you would like to mark (0 to cancel): ");
            markInput = console.nextInt();
            if (markInput == 0) {
                System.out.println("Cancelled.");
                break;
            } else if (map.get(markInput) != null) {
                System.out.println(packageList.get(map.get(markInput)).getName() + " has been marked as delivered.");
                packageList.get(map.get(markInput)).setDelivered(true);
                markInput = 0;
            } else if (map.get(markInput) == null) {
                System.out.println("Input is invalid, please try again.");
                markInput = -1;
            }
        }
    }

    private void listUpcoming() {
        if (packageList.isEmpty()) {
            System.out.println("No upcoming packages. \n");
        } else {
            sortPackages();
            for (Package thisPackage : packageList) {
                if (thisPackage.isDelivered() == "No" && thisPackage.getDeliveryDate().isAfter(now)) {
                    System.out.println(thisPackage);
                }
            }
        }
    }

    private void listOverdue() {
        if (packageList.isEmpty()) {
            System.out.println("No overdue packages. \n");
        } else {
            sortPackages();
            for (Package thisPackage : packageList) {
                if (thisPackage.isDelivered() == "No" && thisPackage.getDeliveryDate().isBefore(now)) {
                    System.out.println(thisPackage);
                }
            }
        }
    }

    private void removePackage() {
        sortPackages();
        int index = 1;
        int removeId = -1;
        if (packageList.isEmpty()) {
            System.out.println("There is no packages in the list. \n");
            return;
        } else {
            for (Package thisPackage : packageList) {
                System.out.println("Package number: " + index);
                index++;
                System.out.println(thisPackage);
            }
        }
        while (removeId != 0) {
            Scanner console = new Scanner (System.in);
            System.out.println("Enter the package number you would like to remove (0 to cancel): ");
            removeId = console.nextInt();
            if (removeId == 0) {
                System.out.println("Cancelled");
                break;
            } else if (packageList.size() < (removeId)) {
                System.out.println("Package doesn't exist, please try again");
                removeId = -1;

            } else if (packageList.size() >= (removeId)){
                System.out.println(packageList.get(removeId - 1).getName() + " has been removed.\n");
                packageList.remove(removeId - 1);
                removeId = 0;
            }
        }
    }

    private void addPackage() {
        packageList.add(PackageFactory.addPackage());
    }

    private void listAllPackages() {
        int index = 1;
        if (packageList == null || packageList.isEmpty()) {
            System.out.println("No packages to show\n");
        }
        else {
            sortPackages();
            for (Package thisPackage : packageList) {
                System.out.println("Package number: " + index);
                index++;
                System.out.println(thisPackage);
            }
        }
    }

    private void sortPackages() {
        Collections.sort(packageList);
    }

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

    public ArrayList<Package> getPackageList() {
        return packageList;
    }

    public ArrayList<Package> getOverdue() {
        sortPackages();
        ArrayList<Package> overdueList = new ArrayList<>();
        if (packageList.isEmpty()) {
            return overdueList;
        }
        for (Package thisPackage : packageList) {
            if (thisPackage.isDelivered() == "No" && thisPackage.getDeliveryDate().isBefore(now)) {
                overdueList.add(thisPackage);
            }
        }
        return overdueList;
    }
}
