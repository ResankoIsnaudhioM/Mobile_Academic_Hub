# Panduan Integrasi Firebase dan API Eksternal untuk Mobile Academic Hub

Dokumen ini menyediakan panduan langkah demi langkah untuk mengintegrasikan Firebase dan API eksternal (Edlink, Google Classroom) ke dalam proyek Mobile Academic Hub Anda. 

## 1. Integrasi Firebase

Firebase adalah platform pengembangan aplikasi dari Google yang menyediakan berbagai layanan backend, termasuk autentikasi, database real-time (Firestore), dan notifikasi (Cloud Messaging). Proyek Anda telah dikonfigurasi dengan dependensi Firebase dasar. Berikut adalah langkah-langkah untuk menyelesaikan integrasi Firebase:

### 1.1. Menambahkan Proyek ke Firebase Console

1.  Buka [Firebase Console](https://console.firebase.google.com/).
2.  Klik "Add project" dan ikuti langkah-langkah untuk membuat proyek baru.
3.  Setelah proyek dibuat, klik ikon Android untuk menambahkan aplikasi Android ke proyek Firebase Anda.
4.  Masukkan `Package name` aplikasi Anda (yaitu, `com.example.mobileacademichub`).
5.  Opsional: Masukkan `App nickname` dan `SHA-1 signing certificate fingerprint` (diperlukan untuk Google Sign-In atau Phone Authentication).
6.  Unduh file `google-services.json`.

### 1.2. Menambahkan `google-services.json` ke Proyek Android

1.  Salin file `google-services.json` yang telah Anda unduh ke direktori `app/` di proyek Android Studio Anda.
2.  Pastikan struktur proyek Anda terlihat seperti ini:
    ```
    mobile_academic_hub/
    ├── app/
    │   ├── google-services.json
    │   └── src/
    │       └── main/
    │           └── ...
    ├── build.gradle.kts
    └── settings.gradle.kts
    ```

### 1.3. Menginisialisasi Firebase di Aplikasi Anda

Firebase akan otomatis diinisialisasi saat aplikasi Anda dimulai jika `google-services.json` sudah ada. Anda dapat mulai menggunakan layanan Firebase seperti Authentication, Firestore, dan Cloud Messaging.

#### Contoh Penggunaan Firebase Authentication (Placeholder)

Untuk mengimplementasikan autentikasi pengguna (misalnya, dengan email dan password), Anda dapat menggunakan Firebase Authentication. Berikut adalah contoh dasar:

```kotlin
// Dalam Activity atau Composable Anda
import com.google.firebase.auth.FirebaseAuth

// Inisialisasi Firebase Auth
val auth = FirebaseAuth.getInstance()

// Contoh pendaftaran pengguna
auth.createUserWithEmailAndPassword(email, password)
    .addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
            // Pendaftaran berhasil
            val user = auth.currentUser
            // Lanjutkan ke layar utama
        } else {
            // Pendaftaran gagal
            // Tampilkan pesan error
        }
    }

// Contoh login pengguna
auth.signInWithEmailAndPassword(email, password)
    .addOnCompleteListener(this) { task ->
        if (task.isSuccessful) {
            // Login berhasil
            val user = auth.currentUser
            // Lanjutkan ke layar utama
        } else {
            // Login gagal
            // Tampilkan pesan error
        }
    }
```

#### Contoh Penggunaan Firestore (Placeholder)

Untuk menyimpan dan mengambil data (misalnya, jadwal atau tugas), Anda dapat menggunakan Cloud Firestore. Berikut adalah contoh dasar:

```kotlin
// Dalam ViewModel atau Repository Anda
import com.google.firebase.firestore.FirebaseFirestore

// Inisialisasi Firestore
val db = FirebaseFirestore.getInstance()

// Contoh menambahkan data
val scheduleItem = hashMapOf(
    "courseName" to "Pemrograman Mobile",
    "lecturer" to "Dr. Budi",
    "startTime" to "08:00"
    // ... data lainnya
)

db.collection("schedules")
    .add(scheduleItem)
    .addOnSuccessListener { documentReference ->
        // Data berhasil ditambahkan
    }
    .addOnFailureListener { e ->
        // Gagal menambahkan data
    }

// Contoh mengambil data
db.collection("schedules")
    .get()
    .addOnSuccessListener { result ->
        for (document in result) {
            // Proses setiap dokumen
            val courseName = document.getString("courseName")
            // ...
        }
    }
    .addOnFailureListener { exception ->
        // Gagal mengambil data
    }
```

## 2. Integrasi API Edlink

Edlink menyediakan API untuk mengintegrasikan data akademik dari berbagai Sistem Informasi Akademik (SIA) atau Learning Management System (LMS). Untuk mengintegrasikan Edlink, Anda perlu mendaftar sebagai pengembang dan mendapatkan kredensial API (Client ID, Client Secret).

### 2.1. Mendapatkan Kredensial API Edlink

1.  Kunjungi [situs web Edlink](https://ed.link/) dan cari bagian untuk pengembang atau integrasi.
2.  Daftar sebagai pengembang dan ikuti proses untuk mendapatkan kredensial API Anda.
3.  Anda mungkin perlu menghubungi tim Edlink untuk akses API penuh.

### 2.2. Menggunakan API Edlink

Setelah Anda memiliki kredensial, Anda dapat menggunakan library HTTP client (misalnya, Retrofit dengan OkHttp) di Kotlin untuk melakukan panggilan ke API Edlink. Edlink API umumnya berbasis RESTful [3].

**Contoh Konsep Panggilan API Edlink (menggunakan Retrofit):
**

```kotlin
// Interface layanan Retrofit
interface EdlinkService {
    @GET("v2/schedules")
    suspend fun getSchedules(@Header("Authorization") token: String): List<ScheduleItemDto>

    // Tambahkan endpoint lain sesuai kebutuhan (misalnya untuk tugas, kelas, dll.)
}

// Implementasi Retrofit
val retrofit = Retrofit.Builder()
    .baseUrl("https://api.ed.link/") // Ganti dengan base URL API Edlink yang benar
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val service = retrofit.create(EdlinkService::class.java)

// Contoh penggunaan di ViewModel atau Repository
suspend fun fetchSchedulesFromEdlink(accessToken: String): List<ScheduleItem> {
    val schedulesDto = service.getSchedules("Bearer $accessToken")
    // Konversi ScheduleItemDto ke ScheduleItem model aplikasi Anda
    return schedulesDto.map { it.toScheduleItem() }
}
```

**Penting:**
*   **Otentikasi**: Edlink API kemungkinan besar memerlukan token otentikasi (misalnya, OAuth 2.0). Anda perlu mengimplementasikan alur otentikasi untuk mendapatkan dan me-refresh token ini.
*   **Penanganan Data**: Data yang diterima dari Edlink API perlu diproses dan disesuaikan dengan model data aplikasi Anda.
*   **Error Handling**: Implementasikan penanganan error yang robust untuk kegagalan API.

## 3. Integrasi API Google Classroom

Google Classroom API memungkinkan Anda untuk berinteraksi dengan data Google Classroom, seperti daftar kelas, tugas, dan pengajuan tugas. Anda memerlukan akun Google Developer dan mengaktifkan Google Classroom API.

### 3.1. Mengaktifkan Google Classroom API

1.  Buka [Google Cloud Console](https://console.cloud.google.com/).
2.  Pilih proyek Anda atau buat proyek baru.
3.  Navigasi ke "APIs & Services" > "Library".
4.  Cari "Google Classroom API" dan aktifkan.
5.  Navigasi ke "APIs & Services" > "Credentials" untuk membuat kredensial OAuth 2.0 Client ID (tipe "Android") untuk aplikasi Anda. Pastikan untuk menambahkan SHA-1 fingerprint aplikasi Anda.

### 3.2. Menggunakan Google Classroom API

Integrasi Google Classroom API biasanya melibatkan penggunaan Google Sign-In untuk otentikasi pengguna dan kemudian menggunakan token akses yang diperoleh untuk memanggil API. Google menyediakan [Google API Client Library for Java](https://developers.google.com/api-client-library/java/apis/classroom/v1) yang dapat digunakan di Kotlin.

**Contoh Konsep Panggilan API Google Classroom (menggunakan Google API Client Library):
**

```kotlin
// Inisialisasi GoogleSignInClient
val googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)

// Setelah pengguna berhasil login dengan Google Sign-In dan Anda mendapatkan GoogleSignInAccount
val account = GoogleSignIn.getLastSignedInAccount(this)
val credential = GoogleAccountCredential.usingOAuth2(
    this,
    Collections.singleton(ClassroomScopes.CLASSROOM_COURSES_READONLY) // Ganti dengan scope yang diperlukan
)
credential.selectedAccount = account?.account

val classroomService = Classroom.Builder(
    AndroidHttp.newCompatibleTransport(),
    GsonFactory(),
    credential
)
    .setApplicationName("Mobile Academic Hub")
    .build()

// Contoh mengambil daftar kursus
suspend fun fetchCoursesFromClassroom(): List<Course> {
    return withContext(Dispatchers.IO) {
        classroomService.courses().list().execute().courses
    }
}

// Contoh mengambil daftar tugas
suspend fun fetchCourseWork(courseId: String): List<CourseWork> {
    return withContext(Dispatchers.IO) {
        classroomService.courses().courseWork().list(courseId).execute().courseWork
    }
}
```

**Penting:**
*   **Scopes**: Pastikan Anda meminta scope OAuth yang tepat dari pengguna untuk mengakses data Classroom yang diperlukan (misalnya, `ClassroomScopes.CLASSROOM_COURSES_READONLY`, `ClassroomScopes.CLASSROOM_COURSEWORK_STUDENTS`).
*   **Otentikasi**: Alur otentikasi dengan Google Sign-In sangat penting untuk mendapatkan izin pengguna.
*   **Penanganan Data**: Sama seperti Edlink, data dari Google Classroom API perlu diproses dan disesuaikan dengan model data aplikasi Anda.

## 4. Struktur Repository untuk Integrasi Data

Disarankan untuk membuat lapisan Repository yang akan mengabstraksi sumber data (Firebase, Edlink API, Google Classroom API) dari ViewModel Anda. Ini akan membuat kode lebih bersih, mudah diuji, dan fleksibel jika Anda perlu mengubah atau menambahkan sumber data di masa mendatang.

```kotlin
// Contoh struktur Repository
interface ScheduleRepository {
    suspend fun getSchedules(): List<ScheduleItem>
}

class ScheduleRepositoryImpl(private val firestoreDataSource: FirestoreDataSource, private val edlinkDataSource: EdlinkDataSource) : ScheduleRepository {
    override suspend fun getSchedules(): List<ScheduleItem> {
        val firebaseSchedules = firestoreDataSource.getSchedules()
        val edlinkSchedules = edlinkDataSource.getSchedules()
        // Gabungkan dan deduplikasi jadwal dari kedua sumber
        return (firebaseSchedules + edlinkSchedules).distinctBy { it.id }
    }
}

// DataSource untuk Firebase
class FirestoreDataSource { /* ... */ }

// DataSource untuk Edlink API
class EdlinkDataSource { /* ... */ }
```

## 5. Notifikasi Real-time dengan Firebase Cloud Messaging (FCM)

Firebase Cloud Messaging (FCM) akan digunakan untuk notifikasi pengingat jadwal dan deadline tugas. Anda perlu mengimplementasikan `FirebaseMessagingService` untuk menerima dan memproses notifikasi.

### 5.1. Menambahkan `FirebaseMessagingService`

Buat kelas baru yang meng-extend `FirebaseMessagingService`:

```kotlin
package com.example.mobileacademichub.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.example.mobileacademichub.R // Pastikan Anda memiliki file R.java/R.kt

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        remoteMessage.notification?.let {
            sendNotification(it.title, it.body)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Kirim token ke server backend Anda jika diperlukan
        // Log.d(TAG, "Refreshed token: $token")
    }

    private fun sendNotification(title: String?, body: String?) {
        val channelId = "academic_hub_channel"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification) // Ganti dengan ikon notifikasi Anda
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Academic Hub Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}
```

### 5.2. Mendeklarasikan Service di `AndroidManifest.xml`

Tambahkan deklarasi service ini di dalam tag `<application>` di `AndroidManifest.xml` Anda:

```xml
<service
    android:name=".service.MyFirebaseMessagingService"
    android:exported="false">
    <intent-filter>
        <action android:name="com.google.firebase.MESSAGING_EVENT" />
    </intent-filter>
</service>
```

**Catatan:** Untuk mengirim notifikasi, Anda memerlukan backend yang dapat memicu pesan FCM. Ini bisa berupa Cloud Functions for Firebase, server Anda sendiri, atau melalui Firebase Console secara manual.

## Referensi

[1] Firebase. (n.d.). *Add Firebase to your Android project*. Retrieved from [https://firebase.google.com/docs/android/setup](https://firebase.google.com/docs/android/setup)
[2] Firebase. (n.d.). *Get started with Firebase Authentication*. Retrieved from [https://firebase.google.com/docs/auth/android/start](https://firebase.google.com/docs/auth/android/start)
[3] Edlink. (n.d.). *Edlink API Reference*. Retrieved from [https://ed.link/docs/api](https://ed.link/docs/api)
[4] Google Developers. (n.d.). *Google Classroom API*. Retrieved from [https://developers.google.com/classroom/guides/overview](https://developers.google.com/classroom/guides/overview)
[5] Firebase. (n.d.). *Receive messages in an Android app*. Retrieved from [https://firebase.google.com/docs/cloud-messaging/android/receive](https://firebase.google.com/docs/cloud-messaging/android/receive)
