# AnimeHarbour Documentation

AnimeHarbour is a website designed for anime enthusiasts to explore, track, and manage their favorite anime series. Below is a detailed documentation of the website's structure and functionality.

## Index Page

### `/`
- **Description**: The homepage displaying anime content.
- **Accessible by**: All users.

## Genres

### `/genres`
- **Description**: Lists all available genres.
- **Accessible by**: All users.

### `/genres/{id}`
- **Description**: Displays a list of anime within the selected genre. Supports sorting and pagination.
- **Parameters**:
    - `id` - The ID of the selected genre.
- **Accessible by**: All users.

## Watchlist

### `/watchlist`
- **Description**: Displays the user's watchlist where they can see added animes.
- **Accessible by**: Authorized users only.

## Contact Page

### `/contact`
- **Description**: A page where users can find contact information.
- **Accessible by**: All users.

## User Profile

### `/profile`
- **Description**: Authorized users can view their profile details and update their password or profile picture.
- **Accessible by**: Authorized users only.

## Authentication

### `/login`
- **Description**: Login page for unauthorized users.
- **Accessible by**: Unauthorized users only.

### `/register`
- **Description**: Registration page for new users.
- **Accessible by**: Unauthorized users only.

## Search

### `/search`
- **Description**: Search page where users can search for anime and sort results by views, ratings, added date, etc.
- **Accessible by**: All users.

## Anime Details

### `/anime/{id}`
- **Description**: Displays details of the selected anime, including comments and ratings. Authorized users can add anime to their watchlist, rate, and comment. Admin or Super Admin users can see the edit button.
- **Parameters**:
    - `id` - The ID of the selected anime.
- **Accessible by**: All users. Additional features for authorized users and admins.

## Admin Panels

### `/admin/genre`
- **Description**: Genre admin panel where Admin and Super Admin can perform CRUD actions.
- **Accessible by**: Admin and Super Admin users only.

### `/admin/studio`
- **Description**: Anime studio admin panel where Admin and Super Admin can perform CRUD actions.
- **Accessible by**: Admin and Super Admin users only.

### `/admin/anime`
- **Description**: Anime admin panel where Admin and Super Admin can perform CRUD actions.
- **Accessible by**: Admin and Super Admin users only.

### `/admin/user`
- **Description**: User admin panel where Admin and Super Admin can disable or enable users. Only Super Admin can grant privileges.
- **Accessible by**: Admin and Super Admin users only.

## Integration Tests

### `SpringSecurityIntegrationTests`
- **Description**: Contains integration tests for security features.

## Environment Variables

The environment variables should be configured in the `application.yaml` file to ensure proper functioning of the application.
