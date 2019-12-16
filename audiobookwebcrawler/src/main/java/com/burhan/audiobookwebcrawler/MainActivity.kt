package com.burhan.audiobookwebcrawler

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jsoup.Jsoup


class MainActivity : AppCompatActivity() {

    private val categories = mapOf(
//        "Arts & Entertainment" to "2226646011",
//        "Bios & Memoirs" to "2226647011",
//        "Business" to "2226648011",
//        "Classics" to "2226649011",
//        "Sexuality" to "2226651011",
//        "Fiction" to "2226652011",
//        "History" to "2226653011",
//        "Mysteries & Thrillers" to "2226655011",
//        "Romance" to "2226656011",
//        "Science & Technology" to "2226657011",
//        "Sci-Fi & Fantasy" to "2226658011",
//        "Self Development" to "2226659011",
//        "Comedy" to "2226660011"
        "Newspapers & Magazines" to "2226661011"
//        "Nostalgia Radio" to "2226662011",
//        "Radio & TV" to "2226663011",
//        "Sports" to "2226665011",
//        "Travel & Adventure" to "2226666011",
//        "Religion" to "2226667011",
//        "Nonfiction" to "2226668011",
//        "Life Events" to "2226669011",
//        "Language" to "2226669011",
//        "Drama" to "2226671011",
//        "Health & Fitness" to "2226672011",
//        "Kids" to "2239696011",
//        "Teens" to "2239710011"


    )
    private var URL =
        "https://www.audible.com/search?keywords=&title=&author_author=&narrator=&publisher=&node=2226648011&pageSize=20&page=1"
    private val urlPart0 =
        "https://www.audible.com/search?keywords=&title=&author_author=&narrator=&publisher=&node="
    private val urlPart1 = "&pageSize=20&page="


    private val db = FirebaseFirestore.getInstance()

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


//        GlobalScope.launch(Dispatchers.IO) {
//            val batch = db.batch()
//            val collection = db.collection("Categories2")
//            var data: HashMap<String, Any> = HashMap<String, Any>()
//            data["name"] = "Becoming Obama"
//            data["author"] = "Michelle Obama"
//            data["imageUrl"] = "bilmemne.jpg"
//            data["mp3"] = "dotdotdot.mp3"
//            data["description"] = "Uzun uzun bir description..."
//            batch.set(collection.document(), data)
//
//            data = HashMap<String, Any>()
//            data["name"] = "Becoming Obama 2"
//            data["author"] = "Michelle Obama 2"
//            data["imageUrl"] = "bilmemne.jpg 2"
//            data["mp3"] = "dotdotdot.mp3"
//            data["description"] = "Uzun uzun bir description.. 2."
//            batch.set(collection.document(), data)
//
//            batch.commit()
//        }


        startCrawling()


        webView.webViewClient = WebViewClient()
        webView.loadUrl(URL)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

    }

    private fun startCrawling() {
        GlobalScope.launch(Dispatchers.IO) {

            categories.forEach { category ->

                val books: MutableList<AudioBook> = mutableListOf()
                for (page in 0..20) {
                    val bookCrawler = GlobalScope.async {
                        val url = urlPart0 + category.value + urlPart1 + page
                        doCrawling(url)
                    }
                    val bookz = bookCrawler.await()
                    books.addAll(bookz)
                }


                val batch = db.batch()
                val collection = db.collection(category.key)
                Log.d("RESULTx", "${category.key} ------------------------->")
                books.forEachIndexed { index, audioBook ->
                    Log.d("RESULTx", "${String.format("%3d", index)}. $audioBook")
                    var data: HashMap<String, Any> = HashMap<String, Any>()
                    data["name"] = audioBook.name
                    data["author"] = audioBook.author
                    data["imageUrl"] = audioBook.imageUrl
                    data["mp3"] = audioBook.mp3
                    data["description"] = audioBook.description
                    batch.set(collection.document(), data)
                }
//                batch.commit() !! Be Careful

            }

        }
    }

    private fun doCrawling(url: String): List<AudioBook> {
        Log.d("RESULT", "-> $url")
        var audiobooks = MutableList(size = 20) { AudioBook() }

        val document = Jsoup.connect(url).get()
        document?.let { document ->
            val mp3sOnPage = document.select("button[data-mp3]")
            mp3sOnPage.forEachIndexed { index, element ->
                element.attr("data-mp3")?.let { mp3 ->
                    Log.d("MainActivity", "$index. $mp3")
                    audiobooks[index].mp3 = mp3
                }
            }


            var counter = -1
            val authors = document.select("a[href]")
            authors.forEachIndexed { index, element ->
                val href = element.attr("href")
                if (href.startsWith("/author/") || href.contains("searchAuthor")) {
                    if (href.endsWith("_1")) {
                        counter += 1
                        Log.d("MainActivity", "${counter}. ${element.text()}")
                        audiobooks[counter].author = element.text()
                    } else {
                        Log.d("MainActivity", "${counter}. ${element.text()}")
                        audiobooks[counter].author =
                            audiobooks[counter].author.plus(", " + element.text())
                    }
                }
            }

            val images = document.select("img")
            counter = 0
            var lastImage = ""
            images.forEach {
                it?.attr("src")?.let { src ->
                    if (src.endsWith("SL500_.jpg") && lastImage != src) {
                        var name = it.attr("alt")
                        name = name.take(name.indexOf("audiobook cover art")).trim()

                        audiobooks[counter].name = name
                        audiobooks[counter].imageUrl = src
                        Log.d("MainActivity", "${counter++}. $src $name")
                        lastImage = src
                    }
                }
            }
        }

        var counter = 0
        document.getElementsByClass("bc-popover-inner").forEach {
            var isAlreadyFound = false
            it.getElementsByTag("p").forEach {
                if (it.text().length > 150 && !it.text().contains("bc-text")) {
                    if (!isAlreadyFound) {
                        audiobooks[counter].description = it.text()
                        Log.d("MainActivity", "${counter++} ${it.text()}")
                        isAlreadyFound = true
                    }
                }
            }
        }

//        audiobooks.forEach {
//            Log.d("BURHANx", it.toString())
//        }

        return audiobooks
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
