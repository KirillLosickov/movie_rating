package by.training.epam.service.util;

import by.training.epam.service.util.exception.UtilException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Util {

    public final static void isNull(Object... objects) throws UtilException {
        for (Object ob : objects) {
            if (ob == null) {
                throw new UtilException("ошибка ввода данных (null строка)");
            }
        }
    }

    public final static void isEmptyString(String... strings) throws UtilException {
        for (String s : strings) {
            if (s.isEmpty()) {
                throw new UtilException("ошибка ввода данных (пустая строка)");
            }
        }
    }

    public final static void isEmptyMassif(String[]... masStrings) throws UtilException {
        for (String[] mas : masStrings) {
            for (String s : mas) {
                if (s.isEmpty()) {
                    throw new UtilException("ошибка ввода данных (пустая строка)");
                }
            }
        }
    }

    public final static void matchDate(String... strings) throws UtilException {
        Pattern pattern = Pattern.compile("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12]\\d|3[01])$");
        try {
            for (String s : strings) {
                Matcher matcher = pattern.matcher(s);
                if (!matcher.matches()) {
                    throw new UtilException("ошибка ввода даты");
                }
            }
        } catch (NullPointerException e) {
            throw new UtilException(e);
        }
    }

    public final static void matchCorrectString(String... strings) throws UtilException {
        Pattern pattern = Pattern.compile("[A-Za-zА-Яа-яёЁ]+");
        for (String s : strings) {
            Matcher matcher = pattern.matcher(s);
            if (!matcher.matches()) {
                throw new UtilException("данные не соответствуют корректному типу");
            }
        }
    }

    public final static void matchEmail(String... strings) throws UtilException {
        Pattern pattern = Pattern.compile("\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6}");
        for (String s : strings) {
            Matcher matcher = pattern.matcher(s);
            if (!matcher.matches()) {
                throw new UtilException("не коррекный email");
            }
        }
    }
}
