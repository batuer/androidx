#include <jni.h>
#include <string>
#include<android/log.h>

#define TAG "Gusi" // 这个是自定义的LOG的标识
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__) // 定义LOGD类型

extern "C" JNIEXPORT jstring JNICALL
Java_com_gusi_androidx_module_jni_JniActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    int i = 0;
    LOGD("########## i = %d", i);
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_gusi_androidx_module_jni_JniActivity_testFromJNI(JNIEnv *env, jobject thiz) {
const char *hello = "Hello from C++";
size_t i = strlen(hello);
LOGD("------========----- i = %d", i);
std::string hello1 = nullptr;
}