# Empowerly ✩°｡๋࣭ ⭑⚝

_Platform_ yang menghadirkan edukasi yang positif juga inspiratif untuk mencerminkan semangat pemberdayaan perempuan agar memahami hak mereka dan memperjuangkan kesetaraan. Kami menekankan nilai inti _awareness_, _action_, dan _community_.

## Empowerly's Description .ᐟ

**Empowerly** adalah platform edukasi dan komunitas digital yang berfokus pada pemberdayaan perempuan sebagai bagian dari upaya mendukung *Sustainable Development Goals (SDG) nomor 10: Reduced Inequalities*. Program ini ditujukan bagi seluruh lapisan masyarakat untuk meningkatkan pemahaman tentang kesetaraan dan inklusi perempuan di banyak bidang. Empowerly hadir sebagai sarana edukasi menyeluruh yang mendorong kesadaran, solidaritas, dan aksi nyata dalam memperjuangkan hak serta peran perempuan di masyarakat.

## Features ᝰ.ᐟ

Program ini berisi dua fitur utama, yaitu materi edukasi (1) dan komunitas (2). Member dapat mengakses materi sesuai kategori yang mereka inginkan dengan mengklik materi-materi yang telah ditampilkan. Lalu, untuk fitur komunitas, member dapat memilih forum lalu dapat mengomentari topik tersebut. Member juga dapat melakukan proses CRUD pada komentar yang sudah mereka kirim. 

## The 5 Pillars of Object Oriented Programming 𖤐

**Lima pilar utama dalam Pemrograman Berorientasi Objek (Object-Oriented Programming / OOP)** adalah fondasi yang membentuk cara berpikir dan perancangan sistem berbasis objek. 

### ✮ Encapsulation 

Pilar pertama, **Encapsulation**, berarti membungkus data dan fungsi dalam satu kesatuan (class) untuk menjaga keamanan dan mengontrol akses. Atribut dibuat _private_ dengan _getter & setter_ untuk mengontrol akses data. Penerapan tersebut banyak di sebagain besar _view_.

<img width="573" height="328" alt="image" src="https://github.com/user-attachments/assets/b0a062cc-2159-4909-a715-52b303391bdb" />

### ✮ Abstraction

Kedua, **Abstraction**, yaitu menyembunyikan detail kompleks dari pengguna dan hanya menampilkan fitur penting agar sistem lebih mudah dipahami. Kelas ini menyediakan kerangka dasar (method showInfo() abstrak) tanpa implementasi.

<img width="638" height="424" alt="image" src="https://github.com/user-attachments/assets/320e9781-9de3-4816-85a4-90d776b9b3b3" />

### ✮ Inheritance

Ketiga, **Inheritance**, memungkinkan suatu class mewarisi atribut dan metode dari class lain sehingga kode lebih efisien dan terstruktur.  Misalnya, class User berperan sebagai _class_ induk yang menyimpan atribut umum seperti id, username, password, dan role. Kemudian, _class_ AdminPanel dan UserPanel dijadikan _subclass_ yang mewarisi BasePanel.

<img width="410" height="90" alt="image" src="https://github.com/user-attachments/assets/ce5e1c17-7b10-4180-954e-f08ea3b55a5f" />

<img width="425" height="79" alt="image" src="https://github.com/user-attachments/assets/922d8618-7a04-4497-81ca-2d67be68a506" />

### ✮ Polymorphism

Keempat, **Polymorphism**, memberi kemampuan objek untuk memiliki banyak bentuk atau perilaku berbeda tergantung konteks penggunaannya. Metode yang sama (create, read, dll) dapat diimplementasikan dengan perilaku berbeda di _class_ lain. Misalnya di kelas Interface

<img width="322" height="139" alt="image" src="https://github.com/user-attachments/assets/8678334d-58de-4a99-bd27-3b56059b7a07" />

### ✮ Interface

Terakhir, **Interface**, berperan sebagai kontrak yang menentukan metode apa saja yang harus diimplementasikan oleh class tanpa menentukan cara implementasinya. Di program ini, _class interface_ bernama IWelcomeAction diimplementasikan di MainFrame.

<img width="521" height="35" alt="image" src="https://github.com/user-attachments/assets/2f212a07-9c1b-43e0-887c-bd98bce02e5c" />

<img width="485" height="353" alt="image" src="https://github.com/user-attachments/assets/e60070b0-9cfa-44b4-9d06-34b7f8e593b7" />

## Packages ᝰ.ᐟ

_Package_ adalah wadah atau folder yang digunakan untuk mengelompokkan _class-class_ yang saling berhubungan agar kode lebih terorganisir, mudah dikelola, dan mencegah konflik nama antar _class_.
Di sini, kami memisahkan fungsi berdasarkan tanggung jawabnya.

<img width="217" height="147" alt="image" src="https://github.com/user-attachments/assets/e1b3f1c0-f615-4cff-b429-a3fd11a26944" />

### ☆ Model (M)

Berisi _class_ yang merepresentasikan data dan logika bisnis utama.

<img width="176" height="53" alt="image" src="https://github.com/user-attachments/assets/223cbb47-b25d-445e-92fc-974a1b7a6d23" />

### ☆ View (V)

Menangani bagian antarmuka pengguna atau GUI.

<img width="242" height="161" alt="image" src="https://github.com/user-attachments/assets/9c23775d-45e5-496a-88cf-c34d71f62045" />

- MainFrame.java
Kelas ini jadi jendela utama aplikasi (JFrame). MainFrame menggunakan CardLayout supaya bisa menampung dan berpindah antar panel lain seperti LoginPanel, WelcomePanel, AdminPanel, dan UserPanel. Jadi, bisa dibilang ini adalah wadah utama tempat semua tampilan aplikasi dikelola.
- WelcomePanel.java
Panel ini berfungsi sebagai layar pembuka atau menu utama. Saat aplikasi dijalankan, inilah tampilan pertama yang muncul. Di sini pengguna bisa memilih untuk Login, Register, atau langsung Keluar dari aplikasi.
- LoginPanel.java
Panel ini menampilkan antarmuka login, tempat pengguna memasukkan username dan password. Setelah data divalidasi, sistem akan mengarahkan ke panel yang sesuai — apakah itu halaman admin atau halaman user.
- RegisterPanel.java
Panel ini menampilkan form pendaftaran untuk pengguna baru. Di sini pengguna bisa mengisi data seperti username, password, dan umur. Setelah itu, sistem akan memvalidasi dan menyimpan data ke database.
- UserPanel.java
Panel ini adalah dasbor utama bagi pengguna biasa setelah berhasil login. Tampilannya dilengkapi sidebar untuk navigasi ke bagian Materi dan Forum, serta area konten yang menampilkan daftar kategori atau diskusi.
- AdminPanel.java
Panel ini berfungsi sebagai dasbor admin. Sistem akan otomatis mengecek apakah admin yang masuk adalah Admin Materi atau Admin Komunitas, lalu menampilkan tampilan yang sesuai. Di sini juga tersedia fitur CRUD (Tambah, Edit, Hapus) untuk mengelola data materi maupun forum.
- BasePanel.java
Merupakan class JPanel dasar yang berfungsi sebagai superclass (induk). UserPanel dan AdminPanel mewarisi (extend) dari BasePanel ini, yang bertujuan untuk menerapkan konsep pewarisan (Inheritance) dan menyediakan fungsionalitas dasar yang mungkin sama untuk kedua panel tersebut.


### ☆ Controller (C)

_Package_ yang menjembatani antara _Model_ dan _View_ juga mengatur logika interaksi dan alur data antar komponen.

<img width="211" height="98" alt="image" src="https://github.com/user-attachments/assets/f27a40cc-e6f9-49b5-9f88-e3df0342a955" />

### ☆ Utilities 

Kumpulan helper _class_ atau _tools_ tambahan, di sini digunakan untuk _Hibernate_.

<img width="190" height="51" alt="image" src="https://github.com/user-attachments/assets/7fe69416-46fa-4e48-8ced-e04ecd3eff6d" />

### ☆ Main

Berisi _class_ yang digunakan untuk di-_run_ atau dieksekusi. Di dalam _class_ ini memanggil banyak _method_ dari kelas lain.

<img width="244" height="105" alt="image" src="https://github.com/user-attachments/assets/81c07022-eb5d-4d1e-8bbe-20e4b5c5212b" />

## Bonus Points ᯓ★

### ✪ MVC

Struktur program ini menerapkan pola _Model-View-Controller_ (MVC) agar kode mudah dibaca, diuji, dan direvisi.

### ✪ Object Relational Mapping (ORM)

Teknik yang menghubungkan objek Java dengan tabel database sehingga proses penyimpanan, pembaruan, dan pengambilan data dapat dilakukan otomatis tanpa perlu menulis SQL manual (menggunakan Hibernate atau JPA).

<img width="639" height="387" alt="image" src="https://github.com/user-attachments/assets/5722cabb-dda9-4671-b8a4-b6f3f89c5ee8" />

### ✪ Singleton

Pola desain untuk memastikan hanya ada satu _instance_ dari _class_ koneksi yang aktif selama _runtime_.

<img width="477" height="123" alt="image" src="https://github.com/user-attachments/assets/2284c267-cc26-46b9-9da1-f613e198b12e" />

### Libraries ᯓ✮

File JAR (Java Archive) merupakan format arsip yang digunakan untuk menyatukan berbagai file kelas Java (.class), pustaka eksternal, serta metadata ke dalam satu berkas terkompresi. Tujuan utama penggunaan JAR adalah untuk mempermudah distribusi, pengelolaan, dan eksekusi program Java yang terdiri dari banyak komponen.

<img width="353" height="205" alt="image" src="https://github.com/user-attachments/assets/c17dd11e-4fc1-4749-b3da-66d5f4b5829e" />

Dependency seperti slf4j, commons-lang3, dan protobuf-java mendukung _logging_, manipulasi string, serta komunikasi data yang efisien di dalam sistem.

<img width="360" height="298" alt="image" src="https://github.com/user-attachments/assets/ebe5ae4e-927d-4f7d-a974-fc6fa2d6db7d" />

_File_ .jar ini menunjukkan berbagai _library_ dan _framework_ yang digunakan dalam proyek, seperti Hibernate untuk ORM, Jakarta Persistence API untuk manajemen entitas, serta MySQL _Connector_ untuk koneksi basis data.

## Flowchart & Use Case ⋆ˎˊ˗

Tautan berikut menyajikan _flowchart_ dan _usecase diagram_ yang mendukung penjelasan mengenai _platform_ kami.

🔗 https://drive.google.com/file/d/1V-OX0WB-UvoNJsZCv_x6ynBh_GEaHkps/view?usp=sharing

## Program Flows ⭑𓂃 & Graphical User Interface (GUI) —͟͟͞͞★

### Halaman Utama
Ini adalah halaman utama (_welcome page_) saat pertama kali masuk ke dalam _platform_ Empowerly dijalankan, jadi halaman ini menjadi pintu masuk utama bagi user sebelum melanjutkan ke proses login atau registrasi, jadi tampilannya dibuat menarik dengan nuansa pink dan tema Powerpuff Girls sebagai simbol kekuatan dan suara perempuan.

Terdapat tiga tombol utama:

1. Login, menuju ke halaman untuk masuk kalau member atau admin sudah punya akun.
2. Register, menuju ke halaman pendaftaran akun baru (khusus member yang belum mempunyai akun).
3. Keluar, menutup aplikasi.

<img width="1089" height="714" alt="image" src="https://github.com/user-attachments/assets/28890f54-c46e-4854-9cbb-58396ac8412c" />

### Halaman Register (Member)
Halaman ini digunakan sebagai tempat member untuk membuat akun dan mendapatkan akses ke fitur Empowerly, jadi ada kolom username, password, umur. Setelah diisi, member tinggal klik tombol Register, dan data yang dimasukkan akan disimpan ke dalam database tabel member.

Setelah berhasil daftar, user bisa kembali ke halaman login dengan klik “Ada akun?” untuk masuk ke sistem. Admin tidak bisa  memakai halaman ini, karena admin sudah punya akun dari sistem. Halaman register ini hanya digunakan untuk member baru yang mau bergabung ke platform Empowerly.


<img width="1086" height="717" alt="image" src="https://github.com/user-attachments/assets/c1fe7276-cea6-40fd-9ab0-83620238210e" />

### Halaman Login

Pada halaman ini, dari admin maupun member dapat masuk ke dalam sistem Empowerly dengan memasukkan Username dan Password, kemudian setelah selesai memasukkan data dengan benar maka selanjutnya menekan tombol Masuk. Jika data yang dimasukkan sesuai dengan yang ada di database, maka:

1. Member akan diarahkan ke halaman utama yang berisi menu seperti materi, forum, dan komentar.

2. Admin akan diarahkan ke halaman khusus untuk mengelola data, seperti menambah materi atau mengatur konten di platform.

Selain itu, tersedia dua tombol tambahan yaitu “Belum ada akun?” untuk menuju ke halaman register bagi yang belum memiliki akun, dan “Kembali” untuk kembali ke halaman welcome.

<img width="1081" height="709" alt="image" src="https://github.com/user-attachments/assets/a03f58b1-bc52-47ed-a654-596b3d1e2b72" />



### Menu Member

<img width="1088" height="712" alt="image" src="https://github.com/user-attachments/assets/ccf4c6cb-0fd4-4732-8899-4640fc00ad81" />

Halaman ini adalah menu "Materi" untuk pengguna biasa, yang berfungsi sebagai katalog untuk melihat semua materi pembelajaran. Di sebelah kiri, terdapat menu navigasi utama untuk berpindah antar panel ("Materi", "Forum", "Komentar") atau keluar dari sesi ("Keluar"). Area utama di sebelah kanan menampilkan tabel berisi seluruh daftar materi yang tersedia (Read). Saat pengguna memilih salah satu materi dari tabel, detailnya akan ditampilkan di panel tengah. Pengguna kemudian dapat menekan tombol "Lihat Konten" untuk mengakses atau melihat isi materi yang telah dipilih tersebut.

### Menu Member

<img width="1090" height="718" alt="image" src="https://github.com/user-attachments/assets/8e17c77b-d8f4-45a2-a066-13196cd6df4b" />

Halaman ini adalah menu "Forum" untuk member, yang berfungsi sebagai gerbang utama untuk melihat semua topik diskusi yang tersedia. Di sebelah kiri, panel navigasi menunjukkan bahwa tab "Forum" sedang aktif, di mana pengguna juga dapat beralih ke menu "Materi", "Komentar", atau "Keluar". Bagian utama halaman ini menampilkan sebuah tabel (Read) yang merinci semua forum yang ada, lengkap dengan kolom "ID Forum", "Judul", "Deskripsi", dan "Admin" yang mengelolanya. Untuk berpartisipasi atau membaca isi diskusi, pengguna dapat memilih salah satu topik dari tabel, kemudian menekan tombol "Lihat Detail Forum & Komentar" untuk masuk ke ruang diskusi tersebut.

### Menu Member

<img width="1079" height="709" alt="image" src="https://github.com/user-attachments/assets/eb864d56-75f5-418a-bfc4-2a29e9dfdb42" />

Halaman ini adalah menu "Komentar", yang berfungsi untuk mengelola riwayat komentar pribadi member. Panel navigasi di sebelah kiri menunjukkan bahwa tab "Komentar" sedang aktif. Area utama menampilkan tabel (Read) yang berisi daftar komentar yang telah dikirim oleh pengguna, lengkap dengan detail "Isi Komentar", "Forum" tujuan, dan "Status" kiriman. Halaman ini juga mengizinkan pengguna untuk mengelola komentar mereka sendiri; dengan memilih salah satu entri dari tabel, pengguna dapat menekan tombol "Edit Komentar" (Update) atau "Hapus Komentar" (Delete) untuk mengubah atau menghapus komentar yang telah mereka buat.

### Menu Member

<img width="1084" height="712" alt="image" src="https://github.com/user-attachments/assets/d2b574eb-6f0f-4c30-8d60-7c586314b265" />

Halaman ini adalah tampilan yang muncul setelah pengguna memilih salah satu topik dari menu forum utama. Jendela ini berfungsi sebagai ruang diskusi interaktif. Di bagian atas, halaman ini menampilkan judul forum yang spesifik (contoh: "Perlindungan Pekerja di Era Modern") beserta deskripsinya. Di bawahnya, terdapat tabel yang menampilkan semua komentar yang sudah ada dari pengguna lain (Read), lengkap dengan nama "Pengirim" dan "Isi Komentar". Di bagian paling bawah, pengguna dapat berpartisipasi dalam diskusi dengan mengetik di field "Komentar Anda:" dan mengirimkannya menggunakan tombol "Tambah Komentar" (Create).

### Menu Admin Komunitas

<img width="1087" height="713" alt="image" src="https://github.com/user-attachments/assets/42b2d140-eb54-4109-8249-fb72c034813e" />

Halaman ini adalah menu "Kelola Forum" untuk Admin Komunitas. Halaman ini berfungsi penuh untuk mengelola semua topik diskusi (CRUD). Tampilan utamanya adalah tabel yang berisi daftar semua forum (Read). Di bagian bawah, admin memiliki tiga tombol :"Tambah Forum" untuk membuat topik baru (Create), "Edit Forum" untuk mengubah topik yang sudah dipilih (Update), dan "Hapus Forum" untuk menghapus topik tersebut (Delete).

### Menu Admin Komunitas

<img width="1084" height="714" alt="image" src="https://github.com/user-attachments/assets/b054527a-fa55-4ef8-aaea-ea4e183e569a" />

Halaman ini adalah menu "Kelola Komentar", yang digunakan admin untuk memoderasi konten. Halaman ini menampilkan tabel berisi semua komentar dari berbagai forum, lengkap dengan isi pesan, ID forum, dan ID pengirimnya (Read). Sesuai perannya sebagai moderator, admin dapat memilih komentar yang tidak pantas dari tabel dan menghapusnya secara permanen menggunakan tombol "Hapus Komentar" di bagian bawah (Delete).

### Menu Admin Materi

<img width="1087" height="710" alt="image" src="https://github.com/user-attachments/assets/a35c4773-a615-404b-ac82-eb71116d5e94" />

Halaman ini adalah menu utama untuk Admin Materi, yang berfungsi untuk mengelola semua konten pembelajaran. Halaman ini menyediakan fungsi untuk Tambah, Baca, Ubah, dan Hapus (CRUD) data materi. Tampilannya dibagi menjadi tiga bagian utama: di sebelah kiri terdapat menu navigasi (Materi dan Keluar), di bagian tengah adalah formulir untuk memasukkan atau mengedit data (Judul, Tipe, Konten), dan di sebelah kanan adalah tabel yang menampilkan semua data materi yang sudah tersimpan. Alur kerjanya sederhana: admin menekan tombol "Tambah" untuk mengaktifkan mode input data baru (lalu "Simpan"), atau dapat memilih data langsung dari tabel untuk diperbarui ("Edit") maupun dihapus ("Hapus").








