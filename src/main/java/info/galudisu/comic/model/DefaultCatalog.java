/*
 * This file is generated by jOOQ.
 */
package info.galudisu.comic.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Schema;
import org.jooq.impl.CatalogImpl;


@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DefaultCatalog extends CatalogImpl {

    private static final long serialVersionUID = 1549142416;

    public static final DefaultCatalog DEFAULT_CATALOG = new DefaultCatalog();

    public final ComicCloudAdmin COMIC_CLOUD_ADMIN = info.galudisu.comic.model.ComicCloudAdmin.COMIC_CLOUD_ADMIN;

    private DefaultCatalog() {
        super("");
    }

    @Override
    public final List<Schema> getSchemas() {
        List result = new ArrayList();
        result.addAll(getSchemas0());
        return result;
    }

    private final List<Schema> getSchemas0() {
        return Arrays.<Schema>asList(
            ComicCloudAdmin.COMIC_CLOUD_ADMIN);
    }
}
