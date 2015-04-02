package org.mythtv.android.player.common.ui.data;

import org.mythtv.android.library.core.domain.dvr.TitleInfo;

import java.util.List;

/**
 * Created by dmfrey on 11/29/14.
 */
public interface TitleInfoDataConsumer extends ErrorHandler {

    public void setTitleInfos( List<TitleInfo> titleInfos );

}
