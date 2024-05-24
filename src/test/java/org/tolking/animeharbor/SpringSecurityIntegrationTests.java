package org.tolking.animeharbor;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SpringSecurityIntegrationTests {
    private static final String SUCCESS = "SUCCESS: ";
    private static final String FAIL = "FAIL: ";
    private static final String INDEX_MESSAGE = "access to index page";
    private static final String LOGIN_MESSAGE = "access to login page";
    private static final String GENRES_MESSAGE = "access to genres page";
    private static final String WATCHLIST_MESSAGE = "access to watchlist page";
    private static final String ADMIN_ANIME_MESSAGE = "access to admin anime page";
    private static final String ADMIN_STUDIO_MESSAGE = "access to admin studio page";
    private static final String ADMIN_GENRE_MESSAGE = "access to admin genre page";
    private static final String ADMIN_USER_MESSAGE = "access to admin user page";
    private static final String SUPER_ADMIN_MESSAGE = "access to admin privileges";

    private static final String INDEX_URL = "/";
    private static final String INDEX_VIEW = "index";
    private static final String GENRES_URL = "/genres";
    private static final String GENRE_VIEW = "genre";
    private static final String LOGIN_URL = "/login";
    private static final String LOGIN_VIEW = "/authorization/login";
    private static final String WATCHLIST_URL = "/watchlist";
    private static final String WATCHLIST_VIEW = "watchlist";
    private static final String ADMIN_ANIME_URL = "/admin/anime";
    private static final String ADMIN_ANIME_VIEW = "admin/anime";
    private static final String ADMIN_GENRE_URL = "/admin/genre";
    private static final String ADMIN_GENRE_VIEW = "admin/genre";
    private static final String ADMIN_STUDIO_URL = "/admin/studio";
    private static final String ADMIN_STUDIO_VIEW = "admin/studio";
    private static final String ADMIN_USER_URL = "/admin/user";
    private static final String ADMIN_USER_VIEW = "admin/user";
    private static final String SUPER_ADMIN_DEGRADE_URL = "/admin/user/degrade/1";
    private static final String SUPER_ADMIN_UPDATE_URL = "/admin/user/update/1";

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @WithAnonymousUser
    class  anonymous_user {
        @Test
        @DisplayName(SUCCESS + INDEX_MESSAGE)
        void index_page() throws Exception {
            performAndExpectView(INDEX_URL, INDEX_VIEW);
        }

        @Test
        @DisplayName(SUCCESS + GENRES_MESSAGE)
        void genres_page() throws Exception {
            performAndExpectView(GENRES_URL, GENRE_VIEW);
        }

        @Test
        @DisplayName(SUCCESS + LOGIN_MESSAGE)
        void login_page() throws Exception {
            performAndExpectView(LOGIN_URL, LOGIN_VIEW);
        }

        @Test
        @DisplayName(FAIL + WATCHLIST_MESSAGE)
        void watchlist_page() throws Exception {
            performAndExpectStatus(WATCHLIST_URL, status().isUnauthorized());
        }

        @Test
        @DisplayName(FAIL + ADMIN_ANIME_MESSAGE)
        void admin_anime_page() throws Exception {
            performAndExpectStatus(ADMIN_ANIME_URL, status().isUnauthorized());
        }

        @Test
        @DisplayName(FAIL + ADMIN_GENRE_MESSAGE)
        void admin_genre_page() throws Exception {
            performAndExpectStatus(ADMIN_GENRE_URL, status().isUnauthorized());
        }

        @Test
        @DisplayName(FAIL + ADMIN_STUDIO_MESSAGE)
        void admin_studio_page() throws Exception {
            performAndExpectStatus(ADMIN_STUDIO_URL, status().isUnauthorized());
        }

        @Test
        @DisplayName(FAIL + ADMIN_USER_MESSAGE)
        void admin_user_page() throws Exception {
            performAndExpectStatus(ADMIN_USER_URL, status().isUnauthorized());
        }

        @Test
        @DisplayName(FAIL + SUPER_ADMIN_MESSAGE)
        void admin_control() throws Exception {
            performAndExpectStatus(SUPER_ADMIN_DEGRADE_URL, status().isUnauthorized());
            performAndExpectStatus(SUPER_ADMIN_UPDATE_URL, status().isUnauthorized());
        }

    }

    @Nested
    @WithMockUser(username = "user", roles = "USER")
    class user {
        @Test
        @DisplayName(SUCCESS + INDEX_MESSAGE)
        void index_page() throws Exception {
            performAndExpectView(INDEX_URL, INDEX_VIEW);
        }

        @Test
        @DisplayName(SUCCESS + GENRES_MESSAGE)
        void genres_page() throws Exception {
            performAndExpectView(GENRES_URL, GENRE_VIEW);
        }

        @Test
        @DisplayName(FAIL + LOGIN_MESSAGE)
        void login_page() throws Exception {
            performAndExpectStatus(LOGIN_URL, status().isForbidden());
        }

        @Test
        @DisplayName(SUCCESS + WATCHLIST_MESSAGE)
        void watchlist_page() throws Exception {
            performAndExpectView(WATCHLIST_URL, WATCHLIST_VIEW);
        }

        @Test
        @DisplayName(FAIL + ADMIN_ANIME_MESSAGE)
        void admin_anime_page() throws Exception {
            performAndExpectStatus(ADMIN_ANIME_URL, status().isForbidden());
        }

        @Test
        @DisplayName(FAIL + ADMIN_GENRE_MESSAGE)
        void admin_genre_page() throws Exception {
            performAndExpectStatus(ADMIN_GENRE_URL, status().isForbidden());
        }

        @Test
        @DisplayName(FAIL + ADMIN_STUDIO_MESSAGE)
        void admin_studio_page() throws Exception {
            performAndExpectStatus(ADMIN_STUDIO_URL, status().isForbidden());
        }

        @Test
        @DisplayName(FAIL + ADMIN_USER_MESSAGE)
        void admin_user_page() throws Exception {
            performAndExpectStatus(ADMIN_USER_URL, status().isForbidden());
        }

        @Test
        @DisplayName(FAIL + SUPER_ADMIN_MESSAGE)
        void admin_control() throws Exception {
            performAndExpectStatus(SUPER_ADMIN_DEGRADE_URL, status().isForbidden());
            performAndExpectStatus(SUPER_ADMIN_UPDATE_URL, status().isForbidden());
        }
    }

    @Nested
    @WithMockUser(username = "admin", roles = "ADMIN")
    class admin {
        @Test
        @DisplayName(SUCCESS + INDEX_MESSAGE)
        void index_page() throws Exception {
            performAndExpectView(INDEX_URL, INDEX_VIEW);
        }

        @Test
        @DisplayName(SUCCESS + GENRES_MESSAGE)
        void genres_page() throws Exception {
            performAndExpectView(GENRES_URL, GENRE_VIEW);
        }

        @Test
        @DisplayName(FAIL + LOGIN_MESSAGE)
        void login_page() throws Exception {
            performAndExpectStatus(LOGIN_URL, status().isForbidden());
        }

        @Test
        @DisplayName(SUCCESS + WATCHLIST_MESSAGE)
        void watchlist_page() throws Exception {
            performAndExpectView(WATCHLIST_URL, WATCHLIST_VIEW);
        }

        @Test
        @DisplayName(SUCCESS + ADMIN_ANIME_MESSAGE)
        void admin_anime_page() throws Exception {
            performAndExpectView(ADMIN_ANIME_URL, ADMIN_ANIME_VIEW);
        }

        @Test
        @DisplayName(SUCCESS + ADMIN_GENRE_MESSAGE)
        void admin_genre_page() throws Exception {
            performAndExpectView(ADMIN_GENRE_URL, ADMIN_GENRE_VIEW);
        }

        @Test
        @DisplayName(SUCCESS + ADMIN_STUDIO_MESSAGE)
        void admin_studio_page() throws Exception {
            performAndExpectView(ADMIN_STUDIO_URL, ADMIN_STUDIO_VIEW);
        }

        @Test
        @DisplayName(SUCCESS + ADMIN_USER_MESSAGE)
        void admin_user_page() throws Exception {
            performAndExpectView(ADMIN_USER_URL, ADMIN_USER_VIEW);
        }

        @Test
        @DisplayName(FAIL + SUPER_ADMIN_MESSAGE)
        void admin_control() throws Exception {
            performAndExpectStatus(SUPER_ADMIN_DEGRADE_URL, status().isForbidden());
            performAndExpectStatus(SUPER_ADMIN_UPDATE_URL, status().isForbidden());
        }
    }

    @Nested
    @WithMockUser(username = "super_admin", roles = "SUPER_ADMIN")
    class super_admin {
        @Test
        @DisplayName(SUCCESS + INDEX_MESSAGE)
        void index_page() throws Exception {
            performAndExpectView(INDEX_URL, INDEX_VIEW);
        }

        @Test
        @DisplayName(SUCCESS + GENRES_MESSAGE)
        void genres_page() throws Exception {
            performAndExpectView(GENRES_URL, GENRE_VIEW);
        }

        @Test
        @DisplayName(FAIL + LOGIN_MESSAGE)
        void login_page() throws Exception {
            performAndExpectStatus(LOGIN_URL, status().isForbidden());
        }

        @Test
        @DisplayName(SUCCESS + WATCHLIST_MESSAGE)
        void watchlist_page() throws Exception {
            performAndExpectView(WATCHLIST_URL, WATCHLIST_VIEW);
        }

        @Test
        @DisplayName(SUCCESS + ADMIN_ANIME_MESSAGE)
        void admin_anime_page() throws Exception {
            performAndExpectView(ADMIN_ANIME_URL, ADMIN_ANIME_VIEW);
        }

        @Test
        @DisplayName(SUCCESS + ADMIN_GENRE_MESSAGE)
        void admin_genre_page() throws Exception {
            performAndExpectView(ADMIN_GENRE_URL, ADMIN_GENRE_VIEW);
        }

        @Test
        @DisplayName(SUCCESS + ADMIN_STUDIO_MESSAGE)
        void admin_studio_page() throws Exception {
            performAndExpectView(ADMIN_STUDIO_URL, ADMIN_STUDIO_VIEW);
        }

        @Test
        @DisplayName(SUCCESS + ADMIN_USER_MESSAGE)
        void admin_user_page() throws Exception {
            performAndExpectView(ADMIN_USER_URL, ADMIN_USER_VIEW);
        }

        @Test
        @DisplayName(SUCCESS + SUPER_ADMIN_MESSAGE)
        void admin_control() throws Exception {
            performAndExpectStatus(SUPER_ADMIN_DEGRADE_URL, redirectedUrl(ADMIN_USER_URL));
            performAndExpectStatus(SUPER_ADMIN_UPDATE_URL, redirectedUrl(ADMIN_USER_URL));
        }
    }

    private void performAndExpectView(String urlTemplate, String view) throws Exception {
        mockMvc.perform(get(urlTemplate)).andDo(print())
                .andExpect(view().name(view));
    }

    private void performAndExpectStatus(String urlTemplate, ResultMatcher status) throws Exception {
        mockMvc.perform(get(urlTemplate)).andDo(print())
                .andExpect(status);
    }

}
