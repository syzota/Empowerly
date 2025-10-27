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

Keempat, **Polymorphism**, memberi kemampuan objek untuk memiliki banyak bentuk atau perilaku berbeda tergantung konteks penggunaannya. Metode yang sama (create, read, dll) dapat diimplementasikan dengan perilaku berbeda di _class_ lain.

<img width="373" height="164" alt="image" src="https://github.com/user-attachments/assets/0e8c4129-923b-4429-b4b4-d6e290fc9830" />

### ✮ Interface

Terakhir, **Interface**, berperan sebagai kontrak yang menentukan metode apa saja yang harus diimplementasikan oleh class tanpa menentukan cara implementasinya.

<img width="271" height="93" alt="image" src="https://github.com/user-attachments/assets/a66d1846-a421-4c47-aac2-84a5ab8bc646" />

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

### ☆ Controller (C)

_Package_ yang menjembatani antara _Model_ dan _View_ juga mengatur logika interaksi dan alur data antar komponen.

<img width="271" height="93" alt="image" src="https://github.com/user-attachments/assets/a66d1846-a421-4c47-aac2-84a5ab8bc646" />

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

## Flowchart & Use Case ⋆ˎˊ˗

Tautan berikut menyajikan _flowchart_ dan _usecase diagram_ yang mendukung penjelasan mengenai _platform_ kami.

🔗 https://drive.google.com/file/d/1V-OX0WB-UvoNJsZCv_x6ynBh_GEaHkps/view?usp=sharing

## Program Flows ⭑𓂃 & Graphical User Interface (GUI) —͟͟͞͞★

## Halaman Utama
Ini adalah halaman utama (welcome page) saat pertama kali masuk ke dalam platform Empowerly dijalankan, jadi halaman ini menjadi pintu masuk utama bagi user sebelum melanjutkan ke proses login atau registrasi, jadi tampilannya dibuat menarik dengan nuansa pink dan tema Powerpuff Girls sebagai simbol kekuatan dan suara perempuan.

<img width="1089" height="714" alt="image" src="https://github.com/user-attachments/assets/28890f54-c46e-4854-9cbb-58396ac8412c" />

<img width="1086" height="717" alt="image" src="https://github.com/user-attachments/assets/c1fe7276-cea6-40fd-9ab0-83620238210e" />

<img width="1081" height="709" alt="image" src="https://github.com/user-attachments/assets/a03f58b1-bc52-47ed-a654-596b3d1e2b72" />

<img width="1091" height="716" alt="image" src="https://github.com/user-attachments/assets/c2790f50-d7d3-49f0-93f7-c09156bdbd72" />

<img width="1089" height="718" alt="image" src="https://github.com/user-attachments/assets/3fd2008b-fbf0-422b-87e0-26e0243e82fc" />

<img width="1088" height="712" alt="image" src="https://github.com/user-attachments/assets/ccf4c6cb-0fd4-4732-8899-4640fc00ad81" />

<img width="1090" height="718" alt="image" src="https://github.com/user-attachments/assets/8e17c77b-d8f4-45a2-a066-13196cd6df4b" />

<img width="1079" height="709" alt="image" src="https://github.com/user-attachments/assets/eb864d56-75f5-418a-bfc4-2a29e9dfdb42" />

<img width="1084" height="712" alt="image" src="https://github.com/user-attachments/assets/d2b574eb-6f0f-4c30-8d60-7c586314b265" />

<img width="1087" height="713" alt="image" src="https://github.com/user-attachments/assets/42b2d140-eb54-4109-8249-fb72c034813e" />

<img width="1084" height="714" alt="image" src="https://github.com/user-attachments/assets/b054527a-fa55-4ef8-aaea-ea4e183e569a" />

### Menu Admin Materi

<img width="1087" height="710" alt="image" src="https://github.com/user-attachments/assets/a35c4773-a615-404b-ac82-eb71116d5e94" />

Halaman ini adalah menu utama untuk Admin Materi, yang berfungsi untuk mengelola semua konten pembelajaran. Halaman ini menyediakan fungsi lengkap untuk Tambah, Baca, Ubah, dan Hapus (CRUD) data materi. Tampilannya dibagi menjadi tiga bagian utama: di sebelah kiri terdapat menu navigasi ('Materi' dan 'Keluar'), di bagian tengah adalah formulir untuk memasukkan atau mengedit data (Judul, Tipe, Konten), dan di sebelah kanan adalah tabel yang menampilkan semua data materi yang sudah tersimpan. Alur kerjanya sederhana: admin menekan tombol "Tambah" untuk mengaktifkan mode input data baru (lalu "Simpan"), atau dapat memilih data langsung dari tabel untuk diperbarui ("Edit") maupun dihapus ("Hapus").








