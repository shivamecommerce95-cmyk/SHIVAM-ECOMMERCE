package com.example.data.network

import android.util.Log
import com.example.BuildConfig
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiApiClient {
    private const val TAG = "GeminiApiClient"
    private const val MODEL_NAME = "gemini-1.5-flash"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val mediaType = "application/json; charset=utf-8".toMediaType()

    suspend fun generateChatResponse(userPrompt: String): String = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            Log.w(TAG, "Gemini API Key is blank. Using intelligent local luxury shopper fallback.")
            return@withContext getLocalFallbackResponse(userPrompt)
        }

        val url = "https://generativelanguage.googleapis.com/v1beta/models/$MODEL_NAME:generateContent?key=$apiKey"

        val systemPrompt = "You are Shivam AI, the premium virtual shopper for 'Shivam Ecommerce' (a royal, high-end online marketplace selling luxurious clothing garments like Silk Kurtis, Velvet Sherwanis, and Linen Shirts, alongside ultra-rugged premium Mobile Accessories like Carbon Fiber Cases and Fast GaN Chargers). Be concise, luxurious, friendly, and provide style tips and pairing advice. Recommend our products. Keep answers under 3-4 sentences."

        try {
            // Build the standard REST JSON payload manually to guarantee 100% stability
            val requestBodyJson = JSONObject().apply {
                val contentsArray = JSONArray().apply {
                    val contentObj = JSONObject().apply {
                        val partsArray = JSONArray().apply {
                            val partObj = JSONObject().apply {
                                put("text", userPrompt)
                            }
                            put(partObj)
                        }
                        put("parts", partsArray)
                    }
                    put(contentObj)
                }
                put("contents", contentsArray)

                val systemInstructionObj = JSONObject().apply {
                    val partsArray = JSONArray().apply {
                        val partObj = JSONObject().apply {
                            put("text", systemPrompt)
                        }
                        put(partObj)
                    }
                    put("parts", partsArray)
                }
                put("systemInstruction", systemInstructionObj)

                val genConfig = JSONObject().apply {
                    put("temperature", 0.7)
                    put("maxOutputTokens", 400)
                }
                put("generationConfig", genConfig)
            }

            val requestBody = requestBodyJson.toString().toRequestBody(mediaType)
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .header("Content-Type", "application/json")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    val errorBody = response.body?.string() ?: ""
                    Log.e(TAG, "API failed: Code ${response.code}, Body: $errorBody")
                    return@withContext getLocalFallbackResponse(userPrompt)
                }

                val responseBody = response.body?.string() ?: ""
                val jsonObj = JSONObject(responseBody)
                val candidates = jsonObj.getJSONArray("candidates")
                if (candidates.length() > 0) {
                    val contentObj = candidates.getJSONObject(0).getJSONObject("content")
                    val parts = contentObj.getJSONArray("parts")
                    if (parts.length() > 0) {
                        return@withContext parts.getJSONObject(0).getString("text")
                    }
                }
                "I am elegant to match your taste; how can Shivam Ecommerce help you today?"
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error executing Gemini API request", e)
            getLocalFallbackResponse(userPrompt)
        }
    }

    private fun getLocalFallbackResponse(userPrompt: String): String {
        val lowercase = userPrompt.trim().lowercase()
        return when {
            // Hindi shirt query: "shirt accha" or "kaunsa shirt"
            lowercase.contains("shirt") && (lowercase.contains("accha") || lowercase.contains("kaun") || lowercase.contains("recommend") || lowercase.contains("best")) -> {
                "🌟 **SHIVAM ECOMMERCE VIP recommendation:**\n\n" +
                "• **Italian Premium Linen Shirt** (₹2,499) sabse best choice hai! Yeh pure organic linen se bani hai, jo garmi me behad comfortable aur standard luxury look deti hai.\n" +
                "• Sizing tips: Agar aapki height 5'8\" - 5'11\" hai, toh Size **M** perfectly fit aayega. Kya mai ise aapki Cart me add karu?"
            }
            // Shirt general
            lowercase.contains("shirt") || lowercase.contains("clothes") -> {
                "Our top shirts combine premium weaving traditions with modern style:\n" +
                "1. **Italian Premium Linen Shirt** – Airy, ultra-premium linen, perfect for standard meetings.\n" +
                "2. **Royal Zari Silk Kurti** – Authentic gold threads on mulberry silk.\n\n" +
                "Let me know if you need sizing help or pairing recommendations!"
            }
            // Match / Jeans queries: "kaunsi jeans match" or "match karegi"
            lowercase.contains("match") || lowercase.contains("jeans") || lowercase.contains("pant") || lowercase.contains("trousers") -> {
                "👖 **Perfect Fashion Combination by SHIVAM AI:**\n\n" +
                "• Humare **Italian Premium Linen Shirt** (White/Beige variant) ke sath Navy Blue Slim-fit Jeans ya Charcoal Chinos perfectly match karegi!\n" +
                "• Aur agar aap **Royal Velvet Sherwani** pehen rahe hain, toh humara Cream-colored churidar pyjama and royal leather juttis complete luxury royal look denge. ✨"
            }
            // Best mobile accessories
            lowercase.contains("accessories") || lowercase.contains("accessory") || lowercase.contains("best accessory") || lowercase.contains("best mobile") -> {
                "⚡ **SHIVAM ECOMMERCE Top Accessories:**\n\n" +
                "1. **Military-spec Aramid Fiber Case** (₹1,499) – 0.6mm ultra-thin luxury composite bulletproof phone shell.\n" +
                "2. **GaN 120W Triple Charger** (₹2,199) – Charge your laptop, iPhone & Android together at high-speeds.\n\n" +
                "These are incredibly sleek and long-lasting choice for tech enthusiasts!"
            }
            // Specific recommendations
            lowercase.contains("recommend") || lowercase.contains("suggest") || lowercase.contains("buy") -> {
                "✨ **SHIVAM ECOMMERCE recommendations:**\n" +
                "1. **Royal Silk Kurti (Traditional Wear):** Elegant Banarsi handloom legacy tailored with gold threads.\n" +
                "2. **Aramid Carbon Case (Sleek Accessories):** Highly scratch-resistant military fiber shell.\n\n" +
                "Tap under category filters or click any catalog item card to place order!"
            }
            // Brand & Price query
            lowercase.contains("price") || lowercase.contains("cost") || lowercase.contains("expensive") -> {
                "Our collections represent accessible high-end luxury. Shirts and aramid composite accessories are available from ₹1,299 to ₹2,499. Custom handloom Velvet Sherwanis start from ₹8,999. Premium shopping with no compromises."
            }
            // Phone charger / case
            lowercase.contains("phone") || lowercase.contains("charger") || lowercase.contains("case") -> {
                "Our tech gadgets feature robust carbon components. Explore the **Aramid Carbon Case** or our **GaN 120W Compact Charger** for maximum performance and a luxury tactile feel."
            }
            // Greeting / Hello
            lowercase.contains("hello") || lowercase.contains("hi") || lowercase.contains("hey") || lowercase.contains("namaste") -> {
                "Namaste! Welcome to **SHIVAM ECOMMERCE** Customer Lounge. 🌟 I'm your smart AI Assistant & Stylist.\n" +
                "Ask me fashion pairing questions (e.g. \"Kaunsa shirt accha hai?\" or \"Kaunsi jeans match karegi?\") or inquire about our custom mobile accessories!"
            }
            // Fallback response
            else -> {
                "At **SHIVAM ECOMMERCE**, we fuse the absolute finest clothing standards with high-durability aramid phone protectors. Tell me what type of item or style you're looking for, and I will recommend the perfect match immediately!"
            }
        }
    }
}
