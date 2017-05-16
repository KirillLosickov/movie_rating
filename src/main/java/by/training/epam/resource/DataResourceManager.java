package by.training.epam.resource;

import java.util.ResourceBundle;

public final class DataResourceManager {
    private ResourceBundle bundle = ResourceBundle.getBundle("by.training.epam.service.impl.parameter");

    public DataResourceManager() {

    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
