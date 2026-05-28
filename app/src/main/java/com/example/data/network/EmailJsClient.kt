package com.example.data.network

import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object EmailJsClient {
    private const val TAG = "EmailJsClient"
    private const val SERVICE_ID = "service_0h2uzan"
    private const val TEMPLATE_ID = "template_tez7w47"
    private const val PUBLIC_KEY = "Zsj4RaTdCTymUC5o0"

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    private val mediaType = "application/json; charset=utf-8".toMediaType()

    suspend fun sendOtpEmail(toName: String, toEmail: String, otpCode: String): Boolean = withContext(Dispatchers.IO) {
        val url = "https://api.emailjs.com/api/v1.0/email/send"

        try {
            val templateParams = JSONObject().apply {
                put("to_name", toName)
                put("to_email", toEmail)
                put("otp_code", otpCode)
                put("otp", otpCode)
                put("code", otpCode)
                put("user_email", toEmail)
                put("email", toEmail)
                put("message", "Your SHIVAM ECOMMERCE secure dynamic verification OTP code is: $otpCode. It is valid for 59 seconds. Please do not share this OTP with anyone.")
            }

            val requestJson = JSONObject().apply {
                put("service_id", SERVICE_ID)
                put("template_id", TEMPLATE_ID)
                put("user_id", PUBLIC_KEY)
                put("template_params", templateParams)
            }

            val requestBody = requestJson.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            client.newCall(request).execute().use { response ->
                val bodyStr = response.body?.string() ?: ""
                if (response.isSuccessful) {
                    Log.d(TAG, "EmailJS Sent Success: $bodyStr")
                    true
                } else {
                    Log.e(TAG, "EmailJS Failed: Code ${response.code} Body: $bodyStr")
                    false
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "EmailJS Network Exception:", e)
            false
        }
    }
}
