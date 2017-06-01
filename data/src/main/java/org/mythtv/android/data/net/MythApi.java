package org.mythtv.android.data.net;

import org.mythtv.android.data.entity.BackendInfoEntity;

import io.reactivex.Observable;

/**
 * Created by dmfrey on 5/30/17.
 */
public interface MythApi {

    String BACKEND_INFO_URL = "/Myth/GetBackendInfo";

    Observable<BackendInfoEntity> backendInfo();

}
