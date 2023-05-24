#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_cb_videoeditorapp_lovephotoeffectvideomaker_app_Utils_EncScriptPhoto_getKey1(JNIEnv *env,
                                                                               jobject instance) {

    std::string hello = "ncryp@tent#!k@r@";
    return env->NewStringUTF(hello.c_str());
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_cb_videoeditorapp_lovephotoeffectvideomaker_app_Utils_EncScriptPhoto_getKey2(JNIEnv *env,
                                                                               jobject instance) {

    std::string hello = "ncryp@tent#!k@r@";
    return env->NewStringUTF(hello.c_str());
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_cb_videoeditorapp_lovephotoeffectvideomaker_app_Utils_DecScriptPhoto_getKey3(JNIEnv *env,
                                                                               jobject instance) {

    std::string hello = "oB@k@@oytojJeppj";
    return env->NewStringUTF(hello.c_str());
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_cb_videoeditorapp_lovephotoeffectvideomaker_app_Utils_DecScriptPhoto_getKey4(JNIEnv *env,
                                                                               jobject instance) {

    std::string hello = "oB@k@@oytojJeppj";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_cb_videoeditorapp_lovephotoeffectvideomaker_app_Utils_Constant_getMainApi(
        JNIEnv *env, jclass clazz) {

    std::string hello = "http://139.59.25.230/nv/HeartPhotoNV685/V1/";
    return env->NewStringUTF(hello.c_str());
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_cb_videoeditorapp_lovephotoeffectvideomaker_app_encrypt_DecryptEncrypt_encryptionKey(
        JNIEnv *env, jclass clazz) {

    std::string hello = "";
    return env->NewStringUTF(hello.c_str());
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_cb_videoeditorapp_lovephotoeffectvideomaker_app_encrypt_DecryptEncrypt_zipencryptionkey(
        JNIEnv *env, jclass clazz) {

    std::string hello = "";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_cb_videoeditorapp_lovephotoeffectvideomaker_app_encrypt_DecScript_getDecKey1(
        JNIEnv *env, jclass clazz) {

    std::string hello = "EDAE9C9104563064DC26639005D324880CC8EE72A457C3AAA35F79627FD13728";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_cb_videoeditorapp_lovephotoeffectvideomaker_app_encrypt_DecScript_getDecKey2(
        JNIEnv *env, jclass clazz) {

    std::string hello = "ABD54B07822F8F0C44D00A8E20F5A44A0CC8EE72A457C3AAA35F79627FD13728";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_cb_videoeditorapp_lovephotoeffectvideomaker_app_encrypt_EncScript_getEncKey1(
        JNIEnv *env, jclass clazz) {

    std::string hello = "";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_cb_videoeditorapp_lovephotoeffectvideomaker_app_encrypt_EncScript_getEncKey2(
        JNIEnv *env, jclass clazz) {

    std::string hello = "";
    return env->NewStringUTF(hello.c_str());
}