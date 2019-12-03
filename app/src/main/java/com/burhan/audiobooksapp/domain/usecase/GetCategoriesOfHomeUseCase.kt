package com.burhan.audiobooksapp.domain.usecase

import com.burhan.audiobooksapp.domain.model.AudioBook
import com.burhan.audiobooksapp.domain.model.Category

/**
 * Developed by tcbaras on 2019-10-24.
 */
class GetCategoriesOfHomeUseCase {
    fun loadData(callback: (categories: List<Category>) -> Unit) {
        callback(dummy())
    }

    private fun dummy(): @ParameterName(name = "categories") List<Category> {

        val audioBook0 = AudioBook(
            id = "0",
            name = "Becoming Michelle Obama",
            imageUrl = "https://m.media-amazon.com/images/I/51m1G3y9--L._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )
        val audioBook1 = AudioBook(
            id = "1",
            name = "Educated A Memoir",
            imageUrl = "https://m.media-amazon.com/images/I/41dIDDpGepL._SL500_.jpg",
            description = "Born to survivalists in the mountains of Idaho, Tara Westover was 17 the first time she set foot in a classroom. Her family was so isolated from mainstream society that there was no one to ensure the children received an education and no one to intervene when one of Tara’s older brothers became violent. When another brother got himself into college, Tara decided to try a new kind of life. Her quest for knowledge transformed her, taking her over oceans and across continents, to Harvard and to Cambridge University. Only then would she wonder if she’d traveled too far, if there was still a way home.",
            author = "Tara Westover",
            url = "https://samples.audible.com/bk/rand/005325/bk_rand_005325_sample.mp3"
        )
        val audioBook2 = AudioBook(
            id = "2",
            name = "The Giver Of Stars",
            imageUrl = "https://m.media-amazon.com/images/I/51biOSzmhvL._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )
        val audioBook3 = AudioBook(
            id = "3",
            name = "Conviction",
            imageUrl = "https://m.media-amazon.com/images/I/51M6GAQHC6L._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )
        val audioBook4 = AudioBook(
            id = "4",
            name = "John Grisham",
            imageUrl = "https://m.media-amazon.com/images/I/61Kqq0Mwb6L._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )
        val audioBook5 = AudioBook(
            id = "5",
            name = "UnF*ck Yourself",
            imageUrl = "https://m.media-amazon.com/images/I/51UGf2-jOaL._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )
        val audioBook6 = AudioBook(
            id = "6",
            name = "Brandon Sanderson",
            imageUrl = "https://m.media-amazon.com/images/I/51QNIbZh6XL._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )
        val audioBook7 = AudioBook(
            id = "7",
            name = "Norse Mythology",
            imageUrl = "https://m.media-amazon.com/images/I/61CrEYL26KL._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )
        val audioBook8 = AudioBook(
            id = "8",
            name = "Where The Crawdads Sing",
            imageUrl = "https://m.media-amazon.com/images/I/51vV-Rvl+RL._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )
        val audioBook9 = AudioBook(
            id = "9",
            name = "Atomic Habits",
            imageUrl = "https://m.media-amazon.com/images/I/513Y5o-DYtL._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )

        val audioBooks = listOf(
            audioBook0,
            audioBook1,
            audioBook2,
            audioBook3,
            audioBook4,
            audioBook5,
            audioBook6,
            audioBook7,
            audioBook8,
            audioBook9
        ).shuffled()

        val category0 = Category(
            id = "0",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks.shuffled()
        )
        val category1 = Category(
            id = "1",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks.shuffled()
        )
        val category2 = Category(
            id = "2",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks.shuffled()
        )
        val category3 = Category(
            id = "3",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks.shuffled()
        )
        val category4 = Category(
            id = "4",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks.shuffled()
        )
        val category5 = Category(
            id = "5",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks.shuffled()
        )
        val category6 = Category(
            id = "6",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks.shuffled()
        )
        val category7 = Category(
            id = "7",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks.shuffled()
        )
        val category8 = Category(
            id = "8",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks.shuffled()
        )
        val category9 = Category(
            id = "9",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks.shuffled()
        )

        return listOf(
            category0,
            category1,
            category2,
            category3,
            category4,
            category5,
            category6,
            category7,
            category8,
            category9
        )
    }


}