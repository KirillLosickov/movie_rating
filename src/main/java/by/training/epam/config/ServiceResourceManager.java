package by.training.epam.config;

import java.util.ResourceBundle;

public final class ServiceResourceManager {
    private static final ServiceResourceManager instance = new ServiceResourceManager();
    private ResourceBundle bundle = ResourceBundle.getBundle("by.training.epam.service.parameters");

    private ServiceResourceManager() {

    }

    public static ServiceResourceManager getInstance() {
        return instance;
    }
    public String getValue(String key) {
        return bundle.getString(key);
    }
}
