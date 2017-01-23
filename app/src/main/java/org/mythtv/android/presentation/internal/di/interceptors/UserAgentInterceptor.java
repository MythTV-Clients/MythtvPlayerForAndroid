package org.mythtv.android.presentation.internal.di.interceptors;

import java.io.IOException;

import dagger.internal.Preconditions;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 *
 *
 * @author dmfrey
 *
 * Created on 5/10/16.
 */
public class UserAgentInterceptor implements Interceptor {

    private static final String USER_AGENT_HEADER_NAME = "User-Agent";
    private final String userAgentHeaderValue;

    public UserAgentInterceptor( String userAgentHeaderValue ) {

        this.userAgentHeaderValue = Preconditions.checkNotNull( userAgentHeaderValue );

    }

    @Override
    public Response intercept( Chain chain ) throws IOException {

        final Request originalRequest = chain.request();
        final Request requestWithUserAgent = originalRequest.newBuilder()
                .removeHeader( USER_AGENT_HEADER_NAME )
                .addHeader( USER_AGENT_HEADER_NAME, userAgentHeaderValue )
                .build();

        return chain.proceed(requestWithUserAgent);
    }

}
