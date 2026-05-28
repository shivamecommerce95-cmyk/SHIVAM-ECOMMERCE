package com.example.data.repository

import com.example.data.database.AppDatabase
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

class EcommerceRepository(private val db: AppDatabase) {
    val productDao = db.productDao()
    val cartDao = db.cartDao()
    val wishlistDao = db.wishlistDao()
    val orderDao = db.orderDao()
    val reviewDao = db.reviewDao()
    val sellerDao = db.sellerDao()
    val userDao = db.userDao()

    // --- Products ---
    val allProducts: Flow<List<Product>> = productDao.getAllProducts()
    val featuredProducts: Flow<List<Product>> = productDao.getFeaturedProducts()
    val trendingProducts: Flow<List<Product>> = productDao.getTrendingProducts()
    val newArrivals: Flow<List<Product>> = productDao.getNewArrivals()

    fun getProductsByCategory(category: String): Flow<List<Product>> = productDao.getProductsByCategory(category)
    suspend fun getProductById(productId: Int): Product? = productDao.getProductById(productId)
    suspend fun insertProduct(product: Product): Long = productDao.insertProduct(product)
    suspend fun updateProduct(product: Product) = productDao.updateProduct(product)
    suspend fun deleteProduct(product: Product) = productDao.deleteProduct(product)
    suspend fun deleteProductById(productId: Int) = productDao.deleteProductById(productId)

    // --- Cart ---
    val cartItems: Flow<List<CartItem>> = cartDao.getCartItems()
    suspend fun insertCartItem(cartItem: CartItem) = cartDao.insertCartItem(cartItem)
    suspend fun updateCartItem(cartItem: CartItem) = cartDao.updateCartItem(cartItem)
    suspend fun deleteCartItem(cartItem: CartItem) = cartDao.deleteCartItem(cartItem)
    suspend fun clearCart() = cartDao.clearCart()

    // --- Wishlist ---
    val wishlistItems: Flow<List<WishlistItem>> = wishlistDao.getWishlistItems()
    fun isProductInWishlist(productId: Int): Flow<Boolean> = wishlistDao.isProductInWishlist(productId)
    suspend fun addToWishlist(productId: Int) = wishlistDao.insertWishlist(WishlistItem(productId = productId))
    suspend fun removeFromWishlist(productId: Int) = wishlistDao.deleteWishlistByProductId(productId)

    // --- Orders ---
    val allOrders: Flow<List<OrderRecord>> = orderDao.getAllOrders()
    fun getOrdersForCustomer(phone: String): Flow<List<OrderRecord>> = orderDao.getOrdersByCustomerPhone(phone)
    suspend fun insertOrder(order: OrderRecord): Long = orderDao.insertOrder(order)
    suspend fun updateOrderStatus(orderId: Int, status: String) = orderDao.updateOrderStatus(orderId, status)

    // --- Reviews ---
    fun getReviewsForProduct(productId: Int): Flow<List<ReviewRecord>> = reviewDao.getReviewsForProduct(productId)
    suspend fun insertReview(review: ReviewRecord): Long = reviewDao.insertReview(review)

    // --- Sellers ---
    val allSellers: Flow<List<SellerProfile>> = sellerDao.getAllSellers()
    suspend fun insertSeller(seller: SellerProfile): Long = sellerDao.insertSeller(seller)
    suspend fun updateSellerEarnings(sellerId: Int, amount: Double) = sellerDao.updateSellerEarnings(sellerId, amount)

    // --- Users ---
    suspend fun getUserByEmail(email: String): UserAccount? = userDao.getUserByEmail(email)
    suspend fun getUserByPhone(phone: String): UserAccount? = userDao.getUserByPhone(phone)
    suspend fun insertUser(user: UserAccount): Long = userDao.insertUser(user)
    suspend fun updateUser(user: UserAccount) = userDao.updateUser(user)

    suspend fun checkAndPrepopulate() = kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
        val admin = userDao.getUserByEmail("admin@shivam.com")
        if (admin == null) {
            prepopulateEverything()
        }
    }

    private suspend fun prepopulateEverything() {
        // 1. Initial Sellers
        val s1 = SellerProfile(
            id = 1,
            businessName = "Shivam Elite Apparels",
            contactName = "Shivam Vishwakarma",
            email = "shivam.apparel@example.com",
            phone = "9876543210",
            city = "Varanasi",
            earnings = 15420.0,
            isVerified = true
        )
        val s2 = SellerProfile(
            id = 2,
            businessName = "Titanium Tech Accessories",
            contactName = "Aarav Sharma",
            email = "titanium.tech@example.com",
            phone = "8765432109",
            city = "Bengaluru",
            earnings = 8900.0,
            isVerified = true
        )
        sellerDao.insertSeller(s1)
        sellerDao.insertSeller(s2)

        // 2. Initial Users (Admin, Seller, Customer)
        val uAdmin = UserAccount(
            name = "Shivam Admin",
            email = "admin@shivam.com",
            phone = "9999999999",
            passwordHash = "admin123",
            role = "Admin",
            address = "Headquarters, Shivam Luxury tower, Varanasi"
        )
        val uSeller = UserAccount(
            name = "Shivam Seller",
            email = "seller@shivam.com",
            phone = "8888888888",
            passwordHash = "seller123",
            role = "Seller",
            address = "Apparel Hub, Varanasi"
        )
        val uCustomer = UserAccount(
            name = "Shivam Customer",
            email = "customer@shivam.com",
            phone = "7777777777",
            passwordHash = "customer123",
            role = "Customer",
            address = "Assi Ghat, Varanasi, Uttar Pradesh"
        )
        userDao.insertUser(uAdmin)
        userDao.insertUser(uSeller)
        userDao.insertUser(uCustomer)

        // 3. Initial Products (Clothes and Accessories)
        val p1 = Product(
            title = "Royal Zari Silk Kurti",
            description = "Experience premium Banarsi handloom legacy. Tailored from original Mulberry silk fibers, styled with pure gold zari threads and a majestic high neck pattern. Pure luxury feel, perfect for royal family banquets and festive situations.",
            category = "Clothes",
            price = 2499.0,
            originalPrice = 4999.0,
            imageUrl = "https://images.unsplash.com/photo-1610030469983-98e550d6193c?auto=format&fit=crop&w=600&q=80",
            stock = 15,
            rating = 4.8f,
            reviewsCount = 124,
            isFeatured = true,
            isTrending = true,
            isNewArrival = false,
            sellerId = 1,
            brand = "Shivam Luxury"
        )
        val p2 = Product(
            title = "Velvet Bandhgala Sherwani",
            description = "Artisanal deep royal blue velvet bandhgala jacket with custom metal shield buttons. Impeccable shoulder-pad alignment and breathable inner satin lining. Includes slim cotton-stretch breeches.",
            category = "Clothes",
            price = 8999.0,
            originalPrice = 14999.0,
            imageUrl = "https://images.unsplash.com/photo-1593030761757-71fae45fa0e7?auto=format&fit=crop&w=600&q=80",
            stock = 8,
            rating = 4.9f,
            reviewsCount = 42,
            isFeatured = true,
            isTrending = false,
            isNewArrival = true,
            sellerId = 1,
            brand = "Shivam Couture"
        )
        val p3 = Product(
            title = "Italian Premium Linen Shirt",
            description = "Woven from extra-fine certified organic Italian linen fibers. Naturally moisture-wicking, highly breathable design. Features a tailored formal spread collar and premium pearl-finish shell buttons.",
            category = "Clothes",
            price = 1899.0,
            originalPrice = 2999.0,
            imageUrl = "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?auto=format&fit=crop&w=600&q=80",
            stock = 30,
            rating = 4.5f,
            reviewsCount = 88,
            isFeatured = false,
            isTrending = true,
            isNewArrival = true,
            sellerId = 1,
            brand = "Shivam Everyday"
        )
        val p4 = Product(
            title = "Aramid Carbon Fiber Sleek Case",
            description = "Genuine heavy-duty military aramid monofilaments, only 0.6mm thin. Built-in ultra-slim magnetic ring coordinates perfectly with Qi2 chargers. Dynamic corner bumpers ensure military-grade drop dissipation.",
            category = "Mobile Accessories",
            price = 1299.0,
            originalPrice = 2499.0,
            imageUrl = "https://images.unsplash.com/photo-1603302576837-37561b2e2302?auto=format&fit=crop&w=600&q=80",
            stock = 25,
            rating = 4.7f,
            reviewsCount = 210,
            isFeatured = true,
            isTrending = true,
            isNewArrival = false,
            sellerId = 2,
            brand = "Titanium Case"
        )
        val p5 = Product(
            title = "GaN 120W Compact Charger",
            description = "Revolutionary Gallium Nitride dual power chips. Instantly routes up to 100W on a single Type-C run, charging laptops and smartphones simultaneously with advanced temperature sensing technology.",
            category = "Mobile Accessories",
            price = 1999.0,
            originalPrice = 3499.0,
            imageUrl = "https://images.unsplash.com/photo-1583863788434-e58a36330cf0?auto=format&fit=crop&w=600&q=80",
            stock = 40,
            rating = 4.6f,
            reviewsCount = 145,
            isFeatured = false,
            isTrending = true,
            isNewArrival = true,
            sellerId = 2,
            brand = "Titanium Power"
        )
        val p6 = Product(
            title = "Aura Dual Wireless Pad",
            description = "Sleek slate-blue brushed premium aerospace alloy chassis. Charges smartphone and wireless earbuds at full speed together. Soft breathing LED indicator shifts automatically.",
            category = "Mobile Accessories",
            price = 2499.0,
            originalPrice = 3999.0,
            imageUrl = "https://images.unsplash.com/photo-1622445262465-2481c4574875?auto=format&fit=crop&w=600&q=80",
            stock = 12,
            rating = 4.4f,
            reviewsCount = 56,
            isFeatured = true,
            isTrending = false,
            isNewArrival = false,
            sellerId = 2,
            brand = "Titanium Charge"
        )

        productDao.insertProduct(p1)
        productDao.insertProduct(p2)
        productDao.insertProduct(p3)
        productDao.insertProduct(p4)
        productDao.insertProduct(p5)
        productDao.insertProduct(p6)

        // 4. Initial Review
        val r1 = ReviewRecord(
            productId = 1,
            userName = "Amit Mishra",
            rating = 5.0f,
            comment = "Simply breathtaking luxury quality. The gold zari has a high-end matte finish that looks extremely premium. Highly recommended!",
            date = System.currentTimeMillis() - 86400000
        )
        val r2 = ReviewRecord(
            productId = 4,
            userName = "Sneha Patel",
            rating = 4.5f,
            comment = "Incredibly slim and fits like a glove! Actually feels premium carbon fiber. MagSafe magnets are ultra strong.",
            date = System.currentTimeMillis() - 43200000
        )
        reviewDao.insertReview(r1)
        reviewDao.insertReview(r2)
    }
}
