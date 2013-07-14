package org.ebouquin.services.DAO;

import java.sql.Types;

/**
 * Created by IntelliJ IDEA.
 * User: catherine
 * Date: 23/04/11
 * Time: 15:09
 * To change this template use File | Settings | File Templates.
 */
public class HibernateDialectForHsql extends org.hibernate.dialect.HSQLDialect {

    public HibernateDialectForHsql() {
        super();
        registerColumnType(Types.BIT, "boolean");

        // Assert that the new type is registered correctly.
        if (!"boolean".equals(getTypeName(Types.BIT))) {
            throw new IllegalStateException("Failed to register HSQLDialect "
                    + "column type for Types.BIT to \"boolean\".");
        }
    }
}

