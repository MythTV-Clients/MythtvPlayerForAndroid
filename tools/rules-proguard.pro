-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

# AQuery
-dontwarn com.androidquery.auth.**
-dontwarn oauth.signpost.**

# Butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# Retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# OkHTTP3
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontnote okhttp3.**

# Okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class com.bumptech.glide.integration.okhttp3.OkHttpGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

# GSON
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Joda
-dontwarn org.joda.convert.**
-dontwarn org.joda.time.**
-keep class org.joda.time.** { *; }
-keep interface org.joda.time.** { *; }

# Picasso
-dontwarn com.squareup.okhttp.**

# Retrolambda
-dontwarn java.lang.invoke.*

# RxJava
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-dontwarn sun.misc.Unsafe
-dontwarn org.w3c.dom.bootstrap.DOMImplementationRegistry

#-dontwarn com.google.android.gms.**
##-dontwarn org.w3c.dom.**
#-dontwarn org.joda.time.**
#-dontwarn org.shaded.apache.**
#-dontwarn org.ietf.jgss.**
#-dontwarn com.firebase.**
#-dontnote com.firebase.client.core.GaePlatform

#-keepattributes Signature
#-keepattributes *Annotation*
#-keepattributes InnerClasses,EnclosingMethod

#-keep class org.mythtv.android.** { *; }

# Basic ProGuard rules for Firebase Android SDK 2.0.0+
#-keep class com.firebase.** { *; }

#-keepnames class com.fasterxml.jackson.** { *; }
#-keepnames class javax.servlet.** { *; }
#-keepnames class org.ietf.jgss.** { *; }