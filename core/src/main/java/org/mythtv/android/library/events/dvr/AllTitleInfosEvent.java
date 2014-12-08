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
public class AllTitleInfosEvent extends ReadEvent {

    private final List<TitleInfoDetails> details;

    public AllTitleInfosEvent(final List<TitleInfoDetails> details) {

        this.details = Collections.unmodifiableList( details );
        this.entityFound = details.size() > 0;

    }

    public List<TitleInfoDetails> getDetails() {
        return details;
    }

}
