package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val category: String, // "Clothes" or "Accessories"
    val price: Double,
    val originalPrice: Double,
    val imageUrl: String,
    val stock: Int,
    val rating: Float,
    val reviewsCount: Int,
    val isFeatured: Boolean = false,
    val isTrending: Boolean = false,
    val isNewArrival: Boolean = false,
    val sellerId: Int = 1,
    val brand: String = "Shivam Premium"
)

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val quantity: Int,
    val chosenSize: String = "M",
    val chosenColor: String = "Royal Blue"
)

@Entity(tableName = "wishlist_items")
data class WishlistItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int
)

@Entity(tableName = "orders")
data class OrderRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val orderNumber: String,
    val customerName: String,
    val customerAddress: String,
    val customerPhone: String,
    val date: Long = System.currentTimeMillis(),
    val status: String, // "Pending", "Confirmed", "Shipped", "Delivered", "Canceled"
    val paymentMethod: String, // "UPI" or "COD"
    val paymentStatus: String, // "Paid" or "Pending"
    val totalAmount: Double,
    val productIdsJson: String // Serialized list of purchased product info (e.g. "[{id: 1, quantity: 2, title: 'Shirt'}]")
)

@Entity(tableName = "reviews")
data class ReviewRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val userName: String,
    val userAvatar: String = "",
    val rating: Float,
    val comment: String,
    val date: Long = System.currentTimeMillis()
)

@Entity(tableName = "sellers")
data class SellerProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val businessName: String,
    val contactName: String,
    val email: String,
    val phone: String,
    val city: String,
    val earnings: Double = 0.0,
    val registeredDate: Long = System.currentTimeMillis(),
    val isVerified: Boolean = true
)

@Entity(tableName = "users")
data class UserAccount(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val phone: String,
    val passwordHash: String, // Plain for prototype authentication stability
    val role: String, // "Customer", "Seller", "Admin"
    val address: String = "Varanasi, Uttar Pradesh, India",
    val profilePhoto: String = ""
)
