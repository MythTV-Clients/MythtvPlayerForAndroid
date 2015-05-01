package org.mythtv.android.library.events.dvr;

import org.mythtv.android.library.events.ReadEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by dmfrey on 11/12/14.
 */
public class AllProgramsCountEvent extends ReadEvent {

    private final int count;

    public AllProgramsCountEvent( final int count ) {

        this.count = count;
        this.entityFound = true;

    }

    public int getCount() {
        return count;
    }

}
