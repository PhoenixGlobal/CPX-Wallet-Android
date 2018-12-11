# sdk
# -keep class com.chinapex.android.datacollect.**{*;}

# okhttp
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn okhttp3.internal.platform.ConscryptPlatform

# sqlcipher
-keep  class net.sqlcipher.** {*;}
-keep  class net.sqlcipher.database.** {*;}

# jni
-keep  class ethmobile.** {*;}
-keep  class go.** {*;}
-keep  class neomobile.** {*;}
-keepclasseswithmembernames class * {
    native <methods>;
}

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# bean
-keep class chinapex.com.wallet.bean.** {*;}