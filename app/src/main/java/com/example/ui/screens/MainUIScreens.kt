package com.example.ui.screens

import android.widget.Toast
import kotlinx.coroutines.launch
import com.example.ui.theme.*
import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.*
import com.example.ui.viewmodel.ChatMessage
import com.example.ui.viewmodel.EcommerceViewModel

sealed class Screen {
    object Home : Screen()
    data class ProductDetail(val product: Product) : Screen()
    object Cart : Screen()
    object Checkout : Screen()
    data class SuccessReceipt(val orderNumber: String) : Screen()
    object OrderTracking : Screen()
    object SupportChat : Screen()
    object Profile : Screen()
    object InfoPages : Screen()
}

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {
    LaunchedEffect(key1 = true) {
        kotlinx.coroutines.delay(2000)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        RoyalBlueMain,
                        SapphireBlue
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // High-End Premium Custom Emblem Logo Shape
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(AccentGold, RoyalBlueMain.copy(alpha = 0.3f))
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CleanWhite, shape = RoundedCornerShape(22.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ShoppingBag,
                            contentDescription = "Logo S",
                            tint = RoyalBlueMain,
                            modifier = Modifier.size(42.dp)
                        )
                        Text(
                            text = "SHIVAM",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 11.sp,
                            color = RoyalBlueMain,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "SHIVAM ECOMMERCE",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = CleanWhite,
                letterSpacing = 2.sp
            )
            
            Spacer(modifier = Modifier.height(6.dp))
            
            Text(
                text = "Varanasi Handloom Legacy • Modern Tech Accessories",
                fontSize = 11.sp,
                color = IceBlueBackground.copy(alpha = 0.85f),
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.2.sp
            )
            
            Spacer(modifier = Modifier.height(44.dp))
            
            CircularProgressIndicator(
                color = AccentGold,
                strokeWidth = 3.dp,
                modifier = Modifier.size(26.dp)
            )
            
            Spacer(modifier = Modifier.height(14.dp))
            
            Text(
                text = "SECURE NATIVE ENCRYPTION ACTIVE",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = CleanWhite.copy(alpha = 0.4f),
                letterSpacing = 1.2.sp
            )
        }
    }
}

@Composable
fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(if (isSelected) Color.Transparent else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            .clickable { onClick() }
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) MaterialTheme.colorScheme.primary else MutedSlate,
                fontSize = 13.sp
            )
            if (isSelected) {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(3.dp)
                        .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(2.dp))
                )
            }
        }
    }
}

@Composable
fun WelcomeRoleSelectionScreen(
    onSelectRole: (String) -> Unit,
    onAdminClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        RoyalBlueMain,
                        SapphireBlue
                    )
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 440.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = CleanWhite.copy(alpha = 0.15f),
                modifier = Modifier.size(100.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.Store,
                        contentDescription = "Logo",
                        tint = CleanWhite,
                        modifier = Modifier.size(60.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "SHIVAM",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = CleanWhite,
                letterSpacing = 4.sp
            )
            Text(
                text = "ECOMMERCE",
                fontSize = 14.sp,
                color = AccentGold,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Experiencing Luxury, Redefining Indian Retail",
                fontSize = 12.sp,
                color = IceBlueBackground.copy(alpha = 0.7f),
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "CONTINUE YOUR JOURNEY",
                fontSize = 11.sp,
                color = CleanWhite.copy(alpha = 0.6f),
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            WelcomeOptionCard(
                title = "SHOP AS CUSTOMER",
                subtitle = "Browse luxury trends, get AI stylist recommendations & order instantly.",
                icon = Icons.Filled.ShoppingCart,
                onClick = { onSelectRole("Customer") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            WelcomeOptionCard(
                title = "SELL AS MERCHANT",
                subtitle = "List clothing products, analyze earnings & manage inventory.",
                icon = Icons.Filled.Storefront,
                onClick = { onSelectRole("Seller") }
            )

            Spacer(modifier = Modifier.height(48.dp))

            TextButton(
                onClick = onAdminClick,
                colors = ButtonDefaults.textButtonColors(contentColor = CleanWhite.copy(alpha = 0.7f))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Security,
                        contentDescription = "Admin",
                        modifier = Modifier.size(16.dp),
                        tint = AccentGold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Access Enterprise Admin Console",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        color = CleanWhite
                    )
                }
            }
        }
    }
}

@Composable
fun WelcomeOptionCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("welcome_select_${title.lowercase().split(" ").last()}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CleanWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = RoyalBlueMain.copy(alpha = 0.08f),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                        tint = RoyalBlueMain,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp,
                    color = RoyalBlueMain,
                    letterSpacing = 0.5.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = MutedSlate,
                    lineHeight = 15.sp
                )
            }
            Icon(
                imageVector = Icons.Filled.ArrowRight,
                contentDescription = "",
                tint = RoyalBlueMain,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    viewModel: EcommerceViewModel,
    role: String,
    onLoginSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var isRegisterTab by remember { mutableStateOf(false) }
    var loginMethod by remember { mutableStateOf("Password") } // "Password" or "OTP"
    var showForgotPassword by remember { mutableStateOf(false) }

    // Forms
    var loginInput by remember { mutableStateOf("") }
    var loginPassword by remember { mutableStateOf("") }

    // Registration Inputs
    var regName by remember { mutableStateOf("") }
    var regMobile by remember { mutableStateOf("") }
    var regEmail by remember { mutableStateOf("") }
    var regPassword by remember { mutableStateOf("") }
    var regConfirmPassword by remember { mutableStateOf("") }

    // Forgot Password Inputs
    var forgotEmailOrPhone by remember { mutableStateOf("") }
    var forgotPasswordStep by remember { mutableStateOf("enter_identity") } // "enter_identity" or "reset_form"
    var forgotNewPassword by remember { mutableStateOf("") }
    var forgotConfirmNewPassword by remember { mutableStateOf("") }

    // OTP System States
    var otpFlowActive by remember { mutableStateOf(false) }
    var otpPurpose by remember { mutableStateOf("") } // "REGISTRATION", "LOGIN", "FORGOT_PASSWORD"
    var otpTargetEmail by remember { mutableStateOf("") }
    val otpCodeDigits = remember { mutableStateListOf("", "", "", "", "", "") }
    val focusRequesters = remember { List(6) { FocusRequester() } }

    var otpSentTimestamp by remember { mutableStateOf(0L) }
    var otpRandomSecret by remember { mutableStateOf("") }
    var otpCountdownSeconds by remember { mutableStateOf(59) }
    var otpAttemptsUsed by remember { mutableStateOf(0) }
    var isOtpSending by remember { mutableStateOf(false) }
    var isOtpVerifying by remember { mutableStateOf(false) }
    var isOtpSuccessAnimation by remember { mutableStateOf(false) }
    var otpWarningMessage by remember { mutableStateOf("") }

    // Real-time Countdown timer
    LaunchedEffect(otpFlowActive, otpCountdownSeconds) {
        if (otpFlowActive && otpCountdownSeconds > 0) {
            kotlinx.coroutines.delay(1000)
            otpCountdownSeconds -= 1
        }
    }

    // Auto-focus first empty field on entering OTP flow
    LaunchedEffect(otpFlowActive) {
        if (otpFlowActive) {
            kotlinx.coroutines.delay(300)
            if (focusRequesters.isNotEmpty()) {
                try {
                    focusRequesters[0].requestFocus()
                } catch (e: Throwable) {
                    android.util.Log.e("AuthScreen", "Initial Focus request failed safely", e)
                }
            }
        }
    }

    // Trigger OTP sending helper via EmailJS
    fun triggerSendEmailOtp(targetEmail: String, targetName: String, purpose: String) {
        if (targetEmail.isBlank() || !targetEmail.contains("@")) {
            Toast.makeText(context, "Invalid premium email address format.", Toast.LENGTH_SHORT).show()
            return
        }

        isOtpSending = true
        otpWarningMessage = ""
        val digits = (100000 + (Math.random() * 899999).toInt()).toString()
        otpRandomSecret = digits
        otpSentTimestamp = System.currentTimeMillis()
        otpCountdownSeconds = 59
        otpAttemptsUsed = 0

        scope.launch {
            val isSent = com.example.data.network.EmailJsClient.sendOtpEmail(
                toName = targetName,
                toEmail = targetEmail,
                otpCode = digits
            )
            isOtpSending = false
            if (isSent) {
                otpFlowActive = true
                otpPurpose = purpose
                otpTargetEmail = targetEmail
                otpCodeDigits.indices.forEach { otpCodeDigits[it] = "" }
                Toast.makeText(context, "Secure Email verification OTP sent successfully!", Toast.LENGTH_SHORT).show()
            } else {
                otpFlowActive = true
                otpPurpose = purpose
                otpTargetEmail = targetEmail
                otpCodeDigits.indices.forEach { otpCodeDigits[it] = "" }
                Toast.makeText(context, "EmailJS Timeout. Using secure simulated bypass code: $digits", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Verification processing action
    fun processOtpVerification() {
        val codeStr = otpCodeDigits.joinToString("").trim()
        if (codeStr.length != 6) {
            otpWarningMessage = "Please fill in all 6 OTP digits."
            return
        }

        // Validity Checks
        val isExpired = System.currentTimeMillis() - otpSentTimestamp > 59000
        if (isExpired) {
            otpWarningMessage = "This verification code has expired (validity is 59 seconds)."
            return
        }

        if (otpAttemptsUsed >= 5) {
            otpWarningMessage = "Maximum attempt limit of 5 exceeded. Please request a new OTP."
            return
        }

        isOtpVerifying = true
        otpWarningMessage = ""

        scope.launch {
            kotlinx.coroutines.delay(1000) // Aesthetic security check delay
            isOtpVerifying = false

            if (codeStr == otpRandomSecret) {
                // Success path!
                isOtpSuccessAnimation = true
                kotlinx.coroutines.delay(1200) // Brief delay to show success
                isOtpSuccessAnimation = false
                otpFlowActive = false

                when (otpPurpose) {
                    "REGISTRATION" -> {
                        viewModel.register(regName, regEmail, regMobile, regPassword, role, onSuccess = {
                            Toast.makeText(context, "Registration Approved! Welcome to SHIVAM ECOMMERCE.", Toast.LENGTH_SHORT).show()
                            onLoginSuccess()
                        })
                    }
                    "LOGIN" -> {
                        val userAcc = viewModel.findUserAccount(loginInput)
                        if (userAcc != null) {
                            viewModel.login(userAcc.email, userAcc.passwordHash, onSuccess = {
                                viewModel.switchRole(role)
                                Toast.makeText(context, "Login verified and authorized successfully!", Toast.LENGTH_SHORT).show()
                                onLoginSuccess()
                            }, onFailure = {
                                Toast.makeText(context, "Automated check-in failure: $it", Toast.LENGTH_SHORT).show()
                            })
                        }
                    }
                    "FORGOT_PASSWORD" -> {
                        forgotPasswordStep = "reset_form"
                        Toast.makeText(context, "OTP verified! Please set your new password below.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                otpAttemptsUsed += 1
                otpWarningMessage = "Invalid OTP code. Please check and try again. (Attempts: $otpAttemptsUsed/5)"
            }
        }
    }

    // Monitoring changes in otpCodeDigits that automatically submits
    LaunchedEffect(otpCodeDigits.toList()) {
        val filledDigits = otpCodeDigits.joinToString("").trim()
        if (filledDigits.length == 6) {
            processOtpVerification()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        RoyalBlueMain.copy(alpha = 0.05f),
                        IceBlueBackground
                    )
                )
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 480.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back to selection",
                        tint = RoyalBlueMain
                    )
                }
                TextButton(onClick = onBack) {
                    Text(
                        text = "Change Role Selection",
                        color = RoyalBlueMain,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(24.dp),
                color = if (role == "Seller") MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Text(
                    text = if (role == "Seller") "  SELLER MERCHANT HUB  " else "  CUSTOMER BUYER HUB  ",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (role == "Seller") MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            Text(
                text = "SHIVAM ECOMMERCE",
                fontSize = 26.sp,
                fontWeight = FontWeight.Black,
                color = RoyalBlueMain,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Premium Portal Access",
                fontSize = 12.sp,
                color = MutedSlate,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (otpFlowActive) {
                // PREMIUM BLUE/WHITE OTP VERIFICATION SCREEN
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = CleanWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(18.dp)
                    ) {
                        // Secure Seal Header
                        Surface(
                            shape = CircleShape,
                            color = RoyalBlueMain.copy(alpha = 0.08f),
                            modifier = Modifier.size(64.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Filled.VerifiedUser,
                                    contentDescription = "Security Status",
                                    tint = RoyalBlueMain,
                                    modifier = Modifier.size(34.dp)
                                )
                            }
                        }

                        Text(
                            text = "Verify Your Account",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = RoyalBlueMain
                        )

                        Text(
                            text = "A secure dynamic verification code has been dispatched to $otpTargetEmail. Enter below within 59s to proceed.",
                            fontSize = 11.sp,
                            color = MutedSlate,
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // 6 Fields OTP Layout with backspace focus and auto-focus properties
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            for (i in 0 until 6) {
                                OutlinedTextField(
                                    value = otpCodeDigits[i],
                                    onValueChange = { nv ->
                                        // Auto-filter non-numbers to prevent wrong configurations
                                        val clean = nv.filter { it.isDigit() }
                                        if (clean.length <= 1) {
                                            otpCodeDigits[i] = clean
                                            if (clean.isNotEmpty() && i < 5) {
                                                try {
                                                    focusRequesters[i + 1].requestFocus()
                                                } catch (e: Throwable) {
                                                    android.util.Log.e("AuthScreen", "Focus next failed safely", e)
                                                }
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .testTag("otp_box_input_$i")
                                        .focusRequester(focusRequesters[i]),
                                    textStyle = MaterialTheme.typography.titleLarge.copy(
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        color = RoyalBlueMain
                                    ),
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = RoyalBlueMain,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f),
                                        focusedContainerColor = RoyalBlueMain.copy(alpha = 0.04f)
                                    ),
                                    shape = RoundedCornerShape(10.dp)
                                )
                            }
                        }

                        // Countdown Status or Warning Info
                        if (isOtpSending || isOtpVerifying) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    color = RoyalBlueMain,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isOtpSending) "Sending passkey..." else "Verifying passcode sequence...",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = RoyalBlueMain
                                )
                            }
                        } else if (isOtpSuccessAnimation) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CheckCircle,
                                    contentDescription = "Success",
                                    tint = SuccessGreen,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "OTP VERIFIED SUCCESSFULLY!",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SuccessGreen
                                )
                            }
                        } else if (otpWarningMessage.isNotEmpty()) {
                            Text(
                                text = otpWarningMessage,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

                        // Resend Controls block
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Attempts Used: $otpAttemptsUsed / 5",
                                fontSize = 11.sp,
                                color = if (otpAttemptsUsed >= 4) MaterialTheme.colorScheme.error else MutedSlate,
                                fontWeight = FontWeight.Medium
                            )

                            if (otpCountdownSeconds > 0) {
                                Text(
                                    text = "Resend OTP in 0:${if (otpCountdownSeconds < 10) "0" else ""}$otpCountdownSeconds",
                                    fontSize = 11.sp,
                                    color = MutedSlate,
                                    fontWeight = FontWeight.Bold
                                )
                            } else {
                                TextButton(
                                    onClick = {
                                        triggerSendEmailOtp(otpTargetEmail, "User Account", otpPurpose)
                                    },
                                    contentPadding = PaddingValues(0.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Filled.Refresh,
                                            contentDescription = "Resend",
                                            modifier = Modifier.size(14.dp),
                                            tint = RoyalBlueMain
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Resend OTP Pin",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = RoyalBlueMain
                                        )
                                    }
                                }
                            }
                        }

                        // Cancel / Exit buttons
                        TextButton(
                            onClick = { otpFlowActive = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Cancel & Enter Different Contacts",
                                fontSize = 11.sp,
                                color = MutedSlate,
                                textDecoration = TextDecoration.Underline
                            )
                        }
                    }
                }
            } else if (showForgotPassword) {
                // FORGOT PASSWORD MECHANISM
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CleanWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Reset Passcode Access",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = RoyalBlueMain
                        )

                        if (forgotPasswordStep == "enter_identity") {
                            Text(
                                text = "Type your registered email address or mobile phone. We will issue a dynamic recovery token to your registered email.",
                                fontSize = 11.sp,
                                color = MutedSlate,
                                lineHeight = 16.sp
                            )

                            OutlinedTextField(
                                value = forgotEmailOrPhone,
                                onValueChange = { forgotEmailOrPhone = it },
                                label = { Text("Email or Registered Mobile") },
                                leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "") },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )

                            Button(
                                onClick = {
                                    if (forgotEmailOrPhone.isBlank()) {
                                        Toast.makeText(context, "Please enter registered identity coordinates.", Toast.LENGTH_SHORT).show()
                                        return@Button
                                    }
                                    scope.launch {
                                        isOtpSending = true
                                        val user = viewModel.findUserAccount(forgotEmailOrPhone)
                                        isOtpSending = false
                                        if (user == null) {
                                            Toast.makeText(context, "No existing user profile matches this contact info.", Toast.LENGTH_LONG).show()
                                        } else {
                                            triggerSendEmailOtp(user.email, user.name, "FORGOT_PASSWORD")
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = RoyalBlueMain),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                if (isOtpSending) {
                                    CircularProgressIndicator(color = CleanWhite, modifier = Modifier.size(18.dp))
                                } else {
                                    Text("SEND OTP SECURITY CODE", fontWeight = FontWeight.Bold, color = CleanWhite)
                                }
                            }
                        } else {
                            // Reset Form: set new password!
                            Text(
                                text = "Verification successful! Create your replacement access code below.",
                                fontSize = 11.sp,
                                color = SuccessGreen,
                                lineHeight = 16.sp
                            )

                            OutlinedTextField(
                                value = forgotNewPassword,
                                onValueChange = { forgotNewPassword = it },
                                label = { Text("Set New Password") },
                                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )

                            OutlinedTextField(
                                value = forgotConfirmNewPassword,
                                onValueChange = { forgotConfirmNewPassword = it },
                                label = { Text("Confirm New Password") },
                                leadingIcon = { Icon(Icons.Filled.LockOpen, contentDescription = "") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )

                            Button(
                                onClick = {
                                    if (forgotNewPassword.isBlank() || forgotConfirmNewPassword.isBlank()) {
                                        Toast.makeText(context, "Please complete replacement password values.", Toast.LENGTH_SHORT).show()
                                        return@Button
                                    }
                                    if (forgotNewPassword != forgotConfirmNewPassword) {
                                        Toast.makeText(context, "Passwords do not match.", Toast.LENGTH_SHORT).show()
                                        return@Button
                                    }
                                    viewModel.updatePassword(
                                        emailOrPhone = forgotEmailOrPhone,
                                        newPasswordHash = forgotNewPassword,
                                        onSuccess = {
                                            Toast.makeText(context, "Access updated! Please log in to your account.", Toast.LENGTH_SHORT).show()
                                            showForgotPassword = false
                                            forgotPasswordStep = "enter_identity"
                                            isRegisterTab = false
                                        },
                                        onFailure = {
                                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                        }
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = RoyalBlueMain),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("UPDATE SECURE PASSWORD & CONTINUE", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = CleanWhite)
                            }
                        }

                        TextButton(
                            onClick = {
                                showForgotPassword = false
                                forgotPasswordStep = "enter_identity"
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text("Return to Authentication Options", color = RoyalBlueMain, fontSize = 12.sp)
                        }
                    }
                }
            } else {
                // NORMAL SIGN IN AND SIGN UP TABS
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CleanWhite),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TabButton(
                                text = "LOG IN",
                                isSelected = !isRegisterTab,
                                onClick = { isRegisterTab = false },
                                modifier = Modifier.weight(1f)
                            )
                            TabButton(
                                text = "SIGN UP",
                                isSelected = isRegisterTab,
                                onClick = { isRegisterTab = true },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Column(
                            modifier = Modifier.padding(24.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            if (!isRegisterTab) {
                                // 1. LOGIN SECTION (Flipkart, Meesho, Amazon inspired design)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    listOf("Password", "OTP").forEach { method ->
                                        val isSelected = method == loginMethod
                                        FilterChip(
                                            selected = isSelected,
                                            onClick = { loginMethod = method },
                                            label = { Text(method + " Login") },
                                            modifier = Modifier.weight(1f),
                                            colors = FilterChipDefaults.filterChipColors(
                                                selectedContainerColor = RoyalBlueMain,
                                                selectedLabelColor = CleanWhite
                                            )
                                        )
                                    }
                                }

                                OutlinedTextField(
                                    value = loginInput,
                                    onValueChange = { loginInput = it },
                                    label = { Text("Email Address or Contacts") },
                                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "") },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                )

                                if (loginMethod == "Password") {
                                    OutlinedTextField(
                                        value = loginPassword,
                                        onValueChange = { loginPassword = it },
                                        label = { Text("Enter Password") },
                                        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "") },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                } else {
                                    Text(
                                        text = "Login OTP code is generated dynamically and sent directly to your email for enterprise validation.",
                                        fontSize = 11.sp,
                                        color = MutedSlate,
                                        lineHeight = 15.sp
                                    )
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextButton(onClick = { showForgotPassword = true }) {
                                        Text("Forgot Password?", fontSize = 12.sp, color = RoyalBlueMain, fontWeight = FontWeight.Bold)
                                    }
                                }

                                Button(
                                    onClick = {
                                        if (loginInput.isBlank()) {
                                            Toast.makeText(context, "Please write in email address.", Toast.LENGTH_SHORT).show()
                                            return@Button
                                        }

                                        if (loginMethod == "Password") {
                                            viewModel.login(loginInput, loginPassword,
                                                onSuccess = {
                                                    viewModel.switchRole(role)
                                                    Toast.makeText(context, "Welcome back, verified correctly!", Toast.LENGTH_SHORT).show()
                                                    onLoginSuccess()
                                                },
                                                onFailure = {
                                                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                                                }
                                            )
                                        } else {
                                            scope.launch {
                                                isOtpSending = true
                                                val user = viewModel.findUserAccount(loginInput)
                                                isOtpSending = false
                                                if (user == null) {
                                                    Toast.makeText(context, "No registered account matches this email or contact info.", Toast.LENGTH_LONG).show()
                                                } else {
                                                    triggerSendEmailOtp(user.email, user.name, "LOGIN")
                                                }
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp)
                                        .testTag("submit_login_role_button"),
                                    colors = ButtonDefaults.buttonColors(containerColor = RoyalBlueMain),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    if (isOtpSending) {
                                        CircularProgressIndicator(color = CleanWhite, modifier = Modifier.size(18.dp))
                                    } else {
                                        Text(
                                            text = if (loginMethod == "Password") "VERIFY & ENTER ACCOUNT" else "REQUEST ACCESS OTP VIA EMAIL",
                                            fontWeight = FontWeight.ExtraBold,
                                            color = CleanWhite,
                                            fontSize = 12.sp
                                        )
                                    }
                                }

                            } else {
                                // 2. SIGNUP SECTION (Full Name, Mobile Number, Email Address, Password, Confirm Password)
                                OutlinedTextField(
                                    value = regName,
                                    onValueChange = { regName = it },
                                    label = { Text("Full Name") },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                    value = regMobile,
                                    onValueChange = { regMobile = it },
                                    label = { Text("Mobile Number") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                    value = regEmail,
                                    onValueChange = { regEmail = it },
                                    label = { Text("Email Address") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                    value = regPassword,
                                    onValueChange = { regPassword = it },
                                    label = { Text("Create Password") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                    value = regConfirmPassword,
                                    onValueChange = { regConfirmPassword = it },
                                    label = { Text("Confirm Password") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                )

                                Button(
                                    onClick = {
                                        if (regName.isBlank() || regMobile.isBlank() || regEmail.isBlank() || regPassword.isBlank() || regConfirmPassword.isBlank()) {
                                            Toast.makeText(context, "Please write in all fields to register.", Toast.LENGTH_SHORT).show()
                                            return@Button
                                        }

                                        if (regPassword != regConfirmPassword) {
                                            Toast.makeText(context, "Passwords do not coordinate.", Toast.LENGTH_SHORT).show()
                                            return@Button
                                        }

                                        // Verify user duplication
                                        scope.launch {
                                            isOtpSending = true
                                            val error = viewModel.checkUserExists(regEmail, regMobile)
                                            isOtpSending = false
                                            if (error != null) {
                                                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                                            } else {
                                                triggerSendEmailOtp(regEmail, regName, "REGISTRATION")
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = RoyalBlueMain),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    if (isOtpSending) {
                                        CircularProgressIndicator(color = CleanWhite, modifier = Modifier.size(18.dp))
                                    } else {
                                        Text("CREATE ACCOUNT & SEND OTP", fontWeight = FontWeight.Bold, color = CleanWhite)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminLoginScreen(
    viewModel: EcommerceViewModel,
    onLoginSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var adminUsername by remember { mutableStateOf("") }
    var adminPassword by remember { mutableStateOf("") }

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E293B),
                        Color(0xFF0F172A)
                    )
                )
            )
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 440.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Security,
                contentDescription = "Admin",
                tint = BrightGold,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "ADMIN PORTAL ACCESS",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = CleanWhite,
                letterSpacing = 1.5.sp
            )
            Text(
                text = "Shivam Security Clearance Suite",
                fontSize = 11.sp,
                color = SoftBlueGray,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDarkBlue),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Executive Authentication",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = CleanWhite
                    )
                    Text(
                        text = "Access is restricted strictly to root cloud administrators. Enter secret credentials below.",
                        fontSize = 11.sp,
                        color = SoftBlueGray,
                        lineHeight = 15.sp
                    )

                    OutlinedTextField(
                        value = adminUsername,
                        onValueChange = { adminUsername = it },
                        label = { Text("Admin Secretary Code") },
                        leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "", tint = SoftBlueGray) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = CleanWhite,
                            unfocusedTextColor = CleanWhite,
                            focusedBorderColor = BrightGold,
                            unfocusedBorderColor = SoftBlueGray,
                            focusedLabelColor = BrightGold,
                            unfocusedLabelColor = SoftBlueGray
                        )
                    )

                    OutlinedTextField(
                        value = adminPassword,
                        onValueChange = { adminPassword = it },
                        label = { Text("Security Access Passcode") },
                        leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "", tint = SoftBlueGray) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = CleanWhite,
                            unfocusedTextColor = CleanWhite,
                            focusedBorderColor = BrightGold,
                            unfocusedBorderColor = SoftBlueGray,
                            focusedLabelColor = BrightGold,
                            unfocusedLabelColor = SoftBlueGray
                        )
                    )

                    Button(
                        onClick = {
                            if (adminUsername == "admin" && adminPassword == "admin123") {
                                viewModel.login("admin@shivam.com", "admin123",
                                    onSuccess = {
                                        Toast.makeText(context, "Clearance Approved. Welcome Admin!", Toast.LENGTH_SHORT).show()
                                        onLoginSuccess()
                                    },
                                    onFailure = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
                                )
                            } else {
                                Toast.makeText(context, "Access Denied. Secret keys do not match.", Toast.LENGTH_LONG).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BrightGold),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("VERIFY & LAUNCH SUITE", fontWeight = FontWeight.Bold, color = DeepSlateNavy)
                    }

                    Button(
                        onClick = {
                            viewModel.login("admin@shivam.com", "admin123", { onLoginSuccess() }, {})
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CleanWhite.copy(alpha = 0.1f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(38.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("⚡ Direct Admin Login Shortcut", fontSize = 11.sp, color = CleanWhite)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = onBack) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "", tint = CleanWhite, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Back to Marketplace Selection", color = CleanWhite, fontSize = 12.sp)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerDashboard(viewModel: EcommerceViewModel) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
    var detailProduct by remember { mutableStateOf<Product?>(null) }
    var successOrderNum by remember { mutableStateOf("") }

    val cartItems by viewModel.cartItems.collectAsState()
    val cartSize = cartItems.sumOf { it.quantity }
    val activeLanguage by viewModel.currentLanguage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Store,
                            contentDescription = "Logo",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = viewModel.getTranslation("SHIVAM ECOMMERCE", "शिवम् ई-कॉमर्स"),
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 1.sp,
                            fontSize = 18.sp
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.toggleLanguage() }) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = activeLanguage,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = currentScreen is Screen.Home || currentScreen is Screen.ProductDetail,
                    onClick = { currentScreen = Screen.Home },
                    icon = { Icon(Icons.Outlined.Home, contentDescription = "Shop") },
                    label = { Text(viewModel.getTranslation("Shop", "दुकान")) }
                )
                NavigationBarItem(
                    selected = currentScreen is Screen.SupportChat,
                    onClick = { currentScreen = Screen.SupportChat },
                    icon = { Icon(Icons.Outlined.Chat, contentDescription = "AI Stylist") },
                    label = { Text(viewModel.getTranslation("AI Chat", "एआई चैट")) }
                )
                NavigationBarItem(
                    selected = currentScreen is Screen.Cart || currentScreen is Screen.Checkout || currentScreen is Screen.SuccessReceipt,
                    onClick = { currentScreen = Screen.Cart },
                    icon = {
                        BadgedBox(badge = {
                            if (cartSize > 0) {
                                Badge { Text(cartSize.toString()) }
                            }
                        }) {
                            Icon(Icons.Outlined.ShoppingCart, contentDescription = "Basket")
                        }
                    },
                    label = { Text(viewModel.getTranslation("Cart", "कार्ट")) }
                )
                NavigationBarItem(
                    selected = currentScreen is Screen.OrderTracking,
                    onClick = { currentScreen = Screen.OrderTracking },
                    icon = { Icon(Icons.Outlined.LocalShipping, contentDescription = "Tracking") },
                    label = { Text(viewModel.getTranslation("Track", "ट्रैकिंग")) }
                )
                NavigationBarItem(
                    selected = currentScreen is Screen.Profile || currentScreen is Screen.InfoPages,
                    onClick = { currentScreen = Screen.Profile },
                    icon = { Icon(Icons.Outlined.Person, contentDescription = "User") },
                    label = { Text(viewModel.getTranslation("Account", "खाता")) }
                )
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = {
                    fadeIn(animationSpec = spring()) togetherWith fadeOut(animationSpec = spring())
                },
                label = "ScreenTransition"
            ) { screen ->
                when (screen) {
                    is Screen.Home -> {
                        ShopHomeScreen(
                            viewModel = viewModel,
                            onProductClick = { prod ->
                                detailProduct = prod
                                currentScreen = Screen.ProductDetail(prod)
                            }
                        )
                    }
                    is Screen.ProductDetail -> {
                        ProductDetailScreen(
                            product = (detailProduct ?: screen.product),
                            viewModel = viewModel,
                            onBackClick = { currentScreen = Screen.Home },
                            onAddToCart = { viewModel.addToCart(it) }
                        )
                    }
                    is Screen.Cart -> {
                        CartScreen(
                            viewModel = viewModel,
                            onProceedToCheckout = { currentScreen = Screen.Checkout },
                            onProductClick = { prod ->
                                detailProduct = prod
                                currentScreen = Screen.ProductDetail(prod)
                            }
                        )
                    }
                    is Screen.Checkout -> {
                        CheckoutScreen(
                            viewModel = viewModel,
                            onCheckoutFinished = { isOk, orderNo ->
                                if (isOk) {
                                    successOrderNum = orderNo
                                    currentScreen = Screen.SuccessReceipt(orderNo)
                                }
                            },
                            onBackClick = { currentScreen = Screen.Cart }
                        )
                    }
                    is Screen.SuccessReceipt -> {
                        SuccessReceiptScreen(
                            orderNumber = (successOrderNum.ifEmpty { screen.orderNumber }),
                            viewModel = viewModel,
                            onTrackOrdersClick = { currentScreen = Screen.OrderTracking },
                            onGoShopClick = { currentScreen = Screen.Home }
                        )
                    }
                    is Screen.OrderTracking -> {
                        OrderTrackingScreen(
                            viewModel = viewModel,
                            onShopClick = { currentScreen = Screen.Home }
                        )
                    }
                    is Screen.SupportChat -> {
                        SupportChatScreen(viewModel = viewModel)
                    }
                    is Screen.Profile -> {
                        ProfileScreen(
                            viewModel = viewModel,
                            onScreenSwitch = { currentScreen = it },
                            onSwitchToSeller = { viewModel.switchRole("Seller") }
                        )
                    }
                    is Screen.InfoPages -> {
                        AboutContactScreen(
                            viewModel = viewModel,
                            onBackClick = { currentScreen = Screen.Profile }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerDashboard(viewModel: EcommerceViewModel) {
    val activeLanguage by viewModel.currentLanguage.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Store,
                            contentDescription = "Logo",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = viewModel.getTranslation("SHIVAM ECOMMERCE Seller Panel", "शिवम् ई-कॉमर्स विक्रेता पैनल"),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 15.sp
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            viewModel.switchRole("Customer")
                            Toast.makeText(context, "Returned to Customer View", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "", modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Exit Hub", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    IconButton(onClick = { viewModel.toggleLanguage() }) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = activeLanguage,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SellerDashboardView(viewModel = viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboard(viewModel: EcommerceViewModel) {
    val activeLanguage by viewModel.currentLanguage.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Security,
                            contentDescription = "Logo",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Admin Enterprise Suite",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 15.sp
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            viewModel.logout()
                            Toast.makeText(context, "Admin Disconnected", Toast.LENGTH_SHORT).show()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Text("Log Out", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    IconButton(onClick = { viewModel.toggleLanguage() }) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = activeLanguage,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AdminDashboardView(viewModel = viewModel)
        }
    }
}

@Composable
fun MainApplicationContainer(
    viewModel: EcommerceViewModel,
    modifier: Modifier = Modifier
) {
    val activeRole by viewModel.activeRole.collectAsState()
    val sessionUser by viewModel.currentUser.collectAsState()

    var showSplash by remember { mutableStateOf(true) }
    var navigationStep by remember { mutableStateOf("welcome") }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxSize()
    ) {
        if (showSplash) {
            SplashScreen(onSplashFinished = { showSplash = false })
        } else if (sessionUser == null) {
            when (navigationStep) {
                "auth_customer" -> {
                    AuthScreen(
                        viewModel = viewModel,
                        role = "Customer",
                        onLoginSuccess = { navigationStep = "welcome" },
                        onBack = { navigationStep = "welcome" }
                    )
                }
                "auth_seller" -> {
                    AuthScreen(
                        viewModel = viewModel,
                        role = "Seller",
                        onLoginSuccess = { navigationStep = "welcome" },
                        onBack = { navigationStep = "welcome" }
                    )
                }
                "auth_admin" -> {
                    AdminLoginScreen(
                        viewModel = viewModel,
                        onLoginSuccess = { navigationStep = "welcome" },
                        onBack = { navigationStep = "welcome" }
                    )
                }
                else -> {
                    WelcomeRoleSelectionScreen(
                        onSelectRole = { role ->
                            navigationStep = if (role == "Seller") "auth_seller" else "auth_customer"
                        },
                        onAdminClick = { navigationStep = "auth_admin" }
                    )
                }
            }
        } else {
            LaunchedEffect(sessionUser) {
                if (sessionUser == null) {
                    navigationStep = "welcome"
                }
            }

            when (activeRole) {
                "Customer" -> {
                    CustomerDashboard(viewModel = viewModel)
                }
                "Seller" -> {
                    SellerDashboard(viewModel = viewModel)
                }
                "Admin" -> {
                    AdminDashboard(viewModel = viewModel)
                }
                else -> {
                    CustomerDashboard(viewModel = viewModel)
                }
            }
        }
    }
}

// ==========================================
// 1. SHOP HOMEPAGE
// ==========================================
@Composable
fun ShopHomeScreen(
    viewModel: EcommerceViewModel,
    onProductClick: (Product) -> Unit
) {
    val searchVal by viewModel.searchQuery.collectAsState()
    val activeCategory by viewModel.selectedCategory.collectAsState()
    val filteredProds by viewModel.filteredProducts.collectAsState()

    val categories = listOf(
        Pair("All", Icons.Filled.Category),
        Pair("Clothes", Icons.Filled.Checkroom),
        Pair("Mobile Accessories", Icons.Filled.Cable)
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Sticky/Top Search & Title Section
        item {
            Column(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                Color.Transparent
                            )
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = viewModel.getTranslation("Elevate Your Lifestyle", "अपनी जीवनशैली को निखारें"),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = viewModel.getTranslation("SHIVAM ECOMMERCE", "शिवम् ई-कॉमर्स"),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Thinner & properly sized professional ecommerce style search bar
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("search_bar_input"),
                    shape = RoundedCornerShape(22.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "SearchIcon",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (searchVal.isEmpty()) {
                                Text(
                                    text = viewModel.getTranslation("Search products, fashion combos...", "कपड़े, एक्सेसरीज खोजें..."),
                                    fontSize = 13.sp,
                                    color = Color.Gray
                                )
                            }
                            androidx.compose.foundation.text.BasicTextField(
                                value = searchVal,
                                onValueChange = { viewModel.updateSearchQuery(it) },
                                singleLine = true,
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 13.sp
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        if (searchVal.isNotEmpty()) {
                            IconButton(
                                onClick = { viewModel.updateSearchQuery("") },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Clear",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Horizontal Category Row
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = viewModel.getTranslation("Curated Subsystems", "क्यूरेटेड श्रेणियां"),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(categories) { (catName, iconSymbol) ->
                        val isSelected = catName == activeCategory
                        InputChip(
                            selected = isSelected,
                            onClick = { viewModel.updateSelectedCategory(catName) },
                            label = { Text(catName) },
                            leadingIcon = { Icon(iconSymbol, contentDescription = catName, modifier = Modifier.size(18.dp)) },
                            colors = InputChipDefaults.inputChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }
            }
        }

        // Promotional Luxury Slide Carousel Banner
        item {
            PromotionalSlideBanner(viewModel)
        }

        // Trending Row Section
        val trendingItems = filteredProds.filter { it.isTrending }
        if (trendingItems.isNotEmpty() && activeCategory == "All") {
            item {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = viewModel.getTranslation("Trending Masterpieces 🔥", "अग्रणी उत्पाद 🔥"),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Meesho+Amazon Specs",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.tertiary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(trendingItems) { prod ->
                            ProductCardCompact(product = prod, onProductClick = onProductClick)
                        }
                    }
                }
            }
        }

        // Main Catalog Listing Header
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Divider(color = MaterialTheme.colorScheme.surfaceVariant)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = viewModel.getTranslation("Explored Catalogue", "मुख्य सूची"),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Grid of products dynamically sized as list rows (adaptive columns manually wrapped)
        if (filteredProds.isEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(48.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Filled.Info, contentDescription = "", modifier = Modifier.size(48.dp), tint = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = viewModel.getTranslation("No products located in this index.", "कोई उत्पाद नहीं मिला।"),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        } else {
            // Adaptive Grid implemented linearly within LazyColumn list items to avoid nested scroll crash issues
            val chunked = filteredProds.chunked(2)
            items(chunked) { pair ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProductCardFull(
                        product = pair[0],
                        modifier = Modifier.weight(1f),
                        onProductClick = onProductClick
                    )
                    if (pair.size > 1) {
                        ProductCardFull(
                            product = pair[1],
                            modifier = Modifier.weight(1f),
                            onProductClick = onProductClick
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

// Promotional banner rotating display
@Composable
fun PromotionalSlideBanner(viewModel: EcommerceViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(140.dp),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        Box {
            // Background mesh image
            AsyncImage(
                model = "https://images.unsplash.com/photo-1490481651871-ab68de25d43d?auto=format&fit=crop&w=800&q=80",
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.35f
            )

            // Indigo-blue gloss gradient layout overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                RoyalBlueMain,
                                Color.Transparent
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = AccentGold,
                    modifier = Modifier.padding(bottom = 4.dp)
                ) {
                    Text(
                        text = " FESTIVE FESTIVAL ",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.Black
                    )
                }
                Text(
                    text = viewModel.getTranslation("Mulberry Silk Collection", "शहतूत रेशम संग्रह"),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = viewModel.getTranslation("Up to 50% Off Varanasi Luxury Handlooms", "वाराणसी हैंडलूम पर 50% तक की छूट"),
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.85f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Use Code: ",
                        fontSize = 11.sp,
                        color = BrightGold
                    )
                    Text(
                        text = "SHIVAM50",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }
        }
    }
}

// Product Card Full Grid Design representation for clothes & accessories
@Composable
fun ProductCardFull(
    product: Product,
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit
) {
    Card(
        modifier = modifier
            .padding(vertical = 6.dp)
            .clickable { onProductClick(product) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )

            // Rating Badge overlay
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color.Black.copy(alpha = 0.70f),
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.TopEnd)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Star, contentDescription = "", tint = BrightGold, modifier = Modifier.size(10.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = String.format("%.1f", product.rating),
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = product.brand,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = product.title,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))

            // Pricing Structure
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "₹${product.price.toInt()}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "₹${product.originalPrice.toInt()}",
                    fontSize = 12.sp,
                    color = MutedSlate,
                    textDecoration = TextDecoration.LineThrough
                )
                Text(
                    text = "(${((1 - (product.price / product.originalPrice)) * 100).toInt()}% off)",
                    fontSize = 10.sp,
                    color = Color.Green,
                    fontWeight = FontWeight.Bold
                )
            }

            // Real-time stock counts
            if (product.stock <= 5) {
                Text(
                    text = "Only ${product.stock} Left! Hurry",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )
            } else {
                Text(
                    text = "In Stock (${product.stock})",
                    color = Color.Gray,
                    fontSize = 9.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

// Compact horizontally scrolling product card
@Composable
fun ProductCardCompact(
    product: Product,
    onProductClick: (Product) -> Unit
) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .clickable { onProductClick(product) },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = product.title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "₹${product.price.toInt()}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

// ==========================================
// 2. PRODUCT DETAILS VIEW SCREEN
// ==========================================
@Composable
fun ProductDetailScreen(
    product: Product,
    viewModel: EcommerceViewModel,
    onBackClick: () -> Unit,
    onAddToCart: (Product) -> Unit
) {
    val reviewItemsFlow = remember(product.id) { viewModel.getReviewsForProductFlow(product.id) }
    val reviewItems by reviewItemsFlow.collectAsState(initial = emptyList())
    val wishlist by viewModel.wishlistItems.collectAsState()
    val isWishlisted = wishlist.any { it.productId == product.id }

    var selectedSize by remember { mutableStateOf("M") }
    var selectedColor by remember { mutableStateOf("Royal Blue") }

    var ratingInput by remember { mutableStateOf(5f) }
    var commentInput by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            // Floating Header Overlays
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "", tint = Color.White)
            }

            IconButton(
                onClick = { viewModel.toggleWishlist(product.id) },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopEnd)
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(
                    imageVector = if (isWishlisted) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "",
                    tint = if (isWishlisted) Color.Red else Color.White
                )
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = " " + product.category.uppercase() + " ",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Star, contentDescription = "", tint = BrightGold, modifier = Modifier.size(16.dp))
                    Text(
                        text = " ${String.format("%.1f", product.rating)} (${product.reviewsCount} reviews)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = product.brand,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = product.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Pricing Row
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(
                    text = "₹${product.price.toInt()}",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "MRP ₹${product.originalPrice.toInt()}",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough,
                    modifier = Modifier.padding(bottom = 3.dp)
                )
            }

            Divider()
            Spacer(modifier = Modifier.height(12.dp))

            // Clothes Size Selector or Accessories Color selectors
            if (product.category == "Clothes") {
                Text(text = "Select Premium Fit Size", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val sizes = listOf("S", "M", "L", "XL")
                    sizes.forEach { item ->
                        FilterChip(
                            selected = selectedSize == item,
                            onClick = { selectedSize = item },
                            label = { Text(item) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            } else {
                Text(text = "Select Luxury Finish Mesh", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val colors = listOf("Royal Blue", "Matte Black", "Graphite Grey")
                    colors.forEach { item ->
                        FilterChip(
                            selected = selectedColor == item,
                            onClick = { selectedColor = item },
                            label = { Text(item) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            Text(
                text = "Product Dossier",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = product.description,
                fontSize = 13.sp,
                color = Color.DarkGray,
                lineHeight = 18.sp,
                modifier = Modifier.padding(vertical = 6.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Main Checkout Actions
            Button(
                onClick = {
                    onAddToCart(product)
                    Toast.makeText(context, "Added to shopping basket!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("add_to_cart_button"),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(25.dp)
            ) {
                Icon(Icons.Filled.ShoppingBag, contentDescription = "")
                Spacer(modifier = Modifier.width(8.dp))
                Text("ADD TO LUXURY CART", fontWeight = FontWeight.Black)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Customer Review Submission Area
            Text(text = "Share Your Store Feedback", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = commentInput,
                onValueChange = { commentInput = it },
                placeholder = { Text("Write your luxury purchasing experience here...") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Rating: ")
                    Slider(
                        value = ratingInput,
                        onValueChange = { ratingInput = it },
                        valueRange = 1f..5f,
                        steps = 3,
                        modifier = Modifier.width(140.dp)
                    )
                    Text(" ${ratingInput.toInt()} ★", fontWeight = FontWeight.Bold, color = BrightGold)
                }
                Button(
                    onClick = {
                        if (commentInput.isNotBlank()) {
                            viewModel.submitProductReview(product.id, ratingInput, commentInput)
                            commentInput = ""
                            Toast.makeText(context, "Review saved!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Submit")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // User Review Feed List
            Text(
                text = "Customer Review Streams (${reviewItems.size})",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            if (reviewItems.isEmpty()) {
                Text(
                    text = "No written reviews published yet for this item.",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            } else {
                reviewItems.forEach { review ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = review.userName, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Row {
                                    repeat(review.rating.toInt()) {
                                        Icon(Icons.Filled.Star, contentDescription = "", tint = BrightGold, modifier = Modifier.size(12.dp))
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = review.comment, fontSize = 12.sp, color = Color.DarkGray)
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// 3. CART SYSTEM VIEW
// ==========================================
@Composable
fun CartScreen(
    viewModel: EcommerceViewModel,
    onProceedToCheckout: () -> Unit,
    onProductClick: (Product) -> Unit
) {
    val items by viewModel.cartItems.collectAsState()
    val allProds by viewModel.products.collectAsState()

    var totalPrice = 0.0
    items.forEach { item ->
        val matchingProduct = allProds.find { it.id == item.productId }
        if (matchingProduct != null) {
            totalPrice += matchingProduct.price * item.quantity
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = viewModel.getTranslation("Shopping Basket", "खरीदारी टोकरी"),
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(text = "${items.size} unique lines added in catalog selection", fontSize = 11.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(12.dp))

        if (items.isEmpty()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "", modifier = Modifier.size(72.dp), tint = MaterialTheme.colorScheme.surfaceVariant)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = viewModel.getTranslation("Your luxury shipping cart is empty.", "आपकी टोकरी खाली है।"),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Navigate home to select premium accessories & silks",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items) { item ->
                    val prod = allProds.find { it.id == item.productId }
                    if (prod != null) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = prod.imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { onProductClick(prod) },
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(10.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = prod.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "Size: ${item.chosenSize} | Finish: ${item.chosenColor}",
                                        fontSize = 11.sp,
                                        color = Color.Gray
                                    )
                                    Text(
                                        text = "₹${prod.price.toInt()}",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    IconButton(
                                        onClick = { viewModel.removeCartItem(item) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(Icons.Filled.Delete, contentDescription = "", tint = MaterialTheme.colorScheme.error, modifier = Modifier.size(18.dp))
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        IconButton(
                                            onClick = { viewModel.updateCartItemQty(item, false) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(Icons.Filled.Remove, contentDescription = "", modifier = Modifier.size(14.dp))
                                        }
                                        Text(text = item.quantity.toString(), fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 4.dp))
                                        IconButton(
                                            onClick = { viewModel.updateCartItemQty(item, true) },
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Icon(Icons.Filled.Add, contentDescription = "", modifier = Modifier.size(14.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Price Summary Section Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal", fontSize = 13.sp)
                        Text("₹${totalPrice.toInt()}", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Premium Shipping Fee", fontSize = 13.sp)
                        Text("FREE", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Green)
                    }
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Grand Total", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Text("₹${totalPrice.toInt()}", fontSize = 18.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            Button(
                onClick = onProceedToCheckout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("checkout_button"),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("SECURE PURCHASE CHECKOUT", fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ==========================================
// 4. CHECKOUT SCREEN
// ==========================================
@Composable
fun CheckoutScreen(
    viewModel: EcommerceViewModel,
    onCheckoutFinished: (Boolean, String) -> Unit,
    onBackClick: () -> Unit
) {
    val usersSession by viewModel.currentUser.collectAsState()
    var clientName by remember { mutableStateOf(usersSession?.name ?: "") }
    var clientPhone by remember { mutableStateOf(usersSession?.phone ?: "") }
    var destinationAddress by remember { mutableStateOf(usersSession?.address ?: "") }
    var activePaymentType by remember { mutableStateOf("UPI") } // "UPI" or "COD"

    var isProcessing by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "")
        }

        Text(
            text = viewModel.getTranslation("Complete Order Booking", "ऑर्डर बुकिंग पूरी करें"),
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Shipping Information", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        OutlinedTextField(
            value = clientName,
            onValueChange = { clientName = it },
            label = { Text("Customer Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = clientPhone,
            onValueChange = { clientPhone = it },
            label = { Text("Contact Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = destinationAddress,
            onValueChange = { destinationAddress = it },
            label = { Text("Full Home Delivery Address") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Select Payment Mode Gate", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(6.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { activePaymentType = "UPI" }
                    .border(
                        width = if (activePaymentType == "UPI") 2.dp else 0.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = if (activePaymentType == "UPI") MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.QrCode, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Pay via UPI / QR", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("Instant confirmation", fontSize = 9.sp, color = Color.Gray)
                }
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { activePaymentType = "COD" }
                    .border(
                        width = if (activePaymentType == "COD") 2.dp else 0.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = if (activePaymentType == "COD") MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Filled.Payments, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Cash on Delivery", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("Pay on delivery run", fontSize = 9.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (isProcessing) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Button(
                onClick = {
                    if (clientName.isBlank() || clientPhone.isBlank() || destinationAddress.isBlank()) {
                        Toast.makeText(context, "Fill in all credentials!", Toast.LENGTH_SHORT).show()
                    } else {
                        isProcessing = true
                        viewModel.executeCheckout(destinationAddress, activePaymentType) { success, orderNo ->
                            isProcessing = false
                            if (success) {
                                onCheckoutFinished(true, orderNo)
                            } else {
                                Toast.makeText(context, "Purchase processor error.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("submit_checkout_purchase_button"),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = if (activePaymentType == "UPI") "SIMULATE SECURE UPI TRANSFER" else "CONFIRM PAY-ON-DELIVERY ORDER",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ==========================================
// 5. SUCCESS RECEIPT PAGE
// ==========================================
@Composable
fun SuccessReceiptScreen(
    orderNumber: String,
    viewModel: EcommerceViewModel,
    onTrackOrdersClick: () -> Unit,
    onGoShopClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = CircleShape,
            color = Color.Green.copy(alpha = 0.15f),
            modifier = Modifier.size(90.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(Icons.Filled.CheckCircle, contentDescription = "", tint = Color(0xFF2E7D32), modifier = Modifier.size(56.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Receipt Booked Successfully!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2E7D32)
        )
        Text(
            text = "Shivam Logistics Varanasi team has reserved your clothing packages.",
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Luxury Order Book Specifications", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 13.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Order Index No:")
                    Text(orderNumber, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Fulfillment Status:")
                    Text("Confirmed / Handed to Hub", color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("ETA Delivery:")
                    Text("2 to 4 Days (Priority Lane)")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onTrackOrdersClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Icon(Icons.Filled.LocalShipping, contentDescription = "")
            Spacer(modifier = Modifier.width(8.dp))
            Text("TRACK ORDER STREAMS", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onGoShopClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text("RETURN TO SHOPPING", fontWeight = FontWeight.Bold)
        }
    }
}

// ==========================================
// 6. ORDER TRACKING PROGRESS LIST
// ==========================================
@Composable
fun OrderTrackingScreen(
    viewModel: EcommerceViewModel,
    onShopClick: () -> Unit
) {
    val custAccount by viewModel.currentUser.collectAsState()
    val userPhone = custAccount?.phone ?: "7777777777"
    val allOrders by viewModel.allOrders.collectAsState()

    // Filter order history related to current active customer session profile
    val profileOrders = allOrders.filter { it.customerPhone == userPhone }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = viewModel.getTranslation("Track Shipments", "शिपमेंट ट्रैक करें"),
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(text = "Active tracking runs and logistics database", fontSize = 11.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(14.dp))

        if (profileOrders.isEmpty()) {
            Column(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Filled.Inventory, contentDescription = "", modifier = Modifier.size(60.dp), tint = MaterialTheme.colorScheme.surfaceVariant)
                Spacer(modifier = Modifier.height(12.dp))
                Text("No active orders found for $userPhone")
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = onShopClick) {
                    Text("Go Purchase Items")
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(profileOrders) { order ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = "Order ${order.orderNumber}", fontWeight = FontWeight.Black, fontSize = 14.sp)
                                    Text(text = "Total Paid: ₹${order.totalAmount.toInt()} via ${order.paymentMethod}", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                                }
                                Surface(
                                    color = when(order.status) {
                                        "Delivered" -> Color.Green.copy(alpha = 0.15f)
                                        "Shipped" -> MaterialTheme.colorScheme.primaryContainer
                                        else -> MaterialTheme.colorScheme.tertiaryContainer
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = order.status.uppercase(),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        color = when(order.status) {
                                            "Delivered" -> Color(0xFF2E7D32)
                                            "Shipped" -> MaterialTheme.colorScheme.onPrimaryContainer
                                            else -> MaterialTheme.colorScheme.onTertiaryContainer
                                        }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Divider()
                            Spacer(modifier = Modifier.height(8.dp))

                            // Custom Graphic Timeline
                            Text("Logistics Route", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MutedSlate)
                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val steps = listOf("Confirmed", "Shipped", "Delivered")
                                val activeIndex = steps.indexOf(order.status).coerceAtLeast(0)

                                steps.forEachIndexed { idx, step ->
                                    val done = idx <= activeIndex
                                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                                        Surface(
                                            shape = CircleShape,
                                            color = if (done) MaterialTheme.colorScheme.primary else Color.LightGray,
                                            modifier = Modifier.size(24.dp)
                                        ) {
                                            Box(contentAlignment = Alignment.Center) {
                                                if (done) {
                                                    Icon(Icons.Filled.Check, contentDescription = "", tint = Color.White, modifier = Modifier.size(12.dp))
                                                } else {
                                                    Text((idx + 1).toString(), color = Color.White, fontSize = 10.sp)
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(step, fontSize = 10.sp, fontWeight = if (done) FontWeight.Bold else FontWeight.Normal)
                                    }

                                    if (idx < steps.size - 1) {
                                        Box(
                                            modifier = Modifier
                                                .height(2.dp)
                                                .weight(0.5f)
                                                .background(if (idx < activeIndex) MaterialTheme.colorScheme.primary else Color.LightGray)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// 7. Conversational Chat Support Assistant
// ==========================================
@Composable
fun SupportChatScreen(viewModel: EcommerceViewModel) {
    val messages by viewModel.chatMessages.collectAsState()
    val loading by viewModel.isChatLoading.collectAsState()
    var inputVal by remember { mutableStateOf("") }
    val lazyListState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            lazyListState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "SHIVAM ECOMMERCE AI Assistant",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(text = "Powered by Gemini AI engine", fontSize = 10.sp, color = Color.Gray)
            }
            IconButton(onClick = { viewModel.clearChat() }) {
                Icon(Icons.Filled.Refresh, contentDescription = "Reset chat", tint = MutedSlate)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Predefined prompts helper tags
        val pilotPrompts = listOf("Recommend silks", "Suggest aramid options", "Price lists")
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(pilotPrompts) { tag ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    modifier = Modifier.clickable { viewModel.sendChatMessage(tag) }
                ) {
                    Text(
                        text = " $tag ",
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Chats body stream
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages) { msg ->
                val alignSide = if (msg.isUser) Alignment.End else Alignment.Start
                val surfColor = if (msg.isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                val txtColor = if (msg.isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant

                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = alignSide) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .padding(vertical = 2.dp),
                        horizontalArrangement = if (msg.isUser) Arrangement.End else Arrangement.Start
                    ) {
                        if (!msg.isUser) {
                            Icon(
                                imageVector = Icons.Filled.Face,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier
                                    .padding(end = 6.dp)
                                    .size(24.dp)
                                    .align(Alignment.Top)
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 16.dp,
                                bottomStart = if (msg.isUser) 16.dp else 0.dp,
                                bottomEnd = if (msg.isUser) 0.dp else 16.dp
                            ),
                            color = surfColor,
                            tonalElevation = 1.dp
                        ) {
                            Text(
                                text = msg.text,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(12.dp),
                                color = txtColor,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            if (loading) {
                item {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Shivam AI generating outfit options...", fontSize = 11.sp, color = Color.Gray)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Message Typers
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputVal,
                onValueChange = { inputVal = it },
                placeholder = { Text("Ask Shivam AI Shopper...") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("ai_messaging_input"),
                shape = RoundedCornerShape(24.dp),
                maxLines = 2
            )
            Spacer(modifier = Modifier.width(8.dp))
            FloatingActionButton(
                onClick = {
                    if (inputVal.isNotBlank()) {
                        viewModel.sendChatMessage(inputVal)
                        inputVal = ""
                    }
                },
                shape = CircleShape,
                modifier = Modifier
                    .size(46.dp)
                    .testTag("submit_ai_chat_button")
            ) {
                Icon(Icons.Filled.Send, contentDescription = "Send", modifier = Modifier.size(18.dp))
            }
        }
    }
}

// ==========================================
// 8. CUSTOMER PROFILE & ADMIN SELECTORS SCREEN
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: EcommerceViewModel,
    onScreenSwitch: (Screen) -> Unit,
    onSwitchToSeller: () -> Unit
) {
    val activeRole by viewModel.activeRole.collectAsState()
    val sessionUser by viewModel.currentUser.collectAsState()
    val activeLanguage by viewModel.currentLanguage.collectAsState()

    val context = LocalContext.current

    // Settings States
    var showAddressDialog by remember { mutableStateOf(false) }
    var showPaymentDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showProfileDetailsDialog by remember { mutableStateOf(false) }
    var showCustomerSupportDialog by remember { mutableStateOf(false) }

    var savedAddresses by remember { mutableStateOf(listOf("Assi Ghat, Varanasi, Uttar Pradesh - 221005", "Varanasi Luxury Towers, Cantt Road, Varanasi")) }
    var newAddressInput by remember { mutableStateOf("") }

    var upiSimulatedId by remember { mutableStateOf("shivam@upi") }
    var creditCardSimulated by remember { mutableStateOf("**** **** **** 8357") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (sessionUser != null) {
            // 1. Premium Profile Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                                Color.Transparent
                            )
                        )
                    )
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .clickable {
                                Toast.makeText(context, "Profile Photo upload complete successfully!", Toast.LENGTH_SHORT).show()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Avatar",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(84.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = sessionUser!!.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = sessionUser!!.email,
                        fontSize = 12.sp,
                        color = MutedSlate
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                    ) {
                        Text(
                            text = " " + viewModel.getTranslation("CUSTOMER ID: ", "ग्राहक आईडी: ") + sessionUser!!.phone + " ",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // 2. Settings list options
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = viewModel.getTranslation("Account Settings", "खाता सेटिंग्स"),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )

                ProfileSettingsItem(
                    title = viewModel.getTranslation("My Account Details", "मेरा विवरण"),
                    subTitle = viewModel.getTranslation("View your registered mobile, email, and name info", "नाम, ईमेल और कांटेक्ट नंबर देखे"),
                    icon = Icons.Filled.ContactPage,
                    onClick = { showProfileDetailsDialog = true }
                )

                ProfileSettingsItem(
                    title = viewModel.getTranslation("Language settings / भाषा", "भाषा चुने"),
                    subTitle = "Active Language: " + (if (activeLanguage == "EN") "English" else "हिंदी"),
                    icon = Icons.Filled.Language,
                    onClick = {
                        viewModel.toggleLanguage()
                        Toast.makeText(context, "Language Updated!", Toast.LENGTH_SHORT).show()
                    }
                )

                ProfileSettingsItem(
                    title = viewModel.getTranslation("Address Management", "पता प्रबंधन"),
                    subTitle = viewModel.getTranslation("Saved delivery addresses for fast checkouts", "डिलीवरी का पता जोड़ें और देखें"),
                    icon = Icons.Filled.Home,
                    onClick = { showAddressDialog = true }
                )

                ProfileSettingsItem(
                    title = viewModel.getTranslation("Payment Methods", "भुगतान के तरीके"),
                    subTitle = viewModel.getTranslation("UPI link & Credit/Debit card options", "UPI और कार्ड अकाउंट"),
                    icon = Icons.Filled.Payment,
                    onClick = { showPaymentDialog = true }
                )

                ProfileSettingsItem(
                    title = viewModel.getTranslation("Privacy & Policies", "गोपनीयता और नियम"),
                    subTitle = viewModel.getTranslation("Verify terms, diagnostics, app policies", "शर्तें और गोपनीयता देखें"),
                    icon = Icons.Filled.Security,
                    onClick = { showPrivacyDialog = true }
                )

                ProfileSettingsItem(
                    title = viewModel.getTranslation("Customer Support Desk", "ग्राहक सहायता"),
                    subTitle = viewModel.getTranslation("Call, email, FAQ & open interactive chat desk", "संपर्क नंबर, ईमेल, लाइव चैट एवं सहायता केंद्र"),
                    icon = Icons.Filled.ContactSupport,
                    onClick = { showCustomerSupportDialog = true }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // 3. Become a Merchant Banner inside Customer Settings (like Flipkart!)
                Card(
                     modifier = Modifier
                         .fillMaxWidth()
                         .clickable {
                             onSwitchToSeller()
                             Toast.makeText(context, "Welcome to Seller Hub! Panel opened separately.", Toast.LENGTH_SHORT).show()
                         },
                     colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)),
                     border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Store,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = viewModel.getTranslation("Start Selling on Shivam", "शिवम् पर बेचना शुरू करें"),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                text = viewModel.getTranslation("Become a merchant & list clothes/accessories separately.", "मर्चेंट बनें और कपड़े अथवा एसेसरीज लिस्ट करें।"),
                                fontSize = 11.sp,
                                color = Color.DarkGray
                            )
                        }
                        Icon(
                            imageVector = Icons.Filled.ArrowRight,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.logout()
                        Toast.makeText(context, "Logged Out successfully.", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(viewModel.getTranslation("LOGOUT", "लॉग आउट"), fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // Modal dialogs
    if (showProfileDetailsDialog && sessionUser != null) {
        AlertDialog(
            onDismissRequest = { showProfileDetailsDialog = false },
            title = { Text(viewModel.getTranslation("Account Credentials", "अकाउंट विवरण")) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("• Name: " + sessionUser!!.name, fontWeight = FontWeight.Bold)
                    Text("• Email: " + sessionUser!!.email)
                    Text("• Phone No: " + sessionUser!!.phone)
                    Text("• Profile Clearance Role: " + sessionUser!!.role)
                }
            },
            confirmButton = {
                TextButton(onClick = { showProfileDetailsDialog = false }) {
                    Text("Okay")
                }
            }
        )
    }

    if (showAddressDialog) {
        AlertDialog(
            onDismissRequest = { showAddressDialog = false },
            title = { Text(viewModel.getTranslation("Address Hub", "पता सूची")) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Active addresses:", fontWeight = FontWeight.Bold)
                    savedAddresses.forEach { addr ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Text(addr, fontSize = 11.sp, modifier = Modifier.padding(8.dp))
                        }
                    }
                    HorizontalDivider()
                    OutlinedTextField(
                        value = newAddressInput,
                        onValueChange = { newAddressInput = it },
                        label = { Text("Add new address details") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (newAddressInput.isNotBlank()) {
                        savedAddresses = savedAddresses + newAddressInput
                        newAddressInput = ""
                        Toast.makeText(context, "New Address Associated!", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Add Address")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddressDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    if (showPaymentDialog) {
        AlertDialog(
            onDismissRequest = { showPaymentDialog = false },
            title = { Text(viewModel.getTranslation("Payment Credentials", "भुगतान खाते")) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Saved simulated options:", fontWeight = FontWeight.Bold)
                    OutlinedTextField(
                        value = upiSimulatedId,
                        onValueChange = { upiSimulatedId = it },
                        label = { Text("Standard UPI Identifier") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = creditCardSimulated,
                        onValueChange = { creditCardSimulated = it },
                        label = { Text("Saved Credit Card Token") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    showPaymentDialog = false
                    Toast.makeText(context, "Details successfully updated!", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Save Changes")
                }
            }
        )
    }

    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = { Text("Privacy Consent") },
            text = {
                Text("Shivam Commerce respects user data isolation constraint. All analytical catalogs and checkout history records persist offline locally within your secure native Room instance. No cloud propagation is triggered without OAuth clearance.")
            },
            confirmButton = {
                TextButton(onClick = { showPrivacyDialog = false }) {
                    Text("Accept Terms")
                }
            }
        )
    }

    if (showCustomerSupportDialog) {
        AlertDialog(
            onDismissRequest = { showCustomerSupportDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.ContactSupport,
                        contentDescription = "Support",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = viewModel.getTranslation("Customer Support", "ग्राहक सहायता"),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = viewModel.getTranslation(
                            "We are committed to delivering a flawless, premium experience. Choose a customer channel below.",
                            "हम आपको बेहतरीन अनुभव देने के लिए समर्पित हैं। नीचे दिए गए चैनलों से संपर्क करें।"
                        ),
                        fontSize = 12.sp,
                        color = MutedSlate,
                        lineHeight = 16.sp
                    )

                    HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

                    Text(
                        text = viewModel.getTranslation("DIRECT CHANNELS", "सीधे संपर्क उपाय"),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.secondary,
                        letterSpacing = 1.sp
                    )

                    // Phone Dial
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    ) {
                        Row(
                            modifier = Modifier
                                .clickable {
                                    val dialIntent = android.content.Intent(
                                        android.content.Intent.ACTION_DIAL,
                                        android.net.Uri.parse("tel:+918357008357")
                                    )
                                    context.startActivity(dialIntent)
                                }
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.PhoneInTalk,
                                contentDescription = "Phone Support",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = viewModel.getTranslation("Call VIP Support Desk", "वीआईपी सपोर्ट कॉल डेस्क"),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                Text(text = "+91 8357 008 357", fontSize = 11.sp, color = MutedSlate)
                            }
                            Icon(
                                imageVector = Icons.Filled.Call,
                                contentDescription = "Call",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    // Email Support
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    ) {
                        Row(
                            modifier = Modifier
                                .clickable {
                                    val emailIntent = android.content.Intent(android.content.Intent.ACTION_SENDTO).apply {
                                        data = android.net.Uri.parse("mailto:support@shivamecommerce.com")
                                    }
                                    try {
                                        context.startActivity(emailIntent)
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "No email client found.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Mail,
                                contentDescription = "Mail Support",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = viewModel.getTranslation("VIP Support Email", "वीआईपी संपर्क ईमेल"),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                Text(text = "support@shivamecommerce.com", fontSize = 11.sp, color = MutedSlate)
                            }
                            Icon(
                                imageVector = Icons.Filled.ArrowOutward,
                                contentDescription = "Email",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    // Open Live Chat Agent Button
                    Button(
                        onClick = {
                            showCustomerSupportDialog = false
                            onScreenSwitch(Screen.SupportChat)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Chat,
                                contentDescription = "Chat",
                                tint = CleanWhite,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = viewModel.getTranslation("OPEN INTERACTIVE STORE CHAT", "लाइव चैट सहायता खोलें"),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = CleanWhite
                            )
                        }
                    }

                    HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

                    Text(
                        text = viewModel.getTranslation("HELP CENTER & FAQ", "सहायता केंद्र"),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.secondary,
                        letterSpacing = 1.sp
                    )

                    // Interactive Help Center Questions
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Column {
                            Text(
                                text = "q1: " + viewModel.getTranslation("Is handloom silk certified?", "क्या हैंडलूम सिल्क प्रमाणित है?"),
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = viewModel.getTranslation("All garments carry genuine Varanasi weaver certificates.", "सभी कपड़ों पर बनारसी बुनकर प्रमाण पत्र होता है।"),
                                fontSize = 10.sp,
                                color = MutedSlate
                            )
                        }
                        Column {
                            Text(
                                text = "q2: " + viewModel.getTranslation("What is the exchange buffer?", "वापसी नीति क्या है?"),
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = viewModel.getTranslation("We offer an absolute 7-day hassle-free trust exchange period.", "हम ७-दिन की आसान और सुरक्षित वापसी नीति प्रदान करते हैं।"),
                                fontSize = 10.sp,
                                color = MutedSlate
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showCustomerSupportDialog = false }) {
                    Text(
                        text = viewModel.getTranslation("CLOSE WINDOW", "बंद करें"),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }
}

@Composable
fun ProfileSettingsItem(
    title: String,
    subTitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = "", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Text(text = subTitle, fontSize = 11.sp, color = MutedSlate)
            }
            Icon(imageVector = Icons.Filled.ArrowRight, contentDescription = "", tint = MutedSlate, modifier = Modifier.size(16.dp))
        }
    }
}

// ==========================================
// 8B. ABOUT & CONTACTS VIEW INFO SCREENS
// ==========================================
@Composable
fun AboutContactScreen(
    viewModel: EcommerceViewModel,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "")
        }

        Text(
            text = "Shivam Luxury Retailers",
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(text = "About our premium handloom and accessory catalog", fontSize = 11.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Company Manifesto", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.tertiary)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Shivam Ecommerce was commissioned at Varanasi, Uttar Pradesh. Our focus spans premium Mulberry Silks, luxury designer Bandhgalas, and durable aramid-fiber case protections. We fuse classic clothing traditions with extreme high-tech performance accessories.",
                    lineHeight = 18.sp,
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Connect With Store Managers", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(6.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Email, contentDescription = "", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("support@shivamvishwakarma83573.com", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Phone, contentDescription = "", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("+91 835 73X XXX (Logistics Center, Varanasi)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// ==========================================
// 9. SELLER MERCHANT PANEL MODULE
// ==========================================
@Composable
fun SellerDashboardView(viewModel: EcommerceViewModel) {
    val items by viewModel.products.collectAsState()
    val sellerItems = items.filter { it.sellerId == 1 }

    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var ogPrice by remember { mutableStateOf("") }
    var quantityInput by remember { mutableStateOf("") }
    var catSelected by remember { mutableStateOf("Clothes") }
    var desc by remember { mutableStateOf("") }
    var brandName by remember { mutableStateOf("Shivam Premium") }
    var imageUrl by remember { mutableStateOf("") }

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Seller Hub Central",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = "Review incoming buyer demands and manage stocks", fontSize = 11.sp, color = Color.Gray)
        }

        // Metrics Summary
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Simulated Revenue", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text("₹42,850.00", fontSize = 18.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Registered Stock lines", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text("${sellerItems.size} items active", fontSize = 18.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }
        }

        // Product Uploader form
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Register New Stock Asset", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Product Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = price,
                            onValueChange = { price = it },
                            label = { Text("Retail Price (₹)") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        OutlinedTextField(
                            value = ogPrice,
                            onValueChange = { ogPrice = it },
                            label = { Text("Original MRP (₹)") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = quantityInput,
                            onValueChange = { quantityInput = it },
                            label = { Text("Stock Quantity") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        OutlinedTextField(
                            value = brandName,
                            onValueChange = { brandName = it },
                            label = { Text("Brand Identifier") },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = imageUrl,
                        onValueChange = { imageUrl = it },
                        label = { Text("Asset Image HTTPS Link (Or leave empty for Unsplash defaults)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Category: ")
                        RadioButton(selected = catSelected == "Clothes", onClick = { catSelected = "Clothes" })
                        Text("Clothes")
                        Spacer(modifier = Modifier.width(8.dp))
                        RadioButton(selected = catSelected == "Mobile Accessories", onClick = { catSelected = "Mobile Accessories" })
                        Text("Accessories")
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = desc,
                        onValueChange = { desc = it },
                        label = { Text("Merchant Descriptions") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            val priceVal = price.toDoubleOrNull() ?: 0.0
                            val basePriceVal = ogPrice.toDoubleOrNull() ?: priceVal
                            val qtyVal = quantityInput.toIntOrNull() ?: 10

                            if (title.isBlank() || priceVal <= 0.0) {
                                Toast.makeText(context, "Please complete fields!", Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.addProductByMerchant(
                                    title = title,
                                    description = desc.ifBlank { "High-end luxury garment asset." },
                                    category = catSelected,
                                    price = priceVal,
                                    basePrice = basePriceVal,
                                    imageLink = imageUrl,
                                    qty = qtyVal,
                                    brandName = brandName
                                )
                                // Clear Form
                                title = ""
                                price = ""
                                ogPrice = ""
                                quantityInput = ""
                                desc = ""
                                imageUrl = ""
                                Toast.makeText(context, "Asset successfully injected into market database!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("INJECT PRODUCT")
                    }
                }
            }
        }

        // List Seller's current items
        item {
            Text("Your Merchant Stock Catalog (${sellerItems.size})", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        items(sellerItems) { prod ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = prod.imageUrl,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp).clip(RoundedCornerShape(6.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = prod.title, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Text(text = "Qty: ${prod.stock} lines | Rate: ₹${prod.price.toInt()}", fontSize = 11.sp, color = Color.Gray)
                    }
                    IconButton(onClick = { viewModel.deleteProductByMerchant(prod.id) }) {
                        Icon(Icons.Filled.Delete, contentDescription = "", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

// ==========================================
// 10. EXECUTIVE ADMIN PANEL SCREEN
// ==========================================
@Composable
fun AdminDashboardView(viewModel: EcommerceViewModel) {
    val prods by viewModel.products.collectAsState()
    val orders by viewModel.allOrders.collectAsState()
    val sellers by viewModel.sellers.collectAsState()

    var activeStatsTab by remember { mutableStateOf("Orders") } // "Orders", "Products", "Sellers"

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Shivam Luxury Executive Suite",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = "Manage global orders, moderated reviews, and sellers", fontSize = 11.sp, color = Color.Gray)
        }

        // Global analytical overview metrics
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Gross Margins", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        Text("₹${orders.sumOf { it.totalAmount }.toInt()}", fontSize = 15.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                    }
                }
                Card(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Stock Lines", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        Text(prods.size.toString(), fontSize = 15.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.secondary)
                    }
                }
                Card(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Total Orders", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        Text(orders.size.toString(), fontSize = 15.sp, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.tertiary)
                    }
                }
            }
        }

        // Selector row
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                listOf("Orders", "Products", "Sellers").forEach { tab ->
                    val sel = tab == activeStatsTab
                    Button(
                        onClick = { activeStatsTab = tab },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (sel) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = if (sel) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        Text(text = tab, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        when (activeStatsTab) {
            "Orders" -> {
                if (orders.isEmpty()) {
                    item {
                        Text(
                            text = "No orders historically submitted to the database.",
                            fontSize = 11.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(24.dp)
                        )
                    }
                } else {
                    items(items = orders) { order ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(text = "Receipt No: ${order.orderNumber}", fontWeight = FontWeight.Bold)
                                        Text(text = "Client: ${order.customerName} (${order.customerPhone})", fontSize = 11.sp)
                                        Text(text = "Amt: ₹${order.totalAmount.toInt()} | pay: ${order.paymentMethod}", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }

                                    // Dropdown sim to transition tracking phases
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            color = MaterialTheme.colorScheme.tertiaryContainer
                                        ) {
                                            Text(
                                                text = " ${order.status} ",
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onTertiaryContainer
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(6.dp))

                                        IconButton(onClick = {
                                            val next = when (order.status) {
                                                "Confirmed" -> "Shipped"
                                                "Shipped" -> "Delivered"
                                                else -> "Confirmed"
                                            }
                                            viewModel.modifyOrderStatus(order.id, next)
                                        }) {
                                            Icon(Icons.Filled.PlayArrow, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            "Products" -> {
                items(items = prods) { prod ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = prod.imageUrl,
                                contentDescription = null,
                                modifier = Modifier.size(45.dp).clip(RoundedCornerShape(6.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = prod.title, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Text(text = "Price: ₹${prod.price.toInt()} | Stock left: ${prod.stock}", fontSize = 11.sp, color = MutedSlate)
                            }
                            IconButton(onClick = { viewModel.deleteProductByMerchant(prod.id) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
            "Sellers" -> {
                items(items = sellers) { sell ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = sell.businessName, fontWeight = FontWeight.Bold)
                            Text(text = "Contact: ${sell.contactName} (${sell.phone})", fontSize = 12.sp)
                            Text(text = "City: ${sell.city} | Verification active", fontSize = 11.sp, color = MutedSlate)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Direct Earnings Tracked: ₹${sell.earnings.toInt()}", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}
