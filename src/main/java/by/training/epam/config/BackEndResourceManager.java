package by.training.epam.config;

import java.util.ResourceBundle;

@Deprecated
public final class BackEndResourceManager {
    private ResourceBundle bundle = ResourceBundle.getBundle("by.training.epam.localization.exception");

    public BackEndResourceManager() {

    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}