package org.mythtv.android.domain;

import java.util.List;

import lombok.Data;

/**
 * Created by dmfrey on 1/18/16.
 */
@Data
public class Encoder {

    private int id;
    private String hostname;
    private boolean local;
    private boolean connected;
    private int state;
    private int sleepStatus;
    private boolean lowOnFreeSpace;
    private List<Input> inputs;
    private Program recording;

}
