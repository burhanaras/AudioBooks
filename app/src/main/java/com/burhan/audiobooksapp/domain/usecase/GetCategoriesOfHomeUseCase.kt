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
            name = "Becoming Michelle Obama",
            imageUrl = "https://m.media-amazon.com/images/I/51m1G3y9--L._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )
        val audioBook2 = AudioBook(
            id = "2",
            name = "Becoming Michelle Obama",
            imageUrl = "https://m.media-amazon.com/images/I/51m1G3y9--L._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )
        val audioBook3 = AudioBook(
            id = "3",
            name = "Becoming Michelle Obama",
            imageUrl = "https://m.media-amazon.com/images/I/51m1G3y9--L._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )
        val audioBook4 = AudioBook(
            id = "4",
            name = "Becoming Michelle Obama",
            imageUrl = "https://m.media-amazon.com/images/I/51m1G3y9--L._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )
        val audioBook5 = AudioBook(
            id = "5",
            name = "Becoming Michelle Obama",
            imageUrl = "https://m.media-amazon.com/images/I/51m1G3y9--L._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )
        val audioBook6 = AudioBook(
            id = "6",
            name = "Becoming Michelle Obama",
            imageUrl = "https://m.media-amazon.com/images/I/51m1G3y9--L._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )
        val audioBook7 = AudioBook(
            id = "7",
            name = "Becoming Michelle Obama",
            imageUrl = "https://m.media-amazon.com/images/I/51m1G3y9--L._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )
        val audioBook8 = AudioBook(
            id = "8",
            name = "Becoming Michelle Obama",
            imageUrl = "https://m.media-amazon.com/images/I/51m1G3y9--L._SL500_.jpg",
            description = "In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As first lady of the United States of America - the first African American to serve in that role - she helped create the most welcoming and inclusive White House in history while also establishing herself as a powerful advocate for women and girls in the US and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.",
            author = "Michelle Obama"
        )
        val audioBook9 = AudioBook(
            id = "9",
            name = "Becoming Michelle Obama",
            imageUrl = "https://m.media-amazon.com/images/I/51m1G3y9--L._SL500_.jpg",
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
        )

        val category0 = Category(
            id = "0",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks
        )
        val category1 = Category(
            id = "1",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks
        )
        val category2 = Category(
            id = "2",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks
        )
        val category3 = Category(
            id = "3",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks
        )
        val category4 = Category(
            id = "4",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks
        )
        val category5 = Category(
            id = "5",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks
        )
        val category6 = Category(
            id = "6",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks
        )
        val category7 = Category(
            id = "7",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks
        )
        val category8 = Category(
            id = "8",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks
        )
        val category9 = Category(
            id = "9",
            name = "The Best of 2019",
            imageUrl = "https://m.media-amazon.com/images/G/01/Audible/en_US/images/creative/CO-933_RP_ListeningClub_9_25_19_HP_Banner_DSK._CB1569250349_.png",
            audioBooks = audioBooks
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