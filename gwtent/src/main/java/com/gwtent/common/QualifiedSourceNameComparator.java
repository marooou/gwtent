package com.gwtent.common;

import com.google.gwt.core.ext.typeinfo.JClassType;

import java.util.Comparator;

public class QualifiedSourceNameComparator implements Comparator<JClassType> {
    public int compare(JClassType o1, JClassType o2) {
        return o1.getQualifiedSourceName().compareTo(o2.getQualifiedSourceName());
    }
}
