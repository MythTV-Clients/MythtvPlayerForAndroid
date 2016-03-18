package org.mythtv.android.presentation.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Created by dmfrey on 1/28/16.
 */
@Data
@RequiredArgsConstructor( suppressConstructorProperties = true )
public class TvCategoryModel {

    private final int position;
    private final String title;
    private final Integer drawable;

}
