package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.database.AppDatabase
import com.example.data.model.*
import com.example.data.repository.EcommerceRepository
import com.example.data.network.GeminiApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

class EcommerceViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val repository = EcommerceRepository(db)

    // --- Active User Sessions & Configuration ---
    private val _currentUser = MutableStateFlow<UserAccount?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _activeRole = MutableStateFlow("Customer") // "Customer", "Seller", "Admin"
    val activeRole = _activeRole.asStateFlow()

    private val _currentLanguage = MutableStateFlow("EN") // "EN" or "HI"
    val currentLanguage = _currentLanguage.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All") // "All", "Clothes", "Mobile Accessories"
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _selectedBrand = MutableStateFlow("All")
    val selectedBrand = _selectedBrand.asStateFlow()

    // --- Reactive Data Flows ---
    val products = repository.allProducts.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val cartItems = repository.cartItems.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val wishlistItems = repository.wishlistItems.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val allOrders = repository.allOrders.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    val sellers = repository.allSellers.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    // --- AI Shopper Companion Chat ---
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(text = "Namaste & Welcome to **Shivam Ecommerce**! 🌟 I am Shivam AI, your luxury wardrobe & titanium accessories stylist. Ask me anything!", isUser = false)
        )
    )
    val chatMessages = _chatMessages.asStateFlow()

    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading = _isChatLoading.asStateFlow()

    // --- Search & Filtering helper ---
    val filteredProducts = combine(products, searchQuery, selectedCategory, selectedBrand) { prodList, query, cat, brand ->
        var list = prodList
        if (query.isNotEmpty()) {
            list = list.filter { it.title.contains(query, ignoreCase = true) || it.brand.contains(query, ignoreCase = true) }
        }
        if (cat != "All") {
            list = list.filter { it.category == cat }
        }
        if (brand != "All") {
            list = list.filter { it.brand == brand }
        }
        list
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            // Guarantee full local database initialization safely & asynchronously
            repository.checkAndPrepopulate()
        }
    }

    // --- Language Swapper ---
    fun toggleLanguage() {
        _currentLanguage.value = if (_currentLanguage.value == "EN") "HI" else "EN"
    }

    fun getTranslation(en: String, hi: String): String {
        return if (_currentLanguage.value == "EN") en else hi
    }

    // --- Authentication Actions ---
    fun login(emailOrPhone: String, code: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            val user = if (emailOrPhone.contains("@")) {
                repository.getUserByEmail(emailOrPhone)
            } else {
                repository.getUserByPhone(emailOrPhone)
            }

            if (user != null && user.passwordHash == code) {
                _currentUser.value = user
                _activeRole.value = user.role
                onSuccess()
            } else if (emailOrPhone == "admin@shivam.com" && code == "admin123") {
                // Hardcoded fallback safety failover
                val fallbackAdmin = UserAccount(name = "Shivam Admin", email = "admin@shivam.com", phone = "9999999999", passwordHash = "admin123", role = "Admin")
                _currentUser.value = fallbackAdmin
                _activeRole.value = "Admin"
                onSuccess()
            } else {
                onFailure("Invalid email/mobile number or password code.")
            }
        }
    }

    fun register(name: String, email: String, phone: String, code: String, role: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val newUser = UserAccount(
                name = name,
                email = email,
                phone = phone,
                passwordHash = code,
                role = role
            )
            repository.insertUser(newUser)
            _currentUser.value = newUser
            _activeRole.value = role

            // If registering as seller, register them as seller profile too
            if (role == "Seller") {
                repository.insertSeller(
                    SellerProfile(
                        businessName = "$name Elite Hub",
                        contactName = name,
                        email = email,
                        phone = phone,
                        city = "Varanasi"
                    )
                )
            }

            onSuccess()
        }
    }

    fun logout() {
        _currentUser.value = null
        _activeRole.value = "Customer"
    }

    fun switchRole(role: String) {
        _activeRole.value = role
    }

    // --- Product Search/Filter setters ---
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updateSelectedCategory(cat: String) {
        _selectedCategory.value = cat
    }

    // --- Cart Management ---
    fun addToCart(product: Product, size: String = "M", color: String = "Royal Blue") {
        viewModelScope.launch {
            val existing = cartItems.value.find { it.productId == product.id && it.chosenSize == size && it.chosenColor == color }
            if (existing != null) {
                repository.updateCartItem(existing.copy(quantity = existing.quantity + 1))
            } else {
                repository.insertCartItem(CartItem(productId = product.id, quantity = 1, chosenSize = size, chosenColor = color))
            }
        }
    }

    fun removeCartItem(item: CartItem) {
        viewModelScope.launch {
            repository.deleteCartItem(item)
        }
    }

    fun updateCartItemQty(item: CartItem, increase: Boolean) {
        viewModelScope.launch {
            val newQty = if (increase) item.quantity + 1 else item.quantity - 1
            if (newQty <= 0) {
                repository.deleteCartItem(item)
            } else {
                repository.updateCartItem(item.copy(quantity = newQty))
            }
        }
    }

    // --- Wishlist Management ---
    fun toggleWishlist(productId: Int) {
        viewModelScope.launch {
            val isInWish = wishlistItems.value.any { it.productId == productId }
            if (isInWish) {
                repository.removeFromWishlist(productId)
            } else {
                repository.addToWishlist(productId)
            }
        }
    }

    // --- Checkout & Payment Simulation ---
    fun executeCheckout(address: String, paymentMethod: String, onComplete: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val cartList = cartItems.value
            if (cartList.isEmpty()) {
                onComplete(false, "Cart is empty")
                return@launch
            }

            var total = 0.0
            val itemsDesc = StringBuilder("[")
            for (item in cartList) {
                val prod = products.value.find { it.id == item.productId }
                if (prod != null) {
                    val price = prod.price
                    total += price * item.quantity
                    // Deduct stock
                    val updatedStock = maxOf(0, prod.stock - item.quantity)
                    repository.updateProduct(prod.copy(stock = updatedStock))
                    // For sellers, append earnings simulation
                    repository.updateSellerEarnings(prod.sellerId, price * item.quantity)

                    itemsDesc.append("{\"id\":${prod.id},\"title\":\"${prod.title}\",\"qty\":${item.quantity},\"price\":$price},")
                }
            }
            if (itemsDesc.length > 1) {
                itemsDesc.setLength(itemsDesc.length - 1) // Remove last comma
            }
            itemsDesc.append("]")

            val phoneObj = currentUser.value?.phone ?: "7777777777"
            val nameObj = currentUser.value?.name ?: "Customer"

            val orderNo = "SHEM" + (100000 + (Math.random() * 900000).toInt())

            val orderRecord = OrderRecord(
                orderNumber = orderNo,
                customerName = nameObj,
                customerAddress = address,
                customerPhone = phoneObj,
                status = "Confirmed",
                paymentMethod = paymentMethod,
                paymentStatus = if (paymentMethod == "UPI") "Paid" else "Pending",
                totalAmount = total,
                productIdsJson = itemsDesc.toString()
            )

            val orderId = repository.insertOrder(orderRecord)
            repository.clearCart()

            if (orderId > 0) {
                onComplete(true, orderNo)
            } else {
                onComplete(false, "Database insert error")
            }
        }
    }

    // --- Admin & Seller Management Panel Tools ---
    fun addProductByMerchant(
        title: String,
        description: String,
        category: String,
        price: Double,
        basePrice: Double,
        imageLink: String,
        qty: Int,
        brandName: String,
        merchantId: Int = 1
    ) {
        viewModelScope.launch {
            val prod = Product(
                title = title,
                description = description,
                category = category,
                price = price,
                originalPrice = basePrice,
                imageUrl = if (imageLink.startsWith("http")) imageLink else "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?auto=format&fit=crop&w=600&q=80",
                stock = qty,
                rating = 5.0f,
                reviewsCount = 0,
                sellerId = merchantId,
                brand = brandName,
                isNewArrival = true
            )
            repository.insertProduct(prod)
        }
    }

    fun updateProductByMerchant(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product)
        }
    }

    fun deleteProductByMerchant(id: Int) {
        viewModelScope.launch {
            repository.deleteProductById(id)
        }
    }

    fun modifyOrderStatus(id: Int, nextState: String) {
        viewModelScope.launch {
            repository.updateOrderStatus(id, nextState)
        }
    }

    // --- Reviews ---
    fun submitProductReview(productId: Int, rate: Float, reviewBody: String) {
        viewModelScope.launch {
            val nameObj = currentUser.value?.name ?: "Verified Shopper"
            val record = ReviewRecord(
                productId = productId,
                userName = nameObj,
                rating = rate,
                comment = reviewBody
            )
            repository.insertReview(record)

            // Dynamic rating average update for the product
            val details = repository.getProductById(productId)
            if (details != null) {
                val newCount = details.reviewsCount + 1
                val newRating = ((details.rating * details.reviewsCount) + rate) / newCount
                repository.updateProduct(details.copy(rating = newRating, reviewsCount = newCount))
            }
        }
    }

    fun getReviewsForProductFlow(productId: Int): Flow<List<ReviewRecord>> {
        return repository.getReviewsForProduct(productId)
    }

    // --- AI Message Submission ---
    fun sendChatMessage(msgText: String) {
        if (msgText.isBlank()) return
        val userMsg = ChatMessage(text = msgText, isUser = true)
        _chatMessages.update { it + userMsg }

        _isChatLoading.value = true
        viewModelScope.launch {
            val replyText = GeminiApiClient.generateChatResponse(msgText)
            _chatMessages.update { it + ChatMessage(text = replyText, isUser = false) }
            _isChatLoading.value = false
        }
    }

    fun clearChat() {
        _chatMessages.value = listOf(
            ChatMessage(text = "Greetings! Let's build your lifestyle. Type what clothes or accessories you require advice on.", isUser = false)
        )
    }

    suspend fun checkUserExists(email: String, phone: String): String? = withContext(Dispatchers.IO) {
        val userByEmail = repository.getUserByEmail(email)
        if (userByEmail != null) return@withContext "Email address is already registered."
        val userByPhone = repository.getUserByPhone(phone)
        if (userByPhone != null) return@withContext "Mobile number is already registered."
        null
    }

    suspend fun findUserAccount(emailOrPhone: String): UserAccount? = withContext(Dispatchers.IO) {
        if (emailOrPhone.contains("@")) {
            repository.getUserByEmail(emailOrPhone)
        } else {
            repository.getUserByPhone(emailOrPhone)
        }
    }

    fun updatePassword(emailOrPhone: String, newPasswordHash: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            val user = findUserAccount(emailOrPhone)
            if (user != null) {
                val updated = user.copy(passwordHash = newPasswordHash)
                withContext(Dispatchers.IO) {
                    repository.updateUser(updated)
                }
                onSuccess()
            } else {
                onFailure("Account identity lookup failed.")
            }
        }
    }
}
