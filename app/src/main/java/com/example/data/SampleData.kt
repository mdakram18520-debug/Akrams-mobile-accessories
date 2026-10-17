package com.example.data

import com.example.model.CartItem
import com.example.model.DeliveryAddress
import com.example.model.Order
import com.example.model.OrderStatus
import com.example.model.PaymentMethod
import com.example.model.Product
import com.example.model.ProductCategory
import com.example.model.SavedCard
import com.example.model.TrackingEvent

object SampleData {

  val products: List<Product> = listOf(
    // CHARGERS
    Product(
      id = "ch_1",
      name = "Akram 25W Super Fast Type-C PD Adapter",
      category = ProductCategory.CHARGERS,
      price = 499,
      originalPrice = 999,
      rating = 4.9f,
      reviewCount = 284,
      description = "Next-gen Power Delivery 3.0 adapter with smart temperature protection. Charges phone from 0 to 50% in just 25 minutes. Built for Samsung, iPhone, and Android flagships.",
      features = listOf(
        "25W Power Delivery (PD 3.0 & PPS)",
        "Over-voltage & Short-Circuit Protection",
        "Compact fire-retardant lightweight shell",
        "Type-C reversible socket"
      ),
      compatibility = "Samsung Galaxy S/A Series, iPhone 15/16, Google Pixel, OnePlus",
      badge = "Bestseller",
      colors = listOf("Glacier White", "Matte Black")
    ),
    Product(
      id = "ch_2",
      name = "Akram 65W GaN Triple Port Turbo Charger",
      category = ProductCategory.CHARGERS,
      price = 1499,
      originalPrice = 2999,
      rating = 4.8f,
      reviewCount = 142,
      description = "Gallium Nitride (GaN III) powered ultra-fast multi-device wall charger. Features 2x USB-C PD and 1x USB-A QC 3.0 ports. Power your laptop, tablet, and mobile simultaneously.",
      features = listOf(
        "65W Max Combined Output",
        "GaN III Technology for cool operation",
        "Triple port: 2 Type-C + 1 USB-A",
        "Universal international 100-240V support"
      ),
      compatibility = "MacBook Air, iPad, iPhone, Dell, Samsung, Nothing Phone",
      badge = "Flagship GaN",
      colors = listOf("Space Grey", "Pure White")
    ),
    Product(
      id = "ch_3",
      name = "Akram 20W Compact Fast Wall Plug",
      category = ProductCategory.CHARGERS,
      price = 399,
      originalPrice = 799,
      rating = 4.7f,
      reviewCount = 195,
      description = "Ultra-compact travel wall charger engineered for quick bursts of power. Perfect companion for everyday desk and travel needs.",
      features = listOf(
        "20W PD Fast Charging",
        "BIS Certified for Indian electrical standards",
        "Dynamic trickle charging for battery longevity"
      ),
      compatibility = "iPhone 11 through 16, AirPods Pro, Android devices",
      colors = listOf("Classic White", "Midnight Black")
    ),
    Product(
      id = "ch_4",
      name = "Akram MagSafe 15W Qi2 Wireless Charging Pad",
      category = ProductCategory.CHARGERS,
      price = 899,
      originalPrice = 1799,
      rating = 4.6f,
      reviewCount = 98,
      description = "Strong magnetic alignment with rapid 15W wireless induction charging. Aluminum alloy base with anti-slip rubber ring.",
      features = listOf(
        "Strong N52 Rare-Earth Magnets",
        "Foreign Object Detection (FOD)",
        "Braided 1.2m Type-C cable integrated"
      ),
      compatibility = "iPhone 12-16 MagSafe, Qi-enabled Samsung, Google Pixel",
      badge = "Magnetic",
      colors = listOf("Silver Grey", "Obsidian Black")
    ),

    // COVERS
    Product(
      id = "cov_1",
      name = "Carbon Fiber Ultra-Slim Armor Case",
      category = ProductCategory.COVERS,
      price = 299, // Starting at ₹299
      originalPrice = 599,
      rating = 4.7f,
      reviewCount = 312,
      description = "Aerospace grade textured carbon fiber back with responsive tactile buttons. Raised bezels to safeguard camera and screen.",
      features = listOf(
        "Shock-absorbing corner air cushions",
        "Anti-fingerprint matte carbon texture",
        "Precise laser cutouts for ports & speakers"
      ),
      compatibility = "Samsung S24/S25, iPhone 15/16, Redmi Note 13/14, OnePlus 12",
      badge = "₹299 Special",
      colors = listOf("Carbon Black", "Navy Blue", "Deep Red")
    ),
    Product(
      id = "cov_2",
      name = "Crystal Clear Anti-Yellowing Hybrid Case",
      category = ProductCategory.COVERS,
      price = 299, // Starting at ₹299
      originalPrice = 499,
      rating = 4.9f,
      reviewCount = 420,
      description = "Optically transparent German Bayer TPU blend with anti-UV molecular coating that resists yellowing for over 18 months.",
      features = listOf(
        "Polycarbonate hard back + flexible TPU bumper",
        "SGS Military 8ft Drop Test Certified",
        "Micro-dot matrix prevents watermarks"
      ),
      compatibility = "iPhone, Samsung, Vivo, Oppo, Realme, OnePlus",
      badge = "Top Rated",
      colors = listOf("Clear Diamond", "Smoky Clear")
    ),
    Product(
      id = "cov_3",
      name = "Liquid Silicone Soft Case with Microfiber Lining",
      category = ProductCategory.COVERS,
      price = 349,
      originalPrice = 699,
      rating = 4.8f,
      reviewCount = 188,
      description = "Silky-smooth baby-skin touch silicone with internal plush microfiber lining to prevent scratches on your device back glass.",
      features = listOf(
        "Soft liquid silicone with easy wipe-clean surface",
        "Internal suede microfiber lining",
        "Full camera island protection"
      ),
      compatibility = "iPhone 13/14/15/16 Series, Samsung S23/S24",
      colors = listOf("Midnight Blue", "Pine Green", "Lavender", "Jet Black")
    ),
    Product(
      id = "cov_4",
      name = "Vintage Leather Wallet Magnetic Flip Case",
      category = ProductCategory.COVERS,
      price = 499,
      originalPrice = 999,
      rating = 4.6f,
      reviewCount = 89,
      description = "Handcrafted PU leather folio with 3 card slots, cash pocket, and horizontal kickstand mode for hands-free video viewing.",
      features = listOf(
        "Dual magnetic clasp closure",
        "RFID blocking credit card slots",
        "Multi-angle landscape kickstand"
      ),
      compatibility = "Popular Samsung, Redmi, OnePlus, and iPhone models",
      colors = listOf("Cognac Brown", "Classic Black", "Burgundy")
    ),

    // EARPHONES
    Product(
      id = "ear_1",
      name = "Akram BassPro Extra Deep Bass Wired Earphones",
      category = ProductCategory.EARPHONES,
      price = 299, // Starting at ₹299
      originalPrice = 599,
      rating = 4.8f,
      reviewCount = 512,
      description = "10mm dynamic neodymium drivers delivering punchy bass and crystal clear vocals. Built-in HD microphone with inline volume slider.",
      features = listOf(
        "Tangle-free reinforced TPE cable (1.2m)",
        "HD mic for clear voice calling",
        "Available in 3.5mm Gold-plated or Type-C DAC"
      ),
      compatibility = "All phones with 3.5mm jack or Type-C audio port",
      badge = "Hot Seller ₹299",
      colors = listOf("Metallic Gunmetal", "Rose Gold", "Onyx Black")
    ),
    Product(
      id = "ear_2",
      name = "Akram AeroPods Pro ANC True Wireless Earbuds",
      category = ProductCategory.EARPHONES,
      price = 1499,
      originalPrice = 2999,
      rating = 4.9f,
      reviewCount = 376,
      description = "32dB Active Noise Cancellation with Transparency mode. 40 hours total playback time, Bluetooth 5.4, and IPX5 sweat resistance.",
      features = listOf(
        "32dB Hybrid Active Noise Cancellation",
        "Quad-mic ENC for studio voice calls",
        "Low 45ms gaming latency mode",
        "10 min charge = 3 hours playback"
      ),
      compatibility = "Android, iOS, Windows, Mac",
      badge = "ANC Flagship",
      colors = listOf("Glacier White", "Phantom Black")
    ),
    Product(
      id = "ear_3",
      name = "Akram FlexNeck Wireless Bluetooth Neckband",
      category = ProductCategory.EARPHONES,
      price = 699,
      originalPrice = 1399,
      rating = 4.7f,
      reviewCount = 230,
      description = "Ergonomic memory-metal silicone neckband with magnetic auto play/pause earbuds and massive 35-hour battery life.",
      features = listOf(
        "35-hour uninterrupted battery life",
        "Magnetic Hall switch power on/off",
        "Deep Bass Boost 12mm drivers",
        "Fast Type-C charging"
      ),
      compatibility = "All Bluetooth enabled smartphones and tablets",
      colors = listOf("Neon Teal", "Sporty Red", "Stealth Black")
    ),
    Product(
      id = "ear_4",
      name = "Akram Studio Hi-Fi Type-C In-Ear Monitors",
      category = ProductCategory.EARPHONES,
      price = 799,
      originalPrice = 1499,
      rating = 4.8f,
      reviewCount = 114,
      description = "Audiophile grade Type-C earphones featuring an integrated 24-bit/96kHz digital DAC chip for lossless audio streaming.",
      features = listOf(
        "Integrated 24-bit Lossless DAC chipset",
        "Zinc-alloy ergonomic acoustic chambers",
        "Braided silver-plated oxygen-free copper wire"
      ),
      compatibility = "Type-C phones including Samsung S/Z series, iPad, iPhone 15/16",
      colors = listOf("Titanium Silver", "Matte Black")
    ),

    // SCREEN PROTECTORS
    Product(
      id = "scr_1",
      name = "9H Edge-to-Edge Tempered Glass (Pack of 2)",
      category = ProductCategory.SCREEN_PROTECTORS,
      price = 299, // Starting at ₹299
      originalPrice = 599,
      rating = 4.8f,
      reviewCount = 388,
      description = "Japanese Asahi 9H hardness tempered glass with 2.5D curved edges. Oleophobic plasma coating prevents oil and fingerprints.",
      features = listOf(
        "2-Pack combo value bundle",
        "99.9% optical transparency",
        "Bubble-free self-adhesive layer"
      ),
      compatibility = "Available for all iPhone, Samsung, and OnePlus models",
      badge = "2-in-1 Combo",
      colors = listOf("Full Glue Clear")
    ),
    Product(
      id = "scr_2",
      name = "Privacy 28° Anti-Spy Tempered Glass",
      category = ProductCategory.SCREEN_PROTECTORS,
      price = 399,
      originalPrice = 799,
      rating = 4.7f,
      reviewCount = 205,
      description = "Protects personal messages and PINs from prying eyes. Only visible directly in front; screen blacks out at 28° side angles.",
      features = listOf(
        "28-degree micro-louver privacy filter",
        "Explosion-proof shatter resistance",
        "Full coverage with alignment frame"
      ),
      compatibility = "iPhone, Samsung Flagships, OnePlus",
      colors = listOf("Privacy Tint")
    ),

    // CABLES & ADAPTERS
    Product(
      id = "cbl_1",
      name = "Braided 60W Type-C to Type-C Cable (1.5m)",
      category = ProductCategory.CABLES,
      price = 299, // Starting at ₹299
      originalPrice = 499,
      rating = 4.9f,
      reviewCount = 478,
      description = "Heavy-duty ballistic nylon braided cable rated for 25,000+ bends. Supports 60W fast charging and 480Mbps high speed data sync.",
      features = listOf(
        "60W 3A Power Delivery",
        "Reinforced zinc alloy connector collars",
        "Velcro cable organizer tie included"
      ),
      compatibility = "Android, iPad, MacBook, Nintendo Switch",
      badge = "Most Durable",
      colors = listOf("Space Grey", "Crimson Red")
    ),
    Product(
      id = "cbl_2",
      name = "Akram 3-in-1 Multi Fast Charging Cable",
      category = ProductCategory.CABLES,
      price = 399,
      originalPrice = 699,
      rating = 4.7f,
      reviewCount = 162,
      description = "One cable to charge all your devices: Type-C, Lightning, and Micro-USB all branching from a single braided cable.",
      features = listOf(
        "Type-C + Lightning + Micro-USB outputs",
        "Simultaneous 3-device charging support",
        "1.2m tangle-proof braided cord"
      ),
      compatibility = "Universal compatibility with all mobile devices",
      colors = listOf("Royal Blue", "Pitch Black")
    ),

    // POWER BANKS
    Product(
      id = "pw_1",
      name = "Akram PowerVault 10000mAh 22.5W Fast Pocket Power Bank",
      category = ProductCategory.POWER_BANKS,
      price = 999,
      originalPrice = 1899,
      rating = 4.8f,
      reviewCount = 210,
      description = "Credit-card footprint pocket power bank with 22.5W Super Charge. LED digital battery percentage display and dual input.",
      features = listOf(
        "10000mAh A+ Grade Lithium Polymer battery",
        "22.5W QC/PD Fast Output",
        "LED numeric battery display",
        "Flight approved cabin carry-on"
      ),
      compatibility = "All smartphones, smartwatches, and wireless earbuds",
      badge = "Travel Must-Have",
      colors = listOf("Matte Navy", "Glacier White")
    ),
    Product(
      id = "pw_2",
      name = "Akram PowerVault Ultra 20000mAh 65W Laptop & Phone Power Bank",
      category = ProductCategory.POWER_BANKS,
      price = 2199,
      originalPrice = 3999,
      rating = 4.9f,
      reviewCount = 135,
      description = "Heavyweight 65W power bank capable of fully charging Type-C laptops and charging phones up to 5 times. Pass-through charging enabled.",
      features = listOf(
        "Massive 20000mAh capacity",
        "65W High-Output Type-C port",
        "Simultaneous charging of 3 devices",
        "Smart temperature and battery cell safeguard"
      ),
      compatibility = "Laptops (MacBook/ThinkPad/Dell), Tablets, and Smartphones",
      colors = listOf("Space Titanium", "Matte Carbon")
    )
  )

  val sampleAddress = DeliveryAddress(
    id = "addr_1",
    fullName = "Akram Khan",
    phoneNumber = "+91 97052 67667",
    addressLine = "Flat 402, Royal Palms Residency, 100 Ft Road",
    area = "Indiranagar",
    city = "Bengaluru",
    state = "Karnataka",
    pincode = "560038",
    tag = "Home"
  )

  val sampleSavedCards: List<SavedCard> = listOf(
    SavedCard(
      id = "card_1",
      cardNumberMasked = "•••• •••• •••• 4242",
      cardHolderName = "AKRAM KHAN",
      expiryDate = "09/28",
      cardType = "Visa",
      bankName = "HDFC Bank",
      isDefault = true
    ),
    SavedCard(
      id = "card_2",
      cardNumberMasked = "•••• •••• •••• 8104",
      cardHolderName = "AKRAM KHAN",
      expiryDate = "11/27",
      cardType = "RuPay",
      bankName = "ICICI Bank",
      isDefault = false
    ),
    SavedCard(
      id = "card_3",
      cardNumberMasked = "•••• •••• •••• 5531",
      cardHolderName = "AKRAM KHAN",
      expiryDate = "04/26",
      cardType = "Mastercard",
      bankName = "SBI Card",
      isDefault = false
    )
  )

  val sampleOrders: List<Order> = listOf(
    Order(
      id = "ord_101",
      orderNumber = "AKM-94812",
      items = listOf(
        CartItem(product = products[9], quantity = 1, selectedColor = "Glacier White") // AeroPods Pro ₹1499
      ),
      orderDate = "Today, 10:15 AM",
      expectedDelivery = "Tomorrow, by 8:00 PM",
      deliveryAddress = sampleAddress,
      paymentMethod = PaymentMethod.PREPAID_UPI,
      subtotal = 1499,
      deliveryFee = 0, // FREE on prepaid!
      discount = 0,
      totalAmount = 1499,
      status = OrderStatus.OUT_FOR_DELIVERY,
      trackingNumber = "AKM-EXP-8891244",
      courierName = "Akram Express Priority",
      trackingEvents = listOf(
        TrackingEvent(
          title = "Out for Delivery",
          location = "Indiranagar Hub, Bengaluru",
          timestamp = "Today, 02:45 PM",
          isCompleted = true,
          isCurrent = true
        ),
        TrackingEvent(
          title = "Arrived at Local Facility",
          location = "Bengaluru Central Sorting Hub",
          timestamp = "Today, 08:30 AM",
          isCompleted = true,
          isCurrent = false
        ),
        TrackingEvent(
          title = "Dispatched from Akram Warehouse",
          location = "Akram Mobile Hub, MG Road Hub",
          timestamp = "Yesterday, 07:15 PM",
          isCompleted = true,
          isCurrent = false
        ),
        TrackingEvent(
          title = "Packed & Quality Inspected",
          location = "Main Fulfillment Center",
          timestamp = "Yesterday, 03:00 PM",
          isCompleted = true,
          isCurrent = false
        ),
        TrackingEvent(
          title = "Order Confirmed (Prepaid UPI - ₹0 Free Delivery)",
          location = "Akram Mobile Hub Online",
          timestamp = "Yesterday, 10:15 AM",
          isCompleted = true,
          isCurrent = false
        )
      )
    ),
    Order(
      id = "ord_102",
      orderNumber = "AKM-81934",
      items = listOf(
        CartItem(product = products[4], quantity = 1, selectedColor = "Carbon Black"), // Cover ₹299
        CartItem(product = products[12], quantity = 1, selectedColor = "Space Grey") // Cable ₹299
      ),
      orderDate = "12 Oct 2026",
      expectedDelivery = "Delivered on 14 Oct",
      deliveryAddress = sampleAddress,
      paymentMethod = PaymentMethod.CASH_ON_DELIVERY,
      subtotal = 598,
      deliveryFee = 50, // ₹50 delivery charge on COD!
      discount = 0,
      totalAmount = 648,
      status = OrderStatus.DELIVERED,
      trackingNumber = "AKM-COD-7712390",
      courierName = "BlueDart Express",
      trackingEvents = listOf(
        TrackingEvent(
          title = "Delivered to Customer",
          location = "Bengaluru - 560038",
          timestamp = "14 Oct 2026, 04:30 PM",
          isCompleted = true,
          isCurrent = true
        ),
        TrackingEvent(
          title = "Cash Collected ₹648 (Items ₹598 + ₹50 COD Fee)",
          location = "Delivery Agent: Ramesh V.",
          timestamp = "14 Oct 2026, 04:28 PM",
          isCompleted = true,
          isCurrent = false
        ),
        TrackingEvent(
          title = "Out for Delivery",
          location = "Indiranagar Facility",
          timestamp = "14 Oct 2026, 10:00 AM",
          isCompleted = true,
          isCurrent = false
        ),
        TrackingEvent(
          title = "Order Placed (Cash on Delivery)",
          location = "Akram Mobile Hub Online",
          timestamp = "12 Oct 2026, 02:15 PM",
          isCompleted = true,
          isCurrent = false
        )
      )
    )
  )

  data class CustomerCareInfo(
    val phone: String = "+91 97052 67667",
    val whatsapp: String = "+91 97052 67667",
    val email: String = "care@akramsmobilehub.in",
    val storeAddress: String = "Shop #12, Mobile Market Plaza, 100 Ft Road, Indiranagar, Bengaluru - 560038",
    val operatingHours: String = "Monday to Sunday: 9:00 AM – 9:00 PM IST",
    val deliveryPolicy: String = "FREE Delivery on all Prepaid Orders across India. For Cash on Delivery (COD), a flat ₹50 convenience & courier charge is applicable."
  )

  val customerCare = CustomerCareInfo()

  val faqs = listOf(
    Pair(
      "How do I get Free Delivery on Akram's Mobile Hub?",
      "All prepaid orders (paid via UPI, Credit/Debit Cards, or Net Banking) automatically receive 100% FREE delivery with zero handling fees! Cash on Delivery (COD) orders carry a nominal ₹50 fee to cover reverse cash transit logistics."
    ),
    Pair(
      "What is the starting price for accessories?",
      "Our products start at just ₹299 in Indian Rupees! You will find top-selling protective covers, high-grade tempered glass, heavy-duty 60W cables, and deep-bass wired earphones starting at ₹299."
    ),
    Pair(
      "How can I track my active order?",
      "Go to the 'Account' tab and select 'Order Tracking'. You'll see real-time updates from packing at our hub to out-for-delivery with live status milestones and courier tracking numbers."
    ),
    Pair(
      "Is there a warranty on chargers and earphones?",
      "Yes! All Akram branded adapters, cables, power banks, and audio gear come with a 1-Year Official Replacement Warranty. Screen protectors and cases come with a 7-day fit-and-finish guarantee."
    ),
    Pair(
      "How do I manage or remove my saved cards?",
      "In the 'Account' tab under 'Card Management', you can view your stored cards, add a new Visa, Mastercard, or RuPay card, set a default card for one-click checkout, or securely remove an existing card."
    )
  )
}
