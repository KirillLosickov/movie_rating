package by.training.epam.resource;

import java.util.ResourceBundle;

public final class BackEndResourceManager {
    private ResourceBundle bundle = ResourceBundle.getBundle("by.training.epam.localization.exception");

    public BackEndResourceManager() {

    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}