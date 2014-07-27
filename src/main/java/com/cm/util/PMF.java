package com.cm.util;


import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
	//Factory name is defined in jdoconfig.xml
    private static final PersistenceManagerFactory PMF_INSTANCE =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");

    private PMF() {}

    public static PersistenceManagerFactory get() {
        return PMF_INSTANCE;
    }
}