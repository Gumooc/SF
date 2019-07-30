package com.run.tools;

import java.util.Comparator;

public class pcmfileComparetor implements Comparator<pcmfile> {
    @Override
    public int compare(pcmfile o1, pcmfile o2) {
        return o1.getStarttime()>o2.getStarttime()?1:-1;
    }
}
