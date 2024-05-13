INSERT INTO genre (title, description)
VALUES ('Action', 'Movies with a focus on physical feats, often involving explosions and intense combat scenes.'),
       ('Adventure', 'Exciting and often risky experiences, typically involving exploration, travel, and danger.'),
       ('Comedy',
        'Entertainment intended to make an audience laugh, often through exaggerated situations or witty dialogue.'),
       ('Drama', 'Serious storytelling focused on emotional themes and character development.'),
       ('Fantasy', 'Imaginative and often magical settings, creatures, or events.'),
       ('Horror',
        'Designed to evoke fear, disgust, or terror in viewers, often featuring supernatural elements or psychopathic killers.'),
       ('Mystery', 'Intriguing narratives centered around solving a puzzle or uncovering hidden truths.'),
       ('Romance',
        'Stories of love, passion, and romantic relationships, often with emotional conflicts and happy endings.'),
       ('Sci-Fi',
        'Speculative fiction exploring futuristic or scientific concepts, often set in space or a technologically advanced world.'),
       ('Thriller', 'Suspenseful stories filled with tension, anticipation, and unexpected twists.');



INSERT INTO studio (name, description)
VALUES ('Madhouse', 'Known for producing high-quality anime series and movies.'),
       ('Studio Ghibli', 'Renowned for its hand-drawn animation and captivating storytelling.'),
       ('Toei Animation', 'One of the oldest and most prominent animation studios in Japan.');

INSERT INTO users (username, email, password, roles)
VALUES ('hinata', 'hinata@example.com', 'password123', 'ROLE_USER'),
       ('sasuke', 'sasuke@example.com', 'password123', 'ROLE_USER'),
       ('chihiro', 'chihiro@example.com', 'password123', 'ROLE_USER'),
       ('light', 'light@example.com', 'password123', 'ROLE_USER'),
       ('mitsuha', 'mitsuha@example.com', 'password123', 'ROLE_USER'),
       ('edward', 'edward@example.com', 'password123', 'ROLE_USER'),
       ('luffy', 'luffy@example.com', 'password123', 'ROLE_USER'),
       ('totoro', 'totoro@example.com', 'password123', 'ROLE_USER'),
       ('spike', 'spike@example.com', 'password123', 'ROLE_USER'),
       ('naruto', 'naruto@example.com', 'password123', 'ROLE_USER');

-- Insert 40 anime
INSERT INTO anime (title, description, type, date, director, creation, status, studio_id)
VALUES ('Haikyuu!!',
        'A high school student joins his school''s volleyball team, aiming to become a top player and compete in national tournaments.',
        'SERIES', '2014-04-06', 'Susumu Mitsunaka', CURRENT_TIMESTAMP, 'FINISHED', 2),
       ('Attack on Titan: Junior High',
        'A comedic spin-off of "Attack on Titan" where the characters are students attending Titan Junior High School.',
        'SERIES', '2015-10-04', 'Yoshihide Ibata', CURRENT_TIMESTAMP, 'FINISHED', 1),
       ('Sword Art Online',
        'In the near future, players enter a virtual reality MMORPG, but soon discover they cannot log out. To escape, they must clear the game''s dungeons.',
        'SERIES', '2012-07-08', 'Tomohiko Itō', CURRENT_TIMESTAMP, 'FINISHED', 3),
       ('Bleach',
        'A teenager gains the power to see ghosts and becomes a Soul Reaper, tasked with defending humans from malevolent spirits and guiding souls to the afterlife.',
        'SERIES', '2004-10-05', 'Noriyuki Abe', CURRENT_TIMESTAMP, 'FINISHED', 1),
       ('Attack on Titan: No Regrets',
        'A two-part OVA focusing on the backstory of Levi Ackerman and his time in the Survey Corps before the events of "Attack on Titan".',
        'OVA', '2014-12-09', 'Tetsurō Araki', CURRENT_TIMESTAMP, 'FINISHED', 2),
       ('Demon Slayer: Mugen Train',
        'Following the events of the anime series, Tanjiro Kamado and his friends board the Mugen Train to hunt down a demon terrorizing passengers.',
        'MOVIE', '2020-10-16', 'Haruo Sotozaki', CURRENT_TIMESTAMP, 'FINISHED', 3),
       ('Fruits Basket',
        'A high school girl discovers that her classmates are cursed to turn into animals from the Chinese zodiac, and she must help break the curse.',
        'SERIES', '2001-07-05', 'Akitaro Daichi', CURRENT_TIMESTAMP, 'FINISHED', 1),
       ('The Promised Neverland',
        'A group of children living in an orphanage uncover a dark secret about their caretaker and the true nature of their existence.',
        'SERIES', '2019-01-11', 'Mamoru Kanbe', CURRENT_TIMESTAMP, 'FINISHED', 2),
       ('Black Clover',
        'A young boy born without magic in a world where it is everything strives to become the Wizard King, the strongest mage in the Clover Kingdom.',
        'SERIES', '2017-10-03', 'Tatsuya Yoshihara', CURRENT_TIMESTAMP, 'ONGOING', 3),
       ('Mob Psycho 100',
        'A powerful psychic teenager tries to live a normal life while suppressing his overwhelming psychic abilities.',
        'SERIES', '2016-07-12', 'Yuzuru Tachikawa', CURRENT_TIMESTAMP, 'FINISHED', 1),
       ('JoJo''s Bizarre Adventure',
        'The multi-generational tale of the Joestar family and their battles against supernatural foes, each arc featuring a new protagonist with unique abilities.',
        'SERIES', '2012-10-05', 'Naokatsu Tsuda', CURRENT_TIMESTAMP, 'ONGOING', 2),
       ('Gurren Lagann',
        'In a future where humanity is forced to live underground, a young man discovers a mecha and leads a rebellion to fight against oppressive forces.',
        'SERIES', '2007-04-01', 'Hiroyuki Imaishi', CURRENT_TIMESTAMP, 'FINISHED', 3),
       ('Konosuba: God''s Blessing on This Wonderful World!',
        'A young man is transported to a fantasy world and forms a dysfunctional party with quirky companions on a quest for riches and adventure.',
        'SERIES', '2016-01-14', 'Takaomi Kanasaki', CURRENT_TIMESTAMP, 'FINISHED', 1),
       ('Attack on Titan: Lost Girls',
        'A two-part OVA adaptation of the spin-off novel focusing on Annie Leonhart and Mikasa Ackerman during the events of "Attack on Titan".',
        'OVA', '2017-12-08', 'Masashi Koizuka', CURRENT_TIMESTAMP, 'FINISHED', 2),
       ('Kimi no Na wa Another Side: Earthbound',
        'A novel adaptation providing additional perspectives and insights into the events of the "Your Name" movie.',
        'NOVEL', '2017-06-15', 'Makoto Shinkai', CURRENT_TIMESTAMP, 'FINISHED', 3),
       ('Promare',
        'In a world where spontaneous human combustion gives rise to flame-wielding mutants, a group of firefighters battles against these "Burnish" to save humanity.',
        'MOVIE', '2019-05-24', 'Hiroyuki Imaishi', CURRENT_TIMESTAMP, 'FINISHED', 1),
       ('Dr. Stone',
        'Thousands of years after humanity is petrified by a mysterious event, a brilliant teenager uses science to revive civilization and rebuild society from scratch.',
        'SERIES', '2019-07-05', 'Shinya Iino', CURRENT_TIMESTAMP, 'ONGOING', 2),
       ('One Piece: Stampede',
        'The Straw Hat Pirates join a massive pirate festival where the world''s most notorious pirates gather to seek the treasure left behind by Gol D. Roger.',
        'MOVIE', '2019-08-09', 'Takashi Otsuka', CURRENT_TIMESTAMP, 'FINISHED', 3),
       ('Demon Slayer: Kimetsu no Yaiba - Entertainment District Arc',
        'Following the events of "Demon Slayer: Mugen Train", Tanjiro Kamado and his companions face new challenges in the Entertainment District.',
        'SERIES', '2021-12-05', 'Haruo Sotozaki', CURRENT_TIMESTAMP, 'ONGOING', 1),
       ('Black Butler',
        'A young earl and his demonic butler navigate the treacherous underworld of Victorian England as they seek revenge against those who wronged the earl''s family.',
        'SERIES', '2008-10-03', 'Toshiya Shinohara', CURRENT_TIMESTAMP, 'FINISHED', 2),
       ('Attack on Titan: Before the Fall',
        'A manga adaptation exploring the events leading up to the creation of the Vertical Maneuvering Equipment and the Survey Corps'' battles against Titans.',
        'MANGA', '2013-07-17', 'Ryoji Fujiwara', CURRENT_TIMESTAMP, 'FINISHED', 3),
       ('The Rising of the Shield Hero',
        'Summoned to another world as the Shield Hero, a young man must rise from the depths of prejudice and betrayal to save the world and clear his name.',
        'SERIES', '2019-01-09', 'Takao Abo', CURRENT_TIMESTAMP, 'ONGOING', 1),
       ('Attack on Titan: Harsh Mistress of the City',
        'A two-part LIGHT_NOVEL adaptation following the story of two characters from the "Attack on Titan" series who struggle to survive in a city outside the walls.',
        'LIGHT_NOVEL', '2014-12-09', 'Ryoji Fujiwara', CURRENT_TIMESTAMP, 'FINISHED', 2),
       ('Cells at Work!',
        'A comedic depiction of the inner workings of the human body, where anthropomorphic cells battle against pathogens and other threats to maintain health.',
        'SERIES', '2018-07-08', 'Kenichi Suzuki', CURRENT_TIMESTAMP, 'FINISHED', 3),
       ('Overlord',
        'A player of a virtual reality MMORPG finds himself trapped in the game as his character, an all-powerful undead overlord, and seeks to dominate the world.',
        'SERIES', '2015-07-07', 'Naoyuki Itou', CURRENT_TIMESTAMP, 'FINISHED', 1),
       ('Attack on Titan: Lost Girls',
        'A manga adaptation of the spin-off novel focusing on Annie Leonhart and Mikasa Ackerman during the events of "Attack on Titan".',
        'MANGA', '2015-08-01', 'Ryoji Fujiwara', CURRENT_TIMESTAMP, 'FINISHED', 2),
       ('No Game No Life',
        'A brother and sister duo known as "Blank" are transported to a world where everything is decided by games and seek to conquer it using their intellect and gaming prowess.',
        'SERIES', '2014-04-09', 'Atsuko Ishizuka', CURRENT_TIMESTAMP, 'FINISHED', 3),
       ('Attack on Titan: Lost Girls',
        'A novel adaptation of the spin-off manga focusing on Annie Leonhart and Mikasa Ackerman during the events of "Attack on Titan".',
        'NOVEL', '2015-12-09', 'Ryoji Fujiwara', CURRENT_TIMESTAMP, 'FINISHED', 1),
       ('Re:Zero − Starting Life in Another World',
        'Transported to a fantasy world, a young man discovers he has the ability to rewind time upon death and must use this power to protect those he cares about.',
        'SERIES', '2016-04-04', 'Masaharu Watanabe', CURRENT_TIMESTAMP, 'ONGOING', 2),
       ('Attack on Titan: No Regrets',
        'A manga adaptation of the two-part OVA focusing on the backstory of Levi Ackerman and his time in the Survey Corps before the events of "Attack on Titan".',
        'MANGA', '2013-11-28', 'Hikaru Suruga', CURRENT_TIMESTAMP, 'FINISHED', 3),
       ('The Seven Deadly Sins',
        'A group of legendary knights known as the Seven Deadly Sins reunite to overthrow a tyrannical regime and save their kingdom.',
        'SERIES', '2014-10-05', 'Tensai Okamura', CURRENT_TIMESTAMP, 'FINISHED', 1),
       ('Attack on Titan: Junior High',
        'A manga adaptation of the comedic spin-off where the characters are students attending Titan Junior High School.',
        'MANGA', '2012-04-09', 'Saki Nakagawa', CURRENT_TIMESTAMP, 'FINISHED', 2),
       ('Attack on Titan: Before the Fall',
        'A novel adaptation exploring the events leading up to the creation of the Vertical Maneuvering Equipment and the Survey Corps'' battles against Titans.',
        'NOVEL', '2011-12-09', 'Suzuhito Yasuda', CURRENT_TIMESTAMP, 'FINISHED', 3),
       ('Attack on Titan: Lost Girls',
        'A novel adaptation of the spin-off manga focusing on Annie Leonhart and Mikasa Ackerman during the events of "Attack on Titan".',
        'LIGHT_NOVEL', '2014-05-02', 'Hiroshi Seko', CURRENT_TIMESTAMP, 'FINISHED', 1),
       ('Attack on Titan: Before the Fall',
        'A manga adaptation exploring the events leading up to the creation of the Vertical Maneuvering Equipment and the Survey Corps'' battles against Titans.',
        'MANGA', '2011-08-09', 'Suzuhito Yasuda', CURRENT_TIMESTAMP, 'FINISHED', 2),
       ('Attack on Titan: Harsh Mistress of the City',
        'A manga adaptation of the two-part LIGHT_NOVEL following the story of two characters from the "Attack on Titan" series who struggle to survive in a city outside the walls.',
        'MANGA', '2015-08-07', 'Ryoji Fujiwara', CURRENT_TIMESTAMP, 'FINISHED', 3),
       ('Attack on Titan: Lost Girls',
        'A manga adaptation of the spin-off novel focusing on Annie Leonhart and Mikasa Ackerman during the events of "Attack on Titan".',
        'MANGA', '2015-09-09', 'Ryoji Fujiwara', CURRENT_TIMESTAMP, 'FINISHED', 1),
       ('Attack on Titan: Before the Fall',
        'A manga adaptation exploring the events leading up to the creation of the Vertical Maneuvering Equipment and the Survey Corps'' battles against Titans.',
        'MANGA', '2011-10-09', 'Suzuhito Yasuda', CURRENT_TIMESTAMP, 'FINISHED', 2),
       ('Attack on Titan: Harsh Mistress of the City',
        'A manga adaptation of the two-part LIGHT_NOVEL following the story of two characters from the "Attack on Titan" series who struggle to survive in a city outside the walls.',
        'MANGA', '2015-09-09', 'Ryoji Fujiwara', CURRENT_TIMESTAMP, 'FINISHED', 3);

-- Insert 50 sample views
DO
$$
    DECLARE
        v_anime_id INT;
    BEGIN
        FOR i IN 1..10000
            LOOP
                -- Generate random genre_id and anime_id until a unique combination is found
                v_anime_id := floor(random() * 39) + 1;

                -- Insert unique combination into genre_m2m_anime table
                INSERT INTO views (anime_id) VALUES (v_anime_id);
            END LOOP;
    END
$$;

INSERT INTO views (anime_id, view_date)
SELECT (SELECT id FROM anime ORDER BY RANDOM() LIMIT 1),
       NOW() - INTERVAL '7 days' * RANDOM()
FROM generate_series(1, (ROUND(RANDOM() * 100) + 1)::integer);


-- Generate 100 random genre with unique combinations
DO
$$
    DECLARE
        v_genre_id INT;
        v_anime_id INT;
    BEGIN
        FOR i IN 1..100
            LOOP
                -- Generate random genre_id and anime_id until a unique combination is found
                LOOP
                    v_genre_id := floor(random() * 10) + 1;
                    v_anime_id := floor(random() * 39) + 1;
                    EXIT WHEN NOT EXISTS (SELECT 1
                                          FROM genre_m2m_anime
                                          WHERE genre_id = v_genre_id
                                            AND anime_id = v_anime_id);
                END LOOP;

                -- Insert unique combination into genre_m2m_anime table
                INSERT INTO genre_m2m_anime (genre_id, anime_id) VALUES (v_genre_id, v_anime_id);
            END LOOP;
    END
$$;


-- Generate 100 random rating with unique combinations
DO
$$
    DECLARE
        v_user_id  INT;
        v_anime_id INT;
        v_score    INT;
    BEGIN
        FOR i IN 1..100
            LOOP
                -- Generate random genre_id and anime_id until a unique combination is found
                LOOP
                    v_user_id := floor(random() * 10) + 1;
                    v_anime_id := floor(random() * 11) + 1;
                    v_score := floor(random() * 10) + 1;
                    EXIT WHEN NOT EXISTS (SELECT 1
                                          FROM rating
                                          WHERE user_id = v_user_id
                                            AND anime_id = v_anime_id);
                END LOOP;

                -- Insert unique combination into genre_m2m_anime table
                INSERT INTO rating (user_id, anime_id, score) VALUES (v_user_id, v_anime_id, v_score);
            END LOOP;
    END
$$;






