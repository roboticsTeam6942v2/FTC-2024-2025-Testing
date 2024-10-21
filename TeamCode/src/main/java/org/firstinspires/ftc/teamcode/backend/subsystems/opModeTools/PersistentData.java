package org.firstinspires.ftc.teamcode.backend.subsystems.opModeTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class PersistentData {
    private static final String FILE = "data.dat";
    private static HashMap<String, DataObject> data;

    public PersistentData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(FILE)))) {
            data = (HashMap<String, DataObject>) ois.readObject();
        } catch (Exception e) {
            data = new HashMap<>();
        }
    }

    public static void addData(String name, DataObject dataObject) {
        data.put(name, dataObject);
    }

    public static void setData(String name, DataObject dataObject) {
        try {
            data.replace(name, dataObject);
        } catch (Exception e) {
            throw new IllegalArgumentException("Name or DataObject invalid");
        }
    }

    public static <T> T getData(String name) { // can cause errors, but if it does that's not my fault
        return (T) data.get(name);
    }

    public static boolean saveData() { // feel free to make this a shutdown hook with Runtime.getRuntime().addShutdownHook(new Thread(() -> {PersistentData.saveData();}));
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(FILE)))) {
            oos.writeObject(data);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public class DataObject<T> {
        Object data;

        public DataObject(Object data) {
            this.data = data;
        }

        T getData() {
            return (T) this.data;
        }
    }
}


