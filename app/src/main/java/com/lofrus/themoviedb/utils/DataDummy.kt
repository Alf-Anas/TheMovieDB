package com.lofrus.themoviedb.utils

import com.lofrus.themoviedb.model.DetailMovieEntity
import com.lofrus.themoviedb.model.MovieEntity

import java.util.ArrayList

object DataDummy {

    fun generateDummyListMovies(): ArrayList<MovieEntity> {
        val movieEntity = ArrayList<MovieEntity>()
        movieEntity.add(MovieEntity(508442,
                0,
                "Soul",
                "2020-12-25",
            8.3,
                "https://www.themoviedb.org/t/p/w220_and_h330_face/hm58Jw4Lw8OIeECIq5qyPYhAeRJ.jpg"))
        movieEntity.add(MovieEntity(651571,
            0,
            "Breach",
            "2020-12-17",
            5.0,
            "https://www.themoviedb.org/t/p/w220_and_h330_face//13B6onhL6FzSN2KaNeQeMML05pS.jpg"))
        movieEntity.add(MovieEntity(520946,
            0,
            "100% Wolf",
            "2020-06-26",
            6.4,
            "https://www.themoviedb.org/t/p/w220_and_h330_face/2VrvxK4yxNCU6KVgo5TADJeBEQu.jpg"))
        movieEntity.add(MovieEntity(553604,
            0,
            "Honest Thief",
            "2020-09-03",
            6.6,
            "https://www.themoviedb.org/t/p/w220_and_h330_face/zeD4PabP6099gpE0STWJrJrCBCs.jpg"))
        movieEntity.add(MovieEntity(604822,
            0,
            "Vanguard",
            "2020-09-30",
            6.7,
            "https://www.themoviedb.org/t/p/w220_and_h330_face/vYvppZMvXYheYTWVd8Rnn9nsmNp.jpg"))
        movieEntity.add(MovieEntity(529203,
            0,
            "The Croods: A New Age",
            "2020-11-25",
            7.7,
            "https://www.themoviedb.org/t/p/w220_and_h330_face/tK1zy5BsCt1J4OzoDicXmr0UTFH.jpg"))
        movieEntity.add(MovieEntity(340102,
            0,
            "The New Mutants",
            "2020-08-26",
            6.4,
            "https://www.themoviedb.org/t/p/w220_and_h330_face/xZNw9xxtwbEf25NYoz52KdbXHPM.jpg"))

        return movieEntity
    }

    fun generateDummyListTVShow(): ArrayList<MovieEntity> {
        val movieEntity = ArrayList<MovieEntity>()
        movieEntity.add(MovieEntity(85271,
            1,
            "WandaVision",
            "2021-01-15",
            8.4,
            "https://www.themoviedb.org/t/p/w220_and_h330_face/glKDfE6btIRcVB5zrjspRIs4r52.jpg"))
        movieEntity.add(MovieEntity(79460,
            1,
            "Legacies",
            "2018-10-25",
            8.6,
            "https://www.themoviedb.org/t/p/w220_and_h330_face/qTZIgXrBKURBK1KrsT7fe3qwtl9.jpg"))
        movieEntity.add(MovieEntity(86382,
            1,
            "The Stand",
            "2020-12-17",
            7.2,
            "https://www.themoviedb.org/t/p/w220_and_h330_face/w6XiuRK5QQaLNmIqDRCWOpEcHwi.jpg"))
        movieEntity.add(MovieEntity(88055,
            1,
            "Servant",
            "2019-11-28",
            7.7,
            "https://www.themoviedb.org/t/p/w220_and_h330_face/8yfkkAeoI77opqAvB9fyf4knftS.jpg"))
        movieEntity.add(MovieEntity(82395,
            1,
            "The Liar",
            "2012-01-08",
            3.0,
            "https://www.themoviedb.org/t/p/w220_and_h330_face/hRigur2ywhUuHzVDBgeG3WICdKJ.jpg"))
        movieEntity.add(MovieEntity(46952,
            1,
            "The Blacklist",
            "2013-09-23",
            7.4,
            "https://www.themoviedb.org/t/p/w220_and_h330_face/htJzeRcYI2ewMm4PTrg98UMXShe.jpg"))

        return movieEntity
    }

    fun generateDummyMovieDetail(movie_id: Int): DetailMovieEntity {
        val detailMovie1 = DetailMovieEntity()
        detailMovie1.id = 635302
        detailMovie1.title = "Demon Slayer: Infinity Train"
        detailMovie1.date = "2020-10-16"
        detailMovie1.genre = "Animation, Action, Adventure, Fantasy, Drama"
        detailMovie1.rating = 8.0
        detailMovie1.poster = "https://www.themoviedb.org/t/p/w220_and_h330_face" + "/yF45egpHwaYLn4jTyZAgk0Cmug9.jpg"
        detailMovie1.backdrop = "https://www.themoviedb.org/t/p/w1920_and_h800_multi_faces" + "/d1sVANghKKMZNvqjW0V6y1ejvV9.jpg"
        detailMovie1.overview = "Tanjirō Kamado, joined with Inosuke Hashibira, a boy raised by boars who wears a boar's head, and Zenitsu Agatsuma, a scared boy who reveals his true power when he sleeps, boards the Infinity Train on a new mission with the Fire Hashira, Kyōjurō Rengoku, to defeat a demon who has been tormenting the people and killing the demon slayers who oppose it!"
        detailMovie1.link = "https://kimetsu.com/anime/movie/mugenressyahen/"

        val detailMovie2 = DetailMovieEntity()
        detailMovie2.id = 82395
        detailMovie2.title = "The Liar"
        detailMovie2.date = "2012-01-08"
        detailMovie2.genre = "Reality"
        detailMovie2.rating = 3.0
        detailMovie2.poster = "https://www.themoviedb.org/t/p/w220_and_h330_face" + "/hRigur2ywhUuHzVDBgeG3WICdKJ.jpg"
        detailMovie2.backdrop = "https://www.themoviedb.org/t/p/w1920_and_h800_multi_faces" + "/hQmNB28ETKKWPBkdNnDbzSSgKtd.jpg"
        detailMovie2.overview = ""
        detailMovie2.link = "https://www.qub.ca/tvaplus/tva/le-tricheur"
        return if (movie_id == 0) detailMovie1 else detailMovie2
    }

}