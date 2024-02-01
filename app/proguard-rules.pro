 #BASIC RULES FOR MY CODE
-keep class com.dherediat97.oompaloompaapp.** { *; }
-renamesourcefileattribute SourceFile
-keepattributes SourceLine,LineNumberTable


 #CUSTOM RULES FOR OKHTTP
 # With R8 full mode generic signatures are stripped for classes that are not
 # kept. Suspend functions are wrapped in continuations where the type argument
 # is used.
 -keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

 # R8 full mode strips generic signatures from return types if not kept.
 -if interface * { @retrofit2.http.* public *** *(...); }
 -keep,allowoptimization,allowshrinking,allowobfuscation class <3>

 # With R8 full mode generic signatures are stripped for classes that are not kept.
 -keep,allowobfuscation,allowshrinking class retrofit2.Response


 #CUSTOM RULES FOR COROUTINES
 # When editing this file, update the following files as well:
 # - META-INF/com.android.tools/proguard/coroutines.pro
 # - META-INF/com.android.tools/r8/coroutines.pro

 # ServiceLoader support
 -keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
 -keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

 # Most of volatile fields are updated with AFU and should not be mangled
 -keepclassmembers class kotlinx.coroutines.** {
     volatile <fields>;
 }

 # Same story for the standard library's SafeContinuation that also uses AtomicReferenceFieldUpdater
 -keepclassmembers class kotlin.coroutines.SafeContinuation {
     volatile <fields>;
 }

 # Only used in `kotlinx.coroutines.internal.ExceptionsConstructor`.
 # The case when it is not available is hidden in a `try`-`catch`, as well as a check for Android.
 -dontwarn java.lang.ClassValue