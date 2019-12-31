package com.burhan.audiobookwebcrawler

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Element


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
        "https://www.audible.com/newreleases?pf_rd_p=d37ffc99-e148-4dbc-a156-ec96fac4289f&pf_rd_r=50JF0K9NZC7H59FDCC85&ref=a_adblbests_t1_navTop_pl2cg1c0r1"
    private val urlPart0 =
        "https://www.audible.com/search?keywords=&title=&author_author=&narrator=&publisher=&node="
    private val urlPart1 = "&pageSize=20&page="


    private val db = FirebaseFirestore.getInstance()

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        GlobalScope.launch {
            fetchAudioBooks()
        }

        webView.webViewClient = WebViewClient()
        webView.loadUrl(URL)
        fab.setOnClickListener {
            GlobalScope.launch {
                fetchAudioBooks()
            }
        }

    }

    private suspend fun fetchAudioBooks() {
        coroutineScope {
            val deferreds = listOf(
                async {
                    val newReleases = fetchNewReleases()
                    Log.d(TAG, "We have ${newReleases.size} new releases.")
                    saveToFireStore(Category("New Releases", "New Releases", "", newReleases, "1"))
                },
                async {
                    val bestSellers = fetchBestSellers()
                    Log.d(TAG, "We have ${bestSellers.size} best sellers.")
                    saveToFireStore(Category("Best Sellers", "Best Sellers", "", bestSellers, "2"))
                },
                async {
                    val favorites = fetchFavorites()
                    Log.d(TAG, "We have ${favorites.size} favorites.")
                    saveToFireStore(
                        Category(
                            "User Favorites",
                            "User Favorites",
                            "",
                            favorites,
                            "3"
                        )
                    )
                }
            )
            deferreds.awaitAll()
            Log.d(TAG, "Finally, we have crawled them all.")
        }
        Log.d(TAG, "And here we are.")
    }

    private suspend fun fetchNewReleases(): List<AudioBook> {
        val newReleases = mutableListOf<AudioBook>()
        val url =
            "https://www.audible.com/newreleases?pf_rd_p=4151a082-617e-4be0-b9af-ab85a5887dc2&pf_rd_r=ZGQ1MZ5E55G6X3CH3D5D&ref=a_newreleas_c4_pageNum_1&publication_date=20191219-20191226&feature_six_browse-bin=9178177011&feature_five_browse-bin=9178163011&submitted=1&page="

        (1..5).forEach { i ->
            val fullUrl = url + i
            val bookz = fetchBookDetail(fullUrl)
            newReleases.addAll(bookz)
        }
        return newReleases
    }

    private suspend fun fetchBestSellers(): List<AudioBook> {
        val bestSellers = mutableListOf<AudioBook>()
        val url =
            "https://www.audible.com/adblbestsellers?pf_rd_p=e1595489-c152-4314-a5d7-ed60b7e2ecc8&pf_rd_r=2JMZJFS5KRBMPREVM17C&ref=a_adblbests_c5_pageNum_1&page="

        (1..5).forEach { i ->
            val fullUrl = url + i
            val bookz = fetchBookDetail(fullUrl)
            bestSellers.addAll(bookz)
        }

        return bestSellers
    }

    private suspend fun fetchFavorites(): List<AudioBook> {

        val document = getDocument("https://www.audible.com/")
        val scripts = document.getElementsByTag("script")
        val books = mutableListOf<AudioBook>()
        var bookIds: MutableList<String>
        scripts.forEach { script ->
            if (script.toString().contains("sourceAsins")) {
                var index = script.toString().indexOf("sourceAsins")
                index += 15
                var text = script.toString().substring(index)
                val endIndex = text.indexOf("\";")
                text = text.substring(0, endIndex)
                bookIds = text.split(",") as MutableList<String>
                val bookz = fetchBookDetail(bookIds)
                books.addAll(bookz)

            }
        }
        return books
    }

    private suspend fun fetchBookDetail(bookIds: MutableList<String>): List<AudioBook> {
        val books = mutableListOf<AudioBook>()
        bookIds.forEach {
            val bookz = fetchBookDetail("https://www.audible.com/search?keywords=$it")
            books.addAll(bookz)
        }
        return books
    }

    private suspend fun fetchBookDetail(url: String): List<AudioBook> {

        val audioBooks = mutableListOf<AudioBook>()
        val document = getDocument(url)
        document?.let {
            val mp3sOnPage = document.select("button[data-mp3]")
            mp3sOnPage.forEachIndexed { index, element ->
                element.attr("data-mp3")?.let { mp3 ->

                    val parentNode =
                        element.parentNode()?.parentNode()?.parentNode()?.parentNode()
                    parentNode?.let { parentNode ->
                        var text = (parentNode as Element).text()
                        val by = text.indexOf(" By: ")
                        val name = text.substring(0, by)
                        text = text.substring(by + " By: ".length)
                        val narratedBy = text.indexOf(" Narrated by: ")
                        val author = text.substring(0, narratedBy).trim()
                        text = text.substring(narratedBy + " Narrated by: ".length)
                        val length = text.indexOf(" Length: ")
                        val narrated = text.substring(0, length)
                        text = text.substring(length + " Length: ".length)
                        val overall = text.indexOf("Overall")
                        val len = text.subSequence(0, overall)
                        text = text.substring(overall + "Overall".length)
                        val performance = text.indexOf(" Performance ")
                        val overalll = text.substring(0, performance)
                        text = text.substring(performance + " Performance ".length)
                        val story = text.indexOf(" Story ")
                        val perf = text.substring(0, story)
                        text = text.substring(story + " Story ".length)
                        val fiveStars = text.indexOf(" out of 5 stars ")
                        text = text.substring(fiveStars + " out of 5 stars ".length)
                        text = text.substring(text.indexOf(" ") + 1)
                        var fourDots = text.indexOf(".... ")
                        if (fourDots < 0) fourDots = text.length
                        val description = text.substring(0, fourDots)
                        val image = parentNode.getElementById("nojs_img_").attr("src")

                        val audioBook = AudioBook(name, author, image, mp3, description)
                        audioBooks.add(audioBook)
                    }
                }
            }
        }

        return audioBooks
    }

    private suspend fun getDocument(url: String) = withContext(Dispatchers.IO) {
        Jsoup.connect(url).get()
    }

    private fun saveToFireStore(category: Category) {
        GlobalScope.launch(Dispatchers.IO) {
            val batch = db.batch()
            val collection = db.collection(category.name)
            Log.d(
                TAG,
                "${category.name} (${category.audioBooks.size} audiobooks) is gonna be saved to Firestore------------------------->"
            )
            category.audioBooks.forEachIndexed { index, audioBook ->
                Log.d(TAG, "${String.format("%3d", index)}. $audioBook")
                val data: HashMap<String, Any> = HashMap()
                data["name"] = audioBook.name
                data["author"] = audioBook.author
                data["imageUrl"] = audioBook.imageUrl
                data["mp3"] = audioBook.mp3
                data["description"] = audioBook.description
                batch.set(collection.document(), data)
            }
           // batch.commit()//!! Be Careful
        }
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

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }
}
