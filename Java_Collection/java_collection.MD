# Java Collection

---

### Java Collection

<!-- @import "[TOC]" {cmd="toc" depthFrom=1 depthTo=6 orderedList=false} -->

_Collection_ atau yang biasa disebut dengan _container_ adalah object yang mengumpulkan atau berisikan data-data, seperti Array. Collection digunakan untuk menyimpan dan memanipulasi sebuah objek.

Teknik pengelompokannya terbagi menjadi 3 , yaitu :
_1. Set_ , yang terdiri dari Hash set , LinkedHashSet dan Tree set
_2. List_ , yang terdiri dari Array List , Vector dan LinkedList
_3. Queue_ , yang terdiri dari LinkedList dan Priority Queue

---

### Iterable dan Iterator Interface

_1. Iterable Interface_
Merupakan parent semua collection di Java , kecuali Map dan hanya digunakan untuk mendukung for - each loop

-> Kode Iterable

java
Iterable<String> names = List.of("Dyandra","Aurellia");
for(var name: names) {
System.out.println(name);
}

_2. Iterator Interface_
Merupakan interface yang mendefinisikan cara mengakses elemen dalam collection secara berurutan. For-each loop menggunakan Iterator untuk bekerja

-> Kode Iterator Interface

java
Iterator<String> iterator = names.iterator();
while (iterator.hasNext()) {
String name = iterator.next();
System.out.println(name);
}

---

### Collection Interface

Collection adalah parent class untuk semua collection di Java. Collection menyediakan fungsi untuk memanipulasi data collection, seperti menambah, menghapus, dan mengecek isi data.

_Method di Collection_

1. Menambah data: add(), addAll()
2. Menghapus data: remove(), removeAll(), clear()
3. Mengecek data: contains(), isEmpty()

---

### List Interface

List adalah struktur data collection yang digunakan untuk menyimpan data dengan beberapa karakteristik seperti duplikasi elemen , urutan dan akses dengan index.

List Interface dibagi menjadi 3, yaitu :

| ArrayList                                 | LinkedList                                                 | Vector                                           |
| ----------------------------------------- | ---------------------------------------------------------- | ------------------------------------------------ |
| Implementasi dari list menggunakan array. | Implementasi list dengan struktur data Double Linked List. | Implementasi list yang menggunakan array dinamis |

-> Kode List

java
public static void _main_(String[] args) {
List<String> names = new ArrayList<>();

    names.add("Dyandra");

    System.out.println (names);

}

---

### Queue

Queue adalah implementasi dari struktur data Antrian atau FIFO (First In First Out).

Queue dibagi menjadi 3 , yaitu :
_1. Array Deque_ , menggunakan array sebagai implementasi queue nya
_2. LinkedList_ , menggunakan double linked list sebagai implementasi queue nya
_3. Priority Queue_ , menggunakan array sebagai implementasi queue nya, namun diurutkan mennggunakan Comparable atau Comparator

-> Kode Queue

java
Queue<String> names = new ArrayDeque<>();
names.offer("Dyandra");

        for(String next = names.poll(); next != null; next = names.poll()) {
        }
        System.out.println(names.size());

---

### Map Interface

Map adalah struktur data collection yang berisikan mapping antara key dan value. Key di Map harus unik dan tidak boleh duplikat.

Map dibagi menjadi 5, yaitu :

1. HashMap
2. Weak HashMap
3. Identity HashMap
4. Linked HashMap
5. Enum HashMap

- Kode HashMap

java
Map<String, String> map = new HashMap<>();
map.put("Dyandra", "Aurellia");
System.out.println(map.get("Dyandra"));
