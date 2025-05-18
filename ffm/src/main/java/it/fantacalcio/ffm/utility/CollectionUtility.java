package it.fantacalcio.ffm.utility;

import java.util.Collection;
import java.util.Optional;

public class CollectionUtility {
    private CollectionUtility(){}

    public static <T> void safeForEach(Collection<T> collection, java.util.function.Consumer<? super T> action) {
        Optional.ofNullable(collection).ifPresent(col -> col.forEach(action));
    }

    public static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        return str.toLowerCase().contains(searchStr.toLowerCase());
    }
}
