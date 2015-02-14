##Rules for android support libraries
-keep class !android.support.v7.internal.view.menu.**,android.support.v7.** {*;}

-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }

## GSON 2.2.4 specific rules ##
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature
# For using GSON @Expose annotation
-keepattributes *Annotation*
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep public class com.karthikb351.vitinfo2.api.Objects.**{*;}

## Exceptions for google play services

-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

##facebook rules
-keep class com.facebook.** { *; }
-keepattributes Signature

##for koush libraries
-keep class * extends com.koushikdutta.**{*;}

-keep class com.parse.** { *; }

-renamesourcefileattribute ProGuard
-keepattributes SourceFile,LineNumberTable

