#  VinylVault | Full-Stack E-Commerce Platform

**VinylVault** is a robust, full-stack e-commerce prototype designed to bridge the gap between analog music lovers and modern digital inventory management. Built with a focus on **User Experience (UX)** and **Role-Based Access Control (RBAC)**, the platform provides distinct, optimized journeys for both Customers and Administrators.

---

##  Key Product Features

###  Customer Experience (Storefront)
* **Dynamic Catalog:** A visually driven "Discover the Sound" hero banner and product grid that syncs instantly with the backend.
* **Persistent Shopping Cart:** Seamlessly add/remove records with real-time feedback.
* **Live Inventory Tracking:** The UI prevents overselling by disabling the "Add to Cart" button when stock reaches zero.

###  Admin Command Center (Management)
* **Unified Dashboard:** A central hub to toggle between inventory management and customer order tracking.
* **Full CRUD Capabilities:** Admins can list new vinyls, update pricing/stock, or remove discontinued records.
* **Smart Image Handling:** Automated placeholder generation for new listings to ensure the storefront always looks professional even without immediate asset uploads.
* **Low Stock Alerts:** Visual indicators (red badges) for items with fewer than 5 units remaining to prompt restocked.

---

##  Technical Stack

| Layer | Technology |
| :--- | :--- |
| **Frontend** | React.js (Hooks, Context, Router) |
| **Backend** | Java, Spring Boot (REST API) |
| **Database** | MySQL |
| **Security** | JWT (JSON Web Tokens) for Role-Based Auth |
| **Styling** | Custom CSS3 (Responsive Design) |

---

##  Architecture & Logic

### **Inventory Sync Algorithm**
To ensure data integrity, the system implements a bidirectional stock update:
1.  **On Cart Addition:** The backend decrements `stockQuantity` in the MySQL database to "reserve" the item.
2.  **On Cart Removal:** If a user removes an item, the backend automatically increments the `stockQuantity` back to the global pool.

### **Security Logic**
The application utilizes **JWT** to verify user identity. Upon login, the backend issues a token containing the `userRole`. React then uses this role to conditionally render the "Admin Dashboard" link in the navigation bar, ensuring restricted access to internal tools.

---

##  Getting Started

### Prerequisites
* JDK 17 or higher
* Node.js (v18+)
* MySQL Server

### Installation
1.  **Clone the Repo:**
    ```bash
    git clone [https://github.com/krithigau/VinylStoreManagement.git](https://github.com/krithigau/VinylStoreManagement.git)
    ```
2.  **Backend Setup:**
    * Update `src/main/resources/application.properties` with your MySQL credentials.
    * Run `mvn spring-boot:run` or launch from IntelliJ.
3.  **Frontend Setup:**
    ```bash
    cd frontend
    npm install
    npm start
    ```

---

## 📈 Future Roadmap
- [ ] **Payment Integration:** Integrating Razorpay/Stripe for simulated checkouts.
- [ ] **Advanced Analytics:** Chart.js integration for Admin sales trends.
- [ ] **User Wishlists:** Allowing customers to save records for later.

---

**Author:** [Krithiga U](www.linkedin.com/in/krithiga-u-477a6028a)  
*Full Stack Developer & Product Aspirant*
