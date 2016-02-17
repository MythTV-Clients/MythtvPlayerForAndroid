package org.mythtv.android.presentation.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by dmfrey on 8/27/15.
 */
@Data
public class CastModel implements Serializable {

    private CastMemberModel[] castMembers;

}
